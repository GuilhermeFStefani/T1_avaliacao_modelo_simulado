# Trabalho 1 - Simulação e Métodos Analíticos

Alunos:

- Guilherme Farias Stefani
- Henrique Cardoso Zanette

### Requisitos

Antes de rodar a aplicação, é necessário ter os seguintes requisitos instalados:

- Java SE 19.

### Configurando a simulação

Para configurar a simulação, é necessário alterar o arquivo config.json.

O arquivo possui os seguintes parâmetros:

`simulationCount`: número de simulações a serem realizadas

`executionCount`: número de execuções da simulação

`queues`: filas a serem utilizadas na simulação

Para uma fila, podemos fornecer os seguintes parâmetros:

`id`: nome da fila

`servers`: número de servidores

`capacity`: capacidade máxima da fila

`exit`: tempo mínimo e máximo de saída

`first_entry`: tempo da primeira chegada

`networks`: possibilidade de roteamento entre as filas, cada objeto da lista representa uma fila de destino e a probabilidade
deste roteamento ocorrer.

## Rodando a aplicação

Para rodar a aplicação, é necessário seguir os seguintes passos:

Executar o executável na raiz do projeto:
`java -jar queuesimulator-1.0-SNAPSHOT.jar`
