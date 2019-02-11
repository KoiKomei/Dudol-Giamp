package com.ilmale.doodlejump;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.database.Possiede;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    EditText userEmail, userPassword;
    Button bnLogin;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private FirebaseFirestore fs=FirebaseFirestore.getInstance();
    private CollectionReference use=fs.collection("User");
    private CollectionReference poss=fs.collection("Possiede");

    private List<User> userList=new ArrayList<>();

    private LoginUser loginUser = LoginUser.getInstance();

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        userEmail=view.findViewById(R.id.txt_email_login);
        userPassword=view.findViewById(R.id.txt_password_login);
        bnLogin=view.findViewById(R.id.bn_login);

        bnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String UserEmail=userEmail.getText().toString();
                final String UserPassword=userPassword.getText().toString();
                use.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                if(!queryDocumentSnapshots.isEmpty()){
                                    boolean login=false;
                                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                    for(DocumentSnapshot d:list){
                                        User user=d.toObject(User.class);
                                        if(UserPassword.equalsIgnoreCase(user.getPassword()) && UserEmail.equalsIgnoreCase(user.getEmail())){
                                            login=true;
                                            loginUser.setEmail(user.getEmail());
                                            loginUser.setUsername(user.getUsername());
                                            loginUser.setMoney(user.getMoney());
                                            loginUser.setPassword(user.getPassword());
                                            loginUser.setPunteggio(user.getPunteggio());
                                            loginUser.setLat(user.getLat());
                                            loginUser.setLongi(user.getLongi());
                                            poss.whereEqualTo("email", loginUser.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    if(!queryDocumentSnapshots.isEmpty()){
                                                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                                        for(DocumentSnapshot d:list){
                                                            Possiede possiede=d.toObject(Possiede.class);
                                                            loginUser.setBlueBob(possiede.isBluebob());
                                                            loginUser.setBunnyBob(possiede.isBunnybob());
                                                            loginUser.setJungleBob(possiede.isJunglebob());
                                                            loginUser.initializeBobEquipped();
                                                            loginUser.login();
                                                            Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                                            startActivity(intent);
                                                            break;
                                                        }
                                                    }
                                                }
                                            });
                                            break;
                                        }
                                    }
                                    if(login==false){
                                        Toast.makeText(getActivity(), "Login not successful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

                //List<User> users=RegisterActivity.db.ourDao().getUsers();

                /*for(User us:users){
                    Log.d(LOG_TAG, "Dentro al for");
                    if(UserEmail.equalsIgnoreCase(us.getEmail()) && UserPassword.equalsIgnoreCase(us.getPassword())){
                        Log.d(LOG_TAG, "Dentro all'if");
                        Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                        login=true;
                        loginUser.setLoginUser(us);
                        loginUser.initializeBobEquipped();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                    else{
                        Log.d(LOG_TAG, "Else");
                    }
                }
                if(login=false){
                    Log.d(LOG_TAG, "Dentro al secondo if");
                    Toast.makeText(getActivity(), "Login not successful", Toast.LENGTH_SHORT).show();
                }*/

            }
        });

        return view;
    }

}
