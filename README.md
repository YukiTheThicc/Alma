# Alma ECS

The Alma project aims to implement a simple yet performant Entity Component System architecture in Java using pooled,
chunked data structures. Inspired by other ECS in Java such as [Artemis](https://github.com/junkdog/artemis-odb) and
[Dominion](https://github.com/dominion-dev/dominion-ecs-java), Alma attempts a more focused and simple approach while
trying
to keep the same level of performance.

As Alma is based on DOP, neither `Entities` nor `Components` implement their own functionality (with the exception
that components can copy themselves onto another instanced component). `Entities` act as an agent to group and identify
`components`, the latter being just data structures with (almost) no functionality. As in other ECS architectures, the
behaviour of entities is defined by the registered `Systems` that operate on certain types `Components`. These Systems
may be simple lambda functions or more complex objects that implement some method that consumes the selected component
types.

## Architecture

The basis of Alma revolves around compositions and their management. In Alma, a `Composition` is an _unique_ combination
of
component types that define a type of entity. In any composition, one component type must only appear once, otherwise an
Exception should be thrown. In other ECS implementations a Composition may be called Archetype, usually having the same
purpose.

Compositions are handled by a manager class conveniently named `CompositionManager` (`CM` for short), and this class is
the only access point
for Compositions. The CompositionManager keeps track of every composition and every class that may be part of any
composition.
Every time a composition is created, the `CM` registers the composition to every class that is included in it. In this
manner,
the compositions that include one specific class can be quickly assessed and even intersected, to obtain all the
compositions
that include a combination of Component types.