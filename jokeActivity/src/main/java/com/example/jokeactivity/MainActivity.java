package com.example.jokeactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jokelibrary.MainClass;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        TextView tv = findViewById(R.id.tv);
        String text = getIntent().getStringExtra("text");
        //tv.setText(MainClass.getJoke());
        tv.setText(text);
    }
}
