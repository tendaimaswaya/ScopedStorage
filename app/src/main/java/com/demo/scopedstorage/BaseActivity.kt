package com.demo.scopedstorage

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import okhttp3.ResponseBody
import org.apache.commons.io.IOUtils
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

open class BaseActivity : AppCompatActivity() {
    fun <T> alert(msg:T){
        Toast.makeText(this, msg.toString(), Toast.LENGTH_LONG).show()
    }
    //file operations
    protected fun getFile(fileName:String): File {
        return File(applicationContext.getDir("OurAppDirectory", Context.MODE_PRIVATE), fileName)
    }

    protected fun isFilePresent(fileName:String):Boolean{
        val myDir = getFile(fileName)
        return myDir.exists()
    }

    protected fun checkAndCreateDirectory(){
            val myDir = File(applicationContext?.filesDir, "OurAppDirectory")
            if (!myDir.exists()) {
                myDir.mkdirs()
            }
    }

    fun writeToDir(response: Response<ResponseBody?>, fileName: String){
        val file = getFile(fileName)
        val fileOutputStream = FileOutputStream(file)
        IOUtils.write(response.body()?.bytes(), fileOutputStream)
        if(isFilePresent(fileName)){
            openFileExternally(file)
        }else{
            alert("File not found")
        }
    }

    protected fun openFileExternally(file: File){
        try {
            val uri = FileProvider.getUriForFile(this, "com.demo.scopedstorage.fileprovider", file)
            val mime = this.contentResolver.getType(uri)
            // Open file with user selected app
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, mime)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        }catch (e: Exception){
            alert(e.message)
        }
    }
}
