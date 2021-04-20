package com.example.utfpr_disciplinaschamadas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.utfpr_disciplinaschamadas.R;
import com.example.utfpr_disciplinaschamadas.adapter.ListaAlunosAdapter;
import com.example.utfpr_disciplinaschamadas.adapter.ListaDisciplinaAdapter;
import com.example.utfpr_disciplinaschamadas.model.Alunos;
import com.example.utfpr_disciplinaschamadas.model.Disciplinas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisciplinasActivity extends AppCompatActivity {

    private DatabaseReference refDisc = FirebaseDatabase.getInstance().getReference().child("Disciplinas");
    private Button btnCriarDisc;
    private RecyclerView recyclerDisc;
    private List<Disciplinas> listDisc = new ArrayList<>();
    private ListaDisciplinaAdapter discAdapter;
    private Disciplinas disc = new Disciplinas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);
        recyclerDisc = findViewById(R.id.recyclerDisc);
        btnCriarDisc = findViewById(R.id.btnNovaDisciplina);
        btnCriarDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNovaDisc();
            }
        });
        buscaDisciplinas();
    }

    public void buscaDisciplinas(){
        listDisc.clear();
        refDisc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Disciplinas disc = post.getValue(Disciplinas.class);
                    listDisc.add(disc);
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
        recyclerDisc.setLayoutManager(layoutManager);
        recyclerDisc.setHasFixedSize(false);
        discAdapter = new ListaDisciplinaAdapter(listDisc);
        recyclerDisc.setAdapter(discAdapter);
    }
    public void dialogNovaDisc() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_disc);

        final EditText editNomeDisc = dialog.findViewById(R.id.editNomeDisc);
        final EditText editCodigo = dialog.findViewById(R.id.editCodigo);
        final EditText editCarga = dialog.findViewById(R.id.editCarga);

        Button btnCriar = dialog.findViewById(R.id.btnCriarDisciplina);
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNomeDisc = editNomeDisc.getText().toString();
                String textoCodigo = editCodigo.getText().toString();
                String textoCarga = editCarga.getText().toString();
                disc.setCarga(Integer.valueOf(textoCarga));
                disc.setNome(textoNomeDisc);
                disc.setCodigo(textoCodigo);
                refDisc.push().setValue(disc);
                disc = new Disciplinas();
                listDisc.clear();
                dialog.dismiss();
            }
        });

        Button btnCancelar = dialog.findViewById(R.id.btnCancelarDisc);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
