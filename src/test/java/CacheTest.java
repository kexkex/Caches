import org.junit.Assert;
import org.junit.Test;

public class CacheTest {

    @Test
    public void lruCacheTest () {
        Cache<Integer, Integer> cache = new LruCache<>(4);

        cache.put(0, 0);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);

        System.out.println(cache);

        cache.put(4, 4);

        System.out.println(cache);

        Cache<Integer, Integer> currentCache = new LruCache<>(4);
        currentCache.put(1, 1);
        currentCache.put(2, 2);
        currentCache.put(3, 3);
        currentCache.put(4, 4);

        Assert.assertTrue(currentCache.equals(cache));

    }

    @Test
    public void lfuCacheTest() throws Exception {
        Cache<Integer, Integer> cache = new LfuCache<>(4);

        cache.put(0, 0);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);

        System.out.println(cache);

        cache.get(1);
        cache.get(2);
        cache.get(3);
        cache.get(1);

        System.out.println(cache);

        cache.put(4, 4);

        System.out.println(cache);

        Cache<Integer, Integer> currentCache = new LfuCache<>(4);
        currentCache.put(1, 1);
        currentCache.put(2, 2);
        currentCache.put(3, 3);
        currentCache.put(4, 4);

        Assert.assertTrue(currentCache.equals(cache));
    }
}
