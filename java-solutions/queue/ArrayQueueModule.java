package queue;

import java.util.Objects;

import static java.lang.Math.max;

// Model: elements[0]..elements[size-1]
// Invariant: for i=0..maxSize-1: elements[i] != null, maxSize = 100005
public class ArrayQueueModule {
    private static final int maxSize = 200010;
    private static Object[] elements = new Object[maxSize];
    private static int size = 0;
    private static int tail = 0;
    private static int head = 0;

    // Let immutable(head, tail):
    // if (head > tail):
    //    for i = head..maxSize: elements'[i] = elements[i]
    //    && for i = 0..tail: elements'[i] = elements[i]
    // if (head <= tail):
    //    for i = head..tail: elements'[i] = elements[i]

//    Pred: element != null
//    Post: size' = size + 1 && tail' = (tail + 1) % maxSize && elements[tail] == element &&
//    && immutable(head, tail)
    public static void enqueue(final Object element) {
        Objects.requireNonNull(element);
        elements[tail] = element;
        tail = (tail + 1) % maxSize;
        size++;
    }

    // Pred: size >= 1
    // Post: size' = size - 1 && immutable(head, tail) && R = elements[head]
    // && elements'[head] = null && head' = (head + 1) % maxSize
    public static Object dequeue() {
        assert size >= 1;
        Object result = elements[head];
        elements[head] = null;
        head = (head + 1) % maxSize;
        size--;
        return result;
    }

    // Pred: size >= 1
    // Post: R == elements[head] && immutable(head, tail) && size' == size
    public static Object element() {
        assert size >= 1;
        return elements[head];
    }

    // Pred: true
    // Post: R == size && size' == size && immutable(head, tail)
    public static int size() {
        return size;
    }

    // Pred: true
    // Post: R == (size == 0) && size' == size && immutable(head, tail)
    public static boolean isEmpty() {
        return size == 0;
    }

    public static int indexOf(Object x) {
        Objects.requireNonNull(x);
        int i = head;
        while (i != tail) {
            if (elements[i].equals(x))
                return (head < tail ? i - head: maxSize - head + i);
            i = (i + 1) % maxSize;
        }
        return -1;
    }

    public static int lastIndexOf(Object x) {
        int result = -1;
        if (head < tail) {
            for (int i = head; i < tail; ++i) {
                if (elements[i].equals(x)) {
                    result = i - head;
                }
            }
            return result;
        } else if (head > tail) {
            for (int i = head; i < maxSize; ++i) {
                if (elements[i].equals(x)) {
                    result = i - head;
                }
            }
            for (int i = 0; i < tail; ++i) {
                if (elements[i] == x) {
                    result = maxSize - head + i;
                }
            }
            return result;
        }
        return -1;
    }

    // Pred: true
    // Post: size' == 0 && head == 0 && tail == 0 && for i=1..maxSize:elements[i] == null
    public static void clear() {
        if (head <= tail) {
            for (int i = head; i < tail; ++i) {
                elements[i] = null;
            }
        } else {
            for (int i = head; i < maxSize; ++i) {
                elements[i] = null;
            }
            for (int i = 0; i < tail; ++i) {
                elements[i] = null;
            }
        }
        size = head = tail = 0;
    }
}
