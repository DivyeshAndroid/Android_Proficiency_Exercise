package com.divyesh.exercise.ui

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.divyesh.exercise.R
import com.divyesh.exercise.adapter.HomeRecyclerViewAdapter
import com.divyesh.exercise.databinding.ActivityMainBinding
import com.divyesh.exercise.models.Row
import com.divyesh.exercise.util.AppLog
import com.divyesh.exercise.util.AppState
import com.divyesh.exercise.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.core.component.KoinApiExtension
import kotlin.math.max

/**
 * [MainActivity] :
 *
 * Activity that show [RecyclerView] with data into it.
 * - handle Pagination and Request data for New Page.
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    private val tag: String = this::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private val rows: ArrayList<Row> = ArrayList()
    private var adapter = HomeRecyclerViewAdapter(rows)
    private lateinit var mainViewModel: MainActivityViewModel
    private val listener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!mainViewModel.isLastPageVisited && (layoutManager is LinearLayoutManager) && (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == rows.size - 1) {
                mainViewModel.requestNextPage()
                AppLog.e(tag, "Request next Page Scroll")
                binding.homeRecyclerView.removeOnScrollListener(this)
            }

            if (!mainViewModel.isLastPageVisited && (layoutManager is StaggeredGridLayoutManager)) {

                val lastPositions =
                    IntArray((layoutManager as StaggeredGridLayoutManager).spanCount)
                val pastVisibleItems =
                    (layoutManager as StaggeredGridLayoutManager).findFirstCompletelyVisibleItemPositions(
                        lastPositions
                    )
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = max(pastVisibleItems[0], pastVisibleItems[1])


                if ((visibleItemCount + lastVisibleItem) >= totalItemCount) {
                    mainViewModel.requestNextPage()
                    binding.homeRecyclerView.removeOnScrollListener(this)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        mainViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainViewModel.getNationDetails()
        setUpRecycler()
        subscribeObservers()

        binding.data = mainViewModel
        binding.executePendingBindings()

    }


    /**
     * This function set [RecyclerView] Properties.
     */
    private fun setUpRecycler() {
        updateUI(rows.isNullOrEmpty())
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.homeRecyclerView.layoutManager = layoutManager
        binding.homeRecyclerView.adapter = adapter
    }


    /**
     * function [Observer] CurrentState From ViewModel and set data accordingly.
     * also check orientation of Activity and change [RecyclerView.LayoutManager] accordingly
     *
     * for-example :
     * App Is Loading While getting response from server.
     * After getting response it is Success or failure according that sets Data.
     */
    private fun subscribeObservers() {
        mainViewModel.mCurrentState.observe(this, Observer { appState ->
            when (appState) {
                is AppState.Loading -> {
                    binding.circularProgressbar.visibility = View.VISIBLE
                    AppLog.e(tag, "LOADING")
                }
                is AppState.Success -> {
                    binding.circularProgressbar.visibility = View.GONE
                    val mNationDetail = appState.data
                    title = if (mNationDetail.title != null) mNationDetail.title else title
                    loadDataInToRecyclerView(mNationDetail.rows, appState.pageno)
                    AppLog.e(tag, "Success ${mNationDetail.rows!!.size}")
                }

                is AppState.NoConnection -> {
                    binding.circularProgressbar.visibility = View.GONE
                    binding.root.showSnackBar(
                        getString(R.string.network_request_error),
                        R.color.purple_500
                    )
                    val mNationDetail = appState.data
                    title = if (mNationDetail.title != null) mNationDetail.title else title
                    loadDataInToRecyclerView(mNationDetail.rows, appState.pageno)
                    AppLog.e(tag, "No Connection ${mNationDetail.rows}")
                }
                is AppState.EmptyData -> {
                    binding.circularProgressbar.visibility = View.GONE
                    AppLog.e(tag, "No Data")
                    binding.root.showSnackBar(
                        getString(R.string.no_data_available),
                        R.color.purple_500
                    )

                    updateUI(true)
                }
                else -> {
                    binding.circularProgressbar.visibility = View.GONE
                }
            }
        })
        when (resources?.configuration?.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                this.rows.clear()
                adapter.notifyDataSetChanged()
                mainViewModel.resetPageData()

                layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
                )
                binding.homeRecyclerView.layoutManager = layoutManager
                binding.data = mainViewModel

                Handler().postDelayed(
                    {
                        binding.homeRecyclerView.addOnScrollListener(listener)
                    }, 1000
                )
//                resetDataAndUpdateUI()
            }
            else -> {
                this.rows.clear()
                adapter.notifyDataSetChanged()
                mainViewModel.resetPageData()

                layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                binding.homeRecyclerView.layoutManager = layoutManager
                binding.data = mainViewModel
                Handler().postDelayed(
                    {

                        binding.homeRecyclerView.addOnScrollListener(listener)
                    }, 1000
                )
//                resetDataAndUpdateUI()
            }

        }
    }


    /**
     * Loads Data into [RecyclerView] and also code for pagination set properties for [RecyclerView].
     *
     * @param rows for Display That Row Data into [RecyclerView]
     * @param currentPage for checking is that page is Last Page
     */
    private fun loadDataInToRecyclerView(rows: ArrayList<Row>?, currentPage: Int) {
        if (rows.isNullOrEmpty()) {
            return
        }
        AppLog.e(tag, "current_page: $currentPage")

        rows.forEach {
            this.rows.add(it)
        }
        updateUI(this.rows.isNullOrEmpty())
        if (!mainViewModel.isLastPageVisited) {
            adapter.notifyDataSetChanged()
        }
        if (rows.size < mainViewModel.noOFItemPerPage) {
            mainViewModel.isLastPageVisited = true
            mainViewModel.isLastPageInt = currentPage
            mainViewModel.currentPage--
        }
        Handler().postDelayed(
            {
                binding.homeRecyclerView.addOnScrollListener(listener)
            }, 2000
        )
    }


    /**
     * function Check Whether to show [RecyclerView] or show message for data unavailable
     *
     * @param shouldShowEmpty
     */
    private fun updateUI(shouldShowEmpty: Boolean) {
        if (shouldShowEmpty) {
            binding.homeRecyclerView.visibility = View.GONE
            binding.noDataTv.visibility = View.VISIBLE
        } else {
            binding.homeRecyclerView.visibility = View.VISIBLE
            binding.noDataTv.visibility = View.GONE
        }
    }


    /**
     *this extention function implements [Snackbar] into [View] so can call from parentView
     *
     * @param Message use for showing that message in [Snackbar]
     * @param colorResource use for change text colour [Snackbar]
     */
    private fun View?.showSnackBar(Message: String, colorResource: Int) {
        this?.let {
            Snackbar.make(it, Message, Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(it.context, colorResource))
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        MenuInflater(this).inflate(R.menu.main_activity_menu, menu)

        menu!!.findItem(R.id.refresh_data).setOnMenuItemClickListener {
            resetDataAndUpdateUI()
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu)
    }


    /**
     * it ResetData from view and make call for data.
     *
     */
    private fun resetDataAndUpdateUI() {
        this.rows.clear()
        adapter.notifyDataSetChanged()
        mainViewModel.resetPageData()
        binding.homeRecyclerView.removeOnScrollListener(listener)
        mainViewModel.getNationDetails()
    }
}