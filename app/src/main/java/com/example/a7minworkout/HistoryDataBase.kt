package com.example.a7minworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDataBase:RoomDatabase() {

    abstract fun historyDao():HistoryDao

    companion object{
        @Volatile
        private var INSTANCE: HistoryDataBase? = null

        fun getInstance(context: Context):HistoryDataBase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDataBase::class.java,
                        "history-database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}