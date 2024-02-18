package queue;

public class LinkedQueueTest {
    public static void main(String[] args) {
        LinkedQueue q1 = new LinkedQueue();
        LinkedQueue q2 = new LinkedQueue();
        for (int i = 0; i < 5; ++i) {
            q1.enqueue("q_1_" + i);
            q2.enqueue("q_2_" + i);
        }
//        ArrayQueueModule.clear();
//        System.out.println(ArrayQueueModule.size());
        System.out.println(q1.element());
        System.out.println(q1.element());
        System.out.println(q1.element());
        System.out.println(q1.element());
        while (!q1.isEmpty()) {
            System.out.println(q1.size() + " " + q1.dequeue());
        }
        while (!q2.isEmpty()) {
            System.out.println(q2.size() + " " + q2.dequeue());
        }
    }
}
