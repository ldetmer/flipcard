package com.detmerl.flipcard.data

import android.app.Activity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import org.json.JSONArray
import java.io.*
import java.nio.charset.Charset


class JsonReader () {
    fun getData (activity: Activity) : ArrayList<Translation>  {
        val `is`: InputStream = activity.assets.open("translations.json")

        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        var gson = Gson()
        var translations = arrayListOf<Translation>()
        try {
            val size = `is`.available()

            val buffer = ByteArray(size)

            `is`.read(buffer)

            `is`.close()

            val json = String(buffer, Charset.forName("UTF-8"))

            // val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
            val response = gson.toJson(json)
            println("laura $response")
            //val jsonArray: JSONArray = response as JSONArray
            val mapper = jacksonObjectMapper()
            translations = mapper.readValue(json)
           /* translations.translations.forEach {
                println(it.english)
            }*/
           // return translations
           /* var i =0[]
            for ( i = 0; i < jsonArray.length(); i++) {

            }*/
           /* var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }*/
        } finally {
            `is`.close()
        }

return translations
    }
}