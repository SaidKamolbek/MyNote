package com.example.mynote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.R
import com.example.mynote.databinding.MsgItemBinding
import com.example.mynote.model.Note
import com.example.mynote.ui.notes.HomeFragmentDirections
import com.example.mynote.ui.starredNotes.StarredNotesDirections


class AllFavNotesAdapter : RecyclerView.Adapter<AllFavNotesAdapter.MyHolder>() {

    private val list = ArrayList<Note>()

    inner class MyHolder(val binding: MsgItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = list[adapterPosition]
            binding.apply {
                textMessage.text = item.text
                textTitle.text = item.title
                textBackground.setBackgroundColor(item.color)
                textDate.text = item.date + "    " + item.time
            }
        }

        init {
            itemView.setOnClickListener {
                val action =
                    StarredNotesDirections.actionNavStarredNotesToUpdate(list[adapterPosition])
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = MsgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(items: List<Note>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

}