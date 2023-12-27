package com.samra.tayqatech.ui.dialog.country

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samra.tayqatech.data.local.CountryEntity
import com.samra.tayqatech.databinding.ItemFilterBinding

class CountryAdapter(var country: List<CountryEntity>, var listener: CountryCheckedListener) :
    RecyclerView.Adapter<CountryAdapter.CountryHolder>() {
    private var checkedCountries = mutableSetOf<CountryEntity>()

    class CountryHolder(var binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        return CountryHolder(
            ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return country.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.binding.checkBox.text = country[position].name
        holder.binding.checkBox.isChecked = checkedCountries.contains(country[position])

        holder.binding.checkBox.setOnCheckedChangeListener {_ , isChecked ->
            if(isChecked){
                checkedCountries.add(country[position])
            }else{
             checkedCountries.remove(country[position])
            }
            listener.onCountryChecked(checkedCountries.toList())
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun onDataChange(data: List<CountryEntity>?) {
        if (data != null) {
            country = data
        }
        notifyDataSetChanged()
    }
}

interface CountryCheckedListener  {
    fun onCountryChecked(checkedCountries: List<CountryEntity>)
}