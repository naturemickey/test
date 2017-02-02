
def ZERO  = {p -> {x -> x}}
def ONE   = {p -> {x -> p(x)}}
def TWO   = {p -> {x -> p(p(x))}}
def THREE = {p -> {x -> p(p(p(x)))}}
def FOUR  = {p -> {x -> p(p(p(p(x))))}}
def FIVE  = {p -> {x -> p(p(p(p(p(x)))))}}

def TRUE  = {x -> {y -> x}}
def FALSE = {x -> {y -> y}}

def IF = {b -> {x -> {y -> b(x)(y)}}}
def IS_ZERO = {p -> p{x -> FALSE}(TRUE)}

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

def Y = {f -> {x -> f(x(x))}{x -> f(x(x))}}
def Z = {f -> {x -> f{y -> x(x)(y)}}{x -> f{y -> x(x)(y)}}}

def IS_LESS_OR_EQUAL = {m -> {n -> IS_ZERO(SUB(m)(n))}}
def MOD = Z{f -> {m -> {n ->
    IF (IS_LESS_OR_EQUAL(n)(m)) (
            {x -> f(SUB(m)(n))(n)(x)}
    ) (
            m
    )
}}}

def EMPTY    =  PAIR(TRUE)(TRUE)
def UNSHIFT  = {l -> {x -> PAIR(FALSE)(PAIR(x)(l))}}
def IS_EMPTY = LEFT
def FIRST    = {l -> LEFT(RIGHT(l))}
def REST     = {l -> RIGHT(RIGHT(l))}

def RANGE = Z{f -> {m -> {n ->
    IF(IS_LESS_OR_EQUAL(m)(n)) (
            {x -> UNSHIFT(f(INCREMENT(m))(n))(m)(x)}
    ) (
            EMPTY
    )
}}}

def FOLD = Z{f -> {l -> {x -> {g ->
    IF(IS_EMPTY(l)) (
            x
    ) (
            {y -> g(f(REST(l))(x)(g))(FIRST(l))(y)}
    )
}}}}

// test:
def to_integer = {num -> num {it + 1} (0)}

println(to_integer(FOLD(RANGE(ONE)(FIVE))(ZERO)(ADD)))
println(to_integer(FOLD(RANGE(ONE)(FIVE))(ONE)(MULTIPLY)))
