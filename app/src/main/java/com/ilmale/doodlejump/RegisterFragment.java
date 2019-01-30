package com.ilmale.doodlejump;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{

    private Button BnRegister, BnRead, BnDelete, BnUpdate, BnLogin;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        BnRegister=view.findViewById(R.id.bn_register);
        BnRegister.setOnClickListener(this);

        BnRead=view.findViewById(R.id.bn_read);
        BnRead.setOnClickListener(this);

        BnLogin=view.findViewById(R.id.bn_login);
        BnLogin.setOnClickListener(this);

        BnDelete=view.findViewById(R.id.bn_delete);
        BnDelete.setOnClickListener(this);

        BnUpdate=view.findViewById(R.id.bn_update);
        BnUpdate.setOnClickListener(this);

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

        }
    }
}
