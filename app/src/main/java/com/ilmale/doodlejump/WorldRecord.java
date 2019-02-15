package com.ilmale.doodlejump;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.database.User;

import java.util.ArrayList;
import java.util.List;

/**
 * MOSTRA I 5 PUNTEGGI MIGLIORI AL MONDO PRENDENDO I 5 PUNTEGGI PIù ALTI NEL DATABASE
 *
 * */

public class WorldRecord extends AppCompatActivity {

    private TextView record;
    private FirebaseFirestore fs=FirebaseFirestore.getInstance();
    private CollectionReference use=fs.collection("User");
    private AudioManager audioManager = AudioManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_record);

        record=findViewById(R.id.worldText);

        use.orderBy("punteggio", Query.Direction.DESCENDING).limit(5)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                            int i=0;
                            for(DocumentSnapshot d: list){

                                User user=d.toObject(User.class);
                                record.setText(record.getText()+"\n"+(i+1)+". "+user.getUsername()+" - "+user.getPunteggio());
                                i++;
                            }

                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        audioManager.playBg_audio();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(audioManager.isCanStopBgAudio()){
            audioManager.pauseBg_audio();
        }
        audioManager.setCanStopBgAudio(true);
    }

    public void launchMainActivity(View view) {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

}
