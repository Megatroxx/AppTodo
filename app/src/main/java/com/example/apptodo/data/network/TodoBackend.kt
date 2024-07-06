package com.example.apptodo.data.network

import com.example.apptodo.data.network.model.GetSingleToDoResponse
import com.example.apptodo.data.network.model.GetToDoListResponse
import com.example.apptodo.data.network.model.UpdateSingleToDoRequest
import com.example.apptodo.data.network.model.UpdateToDoListRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


/**
 * Interface defining API endpoints for interacting with a Todo backend service.
 */

interface TodoBackend {

    @GET("list")
    suspend fun getToDoList(): Response<GetToDoListResponse>

    @PATCH("list")
    suspend fun updateToDoList(@Body request: UpdateToDoListRequest): Response<GetToDoListResponse>

    @POST("list")
    suspend fun addToDoItem(@Body request: UpdateSingleToDoRequest): Response<GetSingleToDoResponse>

    @PUT("list/{id}")
    suspend fun updateToDoItem(
        @Path("id") id: String,
        @Body request: UpdateSingleToDoRequest
    ): Response<GetSingleToDoResponse>

    @DELETE("list/{id}")
    suspend fun deleteItemById(@Path("id") id: String): Response<GetToDoListResponse>

    companion object {
        const val BASE_URL = "https://hive.mrdekk.ru/todo/"
    }
}