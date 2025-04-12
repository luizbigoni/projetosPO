import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {
            // Cria um arquivo de registros aleatórios
            Arquivo arq = new Arquivo("registros.dat");

            // Limpa o arquivo antes de inserir novos registros
            arq.limparArquivo();

            // Preenche o arquivo com 10 registros com códigos únicos
            System.out.println("Criando arquivo com 10 registros aleatórios...");
            Set<Integer> codigosUnicos = new HashSet<>();
            Random random = new Random();

            while (codigosUnicos.size() < 32) {
                int codigo = random.nextInt(100); // Gera um código aleatório entre 0 e 99
                if (codigosUnicos.add(codigo)) { // Adiciona apenas se o código for único
                    Registro reg = new Registro(codigo);
                    arq.inserirNoFinal(reg);
                }
            }

            // Exibe os registros antes da ordenação
            System.out.println("\nRegistros antes da ordenação:");
            arq.exibirRegistros();


            //arq.insercaoDireta(); //--->OK
            //arq.insercaoBinaria(); //--->OK
            //arq.selecaoDireta(); //--->OK
            //arq.bolha(); //--->OK
            //arq.shake(); //--->OK
            //arq.shell(); //--->OK
            //arq.heap(); //--->OK
            //arq.quickSemPivo_Recursivo(); //--->OK
            //arq.quickSemPivo_Interativo(); //--->OK
            //arq.quickComPivo_Recursivo(); //--->OK
            //arq.quickComPivo_Interativo(); //--->OK
            //arq.mergePrimeiraImple(); //--->OK
            //arq.mergeSegundaImple_Recursivo(); //--->OK
            //arq.mergeSegundaImple_Interativo(); //--->OK

            //arq.counting(); //--->OK
            //arq.bucket(); //--->OK    //arq.bucketAlternativo(); //--->OK
            //arq.radix(); /--->OK
            //arq.comb(); //--->OK
            //arq.gnome(); //--->OK
            //arq.tim(); //--->OK

            // Exibe os registros após a ordenação
            System.out.println("\nRegistros após a ordenação:");
            arq.exibirRegistros();

            // Fecha o arquivo
            arq.fecharArquivo();
        } catch (IOException e) {
            System.out.println("Erro ao manipular o arquivo: " + e.getMessage());
        }
    }
}
