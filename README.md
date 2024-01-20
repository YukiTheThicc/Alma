
# Alma ECS

The Alma project is a simple yet performant Entity Component System in Java using pooled, chunked data structures. Inspired by
other ECS in Java such as [Artemis](https://github.com/junkdog/artemis-odb) and [Dominion](https://github.com/dominion-dev/dominion-ecs-java)
Alma tries a more specific and simple approach while trying to keep the same level of performance.

Alma is based on DOP and therefore neither entities nor components implement their own functionality. Entities act as a grouping 
element for components, components being just data structures with no functionality. Systems are the ones that create the 
functionality by operating on the type of components that they are designed to work with. Systems are decoupled from each 
other as to improve concurrency and data locality. More complex functionalities should be implemented as emerging behaviors
by applying multiple systems.
