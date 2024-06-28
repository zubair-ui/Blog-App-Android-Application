package com.example.blogactivity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.blogactivity.contract.Request
import com.example.blogactivity.contract.Response
import com.example.blogactivity.databinding.ActivityHomeBinding
import com.example.blogactivity.network.IRequestContract
import com.example.blogactivity.network.NetworkClient
import com.example.blogactivity.util.Constant
import com.example.blogactivity.util.DataProvider
import retrofit2.Call
import retrofit2.Callback

class HomeActivity : AppCompatActivity(), View.OnClickListener, Callback<Response> {
    lateinit var userId:String
    lateinit var userName:String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var progressDialog: ProgressDialog
    private val retrofitClient = NetworkClient.getNetworkClient()
    private val requestContract = retrofitClient.create(IRequestContract::class.java)
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this,R.layout.activity_home)

        sharedPreferences = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("please wait...")
        progressDialog.setCancelable(true)

        title = "Blog App"
        userId = sharedPreferences.getString(Constant.KEY_USER_ID,"").toString()
        userName = sharedPreferences.getString(Constant.KEY_USER_NAME,"").toString()

        DataProvider.userId = userId
        DataProvider.userName = userName

        binding.txtUserName.text = "Welcome $userName"

        binding.allBlogs.setOnClickListener(this)
        binding.myBlogs.setOnClickListener(this)
        binding.signOut.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        progressDialog.show()
        val request = Request(
            action = Constant.GET_BLOGS,
            userId = userId
        )
        val callResponse = requestContract.makeApiCall(request)
        callResponse.enqueue(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.allBlogs -> {
                if(DataProvider.response.allBlogs.size>0){
                    Intent(this,ViewAllBlogsActivity::class.java).apply {
                        startActivity(this)
                    }
                }
                else{
                    showToast("Blogs are not available")
                }
            }
            R.id.myBlogs -> {
                Intent(this,ViewMyBlogsActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.signOut -> {
                signOut();
            }
        }
    }

    private fun signOut(){
        val editor = sharedPreferences.edit()
        editor.clear().commit()

        Intent(this,MainActivity::class.java).apply {
            startActivity(this)
            finish()
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
                DataProvider.response = serverResponse
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
