package quasi;

import quasi.QuasiFunction.base;

public class Ind {
// private: 
	private int value;
	
// public: 
	public boolean less(int then) { return value < then; }
	
// public: 
	public int set(int val) { return value = val; }
	public int value() { return value; }
	public int clear() { value = 0; return value; }
	public int increase() { ++value; return value; }
	
	public QuasiFunction.one_bool<Object[]> println = args -> {
		System.out.println(args[this.value]);
		return true;
	};

	public boolean custom(base apply, Object[] args) {
		// QuasiFunction.invokeUniversal(apply, args[this.value]);
		QuasiFunction.invokeUniversal(apply, args[this.value]);
		return true;
	};
// public: 
	public Ind(int value) { this.value = value; }
	
}
