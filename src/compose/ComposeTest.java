package compose;

import quasi.QuasiFunction;
import quasi.QuasiFunction.one;
import quasi.QuasiFunction.one_void;
import quasi.QuasiFunction.zero;

public class ComposeTest {
    
    public static void main(String[] args) {
        
        var f = (one_void<String>) in -> System.out.println(in);
        var g = (zero<String>) () -> "Hello World";
        var id = (one<Object, Object>) x -> x;
        var fg = Comb.with(f, id, id, id, g);
        
        QuasiFunction.invokeUniversal(fg);
        
    }
    
}
