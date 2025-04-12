public class NoPilhaArq {
    private int pos;
    private NoPilhaArq prox;

    public void setProxArq(NoPilhaArq prox) {
        this.prox = prox;
    }

    public NoPilhaArq(int pos) {
        this.pos = pos;
        this.prox = null;
    }

    public int getPos() {
        return pos;
    }

    public NoPilhaArq getProxArq() {
        return prox;
    }
}

