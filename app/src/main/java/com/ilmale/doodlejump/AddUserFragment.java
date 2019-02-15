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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.database.Possiede;
import com.ilmale.doodlejump.database.User;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * A fragment used to register a user on the database
 */
public class AddUserFragment extends Fragment {

    private EditText userName, userEmail, userPassword;
    private ImageButton bnRegister, menu;
    private AudioManager audioManager = AudioManager.getInstance();
    private FirebaseFirestore fs=FirebaseFirestore.getInstance();
    private CollectionReference use=fs.collection("User");
    private CollectionReference possiede=fs.collection("Possiede");

    public AddUserFragment() {
        // Required empty public constructor
    }


    /**
     * FUNZIONE PER VEDERE SE TUTTI I CAMPI SONO PIENI
     * O SE IL NOME è TROPPO LUNGO
     * IN CASO SI RIVELINO VERI MANDANO UN MESSAGGIO
     * PER INFORMARE CHE DEVONO RIEMPIRE/CAMBIARE QUALCHE CAMPO
     * */

    private boolean filledCamps(String email, String username, String password){
        if(email.isEmpty() || username.isEmpty() || password.isEmpty()){
            Toast.makeText(getActivity(), "Ci sono campi vuoti", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(username.length()>12){
            Toast.makeText(getActivity(), "Nome troppo lungo", Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_user, container, false);
        userEmail=view.findViewById(R.id.txt_user_email);
        userName=view.findViewById(R.id.txt_username);
        userPassword=view.findViewById(R.id.txt_user_password);
        bnRegister=view.findViewById(R.id.button);
        menu = view.findViewById(R.id.menu_adduser);

        bnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //boolean un=true;
                String UserName=userName.getText().toString();
                String UserEmail=userEmail.getText().toString();
                String UserPassword=userPassword.getText().toString();
                if(filledCamps(UserEmail,UserName,UserPassword)){
                    /**
                     * FUNZIONE DI FIRESTORE
                     * è UNA NORMALE QUERY CHE RITORNA OGNI UTENTE
                     * CI SCUSIAMO IN ANTICIPO PER IL DOPPIO CODICE, MA A QUANTO
                     * PARE FIRESTORE SI ARRABBIA CON POCO E NON ABBIAMO
                     * POTUTO FARE ALTRIMENTI
                     * */
                    use.get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if(!queryDocumentSnapshots.isEmpty()){
                                        boolean uniq=true;
                                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                        for(DocumentSnapshot d:list){
                                            User user=d.toObject(User.class);
                                            if(user.getEmail().equalsIgnoreCase(userEmail.getText().toString())){
                                                uniq=false;
                                            }
                                        }
                                        if(uniq){
                                            String em=userEmail.getText().toString();
                                            String na=userName.getText().toString();
                                            String pass=userPassword.getText().toString();
                                            User user=new User();
                                            Possiede possess=new Possiede();
                                            user.setEmail(em);
                                            user.setUsername(na);
                                            user.setPassword(pass);
                                            user.setLongi(0);
                                            user.setPunteggio(0);
                                            user.setLat(0);
                                            user.setMoney(0);
                                            possess.setEmail(em);
                                            possess.setBob(true);
                                            possess.setBluebob(false);
                                            possess.setBunnybob(false);
                                            possess.setJunglebob(false);
                                            possiede.add(possess)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {

                                                        }
                                                    });
                                            use.add(user)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Toast.makeText(getActivity(), "user added successfully", Toast.LENGTH_SHORT).show();
                                                            userEmail.setText("");
                                                            userName.setText("");
                                                            userPassword.setText("");
                                                            audioManager.setCanStopBgAudio(false);
                                                            RegisterActivity.fm.beginTransaction().replace(R.id.fragment_container, new LoginFragment()).addToBackStack(null).commit();
                                                        }
                                                    });

                                        }
                                    }
                                    else{
                                        String em=userEmail.getText().toString();
                                        String na=userName.getText().toString();
                                        String pass=userPassword.getText().toString();
                                        User user=new User();
                                        Possiede possess=new Possiede();
                                        user.setEmail(em);
                                        user.setUsername(na);
                                        user.setPassword(pass);
                                        user.setLongi(0);
                                        user.setPunteggio(0);
                                        user.setLat(0);
                                        user.setMoney(0);
                                        possess.setEmail(em);
                                        possess.setBob(true);
                                        possess.setBluebob(false);
                                        possess.setBunnybob(false);
                                        possess.setJunglebob(false);
                                        possiede.add(possess)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {

                                                    }
                                                });
                                        use.add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(getActivity(), "user added successfully", Toast.LENGTH_SHORT).show();
                                                        userEmail.setText("");
                                                        userName.setText("");
                                                        userPassword.setText("");
                                                        RegisterActivity.fm.beginTransaction().replace(R.id.fragment_container, new LoginFragment()).addToBackStack(null).commit();
                                                    }
                                                });
                                    }
                                }
                            });




                }




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
