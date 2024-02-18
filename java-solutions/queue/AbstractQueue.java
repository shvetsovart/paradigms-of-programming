package queue;

import java.util.Objects;
import java.util.function.Predicate;

// Model: a subsequence of elements, for every of which
//          prev and next element is uniquely specified, except
//          head and tail, that are the first and the last elements of the subsequence
// Invariant: for every element between head and tail: it is not null
public abstract class AbstractQueue implements Queue {
    protected int size;

    // Let immutable(head, tail):
    // all elements from head to tail aren't modified

    // Pred: true
    // Post: R == size && size' == size && immutable(head, tail)
    public int size() {
        return size;
    }

    // Pred: true
    // Post: R == (size == 0) && size' == size && immutable(head, tail)
    public boolean isEmpty() {
        return size == 0;
    }

    // Pred: element != null
    // Post: size' = size + 1 && element at tail == element &&
    // && immutable(head, tail) && next element to tail is not known (null)
    public void enqueue(final Object element) {
        enqueueImpl(Objects.requireNonNull(element));
        size++;
    }

    protected abstract void enqueueImpl(final Object element);

    // Pred: size >= 1
    // Post: size' == size - 1 && immutable(head', tail) && R == elements[head] &&
    // head' points at the next after head element && element at head == null
    public Object dequeue() {
        assert size >= 1;
        return dequeueImpl();
    }

    protected abstract Object dequeueImpl();

//    public int indexIf() {
//
//    }
}
