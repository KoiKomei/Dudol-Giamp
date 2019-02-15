package com.ilmale.doodlejump;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//a class that saves top 5 scores of the device with the prefs
//and update the best score and the position of the logged user
//if the score is better than the database one

public class Records {

    private Utility utility = Utility.getInstance();
    private MyLocation myLocation = MyLocation.getInstance();

    private static final Records ourInstance = new Records();

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static Records getInstance() {
        return ourInstance;
    }

    private List<Integer> records;
    private List<String> sRecords;

    public LoginUser loginUser = LoginUser.getInstance();
    private FirebaseFirestore fs= FirebaseFirestore.getInstance();
    private CollectionReference use=fs.collection("User");

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

    public void initializeRecords(Context context) {
        this.context = context;
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

        int points = utility.getPoints();
        String name = utility.getName();
        int pos = -1;

        pos = findPosition(points);
        if (pos >= 0) {
            shiftRecords(pos, points, name);
        }

        if (loginUser.getEmail() != null) {
            use.whereEqualTo("email", loginUser.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            User user=d.toObject(User.class);
                            if(utility.getPoints() > loginUser.getPunteggio()) {
                                user.setPunteggio(utility.getPoints());
                                user.setLat(myLocation.getLatLng().latitude);
                                user.setLongi(myLocation.getLatLng().longitude);
                                loginUser.setPunteggio(utility.getPoints());
                                loginUser.setLat(myLocation.getLatLng().latitude);
                                loginUser.setLongi(myLocation.getLatLng().longitude);
                            }
                            int oldValue = user.getMoney();
                            int newValue = oldValue + utility.getPoints() / 10;
                            user.setMoney(newValue);
                            String id=d.getId();
                            use.document(id).set(user);
                            loginUser.setMoney(newValue);
                            loginUser.login();
                        }
                    }
                }
            });
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

    }

    private void shiftRecords(int pos, int points, String name) {
        for (int i = records.size() - 1; i > pos; i--) {
            records.set(i, records.get(i - 1));
            sRecords.set(i, sRecords.get(i - 1));
        }
        records.set(pos, points);
        sRecords.set(pos, name);
    }

    private int findPosition(int points){
        for (int i = 0; i < records.size(); i++) {
            if (points > records.get(i)) {
                return i;
            }
        }
        return -1;
    }

}
