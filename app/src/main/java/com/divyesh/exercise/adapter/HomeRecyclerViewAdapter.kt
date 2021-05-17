package com.divyesh.exercise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.divyesh.exercise.R
import com.divyesh.exercise.databinding.SingleRecyclerviewItemViewBinding
import com.divyesh.exercise.models.Row

/**
 * [HomeRecyclerViewAdapter] :
 *
 * Adapter class for [RecyclerView] that showing data on [MainActivity]
 *
 * @author Divyesh Kalotra
 * @version 1.0.0
 * @since 17-05-2021
 */

class HomeRecyclerViewAdapter(val data: ArrayList<Row>) :
    RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HomeRecyclerViewHolder(
            DataBindingUtil.inflate(inflater,
                R.layout.single_recyclerview_item_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        val currentDataItem = data[position]
        holder.binding.row = currentDataItem
        Glide.with(holder.itemView.context).load(currentDataItem.imageHref)
            .placeholder(R.drawable.no_image)
            .into(holder.binding.descriptionIv)
        holder.binding.executePendingBindings()
    }

    class HomeRecyclerViewHolder(val binding: SingleRecyclerviewItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

}