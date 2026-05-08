# Manual de Utilização: Comparador de Preços de Supermercados

Este manual descreve como utilizar o programa de comparação de preços de supermercados, que permite ao usuário encontrar o melhor preço para uma cesta de produtos em diferentes estabelecimentos.

## 1. Iniciando o Programa

Após compilar o projeto, execute o programa a partir da linha de comando:

```bash
java -cp .:json-20231013.jar:lombok-1.18.30.jar:src/main/java Main
```

O programa iniciará e solicitará a entrada do usuário.

## 2. Interagindo com o Programa

O programa opera em um ciclo interativo, onde você pode realizar as seguintes ações:

### 2.1. Buscar e Adicionar Produtos ao Carrinho

Para buscar um produto, digite o nome do produto desejado quando solicitado e pressione Enter. O programa buscará esse produto nos supermercados Giassi, Bistek e Fort Atacadista. Ele então exibirá uma lista dos produtos que estão disponíveis em **todos** os três supermercados, juntamente com um número de identificação.

**Exemplo:**

```
Digite o nome do produto que você quer buscar, 'listar' para listar os produtos no carrinho ou 'sair' para sair : arroz
Buscando produtos
1 - Arroz Branco Tipo 1 Giassi 5kg
2 - Arroz Parboilizado Bistek 1kg
3 - Arroz Integral Fort 1kg
Digite o id do produto a adicionar no carrinho: 1
```

Após a exibição dos produtos, digite o número de identificação do produto que você deseja adicionar ao seu carrinho de compras e pressione Enter.

### 2.2. Listar Produtos no Carrinho

Para ver os produtos que você já adicionou ao seu carrinho, digite `listar` quando solicitado e pressione Enter.

**Exemplo:**

```
Digite o nome do produto que você quer buscar, 'listar' para listar os produtos no carrinho ou 'sair' para sair : listar
Arroz Branco Tipo 1 Giassi 5kg
```

### 2.3. Comparar Preços da Cesta

Para calcular e comparar o preço total da sua cesta de compras em cada supermercado, digite `preco` quando solicitado e pressione Enter. O programa exibirá o preço total da sua cesta para cada um dos supermercados.

**Exemplo:**

```
Digite o nome do produto que você quer buscar, 'listar' para listar os produtos no carrinho ou 'sair' para sair : preco
Preço do Bistek: 25.50
Preço do Giassi: 27.90
Preço do Fort: 26.00
```

### 2.4. Sair do Programa

Para encerrar o programa, digite `sair` quando solicitado e pressione Enter.

## 3. Observações Importantes

*   O programa só exibirá produtos que estão disponíveis em **todos** os supermercados configurados para a busca atual.
*   Certifique-se de que os arquivos JAR das bibliotecas (`json-20231013.jar` e `lombok-1.18.30.jar`) estejam no mesmo diretório de execução ou que o `classpath` esteja configurado corretamente para evitar erros de execução.
