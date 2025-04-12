public class MetodosOrd {
    private No inicio;
    private No fim;

    public No getInicio(){
        return inicio;
    }
    public void setInicio(No inicio) {
        this.inicio = inicio;
    }
    public No getFim(){
        return fim;
    }
    public void setFim(No fim){
        this.fim = fim;
    }


    public void inicializar(){
        //setInicio(null);
        inicio = null;
        fim = null;
    }
    public int tamanhoLista(){
        No aux = inicio;
        int i;
        for (i = 0; aux != null; i++)
            aux = aux.getProx();
        return i;
    }
    public No getNo(int pos){
        No aux=inicio;
        int i=0;
        while(aux != null && i < pos){
            i++;
            aux=aux.getProx();
        }
        return aux;
    }
    public void inserirFim(int info){
        No nova = new No(info);
        if(inicio == null)
            inicio=fim=nova;
        else{
            fim.setProx(nova);
            fim=nova;
        }
    }
    public void imprimirLista(No inicio) {
        No atual=inicio;
        while(atual != null) {
            System.out.print(atual.getInfo() + " ");
            atual = atual.getProx();
        }
        System.out.println();
    }
    //****************************************************************************METODOS DE ORDENAÇÃO****************************************************************************//
    //---------------------------INSERÇÃO DIRETA---------------------------//
    public void insercaoDireta(){
        No i=inicio.getProx(), pos;
        int aux;

        while(i != null){
            aux=i.getInfo();
            pos=i;

            while(pos != inicio && aux < pos.getAnt().getInfo()){
                pos.setInfo(pos.getAnt().getInfo());
                pos=pos.getAnt();
            }
            pos.setInfo(aux);
            i=i.getProx();
        }
    }

    //---------------------------INSERÇÃO BINÁRIA---------------------------//
    private No buscaBinaria(int chave, int TL){
        int init=0, fim=TL, meio=fim/2;

        No aux=inicio;
        for(int i=0; i!=meio; i++)
            aux=aux.getProx();

        while(init < fim && chave != aux.getInfo()){
            if(chave > aux.getInfo())
                init=meio+1;
            else
                fim=meio-1;
            meio=(init+fim)/2;
            aux=inicio;
            for(int i=0; i!=meio; i++)
                aux=aux.getProx();
        }
        if(chave > aux.getInfo())
            return aux.getProx();
        return aux;
    }

    public void insercaoBinaria(){
        No pa, pos;
        int info, i;

        for (pa = inicio.getProx(), i= 1; pa != null; pa = pa.getProx(), i++) {
            info = pa.getInfo();
            pos = buscaBinaria (info, i) ;
            for (No aux = pa;aux != pos; aux = aux.getAnt())
                aux.setInfo(aux.getAnt().getInfo());
            pos.setInfo(info);
      }
    }

    //---------------------------SELEÇÃO DIRETA---------------------------//
    public void selecaoDireta(){
        No i=inicio, menor, j;
        int aux;

        while(i != null){
            menor=i;
            j=i.getProx();

            while(j != null){
                if(j.getInfo() < menor.getInfo())
                    menor=j;
                j=j.getProx();
            }

            aux=i.getInfo();
            i.setInfo(menor.getInfo());
            menor.setInfo(aux);
            i = i.getProx();
        }
    }

    //---------------------------BOLHA---------------------------// --->REVER, NÃO TEM FLAG
    public void bolha(){
        int  TL=tamanhoLista(), copia;

        while(TL > 0){
            No aux=inicio;
            No aux2=aux.getProx();

            for(int i = 0; i < TL-1; i++){
                if(aux.getInfo() > aux2.getInfo()){
                    copia=aux.getInfo();
                    aux.setInfo(aux2.getInfo());
                    aux2.setInfo(copia);
                }
                aux=aux2;
                aux2=aux.getProx();
            }
            TL--;
        }
    }

    //---------------------------SHAKE---------------------------//
    public void shake(){
        int TL = tamanhoLista(), copia;
        boolean flag=true;

        while(TL > 0 && flag){
            flag=false;

            No aux=inicio;
            No aux2=aux.getProx();
            for(int i = 0; i < TL - 1; i++){
                if(aux.getInfo() > aux2.getInfo()){
                    copia=aux.getInfo();
                    aux.setInfo(aux2.getInfo());
                    aux2.setInfo(copia);
                    flag=true;
                }
                aux=aux2;
                aux2=aux2.getProx();
            }
            TL--;

            if(flag){
                flag=false;

                aux2=aux;
                aux=aux2.getAnt();
                for(int i = TL; i > 0; i--) {
                    if(aux.getInfo() > aux2.getInfo()) {
                        copia=aux.getInfo();
                        aux.setInfo(aux2.getInfo());
                        aux2.setInfo(copia);
                        flag=true;
                    }
                    aux2=aux;
                    aux=aux.getAnt();
                }
            }
        }
    }

    //---------------------------SHELL---------------------------//
    public void shell(){
        int TL=tamanhoLista();
        int dist=encontrarDist(TL);
        int aux, pos;

        while(dist > 0){
            for(int i=dist; i<TL; i++){
                aux=getNo(i).getInfo();
                pos=i;
                while(pos >= dist && aux < getNo(pos-dist).getInfo()){
                    getNo(pos).setInfo(getNo(pos-dist).getInfo());
                    pos=pos-dist;
                }
                getNo(pos).setInfo(aux);
            }
            dist=dist/3;
        }
    }
    private int encontrarDist(int TL){
        int dist=1;
        while(dist < TL)
            dist=dist*3+1;
        dist=dist/3;
        return dist;
    }

    //---------------------------HEAP---------------------------//
    public void heap(){
        int TL=tamanhoLista();
        int pai, filhoEsq, filhoDir, filhoMaior, temp;
        for(int TL2=TL; TL2 >1; TL2--){
            pai=TL2/2-1;
            while(pai >= 0){
                filhoEsq=pai*2+1;
                filhoDir=filhoEsq+1;
                filhoMaior=filhoEsq;
                if(filhoDir < TL2 && getNo(filhoDir).getInfo() > getNo(filhoEsq).getInfo())
                    filhoMaior=filhoDir;
                if(getNo(filhoMaior).getInfo() > getNo(pai).getInfo()){
                    temp=getNo(filhoMaior).getInfo();
                    getNo(filhoMaior).setInfo(getNo(pai).getInfo());
                    getNo(pai).setInfo(temp);
                }
                pai--;
            }
            temp=getNo(0).getInfo();
            getNo(0).setInfo(getNo(TL2-1).getInfo());
            getNo(TL2-1).setInfo(temp);
        }
    }

    //---------------------------QUICK SEM PIVO RECURSIVO---------------------------//
    public void quickSemPivo_Recursivo(){ //RECURSIVO
        int TL=tamanhoLista();
        quickSP(0, TL-1);
    }
    private void quickSP(int ini, int fim){
        int i=ini, j=fim, aux;
        while(i < j){
            while(i < j && getNo(i).getInfo() <= getNo(j).getInfo())
                i++;
            aux=getNo(i).getInfo();
            getNo(i).setInfo(getNo(j).getInfo());
            getNo(j).setInfo(aux);

            while(i < j && getNo(j).getInfo() >= getNo(i).getInfo())
                j--;
            aux=getNo(i).getInfo();
            getNo(i).setInfo(getNo(j).getInfo());
            getNo(j).setInfo(aux);
        }
        if(ini < i-1)
            quickSP(ini, i-1);
        if(j+1 < fim)
            quickSP(j+1, fim);
    }

    //---------------------------QUICK SEM PIVO INTERATIVO---------------------------//
    public void quickSemPivo_Interativo(){
        Pilha pilha = new Pilha();
        int ini=0, fim=tamanhoLista()-1;
        int aux, i, j;

        pilha.push(ini);
        pilha.push(fim);
        while(!pilha.isEmpty()){
            fim=pilha.pop();
            ini=pilha.pop();
            i=ini;
            j=fim;

            while(i < j){
                while(i < j && getNo(i).getInfo() <= getNo(j).getInfo())
                    i++;
                aux=getNo(i).getInfo();
                getNo(i).setInfo(getNo(j).getInfo());
                getNo(j).setInfo(aux);

                while(i < j && getNo(j).getInfo() >= getNo(i).getInfo())
                    j--;
                aux=getNo(i).getInfo();
                getNo(i).setInfo(getNo(j).getInfo());
                getNo(j).setInfo(aux);
            }
            if(ini < i-1){
                pilha.push(ini);
                pilha.push(i - 1);
            }
            if(j+1 < fim){
                pilha.push(j+1);
                pilha.push(fim);
            }
        }
    }

    //---------------------------QUICK COM PIVO---------------------------//
    public void quickComPivo_Recursivo(){
        int TL=tamanhoLista();
        quickCP(0, TL-1);
    }
    private void quickCP(int ini, int fim){
        int i=ini, j=fim, aux;
        int pivo=getNo((ini+fim)/2).getInfo();
        while(i < j){
            while(getNo(i).getInfo() < pivo)
                i++;
            while(getNo(j).getInfo() > pivo)
                j--;
            if(i <= j){
                aux=getNo(i).getInfo();
                getNo(i).setInfo(getNo(j).getInfo());
                getNo(j).setInfo(aux);
                i++;
                j--;
            }
            if(ini < i)
                quickCP(ini, j);
            if(j < fim)
                quickCP(i, fim);
        }
    }

    //---------------------------QUICK COM PIVO INTERATIVO---------------------------//
    public void quickComPivo_Interativo(){
        Pilha pilha = new Pilha();
        int ini=0, fim=tamanhoLista()-1;
        int aux, i, j, pivo;

        pilha.push(ini);
        pilha.push(fim);
        while(!pilha.isEmpty()){
            fim=pilha.pop();
            ini=pilha.pop();
            i=ini;
            j=fim;
            pivo=getNo((ini+fim)/2).getInfo();
            while(i < j){
                while(getNo(i).getInfo() < pivo)
                    i++;
                while(getNo(j).getInfo() > pivo)
                    j--;
                if(i <= j){
                    aux=getNo(i).getInfo();
                    getNo(i).setInfo(getNo(j).getInfo());
                    getNo(j).setInfo(aux);
                    i++;
                    j--;
                }
            }
            if (i < fim){
                pilha.push(i);
                pilha.push(fim);
            }
            if (ini < j){
                pilha.push(ini);
                pilha.push(j);
            }
        }
    }

    //---------------------------MERGE 1 IMPLEMENTAÇÃO---------------------------//
    //tem que ser rodado com 8, 16, 32... elementos!
    public void mergePrimeiraImple(){
        int TL=tamanhoLista();
        MetodosOrd lista1 = new MetodosOrd();
        MetodosOrd lista2 = new MetodosOrd();
        int seq=1;

        while(seq < TL){
            lista1.inicializar();
            lista2.inicializar();
            particao_1(lista1, lista2);
            fusao_1(lista1, lista2, seq);
            seq=seq*2;
        }
    }
    private void particao_1(MetodosOrd lista1, MetodosOrd lista2){
        int TL=tamanhoLista();
        int meio=TL/2;

        for(int i=0; i<meio; i++){
            lista1.inserirFim(getNo(i).getInfo());
            lista2.inserirFim(getNo(i+meio).getInfo());
        }
    }
    public void fusao_1(MetodosOrd lista1, MetodosOrd lista2, int seq){
        int TL=tamanhoLista();
        int i=0, j=0, k=0, aux=seq;

        while(k < TL){
            while(i < seq && j < seq){
                if(lista1.getNo(i).getInfo() < lista2.getNo(j).getInfo())
                    getNo(k++).setInfo(lista1.getNo(i++).getInfo());
                else
                    getNo(k++).setInfo(lista2.getNo(j++).getInfo());
            }
            while(i < seq)
                getNo(k++).setInfo(lista1.getNo(i++).getInfo());
            while(j < seq)
                getNo(k++).setInfo(lista2.getNo(j++).getInfo());
            seq+=aux;
        }
    }

    //---------------------------MERGE 2 IMPLEMENTAÇÃO---------------------------//
    public void mergeSegundaImple(){
        int TL=tamanhoLista();
        merge2(0, TL-1);
    }

    private void merge2(int esq, int dir){
        if(esq < dir){
            int meio=(esq+dir)/2;
            merge2(esq, meio);
            merge2(meio+1, dir);
            fusao_2(esq, meio, meio+1, dir);
        }
    }
    private void fusao_2(int ini1, int fim1, int ini2, int fim2){
        MetodosOrd listaAux = new MetodosOrd();
        int i=ini1, j=ini2;

        while(i <= fim1 && j <= fim2){
            if(getNo(i).getInfo() < getNo(j).getInfo())
                listaAux.inserirFim(getNo(i++).getInfo());
            else
                listaAux.inserirFim(getNo(j++).getInfo());
        }
        while(i <= fim1)
            listaAux.inserirFim(getNo(i++).getInfo());
        while(j <= fim2)
            listaAux.inserirFim(getNo(j++).getInfo());
        No aux=listaAux.getInicio();
        for(int x=ini1; x<=fim2; x++){
            getNo(x).setInfo(aux.getInfo());
            aux=aux.getProx();
        }
    }

    //---------------------------COUNTING---------------------------//
    public void counting(){
        No lista;

        //1° encontrar o valor máximo
        int max=inicio.getInfo();
        No aux=inicio.getProx();
        while(aux != null){
            if(aux.getInfo() > max)
                max=aux.getInfo();
            aux=aux.getProx();
        }
        //2° criar o array de contagem
        int[] contagem = new int[max + 1];
        //3° contar a frequência dos elementos
        lista=inicio;
        while(lista != null){
            contagem[lista.getInfo()]++;
            lista=lista.getProx();
        }
        //4° calcular as posições corretas
        for (int i = 1; i <= max; i++) {
            contagem[i] += contagem[i - 1];
        }
        //5° reconstruir a lista encadeada ordenada
        No[] nosOrdenados = new No[contagem[max]]; // Array para armazenar os nós ordenados
        lista = inicio;
        while (lista != null) {
            int posicao = contagem[lista.getInfo()] - 1; // Posição correta do nó
            nosOrdenados[posicao] = lista;
            contagem[lista.getInfo()]--;
            lista=lista.getProx();
        }
        //Reconstruir a lista encadeada ordenada
        inicio = nosOrdenados[0]; // Atualiza o início da lista
        aux = inicio;
        for (int i = 1; i < nosOrdenados.length; i++) {
            aux.setProx(nosOrdenados[i]);
            aux = aux.getProx();
        }
        aux.setProx(null); // Finaliza a lista
    }

    //---------------------------BUCKET---------------------------//
    public void bucket(){
        int tamanho=tamanhoLista();
        int valorMin=percorrerMin(inicio), valorMax=percorrerMax(inicio);
        int numBaldes=(valorMax-valorMin)/tamanho;
        No[] baldes = new No[numBaldes];
        for(int i = 0; i < numBaldes; i++)
            baldes[i]= new No(0);
        No atual=inicio, prox;
        while(atual != null){
            prox=atual.getProx();
            atual.setProx(null);
            int seuBalde=(atual.getInfo()-valorMin)/tamanho;
            if(seuBalde >= numBaldes)
                seuBalde=numBaldes-1;
            No listaBalde=baldes[seuBalde];
            while(listaBalde.getProx() != null)
                listaBalde=listaBalde.getProx();
            listaBalde.setProx(atual);
            atual=prox;
        }
        for(int i=0; i<numBaldes; i++){
            No baldeAtual=baldes[i].getProx(); //o getProx serve para n pegar No ficticio
            selecaoDireta_Modificado(baldeAtual);
            baldes[i].setProx(baldeAtual);
        }
        No listaOrdenada=null;
        No fimOrdenada=null;
        for(int i=0; i<numBaldes; i++){
            No baldeAtual=baldes[i].getProx(); //o getProx serve para n pegar No ficticio
            if(baldeAtual != null){
                if(listaOrdenada == null)
                    listaOrdenada=baldeAtual;
                else
                    fimOrdenada.setProx(baldeAtual);
                while(baldeAtual.getProx() != null)
                    baldeAtual=baldeAtual.getProx();
                fimOrdenada=baldeAtual;
            }
        }
        inicio=listaOrdenada;
    }

    private int percorrerMin(No lista){
        int min=lista.getInfo();
        while(lista != null){
            if(lista.getInfo() < min)
                min=lista.getInfo();
            lista=lista.getProx();
        }
        return min;
    }
    private int percorrerMax(No lista){
        int max=lista.getInfo();
        while(lista != null){
            if(lista.getInfo() > max)
                max=lista.getInfo();
            lista=lista.getProx();
        }
        return max;
    }

    private void selecaoDireta_Modificado(No balde){
        No i=balde, menor, j;
        int aux;

        while(i != null){
            menor=i;
            j=i.getProx();

            while(j != null){
                if(j.getInfo() < menor.getInfo())
                    menor=j;
                j=j.getProx();
            }

            aux=i.getInfo();
            i.setInfo(menor.getInfo());
            menor.setInfo(aux);
            i = i.getProx();
        }
    }

    public No concatenarListas(No lista1, No lista2){
        No atual=lista1;
        while(atual.getProx() != null) {
            atual=atual.getProx();
        }
        atual.setProx(lista2);

        return lista1;
    }

    //---------------------------RADIX---------------------------//
    public void radix(){
        int maior=encontrarMaior(inicio);
         for(int i=1; maior/i>0; i*=10)
             coutingRadix(i);
    }
    private void coutingRadix(int aux){
        No lista;
        int i;
        int[] contagem = new int[10];

        lista=inicio;
        while(lista != null){
            contagem[(lista.getInfo()/aux)%10]++;
            lista=lista.getProx();
        }
        for (i = 1; i <= 9; i++) {
            contagem[i] += contagem[i - 1];
        }
        No[] nosOrdenados = new No[contagem[9]];
        int cont=contagem[9];
        while (cont > 0){
            lista=getNo(cont-1);
            cont--;
            int posicao = contagem[(lista.getInfo()/aux)%10] - 1;
            nosOrdenados[posicao] = lista;
            contagem[(lista.getInfo()/aux)%10]--;
        }
        inicio = nosOrdenados[0];
        No aux2 = inicio;
        for (i = 1; i < nosOrdenados.length; i++) {
            aux2.setProx(nosOrdenados[i]);
            aux2 = aux2.getProx();
        }
        aux2.setProx(null);
    }

    private int encontrarMaior(No lista){
        int max=lista.getInfo();
        while(lista != null){
            if(lista.getInfo() > max)
                max=lista.getInfo();
            lista=lista.getProx();
        }
        return max;
    }
    //---------------------------COMB---------------------------//
    public void comb(){
        //1° encontrar o tamanho da lista e atibuli-lo ao gap
        int gap=tamanhoLista();
        boolean trocou=true;

        while(gap != 1 || trocou){
            //2° dentro do loop tem que atualizar o gap
            gap=proximoGap(gap);
            trocou=false;

            //3° pegar o NO depois do gap
            //No anterior=null;
            No atual=inicio;
            No proximo=noAposGap(atual, gap);

            //4° entra no loop e verifica ate o null se o atual é maior que o proximo do gap, se for troca
            while(proximo != null){
                if(atual.getInfo() > proximo.getInfo()) {
                    //troca(anterior, atual, proximo);
                    troca(atual, proximo);
                    trocou = true;
                }

                atual = atual.getProx();
                proximo = noAposGap(atual, gap);
            }
        }
    }

    public void troca(No no1, No no2){
        int temp=no1.getInfo();
        no1.setInfo(no2.getInfo());
        no2.setInfo(temp);
    }

    public No noAposGap(No no, int gap){
        No atual=no;
        for(int i = 0; i < gap && atual != null; i++)
            atual=atual.getProx();
        return atual;
    }

    public int proximoGap(int gap){
        gap=(gap * 10)/13;
        return Math.max(gap, 1); //para garantir que o gap seja pelo menos 1
    }

    //---------------------------GNOME---------------------------//
    public void gnome(){
        No anterior=inicio;
        No atual=anterior.getProx();

        while(atual != null){
            if(atual.getInfo() >= anterior.getInfo()){
                anterior=atual;
                atual=atual.getProx();
            }else{
                int temp=atual.getInfo();
                atual.setInfo(anterior.getInfo());
                anterior.setInfo(temp);

                if(anterior.getAnt() != null){
                    atual = anterior;
                    anterior = anterior.getAnt();
                }else
                    atual = atual.getProx();

            }
        }
    }

    //---------------------------TIM---------------------------//
    public void Tim(){
        int div = 32, TL = tamanhoLista(), dir, meio;
        for(int i=0; i<TL; i+=div) {
            if(i+div < TL)
                timInsercao(i, i + div);
            else
                timInsercao(i, TL);
        }
        for(int tam=div; tam<TL; tam*=2){
            for(int esq=0; esq<TL; esq+=2*tam){
                if(esq+2*tam < TL)
                    dir=esq+2*tam-1;
                else
                    dir=TL-1;
                meio=(esq + dir)/2;
                fusao_2(esq, meio, meio + 1, dir);
            }
        }
    }
    private void timInsercao(int ini, int fim){
        int TL = fim < 0 ? tamanhoLista() : fim;

        for(int i=ini+1; i<TL; i++){
            int key=getNo(i).getInfo();
            int pos=buscaBinaria(ini, i-1, key);  // Busca binária para encontrar a posição de inserção
            int j=i;

            while(j > pos){
                int aux=getNo(j-1).getInfo();
                getNo(j).setInfo(aux);
                j--;
            }
            getNo(pos).setInfo(key);
        }
    }
    private int buscaBinaria(int ini, int fim, int key){
        int low=ini, high=fim;
        while(low <= high){
            int mid=(low+high)/2;
            if (getNo(mid).getInfo() == key)
                return mid;
            else if(getNo(mid).getInfo() < key)
                low=mid+1;
            else
                high=mid-1;
        }
        return low;
    }


}
