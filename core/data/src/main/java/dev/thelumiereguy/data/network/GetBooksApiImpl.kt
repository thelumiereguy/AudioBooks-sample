package dev.thelumiereguy.data.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.thelumiereguy.data.network.models.GetBookResponse
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject
import kotlinx.serialization.json.Json

class GetBooksApiImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GetBooksApi {

    private val json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    override suspend fun getBooks(): GetBookResponse {
        return json.decodeFromString(
            GetBookResponse.serializer(),
            loadJSONFromAsset(context, "books_response.json")
        )
    }

    private fun loadJSONFromAsset(context: Context, string: String): String {
        var json = ""
        try {
            val `is` = context.assets.open(string)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }
}