package com.samra.tayqatech.ui.person

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samra.tayqatech.data.local.PeopleEntity
import com.samra.tayqatech.data.network.model.People
import com.samra.tayqatech.databinding.ItemPeopleBinding

class PersonAdapter(private var people: List<PeopleEntity>) :
    RecyclerView.Adapter<PersonAdapter.PeopleHolder>() {
    class PeopleHolder(var binding: ItemPeopleBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder {
        return PeopleHolder(
            ItemPeopleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return people.size
    }

    override fun onBindViewHolder(holder: PeopleHolder, position: Int) {
        holder.binding.peopleName.text = people[position].name
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onDataChange(data: List<PeopleEntity>?) {
        if (data != null) {
            people = data
        }
        notifyDataSetChanged()
    }
}

