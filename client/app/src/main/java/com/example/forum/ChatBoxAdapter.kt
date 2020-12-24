package com.example.forum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatBoxAdapter : RecyclerView.Adapter<ChatBoxAdapter.ViewHolder>() {
    private var messages = listOf<Message>()

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = messages[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nickname: TextView = itemView.findViewById(R.id.nickname)
        private val message: TextView = itemView.findViewById(R.id.message)

        fun bind(item: Message) {
            nickname.text = item.nickname
            message.text = item.message
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item, parent, false)
                return ViewHolder(view)
            }
        }
    }
}