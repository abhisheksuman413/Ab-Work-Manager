package com.fps69.abworkmanager.Api

import com.fps69.abworkmanager.dataclass.FcmMessage
import com.fps69.abworkmanager.dataclass.Notification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiInterface {
    @Headers(
        "Content-Type: application/json",
    )
    @POST("/v1/projects/ab-work-manager/messages:send")
    fun sendNotification(
        @Header("Authorization") authHeader: String,
        @Body message: FcmMessage
    ): Call<Void>

}