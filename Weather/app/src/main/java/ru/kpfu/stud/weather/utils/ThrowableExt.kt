package ru.kpfu.stud.weather.utils

import android.content.res.Resources
import retrofit2.HttpException
import ru.kpfu.stud.weather.R
import java.net.UnknownHostException

fun Throwable.getErrorMessage(resources: Resources): String = when {
    this is UnknownHostException -> "no internet"
    this is HttpException -> response()?.errorBody()?.string() ?: ""
    else -> localizedMessage
}
