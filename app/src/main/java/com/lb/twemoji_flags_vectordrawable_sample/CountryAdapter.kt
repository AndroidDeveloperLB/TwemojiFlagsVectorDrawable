package com.lb.twemoji_flags_vectordrawable_sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lb.twemoji_flags_vectordrawable_sample.databinding.ItemCountryBinding

class CountryAdapter(
    private var countries: List<Country>,
    private val onItemClick: (Country) -> Unit
) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCountryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]
        holder.binding.textFlag.text = country.flagEmoji
        holder.binding.textName.text = country.name
        holder.binding.textLocale.text = country.code
        holder.itemView.setOnClickListener { onItemClick(country) }
    }

    override fun getItemCount(): Int = countries.size

    fun updateList(newList: List<Country>) {
        val diffResult = DiffUtil.calculateDiff(CountryDiffCallback(countries, newList))
        countries = newList
        diffResult.dispatchUpdatesTo(this)
    }

    private class CountryDiffCallback(
        private val oldList: List<Country>, private val newList: List<Country>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].code == newList[newItemPosition].code
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
