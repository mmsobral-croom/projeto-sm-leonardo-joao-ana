import esd.ListaSequencial;
import sm.Produto;

public class InterfaceUsuario {
    public static void exibirResultados(ListaSequencial<Produto> resultados) {
        if (resultados.esta_vazia()) {
            IO.println("Nenhum produto encontrado");
            return;
        }

        int id = 1;
        for (Produto p : resultados) {
            IO.println(id + " - " + p.getId() + " - " + p.getNome());
            id++;
        }
    }

    public static String pedirProduto() {
        return IO.readln("Digite o nome do produto que você quer buscar, 'listar' para listar os produtos no carrinho, 'preco' para calcular o total ou 'sair' para sair: ").trim().toLowerCase();
    }

    public static int pedirIdParaCarrinho() {
        try {
            return Integer.parseInt(IO.readln("Digite o id do produto a adicionar no carrinho: "));
        } catch (Exception e) {
            IO.println("Opção inválida.");
            return -1;
        }
    }
}