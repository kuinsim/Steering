In this project, I worked with a simple game engine written in Java that simulates cars moving around in a two-dimensional plane. In this "game", there is a scenario that contains a number of walls, and a number of cars. The goal is to make some controllers to make some of the cars behave in specific ways using steering behaviors. Each car can be controlled through "steer", "throttle", and "brake".

One thing to note is that I implemented a Vector class that contains methods for various calculations such as subtraction, multiplication, dot product, normalization, and finding the magnitude of a given vector. In addition, I implemented an OutputFiltering class that transforms the output of the steering behaviors into the steer/throttle/brake commands for the cars. I also implemented additional methods and variables in the Game and Car classes to use in my controllers.

The first task was to implement SeekController which controls the blue car to "seek" the "target" red car using the "seek" steering behavior. The SeekController extends the Controller class and uses the method seekVector to calculate and return the seek vector. I referenced the following formula to implement the seek behavior:  
  
Seek(character, E)  
D = E - character.position  
ND = D / |D|  
A = ND * maxAcceleration  
Return A  

The second task was to implement ArriveController which controls the red car to "arrive" at the "target" using the "arrive" steering behavior. The ArriveController extends the Controller class and uses the method arriveVector to calculate and return the arrive vector. I referenced the following formula to implement the arrive behavior:  
  
Arrive(character, E, targetRadius, slowRadius, deltatime)  
D = E - character.position  
Distance= |D|  
If Distance<targetRadius Return (0,0,0)  
If Distance>slowRadius then targetSpeed = maxSpeed  
else targetSpeed = maxSpeed * Distance/slowRadius  
targetVelocity = (D/|D|)*targetSpeed  
A = (targetVelociy – character.velocity)/deltatime  
If |A|>maxAcceleration then A = (A/|A|)*maxAcceleration  
Return A  
  
The third task was to implement the WallAvoidanceController which controls the blue car to "seek" the "target" red car using the "seek" steering behavior without bumping into walls. The WallAvoidanceController extends the SeekController class and uses the methods seekVector and turnVector to calculate and return the seek vectors. Furthermore, I implemented the collisionRectangle and raycasting methods to avoid wall collisions. I referenced the same seek formula from the SeekController in the WallAvoidanceController.
