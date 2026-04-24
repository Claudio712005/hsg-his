CREATE SEQUENCE hsg.seq_enfer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE hsg.tb_enfer (
    id_enfer        BIGINT      PRIMARY KEY DEFAULT nextval('hsg.seq_enfer'),
    frt_nm_enfer    VARCHAR(100),
    lst_nm_enfer    VARCHAR(150)
);

CREATE SEQUENCE hsg.seq_solic_atl
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE hsg.tb_solic_atl (
    id_solic_atl        BIGINT          PRIMARY KEY DEFAULT nextval('hsg.seq_solic_atl'),

    id_pac              BIGINT          NOT NULL,
    id_enfer            BIGINT,

    frt_nm_solic_atl    VARCHAR(100),
    lst_nm_solic_atl    VARCHAR(150),
    ds_email_atl        VARCHAR(255),
    nr_tel_atl          VARCHAR(20),

    snp_nome_completo   VARCHAR(250),
    snp_email           VARCHAR(255),
    snp_tel             VARCHAR(20),

    ds_motivo           VARCHAR(500),

    sit_apr_solic       CHAR(1)         NOT NULL DEFAULT 'P',

    dt_cad_solic_atl    TIMESTAMP       NOT NULL,
    dt_ult_atu          TIMESTAMP,

    CONSTRAINT fk_solic_pac
        FOREIGN KEY (id_pac)
        REFERENCES hsg.tb_pac (id_pac),

    CONSTRAINT fk_solic_enfer
        FOREIGN KEY (id_enfer)
        REFERENCES hsg.tb_enfer (id_enfer),

    CONSTRAINT ck_sit_apr_solic
        CHECK (sit_apr_solic IN ('P', 'A', 'R'))
);

CREATE INDEX idx_solic_pac    ON hsg.tb_solic_atl (id_pac);
CREATE INDEX idx_solic_status ON hsg.tb_solic_atl (sit_apr_solic);
CREATE INDEX idx_solic_dt     ON hsg.tb_solic_atl (id_pac, dt_cad_solic_atl DESC);
