package com.example.petproject_app_notepad.db

import android.provider.BaseColumns

object TableClass : BaseColumns {
    const val TABLE_NAME = "table_for_the_notepad"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_DESCRIPTION = "description"
    const val COLUMN_NAME_IMAGE_URI = "image_uri"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "NotepadDatabase.db"

    const val CREATION_OF_THE_TABLE = "CREATE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_TITLE TEXT" +
            "$COLUMN_NAME_DESCRIPTION TEXT" +
            "$COLUMN_NAME_IMAGE_URI URI"
    const val DELETION_OF_THE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}