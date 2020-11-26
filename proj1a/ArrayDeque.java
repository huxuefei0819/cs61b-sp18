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

//    public ArrayDeque(int capacity, int size, T[] items, int nf, int nl) {
//        this.capacity = capacity;
//        this.size = size;
//        this.items = items;
//        this.nextFirst = nf;
//        this.nextLast = nl;
//    }

    private int minusOne(int index) {
        return (index - 1 + capacity) % capacity;
    }

    private int plusOne(int index) {
        return (index + 1) % capacity;
    }

    private void resize(int cap) {
        T[] tmp = (T[]) new Object[cap];
        //solution1: copy array: nextLast->nextFirst to new array: 0-size
//        int cur = nextLast;
//        for (int i = 0; i < size; i++) {
//            tmp[i] = items[cur];
//            cur = plusOne(cur);
//        }
        //solution2: copy two parts: nextLast->end of array + 0->nextFirst
        System.arraycopy(items, nextLast, tmp, 0, size - nextLast);
        System.arraycopy(items, 0, tmp, size - nextLast, nextFirst + 1);
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

//    public int getCapacity() {
//        return capacity;
//    }

    /**
     * Prints non-empty elements in array items.
     */
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

    /**
     * Prints all elements in array items.
     */
//    public void printAll() {
//        for (int i = 0; i <= capacity - 1; i++) {
//            System.out.print(items[i] + " ");
//        }
//        System.out.println();
//    }
    private void checkDownsize() {
        double curRatio = size / capacity;
        if (capacity >= mCapacity && curRatio < usageRatio) {
            //when usage <50%, need to downsize to 80% original capacity
            int recommendCapacity = (int) (size / recommendUsageRatio) + 1;
//System.out.println("recommendCapacity = "+ recommendCapacity);
            T[] tmp = (T[]) new Object[recommendCapacity];
            //solution1: copy arr from plusOne(nextFirst), with size number of elements
//            int cur = plusOne(nextFirst);
//            for (int i = 0; i < size; i++) {
//                tmp[i] = items[cur];
//                cur = plusOne(cur);
//            }
            //solution2: when nextFirst<nextLast, copy array plusOne(nextFirst)->minusOne(nextLast)
            if (plusOne(nextFirst) < minusOne(nextLast)) {
                System.arraycopy(items, plusOne(nextFirst), tmp, 0, size);
            } else {
                //copy array plusOne(nextFirst)->end of array + 0->minusOne(nextLast)
                System.arraycopy(items, plusOne(nextFirst), tmp, 0, capacity - plusOne(nextFirst));
                System.arraycopy(items, 0, tmp, capacity - plusOne(nextFirst), nextLast);
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
//
//    }

}
