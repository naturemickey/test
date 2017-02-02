
def ZERO  = {p -> {x -> x}}
def ONE   = {p -> {x -> p(x)}}
def TWO   = {p -> {x -> p(p(x))}}
def THREE = {p -> {x -> p(p(p(x)))}}
def FOUR  = {p -> {x -> p(p(p(p(x))))}}
def FIVE  = {p -> {x -> p(p(p(p(p(x)))))}}

def TRUE  = {x -> {y -> x}}
def FALSE = {x -> {y -> y}}

def PAIR  = {x -> {y -> {f -> f(x)(y)}}}
def LEFT  = {p -> p{x -> {y -> x}}}
def RIGHT = {p -> p{x -> {y -> y}}}

def EMPTY    =  PAIR(TRUE)(TRUE)
def UNSHIFT  = {l -> {x -> PAIR(FALSE)(PAIR(x)(l))}}
def IS_EMPTY = LEFT
def FIRST    = {l -> LEFT(RIGHT(l))}
def REST     = {l -> RIGHT(RIGHT(l))}

// test
def to_integer = {num -> num {it + 1} (0)}
def to_boolean = {bool -> bool(true)(false)}

def my_list = UNSHIFT(UNSHIFT(UNSHIFT(EMPTY)(THREE))(TWO))(ONE)

println(to_integer(FIRST(my_list)))
println(to_integer(FIRST(REST(my_list))))
println(to_integer(FIRST(REST(REST(my_list)))))

println(to_boolean(IS_EMPTY(my_list)))
println(to_boolean(IS_EMPTY(EMPTY)))
