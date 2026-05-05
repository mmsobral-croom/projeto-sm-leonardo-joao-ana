import esd.ListaSequencial;
import sm.*;
import sm.Produto;
import java.util.Objects;

import lombok.*;

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

            IO.println("Buscando produtos");

            // procura todos produtos cujo nome contenha "tapioca"
            Supermercado.Resultado produtosGiassi = sm.busca(produtoNome);
            Supermercado.Resultado produtosBistek = bistek.busca(produtoNome);
            Supermercado.Resultado produtosFort = fort.busca(produtoNome);

            ListaSequencial<Produto> produtosGiassi2 = new ListaSequencial<>();
            for (Produto p: produtosGiassi) {
                produtosGiassi2.adiciona_produto_unico(p);
            }
            ListaSequencial<Produto> produtosBistek2 = new ListaSequencial<>();
            for (Produto p: produtosBistek) {
                produtosBistek2.adiciona_produto_unico(p);
            }
            ListaSequencial<Produto> produtosFort2 = new ListaSequencial<>();
            for (Produto p: produtosFort) {
                produtosFort2.adiciona_produto_unico(p);
            }

            produtos = produtosGiassi2.interseccao(produtosBistek2).interseccao(produtosFort2);

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
