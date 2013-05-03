package problemSet1;

public class Tuple <L,R> {
    public L left;
    public R right;
    public Tuple (L left, R right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        else if (!(obj instanceof Tuple)) return false;
        @SuppressWarnings("unchecked")
        final Tuple<L,R> o = (Tuple<L,R>)obj;
        return this.left.equals(o.left) && this.right.equals(o.right);
    }
    @Override
    public int hashCode() {
        return this.left.hashCode()^this.right.hashCode()^47;
    }
}