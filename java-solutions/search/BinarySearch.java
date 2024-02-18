package search;

public class BinarySearch {
    // Pred: args.length >= 1, args[0] - number, args[1..args.length] - numbers, sorted non-ascending
    // Post: program completion
    public static void main(String[] args) {
        // Pred
        int x = Integer.parseInt(args[0]);
        // x == int(args[0])
        int[] a = new int[args.length - 1];
        // a[args.length - 1], x
        for (int i = 1; i < args.length; ++i) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        // a[] - int(args[1..length)), x
//         :NOTE: only one method is tested
        int recursiveResult = recursiveSearch(a, x, 0, a.length - 1);
        // recursiveResult = recursiveSearch(a, x, 0, a.length - 1)
//        int iterativeResult = iterativeSearch(a, x);
        // iterativeResult = iterativeSearch(a, x)
//        assert recursiveResult == iterativeResult;
        // recursiveResult == iterativeResult
        System.out.println(recursiveResult);
    }

    // Pred: l >= 0, r < a.length, l <= r (not in recursion), integer a[l..r) is sorted non-ascending
    // Post: R = min(i: a[i] <= x)
    public static int recursiveSearch(int[] a, int x, int l, int r) {
        // Pred
        if (l <= r) {
            // l <= r
            int m = (l + r) / 2;
            // l <= m <= r
            if (a[m] > x) {
                // l <= m <= r, a[m] > x
                return recursiveSearch(a, x, m + 1, r);
                // l' = m + 1, r' = r, a[m] <= x,
                // r' - l' == r - ⌊(l + r) / 2⌋ - 1, r' >= l' - 1
            } else {
                // l <= m <= r, a[m] <= x
                return recursiveSearch(a, x, l, m - 1);
                // l' = l, r' = m - 1, a[m] > x,
                // r' - l' == ⌊(l + r) / 2⌋ - l + 1, r' >= l'
            }
        }
        // l >= r
        // l == r + 1,
        // l == min(i: a[i] <= x)
        return l;
    }

    // Pred: integer array a[] is sorted non-ascending, l <= r
    // Post: R = min(i: a[i] <= x)
    public static int iterativeSearch(int[] a, int x) {
        // Pred
        if (a.length == 0) {
            // a.length == 0 -> the only place to put x is 0 pos
            return 0;
        }
        // a.length > 0
        int l = -1, r = a.length;
        // r - l >= 1
        while (r - l > 1) {
            int m = (l + r) / 2;
            // r - l > 1, m <= l <= r
            if (a[m] > x) {
                // r - l > 1, m <= l <= r, a[m] > x
                l = m;
                // r' == r, l' == m,
                // r' - l' == r - ⌊(l + r) / 2⌋, r' >= l'
            } else {
                // r - l > 1, m <= l <= r, a[m] <= x
                r = m;
                // r' == m, l' == l,
                // r' - l' == ⌊(l + r) / 2⌋ - l, r' >= l'
            }
//             :NOTE: not really 1/2. It makes termination proof less obvious.
            // r' - l' ~ 1/2(r - l), r' >= l'
        }
        // r - l <= 1, r == min(i: a[i] <= x)
        return r;
    }
}
