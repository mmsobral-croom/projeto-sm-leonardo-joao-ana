import esd.ListaSequencial;
import sm.Produto;
import java.util.Objects;

public class Menu {
    private Buscador buscador;
    private Carrinho carrinho;

    public Menu() {
        this.buscador = new Buscador();
        this.carrinho = new Carrinho();
    }

    public void iniciar() {
        String produtoNome = "";

        while (!Objects.equals(produtoNome, "sair")) {
            produtoNome = InterfaceUsuario.pedirProduto();

            if (Objects.equals(produtoNome, "listar")) {
                carrinho.listar();
                continue;
            }

            if (Objects.equals(produtoNome, "preco")) {
                carrinho.calcularPrecos(buscador.getMapBistek(), buscador.getMapFort());
                continue;
            }

            if (Objects.equals(produtoNome, "sair")) {
                break;
            }

            processarBusca(produtoNome);
        }
    }

    private void processarBusca(String produtoNome) {
        ListaSequencial<Produto> resultados = buscador.buscar(produtoNome);
        InterfaceUsuario.exibirResultados(resultados);

        int idSelecionado = InterfaceUsuario.pedirIdParaCarrinho();
        if (idSelecionado > 0 && idSelecionado <= resultados.comprimento()) {
            Produto selecionado = resultados.obtem(idSelecionado - 1);
            carrinho.adicionar(selecionado);
        }
    }
}
