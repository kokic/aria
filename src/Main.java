
import static aira.Prelude.add;
import static aira.Prelude.embed;
import static aira.Prelude.list;
import static aira.Prelude.map;
import static aira.Prelude.pack;

import java.util.List;

import aira.quasi.Comb;
import aira.quasi.QuasiFunction.Aut;
import aira.quasi.QuasiFunction.any_t;
import aira.quasi.Unsafe;

public class Main {

    public static void main(String[] args) throws Exception {

        /*
        int[] alist = new int[] { 1, -4, 6, 77, -98 };
        qsort(alist, 0, alist.length - 1);
        System.out.println(Arrays.toString(alist));
        */
        
        var alist = list.invoke(1, 2, 3);
        Aut<List<Object>> embedSpace = xs -> embed.invoke(xs, " ");
        Aut<List<Object>> addLine = xs -> add.invoke(xs, "\n");
        any_t<List<Object>> handle = Unsafe.as(Comb.with.invoke(addLine, embedSpace));
        map.invoke(x -> pack.invoke(() -> System.out.print(x)), handle.invoke(alist));

        /*
        array = (Object[]) invoke(embedSpace, cup.invoke(array));
        array = (Object[]) invokeExact(addLine, lisp.invoke(array));
        var state = foreach.invoke(array);
        state.invoke(print);
        */


        /*
        state = Unsafe.as(invokeBase(foreach, Comb.join(Comb.each(1, 5, 3)
                .apply(x -> (int) x - 3))
                .apply(() -> " ", () -> "\n")));
        state.invoke((one_void<Object>) elem -> System.out.print(elem));
        */

        // foreach.invoke("Commutative Algebra", "Homological Algebra").invoke(println);
        // foreach.invoke((Object[]) any_t.class.getMethods()).invoke(println);
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
