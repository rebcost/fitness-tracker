package com.fcrysthian.myfitnesstraker.model

import androidx.room.TypeConverter
import java.util.Date

object DateConverter{

    @TypeConverter
    @JvmStatic
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong != null) Date(dateLong) else null
    }

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}