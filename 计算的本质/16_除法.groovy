import java.util.stream.Collectors

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

def NUM0  = ZERO
def NUM1  = ONE
def NUM2  = TWO
def NUM3  = THREE
def NUM4  = FOUR
def NUM5  = FIVE
def NUM6  = ADD(FIVE)(ONE)
def NUM7  = ADD(FIVE)(TWO)
def NUM8  = ADD(FIVE)(THREE)
def NUM9  = ADD(FIVE)(FOUR)
def NUM10 = ADD(FIVE)(FIVE)
def NUM11 = ADD(NUM10)(NUM1)
def NUM12 = ADD(NUM10)(NUM2)
def NUM13 = ADD(NUM10)(NUM3)
def NUM14 = ADD(NUM10)(NUM4)
def NUM15 = ADD(NUM10)(NUM5)
def NUM16 = ADD(NUM10)(NUM6)
def NUM17 = ADD(NUM10)(NUM7)
def NUM18 = ADD(NUM10)(NUM8)
def NUM19 = ADD(NUM10)(NUM9)
def NUM20 = ADD(NUM10)(NUM10)
def NUM21 = ADD(NUM20)(NUM1)
def NUM22 = ADD(NUM20)(NUM2)
def NUM23 = ADD(NUM20)(NUM3)
def NUM24 = ADD(NUM20)(NUM4)
def NUM25 = ADD(NUM20)(NUM5)
def NUM26 = ADD(NUM20)(NUM6)
def NUM27 = ADD(NUM20)(NUM7)
def NUM28 = ADD(NUM20)(NUM8)
def NUM29 = ADD(NUM20)(NUM9)
def NUM30 = ADD(NUM20)(NUM10)
def NUM31 = ADD(NUM20)(NUM11)
def NUM32 = ADD(NUM20)(NUM12)
def NUM33 = ADD(NUM20)(NUM13)
def NUM34 = ADD(NUM20)(NUM14)
def NUM35 = ADD(NUM20)(NUM15)
def NUM36 = ADD(NUM20)(NUM16)
def NUM37 = ADD(NUM20)(NUM17)
def NUM38 = ADD(NUM20)(NUM18)
def NUM39 = ADD(NUM20)(NUM19)
def NUM40 = ADD(NUM20)(NUM20)

def DIV = Z{f -> {m -> {n ->
    IF (IS_LESS_OR_EQUAL(n)(m)) (
            {x -> INCREMENT(f(SUB(m)(n))(n))(x)}
    ) (ZERO)
}}}

// test:
def to_integer = {num -> num {it + 1} (0)}

println(to_integer(DIV(NUM20)(FIVE)))
println(to_integer(DIV(NUM25)(NUM6)))
