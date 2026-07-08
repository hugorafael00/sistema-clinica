
CREATE TABLE IF NOT EXISTS paciente (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    endereco VARCHAR(200),
    contato VARCHAR(50),
    plano_saude VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS medico (
    crm VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    especialidade VARCHAR(100),
    contato VARCHAR(50),
    senha VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS medicamento (
    codigo SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    dosagem VARCHAR(100),
    tipo_dosagem VARCHAR(100),
    descricao VARCHAR(255),
    observacao VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS consulta (
    codigo SERIAL PRIMARY KEY,
    data_hora VARCHAR(30) NOT NULL,
    data_hora_volta VARCHAR(30),
    observacao VARCHAR(255),
    paciente_cpf VARCHAR(14) NOT NULL,
    medico_crm VARCHAR(20) NOT NULL,
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_cpf)
        REFERENCES paciente (cpf),
    CONSTRAINT fk_consulta_medico FOREIGN KEY (medico_crm)
        REFERENCES medico (crm)
);


CREATE TABLE IF NOT EXISTS prontuario (
    codigo SERIAL PRIMARY KEY,
    descricao VARCHAR(255),
    observacao VARCHAR(255),
    consulta_codigo INT NOT NULL UNIQUE,
    CONSTRAINT fk_prontuario_consulta FOREIGN KEY (consulta_codigo)
        REFERENCES consulta (codigo)
);


CREATE TABLE IF NOT EXISTS receituario (
    codigo SERIAL PRIMARY KEY,
    observacao VARCHAR(255),
    prontuario_codigo INT NOT NULL UNIQUE,
    CONSTRAINT fk_receituario_prontuario FOREIGN KEY (prontuario_codigo)
        REFERENCES prontuario (codigo)
);


CREATE TABLE IF NOT EXISTS item_receituario (
    codigo SERIAL PRIMARY KEY,
    dosagem INT,
    intervalo_entre_doses INT,
    observacao VARCHAR(255),
    receituario_codigo INT NOT NULL,
    medicamento_codigo INT NOT NULL,
    CONSTRAINT fk_item_receituario_receituario FOREIGN KEY (receituario_codigo)
        REFERENCES receituario (codigo),
    CONSTRAINT fk_item_receituario_medicamento FOREIGN KEY (medicamento_codigo)
        REFERENCES medicamento (codigo)
);