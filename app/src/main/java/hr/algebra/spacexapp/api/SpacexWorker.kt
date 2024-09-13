package hr.algebra.spacexapp.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SpacexWorker (
    private val context : Context,
    workerParams : WorkerParameters
    ) : Worker(context, workerParams) {

    override fun doWork(): Result {
        SpacexFetcher(context).fetchItems(10)
        return Result.success()

    }

}
