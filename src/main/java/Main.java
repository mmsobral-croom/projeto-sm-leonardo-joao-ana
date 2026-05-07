import esd.ListaSequencial;
import sm.*;
import sm.Produto;

import java.util.HashMap;
import java.util.Objects;

import lombok.*;

import static esd.ListaSequencial.interseccao;

public class Main {
    static void main() {
        Giassi sm = new Giassi();
        Bistek bistek = new Bistek();
        Fort fort = new Fort();

        ListaSequencial<Produto> produtos = new ListaSequencial<>(); //esse é do giassi

        ListaSequencial<Produto> carrinho = new ListaSequencial<>(); //esse é do giassi

        HashMap<String, Produto> mapProdutosBistek = new HashMap<>();
        HashMap<String, Produto> mapProdutosFort = new HashMap<>();
        HashMap<String, Produto> mapProdutos = new HashMap<>(); //esse é do giassi

        String produtoNome = "";

        while (!Objects.equals(produtoNome, "sair")) {
            produtoNome = IO.readln("Digite o nome do produto que você quer buscar, 'listar' para listar os produtos no carrinho ou 'sair' para sair : ");

            if (Objects.equals(produtoNome, "listar")) {
                for (Produto p: carrinho) {
                    IO.println(p.getNome());
                }

                continue;
            }

            if (Objects.equals(produtoNome, "preco")) {
                float precoBistek = 0;
                float precoGiassi = 0;
                float precoFort = 0;


                for(int i = 0; i < carrinho.comprimento(); i++){
                    precoGiassi += carrinho.obtem(i).getPreco();
                    precoBistek += mapProdutosBistek.get(carrinho.obtem(i).getEan()).getPreco();
                    precoFort += mapProdutosFort.get(carrinho.obtem(i).getEan()).getPreco();
                }

                IO.println("Preço do Bistek: " + precoBistek);
                IO.println("Preço do Giassi: " + precoGiassi);
                IO.println("Preço do Fort: " + precoFort);

                continue;
            }

            IO.println("Buscando produtos");

            // procura todos produtos cujo nome contenha "tapioca"
            Supermercado.Resultado buscaGiassi = sm.busca(produtoNome);
            Supermercado.Resultado buscaBistek = bistek.busca(produtoNome);
            Supermercado.Resultado buscaFort = fort.busca(produtoNome);

            ListaSequencial<String> produtosGiassi = new ListaSequencial<>();
            for (Produto p: buscaGiassi) {
                produtosGiassi.adiciona(p.getEan());
                mapProdutos.put(p.getEan(), p);
            }
            ListaSequencial<String> produtosBistek = new ListaSequencial<>();
            for (Produto p: buscaBistek) {
                produtosBistek.adiciona(p.getEan());
                mapProdutosBistek.put(p.getEan(), p);
            }
            ListaSequencial<String> produtosFort = new ListaSequencial<>();
            for (Produto p: buscaFort) {
                produtosFort.adiciona(p.getEan());
                mapProdutosFort.put(p.getEan(), p);
            }

            ListaSequencial<String> produtosEan =  produtosGiassi.interseccao(produtosBistek.interseccao(produtosFort));

            for(String ean : produtosEan){
                produtos.adiciona(mapProdutos.get(ean));
            }

            if (produtos.esta_vazia()) {
                IO.println("Nenhum produto encontrado");
                continue;
            }

            int id = 1;

            for (Produto p : produtos) {
                IO.println(id + " - " + p.getNome());
                id++;
            }

            int idProdutoSelecionado = Integer.parseInt(IO.readln("Digite o id do produto a adicionar no carrinho: "));
            carrinho.adiciona(produtos.obtem(idProdutoSelecionado - 1));

            produtos.limpa();
        }
    }
}