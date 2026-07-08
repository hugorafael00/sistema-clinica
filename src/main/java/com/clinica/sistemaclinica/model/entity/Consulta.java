package com.clinica.sistemaclinica.model.entity;


public class Consulta {

    private Integer codigo;
    private String dataHora;
    private String dataHoraVolta;
    private String observacao;


    private String pacienteCpf;
    private String medicoCrm;


    private Paciente paciente;
    private Medico medico;

    public Consulta() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getDataHoraVolta() {
        return dataHoraVolta;
    }

    public void setDataHoraVolta(String dataHoraVolta) {
        this.dataHoraVolta = dataHoraVolta;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getPacienteCpf() {
        return pacienteCpf;
    }

    public void setPacienteCpf(String pacienteCpf) {
        this.pacienteCpf = pacienteCpf;
    }

    public String getMedicoCrm() {
        return medicoCrm;
    }

    public void setMedicoCrm(String medicoCrm) {
        this.medicoCrm = medicoCrm;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}