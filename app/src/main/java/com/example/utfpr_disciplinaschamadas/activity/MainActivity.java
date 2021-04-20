package com.example.utfpr_disciplinaschamadas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.utfpr_disciplinaschamadas.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goChamada(View v){
        startActivity(new Intent(this, ChamadasActivity.class));
    }
    public void goTurmas(View v){
        startActivity(new Intent(this, TurmasActivity.class));
    }
    public void goDisciplinas(View v){
        startActivity(new Intent(this, DisciplinasActivity.class));
    }

}
