
def ZERO  = {p -> {x -> x}}
def ONE   = {p -> {x -> p(x)}}
def TWO   = {p -> {x -> p(p(x))}}
def THREE = {p -> {x -> p(p(p(x)))}}
def FOUR  = {p -> {x -> p(p(p(p(x))))}}
def FIVE  = {p -> {x -> p(p(p(p(p(x)))))}}

def INCREMENT = {n -> {p -> {x -> p( n(p)(x) )}}}

// test:
def to_integer = {num -> num {it + 1} (0)}

println(to_integer(INCREMENT(ZERO)))
println(to_integer(INCREMENT(TWO)))
println(to_integer(INCREMENT(FOUR)))
