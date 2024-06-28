package com.example.blogactivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.blogactivity.adapter.MyBlogAdapter
import com.example.blogactivity.contract.Blog
import com.example.blogactivity.databinding.ActivityViewMyBlogsBinding
import com.example.blogactivity.util.Constant
import com.example.blogactivity.util.DataProvider

class ViewMyBlogsActivity : AppCompatActivity() {
    lateinit var adapter: MyBlogAdapter
    lateinit var dataSource:MutableList<Blog>
    private lateinit var context: Context
    private lateinit var activity: Activity
    private lateinit var binding: ActivityViewMyBlogsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_my_blogs)

        context = this
        activity = this
        dataSource = DataProvider.response.myBlogs
        if(dataSource.size>0){
            adapter = MyBlogAdapter(activity,context,dataSource)

            binding.noBlogAvailable.visibility = View.INVISIBLE

            binding.rvMyBlogs.visibility = View.VISIBLE
            binding.rvMyBlogs.adapter = adapter
        }
        else{
            binding.noBlogAvailable.visibility = View.VISIBLE
            binding.rvMyBlogs.visibility = View.INVISIBLE
        }

        binding.add.setOnClickListener {
            Intent(this,AddOrUpdateBlogActivity::class.java).apply {
                putExtra(Constant.KEY_REASON,1) //1 Mean Add
                startActivity(this)
            }
        }
    }
}
