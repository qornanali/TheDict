package id.aliqornan.thedict.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qornanali on 27/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "thedict.db";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + WordSQLHelper.WordEntry.TBL_NAME + " ("
                + WordSQLHelper.WordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WordSQLHelper.WordEntry.COL_NAME_TEXT + " TEXT, "
                + WordSQLHelper.WordEntry.COL_NAME_IS_ENGLISH + " INTEGER(1), "
                + WordSQLHelper.WordEntry.COL_NAME_TRANSLATION + " TEXT " + " )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + WordSQLHelper.WordEntry.TBL_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

}
