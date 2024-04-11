package com.viswa.hobbyapp_160421069.model

import com.google.gson.annotations.SerializedName

data class User(
                @SerializedName("id")
                var id:Int,
                @SerializedName("username")
                var uname:String?,
                @SerializedName("password")
                var pwd:String?,
                @SerializedName("first_name")
                var fname:String?,
                @SerializedName("last_name")
                var lname:String?,
                @SerializedName("photo_url")
                var profUrl:String?)

data class Blog( @SerializedName("id")
                var id:Int,
                @SerializedName("title")
                var title:String?,
                @SerializedName("summary")
                var smr:String?,
                @SerializedName("photo_url")
                var picUrl:String?,
                @SerializedName("contents")
                var cont:String,
                 @SerializedName("full_name")
                 var writer:String?)