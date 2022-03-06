package compose;

import static quasi.QuasiFunction.invokeUniversal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import quasi.QuasiFunction;
import quasi.QuasiFunction.any;
import quasi.QuasiFunction.zero;

public class Comb {
    
    // instance : QuasiFunction.base -> Type[]
    public static final QuasiFunction.any<Type[]> getGenericTypes = (
        Object... args) -> ((ParameterizedType) (args[0]).getClass()
                .getGenericInterfaces()[args.length == 1 ? 0 : (int) args[1]])
                .getActualTypeArguments();

    public static QuasiFunction.base with(QuasiFunction.base f, QuasiFunction.base g) {
        // f: A -> B, g: C -> D, fg: C -> (D as A) -> B
        // getGenericTypes.invoke(f)[0]
         
        Class<?> clazz = g.getClass().getInterfaces()[0];
        return clazz.getSimpleName().startsWith("zero")
            ? (zero<Object>) () -> invokeUniversal(f, invokeUniversal(g))
            : (any<Object>) args -> invokeUniversal(f, invokeUniversal(g, args));
        // -> fg.invoke() or fg.invoke(any...)
    }

    public static QuasiFunction.base with(QuasiFunction.base... fs) {
        QuasiFunction.base phase = fs[fs.length - 1];
        for (int i = fs.length - 2; i > -1; --i)
            phase = with(fs[i], phase);
        return phase;
    }


}
