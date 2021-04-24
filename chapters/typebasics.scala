// Types allow you to denote function domain & codomains. 
// Generally speaking, the typechecker can only guarantee that unsound programs do not compile. It cannot guarantee that every sound program will compile.
// All type information is removed at compile time. It is no longer needed. This is called erasure.

// Scala's type system allows for very rich expression. 
// Features: Parametric Polymorphism (generic programming), Local type inference, existential quantification, views (castability)

// Parametric Polymorphism
// Polymorphism is used in order to write generic code (for values of different types) without compromising static typing richness.

def drop1[A](l: List[A]) = l.tail
drop1(List(1,2,3))

// Scala has rank1 polymorphism

// Type Inference
// A traditional objection to static typing is that it has much syntactic overhead. Scala alleviates this by providing type inference.
// In scala all type inference is local. Scala considers one expression at a time.

def id[T](x: T) = x
val x = id(322)
val x = id("hey")
val x = id(Array(1,2,3,4))

// Types are now preserved, The Scala compiler infers the type parameter for us. Note also how we did not have to specify the return type explicitly.


// Variance 
// Class hierarchies allow the expression of subtype relationships. A central question that comes up when mixing OO with polymorphism is: if T’ is a subclass of T, is Container[T’] considered a subclass of Container[T]?
// Variance annotations allow you to express the following relationships between class hierarchies & polymorphic types
// covariant => C[T’] is a subclass of C[T] => [+T]
// contravariant => C[T] is a sublclass of C[T'] => [-T]
// invariant => C[T] and C[T'] are not related => [T]
class Covariant[+A]
val cv: Covariant[AnyRef] = new Covariant[String]

class Contravariant[-A]
val cv: Contravariant[String] = new Contravariant[AnyRef]

// Contravariance seems strange. When is it used? Somewhat surprising!

// Class Hierarchy = Animal > Bird > Chicken
class Animal { val sound = "rustle" }
class Bird extends Animal { override val sound = "call" }
class Chicken extends Bird { override val sound = "cluck" }
class Duck extends Bird { override val sound = "quack"}

val getTweet: (Bird => String) = ((a: Animal) => a.sound )

// function parameters are contravariant. If you need a function that takes a Bird and you have a function that takes a Chicken, that function would choke on a Duck. But a function that takes an Animal is OK.


val hatch: (() => Bird) = (() => new Chicken )
// function’s return value type is covariant. If you need a function that returns a Bird but have a function that returns a Chicken, that’s great.


// Bounds
// Scala allows you to restrict polymorphic variables using bounds. These bounds express subtype relationships.

def cacophony[T](things: Seq[T]) = things map (_.sound) // This won't work - value sound is not a member of type parameter T
def biophony[T <: Animal](things: Seq[T]) = things map (_.sound)
biophony(Seq(new Chicken, new Bird))

// Lower type bounds are supported. List[+T] is covariant; a list of Birds is a list of Animals. 
val flock = List(new Bird, new Bird)
new Chicken :: flock

// List also defines ::[B >: T](x: B) which returns a List[B]. Notice the B >: T. That specifies type B as a superclass of T.
new Animal :: flock
// List[Animal] = List(Animal@11f8d3a8, Bird@7e1ec70e, Bird@169ea8d2)

// Quantification: Sometimes you do not care to be able to name a type variable
def count[A](l: List[A]) = l.size

// You can use wildcards
def count(l: List[_]) = l.size
// shrothand for
def count(l: List[T forSome { type T }]) = l.size