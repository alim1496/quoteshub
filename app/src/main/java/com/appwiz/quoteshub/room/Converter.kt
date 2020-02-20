package com.appwiz.quoteshub.room

import androidx.room.TypeConverter
import com.appwiz.quoteshub.room.entity.Tag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromJson(json: String): ArrayList<Tag> {
            return Gson().fromJson(json, object: TypeToken<ArrayList<Tag>>(){}.type)
        }

        @TypeConverter
        @JvmStatic
        fun toJson(tags: ArrayList<Tag>): String {
            return Gson().toJson(tags)
        }
    }
}