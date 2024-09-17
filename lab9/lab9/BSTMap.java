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
        if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(p.key) == 0) {
            p.value = value;
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<>();
        tranverse(set, root);
        return set;
    }

    private void tranverse(Set<K> set, Node p) {
        if (p == null) {
            return;
        }
        tranverse(set, p.left);
        set.add(p.key);
        tranverse(set, p.right);

    }

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    private Node findRightTreeMinNode(Node p) {
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    private Node removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            if (p.left == null && p.right == null) {
                return null;
            } else if (p.left != null && p.right != null) {
                Node item = findRightTreeMinNode(p.right);
                item.right = removeHelper(item.key, p.right);
                item.left = p.left;
                p = item;
            } else {
                if (p.left == null) {
                    p = p.right;
                } else {
                    p = p.left;
                }
            }
        } else if (key.compareTo(p.key) < 0) {
            p.left = removeHelper(key, p.left);
        } else {
            p.right = removeHelper(key, p.right);
        }
        return p;
    }

    @Override
    public V remove(K key) {
        V res = get(key);
        root = removeHelper(key, root);
        return res;
    }

    private Node removeHelper(K key, V value, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) < 0) {
            p.left = removeHelper(key, value, p.left);
        } else if (key.compareTo(p.key) > 0) {
            p.right = removeHelper(key, value, p.right);
        } else {
            if (p.value == value) {
                if (p.left == null) {
                    p = p.right;
                }
                if (p.right == null) {
                    p = p.left;
                }
                if (p.left != null && p.right != null) {
                    Node item = findRightTreeMinNode(p.right);
                    item.right = removeHelper(item.key, item.value, p.right);
                    item.left = p.left;
                    p = item;
                }
            } else {
                return null;
            }
        }
        return p;
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V res = get(key);
        if (res.equals(value)) {
            root = removeHelper(key, value, root);
            return value;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator<K>();
    }

    private class BSTIterator<T> implements Iterator<K> {

        List<K> list;
        int index = 0;

        public BSTIterator() {
            list = new ArrayList<>();
            index = 0;
            listIni(root);
        }

        private void listIni(Node r) {
            if (r == null) {
                return;
            }
            listIni(r.left);
            list.add(r.key);
            listIni(r.right);
        }


        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public K next() {
            return list.get(index++);
        }
    }
}
