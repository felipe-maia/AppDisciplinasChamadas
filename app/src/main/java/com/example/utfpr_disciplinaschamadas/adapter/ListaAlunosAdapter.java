package com.example.utfpr_disciplinaschamadas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utfpr_disciplinaschamadas.R;
import com.example.utfpr_disciplinaschamadas.model.Alunos;
import com.example.utfpr_disciplinaschamadas.model.TurmaCurso;

import java.util.List;


public class ListaAlunosAdapter extends RecyclerView.Adapter<ListaAlunosAdapter.AlunosViewHolder> {

    private List<Alunos> listaAlunos;

    public ListaAlunosAdapter(List<Alunos> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }

    @NonNull
    @Override
    public AlunosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_alunos, parent, false);

        return new AlunosViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunosViewHolder holder, int position) {

        Alunos aluno = listaAlunos.get(position);
        String idade, nome = "Nome: "+aluno.getNome();
        idade = "Idade: "+ aluno.getIdade();
        holder.nome.setText(nome);
        holder.idade.setText(idade);
    }

    @Override
    public int getItemCount() {
        return this.listaAlunos.size();
    }


    public class AlunosViewHolder extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView idade;

        public AlunosViewHolder(@NonNull final View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNomeAluno);
            idade = itemView.findViewById(R.id.textIdadeAluno);
        }
    }
}
