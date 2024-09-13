package hr.algebra.spacexapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_URL = "https://api.spacexdata.com/v4/"


interface SpacexApi {
    @GET("ships/{id}")
    fun fetchItem(@Path("id") id: String): Call<SpacexItem>

    @GET("ships")
    fun fetchAllItems(): Call<List<SpacexItem>>
}