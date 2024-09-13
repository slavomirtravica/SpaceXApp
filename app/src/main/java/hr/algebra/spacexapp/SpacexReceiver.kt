package hr.algebra.spacexapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.spacexapp.framework.setBooleanPreference
import hr.algebra.spacexapp.framework.startActivity

class SpacexReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

//        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()

    }
}