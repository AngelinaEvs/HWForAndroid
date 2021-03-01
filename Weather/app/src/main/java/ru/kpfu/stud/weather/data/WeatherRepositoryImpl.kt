package ru.kpfu.stud.weather.data

import android.content.Context
import android.location.Location
import android.widget.Toast
import com.itis.template.SearchActivity
import ru.kpfu.stud.weather.data.api.WeatherApi
import ru.kpfu.stud.weather.data.api.response.Weather
import ru.kpfu.stud.weather.data.api.response.WeatherResponse
import ru.kpfu.stud.weather.data.model.AppDatabase
import ru.kpfu.stud.weather.data.model.dao.WeatherDao
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity
import ru.kpfu.stud.weather.domain.WeatherRepository

class WeatherRepositoryImpl(private val weatherApi: WeatherApi, context: Context) : WeatherRepository {
    private var db: AppDatabase = AppDatabase(context)

    override suspend fun getBase(): WeatherEntity? = db.weatherDao().getBaseCity()

    override suspend fun saveBase(weatherEntity: WeatherEntity) {
        db.weatherDao().deleteBase()
        db.weatherDao().saveBase(weatherEntity)
    }

    override suspend fun getLocation(): Location? {
        return null
    }

    override suspend fun saveAroundLocation(list: List<WeatherEntity>): List<WeatherEntity> {
        db.weatherDao().clearTableForAround()
        db.weatherDao().saveAroundList(list)
        return list
    }

    override suspend fun getAllTable(): List<WeatherEntity> = db.weatherDao().getAllTable()

    override suspend fun getAroundCity(): List<WeatherEntity> = db.weatherDao().getAroundCity()

    override suspend fun deleteAround() = db.weatherDao().clearTableForAround()

    override suspend fun getCityById(id: Int): WeatherEntity = db.weatherDao().getCityById(id)
}
