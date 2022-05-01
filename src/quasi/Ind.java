package quasi;

import quasi.QuasiFunction.one;

public class Ind {
// private: 
	private int value;
	
// public: 
	public boolean less(int than) { return value < than; }
	public boolean great(int than) { return value > than; }

// public: 
    public Object of(Object[] array) { return array[this.value]; }
	public int set(int val) { return value = val; }
	public int step(int step) { value += step; return value; }
	public int value() { return value; }
	public int clear() { value = 0; return value; }
	public int increase() { ++value; return value; }
	public int decrease() { --value; return value; }
	public int assign(Object[] array, Object target) { array[this.value] = target; return value; }
	
	public int with(Object[] array, one<Integer, Integer> indexApply, Object target) {
		array[indexApply.invoke(this.value)] = target;
		return value;
	}

	public QuasiFunction.one_bool<Object[]> println = args -> QuasiExpress
	        .pack(() -> System.out.println(args[this.value]));

	public boolean custom(Object apply, Object[] args) {
		QuasiFunction.invokeUniversal(apply, args[this.value]);
		return true;
	};
// public: 
    public Ind() {}
	public Ind(int value) { this.value = value; }
	
}
