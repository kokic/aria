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
        var state = foreach.invoke(Comb.join(1, 5, 3).apply(() -> " ", () -> "\n"));
        state.invoke((one_void<Object>) elem -> System.out.print(elem));

        state = Unsafe.as(invokeUniversal(foreach, Comb.join(Comb.each(1, 5, 3)
                .apply(x -> (int) x - 3))
                .apply(() -> " ", () -> "\n")));
        state.invoke((one_void<Object>) elem -> System.out.print(elem));

        foreach.invoke("Commutative Algebra", "Homological Algebra").invoke(println);
        foreach.invoke((Object[]) any.class.getMethods()).invoke(println);

    }
}
