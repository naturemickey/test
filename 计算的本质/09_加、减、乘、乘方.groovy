
def ZERO  = {p -> {x -> x}}
def ONE   = {p -> {x -> p(x)}}
def TWO   = {p -> {x -> p(p(x))}}
def THREE = {p -> {x -> p(p(p(x)))}}
def FOUR  = {p -> {x -> p(p(p(p(x))))}}
def FIVE  = {p -> {x -> p(p(p(p(p(x)))))}}

def PAIR  = {x -> {y -> {f -> f(x)(y)}}}
def LEFT  = {p -> p{x -> {y -> x}}}
def RIGHT = {p -> p{x -> {y -> y}}}

def INCREMENT = {n -> {p -> {x -> p( n(p)(x) ) }}}

def SLIDE     = {p -> PAIR(RIGHT(p))(INCREMENT(RIGHT(p)))}
def DECREMENT = {n -> LEFT(n(SLIDE)(PAIR(ZERO)(ZERO)))}

def ADD      = {m -> {n -> n(INCREMENT  )(m   )}}
def SUB      = {m -> {n -> n(DECREMENT  )(m   )}}
def MULTIPLY = {m -> {n -> n(ADD(m)     )(ZERO)}}
def POW      = {m -> {n -> n(MULTIPLY(m))(ONE )}}

// test:
def to_integer = {num -> num {it + 1} (0)}

println(to_integer(ADD(ONE)(TWO)))
println(to_integer(SUB(FIVE)(THREE)))
println(to_integer(MULTIPLY(TWO)(THREE)))
println(to_integer(POW(TWO)(THREE)))
