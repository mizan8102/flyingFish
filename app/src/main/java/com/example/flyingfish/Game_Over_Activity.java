package com.example.flyingfish;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Game_Over_Activity extends AppCompatActivity {
TextView tv1;
Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__over_);
        tv1=(TextView)findViewById(R.id.scoreid);
        btn=(Button) findViewById(R.id.againplay);

        Intent ii=this.getIntent();
        String m=ii.getStringExtra("sc").toString();

        tv1.setText(m);
        int dh=Integer.parseInt(m);
        SharedPreferences sp;
        sp= getApplicationContext().getSharedPreferences("com.example.flyingfish",Context.MODE_PRIVATE);
        sp.edit().putInt("highscore",dh).apply();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Game_Over_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void exit(View view) {
        new AlertDialog.Builder(Game_Over_Activity.this)
                .setIcon(R.drawable.splash_icon)
                .setTitle("Flying Fish")
                .setMessage("Do you want to exit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Game_Over_Activity.this,"Thank you",Toast.LENGTH_SHORT).show();
                    }

                })
                .create()
                .show();



    }
}