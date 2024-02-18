package queue;

import java.util.Objects;

// Model: elements[0]..elements[size-1]
// Invariant: for i=1..maxSize-1: elements[i] != null, maxSize = 100005
public class ArrayQueueADT {
    private static final int maxSize = 100005;
    private final Object[] elements = new Object[maxSize];
    private int size;
    private int tail;
    private int head;

    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }

    // Let immutable(head, tail):
    // if (head > tail):
    //    for i = head..maxSize: elements'[i] = elements[i]
    //    && for i = 0..tail: elements'[i] = elements[i]
    // if (head <= tail):
    //    for i = head..tail: elements'[i] = elements[i]

    //    Pred: element != null
//    Post: size' = size + 1 && tail' = (tail + 1) % maxSize && elements[tail] == element &&
//    && immutable(head, tail)
    public static void enqueue(final ArrayQueueADT queue, final Object element) {
        Objects.requireNonNull(element);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % maxSize;
        queue.size++;
    }

    // Pred: size >= 1
    // Post: size' = size - 1 && immutable(head, tail) && R = elements[head]
    // && elements'[head] = null && head' = (head + 1) % maxSize
    public static Object dequeue(final ArrayQueueADT queue) {
        assert queue.size >= 1;
        Object result = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % maxSize;
        queue.size--;
        return result;
    }

    // Pred: size >= 1
    // Post: R == elements[head] && immutable(head, tail) && size' == size
    public static Object element(final ArrayQueueADT queue) {
        assert queue.size >= 1;
        return queue.elements[queue.head];
    }

    // Pred: true
    // Post: R == size && size' == size && immutable(head, tail)
    public static int size(final ArrayQueueADT queue) {
        return queue.size;
    }

    // Pred: true
    // Post: R == (size == 0) && size' == size && immutable(head, tail)
    public static boolean isEmpty(final ArrayQueueADT queue) {
        return queue.size == 0;
    }

    public static int indexOf(final ArrayQueueADT queue, Object x) {
        Objects.requireNonNull(x);
        int i = queue.head;
        while (i != queue.tail) {
            if (queue.elements[i].equals(x))
                return (queue.head < queue.tail ? i - queue.head : maxSize - queue.head + i);
            i = (i + 1) % maxSize;
        }
        return -1;
    }

    public static int lastIndexOf(final ArrayQueueADT queue, Object x) {
        Objects.requireNonNull(x);
        int i = queue.head;
        int result = -1;
        while (i != queue.tail) {
            if (queue.elements[i].equals(x))
                result = Math.max(result, queue.head < queue.tail ? i - queue.head : maxSize - queue.head + i);
            i = (i + 1) % maxSize;
        }
        return result;
    }

    // Pred: true
    // Post: size' == 0 && head == 0 && tail == 0 && for i=1..maxSize:elements[i] == null
    public static void clear(final ArrayQueueADT queue) {
        if (queue.head <= queue.tail) {
            for (int i = queue.head; i <= queue.tail; ++i) {
                queue.elements[i] = null;
            }
        } else {
            for (int i = queue.head; i < maxSize; ++i) {
                queue.elements[i] = null;
            }
            for (int i = 0; i <= queue.tail; ++i) {
                queue.elements[i] = null;
            }
        }
        queue.size = queue.head = queue.tail = 0;
    }
}
