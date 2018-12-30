package cz.skup5.jEvropa2.data

import java.net.URI

fun URI.isEmpty(): Boolean {
    return this.path == ""
}

/**
 * Abstract POJO E2 data object.
 *
 * @author Skup5
 */
abstract class E2Data(
        /** The unique id. */
        open val id: Int = ID_NONE,
        /** The item title/label. */
        open val name: String
) {

    fun hasId() = id > ID_NONE

    companion object {
        @JvmStatic
        val EMPTY_URI: URI = URI("")

        const val ID_NONE = -1
    }
}
