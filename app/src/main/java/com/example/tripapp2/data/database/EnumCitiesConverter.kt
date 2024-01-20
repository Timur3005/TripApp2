package com.example.tripapp2.data.database

import androidx.room.TypeConverter
import com.example.tripapp2.domain.entities.Cities

class EnumCitiesConverter {
    @TypeConverter
    fun fromEnum(city: Cities): String{
        return city.name
    }

    @TypeConverter
    fun toEnum(city: String): Cities{
        return Cities.valueOf(city)
    }
}