package com.ilmale.doodlejump;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.ilmale.doodlejump.domain.LoginUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{

    private Button BnRead, BnDelete;
    private ImageButton BnRegister, BnUpdate, BnLogin, BnLogout;
    private LoginUser loginUser = LoginUser.getInstance();

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);

        BnRegister=view.findViewById(R.id.bn_register);
        BnRead=view.findViewById(R.id.bn_read);
        BnLogout=view.findViewById(R.id.bn_logout);
        BnLogin=view.findViewById(R.id.bn_login);
        BnDelete=view.findViewById(R.id.bn_delete);
        BnUpdate=view.findViewById(R.id.bn_update);
        BnRegister.setOnClickListener(this);
        BnLogin.setOnClickListener(this);
        BnLogout.setOnClickListener(this);
        BnUpdate.setOnClickListener(this);
        BnDelete.setOnClickListener(this);
        BnRead.setOnClickListener(this);

        if(loginUser.getEmail()!=null){
            setLogoutUpdateButton(view);
        }else{
            setLoginRegisterButton(view);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bn_register:
                RegisterActivity.fm.beginTransaction().replace(R.id.fragment_container, new AddUserFragment()).addToBackStack(null).commit();
                break;
            case R.id.bn_read:
                RegisterActivity.fm.beginTransaction().replace(R.id.fragment_container, new ReadUserFragment()).addToBackStack(null).commit();
                break;
            case R.id.bn_delete:
                RegisterActivity.fm.beginTransaction().replace(R.id.fragment_container, new DeleteUserFragment()).addToBackStack(null).commit();
                break;
            case R.id.bn_update:
                RegisterActivity.fm.beginTransaction().replace(R.id.fragment_container, new UpdateFragment()).addToBackStack(null).commit();
                break;
            case R.id.bn_login:
                RegisterActivity.fm.beginTransaction().replace(R.id.fragment_container, new LoginFragment()).addToBackStack(null).commit();
                break;
            case R.id.bn_logout:
                loginUser.logout();
                setLoginRegisterButton(v);
                break;

        }
    }

    public void setLoginRegisterButton(View view){
        BnRegister.setVisibility(view.VISIBLE);
        BnLogin.setVisibility(view.VISIBLE);
        BnLogout.setVisibility(view.INVISIBLE);
        BnUpdate.setVisibility(view.INVISIBLE);

    }
    public void setLogoutUpdateButton(View view){
        BnLogout.setVisibility(view.VISIBLE);
        BnUpdate.setVisibility(view.VISIBLE);
        BnRegister.setVisibility(view.INVISIBLE);
        BnLogin.setVisibility(view.INVISIBLE);
    }

}
