import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Principal {
    private Arquivo arqOrdenado, arqReversa, arqRandomico, auxOrd, auxRev, auxRand;
    private Arquivo tabela;
    long tini, tfim, OrdTot, RevTot, RandTot, ordComp, revComp, randComp, ordMov, revMov, randMov, regTot;

    public Principal() throws IOException {
        arqOrdenado = new Arquivo("arqOrd.dat");
        arqReversa = new Arquivo("arqRev.dat");
        arqRandomico = new Arquivo("arqRand.dat");

        auxOrd = new Arquivo("auxOrd.dat");
        auxRev = new Arquivo("auxRev.dat");
        auxRand = new Arquivo("auxRand.dat");
    }

    public void gerarTabela(){
        String nomeArquivo = "TABELA.txt";
        double ordMovEqua, ordCompEqua, revMovEqua, revCompEqua, randMovEqua, randCompEqua;
        String divisao, linha;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))){
            String colunasInicio =
                            "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" + "\n" +
                            " Metodos Ordenacao " + "|                     Arquivo Ordenado                      " + "|                      Arquivo em Ordem Reversa                     " + "|                        Arquivo Randomico                        |" + "\n" +
                            "                   |-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|"
                            + "\n                   " +
                            "| Comp.Prog. | Comp.Equa. |  Mov.Prog | Mov.Equa. |  Tempo  " + "|  Comp.Prog.  |  Comp.Equa.  |   Mov.Prog  |  Mov.Equa.  |  Tempo  " + "|  Comp.Prog.  |  Comp.Equa.  |   Mov.Prog |  Mov.Equa. |  Tempo  |";
            writer.write(colunasInicio);
            arqOrdenado.geraArquivoOrdenado();
            arqReversa.geraArquivoInvertido();
            arqRandomico.geraArquivoRandomico();
            regTot = arqOrdenado.getNumRegistros();

            //---------------------------INSERÇÃO DIRETA---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.insercaoDireta(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = regTot-1;
            ordMovEqua = 3*(regTot-1);

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.insercaoDireta(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = (regTot*regTot + regTot - 4)/4;
            revMovEqua = (regTot*regTot + 3*regTot - 4)/2;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.insercaoDireta(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = (regTot * regTot + regTot - 2)/4;
            randMovEqua = (regTot*regTot + 9*regTot - 10)/4;

            System.out.println("chegou 1");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nInser. direta      |%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------INSERÇÃO BINÁRIA---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.insercaoBinaria(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = regTot-1;
            ordMovEqua = 3*(regTot-1);

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.insercaoBinaria(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = (regTot*regTot + regTot - 4)/4;
            revMovEqua = (regTot*regTot + 3*regTot - 4)/2;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.insercaoBinaria(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = (regTot * regTot + regTot - 2)/4;
            randMovEqua = (regTot*regTot + 9*regTot - 10)/4;

            System.out.println("chegou 2");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nInser. binaria     |%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);



            //---------------------------SELEÇÃO DIRETA---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.selecaoDireta(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = regTot-1;
            ordMovEqua = 3*(regTot-1);

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.selecaoDireta(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = (regTot*regTot + regTot - 4)/4;
            revMovEqua = (regTot*regTot + 3*regTot - 4)/2;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.selecaoDireta(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = (regTot * regTot + regTot - 2)/4;
            randMovEqua = (regTot*regTot + 9*regTot - 10)/4;

            System.out.println("chegou 3");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nSelecao direta     |%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------BOLHA---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.bolha(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = regTot-1;
            ordMovEqua = 3*(regTot-1);

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.bolha(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = (regTot*regTot + regTot - 4)/4;
            revMovEqua = (regTot*regTot + 3*regTot - 4)/2;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.bolha(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = (regTot * regTot + regTot - 2)/4;
            randMovEqua = (regTot*regTot + 9*regTot - 10)/4;

            System.out.println("chegou 4");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nBolha              |%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);


            //---------------------------SHAKE---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.shake(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = regTot-1;
            ordMovEqua = 3*(regTot-1);

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.shake(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = (regTot*regTot + regTot - 4)/4;
            revMovEqua = (regTot*regTot + 3*regTot - 4)/2;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.shake(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = (regTot * regTot + regTot - 2)/4;
            randMovEqua = (regTot*regTot + 9*regTot - 10)/4;

            System.out.println("chegou 5");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nShake              |%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------SHELL---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.shell(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.shell(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.shell(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 6");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nShell              |%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------HEAP---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.heap(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.heap(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.heap(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 7");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nHeap               |%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------QUICK SEM PIVO RECURSIVO---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.quickSemPivo_Recursivo(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.quickSemPivo_Recursivo(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.quickSemPivo_Recursivo(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 8");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nQuick sem pivo rec " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------QUICK SEM PIVO INTERATIVO---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.quickSemPivo_Interativo(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.quickSemPivo_Interativo(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.quickSemPivo_Interativo(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 9");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nQuick sem pivo int " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------QUICK COM PIVO RECURSIVO---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.quickComPivo_Recursivo(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.quickComPivo_Recursivo(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.quickComPivo_Recursivo(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 10");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nQuick com pivo rec " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------QUICK COM PIVO INTERATIVO---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.quickComPivo_Interativo(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.quickComPivo_Interativo(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.quickComPivo_Interativo(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 11");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nQuick com pivo int " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------MERGE 1 IMPLEMENTAÇÃO---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.mergePrimeiraImple(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.mergePrimeiraImple(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.mergePrimeiraImple(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 12");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nMerge 1            " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------MERGE 2 IMPLEMENTAÇÃO RECURSIVO---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.mergeSegundaImple_Recursivo(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.mergeSegundaImple_Recursivo(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.mergeSegundaImple_Recursivo(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 13");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nMerge 2 rec        " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------MERGE 2 IMPLEMENTAÇÃO INTERATIVO---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.mergeSegundaImple_Interativo(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.mergeSegundaImple_Interativo(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.mergeSegundaImple_Interativo(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 14");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nMerge 2 int        " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------COUNTING---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.counting(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.counting(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.counting(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 15");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nCounting           " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------BUCKET---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.bucket(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.bucket(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.bucket(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 16");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nBucket             " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------RADIX---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.radix(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.radix(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.radix(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 17");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nRadix              " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------COMB---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.comb(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.comb(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.comb(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 18");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nComb               " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------GNOME---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.gnome(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.gnome(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.gnome(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 19");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nGnome              " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);

            //---------------------------TIM---------------------------//
            auxOrd.truncate(0);
            auxRev.truncate(0);
            auxRand.truncate(0);

            auxOrd.copiaArquivo(arqOrdenado);
            auxOrd.initComp();
            auxOrd.initMov();
            tini = System.currentTimeMillis();
            auxOrd.tim(); //<-----------
            tfim = System.currentTimeMillis();
            OrdTot = (tfim-tini)/1000;
            ordMov = auxOrd.getMov();
            ordComp = auxOrd.getComp();
            ordCompEqua = 0;
            ordMovEqua = 0;

            auxRev.copiaArquivo(arqReversa);
            auxRev.initComp();
            auxRev.initMov();
            tini = System.currentTimeMillis();
            auxRev.tim(); //<-------------
            tfim = System.currentTimeMillis();
            RevTot = (tfim-tini)/1000;
            revMov = auxRev.getMov();
            revComp = auxRev.getComp();
            revCompEqua = 0;
            revMovEqua = 0;

            auxRand.copiaArquivo(arqRandomico);
            auxRand.initComp();
            auxRand.initMov();
            tini = System.currentTimeMillis();
            auxRand.tim(); //<----------
            tfim = System.currentTimeMillis();
            RandTot = (tfim-tini)/1000;
            randMov = auxRand.getMov();
            randComp = auxRand.getComp();
            randCompEqua = 0;
            randMovEqua = 0;

            System.out.println("chegou 20");
            divisao = "\n-------------------|-----------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------|";
            writer.write(divisao);
            linha = (String.format("\nTim                " + "|%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " + "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot + "", revComp, revCompEqua, revMov, revMovEqua, RevTot + "", randComp, randCompEqua, randMov, randMovEqua, RandTot + ""));
            writer.write(linha);



            String colunaFim = "\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
            writer.write(colunaFim);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String args[]) throws IOException {
        Principal p = new Principal();
        p.gerarTabela();
    }

}
