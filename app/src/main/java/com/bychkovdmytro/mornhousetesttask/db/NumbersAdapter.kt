package com.bychkovdmytro.mornhousetesttask.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bychkovdmytro.mornhousetesttask.R
import com.bychkovdmytro.mornhousetesttask.databinding.ItemLayoutBinding
import com.bychkovdmytro.mornhousetesttask.entities.NumbersList

class NumbersAdapter (private val listener: Listener) : ListAdapter<NumbersList, NumbersAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = ItemLayoutBinding.bind(view)

        fun setData(number: NumbersList, listener: Listener) = with(binding){
            numberItem.text = number.number_fact
            itemView.setOnClickListener { listener.onClickItem(number)}
        }

        companion object{
            fun create(parent: ViewGroup):ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                inflate(R.layout.item_layout, parent, false))
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<NumbersList>(){
        override fun areItemsTheSame(oldItem: NumbersList, newItem: NumbersList): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: NumbersList, newItem: NumbersList): Boolean {
            return oldItem==newItem
        }
    }

    interface Listener{
        fun onClickItem(number: NumbersList)
    }

}