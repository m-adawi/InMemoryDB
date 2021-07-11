package DB;

public interface DatabaseKey {
    @Override
    int hashCode();
    @Override
    public boolean equals(Object obj);
}
