package edu.auburn.eng.csse.comp3710.bke0002.timers;

import android.content.Context;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CJ on 4/21/2016.
 *
 * This should never be used in the app.
 */
public class StickyTimesDbHelper extends SQLiteOpenHelper {

    private static String SQL_CREATE_MARKER = "CREATE TABLE \"Marker\" (\n" +
            "\t`MarkerId`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`Description`\tTEXT,\n" +
            "\t`Offset`\tINTEGER NOT NULL,\n" +
            "\t`TimerId`\tINTEGER NOT NULL,\n" +
            "\tFOREIGN KEY(`TimerId`) REFERENCES Timer(TimerId)\n" +
            ")";
    private static String SQL_CREATE_TIMER = "CREATE TABLE `Timer` (\n" +
            "\t`TimerId`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`Name`\tTEXT NOT NULL,\n" +
            "\t`Description`\tTEXT,\n" +
            "\t`StartTime`\tINTEGER NOT NULL,\n" +
            "\t`Length`\tINTEGER NOT NULL\n" +
            ")";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StickyTimes.db";

    public StickyTimesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TIMER);
        db.execSQL(SQL_CREATE_MARKER);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
