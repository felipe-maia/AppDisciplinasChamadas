package com.example.utfpr_disciplinaschamadas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.utfpr_disciplinaschamadas.R;
import com.example.utfpr_disciplinaschamadas.model.Chamada;
import com.example.utfpr_disciplinaschamadas.model.Disciplinas;
import com.example.utfpr_disciplinaschamadas.model.TurmaCurso;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChamadasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DatabaseReference refChamadas = FirebaseDatabase.getInstance().getReference().child("Chamadas");
    private DatabaseReference refTurmas = FirebaseDatabase.getInstance().getReference().child("Turmas");
    private DatabaseReference refDisciplinas = FirebaseDatabase.getInstance().getReference().child("Disciplinas");

    private ArrayAdapter<String> arrayAdapterTurma, arrayAdapterDisciplina;
    private ListView listViewChamadas;
    private List<Map<String, Object>> listaChamadas;
    private Chamada chamada = new Chamada();
    private ArrayList<String> listaTurmas, listaDisciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamada);

        carregarTurmas();
        carregarDisciplinas();
        listViewChamadas = findViewById(R.id.listChamadas);
        listViewChamadas.setOnItemClickListener(this);
        buscaChamadas();
    }

    public void carregarDisciplinas() {

        refDisciplinas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDisciplina = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Disciplinas d = postSnapshot.getValue(Disciplinas.class);
                    listaDisciplina.add(d.getNome());
                }
                configAdapterDisciplinas();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void carregarTurmas() {

        refTurmas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaTurmas = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    TurmaCurso tc = postSnapshot.getValue(TurmaCurso.class);
                    listaTurmas.add(tc.getNome());
                }
                configAdapterTurmas();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void buscaChamadas() {
        refChamadas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaChamadas = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Chamada c = postSnapshot.getValue(Chamada.class);
                    c.setId(postSnapshot.getKey());
                    Map<String, Object> mapa = new HashMap<String, Object>();

                    mapa.put("id", c.getId());
                    mapa.put("nomeTurma", c.getCurso());
                    mapa.put("nomeDisc", c.getDisciplina());
                    mapa.put("data", c.getData());
                    mapa.put("n_aulas", c.getN_aulas());

                    listaChamadas.add(mapa);
                }
                configAdapterChamadas();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void configAdapterChamadas() {
        String[] daonde = {"nomeTurma", "nomeDisc", "data", "n_aulas"};
        int[] paraonde = {R.id.textNomeTurma, R.id.textDisciplina, R.id.textData, R.id.textNAulas};

        SimpleAdapter adapter = new SimpleAdapter(this, listaChamadas,
                R.layout.lista_chamadas, daonde, paraonde);
        listViewChamadas.setAdapter(adapter);
    }

    public void configAdapterTurmas() {
        arrayAdapterTurma = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaTurmas);

    }

    public void configAdapterDisciplinas() {
        arrayAdapterDisciplina = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaDisciplina);

    }

    public void dialogoNovaTurma(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chamada);

        final Spinner spinnerTurma = dialog.findViewById(R.id.spinnerTurma);
        final Spinner spinnerDisciplina = dialog.findViewById(R.id.spinnerDisciplina);

        spinnerTurma.setAdapter(arrayAdapterTurma);
        spinnerDisciplina.setAdapter(arrayAdapterDisciplina);

        final EditText editData = dialog.findViewById(R.id.editData);
        final EditText editQtdAulas = dialog.findViewById(R.id.editQtdAulas);


        Button btnCriar = dialog.findViewById(R.id.btnCriarChamada);
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoQtdAula = editQtdAulas.getText().toString();
                String textoData = editData.getText().toString();
                String textoDisciplina = spinnerDisciplina.getSelectedItem().toString();
                String textoTurma = spinnerTurma.getSelectedItem().toString();
                chamada.setData(textoData);
                chamada.setN_aulas(Integer.valueOf(textoQtdAula));
                chamada.setCurso(textoTurma);
                chamada.setDisciplina(textoDisciplina);

                refChamadas.push().setValue(chamada);
                chamada = new Chamada();
                listaChamadas = new ArrayList<>();
                dialog.dismiss();
            }
        });

        Button btnCancelar = dialog.findViewById(R.id.btnCancelarChamada);
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

        Intent intent = new Intent(this, ChamadaSelecionadaActivity.class);
        Map<String, Object> selecao = listaChamadas.get(pos);
        String id = selecao.get("id").toString();
        String nomeTurma = selecao.get("nomeTurma").toString();
        intent.putExtra("EXTRA_ID", id);
        intent.putExtra("EXTRA_NOMETURMA", nomeTurma);
        startActivity(intent);
    }
}
