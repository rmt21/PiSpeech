package com.rmt.pispeech;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.io.InputStream;

/**
 * Created by Reece on 31/01/2015.
 */
public class speechSender extends Activity {
    String serverName;
    int port = Integer.parseInt("6067");
    String speech;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        speech = getIntent().getExtras().getString("match");
        serverName = getIntent().getExtras().getString("ip");
        sendSpeech();
    }


    public void sendSpeech() {
        Socket client = null;
        try {
            client = new Socket(serverName, port);

        OutputStream outToServer = client.getOutputStream();

        DataOutputStream out = new DataOutputStream(outToServer);

        //send data
            out.writeUTF("speech");
            out.writeUTF(speech);

        InputStream inFromServer = client.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        System.out.println(in.readUTF());
        client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}