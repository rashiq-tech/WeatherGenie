package com.rashiq_tech.weathergenie.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rashiq_tech.weathergenie.databinding.RowItemCityBinding

class CitiesListAdapter constructor(val selectCityCallback: SelectCityCallback): ListAdapter<String, CitiesListAdapter.CitiesViewHolder>(
    CitiesDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val binding = RowItemCityBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CitiesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CitiesViewHolder,
        position: Int
    ) {
        val currentItem = getItem(position)
        if(currentItem != null){
            holder.bind(currentItem)
        }


    }
    inner class CitiesViewHolder(
        private val binding: RowItemCityBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition

                if(position!= RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    selectCityCallback.onCitySelect(item)
                }
            }
        }
        fun bind(article: String){
            binding.apply {
                tvName.text = article
            }
        }

    }

    interface SelectCityCallback{
        fun onCitySelect(item: String?)
    }

    class CitiesDiffUtil : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }
}