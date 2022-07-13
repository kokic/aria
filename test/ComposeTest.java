
import aira.quasi.Comb;
import aira.quasi.QuasiFunction;
import aira.quasi.Unsafe;
import aira.quasi.QuasiFunction.Aut;
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

        var id = (Aut<Object>) x -> x;
        zero_t<Object> fgId = Unsafe.as(Comb.withs.invoke(f, id, id, id, g));
        fgId.invoke();

        var gX = (one_t<Object, String>) x -> "Hello World with " + x;
        any_t<Object> fgX = Unsafe.as(Comb.with.invoke(f, gX));
        fgX.invoke(157);
        QuasiFunction.invoke(fgX, 157);
    }

}
