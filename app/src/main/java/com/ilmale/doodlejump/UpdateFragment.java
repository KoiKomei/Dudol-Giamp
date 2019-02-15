package com.ilmale.doodlejump;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;

import java.util.List;


/**
 *  FRAGMENT PER IL CAMBIO DI PASSWORD
 *  USA UNA QUERY SU FIRESTORE PER TROVARE IL GIUSTO UTENTE
 */
public class UpdateFragment extends Fragment {

    private EditText userEmail, userPassword, newPass;
    private ImageButton bnUpdate, menu;
    private LoginUser loginUser = LoginUser.getInstance();
    private AudioManager audioManager = AudioManager.getInstance();
    private FirebaseFirestore fs=FirebaseFirestore.getInstance();
    private CollectionReference use=fs.collection("User");

    public UpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_update,container, false);

        userEmail=view.findViewById(R.id.txt_email_update);
        userPassword=view.findViewById(R.id.txt_old_password);
        newPass=view.findViewById(R.id.txt_new_password);
        bnUpdate=view.findViewById(R.id.bn_save);
        menu = view.findViewById(R.id.menu_update);

        bnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email=userEmail.getText().toString();
                String oldp=userPassword.getText().toString();

                use.whereEqualTo("email", email).whereEqualTo("password", oldp)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(!queryDocumentSnapshots.isEmpty()){
                                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                    for(DocumentSnapshot d:list){
                                        String newp=newPass.getText().toString();
                                        User user=d.toObject(User.class);
                                        user.setPassword(newp);
                                        String id=d.getId();
                                        use.document(id).set(user);
                                        userEmail.setText("");
                                        userPassword.setText("");
                                        newPass.setText("");
                                        loginUser.setPassword(newp);
                                        loginUser.login();
                                        Toast.makeText(getActivity(), "Password cambiata", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Email o password errata", Toast.LENGTH_SHORT).show();
                    }
                });


            }



        });

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
