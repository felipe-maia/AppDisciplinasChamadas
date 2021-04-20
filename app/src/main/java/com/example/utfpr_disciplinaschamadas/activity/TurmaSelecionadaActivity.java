package com.example.utfpr_disciplinaschamadas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.utfpr_disciplinaschamadas.R;
import com.example.utfpr_disciplinaschamadas.adapter.ListaAlunosAdapter;
import com.example.utfpr_disciplinaschamadas.model.Alunos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TurmaSelecionadaActivity extends AppCompatActivity {

    private DatabaseReference refAlunos = FirebaseDatabase.getInstance().getReference().child("Alunos");

    private RecyclerView recyclerAlunos;
    private Button btnNovoAluno;
    private List<Alunos> listaAlunos = new ArrayList<>();
    private ListaAlunosAdapter alunosAdapter;
    private String id_turma;
    private Alunos aluno = new Alunos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turma_selecionada);
        btnNovoAluno = findViewById(R.id.btnNovoAluno);
        recyclerAlunos = findViewById(R.id.recyclerAlunos);
        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNovoAluno();
            }
        });
        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_ID")) {
            id_turma = intent.getStringExtra("EXTRA_ID");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscaAlunos();
    }

    public void buscaAlunos() {
        listaAlunos.clear();
        Query queryAlunos = refAlunos.orderByChild("turmaCurso").equalTo(id_turma);
        queryAlunos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Alunos aluno = post.getValue(Alunos.class);
                    listaAlunos.add(aluno);
                    configAdapterRecycler();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void configAdapterRecycler() {
        //config recycler e adapter turma
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerAlunos.setLayoutManager(layoutManager);
        recyclerAlunos.setHasFixedSize(false);
        alunosAdapter = new ListaAlunosAdapter(listaAlunos);
        recyclerAlunos.setAdapter(alunosAdapter);
    }

    public void dialogNovoAluno() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_aluno);

        final EditText editNomeAluno = dialog.findViewById(R.id.editNomeAluno);
        final EditText editIdade = dialog.findViewById(R.id.editIdade);
        Button btnCriar = dialog.findViewById(R.id.btnCriarAluno);
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNomeAluno = editNomeAluno.getText().toString();
                String textoIdade = editIdade.getText().toString();
                aluno.setIdade(Integer.valueOf(textoIdade));
                aluno.setNome(textoNomeAluno);
                aluno.setTurmaCurso(id_turma);
                refAlunos.push().setValue(aluno);
                aluno = new Alunos();
                listaAlunos.clear();
                dialog.dismiss();
            }
        });

        Button btnCancelar = dialog.findViewById(R.id.btnCancelarAluno);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
