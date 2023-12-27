package com.samra.tayqatech.data.network.service

import com.samra.tayqatech.data.network.model.Result
import retrofit2.Response
import retrofit2.http.GET

interface PeopleApi {
    @GET("TayqaTech/getdata")
    suspend fun getAll(): Response<Result>
}