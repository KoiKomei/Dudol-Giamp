package com.ilmale.doodlejump.CodicePerduto;

/**
 * AH BENVENUTO
 * QUI NON C'è NULLA DA VEDERE
 * SOLO FRAMMENTI DI CODICE CHE HANNO FATTO IL LORO LAVORO QUANDO SERVIVANO
 * QUESTA CLASSE FA SOSTANZIALMENTE NIENTE
 * SERVE SOLO A MANTENERE DEL CODICE CHE NON VIENE PIù UTILIZZATO
 * IN CASO LO SI RIVOGLIA IMPLEMENTARE
 * OGNI VOLTA è UN PROBLEMA
 * PERCHè MI RISULTA DIFFICILE ABBANDONARE QUESTI FRAMMENTI DI CODICE
 * SONO UN PO' COME DEI FIGLI
 * è UN PO' COME QUANDO DEVI PULIRE CASA O LA TUA CAMERA O LA SOFFITA
 * E TROVI ROBA CON CUI HAI AVUTO UN LEGAME EMOTIVO E NON VUOI SBARAZZARTENE
 * PER ME QUESTI FRAMMENTI SONO LA STESSA COSA
 * FORSE UN GIORNO POTREBBERO TORNARE UTILI
 * OPPURE POTREBBERO RIMANERE PER SEMPRE QUI, IN QUESTA CLASSE JAVA, PROBABILMENTE INSIEME
 * A TUTTO IL PROGETTO, SUL MIO HARD DRIVE OPPURE SUL MIO GITHUB
 * GRAZIE PER TUTTO IL LAVORO
 *                                              -ALEX PARISELLA
 *
 * */


public class CodicePerduto {

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
    /* List<User> users=RegisterActivity.db.ourDao().getUsers();
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

                }*/


    /*
        List<User> users=RegisterActivity.db.ourDao().getUsers();
        List<Possiede> possess=RegisterActivity.db.ourDao().getPossiede();
        List<String> emails=RegisterActivity.db.ourDao().getEmail();


        for(User us:users){
            String name=us.getUsername();
            String password=us.getPassword();
            String email=us.getEmail();
            int money=us.getMoney();
            int punteggio=us.getPunteggio();
            double lat=us.getLat();
            double lon=us.getLongi();


            txt=txt+"\n\n"+"Name: "+name+"\n password: "+password+"\n email: "+email+"\n Soldi: "+money+"\n Punteggio: "+punteggio+"\n Latitudine: "+lat+"\n Longitudine: "+lon+"\n ";
        }
        for(Possiede pos:possess){

            String em=pos.getEmail();
            boolean bob=pos.isBob();
            boolean bluebob=pos.isBluebob();
            boolean junglebob=pos.isJunglebob();
            boolean bunnybob=pos.isBunnybob();

            txt=txt+"\n"+"Email: "+em+"\n Bob: "+bob+"\n BlueBob: "+bluebob+"\n JungleBob: "+junglebob+"\n BunnyBob: "+bunnybob+"\n ";

        }

        for(Iterator<String> it=emails.iterator(); it.hasNext();){
            txt=txt+"\n roba: "+it.next()+"\n size: "+emails.size()+"\n last item: "+emails.get(emails.size()-1);

        }
        */
}
