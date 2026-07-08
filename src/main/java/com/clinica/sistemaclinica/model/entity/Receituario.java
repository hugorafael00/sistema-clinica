package com.clinica.sistemaclinica.model.entity;

public class Receituario {

    private Integer codigo;
    private String observacao;
    private Integer prontuarioCodigo;

    public Receituario() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getProntuarioCodigo() {
        return prontuarioCodigo;
    }

    public void setProntuarioCodigo(Integer prontuarioCodigo) {
        this.prontuarioCodigo = prontuarioCodigo;
    }
}