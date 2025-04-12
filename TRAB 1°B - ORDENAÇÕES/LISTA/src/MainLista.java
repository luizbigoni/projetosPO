import java.io.IOException;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class MainLista {
    public static void main(String[] args){
        //for(int i=0; i < 100; i++){
            Random random = new Random();
            Set<Integer> valoresUnicos = new HashSet<>(); // Armazena valores únicos
            // Cria o primeiro nó com um valor aleatório único
            int valor = random.nextInt(100);
            valoresUnicos.add(valor);
            No inicio = new No(valor);
            No atual = inicio;
            // Adiciona mais nós à lista com valores aleatórios únicos
            int quantidadeNos = 32;
            while(valoresUnicos.size() < quantidadeNos){
                valor = random.nextInt(100);
                if(valoresUnicos.add(valor)){ // Adiciona apenas se o valor for único
                    No novoNo = new No(valor);
                    atual.setProx(novoNo);
                    novoNo.setAnt(atual);
                    atual = novoNo;
                }
            }

            // Ordena a lista
            MetodosOrd lista = new MetodosOrd();
            lista.setInicio(inicio);

            System.out.println("Lista antes da ordenação:");
            imprimirLista(lista.getInicio());


            //lista.insercaoDireta(); //--->OK
            //lista.insercaoBinaria(); //--->OK
            //lista.selecaoDireta(); //--->OK
            //lista.bolha(); //--->OK
            //lista.shake(); //--->OK
            //lista.shell(); //--->OK
            //lista.heap(); //--->OK
            //lista.quickSemPivo_Recursivo(); //--->OK
            //lista.quickSemPivo_Interativo(); //--->OK
            //lista.quickComPivo_Recursivo(); //--->OK
            //lista.quickComPivo_Interativo(); //--->OK
            //lista.mergePrimeiraImple(); //--->OK
            //lista.mergeSegundaImple(); //--->OK

            //lista.counting(); //--->OK
            //lista.bucket(); //--->OK
            //lista.radix(); //--->OK
            //lista.comb(); //--->OK
            //lista.gnome(); //--->OK
            //lista.Tim(); //--->OK

            //if(verificaOrdenado()){
                System.out.println("Lista após a ordenação:");
                imprimirLista(lista.getInicio());
            //}

        //}
    }


    public static void imprimirLista(No inicio) {
        No atual=inicio;
        while(atual != null) {
            System.out.print(atual.getInfo() + " ");
            atual = atual.getProx();
        }
        System.out.println();
    }
}
