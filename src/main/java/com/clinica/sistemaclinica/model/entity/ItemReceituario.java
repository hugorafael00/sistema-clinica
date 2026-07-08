package com.clinica.sistemaclinica.model.entity;

public class ItemReceituario {

    private Integer codigo;
    private Integer dosagem;
    private Integer intervaloEntreDoses;
    private String observacao;

    private Integer receituarioCodigo;
    private Integer medicamentoCodigo;

    
    private Medicamento medicamento;

    public ItemReceituario() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getDosagem() {
        return dosagem;
    }

    public void setDosagem(Integer dosagem) {
        this.dosagem = dosagem;
    }

    public Integer getIntervaloEntreDoses() {
        return intervaloEntreDoses;
    }

    public void setIntervaloEntreDoses(Integer intervaloEntreDoses) {
        this.intervaloEntreDoses = intervaloEntreDoses;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getReceituarioCodigo() {
        return receituarioCodigo;
    }

    public void setReceituarioCodigo(Integer receituarioCodigo) {
        this.receituarioCodigo = receituarioCodigo;
    }

    public Integer getMedicamentoCodigo() {
        return medicamentoCodigo;
    }

    public void setMedicamentoCodigo(Integer medicamentoCodigo) {
        this.medicamentoCodigo = medicamentoCodigo;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }
}