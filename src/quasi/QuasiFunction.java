package quasi;

import static quasi.Ind.index;
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
    
    public static final one_bool<Object> println = new one_bool<Object>() {
        @Override
        public boolean invoke(Object arg) {
            System.out.println(arg);
            return true;
        }
    };

    public static Object invokeUniversal(base func, Object... args) {

        // expand for (...) as (Object[]
        // System.out.println(args.length);
        // System.out.println(args[0].getClass().isArray());
        //if (args.length == 1 && args[0].getClass().isArray())
          //  args = (Object[]) args[0];

        Method[] methods = func.getClass().getMethods();
        Method invoke = null;
        Object result = null;
        boolean vararg = false;
        Parameter[] params = null;

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
              &&  pass(pass(vararg = params[0].isVarArgs() || embed)
              &&  pass(result = invoke.invoke(func, 
                  cond(vararg, new Object[] { args }, args ) )))
              ||  pass(result = invoke.invoke(func)) );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
