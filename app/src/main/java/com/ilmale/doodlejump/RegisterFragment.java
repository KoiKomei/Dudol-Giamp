package com.ilmale.doodlejump;


import android.content.Intent;
import android.media.Image;
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

    private Button BnDelete;
    private ImageButton menu;
    private ImageButton BnRegister, BnUpdate, BnLogin, BnLogout, BnInfo;
    private LoginUser loginUser = LoginUser.getInstance();

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);

        menu = view.findViewById(R.id.menu_register);
        BnRegister=view.findViewById(R.id.bn_register);
        BnInfo=view.findViewById(R.id.bn_info);
        BnLogout=view.findViewById(R.id.bn_logout);
        BnLogin=view.findViewById(R.id.bn_login);
        BnDelete=view.findViewById(R.id.bn_delete);
        BnUpdate=view.findViewById(R.id.bn_update);
        BnRegister.setOnClickListener(this);
        BnLogin.setOnClickListener(this);
        BnLogout.setOnClickListener(this);
        BnUpdate.setOnClickListener(this);
        BnDelete.setOnClickListener(this);
        BnInfo.setOnClickListener(this);

        if(loginUser.getEmail()!=null){
            setLogoutUpdateInfoButton(view);
        }else{
            setLoginRegisterButton(view);
        }
        
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bn_register:
                RegisterActivity.fm.beginTransaction().replace(R.id.fragment_container, new AddUserFragment()).addToBackStack(null).commit();
                break;
            case R.id.bn_info:
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
        BnInfo.setVisibility(view.INVISIBLE);

    }
    public void setLogoutUpdateInfoButton(View view){
        BnLogout.setVisibility(view.VISIBLE);
        BnUpdate.setVisibility(view.VISIBLE);
        BnInfo.setVisibility(view.VISIBLE);
        BnRegister.setVisibility(view.INVISIBLE);
        BnLogin.setVisibility(view.INVISIBLE);
    }

}
