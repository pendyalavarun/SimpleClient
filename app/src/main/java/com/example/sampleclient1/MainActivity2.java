package com.example.sampleclient1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        new ReadMSG().start();
    }
    public void addnewMsg(String msg){

        Log.d("eRROR","add new  MSG  called: " + msg);
        TextView textView = new TextView(this);
        textView.setText(msg);
        textView.setTextSize(20);
        textView.setTextColor(getResources().getColor(R.color.black));
        textView.setBackgroundResource(R.drawable.textview_border);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // width
                LinearLayout.LayoutParams.WRAP_CONTENT // height
        );
        layoutParams.topMargin = 20;
        layoutParams.leftMargin = 20;
        textView.setPadding(10,10,10,10);
        textView.setLayoutParams(layoutParams);
        // Add the TextView to a layout (LinearLayout in this case)
        LinearLayout layout = findViewById(R.id.linear_layout);
        layout.addView(textView);
    }
    public class ReadMSG extends Thread{
        public void run(){
            while(SocketManager.serverrunning) {
                Log.d("eRROR","Reading messages Strated . ");
                String response = null;
                BufferedReader in=SocketManager.getBufferedReader();
                try {
                    response = in.readLine();
                    Log.d("eRROR","Recieved MSG : " + response);
                } catch (IOException e) {
                    Log.d("eRROR","Not Recieved Message  : " + e.getMessage() );
                    throw new RuntimeException(e);
                }
                String finalResponse = response;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            addnewMsg(finalResponse);

                    }
                });

            }
            finish();
        }
    }

    public void onclicksendmsgbutton(View v) {
        Log.d("eRROR ", "msg send button clicked");
        EditText Clientmsgtext1=(EditText) findViewById(R.id.Clientmsgtext1);
        String msg = Clientmsgtext1.getText().toString();
        new SendMSG(msg).start();
    }

    public class SendMSG extends Thread {
        String msg;
        SendMSG(String msg){
            this.msg=msg;
        }
        public void run(){
            Log.d("eRROR","SEND MSG CALLED WITH : " +msg);
            SocketManager.getPrintStream().println(msg);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addnewMsg(msg);

                }
            });
        }
    }
}