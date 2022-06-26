package dev.ozon.gitlab.plplmax.core_network_impl

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ozon.gitlab.plplmax.core_network_api.SharedPrefsProvider
import javax.inject.Inject

class SharedPrefsProviderImpl @Inject constructor(
    private val context: Context,
    private val gson: Gson
) : SharedPrefsProvider {

    override fun <T> getObject(filename: String, key: String, typeToken: TypeToken<T>): T? {
        val json = context.getSharedPreferences(filename, CREATION_MODE)
            .getString(key, null)

        return gson.fromJson(json, typeToken.type)
    }

    override fun <T> saveObject(filename: String, key: String, obj: T, typeToken: TypeToken<T>) {
        context.getSharedPreferences(filename, CREATION_MODE)
            .edit {
                val json = gson.toJson(obj, typeToken.type)

                putString(key, json)
            }
    }

    private companion object {
        const val CREATION_MODE = Context.MODE_PRIVATE
    }
}