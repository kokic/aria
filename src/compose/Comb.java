package compose;

import quasi.QuasiFunction;

public class Comb {

    public static QuasiFunction.base with(QuasiFunction.base f, QuasiFunction.base g) {
        // f: A -> B, g: C -> D, fg: C -> (D as A) -> B

        // System.out.println(getGenericTypes.invoke(f)[0]);

        Class<?> gclazz = g.getClass().getInterfaces()[0];

        // String name = fclazz.getSimpleName();
        String name = gclazz.getSimpleName();

        if (name.startsWith("zero")) {
            return new QuasiFunction.zero<Object>() {
                @Override
                public Object invoke() {
                    Object gin = QuasiFunction.invokeUniversal(g);
                    QuasiFunction.invokeUniversal(f, gin);
                    return true;
                }
            };
        }
        
        return new QuasiFunction.any<Object>() {
            @Override
            public Object invoke(Object... args) {
                Object gin = QuasiFunction.invokeUniversal(g, args);
                return QuasiFunction.invokeUniversal(f, gin);
            }
        };

    }

}
