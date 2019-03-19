import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class LfuCache<K, V> implements Cache<K, V> {

    private final Map<K, Node> storage;
    private final int capacity;

    public LfuCache(final int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity should be more than 0");
        }
        this.capacity = capacity;
        this.storage = new LinkedHashMap <K, Node> (capacity, 1.0f);
    }

    @Override
    public V get(K key) {
        Node node = storage.get(key);
        if (node == null) {
            return null;
        }
        return node.incrementFrequency().getValue();
    }

    @Override
    public V put(K key, V value) {
        doEvictionIfNeeded(key);
        Node oldNode = storage.put(key, new Node(value));
        if (oldNode == null) {
            return null;
        }
        return oldNode.getValue();
    }

    @Override
    public void remove(K key) {
        storage.remove(key);
    }

    private void doEvictionIfNeeded(K putKey) {
        if (storage.size() < capacity) {
            return;
        }
        long minFrequency = Long.MAX_VALUE;
        K keyToRemove = null;
        for (Map.Entry<K, Node> entry : storage.entrySet()) {
            if (Objects.equals(entry.getKey(), putKey)) {
                return;
            }
            if (minFrequency >= entry.getValue().getFrequency()) {
                minFrequency = entry.getValue().getFrequency();
                keyToRemove = entry.getKey();
            }
        }
        storage.remove(keyToRemove);
    }

    private class Node {
        private final V value;
        private long frequency;

        Node(V value) {
            this.value = value;
            this.frequency = 1;
        }

        V getValue() {
            return value;
        }

        long getFrequency() {
            return frequency;
        }

        Node incrementFrequency() {
            ++frequency;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            return value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            return "Node [value = " + value + ", frequency = " + frequency + "]";
        }
    }

    @Override
    public String toString() {
        return "storage = " + storage + ", capacity = " + capacity ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LfuCache<?, ?> lfuCache = (LfuCache<?, ?>) o;

        if (capacity != lfuCache.capacity) return false;
        return storage.equals(lfuCache.storage);
    }

    @Override
    public int hashCode() {
        int result = storage.hashCode();
        result = 31 * result + capacity;
        return result;
    }
}
