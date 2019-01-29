package com.ilmale.doodlejump;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilmale.doodlejump.database.Possiede;
import com.ilmale.doodlejump.database.User;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReadUserFragment extends Fragment {

    private TextView info;

    public ReadUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_read_user, container, false);
        info=view.findViewById(R.id.txt_display_info);

        List<User> users=RegisterActivity.db.ourDao().getUsers();
        List<Possiede> possess=RegisterActivity.db.ourDao().getPossiede();
        String txt="";

        for(User us:users){
            String name=us.getUsername();
            String password=us.getPassword();
            String email=us.getEmail();
            int money=us.getMoney();
            int punteggio=us.getPunteggio();
            double lat=us.getLat();
            double lon=us.getLongi();


            txt=txt+"\n\n"+"Name: "+name+"\n password: "+password+"\n email: "+email+"\n Soldi: "+money+"\n Punteggio: "+punteggio+"\n Latitudine: "+lat+"\n Longitudine: "+lon+"\n ";
        }
        for(Possiede pos:possess){

            String em=pos.getEmail();
            boolean bob=pos.isBob();
            boolean bluebob=pos.isBluebob();
            boolean junglebob=pos.isJunglebob();
            boolean bunnybob=pos.isBunnybob();

            txt=txt+"\n"+"Email: "+em+"\n Bob: "+bob+"\n BlueBob: "+bluebob+"\n JungleBob: "+junglebob+"\n BunnyBob: "+bunnybob+"\n ";

        }

        info.setText(txt);

        return view;
    }

}
