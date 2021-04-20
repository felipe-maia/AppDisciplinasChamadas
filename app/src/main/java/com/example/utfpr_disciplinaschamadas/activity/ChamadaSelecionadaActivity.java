package com.example.utfpr_disciplinaschamadas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.utfpr_disciplinaschamadas.R;
import com.example.utfpr_disciplinaschamadas.model.Alunos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChamadaSelecionadaActivity extends AppCompatActivity {

    private DatabaseReference refChamada = FirebaseDatabase.getInstance().getReference().child("Chamadas");
    private DatabaseReference refAlunos = FirebaseDatabase.getInstance().getReference().child("Alunos");

    private String id_chamada, nomeTurma;
    private Button btnSalvar, btnPresença;
    private ArrayList<String> listaAlunos;
    private ArrayList<String> listaPresentes;
    private ArrayAdapter<String> arrayAdapterTurma, arrayAdapterPresentes;
    private Spinner sTurmas;
    private Spinner sPresentes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamada_selecionada);
        sTurmas = findViewById(R.id.sTurma);
        sPresentes = findViewById(R.id.sPresentes);

        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_ID")) {
            id_chamada = intent.getStringExtra("EXTRA_ID");
        }
        if (intent.hasExtra("EXTRA_NOMETURMA")) {
            nomeTurma = intent.getStringExtra("EXTRA_NOMETURMA");
        }

        buscaAlunosTurma();
        buscaPresentes();

        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarListaPresenca();
            }
        });

        btnPresença = findViewById(R.id.btnAdc);
        btnPresença.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adcPresenca();
            }
        });
    }


    public void buscaAlunosTurma() {
        Query queryAlunos = refAlunos.orderByChild("turmaCurso").equalTo(nomeTurma);
        queryAlunos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaAlunos = new ArrayList<>();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Alunos aluno = post.getValue(Alunos.class);
                    listaAlunos.add(aluno.getNome());
                }
                configAdapterTurmas();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void buscaPresentes() {
        DatabaseReference refAlunosPresentes = refChamada.child(id_chamada).child("listaPresentes");
        refAlunosPresentes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaPresentes = new ArrayList<>();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    String aluno = post.getValue().toString();
                    listaPresentes.add(aluno);
                }
                configAdapterPresentes();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void configAdapterTurmas() {
        arrayAdapterTurma = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaAlunos);
        sTurmas.setAdapter(arrayAdapterTurma);
    }

    public void configAdapterPresentes() {
        arrayAdapterPresentes = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaPresentes);
        sPresentes.setAdapter(arrayAdapterPresentes);
    }

    public void adcPresenca() {
        String aluno = sTurmas.getSelectedItem().toString();
        if (listaPresentes.contains(aluno)) {
            Toast.makeText(getApplicationContext(), "Aluno já presente", Toast.LENGTH_SHORT).show();
        } else {
            listaPresentes.add(aluno);
            Toast.makeText(getApplicationContext(), "Aluno adicionado a lista de presença", Toast.LENGTH_SHORT).show();
        }
    }

    public void salvarListaPresenca() {
        refChamada.child(id_chamada).child("listaPresentes").setValue(listaPresentes);
        Toast.makeText(getApplicationContext(), "Lista de presença salva com sucesso", Toast.LENGTH_SHORT).show();
    }
}
