public class Pilha{
    private NoPilha topNo;

    public Pilha(){
        this.topNo = null;
    }
    public boolean isEmpty(){
        return topNo == null;
    }
    public void push(int pos){
        NoPilha novo = new NoPilha(pos);
        novo.setProx(topNo);
        topNo = novo;
    }
    public int pop (){
        if(!isEmpty()){
            int aux = topNo.getPos();
            topNo = topNo.getProx() ;
            return aux;
        }
        return -1;
    }
}