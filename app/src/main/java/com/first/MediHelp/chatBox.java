package com.first.MediHelp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// this is chatbox
public class chatBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
    }

    public static class ChatActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat_box);
    //        getSupportActionBar().hide();
        }
    }
}