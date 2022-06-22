package org.d3if4105.noteapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if4105.noteapp.model.About
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/FirdhausDwiSukma/static-api/main/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface AboutApiService {
    @GET("static-api.json")
    suspend fun getAbout(): List<About>
}
object AboutApi {
    val service: AboutApiService by lazy {
        retrofit.create(AboutApiService::class.java)
    }
    fun getAboutUrl(name: String): String {
        return "$BASE_URL$name.jpg"
    }
}
enum class ApiStatus { LOADING, SUCCESS, FAILED }