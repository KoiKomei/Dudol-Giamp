package com.ilmale.doodlejump;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ilmale.doodlejump.database.User;


/**
 *
 * QUESTO FRAGMENT NON è PIù USATO.
 * ERA UTILIZZATO NEL VECCHIO ROOM DATABASE PER TESTARE LA CANCELLAZIONE DI UN ACCOUNT
 * GRAZIE A FIRESTORE è POSSIBILE CANCELLARE GLI ACCOUNT MANUALMENTE DAL DATABASE ONLINE
 * QUINDI QUESTO FRAGMENT NON è STATO PIù USATO Nè AGGIORNATO
 *
 *
 */

public class DeleteUserFragment extends Fragment {

    private EditText txtUser;
    private EditText txtPass;
    private Button deleteButton;
    private AudioManager audioManager = AudioManager.getInstance();

    public DeleteUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_delete_user, container, false);
        txtUser=view.findViewById(R.id.txt_email_delete);
        txtPass=view.findViewById(R.id.txt_password_delete);
        deleteButton=view.findViewById(R.id.delete);

        audioManager.setCanStopBgAudio(true);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String email=txtUser.getText().toString();
                String pass=txtPass.getText().toString();

                //RegisterActivity.db.ourDao().deleteUser(email, pass);
                txtUser.setText("");
                txtPass.setText("");
            }

        });

        return view;
    }

}
