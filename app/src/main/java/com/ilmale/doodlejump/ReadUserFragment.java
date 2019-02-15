package com.ilmale.doodlejump;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilmale.doodlejump.database.Possiede;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;


/**
 * FRAGMENT PER LEGGERE TUTTI I DATI DI UN UTENTE LOGGATO
 */
public class ReadUserFragment extends Fragment {

    private TextView info;
    private ImageView bobImageView;
    private ImageButton menu;
    private LoginUser loginUser = LoginUser.getInstance();
    private AudioManager audioManager = AudioManager.getInstance();

    public ReadUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_read_user, container, false);
        info=view.findViewById(R.id.txt_display_info);
        bobImageView = view.findViewById(R.id.bob_imageView);
        menu = view.findViewById(R.id.menu_readuser);
        DecimalFormat f = new DecimalFormat("##.00");
        String txt="";

        txt+="Email: "+loginUser.getEmail()+"\n";
        txt+="Username: "+loginUser.getUsername()+"\n";
        txt+="Password: "+loginUser.getPassword()+"\n";
        txt+="Money: "+loginUser.getMoney()+"\n";
        txt+="Punteggio: "+loginUser.getPunteggio()+"\n";
        String lat = f.format(loginUser.getLat());
        String longi = f.format(loginUser.getLongi());
        txt+="Posizione: "+lat+" - "+longi;
        info.setText(txt);

        if(loginUser.isEquippedBlueBob()){
            bobImageView.setImageResource(R.drawable.blueright);
        }else if(loginUser.isEquippedBunnyBob()){
            bobImageView.setImageResource(R.drawable.bunnyright);
        }else if(loginUser.isEquippedJungleBob()){
            bobImageView.setImageResource(R.drawable.jungleright);
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.setCanStopBgAudio(false);
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}

