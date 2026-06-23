# Calculadora de Materiais para Obra Residencial

Sistema em Spring Boot que calcula o consumo de materiais em fases de obras residenciais, modelando a planta da casa como um grafo G=(V,A), onde as arestas representam as paredes e os vértices representam os encontros das paredes (pilares).

## Sobre o Projeto

Empresas de engenharia precisam dimensionar materiais e gerar orçamentos com base em previsão de custos. Este sistema recebe parâmetros fornecidos pelo usuário e aplica fórmulas da engenharia civil para calcular o consumo de materiais em determinadas fases da obra residencial.

O projeto cobre as seguintes etapas:

- **Etapa 2** — Cálculo do volume de concreto para vigas baldrame na fundação
- **Etapa 3** — Cálculo da quantidade de tijolos para as paredes, com desconto de janelas e portas
- **Etapa 4** — Interface web em Jakarta Faces (JSF) com PrimeFaces para submissão de orçamentos
- **Etapa 5** — Persistência de orçamentos no banco de dados com busca por nome do cliente ou número do orçamento

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.5
- Spring Data JPA
- Jakarta Faces (JSF) + PrimeFaces 13
- JoinFaces (integração JSF + Spring Boot)
- Banco de dados H2
- Maven
- Lombok

## Estrutura do Projeto
src/

└── main/

└── java/com/obra/calculadora/

├── bean/            # Beans JSF (OrcamentoBean)

├── config/          # Tratamento global de exceções

├── controller/      # Endpoints REST

├── domain/          # Entidades do grafo (Vertice, Aresta, Comodo, Orcamento)

├── dto/             # Objetos de requisição e resposta

├── repository/      # Repositórios JPA

└── service/         # Regras de negócio

└── main/

└── resources/

├── META-INF/resources/

│   └── orcamento.xhtml   # Tela JSF

├── static/

│   └── index.html        # Interface HTML alternativa

└── application.properties

## Como Rodar

Pré-requisitos: Java 21 e Maven instalados.

```bash
# Clone o repositório
git clone https://github.com/pietrameneses/calculadora_obra.git

# Entre na pasta
cd calculadora_obra/calculadora

# Rode o projeto
./mvnw spring-boot:run
```

- Interface JSF: http://localhost:8080/orcamento.xhtml
- Interface HTML: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:obradb`
  - Usuário: `sa`
  - Senha: (vazio)

## Tela JSF — Funcionalidades

A tela em `orcamento.xhtml` permite:

- Informar o nome do cliente
- Selecionar o tipo de cálculo (Concreto ou Tijolos)
- Adicionar e remover paredes dinamicamente
- Informar aberturas (janelas e portas) por parede
- Calcular e salvar o orçamento no banco de dados
- Buscar orçamentos salvos por nome do cliente ou número do orçamento

## Endpoints REST

### Etapa 2 — Volume de Concreto (Fundação)
`POST /api/fundacao/concreto`

```json
{
  "arestas": [
    { "nome": "Parede Norte", "comprimento": 5.0 },
    { "nome": "Parede Sul",   "comprimento": 5.0 }
  ],
  "larguraViga": 0.20,
  "alturaViga": 0.30
}
```

### Etapa 3 — Quantidade de Tijolos (Paredes)
`POST /api/paredes/tijolos`

```json
{
  "arestas": [
    {
      "nome": "Parede Norte",
      "comprimento": 5.0,
      "altura": 3.0,
      "possuiJanela": true,
      "larguraJanela": 1.2,
      "alturaJanela": 1.0
    }
  ],
  "comprimentoTijolo": 0.19,
  "alturaTijolo": 0.09,
  "larguraTijolo": 0.09,
  "fatorDesperdicio": 1.10
}
```

### Etapa 4 — Orçamentos
- `POST /api/orcamentos/concreto/{nomeCliente}` — salva orçamento de concreto
- `POST /api/orcamentos/tijolos/{nomeCliente}` — salva orçamento de tijolos
- `GET /api/orcamentos` — lista todos os orçamentos
- `GET /api/orcamentos/cliente/{nomeCliente}` — busca por nome do cliente
- `GET /api/orcamentos/numero/{numeroOrcamento}` — busca por número do orçamento

## Testes

```bash
./mvnw test
```

11 testes no total:

- **FundacaoServiceTest** — 4 testes: volume por aresta, múltiplas arestas, detalhes e fórmula
- **ParedeServiceTest** — 7 testes: sem aberturas, desconto de janela, desconto de porta, janela + porta, fator de desperdício, múltiplas arestas e fórmula

## Fórmulas Utilizadas

**Volume de Concreto — Viga Baldrame:**
V = L × A × C
Onde L = largura da viga, A = altura da viga, C = comprimento da parede.

**Quantidade de Tijolos:**
N = ceil((Área_líquida / Área_tijolo) × fator_desperdício)

Área_líquida = (Comprimento × Altura) - área de janelas - área de portas

## Modelagem — Grafo G=(V,A)

- **Vértices (V)** — pontos de encontro das paredes, que receberão os pilares estruturais
- **Arestas (A)** — as paredes, com comprimento, altura, espessura e aberturas (janelas/portas)
- **Cômodos** — conjuntos de arestas que formam um ambiente
- **Orçamento** — registro persistido no banco com número único, nome do cliente, tipo, resultado e data

## Autor

Desenvolvido por Pietra Meneses
