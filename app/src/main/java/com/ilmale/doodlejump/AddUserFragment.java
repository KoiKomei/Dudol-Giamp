package com.ilmale.doodlejump;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ilmale.doodlejump.database.Possiede;
import com.ilmale.doodlejump.database.User;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserFragment extends Fragment {

    private EditText userName, userEmail, userPassword;
    private Button bnRegister;

    public AddUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_user, container, false);
        userEmail=view.findViewById(R.id.txt_user_email);
        userName=view.findViewById(R.id.txt_username);
        userPassword=view.findViewById(R.id.txt_user_password);
        bnRegister=view.findViewById(R.id.button);


        bnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean un=true;
                String UserName=userName.getText().toString();
                String UserEmail=userEmail.getText().toString();
                String UserPassword=userPassword.getText().toString();
                List<User> users=RegisterActivity.db.ourDao().getUsers();
                for(User us:users){
                    if(UserEmail.equals(us.getEmail())){
                        un=false;
                        break;
                    }

                }
                if(un) {
                    User user = new User();
                    user.setEmail(UserEmail);
                    user.setPassword(UserPassword);
                    user.setUsername(UserName);
                    user.setMoney(0);
                    user.setPunteggio(0);
                    user.setLat(0);
                    user.setLongi(0);

                    Possiede pos = new Possiede();

                    pos.setEmail(UserEmail);
                    pos.setBob(true);
                    pos.setBluebob(false);
                    pos.setJunglebob(false);
                    pos.setBunnybob(false);

                    RegisterActivity.db.ourDao().setUser(user);
                    RegisterActivity.db.ourDao().setPossiede(pos);
                    Toast.makeText(getActivity(), "user added successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);

                    userEmail.setText("");
                    userName.setText("");
                    userPassword.setText("");
                }
                else{
                    Toast.makeText(getActivity(), "user already exists", Toast.LENGTH_SHORT).show();
                    userEmail.setText("");
                    userName.setText("");
                    userPassword.setText("");

                }


            }

        });
        return view;
    }

}
