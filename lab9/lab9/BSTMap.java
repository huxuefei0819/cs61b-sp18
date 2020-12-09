package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        return getHelper(key, root);
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Null values not allowed.");
        }
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /* Print BSTMAp in order of increasing key. */
    private void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node x) {
        if (x.left == null && x.right == null) {
            printNode(x);
        } else if (x.left == null) {
            printNode(x);
            printInOrder(x.right);
        } else if (x.right == null) {
            printInOrder(x.left);
            printNode(x);
        } else {
            printInOrder(x.left);
            printNode(x);
            printInOrder(x.right);
        }
    }

    private void printNode(Node x) {
        System.out.println("key = " + x.key + ", value = " + x.value);
    }


    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set set = new HashSet();
        return keySetHelper(root, set);
    }

    private Set<K> keySetHelper(Node p, Set s) {
        if (p == null) {
            return null;
        } else {
            keySetHelper(p.left, s);
            s.add(p.key);
            keySetHelper(p.right, s);
        }
        return s;
    }

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        V val = get(key);
        if (val == null) {
            return null;
        } else {
            removeHelper(key, root);
        }
        return val;
    }

    private Node removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = removeHelper(key, p.left);
        } else if (cmp > 0) {
            p.right = removeHelper(key, p.right);
        } else {
            if (p.left == null) {
                return p.right;
            }
            if (p.right == null) {
                return p.left;
            }
            Node minNodeInRight = min(p.right);
            p.key = minNodeInRight.key;
            p.value = minNodeInRight.value;
            p.right = removeHelper(minNodeInRight.key, p.right);
            size -= 1;
        }
        return p;
    }

    private Node min(Node p) {
        if (p.left == null) {
            return p;
        } else {
            return min(p.left);
        }
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V val = get(key);
        if (val != value) {
            return null;
        } else {
            removeHelper(key, root);
        }
        return val;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        bstmap.put("ou", 12);
        bstmap.put("aha", 55);
        bstmap.printInOrder();
        //System.out.println(bstmap.keySet());
        bstmap.remove("hello", 5);
        bstmap.printInOrder();
    }

}
