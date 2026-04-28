ALTER TABLE hsg.tb_enfer
    ADD COLUMN IF NOT EXISTS ds_email_enfer       VARCHAR(255),
    ADD COLUMN IF NOT EXISTS nr_tel_enfer         VARCHAR(20),
    ADD COLUMN IF NOT EXISTS nr_coren             VARCHAR(20),
    ADD COLUMN IF NOT EXISTS uf_coren             CHAR(2),
    ADD COLUMN IF NOT EXISTS cat_coren            VARCHAR(3),
    ADD COLUMN IF NOT EXISTS nr_cpf_hash_enfer    VARCHAR(64),
    ADD COLUMN IF NOT EXISTS nr_cpf_enc_enfer     VARCHAR(255),
    ADD COLUMN IF NOT EXISTS dt_nasc_enfer        DATE,
    ADD COLUMN IF NOT EXISTS ds_especialidade_enfer VARCHAR(100),
    ADD COLUMN IF NOT EXISTS ds_setor_enfer       VARCHAR(100),
    ADD COLUMN IF NOT EXISTS st_enfer             CHAR(1)      NOT NULL DEFAULT 'A',
    ADD COLUMN IF NOT EXISTS dt_cad_enfer         TIMESTAMP    NOT NULL DEFAULT NOW(),
    ADD COLUMN IF NOT EXISTS dt_ult_atu_enfer     TIMESTAMP,
    ADD COLUMN IF NOT EXISTS id_conta_usu_enfer   BIGINT;

ALTER TABLE hsg.tb_enfer
    ADD CONSTRAINT uq_enfer_cpf_hash
        UNIQUE (nr_cpf_hash_enfer),
    ADD CONSTRAINT uq_enfer_conta_usu
        UNIQUE (id_conta_usu_enfer),
    ADD CONSTRAINT fk_enfer_conta_usu
        FOREIGN KEY (id_conta_usu_enfer)
        REFERENCES hsg.tb_conta_usu (id_conta_usu);

CREATE SEQUENCE IF NOT EXISTS hsg.seq_medico
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE hsg.tb_medico (
    id_medico               BIGINT          NOT NULL DEFAULT nextval('hsg.seq_medico'),
    frt_nm_medico           VARCHAR(100),
    lst_nm_medico           VARCHAR(150),
    ds_email_medico         VARCHAR(255),
    nr_tel_medico           VARCHAR(20),
    nr_crm                  VARCHAR(20),
    uf_crm                  CHAR(2),
    nr_cpf_hash_medico      VARCHAR(64),
    nr_cpf_enc_medico       VARCHAR(255),
    dt_nasc_medico          DATE,
    ds_especialidade_medico VARCHAR(100),
    st_medico               CHAR(1)         NOT NULL DEFAULT 'A',
    dt_cad_medico           TIMESTAMP       NOT NULL DEFAULT NOW(),
    dt_ult_atu_medico       TIMESTAMP,
    id_conta_usu_medico     BIGINT,
    CONSTRAINT pk_medico            PRIMARY KEY (id_medico),
    CONSTRAINT uq_medico_cpf_hash   UNIQUE (nr_cpf_hash_medico),
    CONSTRAINT uq_medico_conta_usu  UNIQUE (id_conta_usu_medico),
    CONSTRAINT fk_medico_conta_usu  FOREIGN KEY (id_conta_usu_medico)
                                    REFERENCES hsg.tb_conta_usu (id_conta_usu)
);

INSERT INTO hsg.tb_conta_usu (id_conta_usu, id_kcl_usu, nm_usu)
VALUES (
    nextval('hsg.seq_conta_usu'),
    'en000000-0000-0000-0000-000000000001',
    'enf.maria'
);

INSERT INTO hsg.tb_enfer (
    id_enfer,
    frt_nm_enfer, lst_nm_enfer,
    ds_email_enfer, nr_tel_enfer,
    nr_coren, uf_coren, cat_coren,
    nr_cpf_hash_enfer, nr_cpf_enc_enfer,
    dt_nasc_enfer,
    ds_especialidade_enfer, ds_setor_enfer,
    st_enfer, dt_cad_enfer, dt_ult_atu_enfer,
    id_conta_usu_enfer
) VALUES (
    nextval('hsg.seq_enfer'),
    'Maria', 'Santos',
    'maria.santos@hsg.com.br', '11988880022',
    '654321', 'SP', 'ENF',
    'dev_placeholder_cpf_hash_maria_santos_00000000000000000000000000',
    'DEV_PLACEHOLDER_CPF_ENC_MARIA_SANTOS',
    '1990-03-15',
    'UTI Adulto', 'UTI',
    'A', NOW(), NOW(),
    currval('hsg.seq_conta_usu')
);

INSERT INTO hsg.tb_conta_usu (id_conta_usu, id_kcl_usu, nm_usu)
VALUES (
    nextval('hsg.seq_conta_usu'),
    'md000000-0000-0000-0000-000000000001',
    'dr.joao'
);

INSERT INTO hsg.tb_medico (
    id_medico,
    frt_nm_medico, lst_nm_medico,
    ds_email_medico, nr_tel_medico,
    nr_crm, uf_crm,
    nr_cpf_hash_medico, nr_cpf_enc_medico,
    dt_nasc_medico,
    ds_especialidade_medico,
    st_medico, dt_cad_medico, dt_ult_atu_medico,
    id_conta_usu_medico
) VALUES (
    nextval('hsg.seq_medico'),
    'João', 'Silva',
    'joao.silva@hsg.com.br', '11977770033',
    '123456', 'SP',
    'dev_placeholder_cpf_hash_joao_silva_0000000000000000000000000000',
    'DEV_PLACEHOLDER_CPF_ENC_JOAO_SILVA',
    '1982-07-20',
    'Clínica Médica',
    'A', NOW(), NOW(),
    currval('hsg.seq_conta_usu')
);
