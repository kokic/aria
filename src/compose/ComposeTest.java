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

        // QuasiFunction.invokeUniversal(out, "out-test");
        var res = Comb.with(out, out, get);
        var result = QuasiFunction.invokeUniversal(res);
        System.out.println("final result: " + result);
        
    }
    
}
