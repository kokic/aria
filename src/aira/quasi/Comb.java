package aira.quasi;

import static aira.Prelude.foldr;
import static aira.Prelude.last;
import static aira.Prelude.list;
import static aira.Prelude.lizard;
import static aira.quasi.QuasiFunction.invoke;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import aira.quasi.QuasiFunction.any_t;
import aira.quasi.QuasiFunction.base;
import aira.quasi.QuasiFunction.one_t;
import aira.quasi.QuasiFunction.two_t;
import aira.quasi.QuasiFunction.zero;
import aira.quasi.QuasiFunction.zero_t;

public class Comb {

    // 
    public static final one_t<Object, Class<?>> clazz = 
        x -> x instanceof Class ? (Class<?>) x : x.getClass();

    // instance: base -> Type[]
    public static final any_t<Type[]> getGenericTypes = (
        Object... args) -> ((ParameterizedType) clazz.invoke(args[0])
                .getGenericInterfaces()[args.length == 1 ? 0 : (int) args[1]])
                .getActualTypeArguments();

    // f: Y -> Z, g: X -> Y, fg: X -> Z
    public static final two_t<base, base, base> with = (f, g) -> g instanceof zero
            ? (zero_t<Object>) () -> invoke(f, invoke(g))
            : (any_t<Object>) args -> invoke(f, invoke(g, args));

    public static final any_t<base> withs = fs -> (base) 
        foldr.invoke(with, last.invoke(list.invoke(fs)), lizard.invoke(list.invoke(fs)));
        
}
