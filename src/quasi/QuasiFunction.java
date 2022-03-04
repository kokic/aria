package quasi;

import static quasi.Ind.index;
import static quasi.QuasiExpress.cond;
import static quasi.QuasiExpress.expr;
import static quasi.QuasiExpress.keep;
import static quasi.QuasiExpress.pass;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
    
    public static final QuasiFunction.one_bool<Object> println = new QuasiFunction.one_bool<Object>() {
        @Override
        public boolean invoke(Object arg) {
            System.out.println(arg);
            return true;
        }
    };

    // instance : QuasiFunction.base -> Type[]
    public static final QuasiFunction.any<Type[]> getGenericTypes = (
            Object... args) -> ((ParameterizedType) (args[0]).getClass()
                    .getGenericInterfaces()[0])
                    .getActualTypeArguments();

    public static Object invokeUniversal(base func, Object... args) {

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

        try {

            expr( keep(params.length)
              &&  pass(pass(vararg = params[0].isVarArgs())
              &&  pass(result = invoke.invoke(func, 
                  cond(vararg, new Object[] { args }, args))))
              ||  pass(result = invoke.invoke(func)) );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
