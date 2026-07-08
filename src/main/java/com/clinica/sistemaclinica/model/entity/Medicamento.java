package com.clinica.sistemaclinica.model.entity;

public class Medicamento {

    private Integer codigo;
    private String nome;
    private String dosagem;
    private String tipoDosagem;
    private String descricao;
    private String observacao;

    public Medicamento() {
    }

    public Medicamento(Integer codigo, String nome, String dosagem, String tipoDosagem, String descricao, String observacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.dosagem = dosagem;
        this.tipoDosagem = tipoDosagem;
        this.descricao = descricao;
        this.observacao = observacao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getTipoDosagem() {
        return tipoDosagem;
    }

    public void setTipoDosagem(String tipoDosagem) {
        this.tipoDosagem = tipoDosagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}