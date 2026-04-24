CREATE SEQUENCE hsg.seq_endereco
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE hsg.tb_endereco (
    id_endereco         BIGINT      PRIMARY KEY DEFAULT nextval('hsg.seq_endereco'),
    id_pac              BIGINT      NOT NULL UNIQUE,
    ds_logradouro       VARCHAR(200),
    nr_endereco         VARCHAR(10),
    ds_complemento      VARCHAR(100),
    ds_bairro           VARCHAR(100),
    ds_cidade           VARCHAR(100),
    sg_estado           VARCHAR(2),
    nr_cep              VARCHAR(8),
    dt_cad_end          TIMESTAMP,
    dt_ult_atu_end      TIMESTAMP,

    CONSTRAINT fk_end_pac
        FOREIGN KEY (id_pac)
        REFERENCES hsg.tb_pac (id_pac)
);

CREATE INDEX idx_end_pac ON hsg.tb_endereco (id_pac);

ALTER TABLE hsg.tb_solic_atl
    ADD COLUMN tp_solic VARCHAR(10) NOT NULL DEFAULT 'CADASTRAL';

ALTER TABLE hsg.tb_solic_atl
    ADD COLUMN ds_logr_prop    VARCHAR(200),
    ADD COLUMN nr_prop         VARCHAR(10),
    ADD COLUMN ds_compl_prop   VARCHAR(100),
    ADD COLUMN ds_bairro_prop  VARCHAR(100),
    ADD COLUMN ds_cidade_prop  VARCHAR(100),
    ADD COLUMN sg_estado_prop  VARCHAR(2),
    ADD COLUMN nr_cep_prop     VARCHAR(8);

ALTER TABLE hsg.tb_solic_atl
    ADD COLUMN snp_logr    VARCHAR(200),
    ADD COLUMN snp_nr      VARCHAR(10),
    ADD COLUMN snp_compl   VARCHAR(100),
    ADD COLUMN snp_bairro  VARCHAR(100),
    ADD COLUMN snp_cidade  VARCHAR(100),
    ADD COLUMN snp_estado  VARCHAR(2),
    ADD COLUMN snp_cep     VARCHAR(8);

ALTER TABLE hsg.tb_solic_atl
    ADD COLUMN vl_peso_prop   DOUBLE PRECISION,
    ADD COLUMN vl_altura_prop DOUBLE PRECISION,
    ADD COLUMN tp_sang_prop   VARCHAR(10);
ALTER TABLE hsg.tb_solic_atl
    ADD COLUMN snp_peso    DOUBLE PRECISION,
    ADD COLUMN snp_altura  DOUBLE PRECISION,
    ADD COLUMN snp_tp_sang VARCHAR(10);

CREATE INDEX idx_solic_tipo ON hsg.tb_solic_atl (tp_solic);
