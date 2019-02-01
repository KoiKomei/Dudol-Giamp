package com.ilmale.doodlejump.network;

import android.util.Log;

import com.ilmale.doodlejump.domain.Player;
import com.ilmale.doodlejump.domain.RemotePlayer;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {

    private static final String LOG_TAG = Client.class.getSimpleName();

    private Player localplayer;
    private RemotePlayer remoteplayer;
    Socket s;
    BufferedReader dis;
    PrintWriter dos;

    private boolean canStart = false;

    public Client(Player p1, RemotePlayer p2){
        localplayer = p1;
        remoteplayer = p2;
    }

    public void start() {

        try {

            // establish the connection with server port 5555
            s = new Socket("95.235.197.88", 5555);

            // obtaining input and out streams
            dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
            dos = new PrintWriter(s.getOutputStream(), true);

            Log.d(LOG_TAG,"Stiamo per partire");

            // exchanging information between client and server
            ReaderThreadUtil read = new ReaderThreadUtil();
            read.start();

            while (!canStart){
                Thread.sleep(100);
            }

            read.interrupt();

            ReaderThread reader = new ReaderThread();
            WriterThread write = new WriterThread();
            reader.start(); write.start();

        } catch(Exception e) {

            e.printStackTrace();

        }
    }



    private class WriterThread extends Thread {

        public void run() {

            while (true) {

                long yourmilliseconds = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
                Date resultdate = new Date(yourmilliseconds);

                String tosend = sdf.format(resultdate);
                dos.println(
                        //tosend + " " +
                        localplayer.getpX() + " " + localplayer.getpY());

                // If client sends exit,close this connection
                // and then break from the while loop
                if(tosend.equals("Exit")) {

                    Log.d(LOG_TAG,"Closing this connection : " + s);
                    try {
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d(LOG_TAG,"Connection closed");
                    break;
                }

                //PICCOLO SLEEP PER QUESTIONI DI EFFICIENZA E PERCHE ANCORA FACCIO SCHIFO CON I WAIT E NOTIFY
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }//WHILE TRUE

            //closing resources
            dos.close();
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private class ReaderThread extends Thread {


        public void run() {

            try {

                while (true) {

                    // printing date or time as requested by client
                    if (dis.ready()) {
                        String received = dis.readLine();
                        Log.d(LOG_TAG,received);
                        remoteplayer.receiveMessage(received);
                    }

                    //PICCOLO SLEEP PER QUESTIONI DI EFFICIENZA E PERCHE ANCORA FACCIO SCHIFO CON I WAIT E NOTIFY
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }//WHILE TRUE
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReaderThreadUtil extends Thread {


        public void run() {

            try {

                while (true) {

                    // printing date or time as requested by client
                    if (dis.ready()) {
                        String received = dis.readLine();
                        Log.d(LOG_TAG,received);
                        if (received == "CANSTART"){
                            canStart = true;
                        }
                    }

                    //PICCOLO SLEEP PER QUESTIONI DI EFFICIENZA E PERCHE ANCORA FACCIO SCHIFO CON I WAIT E NOTIFY
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }//WHILE TRUE
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
