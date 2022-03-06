package quasi;

import static quasi.QuasiExpress.cond;
import static quasi.QuasiExpress.expr;
import static quasi.QuasiExpress.keep;
import static quasi.QuasiExpress.pass;

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

// public: 
    public static final QuasiFunction.any<one_void<base>> foreach = args -> {
        return (one_void<base>) apply -> {
            Ind index = new Ind();
            QuasiExpress.pass(index.clear());
            while (QuasiExpress.keep(index.less(args.length))
                && QuasiExpress.keep(index.custom(apply, args))
                && QuasiExpress.pass(index.increase())) 
            {};
        };
    };

    public static final one_bool<Object> println = new one_bool<Object>() {
        @Override
        public boolean invoke(Object arg) {
            System.out.println(arg);
            return true;
        }
    };

    public static Object invokeUniversal(base func, Object... args) {

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

        invoke.setAccessible(true);
        params = invoke.getParameters();
        
        boolean embed = params.length == 1 && args.length > 1;

        try {

            expr( keep(params.length)
              &&  pass(pass(vararg = invoke.isVarArgs() || embed)
              &&  pass(result = invoke.invoke(func, 
                  cond(vararg, new Object[] { args }, args ) )))
              ||  pass(result = invoke.invoke(func)) );

        } catch (Exception e) {
            System.out.println("embed: " + embed + ", vararg: " + vararg);
            System.out.println("param_0: " + params[0].getType() + ", arg_0: " + args[0].getClass());
            System.out.println(invoke);
            e.printStackTrace();
        }

        return result;
    }

}
