public class ArrayDeque<T> implements Deque<T> {

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

    @Override
    public void addFirst(T item) {
        if (size == capacity) {
            resize(size * refactor);
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst = minusOne(nextFirst);
    }

    @Override
    public void addLast(T item) {
        if (size == capacity) {
            resize(size * refactor);
        }
        items[nextLast] = item;
        size += 1;
        nextLast = plusOne(nextLast);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int cur = plusOne(nextFirst);
        if (size == capacity) {
            while (cur != nextFirst) {
                System.out.print(items[cur] + " ");
                cur = plusOne(cur);
            }
            System.out.print(items[cur]);
        } else {
            while (cur != nextLast) {
                System.out.print(items[cur] + " ");
                cur = plusOne(cur);
            }
        }
        System.out.println();
    }

    @Override
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

    @Override
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

    @Override
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
}
