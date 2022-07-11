
import static aira.quasi.QuasiFunction.*;

import aira.quasi.Comb;
import aira.quasi.Unsafe;

import static aira.Prelude.*;

public class Main {

    public static void main(String[] args) throws Exception {

        /*
        int[] alist = new int[] { 1, -4, 6, 77, -98 };
        qsort(alist, 0, alist.length - 1);
        System.out.println(Arrays.toString(alist));
        */

        System.out.println(">>> foreach: ");
        var array = arr.invoke(1, 2, 3);
        
        Aut<Object[]> embedSpace = xs -> embed.invoke(xs, " ");
        Aut<Object[]> addLine = xs -> add.invoke(xs, "\n");
        // any_t<Object> handle = Unsafe.as(Comb.with.invoke(addLine, embedSpace));
        
        var state = foreach.invoke((Object[]) invoke(embedSpace, cup.invoke(array)));
        state.invoke(print);

        /*
        state = Unsafe.as(invokeBase(foreach, Comb.join(Comb.each(1, 5, 3)
                .apply(x -> (int) x - 3))
                .apply(() -> " ", () -> "\n")));
        state.invoke((one_void<Object>) elem -> System.out.print(elem));
        */

        foreach.invoke("Commutative Algebra", "Homological Algebra").invoke(println);
        foreach.invoke((Object[]) any_t.class.getMethods()).invoke(println);
    }


    /*
    public static void qsort(int[] alist, int first, int last) {
        while (!pass.invoke(first < last && pack.invoke(() -> {
            class Local {
                static void invoke(int[] alist, int first, int last, int[] cap) {
                    while (!(pack.invoke(() -> {
                        while (cap[1] < cap[2] 
                        && pack.invoke(() -> { while (cap[1] < cap[2] 
                            && alist[cap[2]] >= cap[0] 
                            && pass.invoke(cap[2] -= 1)) {} }) 
                        && pass.invoke(alist[cap[1]] = alist[cap[2]]) 
                        && pack.invoke(() -> { while (cap[1] < cap[2] 
                            && alist[cap[1]] < cap[0] 
                            && pass.invoke(cap[1] += 1)) {} })
                        && pass.invoke(alist[cap[2]] = alist[cap[1]])) {}
                    })
                    && pass.invoke(alist[cap[1]] = cap[0])
                    && pack.invoke(() -> qsort(alist, first, cap[1] - 1))
                    && pack.invoke(() -> qsort(alist, cap[1] + 1, last)) )) {}
                }
            }
            while (!(pack.invoke(() -> Local.invoke(alist, first, last,
                    new int[] { alist[first], first, last })))) {}
        }))) {}
    }
    */
}
