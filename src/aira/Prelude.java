package aira;

import aira.quasi.Index;
import aira.quasi.QuasiFunction;
import aira.quasi.QuasiFunction.any_t;
import aira.quasi.QuasiFunction.base;
import aira.quasi.QuasiFunction.one_bool;
import aira.quasi.QuasiFunction.one_t;
import aira.quasi.QuasiFunction.one_void;
import aira.quasi.QuasiFunction.three_u;
import aira.quasi.QuasiFunction.two_t;
import aira.quasi.QuasiFunction.zero_t;
import aira.quasi.QuasiFunction.zero_throw_t;
import aira.quasi.QuasiFunction.zero_throw_void;
import aira.quasi.QuasiFunction.zero_void;
import aira.quasi.Unsafe;

public final class Prelude {

    public static final three_u<Boolean, Object, Object> delta = 
                    new three_u<Boolean, Object, Object>() {
        public <u> u invoke(Boolean condition, Object trueCase, Object falseCase) {
            return Unsafe.as(condition ? trueCase : falseCase);
        }
    };
    
    public static final three_u<Boolean, zero_t<Object>, zero_t<Object>> deltaIf = 
                    new three_u<Boolean, zero_t<Object>, zero_t<Object>>() {
        public <u> u invoke(Boolean condition, zero_t<Object> trueCase, zero_t<Object> falseCase) {
            return delta.<zero_t<u>>invoke(condition, trueCase, falseCase).invoke();
        }
    };

    public static final one_void<Object> expr = x -> {};
    public static final one_bool<Boolean> keep = x -> x;
    public static final one_bool<Object> pass = x -> true;
    
    public static final one_bool<zero_void> pack = inlineFunction ->
        pass.invoke(QuasiFunction.invoke(inlineFunction)); 

    public static final one_void<zero_throw_void> ignore = f -> {
        try { f.invoke(); } catch (Throwable throwable) {}
    };

    public static final one_void<zero_throw_void> trial = f -> {
        try { f.invoke(); } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }; 

    public interface Option extends two_t<zero_throw_t<Object>, zero_t<Object>, Object> {}
    public static final Option option = (f, g) -> {
        try { return f.invoke(); } catch (Throwable throwable) { 
            return g.invoke();
        }
    };

    public interface Eval extends one_t<zero_throw_t<Object>, Object> {}
    public static final zero_t<Object> nil = () -> null;
    public static final Eval eval = f -> option.invoke(f, nil);
 
// Extend Functions ...

    public static final any_t<one_void<base>> foreach = args -> {
        return (one_void<base>) apply -> {
            Index index = new Index();
            while (keep.invoke(index.less.invoke(args.length))
                && pass.invoke(index.apply.invoke(apply, args))
                && pass.invoke(index.increase.invoke())) 
            {}
        };
    };

    public static final one_bool<Object> print = arg -> pack.invoke(() -> System.out.print(arg));
    public static final one_bool<Object> println = arg -> pack.invoke(() -> System.out.println(arg));
}
