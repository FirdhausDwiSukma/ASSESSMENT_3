package org.d3if4105.noteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if4105.noteapp.dao.NotesDao
import org.d3if4105.noteapp.model.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDb : RoomDatabase() {
    abstract fun myNotesDao():NotesDao

    companion object{
        @Volatile
        var INSTANCE:NotesDb?=null

        fun getDatabaseInstance(context: Context):NotesDb{
            val tempInstnce = INSTANCE
            if (tempInstnce!=null){
                return tempInstnce
            }
            synchronized(this)
            {
                val roomDatabaseInstance = Room.databaseBuilder(context, NotesDb::class.java,"Notes").allowMainThreadQueries().build()
                INSTANCE = roomDatabaseInstance
                return return roomDatabaseInstance
            }
        }
    }
}