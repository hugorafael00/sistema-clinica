package com.clinica.sistemaclinica.model.entity;

public class Prontuario {

    private Integer codigo;
    private String descricao;
    private String observacao;
    private Integer consultaCodigo;

    public Prontuario() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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

    public Integer getConsultaCodigo() {
        return consultaCodigo;
    }

    public void setConsultaCodigo(Integer consultaCodigo) {
        this.consultaCodigo = consultaCodigo;
    }
}