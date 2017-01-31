
def ZERO  = {p -> {x -> x}}
def ONE   = {p -> {x -> p(x)}}
def TWO   = {p -> {x -> p(p(x))}}
def THREE = {p -> {x -> p(p(p(x)))}}
def FOUR  = {p -> {x -> p(p(p(p(x))))}}
def FIVE  = {p -> {x -> p(p(p(p(p(x)))))}}

// test:
def to_integer = {num -> num {it + 1} (0)}

println(to_integer(ZERO ))
println(to_integer(ONE  ))
println(to_integer(TWO  ))
println(to_integer(THREE))
println(to_integer(FOUR ))
println(to_integer(FIVE ))
