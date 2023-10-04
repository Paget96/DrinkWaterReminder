package com.paget96.drinkwaterreminder.features.records

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.data.db.WateringRecord
import com.paget96.drinkwaterreminder.databinding.ItemWateringRecordBinding
import com.paget96.drinkwaterreminder.utils.DateUtils

class RecordsAdapter : ListAdapter<WateringRecord, RecordsAdapter.RecordsViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsViewHolder {
        val binding = ItemWateringRecordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class RecordsViewHolder(
        private val binding: ItemWateringRecordBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: WateringRecord) {
            with(binding) {
                time.text = DateUtils.convertMsToDate(
                    currentItem.timeStamp,
                    useSeconds = false,
                    useMilliseconds = false,
                    showDate = false
                )
                wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_water_cup_customize
                    )
                )
                isUpcoming.visibility = if (currentItem.isUpcoming) View.VISIBLE else View.GONE
                more.visibility = if (currentItem.isUpcoming) View.INVISIBLE else View.VISIBLE
                amountOfWater.text = itemView.context.getString(
                    R.string.ml,
                    "${currentItem.amountOfWater}"
                )
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<WateringRecord>() {
        override fun areItemsTheSame(
            oldItem: WateringRecord,
            newItem: WateringRecord
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: WateringRecord,
            newItem: WateringRecord
        ): Boolean = oldItem == newItem
    }
}