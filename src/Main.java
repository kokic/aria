import static quasi.QuasiExpress.pack;
import static quasi.QuasiExpress.pass;
import static quasi.QuasiFunction.foreach;
import static quasi.QuasiFunction.invokeBase;
import static quasi.QuasiFunction.println;

import java.util.Arrays;

import compose.Comb;
import quasi.QuasiFunction.any;
import quasi.QuasiFunction.one_void;
import quasi.Unsafe;

public class Main {

    public static void qsort(int[] alist, int first, int last) {
        while (!pass(first < last && pack(() -> {
            class Local {
                static void invoke(int[] alist, int first, int last, int[] cap) {
                    while (!(pack(() -> {
                        while (cap[1] < cap[2] 
                        && pack(() -> { while (cap[1] < cap[2] 
                            && alist[cap[2]] >= cap[0] 
                            && pass(cap[2] -= 1)) {} }) 
                        && pass(alist[cap[1]] = alist[cap[2]]) 
                        && pack(() -> { while (cap[1] < cap[2] 
                            && alist[cap[1]] < cap[0] 
                            && pass(cap[1] += 1)) {} })
                        && pass(alist[cap[2]] = alist[cap[1]])) {}
                    })
                    && pass(alist[cap[1]] = cap[0])
                    && pack(() -> qsort(alist, first, cap[1] - 1))
                    && pack(() -> qsort(alist, cap[1] + 1, last)) )) {}
                }
            }
            while (!(pack(() -> Local.invoke(alist, first, last,
                    new int[] { alist[first], first, last })))) {}
        }))) {}
    }

    public static void main(String[] args) throws Exception {

        int[] alist = new int[] { 1, -4, 6, 77, -98 };
        qsort(alist, 0, alist.length - 1);
        System.out.println(Arrays.toString(alist));
        
        
        System.out.println(">>> test: ");
        invokeBase(println, "good");

        System.out.println(">>> foreach: ");
        var state = foreach.invoke(Comb.join(1, 5, 3).apply(() -> " ", () -> "\n"));
        state.invoke((one_void<Object>) elem -> System.out.print(elem));

        state = Unsafe.as(invokeBase(foreach, Comb.join(Comb.each(1, 5, 3)
                .apply(x -> (int) x - 3))
                .apply(() -> " ", () -> "\n")));
        state.invoke((one_void<Object>) elem -> System.out.print(elem));

        foreach.invoke("Commutative Algebra", "Homological Algebra").invoke(println);
        foreach.invoke((Object[]) any.class.getMethods()).invoke(println);

    }
}
