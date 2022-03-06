import static quasi.QuasiFunction.invokeUniversal;
import static quasi.QuasiFunction.println;
import static quasi.QuasiFunction.foreach;

import compose.Comb;
import quasi.QuasiFunction.any;
import quasi.QuasiFunction.one_void;
import quasi.Unsafe;

public class Main {

    public static void main(String[] args) {
        
        System.out.println(">>> test: ");
        invokeUniversal(println, "good");

        System.out.println(">>> foreach: ");
        var state = foreach.invoke(Comb.join(233, 514, 0x7a96)
                .toArray(() -> " ", () -> "\n"));
        state.invoke((one_void<Object>) elem -> System.out.print(elem));

        state = Unsafe.as(invokeUniversal(foreach, Comb.join(1, 5, 3)
                .toArray(() -> " ", () -> "\n")));
        state.invoke((one_void<Object>) elem -> System.out.print(elem));

        foreach.invoke((Object[]) any.class.getMethods()).invoke(println);

        // QuasiFunction.invokeUniversal(foreach, (Object[])
        // QuasiFunction.any.class.getMethods());

        // int value = 2;

        /*
         * coodition ? left : right;
         * 
         * QuasiExpress.expr((coodition
         * && QuasiExpress.pass(left))
         * || QuasiExpress.pass(right));
         */

        /*
         * QuasiExpress.expr((5 > 3
         * && QuasiExpress.pass(++value))
         * || QuasiExpress.pass(--value));
         */

        // System.out.println("value: " + value);
    }
}
