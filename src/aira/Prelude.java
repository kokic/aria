package aira;

import static aira.quasi.QuasiFunction.invoke;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aira.quasi.Index;
import aira.quasi.QuasiFunction.Aut;
import aira.quasi.QuasiFunction.Clip;
import aira.quasi.QuasiFunction.Cup;
import aira.quasi.QuasiFunction.Lift;
import aira.quasi.QuasiFunction.any_t;
import aira.quasi.QuasiFunction.one_bool;
import aira.quasi.QuasiFunction.one_t;
import aira.quasi.QuasiFunction.one_void;
import aira.quasi.QuasiFunction.three_t;
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
 
// List Functions ...
    public static final one_t<List<Object>, Object> head = xs -> xs.get(0);
    public static final one_t<List<Object>, Object> last = xs -> xs.get(xs.size() - 1);
    public static final Aut<List<Object>> lizard = xs -> xs.subList(0, xs.size() - 1);
    public static final Aut<List<Object>> tail = xs -> xs.subList(1, xs.size());

    public interface Map extends two_t<Aut<Object>, List<Object>, List<Object>> {}
    public static final Map map = (f, xs) -> {
        List<Object> rs = new ArrayList<Object>(xs);
        int size = rs.size();
        for (int index = 0; index < size; index++)
            rs.set(index, f.invoke(rs.get(index)));
        return rs;
    };

    public interface Fold extends three_t<two_t<?, ?, ?>, Object, List<Object>, Object> {}
    public static final Fold foldl = (f, init, xs) -> {
        Object phase = init;
        for (int index = 0; index < xs.size(); index++)
            phase = f.invoke(Unsafe.as(phase), Unsafe.as(xs.get(index)));
        return phase;
    };
    public static final Fold foldr = (f, init, xs) -> {
        Object phase = init;
        for (int index = xs.size() - 1; index > -1; index--)
            phase = f.invoke(Unsafe.as(xs.get(index)), Unsafe.as(phase));
        return phase;
    };

    public static final two_t<Integer, Integer, List<Object>> range = (first, last) -> {
        Object[] xs = new Object[last - first + 1];
        for (int index = 0; index < xs.length; index++)
            xs[index] = first + index;
        return Arrays.asList(xs);
    };

    public static final Lift<List<Object>> zip = (xs, ys) -> {
        ArrayList<List<Object>> pairs = new ArrayList<List<Object>>();
        int infsup = Math.max(xs.size(), ys.size());
        for (int index = 0; index < infsup; index++) {
            ArrayList<Object> pair = new ArrayList<Object>();
            pair.add(xs.get(index));
            pair.add(ys.get(index));
            pairs.set(index, pair);
        }
        return pairs;
    };

    public static final Cup<List<Object>> indexed = xs -> 
        zip.invoke(xs, range.invoke(0, xs.size() - 1));

    public static final any_t<Object[]> arr = xs -> xs;
    public static final any_t<Object[]> cup = x -> new Object[] { x };
    public static final any_t<List<Object>> list = xs -> Arrays.asList(xs);
    public static final any_t<List<Object>> lisp = xs -> list.invoke(cup.invoke(xs));

    public static final one_t<Object, List<Object>> toList = xs -> {
        List<Object> rs = new ArrayList<Object>();
        final int length = Array.getLength(xs);
        for (int index = 0; index < length; index++)
            rs.add(Array.get(xs, index));
        return rs;
    };

    public static final Clip<Object> add = (xs, elem) -> {
        ArrayList<Object> rs = new ArrayList<Object>(xs);
        rs.add(elem);
        return rs;
    };

    public static final Clip<Object> embed = (xs, embed) -> {
        final int sup = xs.size() - 1;
        ArrayList<Object> rs = new ArrayList<Object>();
        for (int index = 0; index < sup; index++) {
            rs.add(xs.get(index));
            rs.add(embed);
        }
        rs.add(xs.get(sup));
        return rs;
    };

// Extend Functions ...

    public static final one_t<String, List<Object>> toCharList = x -> toList.invoke(x.toCharArray());

    public static final any_t<one_void<one_void<?>>> foreach = args -> {
        return (one_void<one_void<?>>) apply -> {
            Index index = new Index();
            while (keep.invoke(index.less.invoke(args.length))
                && pass.invoke(index.apply.invoke(apply, args))
                && pass.invoke(index.increase.invoke())) 
            {}
        };
    };

    public static final one_t<Object, any_t<Object>> switchIt = x -> args -> {
        final int sup = args.length - 1;
        for (int index = 0; index < sup; index += 2) {
            Object key = args[index];
            Object val = args[index + 1];
            boolean isArray = key instanceof Object[];
            boolean caseElem = key.equals(x);
            boolean caseGroup = isArray && Arrays.asList((Object[]) key).contains(x);
            if (caseElem || caseGroup)
                return val;
        }
        return args[args.length - 1];
    };

    public static final Aut<Object> print = arg -> pack.invoke(() -> System.out.print(arg));
    public static final Aut<Object> println = arg -> pack.invoke(() -> System.out.println(arg));
}
