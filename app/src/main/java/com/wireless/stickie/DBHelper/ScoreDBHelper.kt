package com.wireless.stickie.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLException

class ScoreDBHelper(private val context: Context) {
    private var db: SQLiteDatabase? = null
    private val dbHelper: ScoreDBOpenHelper

    @Throws(SQLException::class)
    fun open() {
        db = try {
            dbHelper.writableDatabase
        } catch (ex: SQLException) {
            dbHelper.readableDatabase
        }
    }

    fun close() {
        db!!.close()
    }

    fun insertScore(score: Int?): Long {
        val newScoreValues = ContentValues()
        newScoreValues.put(KEY_SCORE, score)
        return db!!.insert(TABLE_NAME, null, newScoreValues)
    }

    // Get all score
    fun selectAllScore(): Cursor {
        return db!!.query(TABLE_NAME, arrayOf(KEY_ID, KEY_SCORE), null, null, null, null, null)
    }

    private class ScoreDBOpenHelper(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

        companion object {
            private const val CREATE_TABLE =
                "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_SCORE INTEGER NOT NULL);"
        }
    }

    companion object {
        private const val DATABASE_NAME = "Score.db"
        private const val TABLE_NAME = "Score"
        private const val DATABSE_VERSION = 1
        const val KEY_ID = "id"
        const val KEY_SCORE = "score"
    }

    init {
        dbHelper = ScoreDBOpenHelper(context, DATABASE_NAME, null, DATABSE_VERSION)
    }

}