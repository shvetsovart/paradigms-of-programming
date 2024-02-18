package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

// Model: elements[0]..elements[size-1]
// Invariant: if size >= 0:
//               if head < tail: for i = head..tail-1: elements[i] != null, maxSize = 100005
//               if head > tail: for i = head..maxSize: elements[i] != null, maxSize = 100005,
//                               for i = 0..tail: elements[i] != null, maxSize = 100005
//            else: for i = 0..maxSize: elements[i] == null
public class ArrayQueue extends AbstractQueue {
    // :NOTE: elements должны быть расширяемыми, а не изначально быть заданы огромным значением
    private Object[] elements = new Object[2];
    private int tail;
    private int head;

    public static ArrayQueue create() {
        return new ArrayQueue();
    }

    // Let immutable(head, tail):
    // if (head > tail):
    //    for i = head..maxSize: elements'[i] = elements[i]
    //    && for i = 0..tail: elements'[i] = elements[i]
    // if (head <= tail):
    //    for i = head..tail: elements'[i] = elements[i]

    // for enqueue
    //    Pred: element != null
    //    Post: size' = size + 1 && tail' = (tail + 1) % maxSize && elements[tail] == element &&
    //    && immutable(head, tail)
    public void enqueueImpl(final Object element) {
        if (size >= elements.length) {
            ensureCapacity(size * 2);
        }
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }

    private void ensureCapacity(int size) {
        Object[] cur = new Object[this.size];
        System.arraycopy(elements, head, cur, 0, elements.length - head);
        System.arraycopy(elements, 0, cur, elements.length - head, tail);
        elements = Arrays.copyOf(cur, 2 * size);
        head = 0;
        tail = this.size;
    }

    // for dequeue
    // Pred: size >= 1
    // Post: size' = size - 1 && immutable(head, tail) && R = elements[head]
    // && elements'[head] = null && head' = (head + 1) % maxSize
    public Object dequeueImpl() {
        Object result = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return result;
    }

    private Indexes indexes(Predicate<Object> x) {
        int i = head;
        int cur = 0;
        int first = -1, last = -1;
        boolean wasInTail = head != tail;

        if (size == 0)
            return new Indexes(-1, -1);

        while (i != tail || !wasInTail) {
            if (head == tail)
                wasInTail = true;
            if (x.test(elements[i])) {
                if (first == -1)
                    first = cur;
                last = cur;
            }
            i = (i + 1) % elements.length;
            cur++;
        }
        return new Indexes(first, last);
    }

    // Pred: x != null
    // Post: if x && Predicate.test(i == x) exists in the queue -> R == first index of x in the queue,
    //                                                                                 starting from head
    //       if x is not in the queue -> R == -1

    // :NOTE: избавиться от дублирующегося кода в методах indexOf и indexIf
    public int indexOf(Object x) {
        Objects.requireNonNull(x);
        Predicate<Object> xx = n -> n.equals(x);
        return indexes(xx).first;
    }

    // Pred: x != null
    // Post: if x && Predicate.test(i == x) exists in the queue -> R == last index of x in the queue,
    //                                                                                 starting from head
    //       if x is not in the queue -> R == -1
    public int indexIf(Predicate<Object> x) {
        return indexes(Objects.requireNonNull(x)).first;
    }

    // Pred: x != null
    // Post: if x exists in the queue -> R == last index of x in the queue, starting from head
    //       if x is not in the queue -> R == -1
    public int lastIndexOf(Object x) {
        Objects.requireNonNull(x);
        Predicate<Object> xx = n -> n.equals(Objects.requireNonNull(x));
        return indexes(xx).last;
    }

    // Pred: x != null
    // Post: if x exists in the queue -> R == last index of x in the queue, starting from head
    //       if x is not in the queue -> R == -1
    public int lastIndexIf(Predicate<Object> x) {
        return indexes(Objects.requireNonNull(x)).last;
    }

    // Pred: size >= 1
    // Post: R == elements[head] && immutable(head, tail) && size' == size
    public Object element() {
        assert size >= 1;
        return elements[head];
    }

    // Pred: true
    // Post: size' == 0 && head == 0 && tail == 0 && for i = 1..maxSize: elements[i] == null
    public void clear() {
        // :NOTE: использовать встроенные функции java вместо for-ов - fixed
        size = head = tail = 0;
    }
}
