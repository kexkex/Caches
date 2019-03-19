public interface Cache<K, V> {

    V put(K key, V value);

    V get(K key);

    void remove(K key);

//    boolean containsAll(Cache<K, V> cache);

}
