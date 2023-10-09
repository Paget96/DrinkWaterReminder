package com.paget96.drinkwaterreminder.features.records.cup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.paget96.drinkwaterreminder.data.db.Cup
import com.paget96.drinkwaterreminder.data.db.CupType
import com.paget96.drinkwaterreminder.data.db.SelectedCup
import com.paget96.drinkwaterreminder.databinding.ItemCupBinding

class CupAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Cup, CupAdapter.CupViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CupViewHolder {
        val binding = ItemCupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CupViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CupViewHolder(
        private val binding: ItemCupBinding
    ) : ViewHolder(binding.root) {
        fun bind(currentItem: Cup) {
            with(binding) {
                wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        currentItem.cupType.icon
                    )
                )

                amountOfWater.text = if (currentItem.cupType == CupType.CupCustom) {
                    "Custom"
                } else {
                    "${currentItem.amountOfWater}ml"
                }

                root.setOnClickListener {
                    listener.onItemSelectedClick(
                        SelectedCup(currentItem)
                    )
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemSelectedClick(cup: SelectedCup)
    }

    class DiffCallback : DiffUtil.ItemCallback<Cup>() {
        override fun areItemsTheSame(
            oldItem: Cup,
            newItem: Cup
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Cup,
            newItem: Cup
        ): Boolean = oldItem == newItem
    }
}