package com.ilmale.doodlejump;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ilmale.doodlejump.database.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserFragment extends Fragment {

    private EditText userId, userName, userEmail, userPassword;
    private Button bnRegister;

    public AddUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_user, container, false);
        userId=view.findViewById(R.id.txt_user_id);
        userEmail=view.findViewById(R.id.txt_user_email);
        userName=view.findViewById(R.id.txt_user_name);
        userPassword=view.findViewById(R.id.txt_user_password);
        bnRegister=view.findViewById(R.id.bn_register);

        bnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                int UserId=Integer.parseInt(userId.getText().toString());
                String UserName=userName.getText().toString();
                String UserEmail=userEmail.getText().toString();
                String UserPassword=userPassword.getText().toString();

                User user=new User();
                user.setEmail(UserEmail);
                user.setId(UserId);
                user.setPassword(UserPassword);
                user.setUsername(UserName);

                RegisterActivity.db.ourDao().setUser(user);
                Toast.makeText(getActivity(), "user added successfully", Toast.LENGTH_SHORT).show();

                userId.setText("");
                userEmail.setText("");
                userName.setText("");
                userPassword.setText("");
                

            }

        });
        return view;
    }

}
