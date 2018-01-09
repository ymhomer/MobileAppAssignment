package app.myguitar.com.myguitar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class StatusReport {
    private DBHelper dbHelper;

    public StatusReport(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Status status) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Status.KEY_feeling, status.feeling);
        values.put(Status.KEY_content, status.content);
        values.put(Status.KEY_title, status.title);

        // Inserting Row
        long status_Id = db.insert(Status.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) status_Id;
    }

    public void delete(int status_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Status.TABLE, Status.KEY_ID + "= ?", new String[] { String.valueOf(status_Id) });
        db.close(); // Closing database connection
    }

    public void update(Status status) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Status.KEY_feeling, status.feeling);
        values.put(Status.KEY_content, status.content);
        values.put(Status.KEY_title, status.title);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Status.TABLE, values, Status.KEY_ID + "= ?", new String[] { String.valueOf(status.student_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getStatusList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Status.KEY_ID + "," +
                Status.KEY_title + "," +
                Status.KEY_content + "," +
                Status.KEY_feeling +
                " FROM " + Status.TABLE;

        //Status student = new Status();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("id", cursor.getString(cursor.getColumnIndex(Status.KEY_ID)));
                student.put("title", cursor.getString(cursor.getColumnIndex(Status.KEY_title)));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    public Status getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Status.KEY_ID + "," +
                Status.KEY_title + "," +
                Status.KEY_content + "," +
                Status.KEY_feeling +
                " FROM " + Status.TABLE
                + " WHERE " +
                Status.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Status status = new Status();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                status.student_ID =cursor.getInt(cursor.getColumnIndex(Status.KEY_ID));
                status.title =cursor.getString(cursor.getColumnIndex(Status.KEY_title));
                status.content  =cursor.getString(cursor.getColumnIndex(Status.KEY_content));
                status.feeling =cursor.getString(cursor.getColumnIndex(Status.KEY_feeling));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return status;
    }

}