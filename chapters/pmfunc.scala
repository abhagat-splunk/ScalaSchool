// Function Composition
def f(s: String) = "f(" + s + ")"

def g(s: String) = "g(" + s + ")"

// compose => makes a new function that composes other functions f(g(x))
val fComposeG = f _ compose g _
fComposeG("yay")

// andThen => andThen is like compose, but calls the first function and then the second, g(f(x))
val fAndThenG = f _ andThen g _
fAndThenG("yay")

/* 
Currying vs Partial Application
case statement - subclass of PartialFunction
multiple case statements - multiple PartialFunctions composed together

Understanding PartialFunctions
A function works for every argument of the defined type.
A function defined as (Int) => String takes any Int and returns a String.

A Partial Function is only defined for certain values of the defined type. 
A Partial Function (Int) => String might not accept every Int.

isDefinedAt is a method on PartialFunction that can be used to determine if the PartialFunction will accept a given argument.

PartialFunction is unrelated to a partially applied function that we talked about earlier.
*/

val one: PartialFunction[Int, String] = { case 1 => "one" }
one.isDefinedAt(1)
one.isDefinedAt(2)

one(1)

val two: PartialFunction[Int, String] = { case 2 => "two" }
val three: PartialFunction[Int, String] = { case 3 => "three" }
val wildcard: PartialFunction[Int, String] = { case _ => "something else" }
val partial = one orElse two orElse three orElse wildcard

partial(5)
partial(3)
partial(2)
partial(0)

// Mystery of case
case class PhoneExt(name: String, ext: Int)
val extensions = List(PhoneExt("steve", 100), PhoneExt("robey", 200))
extensions.filter { case PhoneExt(name, extension) => extension < 200 }