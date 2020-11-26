public class LinkedListDeque<T> implements Deque<T> {

    public class Node {
        private Node prev;
        private T item;
        private Node next;

        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        sentinel.next = new Node(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        sentinel.prev = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node n = sentinel;
        while (n.next != sentinel) {
            System.out.print(n.next.item + " ");
            n = n.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            size -= 1;
            T removedItem = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return removedItem;
        }
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            size -= 1;
            T removedItem = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            return removedItem;
        }
    }

    @Override
    public T get(int index) {
        if (index >= size()) {
            return null;
        }
        Node n = sentinel.next;
        while (index > 0) {
            n = n.next;
            index -= 1;
        }
        return n.item;
    }

    private T getRecursiveHelper(Node n, int index) {
        if (index >= size()) {
            return null;
        } else if (index == 0) {
            return n.item;
        } else {
            return getRecursiveHelper(n.next, index -= 1);
        }
    }

    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }

}
