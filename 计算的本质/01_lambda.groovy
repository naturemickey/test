/**
 * 目的:演示groovy语言的lambda语法,为后面做铺垫。
 */

def f1 = {a, b -> a + b}
def f2 = {a -> {b -> a + b}}

println(f1(1, 2))
println(f2(1)(2))

def f3 = {f, a, b -> f(a)(b)}
def f4 = {f -> {a -> {b -> f(a)(b)}}} // 等价:{f -> f}

println(f3(f2, 3, 4))
println(f4(f2)(3)(4))

println(f4{a -> {b -> a + b}}(3)(4))
