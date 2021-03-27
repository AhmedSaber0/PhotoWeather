package com.rebusta.photoweather.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rebusta.photoweather.data.local.entity.WeatherPhoto
import com.rebusta.photoweather.R
import com.rebusta.photoweather.databinding.LayoutWeatherPhotoItemBinding

class WeatherPhotoViewHolder(
    private val binding: LayoutWeatherPhotoItemBinding,
    private val listener: (View, WeatherPhoto, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(weatherPhoto: WeatherPhoto?) {
        weatherPhoto?.let {
            Glide.with(itemView).load(it.photoPath).placeholder(R.color.gray)
                .error(R.color.gray).into(binding.avatarImv)
            binding.nameTxv.text = it.name
            itemView.setOnClickListener { view ->
                listener.invoke(view, it, adapterPosition)
            }
        }
    }


    companion object {
        fun create(
            parent: ViewGroup,
            listener: (View, WeatherPhoto, Int) -> Unit
        ): WeatherPhotoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_weather_photo_item, parent, false)
            val binding = LayoutWeatherPhotoItemBinding.bind(view)
            return WeatherPhotoViewHolder(binding, listener)
        }
    }
}