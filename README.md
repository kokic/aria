# **aria**
Perhaps the most unreadable, non maintainable, obscure java source code. This project tries to make more use of interfaces as methods and try to replace inheritance with composition in Java.

---

## **remarkable**
### **qsort without ';'**
This is just a small attempt, but in fact, as you can see, it is usually tricky to achieve this effect in Java, which means the abuse of some kind of expression. This example lacks practicability in various senses, it doesn't exist for practical application. It just shows that we can do such a thing.
```
public static void qsort(int[] alist, int first, int last) {
    while (!pass.invoke(first < last && pack.invoke(() -> {
        class Local {
            static void invoke(int[] alist, int first, int last, int[] cap) {
                while (!(pack.invoke(() -> {
                    while (cap[1] < cap[2] 
                    && pack.invoke(() -> { while (cap[1] < cap[2] 
                        && alist[cap[2]] >= cap[0] 
                        && pass.invoke(cap[2] -= 1)) {} }) 
                    && pass.invoke(alist[cap[1]] = alist[cap[2]]) 
                    && pack.invoke(() -> { while (cap[1] < cap[2] 
                        && alist[cap[1]] < cap[0] 
                        && pass.invoke(cap[1] += 1)) {} })
                    && pass.invoke(alist[cap[2]] = alist[cap[1]])) {}
                })
                && pass.invoke(alist[cap[1]] = cap[0])
                && pack.invoke(() -> qsort(alist, first, cap[1] - 1))
                && pack.invoke(() -> qsort(alist, cap[1] + 1, last)) )) {}
            }
        }
        while (!(pack.invoke(() -> Local.invoke(alist, first, last,
                new int[] { alist[first], first, last })))) {}
    }))) {}
}
```


## **basic examples**
Here are some basic usages.

### **compose**
Note `quasi.QuasiFunction.one_void`, `quasi.QuasiFunction.zero_t`
```
var f = (one_void<String>) in -> System.out.println(in);
var g = (zero_t<String>) () -> "Hello World";
zero_t<Object> fg = Unsafe.as(Comb.with.invoke(f, g));
fg.invoke(); // also invoke(fg);
```
or (equivalently)
```
...
var id = (Aut<Object>) x -> x;
zero_t<Object> fgId = Unsafe.as(Comb.withs.invoke(f, id, id, id, g));
fgId.invoke();
```
composition with parameters
```
var gX = (one_t<Object, String>) x -> "Hello World with " + x;
any_t<Object> fgX = Unsafe.as(Comb.with.invoke(f, gX));
fgX.invoke(157);
invoke(fgX, 157);
```

### **expression**
Statement `coodition ? left : right;` can be implemented as
```
expr.invoke(coodition && pass.invoke(left) || pass.invoke(right));
```
`if-else` as
```
deltaIf.invoke(condition, () -> trueCase, () -> falseCase);
```

### **empty body while**
```
while (QuasiExpress.keep(condition1) 
    && QuasiExpress.keep(condition2) 
    && QuasiExpress.pass(todo1) 
    && QuasiExpress.pass(todo2))
{};
```

### **index**
```
Index index = new Index();
while (keep.invoke(index.less.invoke(args.length)) 
    && pass.invoke(index.apply.invoke(apply, args))
    && pass.invoke(index.increase.invoke())) 
{}
```

---

## **fancy print**
### *e.g.*
Note: 
`quasi.QuasiFunction.foreach: any_t<one_void<one_void<?>>>`, 
`quasi.QuasiFunction.println: Aut<Object>`
```
foreach.invoke("Commutative Algebra", "Homological Algebra").invoke(System.out::println);
foreach.invoke((Object[]) any_t.class.getMethods()).invoke(System.out::println);
```
```
Commutative Algebra
Homological Algebra
public abstract java.lang.Object aira.quasi.QuasiFunction$any_t.invoke(java.lang.Object[])
```

### *e.g.*
Note: `foreach.invoke: Object... -> one_void<base>`
```
Aut<List<Object>> embedSpace = xs -> embed.invoke(xs, " ");
Aut<List<Object>> addLine = xs -> add.invoke(xs, "\n");
any_t<List<Object>> handle = Unsafe.as(Comb.with.invoke(addLine, embedSpace));

var alist = list.invoke(1, 2, 3);
map.invoke(x -> pack.invoke(() -> System.out.print(x)), handle.invoke(alist));
foreach.invoke(handle.invoke(alist).toArray()).invoke(System.out::print);
```
```
1 2 3
```

### *e.g.*
```
alist = list.invoke(1, 3, 5);
alist = map.invoke(x -> (int) x - 3, alist);
alist = handle.invoke(alist);
map.invoke(print, alist);
```
```
-2 2 0
```

