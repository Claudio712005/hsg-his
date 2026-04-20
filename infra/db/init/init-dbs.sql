-- Cria bancos adicionais de forma idempotente.
-- Executado pelo postgres na primeira inicialização do volume.

SELECT 'CREATE DATABASE keycloak'
  WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'keycloak') \gexec

SELECT 'CREATE DATABASE hsg'
  WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'hsg') \gexec

\connect hsg
CREATE SCHEMA IF NOT EXISTS hsg;
