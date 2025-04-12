import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Arquivo{
    private int numeroRegistroTot = 1024;

    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;



    public Arquivo(String nomearquivo) {
        try {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void copiaArquivo(Arquivo arquivoOrigem){
        Registro reg = new Registro();
        seekArq(0);
        arquivoOrigem.seekArq(0);
        while (!arquivoOrigem.eof()) {
            reg.leDoArq(arquivoOrigem.getFile());
            reg.gravaNoArq(arquivo);
        }
    }
    public RandomAccessFile getFile() {
        return arquivo;
    }
    public void truncate(long pos) {
        try{
            arquivo.setLength(pos * Registro.length());
        } catch (IOException exc)
        { }
    }
    public boolean eof() {
        boolean retorno = false;
        try
        {
            if (arquivo.getFilePointer() == arquivo.length())
                retorno = true;
        } catch (IOException e)
        { }
        return (retorno);
    }
    public void seekArq(int pos) {
        try
        {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e)
        { }
    }
    public int filesize() {
        try {
            return (int) arquivo.length() / Registro.length();
        } catch (IOException e){}
        return 0;
    }


    public void initComp() {
        comp = 0;
    }
    public void initMov() {
        mov = 0;
    }
    public int getComp() {
        return this.comp;
    }
    public int getMov() {
        return this.mov;
    }
    public void geraArquivoOrdenado(){
        Registro reg = new Registro();
        truncate(0);
        for(int i=1; i <= numeroRegistroTot; i++)
        {
            reg.setCodigo(i);
            reg.gravaNoArq(arquivo);
        }
    }
    public void geraArquivoInvertido ()
    {
        Registro reg = new Registro();
        truncate(0);
        for(int i=numeroRegistroTot; i >0 ; i--)
        {
            reg.setCodigo(i);
            reg.gravaNoArq(arquivo);
        }
    }
    public void geraArquivoRandomico()
    {
        Registro registro = new Registro();
        truncate(0);
        for(int i=1; i <= numeroRegistroTot; i++)
        {
            registro.setCodigo((int) (Math.random() * (numeroRegistroTot * 2) + 1));
            registro.gravaNoArq(arquivo);
        }
    }
    public int getNumRegistros() {
        return numeroRegistroTot;
    }




    public Registro clone(Registro reg){
        return new Registro(reg.getCodigo());
    }

    public void exibe(){
        seekArq(0);
        Registro reg = new Registro();
        for(int i=0;i<filesize();i++){
            reg.leDoArq(getFile());
            System.out.print(reg.getCodigo()+" ");
        }
        System.out.println("");
    }

    public void gravarString(String frase){
        try {
            arquivo.writeUTF(frase);
        } catch (IOException e) {
            System.out.println("ERRO: " + e);
        }
    }
    public void limparArquivo() throws IOException {
        arquivo.setLength(0); // Define o tamanho do arquivo como 0
    }
    public void inserirNoFinal(Registro reg) throws IOException {
        arquivo.seek(arquivo.length());
        reg.gravaNoArq(arquivo);
    }
    public void exibirRegistros() throws IOException {
        arquivo.seek(0); // Volta ao início do arquivo
        while (arquivo.getFilePointer() < arquivo.length()) {
            Registro reg = new Registro();
            reg.leDoArq(arquivo);
            System.out.print(reg.getCodigo() + " "); // Exibe os códigos em uma linha
        }
        System.out.println(); // Pula uma linha após exibir todos os códigos
    }
    //****************************************************************************METODOS DE ORDENAÇÃO****************************************************************************//
    //---------------------------INSERÇÃO DIRETA---------------------------//
    public void insercaoDireta(){
        int TL=filesize(), pos;
        Registro regAnt = new Registro();
        Registro regAtual = new Registro();

        for(int i=1; i<TL; i++){
            seekArq(i-1);
            regAnt.leDoArq(arquivo);
            regAtual.leDoArq(arquivo);//pegou a proxima pos pq o leDoArq pula para o proximo
            pos=i;

            while(pos > 0 && regAtual.getCodigo() < regAnt.getCodigo()){
                comp++;
                seekArq(pos--);
                regAnt.gravaNoArq(arquivo);
                mov++;
                if(pos > 0){
                    seekArq(pos-1);
                    regAnt.leDoArq(arquivo);
                }
            }
            if(pos != i){
                seekArq(pos);
                regAtual.gravaNoArq(arquivo);
                mov++;
            }
        }
    }

    //---------------------------INSERÇÃO BINÁRIA---------------------------//
    private int buscaBinaria (int num,int TL){
        int ini = 0, fim = TL, meio = TL/2;
        Registro reg = new Registro();
        seekArq(meio);
        reg.leDoArq(arquivo);

        while(ini < fim && reg.getCodigo() != num)
        {
            if(num > reg.getCodigo())
                ini=meio+1;
            else
                fim=meio-1;

            meio=(ini+fim)/2;
            seekArq(meio);
            reg.leDoArq(arquivo);
            comp+=2;
        }
        if(num>reg.getCodigo())
            return meio+1;
        return meio;
    }

    public void insercaoBinaria(){
        int TL= filesize(), pos;
        Registro reg = new Registro();
        Registro regAux = new Registro();

        for(int i=1; i<TL; i++){
            seekArq(i);
            reg.leDoArq(arquivo);
            pos=buscaBinaria(reg.getCodigo(), i);

            for(int j=i; j>pos; j--){
                seekArq(j-1);
                regAux.leDoArq(arquivo);
                regAux.gravaNoArq(arquivo);
                mov++; //movimentou o arquivo
                comp++;
            }
            if(pos<i){
                seekArq(pos);
                reg.gravaNoArq(arquivo);
                mov++; //movimentou o arquivo
            }
            comp++;
        }

    }

    //---------------------------SELEÇÃO DIRETA---------------------------//
    public void selecaoDireta(){
        Registro pi = new Registro();
        Registro pj = new Registro();
        int pos,menor;
        int TL = filesize();

        for(int i=0; i<TL-1; i++){
            seekArq(i);
            pi.leDoArq(arquivo);
            menor=pi.getCodigo();
            pos=i;
            for(int j=i+1; j<TL; j++){
                pj.leDoArq(arquivo);
                if(pj.getCodigo() < menor){
                    menor = pj.getCodigo();
                    pos = j;
                }
                comp++;
            }
            // le os dados
            seekArq(i);
            pi.leDoArq(arquivo);
            seekArq(pos);
            pj.leDoArq(arquivo);

            // grava os dados
            seekArq(i);
            pj.gravaNoArq(arquivo);
            seekArq(pos);
            pi.gravaNoArq(arquivo);
            mov+=2;
        }
    }


    //---------------------------BOLHA---------------------------//
    public void bolha(){
        int TL=filesize();
        boolean flag = true;
        Registro reg = new Registro();
        Registro reg2 = new Registro();

        while(TL > 0 && flag){
            flag = false;
            for(int i=0; i<TL-1; i++){
                seekArq(i);
                reg.leDoArq(arquivo);
                reg2.leDoArq(arquivo);

                if(reg.getCodigo() > reg2.getCodigo()){
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg.gravaNoArq(arquivo);
                    flag=true;
                    mov+=2;
                }
                comp++;
            }
            TL--;
        }
    }

    //---------------------------SHAKE---------------------------//
    public void shake(){
        boolean flag = true;
        int ini=0, fim=filesize()-1;
        Registro reg = new Registro();
        Registro regAux = new Registro();

        while(ini < fim && flag){
            flag = false;
            for(int i=ini; i<fim; i++){
                seekArq(i);
                reg.leDoArq(arquivo);
                seekArq(i+1);
                regAux.leDoArq(arquivo);
                if(reg.getCodigo() > regAux.getCodigo()){
                    seekArq(i);
                    regAux.gravaNoArq(arquivo);
                    seekArq(i+1);
                    reg.gravaNoArq(arquivo);
                    flag=true;
                    mov += 2;
                }
                comp++;
            }
            fim--;
            if(flag){
                flag=false;
                for(int i=fim; i>ini; i--){
                    seekArq(i);
                    reg.leDoArq(arquivo);
                    seekArq(i-1);
                    regAux.leDoArq(arquivo);
                    if(reg.getCodigo() < regAux.getCodigo()){
                        seekArq(i);
                        regAux.gravaNoArq(arquivo);
                        seekArq(i-1);
                        reg.gravaNoArq(arquivo);
                        flag=true;
                        mov+=2;
                    }
                    comp++;
                }
                ini++;
            }
        }
    }

    //---------------------------SHELL---------------------------//
    public int encontarDist(int TL){
        int dist = 1;
        while(dist < TL)
            dist=dist*3+1;
        return dist/3;
    }

    public void shell(){
        int dist, TL=filesize(), pos;
        Registro regAux = new Registro();
        Registro regPosDist = new Registro();

        dist=encontarDist(TL);
        while(dist > 0){
            for(int i=dist; i<TL; i++){
                seekArq(i);
                regAux.leDoArq(arquivo);
                pos=i;

                seekArq(pos-dist);
                regPosDist.leDoArq(arquivo);
                comp++;
                while(pos >= dist && regAux.getCodigo() < regPosDist.getCodigo()){
                    seekArq(pos);
                    regPosDist.gravaNoArq(arquivo);
                    pos=pos-dist;
                    mov++;
                    if(pos >= dist){
                        seekArq(pos-dist);
                        regPosDist.leDoArq(arquivo);
                        comp++;
                    }
                }
                seekArq(pos);
                regAux.gravaNoArq(arquivo);
                mov++;
            }
            dist=dist/3;
        }
    }

    //---------------------------HEAP---------------------------//
    public void heap(){
        int TL=filesize(), pai, FE, FD, maiorF;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();

        while(TL > 1){
            for(pai=TL/2-1; pai>=0; pai--){
                FE=2*pai+1;
                FD=FE+1;
                maiorF=FE;

                if(FD < TL){
                    seekArq(FE);
                    reg1.leDoArq(arquivo);
                    reg2.leDoArq(arquivo);
                    if(reg2.getCodigo() > reg1.getCodigo())
                        maiorF=FD;
                    comp++;
                }

                seekArq(maiorF);
                reg1.leDoArq(arquivo);
                seekArq(pai);
                reg2.leDoArq(arquivo);
                if(reg1.getCodigo() > reg2.getCodigo()){
                    seekArq(maiorF);
                    reg2.gravaNoArq(arquivo);
                    seekArq(pai);
                    reg1.gravaNoArq(arquivo);
                    mov+=2;
                }
                comp++;
            }
            // Troca a raiz (maior elemento) com o último elemento do heap
            seekArq(0);
            reg1.leDoArq(arquivo);
            seekArq(TL - 1);
            reg2.leDoArq(arquivo);

            seekArq(0);
            reg2.gravaNoArq(arquivo);
            seekArq(TL - 1);
            reg1.gravaNoArq(arquivo);
            mov += 2;
            TL--;
        }

    }

    //---------------------------QUICK SEM PIVO RECURSIVO---------------------------//
    public void quickSemPivo_Recursivo(){
        quickSP(0, filesize()-1);
    }
    private void  quickSP(int ini, int fim){
        int i=ini, j=fim;
        Registro regI = new Registro();
        Registro regJ = new Registro();

        while(i < j){
            seekArq(i);
            regI.leDoArq(arquivo);
            seekArq(j);
            regJ.leDoArq(arquivo);
            comp++;

            while(i < j && regI.getCodigo() <= regJ.getCodigo()){
                i++;
                seekArq(i);
                regI.leDoArq(arquivo);
                comp+=2;
            }
            seekArq(i);
            regJ.gravaNoArq(arquivo);
            seekArq(j);
            regI.gravaNoArq(arquivo);
            mov+=2;

            while(i < j && regJ.getCodigo() >= regI.getCodigo()){
                j--;
                seekArq(j);
                regJ.leDoArq(arquivo);
                comp+=2;
            }
            seekArq(i);
            regJ.gravaNoArq(arquivo);
            seekArq(j);
            regI.gravaNoArq(arquivo);
            mov+=2;
        }
        if(ini < i-1)
            quickSP(ini, i-1);
        if(j+1 < fim)
            quickSP(j+1, fim);
        comp+=2;
    }

    //---------------------------QUICK SEM PIVO INTERATIVO---------------------------//
    public void quickSemPivo_Interativo(){
        int ini=0, fim=filesize()-1;
        int i, j;
        Registro regI = new Registro();
        Registro regJ = new Registro();
        PilhaArq pilha = new PilhaArq();

        pilha.push(ini);
        pilha.push(fim);
        while(!pilha.isEmpty()){
            fim=pilha.pop();
            ini=pilha.pop();
            i=ini;
            j=fim;

            while(i < j){
                seekArq(i);
                regI.leDoArq(arquivo);
                seekArq(j);
                regJ.leDoArq(arquivo);

                while(i < j && regI.getCodigo() <= regJ.getCodigo()){
                    comp++;
                    i++;
                    seekArq(i);
                    regI.leDoArq(arquivo);
                }
                seekArq(i);
                regJ.gravaNoArq(arquivo);
                seekArq(j);
                regI.gravaNoArq(arquivo);
                mov+=2;

                while(i < j && regJ.getCodigo() >= regI.getCodigo()){
                    comp++;
                    j--;
                    seekArq(j);
                    regJ.leDoArq(arquivo);
                }
                seekArq(i);
                regJ.gravaNoArq(arquivo);
                seekArq(j);
                regI.gravaNoArq(arquivo);
                mov+=2;
            }
            if(ini < i-1){
                pilha.push(ini);
                pilha.push(i-1);
            }
            if(j+1 < fim){
                pilha.push(j+1);
                pilha.push(fim);
            }
        }
    }

    //---------------------------QUICK COM PIVO RECURSIVO---------------------------//
    public void quickComPivo_Recursivo(){
        quickCP(0, filesize()-1);
    }
    private void quickCP(int ini, int fim){
        int i=ini, j=fim;
        Registro regI = new Registro();
        Registro regJ = new Registro();
        int pivo=(ini+fim)/2;
        Registro regPivo = new Registro();
        seekArq(pivo);
        regPivo.leDoArq(arquivo);

        while(i < j){
            seekArq(i);
            regI.leDoArq(arquivo);
            seekArq(j);
            regJ.leDoArq(arquivo);

            while(regI.getCodigo() < regPivo.getCodigo()){
                comp++;
                i++;
                seekArq(i);
                regI.leDoArq(arquivo);
            }
            while( regJ.getCodigo() > regPivo.getCodigo()){
                comp++;
                j--;
                seekArq(j);
                regJ.leDoArq(arquivo);
            }
            if(i <= j){
                seekArq(i);
                regJ.gravaNoArq(arquivo);
                seekArq(j);
                regI.gravaNoArq(arquivo);
                mov+=2;
                i++;
                j--;
            }
        }
        if(ini < i)
            quickSP(ini, j);
        if(j < fim)
            quickSP(i, fim);
    }

    //---------------------------QUICK COM PIVO INTERATIVO---------------------------//
    public void quickComPivo_Interativo(){
        Registro regI = new Registro();
        Registro regJ = new Registro();
        Registro regPivo = new Registro();
        PilhaArq pilha = new PilhaArq();

        int ini=0, fim=filesize()-1, i, j;

        pilha.push(ini);
        pilha.push(fim);
        while(!pilha.isEmpty()){
            fim=pilha.pop();
            ini=pilha.pop();
            i=ini;
            j=fim;

            int pivo=(ini+fim)/2;
            seekArq(pivo);
            regPivo.leDoArq(arquivo);

            while(i < j){
                seekArq(i);
                regI.leDoArq(arquivo);
                seekArq(j);
                regJ.leDoArq(arquivo);

                while(regI.getCodigo() < regPivo.getCodigo()){
                    comp++;
                    i++;
                    seekArq(i);
                    regI.leDoArq(arquivo);
                }
                comp++;
                while( regJ.getCodigo() > regPivo.getCodigo()){
                    comp++;
                    j--;
                    seekArq(j);
                    regJ.leDoArq(arquivo);
                }
                comp++;
                if(i <= j){
                    seekArq(i);
                    regJ.gravaNoArq(arquivo);
                    seekArq(j);
                    regI.gravaNoArq(arquivo);
                    mov+=2;
                    i++;
                    j--;
                }
            }
            if(ini < j){
                pilha.push(ini);
                pilha.push(j);
            }
            if(i < fim){
                pilha.push(i);
                pilha.push(fim);
            }
        }
    }

    //---------------------------MERGE 1 IMPLEMENTAÇÃO---------------------------//
    public void mergePrimeiraImple() throws IOException {
        int TL=filesize(), seq=1;
        Arquivo arquivo1 = new Arquivo("arq1.dat");
        Arquivo arquivo2 = new Arquivo("arq2.dat");

        while(seq < TL){
            arquivo1.truncate(0);
            arquivo2.truncate(0);
            particao(arquivo1, arquivo2);
            fusao_1(arquivo1, arquivo2, seq);
            seq=seq*2;
        }
        arquivo1.close();
        arquivo2.close();
    }
    private void particao(Arquivo arquivo1, Arquivo arquivo2) throws IOException {
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        int TL=filesize();

        int meio=TL/2;
        arquivo1.seekArq(0);
        arquivo2.seekArq(0);
        for(int i=0; i<meio; i++){
            seekArq(i);
            reg1.leDoArq(arquivo);
            reg1.gravaNoArq(arquivo1.getFile());
            seekArq(i+meio);
            reg2.leDoArq(arquivo);
            reg2.gravaNoArq(arquivo2.getFile());
        }
    }
    private void fusao_1(Arquivo arquivo1, Arquivo arquivo2, int seq){
        int i=0, j=0, k=0, aux=seq;
        int TL=filesize();
        Registro regI = new Registro();
        Registro regJ = new Registro();
        truncate(0);


        while(k < TL){
            while(i < seq  && j < seq ){
                arquivo1.seekArq(i);
                regI.leDoArq(arquivo1.arquivo);
                arquivo2.seekArq(j);
                regJ.leDoArq(arquivo2.arquivo);

                comp++;
                if(regI.getCodigo() < regJ.getCodigo()){
                    regI.gravaNoArq(arquivo);
                    i++; k++;
                }else{
                    regJ.gravaNoArq(arquivo);
                    j++; k++;
                }
                mov++;
            }
            while(i < seq && i < TL/2){
                arquivo1.seekArq(i);
                regI.leDoArq(arquivo1.arquivo);
                regI.gravaNoArq(arquivo);
                i++; k++;
                mov++;
            }
            while(j < seq && j < TL/2){
                arquivo2.seekArq(j);
                regJ.leDoArq(arquivo2.arquivo);
                regJ.gravaNoArq(arquivo);
                j++; k++;
                mov++;
            }
            seq+=aux;
        }

    }

    //---------------------------MERGE 2 IMPLEMENTAÇÃO RECURSIVO---------------------------//
    public void mergeSegundaImple_Recursivo(){
        Arquivo arqAux = new Arquivo("arqAux.dat");
        merge2(0, filesize()-1, arqAux);
    }
    private void merge2(int esq, int dir, Arquivo arqAux){
        if(esq < dir){
            int meio=(esq+dir)/2;
            merge2(esq, meio, arqAux);
            merge2(meio+1, dir, arqAux);
            fusao_2(esq, meio, meio+1, dir, arqAux);
        }
    }
    private void fusao_2(int ini1, int fim1, int ini2, int fim2, Arquivo arqAux){
        int i=ini1, j=ini2, k=0;
        Registro regI = new Registro();
        Registro regJ = new Registro();
        int TL=filesize();

        arqAux.truncate(0);
        while(i <= fim1 && j <= fim2){
            seekArq(i);
            regI.leDoArq(arquivo);
            seekArq(j);
            regJ.leDoArq(arquivo);

            comp++;
            if(regI.getCodigo() < regJ.getCodigo()){
                regI.gravaNoArq(arqAux.arquivo);
                i++;
            }else{
                regJ.gravaNoArq(arqAux.arquivo);
                j++;
            }
            mov++;
        }
        while(i <= fim1){
            seekArq(i);
            regI.leDoArq(arquivo);
            regI.gravaNoArq(arqAux.arquivo);
            i++;
            mov++;
        }
        while(j < fim2){
            seekArq(j);
            regJ.leDoArq(arquivo);
            regJ.gravaNoArq(arqAux.arquivo);
            j++;
            mov++;
        }

        seekArq(ini1);
        arqAux.seekArq(0);
        while(!arqAux.eof()){
            regI.leDoArq(arqAux.arquivo);
            regI.gravaNoArq(arquivo);
            mov++;
        }
    }

    //---------------------------MERGE 2 IMPLEMENTAÇÃO INTERATIVO---------------------------//
    public void mergeSegundaImple_Interativo(){
        Arquivo arqAux = new Arquivo("arqAux.dat");
        PilhaArq pilha1 = new PilhaArq();
        PilhaArq pilha2 = new PilhaArq();
        int ini1, fim1, meio, ini2, fim2;

        pilha1.push(0);
        pilha1.push(filesize()-1);
        while(!pilha1.isEmpty()){
            fim1=pilha1.pop();
            ini1=pilha1.pop();
            if(ini1<fim1){
                meio=(ini1+fim1)/2;
                pilha1.push(ini1);
                pilha1.push(meio);
                pilha2.push(ini1);
                pilha2.push(meio);

                pilha1.push(meio+1);
                pilha1.push(fim1);
                pilha2.push(meio+1);
                pilha2.push(fim1);
            }
        }
        while(!pilha2.isEmpty()){
            fim2=pilha2.pop();
            ini2=pilha2.pop();
            fim1=pilha2.pop();
            ini1=pilha2.pop();
            fusao_2(ini1, fim1, ini2, fim2, arqAux); //fusao é a mesma do recursivo
        }
    }

    //---------------------------COUNTING---------------------------//
    public void counting(){
        int TL=filesize(), max, i;
        Registro regAux = new Registro();

        //1° Encontrar o valor máximo
        seekArq(0);
        regAux.leDoArq(arquivo);
        max=regAux.getCodigo();
        for(i=1; i<TL; i++){
            seekArq(i);
            regAux.leDoArq(arquivo);
            if(regAux.getCodigo() > max)
                max = regAux.getCodigo();
        }
        //2° Criar o array de contagem
        int[] contagem = new int[max + 1];
        //3° Contar a frequência dos elementos
        for(i=0; i<TL; i++){
            seekArq(i);
            regAux.leDoArq(arquivo);
            contagem[regAux.getCodigo()]++;
        }
        //4° Calcular as posições corretas
        for(i=1; i<=max; i++)
            contagem[i] += contagem[i - 1];
        //5° Criar arquivos temporários para frequência e saída
        Arquivo freqArq = new Arquivo("frequencia.dat");
        Arquivo saidaArq = new Arquivo("saida.dat");
        //6° Montar o arquivo de frequência
        montaFrequencia(freqArq, max, contagem);
        //7° Montar o arquivo de saída ordenado
        montaSaida(freqArq, saidaArq, TL);
        //8° Copiar o conteúdo do arquivo de saída para o arquivo original
        for(i=0;i<TL;i++){
            saidaArq.seekArq(i);
            regAux.leDoArq(saidaArq.getFile());
            seekArq(i);
            regAux.gravaNoArq(arquivo);
            mov++;
        }
    }
    private void montaFrequencia(Arquivo freqArq, int max, int[] contagem){
        Registro reg = new Registro();
        freqArq.truncate(0);
        //escreve as frequências no arquivo de frequência
        for(int i=0;i<=max;i++){
            reg.setCodigo(contagem[i]);
            freqArq.seekArq(i);
            reg.gravaNoArq(freqArq.getFile());
            mov++;
        }
    }
    private void montaSaida(Arquivo freqArq, Arquivo saidaArq, int TL){
        Registro reg = new Registro();
        int posFreq, posSaida;

        for(int i=TL-1; i>=0; i--){
            seekArq(i);
            reg.leDoArq(arquivo);
            int valor = reg.getCodigo();
            posFreq = valor;

            freqArq.seekArq(posFreq);
            reg.leDoArq(freqArq.getFile());
            posSaida = reg.getCodigo() - 1;

            //decrementa a frequência
            reg.setCodigo(reg.getCodigo() - 1);
            freqArq.seekArq(posFreq);
            reg.gravaNoArq(freqArq.getFile());

            //escreve o valor no arquivo de saída
            saidaArq.seekArq(posSaida);
            reg.setCodigo(valor);
            reg.gravaNoArq(saidaArq.getFile());
            mov += 2;
        }
    }

    //---------------------------BUCKET---------------------------//
    public void bucket(){
        int maior, TL=filesize(), quatBalde = 4, intervalo, pos[] = {0};
        //1° inicialização
        Arquivo balde1 = new Arquivo("balde1.dat");
        Arquivo balde2 = new Arquivo("balde2.dat");
        Arquivo balde3 = new Arquivo("balde3.dat");
        Arquivo balde4 = new Arquivo("balde4.dat");
        Registro reg = new Registro();
        Registro regAux = new Registro();

        //2° reinicialização dos arquivos temporarios
        balde1.truncate(0);
        balde2.truncate(0);
        balde3.truncate(0);
        balde4.truncate(0);
        balde1.seekArq(0);
        reg.gravaNoArq(balde1.getFile());
        balde2.seekArq(0);
        reg.gravaNoArq(balde2.getFile());
        balde3.seekArq(0);
        reg.gravaNoArq(balde3.getFile());
        balde4.seekArq(0);
        reg.gravaNoArq(balde4.getFile());
        mov+=4;

        //3° distribuição dos registros nos baldes
        if(TL>0){
            maior=BuscaMaior(TL);
            intervalo=maior/quatBalde;
            for(int i=0;i<TL;i++){
                seekArq(i);
                reg.leDoArq(arquivo);
                if(reg.getCodigo() <= intervalo){
                    balde1.seekArq(0);
                    regAux.leDoArq(balde1.getFile());
                    balde1.seekArq(regAux.getCodigo()+1);
                    reg.gravaNoArq(balde1.getFile());
                    regAux.setCodigo(regAux.getCodigo()+1);
                    balde1.seekArq(0);
                    regAux.gravaNoArq(balde1.getFile());
                    comp++;
                }
                else if(reg.getCodigo() <= 2*intervalo){
                    balde2.seekArq(0);
                    regAux.leDoArq(balde2.getFile());
                    balde2.seekArq(regAux.getCodigo()+1);
                    reg.gravaNoArq(balde2.getFile());
                    regAux.setCodigo(regAux.getCodigo()+1);
                    balde2.seekArq(0);
                    regAux.gravaNoArq(balde2.getFile());
                    comp+=2;
                }
                else if(reg.getCodigo() <= 3*intervalo){
                    balde3.seekArq(0);
                    regAux.leDoArq(balde3.getFile());
                    balde3.seekArq(regAux.getCodigo()+1);
                    reg.gravaNoArq(balde3.getFile());
                    regAux.setCodigo(regAux.getCodigo()+1);
                    balde3.seekArq(0);
                    regAux.gravaNoArq(balde3.getFile());
                    comp+=3;
                }
                else{
                    balde4.seekArq(0);
                    regAux.leDoArq(balde4.getFile());
                    balde4.seekArq(regAux.getCodigo()+1);
                    reg.gravaNoArq(balde4.getFile());
                    regAux.setCodigo(regAux.getCodigo()+1);
                    balde4.seekArq(0);
                    regAux.gravaNoArq(balde4.getFile());
                    comp+=3;
                }
                mov++;
            }

            //4° ordenação dos baldes
            balde1.seekArq(0);
            regAux.leDoArq(balde1.getFile());
            balde1.insercaoDireta();

            balde2.seekArq(0);
            regAux.leDoArq(balde2.getFile());
            balde2.insercaoDireta();

            balde3.seekArq(0);
            regAux.leDoArq(balde3.getFile());
            balde3.insercaoDireta();

            balde4.seekArq(0);
            regAux.leDoArq(balde4.getFile());
            balde4.insercaoDireta();

            //5° concatenação dos baldes
            truncate(0);
            gravarBucket(balde1, pos);
            gravarBucket(balde2, pos);
            gravarBucket(balde3, pos);
            gravarBucket(balde4, pos);
        }
    }
    public void gravarBucket(Arquivo balde, int pos[]){
        Registro reg = new Registro();
        seekArq(pos[0]);
        balde.seekArq(1);
        while (!balde.eof()) {
            reg.leDoArq(balde.getFile());
            reg.gravaNoArq(arquivo);
            pos[0]++;
            mov++;
        }
    }
    private int BuscaMaior(int TL){
        int maior=-1;
        Registro reg = new Registro();
        for(int i=0;i<TL;i++){
            seekArq(i); reg.leDoArq(arquivo);
            if(reg.getCodigo()>maior)
                maior = reg.getCodigo();
            comp++;
        }
        return maior;
    }

    //OU

    public void bucketAlternativo() throws IOException{
        int TL=filesize(), pos, tlBalde;
        int min=percorrerMin(TL), max=percorrerMax(TL);
        int baldes=(int)Math.ceil(Math.sqrt(max-min+1));//Math.ceil pega o valor aproximado pra cima. É aqui que pega a quantidade de baldes
        int intervalo=(max-min+1)/baldes; //Intervalo de cada balde
        Arquivo[] arquivos = new Arquivo[baldes];

        //1° inicializa cada balde
        for(int i=0; i<baldes; i++)
            arquivos[i] = new Arquivo("balde"+i+"dat");
        //2° posiciona cada registro no seu devido balde
        seekArq(0);
        for(int i=0; i<TL; i++){
            Registro reg = new Registro();
            reg.leDoArq(arquivo);
            pos=Math.min((reg.getCodigo()-min)/intervalo, baldes-1);
            arquivos[pos].insercaoFim(reg);
        }
        //3° percorre cada arquivo e faz a ordenação
        for(Arquivo arquivo : arquivos)
            arquivo.insercaoDireta();
        //4° grava os baldes ordenados no arquivo principal
        truncate(0);
        for(int i=0; i<arquivos.length; i++){
            tlBalde=arquivos[i].filesize();
            arquivos[i].seekArq(0);
            for(int j=0; j<tlBalde; j++){
                Registro reg = new Registro();
                reg.leDoArq(arquivos[i].arquivo);//le no arquivo do balde
                reg.gravaNoArq(arquivo);//grava no arquivo principal
            }
        }
        //5° fecha todos os baldes
        for(int i=0; i<baldes; i++)
            arquivos[i].arquivo.close();
        for(int i=0; i<baldes; i++){
            File balde = new File("balde"+i+"dat");
            balde.delete();
        }
    }
    public int percorrerMin(int TL){
        Registro reg = new Registro();
        seekArq(0);
        reg.leDoArq(arquivo);
        int min=reg.getCodigo(), i=1;

        while(i < TL){
            seekArq(i);
            reg.leDoArq(arquivo);
            if(reg.getCodigo() < min)
                min=reg.getCodigo();
            i++;
        }
        return min;
    }
    public int percorrerMax(int TL){
        Registro reg = new Registro();
        seekArq(0);
        reg.leDoArq(arquivo);
        int max=reg.getCodigo(), i=1;

        while(i < TL){
            seekArq(i);
            reg.leDoArq(arquivo);
            if(reg.getCodigo() > max)
                max=reg.getCodigo();
            i++;
        }
        return max;
    }
    public void insercaoFim(Registro reg) throws IOException{
        arquivo.seek(arquivo.length());
        reg.gravaNoArq(arquivo);
    }

    //---------------------------RADIX---------------------------//
    public void radix() throws IOException {
        int max=encontrarMax(), TL=filesize();

        for(int digito=1; digito<=max; digito*=10){
            int[] couting =new int[10];

            seekArq(0);
            for(int i=0; i<TL; i++){
                Registro reg = new Registro();
                reg.leDoArq(arquivo);
                couting[(reg.getCodigo()/digito)%10]++;
                comp++;
            }

            for(int i=1; i<couting.length; i++) {
                couting[i] += couting[i - 1];
                comp++;
            }
            Arquivo arquivoNovo = new Arquivo("ordem.dat");
            int i=TL-1;
            while(i >= 0){
                Registro reg = new Registro();
                seekArq(i--);
                reg.leDoArq(arquivo);
                arquivoNovo.seekArq(couting[(reg.getCodigo()/digito)%10]-1);
                reg.gravaNoArq(arquivoNovo.arquivo);
                couting[(reg.getCodigo()/digito)%10]--;
                mov+=2;
            }

            close();
            arquivoNovo.close();
            File original = new File("copia.dat");
            File ordenado = new File("ordem.dat");
            original.delete();
            ordenado.renameTo(original);
            arquivo = new Arquivo("copia.dat").getFile();
        }
    }

    public void close() throws IOException {
        if (arquivo != null) {
            arquivo.close(); // Fecha o arquivo
        }
    }
    public int encontrarMax(){
        Registro reg = new Registro();
        seekArq(0);
        reg.leDoArq(arquivo);
        int max=reg.getCodigo(), i=1, TL=filesize();

        while(i < TL){
            seekArq(i);
            reg.leDoArq(arquivo);
            if(reg.getCodigo() > max)
                max=reg.getCodigo();
            i++;
            comp++;
        }
        return max;
    }


    //---------------------------COMB---------------------------//
    public void comb(){
        Registro regAtual = new Registro();
        Registro regProx = new Registro();

        //1° encontrar o tamanho da lista e atibuli-lo ao gap
        int TL=filesize(), gap;
        boolean trocou=true;

        gap=TL;
        while(gap!=1 || trocou){
            //2° dentro do loop tem que atualizar o gap
            gap=proximoGap(gap);
            trocou=false;
            //3° pegar o NO depois do gap
            int i=0;
            while(i+gap < TL){
                seekArq(i);
                regAtual.leDoArq(arquivo);
                seekArq(i+gap);
                regProx.leDoArq(arquivo);

                if(regAtual.getCodigo() > regProx.getCodigo()){
                    seekArq(i);
                    regProx.gravaNoArq(arquivo);
                    seekArq(i+gap);
                    regAtual.gravaNoArq(arquivo);
                    mov+=2;
                    trocou=true;
                }
                comp++;
                i++;
            }
        }
    }
    public int proximoGap(int gap){
        gap=(gap * 10)/13;
        return Math.max(gap, 1); //para garantir que o gap seja pelo menos 1
    }

    //---------------------------GNOME---------------------------//
    public void gnome(){
        int TL=filesize();
        Registro regAnt = new Registro();
        Registro regAtual = new Registro();

        int i=1;
        while(i < TL){
            seekArq(i-1);
            regAnt.leDoArq(arquivo);
            seekArq(i);
            regAtual.leDoArq(arquivo);

            comp++;
            if(regAtual.getCodigo() >= regAnt.getCodigo())
                i++;
            else{
                seekArq( i-1);
                regAtual.gravaNoArq(arquivo);
                seekArq(i);
                regAnt.gravaNoArq(arquivo);
                mov += 2;

                if(i > 1)
                    i--;
            }
        }
    }

    //---------------------------TIM---------------------------//
    public void timInsert(int ini, int fim){
        Registro aux = new Registro();
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        int TL, i=ini+1, pos;

        if(fim<0)
            TL = filesize();
        else
            TL = fim;

        while(i < TL){
            seekArq(i);
            aux.leDoArq(arquivo);
            pos=buscaBinaria(aux.getCodigo(), i);
            for(int j=i; j>pos; j--){
                seekArq(j-1); reg1.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                seekArq(j-1); reg2.gravaNoArq(arquivo);
                reg1.gravaNoArq(arquivo);
                mov+=2;
            }
            i++;
        }
    }
    public void tim(){
        int div=32, TL=filesize(), dir, meio;
        Arquivo aux = new Arquivo("tim.dat");
        aux.truncate(TL);
        for(int i=0; i<TL; i+=div){
            if(i+div < TL)
                timInsert(i, i+div);
            else
                timInsert(i, TL);
        }
        for(int tam=div; tam<TL; tam*=2){
            for(int esq=0; esq<TL; esq+=2*tam){
                if(esq+2*tam < TL)
                    dir=esq+2*tam-1;
                else
                    dir=TL-1;
                meio=(esq+dir)/2;
                fusao_2(esq, meio, meio+1, dir, aux);
            }
        }
    }



    //usado no main de teste!
    public void fecharArquivo() throws IOException {
        arquivo.close();
    }
}


