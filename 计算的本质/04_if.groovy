
def ZERO  = {p -> {x -> x}}
def ONE   = {p -> {x -> p(x)}}
def TWO   = {p -> {x -> p(p(x))}}
def THREE = {p -> {x -> p(p(p(x)))}}
def FOUR  = {p -> {x -> p(p(p(p(x))))}}
def FIVE  = {p -> {x -> p(p(p(p(p(x)))))}}

def TRUE  = {x -> {y -> x}}
def FALSE = {x -> {y -> y}}

def IF = {b -> {x -> {y -> b(x)(y)}}} // {b -> b}

// test:
def to_integer = {num -> num {it + 1} (0)}
def to_boolean = {bool -> bool(true)(false)}

println(to_integer(IF(TRUE )(ONE)(TWO)))
println(to_integer(IF(FALSE)(ONE)(TWO)))
