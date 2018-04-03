package id.aliqornan.thedict.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import id.aliqornan.thedict.model.Word;

/**
 * Created by qornanali on 27/03/18.
 */

public class WordSQLHelper {

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    ;

    public WordSQLHelper(Context context) {
        this.context = context;
    }

    public WordSQLHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public List<Word> query(String columns[], String selection, String limit) {
        List<Word> words = new ArrayList<Word>();
        Cursor cursor = database.query(WordEntry.TBL_NAME, columns, selection, null, null, null, WordEntry._ID + " ASC", limit);
        cursor.moveToFirst();
        Word word;
        if (cursor.getCount() > 0) {
            do {
                word = new Word();
                word.setId(cursor.getInt(cursor.getColumnIndexOrThrow(WordEntry._ID)));
                word.setText(cursor.getString(cursor.getColumnIndexOrThrow(WordEntry.COL_NAME_TEXT)));
                word.setEnglish(cursor.getInt(cursor.getColumnIndexOrThrow(WordEntry.COL_NAME_IS_ENGLISH)) == 1 ? true : false);
                word.setTranslation(cursor.getString(cursor.getColumnIndexOrThrow(WordEntry.COL_NAME_TRANSLATION)));

                words.add(word);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return words;
    }

    public int count(String selection) {
        String[] column = {WordEntry._ID};
        Cursor cursor = database.query(WordEntry.TBL_NAME, column, selection, null, null, null, WordEntry._ID + " ASC", null);
        return cursor.getCount();
    }

    public long insert(Word Word) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(WordEntry.COL_NAME_TEXT, Word.getText());
        initialValues.put(WordEntry.COL_NAME_IS_ENGLISH, Word.isEnglish() ? 1 : 0);
        initialValues.put(WordEntry.COL_NAME_TRANSLATION, Word.getTranslation());
        return database.insert(WordEntry.TBL_NAME, null, initialValues);
    }

    public int update(Word Word) {
        ContentValues args = new ContentValues();
        args.put(WordEntry.COL_NAME_TEXT, Word.getText());
        args.put(WordEntry.COL_NAME_IS_ENGLISH, Word.isEnglish() ? 1 : 0);
        args.put(WordEntry.COL_NAME_TRANSLATION, Word.getTranslation());
        return database.update(WordEntry.TBL_NAME, args, WordEntry._ID + "= '" + Word.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(WordEntry.TBL_NAME, WordEntry._ID + " = '" + id + "'", null);
    }

    public static class WordEntry implements BaseColumns {

        public static final String TBL_NAME = "word";
        public static final String COL_NAME_TEXT = "mtext";
        public static final String COL_NAME_TRANSLATION = "translation";
        public static final String COL_NAME_IS_ENGLISH = "is_english";

    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

}
