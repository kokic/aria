package quasi;

public final class Unsafe {

    @SuppressWarnings("unchecked")
    public static final <type> type as(Object instance) {
        return (type) instance;
    }
    
}
