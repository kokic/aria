package quasi;

import static quasi.QuasiExpress.cond;
import static quasi.QuasiExpress.expr;
import static quasi.QuasiExpress.keep;
import static quasi.QuasiExpress.pass;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class QuasiFunction {

    public interface base {}

    public interface any<t> extends base { t invoke(Object... args); }
    public interface any_void extends base { void invoke(Object... args); }
    public interface any_bool extends base { boolean invoke(Object... args); }

    public interface one<k, v> extends base { v invoke(k arg); }
    public interface one_void<t> extends base { void invoke(t arg); }
    public interface one_bool<t> extends base { boolean invoke(t arg); }

    public interface zero<t> extends base { t invoke(); }
    public interface zero_void extends base { void invoke(); }
    public interface zero_bool extends base { boolean invoke(); }

    public interface zero_u extends base { <u> u invoke(); }
    public interface zero_bool_u extends base { <u> boolean invoke(); }

// public: 
    public static final QuasiFunction.any<one_void<? super Object>> foreach = args -> {
        return (one_void<? super Object>) apply -> {
            Ind index = new Ind();
            while (QuasiExpress.keep(index.less(args.length))
                && QuasiExpress.pass(index.custom(apply, args))
                && QuasiExpress.pass(index.increase())) 
            {}
        };
    };

    public static final one_bool<Object> println = arg -> QuasiExpress
            .pack(() -> System.out.println(arg));
    
    public static Object invokeUniversal(Object func, Object... args) {
        if (func instanceof base)
            return invokeBase((base) func, args);
        return null;
    }

    public static Object invokeBase(base func, Object... args) {
        Method[] methods = func.getClass().getMethods();
        Method invoke = null;
        Object result = null;
        boolean vararg = false;
        Parameter[] params = null;

        Ind index = new Ind();
        index.clear();
        while ( keep(index.less(methods.length)) 
          && !( keep(methods[index.value()].toString().contains("invoke")) 
          &&    pass(invoke = methods[index.value()]) ) 
          &&    pass(index.increase()) ) 
        {}

        MethodHandles.Lookup lookup = MethodHandles.lookup();

        invoke.setAccessible(true);
        params = invoke.getParameters();
        
        boolean embed = params.length == 1 && args.length > 1;

        final var finals = new Object[] { vararg, result, invoke, params.length };
        QuasiExpress.slience(() -> {
            var invoke_ = (Method) finals[2];
            var refl = lookup.unreflect(invoke_);
            expr( keep(finals[3])
              &&  pass(pass(finals[0] = invoke_.isVarArgs() || embed)
              &&  pass(finals[1] = invoke_.invoke(func, 
                  cond(finals[0], new Object[] { args }, args ) )))
              ||  pass(finals[1] = refl.invoke(func)) );
        });
        
        return finals[1];
    }

}
