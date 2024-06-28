package com.example.blogactivity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.blogactivity.adapter.AllBlogAdapter
import com.example.blogactivity.contract.Blog
import com.example.blogactivity.databinding.ActivityViewAllBlogsBinding
import com.example.blogactivity.util.DataProvider

class ViewAllBlogsActivity : AppCompatActivity() {

    lateinit var adapter: AllBlogAdapter
    lateinit var dataSource:MutableList<Blog>
    private lateinit var context: Context
    private lateinit var binding: ActivityViewAllBlogsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_view_all_blogs)

        context = this
        dataSource = DataProvider.response.allBlogs
        adapter = AllBlogAdapter(context,dataSource)
        binding.rvAllBlogs.adapter = adapter
    }
}
