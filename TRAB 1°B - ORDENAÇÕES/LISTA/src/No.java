public class No {
    private int info;
    private No prox;
    private No ant;

    public No(int info){ //construtor
        this.info = info;
        this.prox = null;
        this.ant = null;
    }

    public No getProx(){ //retorna o nó posterior, ou null se não houver
        return prox;
    }

    public void setProx(No prox){ //atribui no nó posterior
        this.prox = prox;
    }

    public No getAnt(){ //retorna o nó anterior, ou null se não houver
        return ant;
    }

    public void setAnt(No ant){ //atribui no nó anterior
        this.ant = ant;
    }

    public int getInfo(){ //retorna o valor da informação
        return info;
    }

    public void setInfo(int info){ //atribui um valor à informação do nó
        this.info = info;
    }

}


