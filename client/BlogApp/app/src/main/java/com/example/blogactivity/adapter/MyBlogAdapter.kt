package com.example.blogactivity.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.blogactivity.AddOrUpdateBlogActivity
import com.example.blogactivity.contract.Blog
import com.example.blogactivity.contract.Request
import com.example.blogactivity.contract.Response
import com.example.blogactivity.databinding.ItMyBlogBinding
import com.example.blogactivity.network.IRequestContract
import com.example.blogactivity.network.NetworkClient
import com.example.blogactivity.util.Constant
import com.example.blogactivity.util.DataProvider
import retrofit2.Call
import retrofit2.Callback

class MyBlogAdapter(var activity: Activity, var context: Context, var dataSource:MutableList<Blog>): RecyclerView.Adapter<MyBlogAdapter.MyBlogViewHolder>(),
    Callback<Response> {

    private var progressDialog: ProgressDialog = ProgressDialog(context)
    private val retrofitClient = NetworkClient.getNetworkClient()
    private val requestContract = retrofitClient.create(IRequestContract::class.java)
    private lateinit var deletedBlog:Blog
    private var deletedPosition:Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBlogViewHolder {
        val view = ItMyBlogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyBlogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: MyBlogViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val blog = dataSource[position]
        holder.title.text = blog.title
        holder.description.text = blog.description
        holder.dateTime.text = blog.dateTime

        holder.btnEdit.setOnClickListener {
            Intent(context, AddOrUpdateBlogActivity::class.java).apply {
                DataProvider.blog = blog
                putExtra(Constant.KEY_REASON,2)  //2 Mean Edit
                activity.startActivity(this)
            }
        }

        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Blog App Alert")
                .setMessage("Are you sure? You want to delete this Blog")
                .setPositiveButton("Yes") { dialog, which ->
                    progressDialog.setMessage("Please wait...")
                    progressDialog.setCancelable(false)
                    deletedBlog = blog
                    deletedPosition = position
                    val request = Request(
                        action = Constant.DELETE_BLOG,
                        userId = DataProvider.userId,
                        blogId = blog.blogId
                    )
                    progressDialog.show()
                    val callResponse = requestContract.makeApiCall(request)
                    callResponse.enqueue(this)
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog?.dismiss()
                }
                .create()
                .show()
        }
    }

    class MyBlogViewHolder(view: ItMyBlogBinding): RecyclerView.ViewHolder(view.root){
        var title = view.title
        var description = view.description
        var dateTime = view.dateTime
        var btnEdit = view.edit
        var btnDelete = view.delete
    }

    override fun onFailure(call: Call<Response>, t: Throwable) {
        if(progressDialog.isShowing)
            progressDialog.dismiss()
        context.showToast("Server is not responding. Please contact your system administrator")
    }

    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
        if(progressDialog.isShowing)
            progressDialog.dismiss()
        if(response.body()!=null){
            val serverResponse = response.body()
            if(serverResponse!!.status){
                dataSource.remove(deletedBlog)
                notifyItemRemoved(deletedPosition)
                notifyItemRangeChanged(deletedPosition,dataSource.size)
                context.showToast(serverResponse.message)
            }
            else{
                context.showToast(serverResponse.message)
            }
        }
        else{
            context.showToast("Server is not responding. Please contact your system administrator")
        }
    }
    fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}