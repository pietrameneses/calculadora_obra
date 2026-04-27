# Calculadora de Materiais para Obra Residencial

Sistema backend em Spring Boot que calcula o consumo de materiais em fases de obras residenciais, modelando a planta da casa como um grafo G=(V,A), onde as arestas representam as paredes e os vértices representam os encontros das paredes que receberão elementos estruturais chamados pilares.

---

## Sobre o Projeto

Empresas de engenharia precisam dimensionar materiais e gerar orçamentos com base em previsão de custos. Este sistema recebe parâmetros fornecidos pelo usuário e aplica fórmulas da engenharia civil para calcular o consumo de materiais em determinadas fases da obra residencial.

O projeto cobre duas etapas principais:

- **Etapa 2** — Cálculo do volume de concreto para vigas baldrame na fundação
- **Etapa 3** — Cálculo da quantidade de tijolos para as paredes, com desconto de janelas e portas

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.5
- Spring Data JPA
- Banco de dados H2 (em memória)
- Maven
- Lombok

---

## Estrutura do Projeto

```
src/
└── main/
    └── java/com/obra/calculadora/
        ├── config/          # Tratamento global de exceções
        ├── controller/      # Endpoints REST
        ├── domain/          # Entidades do grafo (Vertice, Aresta, Comodo)
        ├── dto/             # Objetos de requisição e resposta
        ├── repository/      # Repositórios JPA
        └── service/         # Regras de negócio
```

---

## Como Rodar

**Pré-requisitos:** Java 21 e Maven instalados.

```bash
# Clone o repositório
git clone https://github.com/SEU_USUARIO/calculadora-obra.git

# Entre na pasta
cd calculadora-obra

# Rode o projeto
./mvnw spring-boot:run
```

Acesse a interface em: **http://localhost:8080**

Console do banco de dados H2: **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:obradb`
- Usuario: `sa`
- Senha: *(vazio)*

---

## Endpoints REST

### Etapa 2 — Volume de Concreto (Fundação)

**POST** `/api/fundacao/concreto`

Requisição:
```json
{
  "arestas": [
    { "nome": "Parede Norte", "comprimento": 5.0 },
    { "nome": "Parede Sul",   "comprimento": 5.0 },
    { "nome": "Parede Leste", "comprimento": 3.0 },
    { "nome": "Parede Oeste", "comprimento": 3.0 }
  ],
  "larguraViga": 0.20,
  "alturaViga": 0.30
}
```

Resposta:
```json
{
  "volumeTotalM3": 0.96,
  "formula": "V = L x A x C  |  L=0.2m, A=0.3m, C=comprimento da aresta",
  "detalhes": [
    {
      "nomeAresta": "Parede Norte",
      "comprimento": 5.0,
      "largura": 0.2,
      "altura": 0.3,
      "volume": 0.3
    }
  ]
}
```

---

### Etapa 3 — Quantidade de Tijolos (Paredes)

**POST** `/api/paredes/tijolos`

Requisição:
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
    },
    {
      "nome": "Parede Leste",
      "comprimento": 3.0,
      "altura": 3.0,
      "possuiPorta": true,
      "larguraPorta": 0.9,
      "alturaPorta": 2.1
    }
  ],
  "comprimentoTijolo": 0.19,
  "alturaTijolo": 0.09,
  "larguraTijolo": 0.09,
  "fatorDesperdicio": 1.10
}
```

Resposta:
```json
{
  "totalTijolos": 2094,
  "areaTotalLiquidaM2": 25.71,
  "areaTijoloM2": 0.017,
  "fatorDesperdicio": 1.1,
  "formula": "N = ceil((Area_liquida / Area_tijolo) x fator_desperdicio)",
  "detalhes": [
    {
      "nomeAresta": "Parede Norte",
      "comprimento": 5.0,
      "altura": 3.0,
      "areaDesconto": 1.2,
      "areaLiquida": 13.8,
      "tijolosPorParede": 891
    }
  ]
}
```

---

## Testes

Para rodar os testes:

```bash
./mvnw test
```

A suite cobre 11 testes no total:

- `FundacaoServiceTest` — 4 testes cobrindo cálculo de volume de concreto por aresta, múltiplas arestas, preenchimento de detalhes e retorno da fórmula
- `ParedeServiceTest` — 7 testes cobrindo cálculo sem aberturas, desconto de janela, desconto de porta, aplicação do fator de desperdício, fator padrão quando nulo, soma de múltiplas arestas e retorno da fórmula

---

## Fórmulas Utilizadas

**Volume de Concreto — Viga Baldrame:**

```
V = L x A x C
```

Onde L = largura da viga, A = altura da viga, C = comprimento da parede.

**Quantidade de Tijolos:**

```
N = ceil((Area_liquida / Area_tijolo) x fator_desperdicio)

Area_liquida = (Comprimento x Altura) - area de janelas - area de portas
```

---

## Modelagem — Grafo G=(V,A)

A planta da casa é representada como um grafo onde:

- **Vertices (V)** — pontos de encontro das paredes, que receberão os pilares estruturais
- **Arestas (A)** — as paredes, que possuem comprimento, altura, espessura e podem conter janelas ou portas
- **Comodos** — conjuntos de arestas que formam um ambiente, identificados por nome, largura, comprimento e altura

---

## Autor

Desenvolvido por **Pietra Meneses**  
