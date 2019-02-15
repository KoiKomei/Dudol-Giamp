package com.ilmale.doodlejump.network;

import android.util.Log;

import com.ilmale.doodlejump.Utility;
import com.ilmale.doodlejump.domain.Player;
import com.ilmale.doodlejump.domain.RemotePlayer;

import java.io.*;
import java.net.*;

//client used to connect to the server for multi-player

public class Client extends Thread{

    private static final String LOG_TAG = Client.class.getSimpleName();

    private Utility utility = Utility.getInstance();

    private Player localplayer;
    private RemotePlayer remoteplayer;
    Socket s;
    BufferedReader dis;
    PrintWriter dos;

    //WriterThread write;
    ReaderThread reader;

    public boolean isGameOver;
    boolean shutdown = false; //variabile di controllo del thread writer sul thread reader

    private boolean canStart = false;

    public Client(Player p1, RemotePlayer p2){
        isGameOver = false;
        localplayer = p1;
        remoteplayer = p2;
    }

    public void setIsGameOver(boolean value){
        this.isGameOver = value;
    }

    @Override
    public void run() { //public void start(){

        try {

            // establish the connection with server port 5555
            s = new Socket("151.52.152.164", 5555);
            //s = new Socket("192.168.43.213", 5555);

            // obtaining input and out streams
            dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
            dos = new PrintWriter(s.getOutputStream(), true);

            Log.d(LOG_TAG,"Stiamo per startare WriterThread e ReadeThread");


//            ReaderThreadUtil read = new ReaderThreadUtil();
//            read.start();
//
//            while (!canStart){
//                Thread.sleep(100);
//            }
//
//            read.interrupt();

            // exchanging information between client and server
            reader = new ReaderThread();
            //write = new WriterThread();
            reader.start(); //write.start();

            while (!shutdown) {

//                long yourmilliseconds = System.currentTimeMillis();
//                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
//                Date resultdate = new Date(yourmilliseconds);
//
//                String tosend = sdf.format(resultdate);
                String tosend = //dos.println(
                        //tosend + " " +
                        localplayer.getpX() + " " + (localplayer.getpY() - utility.getPoints());//);

                if (isGameOver){
                    tosend = "Exit";
                }

                dos.println(tosend);

                // If client sends exit,close this connection
                // and then break from the while loop
                if(tosend.equals("Exit")) {

                    Log.d(LOG_TAG,"Closing this connection : " + s);
                    try {
                        s.close();
                        Log.d(LOG_TAG,"socket closed");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    closeConnection();
                    Log.d(LOG_TAG,"Connection closed");
                    break;
                }

                //sleep for efficiency
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }//WHILE TRUE

            //dos.println("Exit");

            //closing resources
            dos.close();
            try {
                if (!s.isClosed()) {
                    s.close();
                    Log.d(LOG_TAG,"socket closed");
                }
                dis.close();
                Log.d(LOG_TAG,"Closed dis");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch(Exception e) {

            e.printStackTrace();

        }
    }

    public void closeConnection() {
        try {
            //write.join();
            shutdown = true;
            reader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ReaderThread extends Thread {


        public void run() {

            try {

                while (!shutdown) {

                    // printing date or time as requested by client
                    if (dis.ready()) {
                        String received = dis.readLine();
                        if (received.equals("Exit")) {
                            shutdown = true;
                            Log.d(LOG_TAG,"Now shutdown is:" + shutdown);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d(LOG_TAG,"Received: " + received);
                        remoteplayer.receiveMessage(received);
                    }

                    //PICCOLO SLEEP PER QUESTIONI DI EFFICIENZA E PERCHE ANCORA FACCIO SCHIFO CON I WAIT E NOTIFY
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }//WHILE TRUE

                Log.d(LOG_TAG, "Shutting down readerTread");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*private class WriterThread extends Thread {

        public void run() {

            while (!shutdown) {

//                long yourmilliseconds = System.currentTimeMillis();
//                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
//                Date resultdate = new Date(yourmilliseconds);
//
//                String tosend = sdf.format(resultdate);
                String tosend = //dos.println(
                        //tosend + " " +
                        localplayer.getpX() + " " + localplayer.getpY();//);

                if (isGameOver){
                    tosend = "Exit";
                }

                dos.println(tosend);

                // If client sends exit,close this connection
                // and then break from the while loop
                if(tosend.equals("Exit")) {

                    Log.d(LOG_TAG,"Closing this connection : " + s);
                    try {
                        s.close();
                        Log.d(LOG_TAG,"socket closed");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    closeConnection();
                    Log.d(LOG_TAG,"Connection closed");
                    break;
                }

                //PICCOLO SLEEP PER QUESTIONI DI EFFICIENZA E PERCHE ANCORA FACCIO SCHIFO CON I WAIT E NOTIFY
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }//WHILE TRUE

            //closing resources
            dos.close();
            try {
                if (!s.isClosed()) {
                    s.close();
                    Log.d(LOG_TAG, "socket closed");
                }
                dis.close();
                Log.d(LOG_TAG,"Closed dis");
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
                        Log.d(LOG_TAG, received);
                        if (received == "CANSTART"){
                            canStart = true;
                        }
                        else if (received.equals("Exit")) {
                            shutdown = true;
                            System.out.println("Now shutdown is:" + shutdown);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    //PICCOLO SLEEP PER QUESTIONI DI EFFICIENZA E PERCHE ANCORA FACCIO SCHIFO CON I WAIT E NOTIFY
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Log.d(LOG_TAG, "Shutting down readerTread");

                }//WHILE TRUE
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

}//CLIENT
