package aira.quasi;

import aira.quasi.QuasiFunction.base;
import aira.quasi.QuasiFunction.one_bool;
import aira.quasi.QuasiFunction.one_t;
import aira.quasi.QuasiFunction.one_u;
import aira.quasi.QuasiFunction.two_t;
import aira.quasi.QuasiFunction.zero_t;

public class Index {

	private int value;

	public one_bool<Integer> less = than -> value < than;
	public one_bool<Integer> lessEq = than -> value <= than; 
	public one_bool<Integer> great = than -> value > than;
	public one_bool<Integer> greatEq = than -> value >= than;
	
	public zero_t<Integer> get = () -> value;
	public one_t<Integer, Integer> set = x -> value = x;
	
	public zero_t<Integer> increase = () -> ++value;
	public zero_t<Integer> decrease = () -> ++value;
	public one_t<Integer, Integer> step = step -> value += step;
	
	public one_t<Object[], Object> of = xs -> xs[value];
	public two_t<Object[], Object, Object> assign = (xs, val) -> xs[value] = val;
	public two_t<base, Object[], Object> apply = (f, xs) -> QuasiFunction.invoke(f, of.invoke(xs));
	
	public one_u<Object[]> as = new one_u<Object[]>() {
		public <u> u invoke(Object[] xs) {
			return Unsafe.as(xs[value]);
		}
	}; 
}
