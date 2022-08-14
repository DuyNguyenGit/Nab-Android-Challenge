package com.p109.nab_android_challenge.ui.search.view.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.p109.nab_android_challenge.databinding.DailyWeatherItemBinding
import com.p109.nab_android_challenge.domain.search.businessmodel.DailyWeatherDomainModel

class DailyWeatherListAdapter :
    DataBoundListAdapter<DailyWeatherDomainModel, DailyWeatherItemBinding>(diffCallback) {

    override fun createBinding(parent: ViewGroup): DailyWeatherItemBinding {
        val binding = DailyWeatherItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return binding
    }

    override fun bind(binding: DailyWeatherItemBinding, item: DailyWeatherDomainModel) {
        binding.dailyUIModel = item
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<DailyWeatherDomainModel>() {
            override fun areItemsTheSame(oldItem: DailyWeatherDomainModel, newItem: DailyWeatherDomainModel): Boolean {
                return oldItem.date == newItem.date && oldItem.city == newItem.city
            }

            override fun areContentsTheSame(oldItem: DailyWeatherDomainModel, newItem: DailyWeatherDomainModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
