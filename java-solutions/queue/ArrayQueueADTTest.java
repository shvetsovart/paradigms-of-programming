package queue;

// :NOTE: тесты пишутся не так, необходимо с помощью assert сравнивать получаемые значения с эталонными
//        пересмотрите лекцию
public class ArrayQueueADTTest {
    public static void main(String[] args) {
        ArrayQueueADT q1 = ArrayQueueADT.create();
        ArrayQueueADT q2 = ArrayQueueADT.create();
        for (int i = 0; i < 5; ++i) {
            ArrayQueueADT.enqueue(q1, "q_1_" + i);
            ArrayQueueADT.enqueue(q2, "q_2_" + i);
        }
//        ArrayQueueModule.clear();
//        System.out.println(ArrayQueueModule.size());
        System.out.println(ArrayQueueADT.element(q1));
        System.out.println(ArrayQueueADT.element(q1));
        System.out.println(ArrayQueueADT.element(q1));
        System.out.println(ArrayQueueADT.element(q1));
        while (!ArrayQueueADT.isEmpty(q1)) {
            System.out.println(ArrayQueueADT.size(q1) + " " + ArrayQueueADT.dequeue(q1));
        }
        while (!ArrayQueueADT.isEmpty(q2)) {
            System.out.println(ArrayQueueADT.size(q2) + " " + ArrayQueueADT.dequeue(q2));
        }
    }
}
