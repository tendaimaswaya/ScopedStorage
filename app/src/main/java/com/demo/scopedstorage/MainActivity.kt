package com.demo.scopedstorage

import android.content.Intent
import android.os.Bundle
import java.io.File
import android.view.View
import androidx.core.content.FileProvider
import com.demo.scopedstorage.interfaces.IRetrofit
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import java.lang.Exception


class MainActivity : BaseActivity() {

    var demoFileName = "FileDemoName.pdf"
    var token = "Bearer place_your_token_here"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAndCreateDirectory()
    }

    fun onClick(view: View){
        if(!isFilePresent(demoFileName)){
            alert("downloading")
            downloadFile(demoFileName)
        }else{
            openFileExternally(File(getFile(demoFileName).path))
        }
    }

    private fun downloadFile(filename:String){
        val api = IRetrofit.retrofit.create(IRetrofit::class.java)
        api.download("_url_ext_here", token)
            ?.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                    try {
                        writeToDir(response, filename)
                    } catch (ex: Exception) {
                        alert(ex.message)
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable?) {
                    alert(t.toString())
                }
            })
    }




}