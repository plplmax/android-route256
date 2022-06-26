package dev.ozon.gitlab.plplmax.core_network_api

import com.google.gson.reflect.TypeToken

interface SharedPrefsProvider {
    fun <T> getObject(filename: String, key: String, typeToken: TypeToken<T>): T?
    fun <T> saveObject(filename: String, key: String, obj: T, typeToken: TypeToken<T>)
}