package quasi;

public class Ind {
	
	public static Ind index = new Ind(0);
	
// private: 
	private int value;
	
// public: 
	public boolean less(int then) { return value < then; }
	
// public: 
	public int set(int val) { return value = val; }
	public int value() { return value; }
	public int clear() { value = 0; return value; }
	public int increase() { ++value; return value; }
	
	public QuasiFunction.one_bool<Object[]> println = arg -> {
		System.out.println(arg[this.value]);
		return true;
	};
	
// public: 
	public Ind(int value) { this.value = value; }
	
}
