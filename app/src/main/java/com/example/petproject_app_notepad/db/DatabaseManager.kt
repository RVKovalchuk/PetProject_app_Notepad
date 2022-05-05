package com.example.petproject_app_notepad.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DatabaseManager(context: Context) {
    private val databaseHelper = DatabaseHelper(context)
    var db: SQLiteDatabase? = null

    fun openDatabase() {
        db = databaseHelper.writableDatabase
    }

    fun closeDatabase() {
        databaseHelper.close()
    }

    fun insertToDatabase(title: String, description: String, uri: String) {
        val values = ContentValues().apply {
            put(TableClass.COLUMN_NAME_TITLE, title)
            put(TableClass.COLUMN_NAME_DESCRIPTION, description)
            put(TableClass.COLUMN_NAME_IMAGE_URI, uri)
        }
        db?.insert(TableClass.TABLE_NAME, null, values)
    }

    fun readFromDatabase(): ArrayList<PostItemClass> {
        val postList = ArrayList<PostItemClass>()
        val cursor = db?.query(
            TableClass.TABLE_NAME, null, null, null,
            null, null, null
        )
        while (cursor?.moveToNext() == true) {
            val postTitle = cursor.getString(cursor.getColumnIndexOrThrow(TableClass.COLUMN_NAME_TITLE))
            val postDesc = cursor.getString(cursor.getColumnIndexOrThrow(TableClass.COLUMN_NAME_DESCRIPTION))
            val postUri = cursor.getString(cursor.getColumnIndexOrThrow(TableClass.COLUMN_NAME_IMAGE_URI))
            val post = PostItemClass(postTitle, postDesc, postUri)
            postList.add(post)
        }
        return postList
    }
}