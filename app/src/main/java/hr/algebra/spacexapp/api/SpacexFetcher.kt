package hr.algebra.spacexapp.api

import android.content.Context
import hr.algebra.spacexapp.SpacexReceiver
import hr.algebra.spacexapp.framework.*

class SpacexFetcher (private val context: Context) {
    fun fetchItems(count: Int) {
        // fake work
        Thread.sleep(600)

        context.sendBroadcast<SpacexReceiver>()
    }
}