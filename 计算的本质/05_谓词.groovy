
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

// test:
def to_integer = {num -> num {it + 1} (0)}
def to_boolean = {bool -> bool(true)(false)}

println(to_boolean(IS_ZERO(ZERO)))
println(to_boolean(IS_ZERO(ONE)))
println(to_boolean(IS_ZERO(TWO)))
