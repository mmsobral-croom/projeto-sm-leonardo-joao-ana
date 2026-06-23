import esd.ListaSequencial;
import esd.TabHash;
import sm.*;

public class Buscador {
    private Giassi giassi;
    private Bistek bistek;
    private Fort fort;
    private TabHash<String, TabHash<String, ListaSequencial<Produto>>> cache;
    private TabHash<String, Produto> mapGiassi;
    private TabHash<String, Produto> mapBistek;
    private TabHash<String, Produto> mapFort;

    public Buscador() {
        this.giassi = new Giassi();
        this.bistek = new Bistek();
        this.fort = new Fort();
        this.cache = new TabHash<>();
        this.mapGiassi = new TabHash<>();
        this.mapBistek = new TabHash<>();
        this.mapFort = new TabHash<>();
    }

    public ListaSequencial<Produto> buscar(String produtoNome) {
        IO.println("Buscando produtos");

        if (cache.contem(produtoNome)) {
            TabHash<String, ListaSequencial<Produto>> produtosIntersecaoHash = cache.obtem(produtoNome);
            ListaSequencial<Produto> produtosGiassiCache = produtosIntersecaoHash.obtem("Giassi");
            ListaSequencial<Produto> produtosBistekCache = produtosIntersecaoHash.obtem("Bistek");
            ListaSequencial<Produto> produtosFortCache = produtosIntersecaoHash.obtem("Fort");

            for (int i = 0; i < produtosGiassiCache.comprimento(); i++) {
                Produto pG = produtosGiassiCache.obtem(i);
                Produto pB = produtosBistekCache.obtem(i);
                Produto pF = produtosFortCache.obtem(i);

                mapGiassi.adiciona(pG.getEan(), pG);
                mapBistek.adiciona(pB.getEan(), pB);
                mapFort.adiciona(pF.getEan(), pF);
            }
            return produtosGiassiCache;
        }

        Supermercado.Resultado buscaGiassi = giassi.busca(produtoNome);
        Supermercado.Resultado buscaBistek = bistek.busca(produtoNome);
        Supermercado.Resultado buscaFort = fort.busca(produtoNome);

        ListaSequencial<String> eansGiassi = new ListaSequencial<>();
        if (buscaGiassi != null) {
            for (Produto p : buscaGiassi) {
                if (p.isDisponivel()) {
                    eansGiassi.adiciona(p.getEan());
                    mapGiassi.adiciona(p.getEan(), p);
                }
            }
        }

        ListaSequencial<String> eansBistek = new ListaSequencial<>();
        if (buscaBistek != null) {
            for (Produto p : buscaBistek) {
                if (p.isDisponivel()) {
                    eansBistek.adiciona(p.getEan());
                    mapBistek.adiciona(p.getEan(), p);
                }
            }
        }

        ListaSequencial<String> eansFort = new ListaSequencial<>();
        if (buscaFort != null) {
            for (Produto p : buscaFort) {
                if (p.isDisponivel()) {
                    eansFort.adiciona(p.getEan());
                    mapFort.adiciona(p.getEan(), p);
                }
            }
        }

        ListaSequencial<String> produtosEan = eansGiassi.interseccao(eansBistek.interseccao(eansFort));

        ListaSequencial<Produto> produtosIntersecaoGiassi = new ListaSequencial<>();
        ListaSequencial<Produto> produtosIntersecaoBistek = new ListaSequencial<>();
        ListaSequencial<Produto> produtosIntersecaoFort = new ListaSequencial<>();

        for (String ean : produtosEan) {
            produtosIntersecaoGiassi.adiciona(mapGiassi.obtem(ean));
            produtosIntersecaoBistek.adiciona(mapBistek.obtem(ean));
            produtosIntersecaoFort.adiciona(mapFort.obtem(ean));
        }

        if (!produtosIntersecaoGiassi.esta_vazia()) {
            TabHash<String, ListaSequencial<Produto>> produtosIntersecaoHash = new TabHash<>();
            produtosIntersecaoHash.adiciona("Giassi", produtosIntersecaoGiassi);
            produtosIntersecaoHash.adiciona("Bistek", produtosIntersecaoBistek);
            produtosIntersecaoHash.adiciona("Fort", produtosIntersecaoFort);
            cache.adiciona(produtoNome, produtosIntersecaoHash);
        }

        return produtosIntersecaoGiassi;
    }

    public TabHash<String, Produto> getMapBistek() {
        return mapBistek;
    }

    public TabHash<String, Produto> getMapFort() {
        return mapFort;
    }
}
