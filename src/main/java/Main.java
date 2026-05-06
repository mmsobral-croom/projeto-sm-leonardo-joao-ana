import esd.ListaSequencial;
import sm.*;
import sm.Produto;
import java.util.Objects;

import lombok.*;

import static esd.ListaSequencial.interseccao;

public class Main {
    static void main() {
        Giassi sm = new Giassi();
        Bistek bistek = new Bistek();
        Fort fort = new Fort();

        ListaSequencial<Produto> produtos = new ListaSequencial<>();
        ListaSequencial<Produto> carrinho = new ListaSequencial<>();

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


                for (Produto p: carrinho) {
                    precoGiassi += p.getPreco();
                }
                IO.println("Preço do Bistek: " + precoBistek);
                IO.println("Preço do Giassi: " + precoGiassi);
                IO.println("Preço do Fort: " + precoFort);

                continue;
            }

            IO.println("Buscando produtos");

            // procura todos produtos cujo nome contenha "tapioca"
            Supermercado.Resultado produtosGiassi = sm.busca(produtoNome);
            Supermercado.Resultado produtosBistek = bistek.busca(produtoNome);
            Supermercado.Resultado produtosFort = fort.busca(produtoNome);

            ListaSequencial<String> produtosGiassi2 = new ListaSequencial<>();
            for (Produto p: produtosGiassi) {
                produtosGiassi2.adiciona(p.getEan());
            }
            ListaSequencial<String> produtosBistek2 = new ListaSequencial<>();
            for (Produto p: produtosBistek) {
                produtosBistek2.adiciona(p.getEan());
            }
            ListaSequencial<String> produtosFort2 = new ListaSequencial<>();
            for (Produto p: produtosFort) {
                produtosFort2.adiciona(p.getEan());
            }

            ListaSequencial<String> produtosEan =  produtosGiassi2.interseccao(produtosBistek2.interseccao(produtosFort2));

            ListaSequencial<Produto> produtosGiassi3 = new ListaSequencial<>();
            for (Produto p: produtosGiassi) {
                produtosGiassi3.adiciona(p);
            }

            for(String ean : produtosEan){
                for(Produto produto: produtosGiassi3){
                    if(Objects.equals(produto.getEan(), ean)){
                        produtos.adiciona(produto);
                    }
                }
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
