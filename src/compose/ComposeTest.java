package compose;

import quasi.QuasiFunction;

public class ComposeTest {

    public static final QuasiFunction.one<Object, String> out = new QuasiFunction.one<Object,String>() {
        @Override
        public String invoke(Object arg) {
            System.out.println(arg);
            return "end";
        }
    };

    public static final QuasiFunction.zero<String> get = new QuasiFunction.zero<String>() {
        @Override
        public String invoke() {
            return "out";
        }
    };

    public static void main(String[] args) {

        // QuasiFunction.invokeUniversal(QuasiFunction.println, "test");
        Object res = QuasiFunction.invokeUniversal(Comb.with(out, out), "exec"); 
        System.out.println(res);

    }

    
}
