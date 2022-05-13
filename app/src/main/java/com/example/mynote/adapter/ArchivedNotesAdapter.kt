package com.example.mynote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.R
import com.example.mynote.databinding.MsgItemBinding
import com.example.mynote.model.Note
import com.example.mynote.ui.archived.ArchivedDirections
import com.example.mynote.ui.notes.HomeFragmentDirections


class ArchivedNotesAdapter : RecyclerView.Adapter<ArchivedNotesAdapter.MyHolder>() {

    private val list = ArrayList<Note>()
    private lateinit var listener: (Note) -> Unit

    fun setListener(l: (Note) -> Unit) {
        listener = l
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = MsgItemBinding.bind(itemView)
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
                val action = ArchivedDirections.actionNavArchivedToUpdate(list[adapterPosition])
                itemView.findNavController().navigate(action)
            }
            itemView.setOnLongClickListener {
                listener.invoke(list[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.msg_item, parent, false)
        )

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