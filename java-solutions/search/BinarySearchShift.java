package search;

public class BinarySearchShift {
    // Pred: int(args) was sorted ascending before shift && for all i, j, i != j: int(args[i]) != int(args[j])
    // Post: program completion
    public static void main(String[] args) {
        // Pred
        int[] a = new int[args.length];
        // a[args.length]
        for (int i = 0; i < args.length; ++i) {
            a[i] = Integer.parseInt(args[i]);
        }
        // a[] - int(args[])
        int iterativeResult = iterativeShiftSearch(a);
        // iterativeResult = shiftSearch(a)
//        int recursiveResult = recursiveShiftSearch(a, 0);
        // recursiveResult = recursiveShiftSearch(a, 0)
//        assert iterativeResult == recursiveResult;
        // iterativeResult == recursiveResult
        System.out.println(iterativeResult);
    }

    // Pred: a was sorted ascending before shift
    // Post: R == shift
    public static int iterativeShiftSearch(int[] a) {
        // Pred
        if (a.length == 0 || a.length == 1) {
            // a.length <= 1 -> shift == 0
            return 0;
        }
        // a.length > 1, shift - ?
        for (int i = 0; i < a.length - 1; ++i) {
            // i < a.length - 1, a.length > 1
            if (a[i] > a[i + 1]) {
                // i < a.length - 1, i + 1 < a.length
                // a[i] > a[i + 1] -> shift = i + 1
                return i + 1;
            }
            // a[i] < a[i + 1], shift - ?
        }
        // a[i] < a[i + 1] for all i,
        // i = 0..a.length-1 -> shift == 0
        return 0;
    }

    // Pred: i >= 0 && a[i..length) is a part of a[] that was sorted ascending before shift
    // Post: R == shift
    public static int recursiveShiftSearch(int[] a, int i) {
        // Pred
        if (i >= a.length - 1) {
            // i is either the last num of the array -> shift == 0
            // or the array size is 0 -> shift == 0
            return 0;
        }
        // i < a.length - 1
        if (a[i] > a[i + 1]) {
            // a[i] > a[i + 1] -> shift == i + 1
            return i + 1;
        }
        // a[i] < a[i + 1], shift - ?
        return recursiveShiftSearch(a, i + 1);
    }
}
