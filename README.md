# Documentação do Projeto: Sistema de Gestão de Estoque e Ativos (SENAI-SP)

## Requisitos Funcionais (RF)

| ID | Requisito | Descrição | Regra de Negócio |
| :--- | :--- | :--- | :--- |
| **RF-01** | Autenticação por NIF | Permitir o login de funcionários via NIF e senha. | **RN-02** |
| **RF-02** | White-list de Cadastro | Validar se o NIF/Nome estão autorizados antes de criar a conta. | **RN-01** |
| **RF-03** | Controle de Sessão | Restringir o acesso às áreas internas apenas para usuários logados. | **RN-04** |
| **RF-04** | Cadastro de Materiais | CRUD completo de materiais de consumo. | **RN-03** |
| **RF-05** | Gestão de Ativos | Registro e controle de bens permanentes da unidade escolar. | **RN-05** |

---

## Requisitos Não Funcionais (RNF)

| ID | Categoria | Descrição |
| :--- | :--- | :--- |
| **RNF-01** | **Interface** | A interface deve seguir rigorosamente o **Manual de Identidade Visual do SENAI-SP** (cores, fontes oficiais e logotipos). |
| **RNF-02** | **Segurança** | As senhas dos usuários devem ser protegidas por criptografia/hash (ex: BCrypt). |
| **RNF-03** | **Persistência** | O sistema deve utilizar banco de dados relacional (Postgre) |
| **RNF-04** | **Tecnologia** | Back-end desenvolvido em **Java 21** com **Spring Boot 4.0.3** e Front-end com **Thymeleaf**. |
| **RNF-05** | **Usabilidade** | O layout deve ser responsivo, adaptando-se a desktops e tablets de laboratórios. |

---

## Regras de Negócio (RN)

| ID | Nome | Descrição Detalhada |
| :--- | :--- | :--- |
| **RN-01** | **Validação de Cadastro** | O cadastro de um novo funcionário só é efetuado se o **NIF** e o **Nome** constarem exatamente iguais na tabela `funcionarios_autenticados`. |
| **RN-02** | **Unicidade de Identidade** | O sistema deve impedir o cadastro de dois funcionários diferentes com o mesmo **NIF**. |
| **RN-03** | **Bloqueio de Estoque Negativo** | Não é permitido realizar saídas de materiais se a quantidade solicitada for superior ao saldo atual em estoque. |
| **RN-04** | **Expiração de Sessão** | Por segurança, a sessão do usuário deve ser invalidada após 30 minutos de inatividade. |
| **RN-05** | **Integridade de Ativos** | Ativos patrimoniais vinculados a movimentações não podem ser excluídos, apenas marcados como "Inativos". |
