package com.example.blogactivity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blogactivity.contract.Blog
import com.example.blogactivity.databinding.ItAllBlogBinding

class AllBlogAdapter(var context: Context, var dataSource:MutableList<Blog>): RecyclerView.Adapter<AllBlogAdapter.AllBlogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBlogViewHolder {
        val binding = ItAllBlogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllBlogViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: AllBlogViewHolder, position: Int) {
        val blog = dataSource[position]
        holder.title.text = blog.title
        holder.description.text = blog.description
        holder.blogger.text= blog.bloggerName
        holder.dateTime.text = blog.dateTime
    }

    class AllBlogViewHolder(view: ItAllBlogBinding): RecyclerView.ViewHolder(view.root){
        var title = view.title
        var description = view.description
        var blogger = view.blogger
        var dateTime = view.dateTime
    }


}