package com.example.utfpr_disciplinaschamadas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.utfpr_disciplinaschamadas.R;
import com.example.utfpr_disciplinaschamadas.model.TurmaCurso;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurmasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DatabaseReference refTurma = FirebaseDatabase.getInstance().getReference().child("Turmas");
    private ListView listViewTurma;
    private List<Map<String, Object>> listaTurmas;
    private TurmaCurso tc = new TurmaCurso();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turmas);
        listViewTurma = findViewById(R.id.listTurma);
        listViewTurma.setOnItemClickListener(this);
        listaTurmas = new ArrayList<>();
        buscaTurmas();
    }

    public void buscaTurmas() {
        listaTurmas = new ArrayList<>();
        listaTurmas.clear();
        refTurma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    TurmaCurso tc = postSnapshot.getValue(TurmaCurso.class);
                    tc.setId(postSnapshot.getKey());
                    Map<String, Object> mapa = new HashMap<String, Object>();

                    mapa.put("id", tc.getId());
                    mapa.put("nomeTurma", tc.getNome());

                    listaTurmas.add(mapa);
                }
                configAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void configAdapter() {
        String[] daonde = {"nomeTurma"};
        int[] paraonde = {R.id.textNomeTurma};

        SimpleAdapter adapter = new SimpleAdapter(this, listaTurmas,
                R.layout.lista_turmas, daonde, paraonde);
        listViewTurma.setAdapter(adapter);
    }

    public void dialogoNovaTurma(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_turma);

        final EditText editNomeTurma = dialog.findViewById(R.id.editNomeTurma);
        Button btnCriar = dialog.findViewById(R.id.btnCriar);
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNomeTurma = editNomeTurma.getText().toString();
                tc.setNome(textoNomeTurma);
                refTurma.push().setValue(tc);
                tc = new TurmaCurso();
                listaTurmas = new ArrayList<>();
                listaTurmas.clear();
                dialog.dismiss();
            }
        });

        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        Intent intent = new Intent(this, TurmaSelecionadaActivity.class);
        Map<String, Object> selecao = listaTurmas.get(pos);
        String id = selecao.get("nomeTurma").toString();
        intent.putExtra("EXTRA_ID", id);
        startActivity(intent);
    }
}
