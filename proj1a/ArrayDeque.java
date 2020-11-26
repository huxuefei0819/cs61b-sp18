public class ArrayDeque<T> {

    private static int initialCapacity = 3;
    private static int mCapacity = 3;
    private static int refactor = 2;
    private static double usageRatio = 0.25;
    private static double recommendUsageRatio = 0.8;
    private int size;
    private T[] items;
    private int capacity;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        capacity = initialCapacity;
        size = 0;
        items = (T[]) new Object[initialCapacity];
        nextFirst = capacity - 1;
        nextLast = 0;
    }

    private int minusOne(int index) {
        return (index - 1 + capacity) % capacity;
    }

    private int plusOne(int index) {
        return (index + 1) % capacity;
    }

    private void resize(int cap) {
        T[] tmp = (T[]) new Object[cap];
        int cur = nextLast;
        for (int i = 0; i < size; i++) {
            tmp[i] = items[cur];
            cur = plusOne(cur);
        }
        this.items = tmp;
        this.capacity = cap;
        this.nextFirst = capacity - 1;
        this.nextLast = size;
    }

    public void addFirst(T item) {
        if (size == capacity) {
            resize(size * refactor);
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst = minusOne(nextFirst);
    }

    public void addLast(T item) {
        if (size == capacity) {
            resize(size * refactor);
        }
        items[nextLast] = item;
        size += 1;
        nextLast = plusOne(nextLast);
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int cur = plusOne(nextFirst);
        if (size == capacity) {
            //special case, when array is full, nextLast==onePlus(nextFirst),
            //iterate: onePlus(nextFirst)-->nextFirst
            while (cur != nextFirst) {
                System.out.print(items[cur] + " ");
                cur = plusOne(cur);
            }
            System.out.print(items[cur]);
        } else {
            //normal case, nextLast<=nextFirst
            while (cur != nextLast) {
                System.out.print(items[cur] + " ");
                cur = plusOne(cur);
            }
        }
        System.out.println();
    }

    private void checkDownsize() {
        double curRatio = size / capacity;
        if (capacity >= mCapacity && curRatio < usageRatio) {
            int recommendCapacity = (int) (size / recommendUsageRatio) + 1;
            T[] tmp = (T[]) new Object[recommendCapacity];
            int cur = plusOne(nextFirst);
            for (int i = 0; i < size; i++) {
                tmp[i] = items[cur];
                cur = plusOne(cur);
            }
            this.items = tmp;
            this.capacity = recommendCapacity;
            this.nextFirst = capacity - 1;
            this.nextLast = size;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = plusOne(nextFirst);
        T item = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;

        checkDownsize();
        return item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = minusOne(nextLast);
        T item = items[nextLast];
        items[nextLast] = null;
        size -= 1;

        checkDownsize();
        return item;
    }

    public T get(int index) {
        if (size == 0 || index > size) {
            return null;
        }
        int cur = plusOne(nextFirst);
        while (index != 0) {
            cur = plusOne(cur);
            index -= 1;
        }
        return items[cur];
    }

//    public static void main(String[] args) {
//        ArrayDeque<Integer> q = new ArrayDeque<>();
//        for (int i = 0; i < 18; i++) {
//            q.addFirst(i);
//        }
//        q.printDeque();
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("--------------------------");
//        for (int i = 0; i < 14; i++) {
//            //q.removeFirst();
//            System.out.println("removeFirst: " + q.removeFirst());
//        }
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        q.printDeque();
//    }

}
