package compose;

import java.util.ArrayList;

import quasi.QuasiFunction;
import quasi.QuasiFunction.one_void;
import quasi.QuasiFunction.zero;

public class ComposeTest {
    
    public static void main(String[] args) {
        
        var f = (one_void<String>) in -> System.out.println(in);
        var g = (zero<String>) () -> "Hello World";
        var fg = Comb.with(f, g);


        var list = new ArrayList<Object>();
        list.add("good");
        list.add("bad");
        list.add("flat");
        list.forEach(System.out::println);

        QuasiFunction.invokeUniversal(fg);
        
    }
    
}
