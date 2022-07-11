package aira;

import java.util.Arrays;

import aira.quasi.Index;
import aira.quasi.Unsafe;

import static aira.quasi.QuasiFunction.*;

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
        pass.invoke(invoke(inlineFunction)); 

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

    public static final Option trialOption = (f, g) -> {
        try { return f.invoke(); } catch (Throwable throwable) {
            throwable.printStackTrace();
            return g.invoke();
        }
    };
    public static final Eval trialEval = f -> trialOption.invoke(f, nil);

// Array Functions ...
    public static final one_t<Object[], Object> head = xs -> xs[0];
    public static final one_t<Object[], Object> last = xs -> xs[xs.length - 1];
    public static final Aut<Object[]> lizard = xs -> Arrays.copyOf(xs, xs.length - 1);
    public static final Aut<Object[]> tail = xs -> Arrays.copyOfRange(xs, 1, xs.length - 1);

    public static final two_t<Aut<Object>, Object[], Object[]> map = (f, xs) -> {
        var rs = new Object[xs.length];
        for (int index = 0; index < rs.length; index++)
            rs[index] = f.invoke(xs[index]);
        return rs;
    };

    public interface Fold extends three_t<two_t<?, ?, ?>, Object, Object[], Object> {}
    public static final Fold foldl = (f, init, xs) -> {
        Object phase = init;
        for (int index = 0; index < xs.length; index++)
            phase = f.invoke(Unsafe.as(phase), Unsafe.as(xs[index]));
        return phase;
    };
    public static final Fold foldr = (f, init, xs) -> {
        Object phase = init;
        for (int index = xs.length - 1; index > -1; index--)
            phase = f.invoke(Unsafe.as(xs[index]), Unsafe.as(phase));
        return phase;
    };

    public static final Lift<Integer> range = (first, last) -> {
        Integer[] xs = new Integer[last - first + 1];
        for (int index = 0; index < xs.length; index++)
            xs[index] = first + index;
        return xs;
    };

    public static final Lift<Object[]> zip = (xs, ys) -> {
        Object[][] pairs = new Object[xs.length][2];
        for (int index = 0; index < xs.length; index++) {
            pairs[index][0] = xs[index];
            pairs[index][1] = ys[index];
        }
        return pairs;
    };

    public static final Cup<Object[]> indexed = xs -> 
        zip.invoke(xs, range.invoke(0, xs.length - 1));

    public static final any_t<Object[]> arr = xs -> xs;

    public static final Cup<Object> cup = x -> new Object[] { x };

    public static final Clip<Object> add = (xs, elem) -> {
        Object[] array = Arrays.copyOf(xs, xs.length + 1);
        array[xs.length] = elem;
        return array;
    };

    public static final Clip<Object> embed = (xs, embed) -> {
        final int supIndex = xs.length - 1;
        Object[] array = new Object[2 * xs.length - 1];
        for (int index = 0; index < supIndex; index++) {
            array[index * 2] = xs[index];
            array[index * 2 + 1] = embed;
        }
        array[array.length - 1] = xs[supIndex];
        return array;
    };

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
