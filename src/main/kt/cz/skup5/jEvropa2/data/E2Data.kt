package cz.skup5.jEvropa2.data

import java.net.URI

fun URI.isEmpty(): Boolean {
    return this.path == ""
}

/**
 * Abstract POJO E2 data object.
 * @author Skup5
 */
abstract class E2Data(val id: Int = 0, open val name: String) {

    companion object {
        @JvmStatic
        val EMPTY_URI: URI = URI("")
    }
}
