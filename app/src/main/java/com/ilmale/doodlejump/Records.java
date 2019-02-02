package com.ilmale.doodlejump;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    private List<String> sRecords;

    public LoginUser loginUser = LoginUser.getInstance();

    public Records() {
        records = new ArrayList<Integer>();
        sRecords = new ArrayList<String>();
    }

    public List<Integer> getRecords() {
        return records;
    }

    public List<String> getSRecords() {
        return sRecords;
    }

    Context context;

    public static OurDatabase db;

    public void initializeRecords(Context context) {
        this.context=context;
        final SharedPreferences pref = context.getSharedPreferences("RECORDS_PREF", MODE_PRIVATE);
        int first = pref.getInt("first", 0);
        int second = pref.getInt("second", 0);
        int third = pref.getInt("third", 0);
        int fourth = pref.getInt("fourth", 0);
        int fifth = pref.getInt("fifth", 0);
        String sFirst = pref.getString("sfirst", "");
        String sSecond = pref.getString("ssecond", "");
        String sThird = pref.getString("sthird", "");
        String sFourth = pref.getString("sfourth", "");
        String sFifth = pref.getString("sfifth", "");
        records.add(first);
        records.add(second);
        records.add(third);
        records.add(fourth);
        records.add(fifth);
        sRecords.add(sFirst);
        sRecords.add(sSecond);
        sRecords.add(sThird);
        sRecords.add(sFourth);
        sRecords.add(sFifth);
    }

    public void updateRecords() {
        records.add(constants.getPoints());
        sRecords.add(constants.getName());
        sortRecords();
        if(records.size()>5){
            records.remove(records.size()-1);
            sRecords.remove(sRecords.size()-1);
        }
        final SharedPreferences pref = context.getSharedPreferences("RECORDS_PREF", context.MODE_PRIVATE);
        final SharedPreferences.Editor editorPref = pref.edit();
        editorPref.putInt("first", records.get(0));
        editorPref.putInt("second", records.get(1));
        editorPref.putInt("third", records.get(2));
        editorPref.putInt("fourth", records.get(3));
        editorPref.putInt("fifth", records.get(4));
        editorPref.putString("sfirst", sRecords.get(0));
        editorPref.putString("ssecond", sRecords.get(1));
        editorPref.putString("sthird", sRecords.get(2));
        editorPref.putString("sfourth", sRecords.get(3));
        editorPref.putString("sfifth", sRecords.get(4));
        editorPref.commit();

        if(loginUser.getEmail()!=null) {
            db = Room.databaseBuilder(context, OurDatabase.class,"userdb").allowMainThreadQueries().build();
            if(constants.getPoints()>loginUser.getPunteggio()) {
                db.ourDao().updatePunteggio(constants.getPoints(), loginUser.getEmail());
                db.ourDao().updateLat(myLocation.getLatLng().latitude, loginUser.getEmail());
                db.ourDao().updateLong(myLocation.getLatLng().longitude, loginUser.getEmail());
                loginUser.setPunteggio(constants.getPoints());
                loginUser.setLat(myLocation.getLatLng().latitude);
                loginUser.setLongi(myLocation.getLatLng().longitude);
            }
            int oldValue = loginUser.getMoney();
            int newValue = oldValue + constants.getPoints()/10;
            db.ourDao().updateMoney(loginUser.getEmail(), newValue, oldValue);
            loginUser.setMoney(newValue);
        }
    }

    private void sortRecords() {
        for(int i=0; i<records.size(); i++) {
            for (int j = i+1; j < records.size(); j++) {
                if(records.get(j)>records.get(i)){
                    int temp = records.get(i);
                    String tempS = sRecords.get(i);
                    records.set(i, records.get(j));
                    sRecords.set(i, sRecords.get(j));
                    records.set(j, temp);
                    sRecords.set(j,tempS);
                }
            }
        }
    }

}
