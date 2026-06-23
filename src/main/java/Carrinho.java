import esd.ListaSequencial;
import esd.TabHash;
import sm.Produto;

public class Carrinho {
    private ListaSequencial<Produto> itens;

    public Carrinho() {
        this.itens = new ListaSequencial<>();
    }

    public void adicionar(Produto p) {
        this.itens.adiciona(p);
    }

    public void listar() {
        for (Produto p : itens) {
            IO.println(p.getNome());
        }
    }

    public void calcularPrecos(TabHash<String, Produto> mapBistek, TabHash<String, Produto> mapFort) {
        float precoBistek = 0;
        float precoGiassi = 0;
        float precoFort = 0;

        for (int i = 0; i < itens.comprimento(); i++) {
            Produto p = itens.obtem(i);
            precoGiassi += p.getPreco();
            precoBistek += mapBistek.obtem(p.getEan()).getPreco();
            precoFort += mapFort.obtem(p.getEan()).getPreco();
        }

        IO.println("Preço do Bistek: " + precoBistek);
        IO.println("Preço do Giassi: " + precoGiassi);
        IO.println("Preço do Fort: " + precoFort);
    }

    public ListaSequencial<Produto> getItens() {
        return itens;
    }
}
