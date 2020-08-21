package com.example.milmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.milmail.Gonnect.ResponseSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonLogin(View view) {
        EditText txtUsername = findViewById(R.id.username);
        EditText txtPassword = findViewById(R.id.password);
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        Gonnect.getData("http://spneshaei.com/mil/getEmails.php?username=" + username + "&password=" + password,
                this, new ResponseSuccessListener() {
                    @Override
                    public void responseRecieved(final String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (response.equals("invalid-user")) {
                                    Toast.makeText(MainActivity.this, "Invalid User", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ArrayList<Email> allEmails = new Gson().fromJson(response
                                            , new TypeToken<ArrayList<Email>>(){}.getType());
//                                    Email.setAllEmails(allEmails); fault
                                    Email.getAllEmails().clear();
                                    Email.getAllEmails().addAll(allEmails);
                                    //adapter.notifyDataSetChange   should be called after list change
                                    Intent intent = new Intent(MainActivity.this, emailList.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }, new Gonnect.ResponseFailureListener() {
                    @Override
                    public void responseFailed(IOException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "ERROR IN LOADING :(", Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });
//        Toast.makeText(this, username, Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this, emailList.class);
//        intent.putExtra("username", username);
//        startActivity(intent);
    }


    public void sendButton(View view) {
        Intent intent = new Intent(this, SendEmail.class);
        startActivity(intent);
    }
}