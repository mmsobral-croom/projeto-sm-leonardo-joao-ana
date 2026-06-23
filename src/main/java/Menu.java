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

        if (!resultados.esta_vazia()) {
            InterfaceUsuario.pedirIdParaCarrinho();
            // Mantendo a lógica original: o ID é lido mas não é usado para adicionar ao carrinho no código original da Main.
        }
    }
}
