package com.kapibarabanka.alcodiary.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.kapibarabanka.alcodiary.BASE_TAG

private const val DB_NAME = "dbLocal.db"
private const val SCHEMA = 1

const val COL_ID = "id"
const val COL_NAME = "name"
const val COL_RATING = "rating"

const val TYPES_TABLE = "drink_types"
const val COL_MIN_ALCO = "min_alco"
const val COL_MAX_ALCO = "max_alco"

const val DRINKS_TABLE = "drinks"
const val COL_TYPE = "type"
const val COL_COMMENT = "comment"

const val EVENTS_TABLE = "events"
const val COL_DATE = "date"

const val DRINKS_IN_EVENTS_TABLE = "drinks_in_events"
const val COL_EVENT = "event_id"
const val COL_DRINK = "drink_id"
const val COL_AMOUNT = "amount"


class LocalDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null , SCHEMA) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("PRAGMA foreign_keys=ON")
        initTypesTable(db)
        initDrinksTable(db)
        initEventsTable(db)
        initDrinksInEventsTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TYPES_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $DRINKS_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $EVENTS_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $DRINKS_IN_EVENTS_TABLE")
        onCreate(db)
    }

    private fun initTypesTable(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TYPES_TABLE ("+
                "$COL_ID integer primary key autoincrement, "+
                "$COL_NAME text, $COL_MIN_ALCO real, $COL_MAX_ALCO real"+
                ");")

        db?.execSQL("INSERT INTO $TYPES_TABLE VALUES (1, 'Beer', 4.0, 7.0);")
        db?.execSQL("INSERT INTO $TYPES_TABLE VALUES (2, 'Wine', 10.0, 17.0);")
        db?.execSQL("INSERT INTO $TYPES_TABLE VALUES (3, 'Vodka', 37.0, 42.0);")

        Log.i(BASE_TAG, "Table $TYPES_TABLE with data created")
    }

    private fun initDrinksTable(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $DRINKS_TABLE ("+
                "$COL_ID integer primary key autoincrement, "+
                "$COL_NAME text, $COL_TYPE integer, $COL_RATING real, $COL_COMMENT text, "+
                "FOREIGN KEY($COL_TYPE) REFERENCES $TYPES_TABLE($COL_ID)"+
                ");")

        db?.execSQL("INSERT INTO $DRINKS_TABLE VALUES (1, 'White night', 1, 5.0, '');")
        db?.execSQL("INSERT INTO $DRINKS_TABLE VALUES (2, 'First private', 1, 5.0, '');")
        db?.execSQL("INSERT INTO $DRINKS_TABLE VALUES (3, 'Bud', 1, 4.0, '');")
        db?.execSQL("INSERT INTO $DRINKS_TABLE VALUES (4, 'Non-filtered', 1, 5.0, '');")

        db?.execSQL("INSERT INTO $DRINKS_TABLE VALUES (5, 'Bolgrad', 2, 5.0, '');")
        db?.execSQL("INSERT INTO $DRINKS_TABLE VALUES (6, 'Vila Crimea', 2, 4.0, '');")
        db?.execSQL("INSERT INTO $DRINKS_TABLE VALUES (7, 'Mikado', 2, 3.0, '');")

        db?.execSQL("INSERT INTO $DRINKS_TABLE VALUES (8, 'Morosha', 3, 3.0, '');")
        db?.execSQL("INSERT INTO $DRINKS_TABLE VALUES (9, 'Vozduh', 3, 3.0, '');")
        db?.execSQL("INSERT INTO $DRINKS_TABLE VALUES (10, 'Zubrovka', 3, 5.0, '');")

        Log.i(BASE_TAG, "Table $DRINKS_TABLE with data created")
    }

    private fun initEventsTable(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $EVENTS_TABLE ("+
                "$COL_ID integer primary key autoincrement, "+
                "$COL_NAME text, $COL_DATE text, $COL_RATING real"+
                ");")

        db?.execSQL("INSERT INTO $EVENTS_TABLE VALUES (1, 'first', '2020-05-07', 3.0);")
        db?.execSQL("INSERT INTO $EVENTS_TABLE VALUES (2, 'second', '2020-05-09', 4.0);")
        db?.execSQL("INSERT INTO $EVENTS_TABLE VALUES (3, 'third', '2020-05-15', 5.0);")
        db?.execSQL("INSERT INTO $EVENTS_TABLE VALUES (4, 'fourth', '2020-06-02', 2.0);")
        db?.execSQL("INSERT INTO $EVENTS_TABLE VALUES (5, 'fifth', '2020-06-11', 3.0);")

        Log.i(BASE_TAG, "Table $EVENTS_TABLE with data created")
    }

    private fun initDrinksInEventsTable(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $DRINKS_IN_EVENTS_TABLE ("+
                "$COL_ID integer primary key autoincrement, "+
                "$COL_EVENT integer, $COL_DRINK integer, $COL_AMOUNT real, "+
                "FOREIGN KEY($COL_EVENT) REFERENCES $EVENTS_TABLE($COL_ID),"+
                "FOREIGN KEY($COL_DRINK) REFERENCES $DRINKS_TABLE($COL_ID)"+
                ");")

        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 1, 1, 0.3);")
        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 1, 2, 0.5);")
        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 1, 9, 0.3);")

        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 2, 5, 1.8);")
        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 2, 6, 1.0);")

        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 3, 1, 1.0);")
        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 3, 7, 0.5);")
        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 3, 10, 0.2);")

        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 4, 4, 2.0);")

        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 5, 1, 0.5);")
        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 5, 2, 0.5);")
        db?.execSQL("INSERT INTO $DRINKS_IN_EVENTS_TABLE VALUES (null, 5, 8, 0.5);")

        Log.i(BASE_TAG, "Table $DRINKS_IN_EVENTS_TABLE with data created")
    }
}