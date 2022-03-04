import quasi.*;

import static quasi.Ind.index;

public class Main
{
	public static final QuasiFunction.one_bool<Object> println = new QuasiFunction.one_bool<Object>() {
		@Override
		public boolean invoke(Object arg) {
			System.out.println(arg);
			return true;
		}
	};
	
	public static final QuasiFunction.any_void foreach = new QuasiFunction.any_void() {
		@Override
		public void invoke(Object... args) {
			QuasiExpress.pass(index.clear());
			while ( QuasiExpress.keep(index.less(args.length)) && 
			        QuasiExpress.keep(index.println.invoke(args)) && 
				    QuasiExpress.pass(index.increase()) ) 
			{}
		}
	};
	
	
	public static void main(String[] args)
	{
		System.out.println(">>> test: ");
		QuasiFunction.invokeUniversal(println, "good");
		
		System.out.println(">>> foreach: ");
		QuasiFunction.invokeUniversal(foreach, 1, 5, 3);
		
		
		int value = 2; 
		// coodition ? left() : right();
		QuasiExpress.expr((5 > 3
			&& QuasiExpress.pass(++value)) 
			|| QuasiExpress.pass(--value));
		
		System.out.println("value: " + value);
	}
}
