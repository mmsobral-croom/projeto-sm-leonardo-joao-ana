package esd;

import sm.Produto;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class ListaSequencial<T> implements Iterable<T>{
    public class ListaSequencialIterator implements Iterator <T>{
        ListaSequencial<T> l;

        int pos = 0;

        public ListaSequencialIterator(ListaSequencial<T> l) {
            this.l = l;
        }
        @Override
        public boolean hasNext() {
            // a iteração não chegou ao fim se a posição atual da iteração for menor que o comprimento do Deque
            return pos < l.comprimento();
        }

        @Override
        public T next() {
            // somente pode obter o próximo valor da iteração se ela não chegou ao fim !
            if (! hasNext()) {
                throw new NoSuchElementException("fim da iteração");
            }
            // retorna o próximo valor da iteração, e também incrementa a posição atual da iteração
            return l.obtem(pos++);
        }
    }

    T[] area;
    int len = 0;
    final int defcap = 8;

    @SuppressWarnings("unchecked")
    public ListaSequencial() {
        area = (T[])new Object[defcap];
    }

    @Override
    public Iterator<T> iterator()
    {
        // cria um iterador. Note que este Deque é passado como parãmetro na criação do iterador
        return new ListaSequencialIterator(this);
    }

    @SuppressWarnings("unchecked")
    void expande() {
        // isto será usado quando for necessário expandir a capacidade da lista
        T[] novo = (T[])new Object[2*area.length];

        //passo 2: mover valores para novo array
        for(int i=0; i < len; i++){
            novo[i] = this.area[i];
        }
        area = novo;
    }

    public boolean esta_vazia() {
        // retorna true se lista estiver vazia, ou false caso contrário
        return len == 0;
    }

    public int capacidade() {
        // retorna um inteiro que representa a capacidade da lista
        return area.length;
    }

    public int tamanho() {
        return len;
    }

    public void adiciona(T elemento) {
        // adiciona um valor ao final da lista
        if(len >= area.length) {
            expande();
        }
        area[len++] = elemento;
    }

    public void adiciona_produto_unico(Produto elemento) {
        boolean encontrou = false;

        for (int pos = 0; pos < this.len; pos++) {
            Produto p = (Produto) this.area[pos];
            encontrou = p.getEan() == elemento.getEan();

            if (encontrou) break;
        }

        if (!encontrou) this.adiciona((T) elemento);
    }

    public void insere_ordenado(Comparable valor) {
        // insere o valor na lista, preservando seu ordenamento
        if(len >= area.length) {
            expande();
        }
        if(! this.esta_vazia()) {
            int i = 0;
            while(i < len && valor.compareTo(area[i]) > 0) {
                i++;
            }
            insere(i, (T) valor);
        }else{
            insere(0, (T) valor);
        }
    }

    public void ordena_bubble() {
        // ordena a lista com algum bom algoritmo !
        // Bubble Sort
        for(int j = len-1; j > 1; j--){
            //for(int j=i+1; j < len-1; j++){
            for(int i = 0; i < j; i++){
                Comparable valor = (Comparable) area[i];
                if(valor.compareTo(area[i+1]) > 0){
                    T aux = (T) area[i+1];
                    area[i+1] = (T) area[i];
                    area[i] = aux;
                }
            }
        }
    }

    public int busca_binaria(Comparable valor) {
        // procura o valor dentro da lista usando busca binária
        // retorna a posição onde se encontra, ou -1 caso não exista
        int metade = 0;
        int inicio = 0;
        int fim = len-1;
        while(inicio <= fim){
            metade = (fim+inicio)/2;
            if(valor.compareTo(area[metade]) == 0){
                return metade;
            }else if(valor.compareTo(area[metade]) > 0){
                inicio = metade + 1;
            }else{
                fim = metade - 1;
            }
        }
        return -1;
    }



    public void insere_rapido(int indice, T elemento) {
        // insere "elemento" na posição "indice"
        // o valor que estava na posição "indice" deve ser movido para o final da lista
        // valores válidos de "indice" vão de 0 até comprimento da lista (inclusive)
        // se "indice" for o comprimento da lista, insere faz o mesmo que "adiciona"
        // dispara IndexOutOfBoundsException se "indice" for inválido

        if(indice < 0 || indice > len ) {
            throw new IndexOutOfBoundsException("Posição invalida");
        }

        if(len >= area.length) {
            expande();
        }

        if (indice < len) {
            area[len] = area[indice];
        }
        area[indice] = elemento;
        len++;
    }

    public void insere(int indice, T elemento) {
        // insere "elemento" na posição "indice"
        // o valor que estava na posição "indice" deve ser movido para o final da lista
        // valores válidos de "indice" vão de 0 até comprimento da lista (inclusive)
        // se "indice" for o comprimento da lista, insere faz o mesmo que "adiciona"
        // dispara IndexOutOfBoundsException se "indice" for inválido

        if(indice < 0 || indice > len ) {
            throw new IndexOutOfBoundsException("Posição invalida");
        }

        if(len >= area.length) {
            expande();
        }

        if(indice < len) {
            for(int i = len; i > indice; i--){
                area[i] = area[i-1];
            }
        }
        area[indice] = elemento;

        len++;
    }

    public T remove(int indice) {
        // remove um valor da posição indicada pelo parâmetro "indice"
        // move para essa posição o valor que está no final da lista
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        if(indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("Posição invalida");
        }
//        if(indice > len - 1) {
//            throw new IndexOutOfBoundsException("Sem elemento nessa posição");
//        }
        T ret = area[indice];
        for(int i = indice; i < len-1; i++){
            area[i] = area[i+1];
        }
        area[len-1] = null;
        len--;
        return ret;
    }

    public void remove_rapido(int indice) {
        // remove um valor da posição indicada pelo parâmetro "indice"
        // move para essa posição o valor que está no final da lista
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida

        if(indice < 0 || indice > len) {
            throw new IndexOutOfBoundsException("Posição invalida");
        }
        if(indice > len - 1) {
            throw new IndexOutOfBoundsException("Sem elemento nessa posição");
        }
        area[indice] = area[len-1];
        area[len-1] = null;
        len--;
    }

    public T remove_ultimo(){
        if(this.esta_vazia()) throw new IndexOutOfBoundsException("lista vazia");
        len--;
        T ret = area[len];
        area[len] = null;
        return ret;
    }

    public T ultimo(){
        if(this.esta_vazia()) throw new IndexOutOfBoundsException("lista vazia");
        return  area[len-1];
    }

    public T primeiro(){
        if(this.esta_vazia()) throw new IndexOutOfBoundsException("lista vazia");
        return area[0];
    }

    public int procura(T valor) {
        // retorna um inteiro que representa aposição onde valor foi encontrado pela primeira vez (contando do início da lista)
        // retorna -1 se não o encontrar !
        for(int i = 0; i < len; i++) {
            if(area[i].equals(valor)) {
                return i;
            }
        }
        return -1;
    }

    public T obtem(int indice) {
        // retorna o valor armazenado na posição indica pelo parâmetro "indice"
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        if(indice < 0 || indice > len - 1) {
            throw new IndexOutOfBoundsException("Posição invalida");
        }
        return area[indice];
    }

    public void substitui(int indice, T valor) {
        // armazena o valor na posição indicada por "indice", substituindo o valor lá armazenado atualmente
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        if(indice < 0 || indice > len - 1) {
            throw new IndexOutOfBoundsException("Posição invalida");
        }
        area[indice] = valor;
    }

    public int comprimento() {
        // retorna um inteiro que representa o comprimento da lista (quantos valores estão armazenados)
        return len;
    }

    public void limpa() {
        // esvazia a lista
        for(int i = 0; i < len; i++) {
            area[i] = null;
        }
        len = 0;
    }

    public boolean esta_ordenada() {
        // implemente aqui o método
        if(len == 0) return true;
        if(len == 1) return true;
        for(int i = 0; i < len-1; i++){
            Comparable valor = (Comparable) area[i];
            if(valor.compareTo(area[i+1]) > 0) return false;
        }
        return true;
    };

    public void embaralha(){
        if(len < 2) return;
        Random gerador = new Random();

        for(int i = len-1; i > 0; i--){
            int x = gerador.nextInt(0, i+1);
            T aux = this.obtem(x);
            this.substitui(x, obtem(i));
            this.substitui(i, aux);
        }
    }

    //@Override
    public boolean equals(ListaSequencial<T> outra) {
        return false;
    }

//    se pos2 - pos1 > 1 então
//    meio <- pos1 + (pos2 - pos1)/2
//    ordena_mescla(sequencia, pos1, meio)
//    ordena_mescla(sequencia, meio, pos2)
//
//    mescla(sequencia, pos1, meio, pos2)
//    fim se
//

    public void ordena(){
        if(len < 2) return;
        ordena(0, len);
    }
    public void ordena(int pos1, int pos2){
        if(pos2-pos1 < 2) return;
        int meio = pos1 + (pos2 - pos1) / 2;
        ordena(pos1, meio);
        ordena(meio, pos2);
        mescla(pos1, meio, pos2);
    }

    //    algoritmo mescla
//    Entradas: sequencia, pos1, meio, pos2 (posição inicial, mediana, e final do trecho da sequência a ser ordenado)
//    // cria um arranjo auxiliar vazio
//    aux <- novo arranjo de tamanho (pos2 - pos1)
//    i <- pos1
//    j <- meio
//    k <- 0
//
//    // mescla as duas metades da sequencia, entre pos1 e pos2
//    // cada uma dessas metades já está ordenada
//    enquanto i < meio e j < pos2 faça
    //    se sequencia[i] <= sequencia[j] então
    //    aux[k] <- sequencia[i]
    //    i <- i + 1
    //    senão
    //    aux[k] <- sequencia[j]
    //    j <- j + 1
    //    fim se
    //    k <- k + 1
//    fim enquanto
//
//    enquanto i < meio faça
//    aux[k] <- sequencia[i]
//    i <- i + 1
//    k <- k + 1
//    fim enquanto
//    enquanto j < pos2 faça
//    aux[k] <- sequencia[j]
//    j <- j + 1
//    k <- k + 1
//    fim enquanto
//
//    para i de pos1 até pos2 faça
//    sequencia[i] <- aux[i - pos1]
//    fim para
//    fim

    public void mescla(int pos1, int meio, int pos2){
        ListaSequencial<T> aux = new ListaSequencial<>();

        int i = pos1;
        int j = meio;

        while(i < meio && j < pos2){
            Comparable v1 = (Comparable) this.obtem(i);
            Comparable v2 = (Comparable) this.obtem(j);

            if(v1.compareTo(v2) <= 0){
                aux.adiciona(this.obtem(i));
                i++;
            } else {
                aux.adiciona(this.obtem(j));
                j++;
            }
        }

        while(i < meio){
            aux.adiciona(this.obtem(i));
            i++;
        }

        while(j < pos2){
            aux.adiciona(this.obtem(j));
            j++;
        }

        for(int k = 0; k < aux.comprimento(); k++){
            this.substitui(pos1 + k, (T) aux.obtem(k));
        }
    }

    public <T> ListaSequencial<T> interseccao(ListaSequencial<T> l1) {
        ListaSequencial<T> resultado = new ListaSequencial<>();

        if (l1.esta_vazia() || this.esta_vazia()) {
            return resultado;
        }
        l1.ordena();
        this.ordena();

        int i = 0;
        int j = 0;

        // percorrer as listas simultaneamente O(n)
        while (i < l1.comprimento() && j < this.comprimento()) {
            T elem1 = l1.obtem(i);
            T elem2 = (T) this.obtem(j);

            Comparable v1 = (Comparable) elem1;
            Comparable v2 = (Comparable) elem2;

            int comparacao = v1.compareTo(v2);

            if (comparacao == 0) {
                if (resultado.esta_vazia() || !resultado.ultimo().equals(elem1)) {
                    resultado.adiciona(elem1);
                }
                i++;
                j++;
            } else if (comparacao < 0) {
                // elemento de l1 é menor, avança em l1 para tentar achar um maior
                i++;
            } else {
                // elemento de l2 é menor, avança em l2
                j++;
            }
        }

        return resultado;
    }



}