# **aria**
Perhaps the most unreadable, non maintainable, obscure java source code. This project tries to make more use of interfaces as methods and try to replace inheritance with composition in Java.

---

## **basic examples**
Here are some basic usages.

### **compose**
Note `quasi.QuasiFunction.one_void`, `quasi.QuasiFunction.zero`
```
var f = (one_void<String>) in -> System.out.println(in);
var g = (zero<String>) () -> "Hello World";
var fg = Comb.with(f, g);
QuasiFunction.invokeUniversal(fg);
```
or
```
...
var id = (one<Object, Object>) x -> x;
var fg = Comb.with(f, id, id, id, g);
QuasiFunction.invokeUniversal(fg);
```
```
Hello World
```

### **invalid expression**
Statement `coodition ? left : right;` can be implemented as
```
QuasiExpress.expr((coodition 
    && QuasiExpress.pass(left))
    || QuasiExpress.pass(right)
);
```

### **empty body while**
```
while (QuasiExpress.keep(condition1) 
    && QuasiExpress.keep(condition2) 
    && QuasiExpress.pass(todo1) 
    && QuasiExpress.pass(todo2))
{};
```

### **meaningless index**
```
Ind index = new Ind(); 
while (keep(index.less(elements.length)) 
    && pass(index.assign(array, value)) 
    && pass(index.increase())) 
{};
```

---

## **fancy print**
### *e.g.*
Note: 
`quasi.QuasiFunction.foreach: any<one_void<base>>`, 
`quasi.QuasiFunction.println: one_bool<Object>`
```
foreach.invoke("Commutative Algebra", "Homological Algebra").invoke(println);
foreach.invoke((Object[]) any.class.getMethods()).invoke(println);
```
```
Commutative Algebra
Homological Algebra
public abstract java.lang.Object quasi.QuasiFunction$any.invoke(java.lang.Object[])
```

### *e.g.*
Note: `foreach.invoke: Object... -> one_void<base>`
```
var state = foreach.invoke(Comb.join(Comb.each(1, 5, 3)
        .apply(x -> (int) x - 3))
        .apply(() -> " "));
state.invoke((one_void<Object>) elem -> System.out.print(elem));
```
```
-2 2 0
```

## **local quasi function**
Note: `quasi.QuasiFunction.zero_bool_u`
```
zero_bool_u localInstanceof = new zero_bool_u() {
    public <u> boolean invoke() {
        return Unsafe.<u>as(last) instanceof u 
            && Unsafe.<u>as(elem) instanceof u;
    }
};
...
value = /* -------- space -------- */
          localInstanceof.<Integer>invoke()
        ? (Integer) elem + (Integer) last

        : localInstanceof.<Double>invoke()
        ? (Double) elem + (Double) last

        : localInstanceof.<Long>invoke()
        ? (Long) elem + (Long) last

        : localInstanceof.<Float>invoke()
        ? (Float) elem + (Float) last
                        ...
        : (String) elem + last;
```