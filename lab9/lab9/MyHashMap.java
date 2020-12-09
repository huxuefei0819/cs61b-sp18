package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 *
 * @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 3;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /**
     * Computes the hash function of the given key. Consists of
     * computing the hashcode, followed by modding by the number of buckets.
     * To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        for (ArrayMap<K, V> bucket : buckets) {
            if (bucket.containsKey(key)) {
                return bucket.get(key);
            }
        }
        return null;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (loadFactor() > MAX_LF) {
            resize();
        }
        int bucketNum = hash(key);
        if (buckets[bucketNum].containsKey(key)) {
            buckets[bucketNum].put(key, value);
        } else {
            buckets[bucketNum].put(key, value);
            size += 1;
        }

    }

    private void resize() {
        ArrayMap<K, V>[] new_buckets = new ArrayMap[buckets.length * 2];
        for (int i = 0; i < new_buckets.length; i += 1) {
            new_buckets[i] = new ArrayMap<>();
        }
        for (ArrayMap<K, V> bucket : buckets) {
            for (K key : bucket.keySet()) {
                int new_bucketNum = hash(key);
                new_buckets[new_bucketNum].put(key, bucket.get(key));
            }
            this.buckets = new_buckets;
        }
    }


    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        for (int i = 0; i < 10; i++) {
            b.put("hi" + i, i);
            assertTrue(null != b.get("hi" + i)
                    && b.containsKey("hi" + i));
        }
        assertTrue(b.size() == 10);
        b.remove("hi1");
        assertTrue(null == b.get("hi" + 1)
                && !b.containsKey("hi" + 1));
        b.remove("hi5", 5);
        assertTrue(null == b.get("hi" + 5)
                && !b.containsKey("hi" + 5));
        b.remove("hi6", 5);
        assertTrue(null != b.get("hi" + 6)
                && b.containsKey("hi" + 6));
        assertTrue(b.size() == 8);
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (ArrayMap<K, V> bucket : buckets) {
            set.addAll(bucket.keySet());
        }
        return set;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        for (ArrayMap<K, V> bucket : buckets) {
            if (bucket.containsKey(key)) {
                V val = bucket.remove(key);
                if (val != null) {
                    size -= 1;
                }
                return val;
            }
        }
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        for (ArrayMap<K, V> bucket : buckets) {
            if (bucket.containsKey(key)) {
                V val = bucket.remove(key, value);
                if (val != null) {
                    size -= 1;
                }
                return val;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
