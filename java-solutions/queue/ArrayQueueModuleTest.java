package queue;

public class ArrayQueueModuleTest {
    public static void main(String[] args) {
        for (int i = 0; i < 5; ++i) {
            ArrayQueueModule.enqueue("e" + i);
        }
        ArrayQueueModule.enqueue("e" + 4);
        System.out.println(ArrayQueueModule.element());
        System.out.println(ArrayQueueModule.element());
        System.out.println(ArrayQueueModule.element());

        System.out.println(ArrayQueueModule.indexOf("e4"));
        System.out.println(ArrayQueueModule.lastIndexOf("e4"));
        System.out.println(ArrayQueueModule.element());
//        ArrayQueueModule.clear();
//        System.out.println(ArrayQueueModule.size());
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.dequeue());
        }
    }
}
