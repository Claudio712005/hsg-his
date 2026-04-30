INSERT INTO hsg.tb_conta_usu (id_conta_usu, id_kcl_usu, nm_usu)
VALUES (
    nextval('hsg.seq_conta_usu'),
    'ad000000-0000-0000-0000-000000000001',
    'admin.hsg'
);

INSERT INTO hsg.tb_pac (
    id_pac, frt_nm_pac, lst_nm_pac, ds_email,
    nr_cpf_hash, nr_cpf_enc,
    dt_nasc_pac, nr_tel, st_pac, dt_cad_pac, id_conta_usu
) VALUES (
    nextval('hsg.seq_pac'),
    'Administrador', 'HSG', 'admin@hsg.com.br',
    'admin_cpf_hash_000000000000000000000000000000000000000000000000',
    'admin_cpf_enc_placeholder',
    '1980-01-01', '11999999999', 'A', NOW(),
    currval('hsg.seq_conta_usu')
);

INSERT INTO hsg.tb_conta_usu (id_conta_usu, id_kcl_usu, nm_usu)
VALUES (
    nextval('hsg.seq_conta_usu'),
    'cf000000-0000-0000-0000-000000000001',
    'claudio.filho'
);

INSERT INTO hsg.tb_pac (
    id_pac, frt_nm_pac, lst_nm_pac, ds_email,
    nr_cpf_hash, nr_cpf_enc, nr_rg_enc,
    dt_nasc_pac, nr_tel, st_pac, dt_cad_pac, id_conta_usu
) VALUES (
    nextval('hsg.seq_pac'),
    'Cláudio', 'Filho', 'clausilvaaraujo11@gmail.com',
    'claudio_filho_dev_cpf_placeholder_0000000000000000000000000000',
    'DEV_PLACEHOLDER_CPF_ENC_CLAUDIO_FILHO',
    'DEV_PLACEHOLDER_RG_ENC_CLAUDIO_FILHO',
    '2005-01-07', '11999990011', 'A', NOW(),
    currval('hsg.seq_conta_usu')
);

INSERT INTO hsg.tb_tp_sang (
    id_tp_sang, id_pac, tp_sang, ds_tp_sang, st_valid_tp_sang, dt_cad_tp_sang
) VALUES (
    nextval('hsg.seq_tp_sang'),
    currval('hsg.seq_pac'),
    'O_POS', 'Informado pelo paciente no cadastro.', '0', NOW()
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

INSERT INTO hsg.tb_alrg (
    id_alrg, id_pac, nm_alrg, ds_alrg, tp_alrg, tp_grav_alrg,
    st_alergia, obs_alrg, ds_reacao,
    id_cad_alrg, dt_cad_alrg, dt_ult_atu_alrg
)
SELECT
    nextval('hsg.seq_alrg'),
    p.id_pac,
    'Dipirona',
    'Alergia ao princípio ativo dipirona sódica.',
    'M', 'G',
    'INFORMADA',
    'Paciente relata reação alérgica grave após uso em 2023.',
    'Urticária generalizada, queda de pressão.',
    p.id_pac, NOW(), NOW()
FROM hsg.tb_pac p
JOIN hsg.tb_conta_usu c ON c.id_conta_usu = p.id_conta_usu
WHERE c.nm_usu = 'claudio.filho'
LIMIT 1;

INSERT INTO hsg.tb_alrg_hist (
    id_alrg_hist, id_alrg, id_usr_hist, acao_hist,
    nm_alrg_snap, tp_alrg_snap, tp_grav_snap, st_alrg_snap, dt_acao_hist
)
SELECT
    nextval('hsg.seq_alrg_hist'),
    a.id_alrg, p.id_pac, 'CRIADA',
    a.nm_alrg, a.tp_alrg, a.tp_grav_alrg, a.st_alergia, NOW()
FROM hsg.tb_alrg a
JOIN hsg.tb_pac p ON p.id_pac = a.id_cad_alrg
JOIN hsg.tb_conta_usu c ON c.id_conta_usu = p.id_conta_usu
WHERE c.nm_usu = 'claudio.filho'
  AND a.nm_alrg = 'Dipirona';

INSERT INTO hsg.tb_alrg (
    id_alrg, id_pac, nm_alrg, ds_alrg, tp_alrg, tp_grav_alrg,
    st_alergia, obs_alrg, ds_reacao,
    id_cad_alrg, dt_cad_alrg, dt_ult_atu_alrg
)
SELECT
    nextval('hsg.seq_alrg'),
    p.id_pac,
    'Amendoim',
    'Alergia alimentar a amendoim e derivados.',
    'A', 'A',
    'APROVADA',
    'Confirmada por exame de IgE específica.',
    'Anafilaxia. Usa EpiPen.',
    p.id_pac,
    NOW() - INTERVAL '30 days',
    NOW() - INTERVAL '10 days'
FROM hsg.tb_pac p
JOIN hsg.tb_conta_usu c ON c.id_conta_usu = p.id_conta_usu
WHERE c.nm_usu = 'claudio.filho'
LIMIT 1;

INSERT INTO hsg.tb_alrg_hist (
    id_alrg_hist, id_alrg, id_usr_hist, acao_hist,
    nm_alrg_snap, tp_alrg_snap, tp_grav_snap, st_alrg_snap, dt_acao_hist
)
SELECT
    nextval('hsg.seq_alrg_hist'),
    a.id_alrg, p.id_pac, 'CRIADA',
    a.nm_alrg, a.tp_alrg, a.tp_grav_alrg, 'INFORMADA',
    NOW() - INTERVAL '30 days'
FROM hsg.tb_alrg a
JOIN hsg.tb_pac p ON p.id_pac = a.id_cad_alrg
JOIN hsg.tb_conta_usu c ON c.id_conta_usu = p.id_conta_usu
WHERE c.nm_usu = 'claudio.filho'
  AND a.nm_alrg = 'Amendoim';

INSERT INTO hsg.tb_alrg_hist (
    id_alrg_hist, id_alrg, id_usr_hist, acao_hist,
    nm_alrg_snap, tp_alrg_snap, tp_grav_snap, st_alrg_snap, dt_acao_hist
)
SELECT
    nextval('hsg.seq_alrg_hist'),
    a.id_alrg, p.id_pac, 'APROVADA',
    a.nm_alrg, a.tp_alrg, a.tp_grav_alrg, a.st_alergia,
    NOW() - INTERVAL '10 days'
FROM hsg.tb_alrg a
JOIN hsg.tb_pac p ON p.id_pac = a.id_cad_alrg
JOIN hsg.tb_conta_usu c ON c.id_conta_usu = p.id_conta_usu
WHERE c.nm_usu = 'claudio.filho'
  AND a.nm_alrg = 'Amendoim';
