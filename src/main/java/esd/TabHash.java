package esd;


import java.lang.reflect.Array;

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
            Par _outro = (Par)outro;
            return chave.equals(_outro.chave);
        }
    }

    Par[] tab;
    int len = 0; // quantos pares estao armazenados
    final int defcap = 31;

    public TabHash() {
        // dimensiona a tabela
        tab = inicia_tabela(defcap);
    }


    @SuppressWarnings("unchecked")
    Par[] inicia_tabela(int linhas) {
        Par[] nova = (Par[]) Array.newInstance(Par.class, linhas);

        // inicia a lista com essa quantidade de linhas

        return nova;
    }

    public void adiciona(K chave, V valor) {
        int hash = Math.abs(chave.hashCode()) % tab.length;
        int originalHash = hash;

        while (tab[hash] != null) {
            if (tab[hash].chave.equals(chave)) {
                tab[hash].valor = valor; // Atualiza o valor se a chave já existe
                return;
            }
            hash = (hash + 1) % tab.length; // Sondagem linear
            if (hash == originalHash) {
                // Tabela cheia ou loop infinito, precisa redimensionar
                // Por simplicidade, vamos lançar uma exceção por enquanto
                throw new IllegalStateException("Tabela hash cheia");
            }
        }
        tab[hash] = new Par(chave, valor);
        len++;
    }

    public V obtem(K chave) {
        int hash = Math.abs(chave.hashCode()) % tab.length;
        int originalHash = hash;

        while (tab[hash] != null) {
            if (tab[hash].chave.equals(chave)) {
                return tab[hash].valor;
            }
            hash = (hash + 1) % tab.length;
            if (hash == originalHash) {
                break; // Percorreu a tabela inteira e não encontrou
            }
        }
        throw new IndexOutOfBoundsException("chave inexistente");
    }

    public void remove(K chave) {
        int hash = Math.abs(chave.hashCode()) % tab.length;
        int originalHash = hash;

        while (tab[hash] != null) {
            if (tab[hash].chave.equals(chave)) {
                tab[hash] = null; // Marca como removido
                len--;

                // Reorganizar a tabela para evitar problemas com sondagem linear
                // Re-adiciona os elementos subsequentes que foram deslocados
                int currentHash = (hash + 1) % tab.length;
                while (tab[currentHash] != null && Math.abs(tab[currentHash].chave.hashCode()) % tab.length != currentHash) {
                    Par tempPar = tab[currentHash];
                    tab[currentHash] = null;
                    len--;
                    adiciona(tempPar.chave, tempPar.valor);
                    currentHash = (currentHash + 1) % tab.length;
                }
                return;
            }
            hash = (hash + 1) % tab.length;
            if (hash == originalHash) {
                break; // Percorreu a tabela inteira e não encontrou
            }
        }
        throw new IndexOutOfBoundsException("chave inexistente");
    }

    public boolean contem(K chave) {
        int hash = Math.abs(chave.hashCode()) % tab.length;
        int originalHash = hash;

        while (tab[hash] != null) {
            if (tab[hash].chave.equals(chave)) {
                return true;
            }
            hash = (hash + 1) % tab.length;
            if (hash == originalHash) {
                break; // Percorreu a tabela inteira e não encontrou
            }
        }
        return false;
    }

    public boolean esta_vazia() {
        return len == 0;
    }

    public V obtem_ou_default(K chave, V defval) {
        try {
            return obtem(chave);
        } catch (IndexOutOfBoundsException e) {
            return defval;
        }
    }

    public ListaSequencial<K> chaves() {
        ListaSequencial<K> lk = new ListaSequencial<>();
        for (Par par : tab) {
            if (par != null) {
                lk.adiciona(par.chave);
            }
        }
        return lk;
    }

    public ListaSequencial<V> valores() {
        ListaSequencial<V> lv = new ListaSequencial<>();
        for (Par par : tab) {
            if (par != null) {
                lv.adiciona(par.valor);
            }
        }
        return lv;
    }

    public ListaSequencial<Par> items() {
        ListaSequencial<Par> lp = new ListaSequencial<>();
        for (Par par : tab) {
            if (par != null) {
                lp.adiciona(par);
            }
        }
        return lp;
    }

    public int comprimento() {
        return len;
    }

    public void limpa() {
        // remove os pares
        len = 0;
    }
}