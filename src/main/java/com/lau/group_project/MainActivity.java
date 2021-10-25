package com.lau.group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button easy;
    private Button medium;
    private Button hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easy = (Button) findViewById(R.id.level1);
        medium = (Button) findViewById(R.id.level2);
        hard = (Button) findViewById(R.id.level3);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLevel1();

            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLevel2();

            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLevel3();

            }
        });

    }
    public void openLevel1(){
        Intent intent = new Intent(this, Level1.class);
        startActivity(intent);
    }
    public void openLevel2(){
        Intent intent = new Intent(this, Level2.class);
        startActivity(intent);
    }
    public void openLevel3(){
        Intent intent = new Intent(this, Level3.class);
        startActivity(intent);
    }
}