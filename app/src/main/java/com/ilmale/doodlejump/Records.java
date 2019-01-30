package com.ilmale.doodlejump;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Records {

    private Constants constants = Constants.getInstance();
    private MyLocation myLocation = MyLocation.getInstance();

    private static final Records ourInstance = new Records();

    public static Records getInstance() {
        return ourInstance;
    }

    private List<Integer> records;

    public LoginUser loginUser = LoginUser.getInstance();

    public Records() {
        records = new ArrayList<Integer>();
    }

    public List<Integer> getRecords() {
        return records;
    }

    Context context;

    public static OurDatabase db;

    public void setRecords(List<Integer> records) {
        this.records = records;
    }

    public void initializeRecords(Context context) {
        this.context=context;
        final SharedPreferences pref = context.getSharedPreferences("RECORDS_PREF", MODE_PRIVATE);
        int first = pref.getInt("first", 0);
        int second = pref.getInt("second", 0);
        int third = pref.getInt("third", 0);
        int fourth = pref.getInt("fourth", 0);
        int fifth = pref.getInt("fifth", 0);
        records.add(first);
        records.add(second);
        records.add(third);
        records.add(fourth);
        records.add(fifth);
    }

    public void updateRecords() {
        records.add(constants.getPoints());
        Collections.sort(records, Collections.reverseOrder());
        if(records.size()>5){
            records.remove(records.size()-1);
        }
        final SharedPreferences pref = context.getSharedPreferences("RECORDS_PREF", context.MODE_PRIVATE);
        final SharedPreferences.Editor editorPref = pref.edit();
        editorPref.putInt("first", records.get(0));
        editorPref.putInt("second", records.get(1));
        editorPref.putInt("third", records.get(2));
        editorPref.putInt("fourth", records.get(3));
        editorPref.putInt("fifth", records.get(4));
        editorPref.commit();

        if(loginUser.getEmail()!=null) {
            db = Room.databaseBuilder(context, OurDatabase.class,"userdb").allowMainThreadQueries().build();
            db.ourDao().updatePunteggio(records.get(0), loginUser.getEmail());
            db.ourDao().updateLat((double) myLocation.getLatLng().latitude, loginUser.getEmail());
            db.ourDao().updateLong((double) myLocation.getLatLng().longitude, loginUser.getEmail());
        }
    }

}
