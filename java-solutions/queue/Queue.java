package queue;

import java.util.function.Predicate;

// Model: a subsequence of elements, for every of which
//          prev and next element is uniquely specified, except
//          head and tail, that are the first and the last elements of the subsequence
// Invariant: for every element between head and tail: it is not null
public interface Queue {
    // Let immutable(head, tail):
    // all elements from head to tail aren't modified

    // Pred: size >= 1
    // Post: size' == size - 1 && immutable(head', tail) && R == elements[head] &&
    // head' points at the next after head element && element at head == null
    Object dequeue();

    // Pred: size >= 1
    // Post: R == head element && immutable(head, tail) && size' == size
    Object element();

    // Pred: true
    // Post: R == size && size' == size && immutable(head, tail)
    int size();

    // Pred: true
    // Post: R == (size == 0) && size' == size && immutable(head, tail)
    boolean isEmpty();

    // Pred: element != null
    // Post: size' = size + 1 && element at tail == element &&
    // && immutable(head, tail) && next element to tail is not known (null)
    void enqueue(final Object element);

    // Pred: true
    // Post: size' == 0 && head == tail && head element is null
    void clear();

    // Pred: x != null
    // Post: if x && Predicate.test(elements[i] == x) exists in the queue -> R == first index of x in the queue,
    //                                                                                 starting from head
    //       if x is not in the queue -> R == -1
    int indexIf(Predicate<Object> x);

    // Pred: x != null
    // Post: if x && Predicate.test(elements[i] == x) exists in the queue -> R == last index of x in the queue,
    //                                                                                 starting from head
    //       if x is not in the queue -> R == -1
    int lastIndexIf(Predicate<Object> x);
}
