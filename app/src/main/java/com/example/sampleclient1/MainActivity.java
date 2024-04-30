package com.example.sampleclient1;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
public class MainActivity extends AppCompatActivity {
    private static EditText ipEditText, portEditText;
    public static boolean serverrunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipEditText = (EditText) findViewById(R.id.ipEditText);
        portEditText = (EditText) findViewById(R.id.portEditText);
    }

    public void connecttoserver(View v) {
        ConnectTask ct = new ConnectTask();
        Log.d("eRROR", "connecting to server");
        ct.execute();
        while(!serverrunning);
        if(serverrunning){
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        }
    }
    public class ConnectTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.d("eRROR", "Connectcalled");
                String ip = ipEditText.getText().toString();
                Integer port = Integer.valueOf(portEditText.getText().toString());
                Socket socket = new Socket(ip, port);
                SocketManager.setSocket(socket);
                Log.d("eRROR", "connected to server");
                serverrunning=true;
                PrintStream ps = new PrintStream(socket.getOutputStream());
                SocketManager.setPrintStream(ps);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                SocketManager.setBufferedReader(in);
                SocketManager.serverrunning=true;
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Cannot connect to server !!!!! ", Toast.LENGTH_SHORT).show();
                Log.d("eRROR", "ERROR IN " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

    }
}
