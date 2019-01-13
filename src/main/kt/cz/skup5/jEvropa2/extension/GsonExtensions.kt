package cz.skup5.jEvropa2.extension

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import java.io.StringReader

/**
 * Extension functions of [com.google.gson] library objects.
 * @since 2.0
 * @author Skup5
 */

/////////////////////////////// JsonArray ////////////////////////////////////

/**
 *
 */
fun JsonArray.getJsonObject(index: Int): JsonObject = get(index).asJsonObject

/////////////////////////////// JsonObject ////////////////////////////////////

fun JsonObject.getJsonObject(key: String, default: JsonObject? = null): JsonObject? = if (has(key)) get(key).asJsonObject else default

fun JsonObject.getJsonArray(key: String, default: JsonArray? = null): JsonArray? = if (has(key)) get(key).asJsonArray else default

fun JsonObject.getString(key: String, default: String = ""): String = if (has(key)) get(key).asString else default

fun JsonObject.getInt(key: String, default: Int = 0): Int = if (has(key)) get(key).asInt else default

///////////////////////////////////////////////////////////////////////////////

object JsonUtil {
    val jsonParser = JsonParser()

    fun toJsonObject(json: String): JsonObject {
        val reader = JsonReader(StringReader(json))
        reader.isLenient = true
        return jsonParser.parse(reader).asJsonObject
    }
}