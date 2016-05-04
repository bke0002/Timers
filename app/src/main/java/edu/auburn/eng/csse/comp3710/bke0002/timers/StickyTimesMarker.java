package edu.auburn.eng.csse.comp3710.bke0002.timers;

/**
 * Created by CJ on 4/21/2016.
 */
public class StickyTimesMarker {
    public int MarkerId;
    public String Description;
    public long Offset;
    public int TimerId;

    //DO NOT USE THIS IN THE APP
    //Use the AddMarker method in StickyTimesTimer instead
    public StickyTimesMarker(int MarkerId, String Description, long Offset, int TimerId)
    {
        this.MarkerId = MarkerId;
        this.Description = Description;
        this.Offset = Offset;
        this.TimerId = TimerId;
    }
}
