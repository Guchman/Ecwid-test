# Deep cloner

## Reflection implementation
### Implementation supports:
* Primitives, Strings, arrays, primitive wrappers, simple enums
* Objects with default constructor
* Mutable Objects, which have simple constructor and fields
* Mutable collections (those that have default constructors)
* Objects with copy() method. Kotlin data classes for example
* Cyclic references
* Custom processors

### Use original objects with addImmutables
If you have some class that is safe to use
as copy (you know that it's immutable for example, like BigDecimal)
You free to white filter such class with addImmutables method

### Cyclic reference preservation
If original object has cyclic references 
then copied object will have cyclic reference at same place.
So it's safe to copy something like Cyclic List.

### Limitations
In general it's not possible to provide
implementation that safe to go with any objects.
Constructors can do things inside, object could be 
created with different constructors.

The way to go here is provided addProcessor/addImmutables methods.
Custom processors allows to extend current cloning on your own. 

Current known limitations (I'm sure there is bunch more of corner cases):
* It's unsafe to use if constructor do anything beyond simple field assignment
* It doesn't support immutable collections
* It doesn't support collections without default constructor

## Other possible implementations
* Serialization
* Json Marshaling/Unmarshaling
* Manual copying provided by each class
* Some code-generative approach for limited cases maybe

---
P.S. test coverage far from perfect