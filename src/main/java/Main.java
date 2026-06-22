import esd.ListaSequencial;
import esd.TabHash;
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

        ListaSequencial<Produto> carrinho = new ListaSequencial<>(); //esse é do giassi



        String produtoNome = "";

//        TabHash<String,Produto> tabHashProdutosIntersecaoGiassi = new TabHash<>();
//        TabHash<String,Produto> tabHashProdutosIntersecaoFort = new TabHash<>();
//        TabHash<String,Produto> tabHashProdutosIntersecaoBistek = new TabHash<>();


        //nome pesquisado, supermercado, lista produto
        TabHash<String, TabHash<String, ListaSequencial<Produto>>> cache = new TabHash<>();

        while (!Objects.equals(produtoNome, "sair")) {

            ListaSequencial<Produto> produtosIntersecaoGiassi = new ListaSequencial<>(); //esse é do giassi
            ListaSequencial<Produto> produtosIntersecaoBistek = new ListaSequencial<>(); //esse é do bistek
            ListaSequencial<Produto> produtosIntersecaoFort = new ListaSequencial<>(); //esse é do fort




            TabHash<String, Produto> mapProdutosBistek = new TabHash<>();
            TabHash<String, Produto> mapProdutosFort = new TabHash<>();
            TabHash<String, Produto> mapProdutos = new TabHash<>(); //esse é do giassi

            produtoNome = IO.readln("Digite o nome do produto que você quer buscar, 'listar' para listar os produtos no carrinho, 'preco' para calcular o total ou 'sair' para sair : ");

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
                    precoBistek += mapProdutosBistek.obtem(carrinho.obtem(i).getEan()).getPreco();
                    precoFort += mapProdutosFort.obtem(carrinho.obtem(i).getEan()).getPreco();
                }

                IO.println("Preço do Bistek: " + precoBistek);
                IO.println("Preço do Giassi: " + precoGiassi);
                IO.println("Preço do Fort: " + precoFort);

                continue;
            }

            IO.println("Buscando produtos");

            if (cache.contem(produtoNome)) {
                TabHash<String, ListaSequencial<Produto>> produtosIntersecaoHash = cache.obtem(produtoNome);
                ListaSequencial<Produto> produtosIntersecaoGiassiCache = produtosIntersecaoHash.obtem("Giassi");
                ListaSequencial<Produto> produtosIntersecaoBistekCache = produtosIntersecaoHash.obtem("Bistek");
                ListaSequencial<Produto> produtosIntersecaoFortCache = produtosIntersecaoHash.obtem("Fort");

                int id = 1;

                for (Produto p : produtosIntersecaoGiassiCache) {
                    IO.println(id + " - " + p.getId() +" - " + p.getNome()) ;
                    id++;
                }

                int idProdutoSelecionado = Integer.parseInt(IO.readln("Digite o id do produto a adicionar no carrinho: "));
                carrinho.adiciona(produtosIntersecaoGiassiCache.obtem(idProdutoSelecionado - 1));

                continue;
            }

            // procura todos produtos cujo nome contenha "tapioca"
            Supermercado.Resultado buscaGiassi = sm.busca(produtoNome);
            Supermercado.Resultado buscaBistek = bistek.busca(produtoNome);
            Supermercado.Resultado buscaFort = fort.busca(produtoNome);

            ListaSequencial<String> produtosGiassi = new ListaSequencial<>();
            for (Produto p: buscaGiassi) {
                produtosGiassi.adiciona(p.getEan());
                mapProdutos.adiciona(p.getEan(), p);
            }
            ListaSequencial<String> produtosBistek = new ListaSequencial<>();
            for (Produto p: buscaBistek) {
                produtosBistek.adiciona(p.getEan());
                mapProdutosBistek.adiciona(p.getEan(), p);
            }
            ListaSequencial<String> produtosFort = new ListaSequencial<>();
            for (Produto p: buscaFort) {
                produtosFort.adiciona(p.getEan());
                mapProdutosFort.adiciona(p.getEan(), p);
            }

            ListaSequencial<String> produtosEan =  produtosGiassi.interseccao(produtosBistek.interseccao(produtosFort));

            for(String ean : produtosEan){
                produtosIntersecaoGiassi.adiciona(mapProdutos.obtem(ean));
                produtosIntersecaoBistek.adiciona(mapProdutosBistek.obtem(ean));
                produtosIntersecaoFort.adiciona(mapProdutosFort.obtem(ean));
            }



            if (produtosIntersecaoGiassi.esta_vazia()) {
                IO.println("Nenhum produto encontrado");
                continue;
            }


            TabHash <String, ListaSequencial<Produto>> produtosIntersecaoHash = new TabHash<>();
            produtosIntersecaoHash.adiciona("Giassi", produtosIntersecaoGiassi);
            produtosIntersecaoHash.adiciona("Bistek", produtosIntersecaoBistek);
            produtosIntersecaoHash.adiciona("Fort", produtosIntersecaoFort);
            cache.adiciona(produtoNome, produtosIntersecaoHash);

            int id = 1;

            for (Produto p : produtosIntersecaoGiassi) {
                IO.println(id + " - " + p.getId() +" - " + p.getNome()) ;
                id++;
            }

            int idProdutoSelecionado = Integer.parseInt(IO.readln("Digite o id do produto a adicionar no carrinho: "));
            carrinho.adiciona(produtosIntersecaoGiassi.obtem(idProdutoSelecionado - 1));

            produtosIntersecaoGiassi.limpa();
            produtosIntersecaoBistek.limpa();
            produtosIntersecaoFort.limpa();
        }
    }
}