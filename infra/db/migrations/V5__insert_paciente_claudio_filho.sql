INSERT INTO hsg.tb_conta_usu (id_conta_usu, id_kcl_usu, nm_usu)
VALUES (
    nextval('hsg.seq_conta_usu'),
    'cf000000-0000-0000-0000-000000000001',
    'claudio.filho'
);

INSERT INTO hsg.tb_pac (
    id_pac,
    frt_nm_pac,
    lst_nm_pac,
    ds_email,
    nr_cpf_hash,
    nr_cpf_enc,
    nr_rg_enc,
    dt_nasc_pac,
    nr_tel,
    st_pac,
    dt_cad_pac,
    id_conta_usu
) VALUES (
    nextval('hsg.seq_pac'),
    'Cláudio',
    'Filho',
    'clausilvaaraujo11@gmail.com',
    'claudio_filho_dev_cpf_placeholder_0000000000000000000000000000',
    'DEV_PLACEHOLDER_CPF_ENC_CLAUDIO_FILHO',
    'DEV_PLACEHOLDER_RG_ENC_CLAUDIO_FILHO',
    '2005-01-07',
    '11999990011',
    'A',
    NOW(),
    currval('hsg.seq_conta_usu')
);

INSERT INTO hsg.tb_tp_sang (
    id_tp_sang,
    id_pac,
    tp_sang,
    ds_tp_sang,
    st_valid_tp_sang,
    dt_cad_tp_sang
) VALUES (
    nextval('hsg.seq_tp_sang'),
    currval('hsg.seq_pac'),
    'O_POS',
    'Informado pelo paciente no cadastro.',
    '0',
    NOW()
);