package quasi;

public class QuasiExpress {
	
	public static Object dot(Object... terms) {
		return terms[terms.length - 1];
	}
	
	public static <type> type cond(Object test, type left, type right) {
		return keep(test) ? left : right;
	}
	
	public static boolean pass(Object expr) { return true; }
	
	public static boolean keep(Object test) {
		if (test.getClass() == Boolean.class)
			return (Boolean) test;

		boolean bool = false;
		expr(test.getClass() == Integer.class && (Integer) test >= 1 && pass(bool = true));
		expr(test.getClass() == String.class
				&& keep(test.equals("true") || test.equals("1"))
				&& pass(test = true));
		
		return bool;
	}
	
	public static void expr(Object expr) {}
}
