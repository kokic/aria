package aira;

import java.util.Arrays;

import aira.quasi.Unsafe;
import aira.quasi.QuasiFunction.one_t;
import aira.quasi.QuasiFunction.two_t;

public final class Blur {

    public static final Object head(Object[] xs) {
        return xs[0];
    }

    public static final Object last(Object[] xs) {
        return xs[xs.length - 1];
    }

    public static final Object[] tail(Object[] xs) {
        return Arrays.copyOfRange(xs, 1, xs.length - 1);
    }

    public static final Object[] lizard(Object[] xs) {
        return Arrays.copyOf(xs, xs.length - 1);
    }

    public static final Object[] map(one_t<Object, Object> f, Object[] xs) {
        var rs = new Object[xs.length];
        for (int index = 0; index < rs.length; index++)
            rs[index] = f.invoke(xs[index]);
        return rs;
    }

    public static final Object foldl(two_t<Object, Object, Object> f, Object first, Object[] xs) {
        Object phase = first;
        for (int index = 0; index < xs.length; index++)
            phase = f.invoke(phase, xs[index]);
        return phase;
    }

    public static final Object foldr(two_t<?, ?, ?> f, Object first, Object[] xs) {
        Object phase = first;
        for (int index = xs.length - 1; index > -1; index--)
            phase = f.invoke(Unsafe.as(xs[index]), Unsafe.as(phase));
        return phase;
    }

    public static Object[] range(int first, int last) {
        Object[] xs = new Object[last - first + 1];
        for (int index = 0; index < xs.length; index++)
            xs[index] = first + index;
        return xs;
    }

    public static final Object[][] zip(Object[] xs, Object[] ys) {
        Object[][] pairs = new Object[xs.length][2];
        for (int index = 0; index < xs.length; index++) {
            pairs[index][0] = xs[index];
            pairs[index][1] = ys[index];
        }
        return pairs;
    }

    public static final Object[][] indexed(Object[] xs) {
        return zip(xs, range(0, xs.length - 1));
    }










    public static final Object[] array(Object... elements) {
        return elements;
    }

    public static final Object[] shell(Object args) {
        return new Object[] { args };
    }


    public static final Object[] add(Object[] xs, Object elem) {
        Object[] array = Arrays.copyOf(xs, xs.length + 1);
        array[xs.length] = elem;
        return array;
    }
    
    public static final Object[] embed(Object[] xs, Object embed) {
        final int supIndex = xs.length - 1;
        Object[] array = new Object[2 * xs.length - 1];
        for (int index = 0; index < supIndex; index++) {
            array[index * 2] = xs[index];
            array[index * 2 + 1] = embed;
        }
        array[array.length - 1] = xs[supIndex];
        return array;
    }
}
