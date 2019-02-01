package com.ilmale.doodlejump;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilmale.doodlejump.database.Possiede;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;

import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReadUserFragment extends Fragment {

    private TextView info;
    private ImageView bobImageView;
    private LoginUser loginUser = LoginUser.getInstance();

    public ReadUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_read_user, container, false);
        info=view.findViewById(R.id.txt_display_info);
        bobImageView = view.findViewById(R.id.bob_imageView);
        String txt="";
        /*
        List<User> users=RegisterActivity.db.ourDao().getUsers();
        List<Possiede> possess=RegisterActivity.db.ourDao().getPossiede();
        List<String> emails=RegisterActivity.db.ourDao().getEmail();


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

        for(Iterator<String> it=emails.iterator(); it.hasNext();){
            txt=txt+"\n roba: "+it.next()+"\n size: "+emails.size()+"\n last item: "+emails.get(emails.size()-1);

        }
        */
        txt+="Email: "+loginUser.getEmail()+"\n";
        txt+="Username: "+loginUser.getUsername()+"\n";
        txt+="Password: "+loginUser.getPassword()+"\n";
        txt+="Money: "+loginUser.getMoney()+"\n";
        txt+="Punteggio: "+loginUser.getPunteggio()+"\n";
        txt+="Posizione: "+loginUser.getLat()+", "+loginUser.getLongi();
        info.setText(txt);

        if(loginUser.isEquippedBlueBob()){
            bobImageView.setImageResource(R.drawable.blueright);
        }else if(loginUser.isEquippedBunnyBob()){
            bobImageView.setImageResource(R.drawable.bunnyright);
        }else if(loginUser.isEquippedJungleBob()){
            bobImageView.setImageResource(R.drawable.jungleright);
        }


        return view;
    }

}
