package hr.algebra.spacexapp.api

import android.content.Context
import android.util.Log
import hr.algebra.spacexapp.model.Item
import hr.algebra.spacexapp.SpacexReceiver
import hr.algebra.spacexapp.framework.*
import hr.algebra.spacexapp.handler.downloadImageAndStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpacexFetcher(private val context: Context) {
    private val spacexApi: SpacexApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        spacexApi = retrofit.create(SpacexApi::class.java)
    }


    fun fetchItems(count: Int) {
        // background
        val request = spacexApi.fetchAllItems()

        request.enqueue(object : Callback<List<SpacexItem>> {
            override fun onResponse(
                call: Call<List<SpacexItem>>,
                response: Response<List<SpacexItem>>
            ) {
                response.body()?.let { populateItems(it, count) }
            }

            override fun onFailure(call: retrofit2.Call<List<SpacexItem>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }
        })
    }

    private fun populateItems(spacexItems: List<SpacexItem>, count: Int) {
        // foreground
        val items = mutableListOf<Item>()

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            spacexItems.take(count).forEach {
                val picturePath = it.image?.let { imageUrl ->
                    downloadImageAndStore(context, imageUrl)
                } ?: ""
                items.add(
                    Item(
                        null,
                        it.lastAisUpdate,
                        it.legacyId,
                        it.model,
                        it.type,
                        it.roles,
                        it.imo,
                        it.mmsi,
                        it.abs,
                        it.`class`,
                        it.massKg,
                        it.massLbs,
                        it.yearBuilt,
                        it.homePort,
                        it.status,
                        it.speedKn,
                        it.courseDeg,
                        it.latitude,
                        it.longitude,
                        it.link,
                        picturePath ?: "",
                        it.name,
                        it.active,
                        it.launches,
                        it.id,
                        false
                    )
                )
            }

            context.sendBroadcast<SpacexReceiver>()
        }
    }
}