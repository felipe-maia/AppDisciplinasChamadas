package com.example.utfpr_disciplinaschamadas.model;

public class Alunos {
    private String id;
    private String nome;
    private Integer idade;
    private String turmaCurso;

    public String getTurmaCurso() {
        return turmaCurso;
    }

    public void setTurmaCurso(String turmaCurso) {
        this.turmaCurso = turmaCurso;
    }

    public Alunos() {
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

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}
