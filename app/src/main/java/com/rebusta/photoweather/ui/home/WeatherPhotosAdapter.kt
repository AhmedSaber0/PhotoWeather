package com.rebusta.photoweather.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.rebusta.photoweather.data.local.entity.WeatherPhoto

class WeatherPhotosAdapter(private val listener: (View, WeatherPhoto, Int) -> Unit) :
    ListAdapter<WeatherPhoto, WeatherPhotoViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherPhotoViewHolder {
        return WeatherPhotoViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: WeatherPhotoViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<WeatherPhoto>() {
            override fun areItemsTheSame(
                oldItem: WeatherPhoto,
                newItem: WeatherPhoto
            ): Boolean {
                return oldItem.photoPath == newItem.photoPath
            }

            override fun areContentsTheSame(
                oldItem: WeatherPhoto,
                newItem: WeatherPhoto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}