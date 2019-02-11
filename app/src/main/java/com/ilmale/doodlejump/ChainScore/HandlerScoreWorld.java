package com.ilmale.doodlejump.ChainScore;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.database.User;

import java.util.List;

public class HandlerScoreWorld extends HandlerScore{

    private static final HandlerScoreWorld ourInstance = new HandlerScoreWorld();

    public static HandlerScoreWorld getInstance() {
        return ourInstance;
    }

    public HandlerScoreWorld() {
        use.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        User user = d.toObject(User.class);
                        users.add(user);
                    }
                }
            }
        });
        successor=HandlerScore1000.getInstance();
    }

    @Override
    protected int getAllowable() {
        return 100000;
    }

    @Override
    public String getString() {
        return "Your are the best of the world";
    }

}
