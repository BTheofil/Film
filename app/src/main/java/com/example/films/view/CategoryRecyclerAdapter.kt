package com.example.films.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import com.example.films.model.Category
import kotlinx.android.synthetic.main.activity_details_category.view.*

class CategoryRecyclerAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = CategoryViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.activity_details_category, parent, false)
        )
        return v
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        CategoryViewHolder(holder.itemView).bind(items[position])
    }

    override fun getItemCount() = items.size

//    fun submitList(CList: List<Category>) {
//        items = CList
//        notifyDataSetChanged()
//    }
}

class CategoryViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    private  val categoryTopic: TextView = itemView.topic

    fun bind(topic: Category) {
        categoryTopic.text = topic.toString()
    }
}