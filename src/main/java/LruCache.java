import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> implements Cache<K, V> {

    private final Map<K, V> storage;
    private final int capacity;

    public LruCache(final int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity should be more than 0");
        }

        this.capacity = capacity;
        this.storage = new LinkedHashMap <K, V> (capacity, 1.0f) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
                return super.size() > capacity;
            }
        };
    }

    @Override
    public V put(K key, V value) {
        return storage.put(key, value);
    }

    @Override
    public V get(K key) {
        return storage.get(key);
    }

    @Override
    public void remove(K key) {
        storage.remove(key);
    }

    @Override
    public String toString() {
        return "storage = " + storage + ", capacity = " + capacity ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LruCache<?, ?> lruCache = (LruCache<?, ?>) o;

        if (capacity != lruCache.capacity) return false;
        return storage.equals(lruCache.storage);
    }

    @Override
    public int hashCode() {
        int result = storage.hashCode();
        result = 31 * result + capacity;
        return result;
    }
}
