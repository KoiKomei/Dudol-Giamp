package com.ilmale.doodlejump;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_read_user, container, false);
        info=view.findViewById(R.id.txt_display_info);

        List<User> users=RegisterActivity.db.ourDao().getUsers();

        String txt="";

        for(User us:users){
            int id=us.getId();
            String name=us.getUsername();
            String password=us.getPassword();
            String email=us.getEmail();

            txt=txt+"\n\n"+"id: "+id+"\n Name: "+name+"\n password: "+password+"\n email"+email;
        }

        info.setText(txt);

        return view;
    }

}
