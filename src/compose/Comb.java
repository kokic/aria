package compose;

import static quasi.QuasiFunction.invokeUniversal;
import static quasi.QuasiExpress.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import quasi.Ind;
import quasi.QuasiFunction;
import quasi.QuasiFunction.any;
import quasi.QuasiFunction.one;
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
        for (Ind index = new Ind(fs.length - 2); index.great(-1); index.decrease())
            phase = with(fs[index.value()], phase);
        return phase;
    }


    public interface Haidilao { Object[] apply(one<Object, Object> apply); }

    public static final Haidilao each(Object... elements) {
        return apply -> {
            Object[] array = new Object[elements.length];
            Ind index = new Ind();
            while (keep(index.less(elements.length))
                && pass(index.assign(array, apply.invoke(elements[index.value()])))
                && pass(index.increase())) 
            {};
            return array;
        };
    }

    public interface ZeroObject extends zero<Object> {}

    /* 
    applies: 
        apply to between, 
        apply to last, 
    */
    public interface Jhon23 { Object[] apply(ZeroObject... applies); }
    
    public static final Jhon23 join(Object... elements) {
        return applies -> {
            Object[] array = new Object[2 * elements.length - 1];
            final int alien = array.length - 1;
            for (int index = 0; index < elements.length - 1; ++index) {
                array[index * 2] = elements[index];
                array[index * 2 + 1] = applies[0].invoke();
            }
            array[alien] = elements[elements.length - 1];

            if (applies.length > 1) {
                Object last = applies[1].invoke();
                Object elem = array[alien];
                if (last instanceof String)
                    array[alien] = elem + (String) last;
                else if (last instanceof Integer && elem instanceof Integer)
                    array[alien] = (Integer) elem + (Integer) last;
            }

            return array;
        };
    }
    
}
