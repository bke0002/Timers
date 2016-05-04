package edu.auburn.eng.csse.comp3710.bke0002.timers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by CJ on 4/21/2016.
 */
public class StickyTimesTimer {
    private static StickyTimesDbHelper dbHelper;

    public int TimerId;
    public String Name;
    public String Description;
    public long StartTime;
    public long Length;
    public ArrayList<StickyTimesMarker> Markers;

    //DO NOT USE THIS CONSTRUCTOR IN THE APP
    //IT WILL NOT SAVE THINGS TO THE DATABASE
    private StickyTimesTimer(int TimerId, String Name, String Description, long StartTime, long Length)
    {
        this.TimerId = TimerId;
        this.Name = Name;
        this.Description = Description;
        this.StartTime = StartTime;
        this.Length = Length;
        Markers = new ArrayList<StickyTimesMarker>();
    }

    //Use this to get all of the timers from the database
    public static StickyTimesTimer[] GetTimers(Context context)
    {
        if (dbHelper == null)
        {
            dbHelper = new StickyTimesDbHelper(context);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"TimerId", "Name", "Description", "StartTime", "Length"};
        String[] innerProjection = {"MarkerId", "Description", "Offset", "TimerId"};
        String sortType = "StartTime DESC";
        String innerSortType = "Offset DESC";

        Cursor c = db.query("Timer", projection, null, null, null, null, sortType);

        c.moveToFirst();
        ArrayList<StickyTimesTimer> timers = new ArrayList<StickyTimesTimer>();

        try {
            do {
                try {
                    StickyTimesTimer newTimer = new StickyTimesTimer(
                            c.getInt(c.getColumnIndex("TimerId")),
                            c.getString(c.getColumnIndex("Name")),
                            c.getString(c.getColumnIndex("Description")),
                            c.getLong(c.getColumnIndex("StartTime")),
                            c.getLong(c.getColumnIndex("Length")));
                    if (newTimer.Length == 0)
                    {
                        continue;
                    }
                    Cursor innerC = db.query(
                            "Marker",
                            innerProjection,
                            "TimerId LIKE ?",
                            new String[]{String.valueOf(c.getInt(c.getColumnIndex("TimerId")))},
                            null,
                            null,
                            innerSortType
                    );
                    try {
                        innerC.moveToFirst();

                        do {
                            newTimer.Markers.add(new StickyTimesMarker(
                                            innerC.getInt(innerC.getColumnIndex("MarkerId")),
                                            innerC.getString(innerC.getColumnIndex("Description")),
                                            innerC.getInt(innerC.getColumnIndex("Offset")),
                                            innerC.getInt(innerC.getColumnIndex("TimerId")))
                            );
                        } while (innerC.moveToNext());
                    }
                    catch (Exception e)
                    {

                    }


                    timers.add(newTimer);
                    innerC.close();
                }
                catch (Exception e)
                {
                    for (int i = 0; i < 10; i++){
                        System.out.println(e.toString());
                    }
                }
            }
            while (c.moveToNext());
        }
        catch ( Exception e)
        {
            for (int i = 0; i < 10; i++){
                System.out.println(e.toString());
            }
        }
        finally {
            c.close();
            db.close();
        }
        return timers.toArray(new StickyTimesTimer[timers.size()]);
    }

    //Retrieves the markers for the current object
    public StickyTimesMarker[] GetMarkers()
    {
        return Markers.toArray(new StickyTimesMarker[Markers.size()]);
    }

    //Use this method to create a timer.
    //It'll handle saving it to the database.
    //Pass it the current context, a Name, a Description,
    //And a start time (probably the current time using System.currentTimeMillis())
    public StickyTimesTimer(Context context, String Name, String Description, long StartTime)
    {
        if (dbHelper == null)
        {
            dbHelper = new StickyTimesDbHelper(context);
        }
        this.Name = Name;
        this.Description = Description;
        this.StartTime = StartTime;
        this.Length = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", this.Name);
        values.put("Description", this.Description);
        values.put("StartTime", this.StartTime);
        values.put("Length", this.Length);
        long newRowId = db.insert(
                "Timer",
                null,
                values
        );
        this.TimerId = (int)newRowId;
        Markers = new ArrayList<StickyTimesMarker>();
        db.close();
    }


    //Use this method to save changes to a timer.
    //Pass it in the current context, and an existing timer object that you've modified,
    //And it'll save the data.
    public static void SaveTimer(Context context, StickyTimesTimer timer)
    {
        if (dbHelper == null)
        {
            dbHelper = new StickyTimesDbHelper(context);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", timer.Name);
        values.put("Description", timer.Description);
        values.put("StartTime", timer.StartTime);
        values.put("Length", timer.Length);

        if (timer.TimerId != 0)
        {
            String[] selectArg = { String.valueOf(timer.TimerId) };
            int count = db.update(
                    "Timer",
                    values,
                    "TimerId LIKE ?",
                    selectArg
            );
        }
        else
        {
            values.put("TimerId", timer.TimerId);
            long newRowId = db.insert(
                    "Timer",
                    null,
                    values
            );
        }
        db.close();
    }

    //Use this method to set the length of a timer.
    //Pass in the current time with System.System.currentTimeMillis()
    public void SetLength(long currentTime)
    {
        this.Length = currentTime - this.StartTime;
    }

    //Use this method to add a marker
    //Pass in the current context, a description (or an empty string),
    //And the current time with System.currentTimeMillis()
    public long AddMarker(Context context, String Description, long CurrentTime)
    {
        if (dbHelper == null)
        {
            dbHelper = new StickyTimesDbHelper(context);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long Offset = CurrentTime - this.StartTime;

        ContentValues values = new ContentValues();
        values.put("Description", Description);
        values.put("Offset", Offset);
        values.put("TimerId", this.TimerId);
        long newRowId = db.insert(
                "Marker",
                null,
                values
        );
        this.Markers.add(new StickyTimesMarker((int) newRowId, Description, Offset, this.TimerId));
        db.close();

        return Offset; // TODO Colin check this i added it to your original file
    }
}
