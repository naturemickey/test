
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

def MAP = {k -> {f -> FOLD(k)(EMPTY)(
        {l -> {x -> UNSHIFT(l)(f(x))}}
)}}

def DIV = Z{f -> {m -> {n ->
    IF (IS_LESS_OR_EQUAL(n)(m)) (
            {x -> INCREMENT(f(SUB(m)(n))(n))(x)}
    ) (ZERO)
}}}

def PUSH = {l -> {x -> FOLD(l)(UNSHIFT(EMPTY)(x))(UNSHIFT)}}

def TO_DIGITS = Z{f -> {n -> PUSH(
        IF (IS_LESS_OR_EQUAL(n)(DECREMENT(NUM10))) (
                EMPTY
        ) (
                {x -> f(DIV(n)(NUM10))(x)}
        )
)(MOD(n)(NUM10))}}

// test:
def to_integer = {num -> num {it + 1} (0)}
def to_boolean = {bool -> bool(true)(false)}
def to_array = {l, count ->
    def array = []
    while (!to_boolean(IS_EMPTY(l)) && count != 0) {
        array.add(FIRST(l))
        l = REST(l)
        count -= 1
    }
    array
}
def printStream = {l, c -> println(to_array(l, c).collect {to_integer(it)}.join(","))}

def ZEROS = Z{f -> UNSHIFT(f)(ZERO)}

printStream(ZEROS, 5)
printStream(ZEROS, 20)
println()

def UPWARDS_OF = Z{f -> {n -> UNSHIFT{x -> f(INCREMENT(n))(x)}(n)}}

printStream(UPWARDS_OF(ZERO), 10)
printStream(UPWARDS_OF(FIVE), 20)
println()

def MULTIPLES_OF = {m -> Z{f -> {n -> UNSHIFT{x -> f(ADD(m)(n))(x)}(n)}}(m)}

printStream(MULTIPLES_OF(TWO), 10)
printStream(MULTIPLES_OF(FIVE), 20)
println()
