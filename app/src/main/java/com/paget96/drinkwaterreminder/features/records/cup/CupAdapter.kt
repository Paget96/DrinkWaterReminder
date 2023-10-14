package com.paget96.drinkwaterreminder.features.records.cup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.data.db.Cup
import com.paget96.drinkwaterreminder.data.db.CupType
import com.paget96.drinkwaterreminder.data.db.SelectedCup
import com.paget96.drinkwaterreminder.databinding.ItemCupBinding
import com.paget96.drinkwaterreminder.databinding.ItemCustomCupInputBinding

class CupAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<CupAdapter.Item, ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            R.layout.item_cup -> CupViewHolder(
                ItemCupBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.item_custom_cup_input -> CustomCpuInputViewHolder(
                ItemCustomCupInputBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            Item.CupInputItem -> R.layout.item_custom_cup_input
            is Item.CupItem -> R.layout.item_cup
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val currentItem = getItem(position)) {
            is Item.CupInputItem -> holder as CustomCpuInputViewHolder
            is Item.CupItem -> (holder as CupViewHolder).bind(currentItem.cup)
        }
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
                    "${currentItem.amountOfWater} ml"
                }

                root.setOnClickListener {
                    listener.onItemSelectedClick(
                        SelectedCup(currentItem)
                    )
                }
            }
        }
    }

    inner class CustomCpuInputViewHolder(
        binding: ItemCustomCupInputBinding
    ) : ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemSelectedClick(cup: SelectedCup)
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
    }

    sealed class Item {
        data class CupItem(val cup: Cup) : Item()
        data object CupInputItem : Item()
    }
}