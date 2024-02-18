package queue;

public class ArrayQueueMyTest {
    public static void main(String[] args) {
        ArrayQueue q1 = ArrayQueue.create();
        ArrayQueue q2 = ArrayQueue.create();
        for (int i = 0; i < 2; ++i) {
            q1.enqueue("q_1_" + i);
            q2.enqueue("q_2_" + i);
        }
        System.out.println(q1.indexOf("q_1_1"));
//        ArrayQueueModule.clear();
//        System.out.println(ArrayQueueModule.size());
//        System.out.println(q1.element());
//        System.out.println(q1.element());
//        System.out.println(q1.element());
//        System.out.println(q1.element());
//        while (!q1.isEmpty()) {
//            System.out.println(q1.size() + " " + q1.dequeue());
//        }
//        while (!q2.isEmpty()) {
//            System.out.println(q2.size() + " " + q2.dequeue());
//        }
    }
}
