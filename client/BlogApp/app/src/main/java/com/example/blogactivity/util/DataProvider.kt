package com.example.blogactivity.util

import com.example.blogactivity.contract.Blog
import com.example.blogactivity.contract.Response

object DataProvider {
    var response: Response = Response()
    var blog: Blog = Blog()
    lateinit var userId:String
    lateinit var userName:String
}