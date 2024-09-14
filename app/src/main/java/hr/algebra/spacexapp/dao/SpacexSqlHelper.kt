package hr.algebra.spacexapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.spacexapp.model.Item

private const val DB_NAME = "items.db"
private const val DB_VERSION = 1
private const val TABLE_NAME = "items"
private val CREATE_TABLE = "create table $TABLE_NAME( " +
        "${Item::_id.name} integer primary key autoincrement, " +
        "${Item::lastAisUpdate.name} text, " +
        "${Item::legacyId.name} text, " +
        "${Item::model.name} text, " +
        "${Item::type.name} text, " +
        "${Item::roles.name} text, " +
        "${Item::imo.name} integer, " +
        "${Item::mmsi.name} integer, " +
        "${Item::abs.name} integer, " +
        "${Item::`class`.name} integer, " +
        "${Item::massKg.name} integer, " +
        "${Item::massLbs.name} integer, " +
        "${Item::yearBuilt.name} integer, " +
        "${Item::homePort.name} text, " +
        "${Item::status.name} text, " +
        "${Item::speedKn.name} integer, " +
        "${Item::courseDeg.name} text, " +
        "${Item::latitude.name} text, " +
        "${Item::longitude.name} text, " +
        "${Item::link.name} text, " +
        "${Item::image.name} text, " +
        "${Item::name.name} text not null, " +
        "${Item::active.name} text, " +
        "${Item::launches.name} text, " +
        "${Item::id.name} text, " +
        "${Item::read.name} integer not null" +
        ")"
private const val DROP_TABLE = "drop table $TABLE_NAME"

class SpacexSqlHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    Repository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?) =
        writableDatabase.delete(TABLE_NAME, selection, selectionArgs)


    override fun insert(values: ContentValues?) = writableDatabase.insert(
        TABLE_NAME,
        null,
        values
    )

    override fun query(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor = readableDatabase.query(
        TABLE_NAME,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )

    override fun update(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(
        TABLE_NAME,
        values,
        selection,
        selectionArgs
    )
}
