package esd;

import java.lang.reflect.Array;
import java.security.KeyException;

public class TabHash <K, V> {
    public class Par {
        K chave;
        V valor;

        Par(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public K obtemChave() {
            return chave;
        }

        public V obtemValor() {
            return valor;
        }

        @Override
        public boolean equals(Object outro) {
            if (outro instanceof TabHash.Par) {
                Par _outro = (Par)outro;
                return chave.equals(_outro.chave);
            }
            return false;
        }
    }

    Par[] tab;
    int len = 0; // quantos pares estao armazenados
    final int defcap = 160000000;

    public TabHash() {
        // dimensiona a tabela
        tab = inicia_tabela(defcap);
    }


    @SuppressWarnings("unchecked")
    Par[] inicia_tabela(int linhas) {
        Par[] nova = (Par[]) Array.newInstance(Par.class, linhas);

        //inicia a lista com essa quantidade de linhas

        return nova;
    }

    public void adiciona(K chave, V valor) throws IndexOutOfBoundsException {
        //calcular o hash de chave e com ele o numero da linha
        int linha = (chave.hashCode() & 0x7fffffff) % tab.length;

        //verifica se existe um par contendo esta chave
        //se exister, ele esta na linha da tabela correspondente as hash

        Par p = tab[linha];
        if (p != null) {
            if (chave.equals(p.chave)) {
                p.valor = valor;
            }else{
                throw new IndexOutOfBoundsException("Colisão");
            }
        } else {
            tab[linha] = new Par(chave, valor);
            len++;
        }
    }

    public V obtem(K chave) {
        int linha = (chave.hashCode() & 0x7fffffff) % tab.length;

        Par p = tab[linha];
        if (p != null && chave.equals(p.chave)) {
            return p.valor;
        }
        //talvez se nao encontrar desse jeito, posso tentar percorer um por um
        throw new IndexOutOfBoundsException("chave inexistente");
    }

    public void remove(K chave) {
        int linha = (chave.hashCode() & 0x7fffffff) % tab.length;
        Par p = tab[linha];
        if (p != null && chave.equals(p.chave)) {
            tab[linha] = null;
            len--;
        } else {
            //talvez se nao encontrar desse jeito, posso tentar percorer um por um
            throw new IndexOutOfBoundsException("chave inexistente");
        }
    }

    public boolean contem(K chave) {
        int linha = (chave.hashCode() & 0x7fffffff) % tab.length;
        Par p = tab[linha];
        if (p == null) return false;
        return chave.equals(p.chave);
    }

    public boolean esta_vazia() {
        return len == 0;
    }

    public V obtem_ou_default(K chave, V defval) {
        int linha = (chave.hashCode() & 0x7fffffff) % tab.length;

        Par p = tab[linha];
        if (p != null && chave.equals(p.chave)) {
            return p.valor;
        }
        return defval;
    }

    public ListaSequencial<K> chaves() {
        ListaSequencial<K> lk = new ListaSequencial<>();
        for(Par p : tab){
            if(p != null) lk.adiciona(p.chave);
        }
        return lk;
    }

    public ListaSequencial<V> valores() {
        ListaSequencial<V> lv = new ListaSequencial<>();
        for(Par p : tab){
            if(p != null) lv.adiciona(p.valor);
        }
        return lv;
    }

    public ListaSequencial<Par> items() {
        ListaSequencial<Par> lp = new ListaSequencial<>();
        for(Par p : tab){
            if(p != null) lp.adiciona(p);
        }
        return lp;
    }

    public int comprimento() {
        return len;
    }

    public void limpa() {
        // remove os pares
        tab = inicia_tabela(defcap);
        len = 0;
    }

}