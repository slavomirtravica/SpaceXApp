package hr.algebra.spacexapp.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.algebra.spacexapp.SPACEX_PROVIDER_CONTENT_URI
import hr.algebra.spacexapp.model.Item

fun View.applyAnimation(animationId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, animationId))

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(Intent(this, T::class.java)
        .apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean = true) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key, value)
        .apply()

fun Context.getBooleanPreference(key: String) = PreferenceManager.getDefaultSharedPreferences(this)
    .getBoolean(key, false)

fun callDelayed(delay: Long, work: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(
        work,
        delay
    )
}

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let { network ->
        connectivityManager.getNetworkCapabilities(network)?.let { networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
    return false
}

@SuppressLint("Range")
fun Context.fetchItems(): MutableList<Item> {
    val items = mutableListOf<Item>()

    val cursor = contentResolver.query(
        SPACEX_PROVIDER_CONTENT_URI,
        null,
        null,
        null,
        null
    )

    while (cursor != null && cursor.moveToNext()) {
        items.add(
            Item(
                cursor.getLong(cursor.getColumnIndexOrThrow(Item::_id.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(Item::name.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::legacyId.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::model.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::type.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::roles.name))?.split(",")
                    ?: emptyList(),
                cursor.getInt(cursor.getColumnIndexOrThrow(Item::imo.name)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Item::mmsi.name)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Item::abs.name)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Item::`class`.name)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Item::massKg.name)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Item::massLbs.name)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Item::yearBuilt.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(Item::homePort.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::status.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::speedKn.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::courseDeg.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::latitude.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::longitude.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::link.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::image.name)) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow(Item::name.name)) ?: "",
                active = cursor.getString(cursor.getColumnIndexOrThrow(Item::active.name))
                    ?.toBoolean() ?: false,
                launches = cursor.getString(cursor.getColumnIndexOrThrow(Item::launches.name))
                    ?.split(",") ?: emptyList(),
                id = cursor.getString(cursor.getColumnIndexOrThrow(Item::id.name)),
                read = cursor.getInt(cursor.getColumnIndexOrThrow(Item::read.name)) == 1
            )
        )
    }

    return items
}