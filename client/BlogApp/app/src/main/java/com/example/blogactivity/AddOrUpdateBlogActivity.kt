package com.example.blogactivity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.blogactivity.contract.Blog
import com.example.blogactivity.contract.Request
import retrofit2.Call
import retrofit2.Callback
import com.example.blogactivity.contract.Response
import com.example.blogactivity.databinding.ActivityAddOrUpdateBlogBinding
import com.example.blogactivity.network.IRequestContract
import com.example.blogactivity.network.NetworkClient
import com.example.blogactivity.util.Constant
import com.example.blogactivity.util.DataProvider

class AddOrUpdateBlogActivity : AppCompatActivity() , Callback<Response> {
    lateinit var userId:String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var progressDialog: ProgressDialog
    private val retrofitClient = NetworkClient.getNetworkClient()
    private val requestContract = retrofitClient.create(IRequestContract::class.java)
    private var reason:Int = 0
    private lateinit var editedBlog: Blog
    private lateinit var binding: ActivityAddOrUpdateBlogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_or_update_blog)

        sharedPreferences = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("please wait...")
        progressDialog.setCancelable(true)

        userId = sharedPreferences.getString(Constant.KEY_USER_ID,"").toString()
        reason = intent.getIntExtra(Constant.KEY_REASON,0)

        renderUIForEdit()

        binding.btnSubmit.setOnClickListener {
            val title = binding.edTitle.text.toString().trim()
            val description = binding.edDescription.text.toString().trim()

            if(title.isNotEmpty() && description.isNotEmpty()){
                var request = Request()
                if(reason==2){
                    request = Request(
                        action = Constant.UPDATE_BLOG,
                        userId = userId,
                        blogId = editedBlog.blogId,
                        title = title,
                        description = description
                    )
                }
                else{
                    request = Request(
                        action = Constant.ADD_BLOG,
                        userId = userId,
                        title = title,
                        description = description
                    )
                }
                progressDialog.show()
                val callResponse = requestContract.makeApiCall(request)
                callResponse.enqueue(this)
            }
            else{
                showToast("Please enter blog details")
            }
        }
    }

    private fun renderUIForEdit(){
        if(reason==2){
            editedBlog = DataProvider.blog
            binding.edTitle.setText(editedBlog.title)
            binding.edDescription.setText(editedBlog.description)
            binding.btnSubmit.text = "UPDATE"
        }
    }
    override fun onFailure(call: Call<Response>, t: Throwable) {
        if(progressDialog.isShowing)
            progressDialog.dismiss()
        showToast("Server is not responding. Please contact your system administrator")
    }

    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
        if(progressDialog.isShowing)
            progressDialog.dismiss()
        if(response.body()!=null){
            val serverResponse = response.body()
            if(serverResponse!!.status){
                showToast(serverResponse.message)
                Intent(this,HomeActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(this)
                }
            }
            else{
                showToast(serverResponse.message)
            }
        }
        else{
            showToast("Server is not responding. Please contact your system administrator")
        }
    }
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
