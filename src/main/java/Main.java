import esd.ListaSequencial;
import sm.Bistek;
import sm.Fort;
import sm.Giassi;
import sm.Produto;

public class Main {
    static void main() {

        // cria um acessador para o Giassi
        Giassi sm = new Giassi();
        Bistek bistek = new Bistek();
        Fort fort = new Fort();

        String produtoNome = IO.readln("Digite o nome do produto que você quer buscar: ");

        IO.println("Buscando produtos");
        ListaSequencial<String> produtosEan = new ListaSequencial<>();

        // procura todos produtos cujo nome contenha "tapioca"
        ListaSequencial<Produto> produtosGiassi = sm.busca(produtoNome);
        ListaSequencial<Produto> produtosBistek = bistek.busca(produtoNome);
        ListaSequencial<Produto> produtosFort = fort.busca(produtoNome);

        // Mostra cada um dos produtos encontrados
        for (int pos=0; pos < produtosGiassi.comprimento(); pos++) {
            produtosEan.adiciona_unico(produtosGiassi.obtem(pos).getEan());
        }

        for (int pos=0; pos < produtosBistek.comprimento(); pos++) {
            produtosEan.adiciona_unico(produtosBistek.obtem(pos).getEan());
        }

        for (int pos=0; pos < produtosFort.comprimento(); pos++) {
            produtosEan.adiciona_unico(produtosFort.obtem(pos).getEan());
        }


        for (int pos=0; pos < produtosEan.comprimento(); pos++) {
            IO.println(produtosEan.obtem(pos));
        }
    }
}
