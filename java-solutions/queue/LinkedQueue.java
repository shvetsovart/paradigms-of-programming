package queue;

import java.util.function.Predicate;

// Model: Node *(head) -> *(head + 1) -> ... -> *(tail)
// Invariant: for every element between head and tail: it is not null
//            (if size == 0 -> head and tail are null)
public class LinkedQueue extends AbstractQueue {
    private static class Node {
        private Object element;
        private Node next;

        private Node(Object element, Node next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node head;
    private Node tail = new Node(null, null);

    // Let immutable(head, tail):
    // if (head > tail):
    //    for i = head..maxSize: elements'[i] = elements[i]
    //    && for i = 0..tail: elements'[i] = elements[i]
    // if (head <= tail):
    //    for i = head..tail: elements'[i] = elements[i]

    // Pred: element != null (checked in AbstractQueue)
    // Post: size' = size + 1 (made in AbstractQueue),
    // head == null -> head.element == element, head.next = tail
    // head != null -> tail.element = element, tail.next = tail'
    public void enqueueImpl(final Object element) {
        if (head == null) {
            head = new Node(element, tail);
        } else {
            tail.element = element;
            tail.next = new Node(null, null);
            tail = tail.next;
        }
    }

    private Indexes indexes(Predicate<Object> x) {
        Node cur = head;
        int id = 0;
        int first = -1, last = -1;
        while (cur != tail) {
            if (x.test(cur.element)) {
                if (first == -1)
                    first = id;
                last = id;
            }
            id++;
            cur = cur.next;
        }
        return new Indexes(first, last);
    }

    // Pred: x != null
    // Post: if x && Predicate.test(i == x) exists in the queue -> R == first index of x in the queue,
    //                                                                                 starting from head
    //       if x is not in the queue -> R == -1
    public int indexIf(Predicate<Object> x) {
        return indexes(x).first;
    }


    // Pred: x != null
    // Post: if x && Predicate.test(i == x) exists in the queue -> R == last index of x in the queue,
    //                                                                                 starting from head
    //       if x is not in the queue -> R == -1
    public int lastIndexIf(Predicate<Object> x) {
        return indexes(x).last;
    }

    // Pred: this != null, size >= 1 (checked in AbstractQueue)
    // Post: R = head.element, head = head.next, size' = size - 1
    public Object dequeueImpl() {
        Object result = head.element;
        head = head.next;
        size--;
        return result;
    }

    // Pred: size >= 1
    // Post: R == head.element && immutable(head, tail) && size' == size
    public Object element() {
        assert size >= 1;
        return head.element;
    }

    // Pred: true
    // Post: size' == 0 && head == null && tail == null
    public void clear() {
        head = null;
        tail = new Node(null, null);
        size = 0;
    }
}
