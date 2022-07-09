package aira.quasi;

import aira.quasi.QuasiFunction.base;

public class Index {

	private int value;

	public boolean less(int than) { return value < than; }
	public boolean great(int than) { return value > than; }

    public Object of(Object[] array) { return array[this.value]; }
	public int set(int val) { return value = val; }
	public int step(int step) { value += step; return value; }
	public int value() { return value; }
	public int clear() { value = 0; return value; }
	public int increase() { ++value; return value; }
	public int decrease() { --value; return value; }
	public int assign(Object[] array, Object target) { array[this.value] = target; return value; }
	
	public boolean custom(base apply, Object[] args) {
		QuasiFunction.invoke(apply, args[this.value]);
		return true;
	};
}
