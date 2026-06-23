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

    ListaSequencial<Par>[] tab;
    int len = 0; // quantos pares estao armazenados
    final int defcap = 31;
    static double FatorCarga = 0.75;

    public TabHash() {
        // dimensiona a tabela
        tab = inicia_tabela(defcap);
    }


    @SuppressWarnings("unchecked")
    ListaSequencial<Par>[] inicia_tabela(int linhas) {
        ListaSequencial<Par>[] nova = (ListaSequencial<Par>[]) Array.newInstance(ListaSequencial.class, linhas);

        // inicia a lista com essa quantidade de linhas
        for (int i = 0; i < linhas; i++){
            nova[i] = new ListaSequencial<>();
        }

        return nova;
    }

    void expande(){
        var old = tab;
        tab = inicia_tabela(2*tab.length);
        len = 0;

        for(ListaSequencial<Par> pares: old){
            if(pares != null){
                for(Par p : pares){
                    if(p.valor != null) adiciona(p.chave, p.valor);
                }
            }
        }
        // 1.  Expande a tabela: como é formada por um array de ListaSequencial, deve-se criar
        // uma novo array contendo o dobro de linhas (listas vazias) da tabela atual

        // 2. para cada par da tabela atual, deve-se recalcular o hash de
        // sua chave, e adicioná-lo à lista na linha correspondente no novo array

        // 3. ao final, substituir o array atual pelo novo array
    }

    double FatorCarga(){
        double k = len;
        double m = tab.length;
        //System.out.println(m);
        return k / m;
    }

    public void adiciona(K chave, V valor) throws IndexOutOfBoundsException {
        //calcular o hash de chave e com ele o numero da linha
        //FatorCarga = k/m
        if(FatorCarga() >= FatorCarga) expande();

        int linha = Math.abs(chave.hashCode()) % tab.length;

        //verifica se existe um par contendo esta chave
        //se exister, ele esta na linha da tabela correspondente as hash

        ListaSequencial<Par> pares = tab[linha];
        if (pares != null) {
            for(Par p: pares) {
                if (chave.equals(p.chave)) {
                    p.valor = valor;
                    return;
                }
            }
            pares.adiciona(new Par(chave, valor));
            len++;
        }else{
            pares = new ListaSequencial<>();
            pares.adiciona(new Par(chave, valor));
            len++;
        }
    }

    public V obtem(K chave) {
        int linha = Math.abs(chave.hashCode()) % tab.length;

        ListaSequencial<Par> pares = tab[linha];

        if(pares == null) return null;
        //throw new IndexOutOfBoundsException("chave inexistente");

        if (pares != null) {
            for(Par p: pares) {
                if (chave.equals(p.chave)) {
                    return p.valor;
                }
            }
        }
        return null;
        //talvez se nao encontrar desse jeito, posso tentar percorer um por um
        //throw new IndexOutOfBoundsException("chave inexistente");
    }

    public void remove(K chave) {
        int linha = Math.abs(chave.hashCode()) % tab.length;
        ListaSequencial<Par> pares = tab[linha];

        //if(pares == null) throw new IndexOutOfBoundsException("chave inexistente");

        for(int i = 0; i < pares.comprimento(); i++){
            Par p = pares.obtem(i);
            if (p.valor != null && chave.equals(p.chave)) {
                pares.remove(i);
                len--;
                return;
            }
        }
        throw new IndexOutOfBoundsException("chave inexistente");
    }

    public boolean contem(K chave) {
        int linha = Math.abs(chave.hashCode()) % tab.length;
        ListaSequencial<Par> pares = tab[linha];
        for(Par p: pares){
            if (p.valor != null && chave.equals(p.chave)) {
                return true;
            }
        }
        return false;
    }

    public boolean esta_vazia() {
        return len == 0;
    }

    public V obtem_ou_default(K chave, V defval) {
        int linha = Math.abs(chave.hashCode()) % tab.length;

        ListaSequencial<Par> pares = tab[linha];
        for(Par p: pares){
            if (p.valor != null && chave.equals(p.chave)) {
                return p.valor;
            }
        }
        return defval;
    }

    public ListaSequencial<K> chaves() {
        ListaSequencial<K> lk = new ListaSequencial<>();


        for(ListaSequencial<Par> pares : tab){
            for(Par p : pares){
                if(p != null) lk.adiciona(p.chave);
            }
        }
        return lk;
    }

    public ListaSequencial<V> valores() {
        ListaSequencial<V> lv = new ListaSequencial<>();
        for(ListaSequencial<Par> pares : tab){
            for(Par p : pares){
                if(p != null) lv.adiciona(p.valor);
            }
        }
        return lv;
    }

    public ListaSequencial<Par> items() {
        ListaSequencial<Par> lp = new ListaSequencial<>();
        for(ListaSequencial<Par> pares : tab){
            for(Par p : pares){
                if(p != null) lp.adiciona(p);
            }
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

    public int linhas(){
        return tab.length;
    }
}