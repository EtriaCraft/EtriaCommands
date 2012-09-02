package Commands.Trade;

public class Pair<A, B> {
    
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Pair) {
            Pair other = (Pair) o;
            return ((this.first.equals(other.first)) && (this.second.equals(other.second)));
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    @Override
    public String toString() {
           return "(" + first + ", " + second + ")";
    }
    
    public void setFirst(A first) {
        this.first = first;
    }
    
    public void setSecond(B second) {
        this.second = second;
    }
    
    public A getFirst() {
        return this.first;
    }
    
    public B getSecond() {
        return this.second;
    }
    
}