package com.example.tripapp2.data.database

import androidx.room.TypeConverter
import com.example.tripapp2.domain.entities.Category

class EnumCategoryListConverter {
    @TypeConverter
    fun fromEnum(list: List<Category>): String{
        return list.joinToString(","){ it.name }
    }

    @TypeConverter
    fun toEnum(list: String): List<Category>{
        return list.split(",").map { Category.valueOf(it) }
    }
}