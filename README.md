# HSG HIS — Hospital Information System

Sistema de Gestão Hospitalar desenvolvido como simulação de ambiente corporativo baseado em arquitetura Java EE legada, com foco em domínio hospitalar realista e evolução incremental via MVPs.

---

## 1. Objetivo do Sistema

O HSG HIS tem como objetivo simular um sistema hospitalar real, cobrindo:

- Gestão de pacientes
- Agendamento de consultas
- Atendimento médico
- Fluxo operacional hospitalar
- Controle de acesso por perfis

O sistema foi projetado para refletir cenários corporativos encontrados em instituições de saúde de médio porte.

---

## 2. Stack Tecnológica

- Java EE (Jakarta EE legado)
- JSF + PrimeFaces (frontend server-side)
- EJB (regras de negócio)
- JPA (persistência)
- Maven multi-module
- EAR (empacotamento)
- WildFly como servidor de aplicação
- Keycloak para autenticação e autorização
- OpenShift para orquestração de containers
- Kubernetes para deploy e escalabilidade
- Docker para containerização
- Primefaces para componentes UI

---

## 3. Arquitetura

O sistema segue uma arquitetura monolítica modular:

- hsg-his-domain → entidades e modelo de domínio
- hsg-his-service → regras de negócio (EJB)
- hsg-his-web → camada de apresentação (JSF)
- hsg-his-ear → empacotamento e deploy

---

## 4. Execução do Projeto

### Build

```bash
./mvnw clean install