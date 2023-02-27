package com.sirui.ruiping.net.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

object GsonUtils {

    private val gsonBuilder: GsonBuilder by lazy { GsonBuilder() }

    private val gson: Gson by lazy { gsonBuilder.create() }

    fun toJson(paramObject: Any?): String = gson.toJson(paramObject)

    fun <T> json2Object(json: String?, clazz: Class<T>?): T? {
        return try {
            gson.fromJson(json, clazz)
        } catch (e: Exception) {
            null
        }
    }

    fun <T> json2List(json: String?, clazz: Class<Array<T>>): List<T> {
        return try {
            gson.fromJson(json, clazz)?.toList()?: emptyList()
        }catch (e:Exception){
            emptyList()
        }
    }
}