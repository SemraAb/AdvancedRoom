package com.samra.tayqatech.ui.dialog.city

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samra.tayqatech.data.local.CityEntity
import com.samra.tayqatech.data.local.CountryEntity
import com.samra.tayqatech.data.network.model.City
import com.samra.tayqatech.databinding.ItemFilterBinding
import com.samra.tayqatech.ui.dialog.country.CountryCheckedListener

class CityAdapter(var city: List<CityEntity>, var listener: CityCheckedListener) :
    RecyclerView.Adapter<CityAdapter.CityHolder>() {
    private var checkedCities = mutableSetOf<CityEntity>()


    class CityHolder(var binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        return CityHolder(
            ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return city.size
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.binding.checkBox.text = city[position].name
        holder.binding.checkBox.isChecked = checkedCities.contains(city[position])

        holder.binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkedCities.add(city[position])
            } else {
                checkedCities.remove(city[position])
            }
            listener.onCityChecked(checkedCities.toList())
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun onDataChange(data: List<CityEntity>?) {
        if (data != null) {
            city = data
        }
        notifyDataSetChanged()
    }
}

interface CityCheckedListener {
    fun onCityChecked(checkedCities: List<CityEntity>)
}