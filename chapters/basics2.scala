// apply methods give you a nice syntactic sugar for when a class or object has one main use.
class Foo{}

object FooMaker{
    def apply() = new Foo
}

val newFoo = FooMaker()

class Bar {
    def apply() = 0
}

val bar = new Bar
bar()

/*-------------------------------*/

// Objects are used to hold single instances of a class. Often used for factories.

object Timer {
  var count = 0

  def currentCount(): Long = {
    count += 1
    count
  }
}

Timer.currentCount()

// Classes and Objects can have the same name. The object is called a ‘Companion Object’. We commonly use Companion Objects for Factories.

class Bar(foo: String)

object Bar {
  def apply(foo: String) = new Bar(foo)
}


class Bar(foo: String){
    def printString(): String = foo
}

object Bar {
  def apply(foo: String) = new Bar(foo)
}

Bar("xyz").printString()

// A Function is a set of traits. 
// Specifically, a function that takes one argument is an instance of a Function1 trait.
// This trait defines the apply() syntactic sugar we learned earlier, allowing you to call an object like you would a function.
object addOne extends Function1[Int, Int] {
    def apply(m: Int): Int = m + 1
}

addOne(1)

object addPlusOne extends Function2[Int, Int, Int] {
    def apply(m: Int, n:Int): Int = m + n + 1
}

var curriedAddOneMore = addPlusOne(1,_)
curriedAddOneMore(2)


// Fun Fact: There is Function0 through 22. Why 22? It’s an arbitrary magic number.

// nice short-hand for extends Function1[Int, Int] is extends (Int => Int)

class AddOne extends (Int => Int) {
    def apply(m: Int) = m+1
}

val a = new AddOne()
a(1)

// Organize your code with packages
// package com.twitter.example

// Objects are a useful tool for organizing static functions.
// Values and functions cannot be outside of a class or object. 


package com.twitter.example

object colorHolder {
  val BLUE = "Blue"
  val RED = "Red"
}

println("the color is: " + com.twitter.example.colorHolder.BLUE)


// Pattern Matching with `match`
val times = 1

times match {
  case 1 => "one"
  case 2 => "two"
  case _ => "some other number"
}


// Match with guards. 
times match {
  case i if i == 1 => "one"
  case i if i == 2 => "two"
  case _ => "some other number"
}

// Matching with types
def bigger(o: Any): Any = {
  o match {
    case i: Int if i < 0 => i - 1
    case i: Int => i + 1
    case d: Double if d < 0.0 => d - 0.1
    case d: Double => d + 0.1
    case text: String => text + "s"
  }
}

// Case Classes - case classes are used to conveniently store and match on the contents of a class. You can construct them without using new.
case class Calculator(brand: String, model: String)

// case classes automatically have equality and nice toString methods based on the constructor arguments.
val hp20b = Calculator("HP", "20B")
val hp30b = Calculator("HP", "30B")

def calcType(calc: Calculator) = calc match {
  case Calculator("HP", "20B") => "financial"
  case Calculator("HP", "48G") => "scientific"
  case Calculator("HP", "30B") => "business"
  case Calculator(ourBrand, ourModel) => "Calculator: %s %s is of unknown type".format(ourBrand, ourModel)
}

calcType(hp20b)
calcType(hp30b)

// Default case alternatives: case Calculator(_, _) => "Calculator of unknown type", case _ => "Calculator of unknown type"
// case c@Calculator(_, _) => "Calculator: %s of unknown type".format(c)


// Try-Catch-Finally syntax
try {
  remoteCalculatorService.add(1, 2)
} catch {
  case e: ServerIsDownException => log.error(e, "the remote calculator service is unavailable. should have kept your trusty HP.")
} finally {
  remoteCalculatorService.close()
}