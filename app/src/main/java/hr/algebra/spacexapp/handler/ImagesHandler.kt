package hr.algebra.spacexapp.handler

import android.content.Context
import android.util.Log
import hr.algebra.spacexapp.factory.createGetHttpUrlConnection
import java.io.File
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths

fun  downloadImageAndStore(context: Context, imageUrl: String): String? {

    val fileName = imageUrl.substringAfterLast("/")

    val file: File = createLocalFile(context, fileName) // target

    try {
        val con: HttpURLConnection = createGetHttpUrlConnection(imageUrl) // source
        Files.copy(con.inputStream, Paths.get(file.toURI()))
        return file.absolutePath
    } catch (e: Exception) {
        Log.e("IMAGES_HANDLER", e.toString(), e)
    }

    return null
}

fun createLocalFile(context: Context, fileName: String): File {
    val dir = context.getExternalFilesDir(null)
    val file = File(dir, fileName)
    if (file.exists()) file.delete()
    return file
}
