
def TRUE  = {x -> {y -> x}}
def FALSE = {x -> {y -> y}}

// test:
def to_boolean = {bool -> bool(true)(false)}

println(to_boolean(TRUE))
println(to_boolean(FALSE))