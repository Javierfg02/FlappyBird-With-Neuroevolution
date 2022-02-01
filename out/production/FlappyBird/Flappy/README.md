# FlappyBird With Modified Neuroevolution

---

## Project Status
This project is a work in progress. The Bird sure can learn
how to fly, but many improvement and bug fixes are underway. You
can find a list of planned improvements in the 
[Extensions](#extensions) section of this README.

## Installation
All the code was done in Java 17.0.1 using the JavaFX graphics 
library, so that no ~~installation~~ (apart from Java)
is required!

## Description
The project emulates the famous (or infamous) game of Flappy Bird
and applies NeuroEvolution (a neural network with a genetic 
algorithm) to it so that the Bird learns how to flap and
slip through the pipes by itself.

It is possible to play manually, but if at any point laziness
overwhelms you, you can always let the computer do the work.

### Key differences from the standard Flappy Bird
The project emulates the standard Flappy Bird, but there are some
key differences which affect gameplay:
- Only the space bar will make the bird flap. 
- The bird cannot flap above the top of the window.
- The bird will not die upon hitting the ground
- Pressing space bar will not start the game, a game mode must be selected and the 'play' button must be pressed to begin.

There are other less significant differences such as the design of
the bird which represents a roadman bird as well as the design of
the pipes. The way in which the bird rotates was modified to make
it more fluid. Sound effects may also differ.

Differences in the algorithm are also outlined below.

### The algorithm in more detail
Explaining how the NeuroEvolution algorithm works is out of the
scope of this README, I will explain how the algorithm I have used
differs from the norm. To read more into NeuroEvolution algorithms,
you may find the following useful: 
[the least reading](https://towardsdatascience.com/neat-an-awesome-approach-to-neuroevolution-3eca5cc7930f),
[for nerds](http://nn.cs.utexas.edu/downloads/papers/stanley.ec02.pdf).

In short, NeuroEvolution combines a neural network with a genetic
algorithm which greatly simplifies the neural network algorithms.

The standard is to create a generation of many (a population)
Birds (in the case of Flappy Bird) which all contain their 
own neural network with a distinct set of weights.

Once this population dies, a new generation of birds is created.
The weights of the birds in this new generation, however, are
inherited from the birds that best performed in the last generation -
the fittest birds per se. In this case, the fittest birds are the
ones which travelled the furthest. These inherited weights also have
a chance of being mutated so that the child birds differ from their
parent birds. Over time, through a process which mirrors natural
selection, the birds learn to stay alive and flap, flap, flap.

My algorithm, however, does not use a population of birds, 
but only one bird. The weights of this one bird are saved in a file
if the bird beats the high score, and they have a chance of being
mutated every time the bird respawns. If at any point you wish 
to erase the bird's memory, you can simply delete the file named
"output.txt", and the bird will have to learn all over again.

This may slow down learning, but makes it
more exciting to see your bird flap and fail, fail and flap. It
may also further simplify the algorithm.

## Instructions 
The aim of the game is to get the highest score possible. The score
increases every time the bird manages to slip through the pipes.

Here is a list of more specific instructions to interact with
the GUI:

|       Key       |                                                    Action                                                     |
|:---------------:|:-------------------------------------------------------------------------------------------------------------:|
|    SPACE BAR    |                                              Makes the bird flap                                              |
|  MANUAL BUTTON  |                                     Enables manual control over the bird                                      |
| COMPUTER BUTTON |              Enables computer control over the bird, <br/> the algorithm will make the bird flap              |
|  RESET BUTTON   | During manual gameplay this respawns the bird. <br/> During computer gameplay the bird respawns automatically |
|   QUIT BUTTON   |                                                Quits the game                                                 |
|     SLIDER      |                                          Sets the speed of gameplay                                           |

You may switch from computer to manual game modes and 
vice-versa only when the bird has died by selecting the desired
game mode and clicking the reset button to respawn the bird.


## Extensions