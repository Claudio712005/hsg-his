-- Fixed Keycloak UUID that matches the admin.hsg user in hsg-realm.json
-- UUID: ad000000-0000-0000-0000-000000000001

INSERT INTO hsg.tb_conta_usu (id_conta_usu, id_kcl_usu, nm_usu)
VALUES (
    nextval('hsg.seq_conta_usu'),
    'ad000000-0000-0000-0000-000000000001',
    'admin.hsg'
);

INSERT INTO hsg.tb_pac (
    id_pac,
    frt_nm_pac,
    lst_nm_pac,
    ds_email,
    nr_cpf_hash,
    nr_cpf_enc,
    dt_nasc_pac,
    nr_tel,
    st_pac,
    dt_cad_pac,
    id_conta_usu
) VALUES (
    nextval('hsg.seq_pac'),
    'Administrador',
    'HSG',
    'admin@hsg.com.br',
    'admin_cpf_hash_000000000000000000000000000000000000000000000000',
    'admin_cpf_enc_placeholder',
    '1980-01-01',
    '11999999999',
    'A',
    NOW(),
    currval('hsg.seq_conta_usu')
);
