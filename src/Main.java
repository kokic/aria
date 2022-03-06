import quasi.*;
import quasi.QuasiFunction.any;
import quasi.QuasiFunction.base;
import quasi.QuasiFunction.one;
import quasi.QuasiFunction.one_void;

public class Main {

    public static final QuasiFunction.any<one_void<base>> foreach = args -> {
        return (one_void<base>) apply -> {
            Ind index = new Ind(0);
            QuasiExpress.pass(index.clear());
            while (QuasiExpress.keep(index.less(args.length))
                && QuasiExpress.keep(index.custom(apply, args))
                && QuasiExpress.pass(index.increase())) 
            {};
        };
    };
    
    
    public static void main(String[] args)
    {
        System.out.println(">>> test: ");
        QuasiFunction.invokeUniversal(QuasiFunction.println, "good");

        System.out.println(">>> foreach: ");
        var state = foreach.invoke(233, 514, 0x7a96); // .invoke(QuasiFunction.println);
        state.invoke( (one<Object, Object>) elem -> QuasiFunction.println.invoke(elem) );

        foreach.invoke((Object[]) any.class.getMethods()).invoke(QuasiFunction.println);

        state = Unsafe.as(QuasiFunction.invokeUniversal(foreach, 1, 5, 3));
        state.invoke( (one<Object, Object>) elem -> QuasiFunction.println.invoke(elem) );
        
        // QuasiFunction.invokeUniversal(foreach, (Object[]) QuasiFunction.any.class.getMethods());
        
        // int value = 2; 
        
        /* 
        coodition ? left : right;

        QuasiExpress.expr((coodition
            && QuasiExpress.pass(left))
            || QuasiExpress.pass(right)); 
        */

        /* QuasiExpress.expr((5 > 3
            && QuasiExpress.pass(++value)) 
            || QuasiExpress.pass(--value)); */
        
        // System.out.println("value: " + value);
    }
}
