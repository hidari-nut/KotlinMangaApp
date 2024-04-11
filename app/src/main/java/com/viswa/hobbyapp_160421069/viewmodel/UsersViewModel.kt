package com.viswa.hobbyapp_160421069.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.viswa.hobbyapp_160421069.model.Blog
import com.viswa.hobbyapp_160421069.model.User
import com.viswa.hobbyapp_160421069.view.LoginFragmentDirections
import org.json.JSONObject

class UsersViewModel(application: Application,):AndroidViewModel(application) {
    private val TAG = "volleyTag"
    private var queue: RequestQueue? = null
    var userLD = MutableLiveData<User>()

    init {
        queue = Volley.newRequestQueue(application)
    }
    fun loginUser(username: String, password: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val url = "http://10.0.2.2/anmp/login.php"

        val stringRequest = object : StringRequest(Method.POST, url,
            { response ->
                try {
                    val gson = Gson()
                    val responseObject = JSONObject(response)
                    val userData = responseObject.getJSONObject("userdata")

                    val user = gson.fromJson(userData.toString(), User::class.java)
                    if (user != null) {
                        userLD.value = user as User
                        Log.d("USERLD Data",userLD.value.toString())
                        onSuccess("Login Successful ${user.uname ?: ""}")
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
    fun registerUser (user: User, onSuccess: (String) -> Unit, onError: (String) -> Unit){

        val url = "http://10.0.2.2/anmp/new_user.php"

        val stringRequest = object : StringRequest(Method.POST, url,
            {
                try {
                    val responseData = Gson().fromJson(it, Map::class.java)
                    if (responseData["result"] == "success") {
                        onSuccess("Registering User Succeed!")
                    } else {
                        onError("Registration failed")
                    }
                } catch (e: Exception) {
                    onError("Error parsing response")
                }
            },
            {
                onError(it.message ?: "Unknown error occurred")
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["uname"] = user.uname ?: ""
                params["pwd"] = user.pwd ?: ""
                params["fname"] = user.fname ?: ""
                params["lname"] = user.lname ?: ""
                params["url"] = user.profUrl ?: ""
                return params
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun updateUser(id: Int, username: String, password: String, fname:String, lname:String, photo_url:String) {
        val url = "http://10.0.2.2/anmp/edit_user.php"

        val stringRequest = object : StringRequest(Method.POST, url,
            {
                try {
                    val responseData = Gson().fromJson(it, Map::class.java)
                    if (responseData["result"] == "success") {
                        loginUser(username, password, { successMessage ->
                        }, { errorMessage ->

                        })

                    }
                } catch (e: Exception) {
                    Log.e("Error", "Error Message: ${e.message}")
                }
            },
            {
                Log.e("UpdateUserError", "Error: ${it.message}")
               })
        {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = id.toString()
                params["uname"] = username
                params["pwd"] = password
                params["fname"] = fname
                params["lname"] = lname
                params["url"] = photo_url
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    fun clear (){
        userLD = MutableLiveData()
        Log.d("Current Data",userLD.value.toString())
    }
}