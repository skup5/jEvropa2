package cz.skup5.jEvropa2

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.util.logging.Logger


/**
 * Static class for sending http request to the server.
 *
 * @author Skup5
 */
object HttpRequest {

    private val LOGGER = Logger.getLogger(HttpRequest.javaClass.name)

    @JvmStatic
    @Throws(IOException::class)
    fun httpGetSite(url: String): Document {
        return Jsoup.connect(url).get()
    }

    @JvmStatic
    @Throws(IOException::class)
    fun httpGetSite(url: String, vararg keysVals: String): Document {
        return Jsoup.connect(url).data(*keysVals).get()
    }

    /**
     * Sends POST request with the given [dataPayload] content.
     * This method is blocking and returns response like a string.
     *
     * @param url POST request url
     * @param dataPayload content of POST request in json format
     * @return response like a string
     * @since 2.0
     */
    @JvmStatic
    @Throws(IOException::class)
    fun httpPostJson(url: String, dataPayload: String, followRedirects: Boolean = true): String {
        val response = Jsoup
                .connect(url)
                .method(Connection.Method.POST)
                .requestBody(dataPayload)
                .header("content-Type", "application/json")
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .followRedirects(false)
                .execute()

        // manual redirecting a request with the same header and payload
        if (followRedirects and response.hasHeader("location")) {
            LOGGER.info("${response.statusCode()} ${response.statusMessage()}, redirect to ${response.header("location")}")
            return httpPostJson(response.header("location"), dataPayload)
        }

        return response.body()
    }
}
