package aira;

import aira.quasi.Index;
import aira.quasi.QuasiFunction;
import aira.quasi.QuasiFunction.any_t;
import aira.quasi.QuasiFunction.base;
import aira.quasi.QuasiFunction.one_bool;
import aira.quasi.QuasiFunction.one_void;
import aira.quasi.QuasiFunction.three_u;
import aira.quasi.QuasiFunction.zero_t;
import aira.quasi.QuasiFunction.zero_void;
import aira.quasi.Unsafe;

public final class Prelude {

    public interface RunnableT { void run() throws Throwable; }
	public interface RunnableE { void run() throws Exception; }

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

    public static final one_void<RunnableT> ignoreThrowable = runnable -> {
        try { runnable.run(); } catch (Throwable throwable) {}
    };

    public static final one_void<RunnableT> ignoreThrowableTrial = runnable -> {
        try { runnable.run(); } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    };


// Extend Functions ...

    public static final any_t<one_void<? super Object>> foreach = args -> {
        return (one_void<? super Object>) apply -> {
            Index index = new Index();
            while (keep.invoke(index.less(args.length))
                && pass.invoke(index.custom((base) apply, args))
                && pass.invoke(index.increase())) 
            {}
        };
    };

    public static final one_bool<Object> print = arg -> pack.invoke(() -> System.out.print(arg));
    public static final one_bool<Object> println = arg -> pack.invoke(() -> System.out.println(arg));
}
