package com.bychkovdmytro.mornhousetesttask.screens

import android.app.Application
import com.bychkovdmytro.mornhousetesttask.db.MainDataBase

class MainApp: Application() {
    val database by lazy { MainDataBase.getDataBase(this) }
}