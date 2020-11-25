public class LinkedListDeque<T> {

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

    public void addFirst(T item) {
        sentinel.next = new Node(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        sentinel.prev = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public boolean isEmpty() {
        if (sentinel.next.equals(sentinel) && size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node n = sentinel;
        while (n.next != sentinel) {
            System.out.print(n.next.item + " ");
            n = n.next;
        }
        System.out.println();
    }

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

    //sentinel.next has index 0
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

//    public static void main(String[] args) {
//        LinkedListDeque<Integer> q = new LinkedListDeque<>();
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println("removeFirst: " + q.removeFirst());
//        System.out.println("removeLast: " + q.removeLast());
//        q.printDeque();
//        System.out.println();
//
//        q.addFirst(666);
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println("get 0th: " + q.get(0));
//        System.out.println("get 1st: " + q.getRecursive(1));
//        q.printDeque();
//        System.out.println();
//
//        q.addLast(-999);
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println("get 0th: " + q.get(0));
//        System.out.println("get 1st: " + q.getRecursive(1));
//        q.printDeque();
//        System.out.println();
//
//        int removedFirst = q.removeFirst();
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println("removed: " + removedFirst);
//        System.out.println("get 0th: " + q.get(0));
//        System.out.println("get 1st: " + q.getRecursive(1));
//        q.printDeque();
//        System.out.println();
//
//        int removedLast = q.removeLast();
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println("removed: " + removedLast);
//        System.out.println("get 0th: " + q.get(0));
//        System.out.println("get 1st: " + q.getRecursive(1));
//        q.printDeque();
//    }
}
