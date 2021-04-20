package com.example.utfpr_disciplinaschamadas.model;

import java.util.List;

public class TurmaCurso {
    private String id;
    private String nome;
    private List<String> alunos;

    public TurmaCurso() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<String> alunos) {
        this.alunos = alunos;
    }
}
