public class ArrayDeque<T> {
    private static int initialCapacity = 8;
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

    private void resize(int capacity) {
        T[] tmp = (T[]) new Object[capacity];
        //copy array: nextLast->nextFirst to new array: 0-size
        int cur = nextLast;
        for (int i = 0; i < size; i++) {
            tmp[i] = items[cur];
            cur = plusOne(cur);
        }
        this.items = tmp;
        this.capacity = capacity;
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
        if (curRatio < usageRatio) {
            //when usage <50%, need to downsize to 80% original capacity
            int recommendCapacity = (int) (size / recommendUsageRatio);

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
//        String[] items =new String[100];
//        Arrays.fill(items,0,50, "a");
//        items[0] ="b";
//        ArrayDeque<String> q = new ArrayDeque<String>(100,  50, items, 99, 50);
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("   size: " + q.size());
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        q.printDeque();
//
//        System.out.println("removeFirst: " + q.removeFirst());
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        q.printDeque();

//        ArrayDeque<String> q = new ArrayDeque<>();
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        System.out.println("get[0]: " + q.get(0));
//        System.out.println("get[1]: " + q.get(1));
//        System.out.println("get[2]: " + q.get(2));
//        q.printDeque();
//        System.out.println();
//
//        q.addFirst("666");
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        System.out.println("get[0]: " + q.get(0));
//        System.out.println("get[1]: " + q.get(1));
//        System.out.println("get[2]: " + q.get(2));
//        q.printDeque();
//        System.out.println();
//
//        q.addLast("999");
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        System.out.println("get[0]: " + q.get(0));
//        System.out.println("get[1]: " + q.get(1));
//        System.out.println("get[2]: " + q.get(2));
//        q.printDeque();
//        System.out.println();
//
//        q.addLast("777");
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        System.out.println("get[0]: " + q.get(0));
//        System.out.println("get[1]: " + q.get(1));
//        System.out.println("get[2]: " + q.get(2));
//        q.printDeque();
//        System.out.println();
//
//        q.addLast("111");
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        System.out.println("get[0]: " + q.get(0));
//        System.out.println("get[1]: " + q.get(1));
//        System.out.println("get[2]: " + q.get(2));
//        System.out.println("get[3]: " + q.get(3));
//        System.out.println("get[4]: " + q.get(4));
//        q.printDeque();
//        System.out.println();
//
//        q.addLast("222");
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        System.out.println("get[0]: " + q.get(0));
//        System.out.println("get[1]: " + q.get(1));
//        System.out.println("get[2]: " + q.get(2));
//        System.out.println("get[3]: " + q.get(3));
//        System.out.println("get[4]: " + q.get(4));
//        q.printDeque();
//        System.out.println();
//
//        System.out.println("removeFirst: " + q.removeFirst());
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        System.out.println("get[0]: " + q.get(0));
//        System.out.println("get[1]: " + q.get(1));
//        System.out.println("get[2]: " + q.get(2));
//        System.out.println("get[3]: " + q.get(3));
//        System.out.println("get[4]: " + q.get(4));
//        q.printDeque();
//        System.out.println();
//
//
//        System.out.println("removeLast: " + q.removeLast());
//        System.out.println("isEmpty: " + q.isEmpty());
//        System.out.println("   size: " + q.size());
//        System.out.println(" capacity: " + q.capacity);
//        System.out.println("nextFirst: " + q.nextFirst);
//        System.out.println("nextLast: " + q.nextLast);
//        System.out.println("get[0]: " + q.get(0));
//        System.out.println("get[1]: " + q.get(1));
//        System.out.println("get[2]: " + q.get(2));
//        System.out.println("get[3]: " + q.get(3));
//        System.out.println("get[4]: " + q.get(4));
//        q.printDeque();
//        System.out.println();

//    }

}
