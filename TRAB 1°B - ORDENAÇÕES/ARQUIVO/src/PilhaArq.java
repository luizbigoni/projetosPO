public class PilhaArq {
    private NoPilhaArq topNoArq;

    public PilhaArq(){
        this.topNoArq = null;
    }

    public boolean isEmpty(){
        return topNoArq == null;
    }

    public void push(int pos){
        NoPilhaArq novo = new NoPilhaArq(pos);
        novo.setProxArq(topNoArq);
        topNoArq = novo;
    }

    public int pop (){
        if(!isEmpty()){
            int aux = topNoArq.getPos();
            topNoArq = topNoArq.getProxArq() ;
            return aux;
        }
        return -1;
    }

}
