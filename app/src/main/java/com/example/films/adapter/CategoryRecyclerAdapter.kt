package com.example.films.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import com.example.films.model.Category
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryRecycleAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Category> = ArrayList()

    fun setData(categories : List<Category>){
        items = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        CategoryViewHolder(holder.itemView).bind(items[position])
    }

    override fun getItemCount() = items.size

}

class CategoryViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    private  val categoryTopic: TextView = itemView.topic

    fun bind(topic: Category) {
        categoryTopic.text = topic.name
    }

}
