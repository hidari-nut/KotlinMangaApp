package com.viswa.hobbyapp_160421069.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.viswa.hobbyapp_160421069.model.User

class UsersViewModel(application: Application):AndroidViewModel(application) {
    private val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    init {
        queue = Volley.newRequestQueue(application)
    }
    fun loginUser(username: String, password: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val url = "http://10.0.2.2/anmp/login.php"

        val stringRequest = object : StringRequest(Method.POST, url,
            { response ->
                try {
                    val gson = Gson()
                    val user = gson.fromJson(response, User::class.java)
                    if (user != null) {
                        onSuccess(user.fname ?: "User")
                    } else {
                        onError("Login failed")
                    }
                } catch (e: Exception) {
                    onError("Wrong Username or Password")
                }
            },
            { error ->
                onError(error.message ?: "Unknown error occurred")
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["username"] = username
                params["password"] = password
                return params
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}