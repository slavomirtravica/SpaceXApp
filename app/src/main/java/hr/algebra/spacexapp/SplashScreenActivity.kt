package hr.algebra.spacexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.algebra.spacexapp.api.SpacexWorker
import hr.algebra.spacexapp.databinding.ActivitySplashScreenBinding
import hr.algebra.spacexapp.framework.applyAnimation
import hr.algebra.spacexapp.framework.callDelayed
import hr.algebra.spacexapp.framework.getBooleanPreference
import hr.algebra.spacexapp.framework.isOnline
import hr.algebra.spacexapp.framework.startActivity
import com.google.firebase.analytics.FirebaseAnalytics

private const val DELAY = 3000L
const val DATA_IMPORTED = "hr.algebra.spacexapp.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        binding.ivSplash.applyAnimation(R.anim.rotate)
        binding.tvSplash.applyAnimation(R.anim.blink)
    }

    private fun redirect() {
//        setBooleanPreference(DATA_IMPORTED, false) // nema nigdje usage, ali uporno get vraća da je importano
//        println("DATA_IMPORTED: ${getBooleanPreference(DATA_IMPORTED)}")

        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) {
                startActivity<HostActivity>()
            }
        } else {
            if (isOnline()) {

                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(SpacexWorker::class.java)
                    )
                }

            } else {
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) {
                    finish()
                }
            }
        }

    }

}