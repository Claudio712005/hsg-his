ALTER TABLE hsg.tb_solic_atl
    ADD COLUMN ds_mot_cancel  VARCHAR(500),
    ADD COLUMN id_cancelador  BIGINT,
    ADD COLUMN tp_cancelador  VARCHAR(10),
    ADD COLUMN dt_cancelamento TIMESTAMP;
