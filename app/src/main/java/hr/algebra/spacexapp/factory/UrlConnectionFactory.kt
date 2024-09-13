package hr.algebra.spacexapp.factory

import java.net.HttpURLConnection
import java.net.URL

private const val TIMEOUT = 10000
private const val METHOD_GET = "GET"
private const val USER_AGENT = "User-Agent"
private const val MOZILLA = "Mozilla/5.0"


fun createGetHttpUrlConnection(path: String) : HttpURLConnection {
    val url = URL(path)

    return (url.openConnection() as HttpURLConnection).apply {
        requestMethod = METHOD_GET
        connectTimeout = TIMEOUT
        readTimeout = TIMEOUT
        setRequestProperty(USER_AGENT, MOZILLA)
    }
}