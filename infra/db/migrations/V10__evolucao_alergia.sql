CREATE SEQUENCE IF NOT EXISTS hsg.SEQ_ALRG
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE hsg.TB_ALRG (
    ID_ALRG         BIGINT          NOT NULL DEFAULT nextval('hsg.SEQ_ALRG'),
    ID_PAC          BIGINT          NOT NULL,
    NM_ALRG         VARCHAR(100)    NOT NULL,
    DS_ALRG         VARCHAR(500),
    TP_ALRG         VARCHAR(2)      NOT NULL,
    TP_GRAV_ALRG    VARCHAR(1)      NOT NULL,
    ST_ALERGIA      VARCHAR(10)     NOT NULL DEFAULT 'INFORMADA',
    OBS_ALRG        VARCHAR(500),
    OBS_ENF_ALRG    VARCHAR(500),
    DS_REACAO       VARCHAR(500),
    ID_CAD_ALRG     BIGINT,
    ID_APR_ALRG     BIGINT,
    DT_ULT_REACAO   TIMESTAMP,
    DT_CAD_ALRG     TIMESTAMP       NOT NULL DEFAULT NOW(),
    DT_ULT_ATU_ALRG TIMESTAMP,
    CONSTRAINT PK_ALRG PRIMARY KEY (ID_ALRG),
    CONSTRAINT FK_ALRG_PAC FOREIGN KEY (ID_PAC) REFERENCES hsg.tb_pac (id_pac)
);

CREATE SEQUENCE IF NOT EXISTS hsg.SEQ_ALRG_HIST
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE hsg.TB_ALRG_HIST (
    ID_ALRG_HIST    BIGINT          NOT NULL DEFAULT nextval('hsg.SEQ_ALRG_HIST'),
    ID_ALRG         BIGINT          NOT NULL,
    ID_USR_HIST     BIGINT,
    ACAO_HIST       VARCHAR(10)     NOT NULL,
    NM_ALRG_SNAP    VARCHAR(100),
    TP_ALRG_SNAP    VARCHAR(2),
    TP_GRAV_SNAP    VARCHAR(1),
    ST_ALRG_SNAP    VARCHAR(10),
    DT_ACAO_HIST    TIMESTAMP       NOT NULL DEFAULT NOW(),
    CONSTRAINT PK_ALRG_HIST PRIMARY KEY (ID_ALRG_HIST),
    CONSTRAINT FK_ALRG_HIST_ALRG FOREIGN KEY (ID_ALRG) REFERENCES hsg.TB_ALRG (ID_ALRG)
);

INSERT INTO hsg.TB_ALRG (
    ID_ALRG, ID_PAC, NM_ALRG, DS_ALRG, TP_ALRG, TP_GRAV_ALRG,
    ST_ALERGIA, OBS_ALRG, DS_REACAO,
    ID_CAD_ALRG, DT_CAD_ALRG, DT_ULT_ATU_ALRG
)
SELECT
    nextval('hsg.SEQ_ALRG'),
    p.id_pac,
    'Dipirona',
    'Alergia ao princípio ativo dipirona sódica.',
    'M', 'G',
    'INFORMADA',
    'Paciente relata reação alérgica grave após uso em 2023.',
    'Urticária generalizada, queda de pressão.',
    p.id_pac,
    NOW(),
    NOW()
FROM hsg.tb_pac p
JOIN hsg.tb_conta_usu c ON c.id_conta_usu = p.id_conta_usu
WHERE c.nm_usu = 'claudio.filho'
LIMIT 1;

INSERT INTO hsg.TB_ALRG_HIST (
    ID_ALRG_HIST, ID_ALRG, ID_USR_HIST, ACAO_HIST,
    NM_ALRG_SNAP, TP_ALRG_SNAP, TP_GRAV_SNAP, ST_ALRG_SNAP, DT_ACAO_HIST
)
SELECT
    nextval('hsg.SEQ_ALRG_HIST'),
    a.ID_ALRG,
    p.id_pac,
    'CRIADA',
    a.NM_ALRG, a.TP_ALRG, a.TP_GRAV_ALRG, a.ST_ALERGIA,
    NOW()
FROM hsg.TB_ALRG a
JOIN hsg.tb_pac p ON p.id_pac = a.ID_CAD_ALRG
JOIN hsg.tb_conta_usu c ON c.id_conta_usu = p.id_conta_usu
WHERE c.nm_usu = 'claudio.filho'
  AND a.NM_ALRG = 'Dipirona';

INSERT INTO hsg.TB_ALRG (
    ID_ALRG, ID_PAC, NM_ALRG, DS_ALRG, TP_ALRG, TP_GRAV_ALRG,
    ST_ALERGIA, OBS_ALRG, DS_REACAO,
    ID_CAD_ALRG, DT_CAD_ALRG, DT_ULT_ATU_ALRG
)
SELECT
    nextval('hsg.SEQ_ALRG'),
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

INSERT INTO hsg.TB_ALRG_HIST (
    ID_ALRG_HIST, ID_ALRG, ID_USR_HIST, ACAO_HIST,
    NM_ALRG_SNAP, TP_ALRG_SNAP, TP_GRAV_SNAP, ST_ALRG_SNAP, DT_ACAO_HIST
)
SELECT
    nextval('hsg.SEQ_ALRG_HIST'),
    a.ID_ALRG,
    p.id_pac,
    'CRIADA',
    a.NM_ALRG, a.TP_ALRG, a.TP_GRAV_ALRG, 'INFORMADA',
    NOW() - INTERVAL '30 days'
FROM hsg.TB_ALRG a
JOIN hsg.tb_pac p ON p.id_pac = a.ID_CAD_ALRG
JOIN hsg.tb_conta_usu c ON c.id_conta_usu = p.id_conta_usu
WHERE c.nm_usu = 'claudio.filho'
  AND a.NM_ALRG = 'Amendoim';

INSERT INTO hsg.TB_ALRG_HIST (
    ID_ALRG_HIST, ID_ALRG, ID_USR_HIST, ACAO_HIST,
    NM_ALRG_SNAP, TP_ALRG_SNAP, TP_GRAV_SNAP, ST_ALRG_SNAP, DT_ACAO_HIST
)
SELECT
    nextval('hsg.SEQ_ALRG_HIST'),
    a.ID_ALRG,
    p.id_pac,
    'APROVADA',
    a.NM_ALRG, a.TP_ALRG, a.TP_GRAV_ALRG, a.ST_ALERGIA,
    NOW() - INTERVAL '10 days'
FROM hsg.TB_ALRG a
JOIN hsg.tb_pac p ON p.id_pac = a.ID_CAD_ALRG
JOIN hsg.tb_conta_usu c ON c.id_conta_usu = p.id_conta_usu
WHERE c.nm_usu = 'claudio.filho'
  AND a.NM_ALRG = 'Amendoim';
