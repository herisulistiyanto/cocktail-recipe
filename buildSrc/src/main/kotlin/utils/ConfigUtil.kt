package utils

import java.io.File
import java.io.FileInputStream
import java.util.*

object ConfigUtil {

    private fun loadPropertiesFile(fileName: String): Properties {
        return Properties().apply {
            val propFile = File(fileName)
            if (propFile.canRead()) {
                load(FileInputStream(propFile))
            }
        }
    }

    fun loadConfigMap(fileName: String, act: (String, String) -> Unit) {
        val config = loadPropertiesFile(fileName)
        config.forEach { key, value ->
            act(key.toString(), value.toString())
        }
    }

}