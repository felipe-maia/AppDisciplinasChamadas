package com.example.utfpr_disciplinaschamadas.model;

import java.util.Date;
import java.util.List;

public class Chamada {
    private String id;
    private String data;
    private Integer n_aulas;
    private String curso;
    private String disciplina;
    private List<String> listaPresentes;

    public Chamada() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getN_aulas() {
        return n_aulas;
    }

    public void setN_aulas(Integer n_aulas) {
        this.n_aulas = n_aulas;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public List<String> getListaPresentes() {
        return listaPresentes;
    }

    public void setListaPresentes(List<String> listaPresentes) {
        this.listaPresentes = listaPresentes;
    }
}
