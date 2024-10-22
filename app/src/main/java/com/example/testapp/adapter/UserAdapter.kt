package com.example.testapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.model.Todo
import com.example.testapp.databinding.RowLayoutBinding


class UserAdapter(private var todoList: List<Todo> = emptyList()) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.title.text = todo.title
            binding.body.text = todo.completed.toString().toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount() = todoList.size


    fun updateUsers(newTodos: List<Todo>) {
        todoList = newTodos
        notifyDataSetChanged()
    }
}


