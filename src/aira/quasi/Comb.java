package aira.quasi;

import static aira.quasi.QuasiFunction.invoke;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import aira.Blur;
import aira.quasi.QuasiFunction.any_t;
import aira.quasi.QuasiFunction.base;
import aira.quasi.QuasiFunction.two_t;
import aira.quasi.QuasiFunction.zero;
import aira.quasi.QuasiFunction.zero_t;

public class Comb {
    
    // instance: base -> Type[]
    public static final any_t<Type[]> getGenericTypes = (
        Object... args) -> ((ParameterizedType) (args[0]).getClass()
                .getGenericInterfaces()[args.length == 1 ? 0 : (int) args[1]])
                .getActualTypeArguments();

    // f: Y -> Z, g: X -> Y, fg: X -> Z
    public static two_t<base, base, base> with = (f, g) -> g instanceof zero
            ? (zero_t<Object>) () -> invoke(f, invoke(g))
            : (any_t<Object>) args -> invoke(f, invoke(g, args));

    public static any_t<base> withs = 
       fs -> (base) Blur.foldr(with, Blur.last(fs), Blur.lizard(fs));

}
