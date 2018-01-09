package app.myguitar.com.myguitar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class myDbAdapter2 {
    myDbHelper myhelper;

    public myDbAdapter2(Context context) {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String title, String content) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.TITLE, title);
        contentValues.put(myDbHelper.CONTENT, content);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.TITLE, myDbHelper.CONTENT, myDbHelper.DATE};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            //int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.));
            String title = cursor.getString(cursor.getColumnIndex(myDbHelper.TITLE));
            String content = cursor.getString(cursor.getColumnIndex(myDbHelper.CONTENT));
            buffer.append(title + "   " + content + " \n");
        }
        return buffer.toString();
    }

    public int delete(String uname) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs = {uname};

        int count = db.delete(myDbHelper.TABLE_NAME, myDbHelper.CONTENT + " = ?", whereArgs);
        return count;
    }

    public int updateName(String oldName, String newName) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.CONTENT, newName);
        String[] whereArgs = {oldName};
        int count = db.update(myDbHelper.TABLE_NAME, contentValues, myDbHelper.CONTENT + " = ?", whereArgs);
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "myGuitarDB";    // Database Name
        private static final String TABLE_NAME = "MYSTATUS";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String TITLE = "title";     // Column I (Primary Key)
        private static final String CONTENT = "content";    //Column II
        private static final String DATE = "date";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + TITLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTENT + " VARCHAR(255) ," + DATE + " VARCHAR(225));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }
    }
}