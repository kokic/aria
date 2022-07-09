package aira.quasi;

import static aira.Blur.foldr;

import aira.quasi.QuasiFunction.any_t;
import aira.quasi.QuasiFunction.one_t;
import aira.quasi.QuasiFunction.one_void;
import aira.quasi.QuasiFunction.zero_t;

public class ComposeTest {
    
    public static void main(String[] args) {

        var f = (one_void<String>) in -> System.out.println(in);
        var g = (zero_t<String>) () -> "Hello World";
        zero_t<Object> fg = Unsafe.as(Comb.with.invoke(f, g));
        fg.invoke();
        QuasiFunction.invoke(fg);

        var gX = (one_t<Object, String>) x -> "Hello World with " + x;
        any_t<Object> fgX = Unsafe.as(Comb.with.invoke(f, gX));
        fgX.invoke(233);
        QuasiFunction.invoke(fgX, 233);


        /*
        Integer[] xs = { 4, 9, -3 };
        Object r = foldr((x, y) -> (int) x + (int) y, 1, xs);
        System.out.println(r);
        */

        zero_t<Object> fg2 = Unsafe.as(foldr(Comb.with, g, new Object[] { f }));
        fg2.invoke();
        
        // Unsafe.<zero_t<Object>>as(fg).invoke()
        // QuasiFunction.invoke(fg);
        // Unsafe.<any_t<Object>>as(fgX).invoke(31);

    }

}
