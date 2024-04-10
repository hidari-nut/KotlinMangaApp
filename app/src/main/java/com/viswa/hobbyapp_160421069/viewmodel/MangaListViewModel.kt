package com.viswa.hobbyapp_160421069.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.viswa.hobbyapp_160421069.model.Blog
class MangaListViewModel(application: Application): AndroidViewModel(application) {
    val mangasLD=MutableLiveData<ArrayList<Blog>>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue?=null

    fun refresh(){

        loadingLD.value = true
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/blogs.php"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url, {
                val sType = object :TypeToken<List<Blog>>(){}.type

                val result = Gson().fromJson<List<Blog>>(it, sType)

                mangasLD.value = result as ArrayList<Blog>?
                loadingLD.value = false
                Log.d("show volley", it)
            }, {
                loadingLD.value = false
                Log.d("showvolley", it.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}