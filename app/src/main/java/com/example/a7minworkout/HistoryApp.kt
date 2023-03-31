package com.example.a7minworkout

import android.app.Application

class HistoryApp:Application() {
    val db by lazy {
        HistoryDataBase.getInstance(this)
    }
}