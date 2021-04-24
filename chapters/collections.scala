// Arrays preserve order, can contain duplicates, and are mutable.
val numbers = Array(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)

numbers(0)

// Lists preserve order, can contain duplicates, and are immutable.
val numbers = List(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
numbers(3) = 10 // error cause immutable

// Sets do not preserve order and have no duplicates
val numbers = Set(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)

// A tuple groups together simple logical collections of items without using a class.
val hostPort = ("localhost", 80)

// Unlike case classes, they don’t have named accessors, instead they have accessors that are named by their position and is 1-based rather than 0-based.
hostPort._1
hostPort._2

// Matching tuples
hostPort match {
  case ("localhost", port) => ...
  case (host, port) => ...
}

// x -> y makes a tuple of (x,y) 
val tup = "a" -> "b"

// Maps can hold basic datatypes
Map(1 -> 2)
Map("foo" -> "bar")

// Map(1 -> "one", 2 -> "two") which expands into Map((1, "one"), (2, "two"))
// Maps can themselves contain Maps or even functions as values.
Map(1 -> Map("foo" -> "bar"))
Map("timesTwo" -> { timesTwo(_) })

// Option is a container that may or may not hold something.
// Basic interface of Option looks like this:
trait Option[T] {
  def isDefined: Boolean
  def get: T
  def getOrElse(t: T): T
}

// Option itself is generic and has two subclasses: Some[T] or None
// Map.get uses Option for its return type. Option tells you that the method might not return what you’re asking for.


/*
scala> val numbers = Map("one" -> 1, "two" -> 2)
numbers: scala.collection.immutable.Map[java.lang.String,Int] = Map(one -> 1, two -> 2)

scala> numbers.get("two")
res0: Option[Int] = Some(2)

scala> numbers.get("three")
res1: Option[Int] = None

Now our data appears trapped in this Option. How do we work with it?

A first instinct might be to do something conditionally based on the isDefined method.

val result = if (res1.isDefined) {
  res1.get * 2
} else {
  0
}

We would suggest that you use either getOrElse or pattern matching to work with this result.

val result = res1.getOrElse(0) * 2

Pattern matching fits naturally with Option.

val result = res1 match {
  case Some(n) => n * 2
  case None => 0
}
*/


// Functional Combinators


val numbers = List(1, 2, 3, 4)

// map => Evaluates a function over each element in the list, returning a list with the same number of elements.
numbers.map((i: Int) => i * 2)
def timesTwo(i: Int): Int = i * 2
numbers.map(timesTwo)

// foreach => foreach is like map but returns nothing. foreach is intended for side-effects only.
numbers.foreach((i: Int) => i * 2)

// filter => removes any elements where the function you pass in evaluates to false.
numbers.filter((i: Int) => i % 2 == 0)
def isEven(i: Int): Boolean = i % 2 == 0
numbers.filter(isEven)

// zip => aggregates the contents of two lists into a single list of pairs.
List(1, 2, 3).zip(List("a", "b", "c"))
// res0: List[(Int, String)] = List((1,a), (2,b), (3,c))

// partition => splits a list based on where it falls with respect to a predicate function.
val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
numbers.partition(_ % 2 == 0)

// find => returns the first element of a collection that matches a predicate function.
numbers.find((i: Int) => i > 5)

// drop => drops the first i elements
numbers.drop(5)

// dropWhile => removes the first element that match a predicate function.
numbers.dropWhile(_ % 2 != 0)

// foldLeft, foldRight
numbers.foldLeft(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n }
numbers.foldRight(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n }

// flatten
List(List(1, 2), List(3, 4)).flatten

// flatMap => combines mapping and flattening. flatMap takes a function that works on the nested lists and then concatenates the results back together.
val nestedNumbers = List(List(1, 2), List(3, 4))
nestedNumbers.flatMap(x => x.map(_ * 2))

// shorthand for 
nestedNumbers.map((x: List[Int]) => x.map(_ * 2)).flatten


def ourMap(numbers: List[Int], fn: Int => Int): List[Int] = {
  numbers.foldRight(List[Int]()) { (x: Int, xs: List[Int]) =>
    println("x: " + x + " xs: " + xs);
    fn(x) :: xs
  }
}

// All of the functional combinators shown work on Maps, too. Maps can be thought of as a list of pairs so the functions you write work on a pair of the keys and values in the Map.
val extensions = Map("steve" -> 100, "bob" -> 101, "joe" -> 201)
extensions.filter((namePhone: (String, Int)) => namePhone._2 < 200)
extensions.filter({case (name, extension) => extension < 200})

// Functions that return a Boolean are often called predicate functions.