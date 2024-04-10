package com.viswa.hobbyapp_160421069.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.viswa.hobbyapp_160421069.model.Blog
import org.json.JSONObject

class MangaDetailViewModel(application: Application):AndroidViewModel(application) {
    private val TAG = "volleyTag"
    private val gson = Gson()
    private var requestQueue: RequestQueue? = null

    val mangaLD = MutableLiveData<Blog>()
    val paragraphLD = MutableLiveData<ArrayList<String>>()

    fun fetch(mangaId: Int) {

        val url = "http://10.0.2.2/anmp/blog_detail.php"

        requestQueue = Volley.newRequestQueue(getApplication())

        val stringRequest =
            object: StringRequest(Request.Method.POST, url,
            {
                val sType = object: TypeToken<Blog>(){}.type
                val response = JSONObject(it)
                val data = response.getJSONObject("data")
                val result = Gson().fromJson<Blog>(data.toString(), sType)
                mangaLD.value = result as Blog
                paragraphLD.value =  splitBlog(result.cont)
            },
            Response.ErrorListener { error ->
                Log.d(TAG, error.message.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["id"] = mangaId.toString()
                return params
            }
        }


        stringRequest.tag = TAG

        requestQueue?.let { queue ->
            queue.add(stringRequest)
        } ?: run {
            Log.e(TAG, "RequestQueue is null")
        }
    }

    fun splitBlog(content: String): ArrayList<String>{
        var pages = arrayListOf<String>()
        var paragraphs = content.split("\\n\\n")
        for (paragraph in paragraphs){
            pages.add(paragraph)
        }
        return pages
    }
}