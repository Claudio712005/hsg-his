ALTER TABLE hsg.tb_solic_atl
    DROP CONSTRAINT IF EXISTS ck_sit_apr_solic;

ALTER TABLE hsg.tb_solic_atl
    ADD CONSTRAINT ck_sit_apr_solic
    CHECK (sit_apr_solic IN ('P', 'A', 'R', 'C'));

