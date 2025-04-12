public class NoPilha{
    private int pos;
    private NoPilha prox;

    public NoPilha getProx(){
        return prox;
    }
    public void setProx(NoPilha prox){
        this.prox = prox;
    }
    public int getPos(){
        return pos;
    }
    public NoPilha(int pos){
        this.pos = pos;
        this.prox = null;
    }
}