package com.example.utfpr_disciplinaschamadas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utfpr_disciplinaschamadas.R;
import com.example.utfpr_disciplinaschamadas.model.Disciplinas;

import java.util.List;


public class ListaDisciplinaAdapter extends RecyclerView.Adapter<ListaDisciplinaAdapter.DisciplinasViewHolder> {

    private List<Disciplinas> listaDisciplinas;

    public ListaDisciplinaAdapter(List<Disciplinas> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
    }

    @NonNull
    @Override
    public DisciplinasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_disciplinas, parent, false);
        return new DisciplinasViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull DisciplinasViewHolder holder, int position) {

        Disciplinas disciplinas = listaDisciplinas.get(position);
        String nome, carga, codigo;
        nome = "Nome: " + disciplinas.getNome();
        carga = "Carga Horária: " + disciplinas.getCarga();
        codigo = "Código: " + disciplinas.getCodigo();
        holder.nome.setText(nome);
        holder.codigo.setText(codigo);
        holder.carga.setText(carga);
    }

    @Override
    public int getItemCount() {
        return this.listaDisciplinas.size();
    }


    public class DisciplinasViewHolder extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView codigo;
        private TextView carga;

        public DisciplinasViewHolder(@NonNull final View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNomeDisc);
            codigo = itemView.findViewById(R.id.textCodigo);
            carga = itemView.findViewById(R.id.textCarga);
        }
    }
}
