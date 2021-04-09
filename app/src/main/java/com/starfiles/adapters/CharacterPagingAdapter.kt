package com.starfiles.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.starfiles.R
import com.starfiles.data.models.Character
import com.starfiles.databinding.ListItemBinding
import com.starfiles.views.CharacterDialogFragment

class CharacterPagingAdapter(private val fragmentManager: FragmentManager): PagingDataAdapter<Character, CharacterPagingAdapter.ViewHolder>(
    COMPARATOR) {

    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cur = getItem(position) ?: return
        holder.binding.character = cur
        holder.binding.listItem.setOnClickListener{
            Toast.makeText(it.context, "Eye color: ${cur.eye_color}" , Toast.LENGTH_SHORT).show()
            CharacterDialogFragment(cur).show(fragmentManager, "Character Page Adapter")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemBind: ListItemBinding = DataBindingUtil.inflate<ListItemBinding>(LayoutInflater.from(parent.context), R.layout.list_item, parent, false)
        return ViewHolder(listItemBind)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}