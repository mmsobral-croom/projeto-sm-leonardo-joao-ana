[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/0aiXsnlU)
# Projeto 1: Melhor Preço

## Descrição

Este projeto tem como objetivo principal auxiliar o usuário a encontrar o supermercado com o melhor preço para uma cesta de produtos específica. Desenvolvido em Java, o programa permite que o usuário defina uma lista de itens desejados e, em seguida, calcula o custo total dessa cesta em diferentes supermercados. Ao final, apresenta os resultados de forma organizada, indicando qual estabelecimento oferece a opção mais econômica.

Para alcançar essa funcionalidade, o sistema integra buscadores de produtos para supermercados como Giassi, Bistek e Fort Atacadista. A gestão eficiente dos dados de produtos e preços é realizada através de uma implementação de Tabela Hash, que otimiza o armazenamento e a recuperação de informações.

## Objetivo

Desenvolver um programa que permita ao usuário definir uma cesta de compras e identificar qual supermercado oferece o menor preço total para essa cesta. O programa deve exibir os supermercados e seus respectivos preços de cesta, ordenados do mais barato para o mais caro.

## Supermercados Suportados

Atualmente, o sistema integra buscadores para os seguintes supermercados:

*   Giassi
*   Bistek
*   Fort Atacadista

## Componentes Principais

O projeto é composto por diversas classes que trabalham em conjunto para fornecer a funcionalidade de comparação de preços:

*   **Buscadores de Produtos (`sm` package)**: Classes como `Giassi`, `Bistek` e `Fort` implementam a lógica para buscar produtos em seus respectivos supermercados. Eles oferecem os métodos `busca(String nome)` para encontrar produtos por nome e `obtem(String productId)` para recuperar um produto específico.

*   **`Resultado` (classe auxiliar)**: Um objeto `Resultado` é retornado pelos buscadores e permite iterar sobre os produtos encontrados. Ele facilita o acesso aos itens da busca, podendo ser utilizado em laços `for-each` ou com a API de Streams do Java.

*   **`Produto` (classe `sm/Produto.java`)**: Representa um item de produto, contendo informações essenciais como:
    *   `nome()`: Nome do produto.
    *   `id()`: Identificador único do produto no supermercado.
    *   `marca()`: Marca do produto.
    *   `preco()`: Preço unitário do produto.
    *   `ean()`: Código EAN (código de barras) do produto.
    *   `disponivel()`: Indica se o produto está em estoque.

*   **`TabHash` (classe `esd/TabHash.java`)**: Uma implementação genérica de Tabela Hash, utilizada internamente para armazenar e gerenciar dados de forma eficiente. Ela emprega a técnica de **sondagem linear** para resolver colisões, garantindo um acesso rápido aos elementos mesmo em casos de chaves que mapeiam para a mesma posição inicial.

*   **`ListaSequencial` (classe `esd/ListaSequencial.java`)**: Uma lista sequencial genérica que serve como estrutura de apoio para a `TabHash` e outras partes do projeto que necessitam de uma coleção dinâmica de elementos.

## Manual de Utilização

Para utilizar o programa de comparação de preços, siga as instruções detalhadas no arquivo `MANUAL.md`.

## Autores

*   Ana Ronzani
*   João Schmitt
*   Leonardo Muller