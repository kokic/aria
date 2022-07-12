package aira.quasi;

import static aira.Prelude.eval;
import static aira.Prelude.lisp;
import static aira.Prelude.list;
import static aira.Prelude.trialEval;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class QuasiFunction {

    public interface base {}

    public interface any extends base {}
    public interface any_t<r> extends any { r invoke(Object... args); }
    public interface any_void extends any { void invoke(Object... args); }
    public interface any_bool extends any { boolean invoke(Object... args); }

    public interface zero extends base {}
    public interface zero_t<r> extends zero { r invoke(); }
    public interface zero_void extends zero { void invoke(); }
    public interface zero_bool extends zero { boolean invoke(); }
    public interface zero_u extends zero { <u> u invoke(); }
    public interface zero_bool_u extends zero { <u> boolean invoke(); }
    public interface zero_throw_t<r> extends zero { r invoke() throws Throwable; }
    public interface zero_throw_void extends zero { void invoke() throws Throwable; }

    public interface one extends base {}
    public interface one_t<tX, r> extends one { r invoke(tX x); }
    public interface one_void<tX> extends one { void invoke(tX x); }
    public interface one_bool<tX> extends one { boolean invoke(tX x); }

    public interface two extends base {}
    public interface two_t<tX, tY, r> extends two { r invoke(tX x, tY y); }

    public interface three extends base {}
    public interface three_t<tX, tY, tZ, r> extends three { r invoke(tX x, tY y, tZ z); }
    public interface three_u<tX, tY, tZ> extends three { <u> u invoke(tX x, tY y, tZ z); }

// alias interface
    public interface Aut<X> extends one_t<X, X> {};
    public interface Cup<X> extends one_t<X, List<X>> {};
    public interface Cap<X> extends one_t<List<X>, X> {};
    public interface Lift<X> extends two_t<X, X, List<X>> {};
    public interface Clip<X> extends two_t<List<X>, X, List<X>> {};

// invoke

    public static Object invokeExact(base func, List<Object> args) {
        Method[] methods = func.getClass().getMethods();
        Method invoke = null;
        for (int index = 0; index < methods.length; index++) {
            if (!methods[index].getName().equals("invoke"))
                continue;
            invoke = methods[index];
            break;
        }
        invoke.setAccessible(true);

        Object result = null;
        List<Object> list = new ArrayList<Object>();
        list.add(func);
        list.addAll(args);

        final Method method = invoke;
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodHandle methodHandle = (MethodHandle) eval.invoke(() -> lookup.unreflect(method));
        result = trialEval.invoke(() -> methodHandle.invokeWithArguments(list));

        return result; 
    }

    public static Object invoke(base func, Object... args) {
        boolean shouldCup = func instanceof any;
        List<Object> alist = shouldCup ? lisp.invoke(args) : list.invoke(args);
        return invokeExact(func, alist);
    }

}
