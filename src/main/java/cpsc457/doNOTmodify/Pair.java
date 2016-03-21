package cpsc457.doNOTmodify;

public class Pair<A, B>{
    final A a;
    final B b;

    public Pair(A a, B b){
        this.a = a;
        this.b = b;
    }

    public A fst() { return a; }
    public B snd() { return b; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (a != null ? !a.equals(pair.a) : pair.a != null) return false;
        if (b != null ? !b.equals(pair.b) : pair.b != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
