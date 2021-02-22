# Adrenaline

Adrenaline is a strategic board game from Cranio Creations. You will be called to fight in the bloody Arena, where the resolution of the conflict among 5 different Factions will be solved in order to avoid the destruction of what remained of the planet after a big war. You will engage with this fight against other heroes running, shooting, collecting weapons and ammos in a maze that will challenge your spirit of survival.

In agreement with the company Cranio Creations, our goal was to develop the digital version of this game. This matters a lot for us as it is our first big project as software engineers and our Bachelor's thesis.

#### Structure of the repo
As we structured our work in a client-server architecture, we needed to keep these entities separated in two different directories. The directory ```common``` contains the files that need to be shared between them.  

#### Getting started and setting up the game 
In order to start the game we need to launch the server with the command ```java -jar server.jar```. You will see the sentence "Server listening" when everything will be ready.
//distinguere tra cli e gui
//
For each player that wants to enter the lobby of the game you need to launch on a different terminal (or machine connected in the same LAN). You can do it by using the command ```java -jar client.jar```. 
Each game can contain from 3 to 5 players. Each player joins a lobby and when the required number of players is reached, a timer is started. When the timer ends the game starts. If a new player joins the lobby before the time is up, the timer is restarted unless the maximum number of players has been reached.
At the beginning of the game there are some setup steps that need to be done. They are performed by the first player who entered the lobby. They involve choosing:
    - the map (indicate a number between 1 and 4)
    - the number of starting skulls to put on the killshot track (varies between 5 and 8)
After that, each player will choose its own colour. Possible choices are: red, white, violet, yellow, green.
Each choice made in the game (both in the setup phase and in the core one) must be confirmed by sending an "ok" command to the server. If not sure about the choice you can use an "undo" command in specific situations of the game.
Each player will be then provided with an ammo cube for each of the three colours (red, yellow, blue).
At this point the game can start.

#### Overview of game rules
On his very first turn, each player starts by determining its own spawnpoint:
    1. 2 power ups are assigned to each player after being drawn from the deck.
    2. The player chooses to keep one of them and discards the other. Its character will spawn in the spawnpoint having the same colour of the discarded card. 
After this setup phase, each turn will be structured in the following way.
Each player executes 2 actions on his turn. The possible actions are:
    - RUN AROUND
    - GRAB STUFF
    - SHOOT
The RUN AROUND action allows the player to move 1, 2 or 3 squares. A move is always from one square to an adjacent square
(not diagonally). You can move through a door but not through a wall.
The GRAB STUFF action allows you to grab stuff on the squares. Every square has stuff. This action includes a free move. You can either:
    - Move one square and grab the stuff in that new square.
    - Or stay and grab the stuff in your current square.
Depending on the kind of square you grab your item (simple square or spawnpoint square) you can either gain:
    - An ammo card, that contains ammos which are required for the shooting phase
    - An already loaded weapon. To grab a weapong you need to pay it with the ammos you own. You can have a maximum of 3 weapons.
The SHOOT action is the most exciting part. This action is also the most singular one as the way you execute the shooting is strongly connected with the weapon you are using. In order to shoot you need to:
    1. Select one among your loaded weapons
    2. Specifiy one or more targets
    3. (Optional) Pay an additional cost if you want to strengthen your attack
    4. Resolve the effects involved by your attack
Every time a player is attacked he receives damage and lose life points. When a player is particularly weak, the adrenaline level increases and this leads to more performing actions. In particular:
    - If your board has 3 or more points of damage, your GRAB STUFF action improves. When you use it, you can move up to 2 squares before grabbing.
    - If your board has at least 6 damage, your SHOOT action also improves. As part of that action, you can move 1 square before shooting.
Whenever a player fills its board with damage points, he dies and needs to respawn in a new square. When a player dies, a skull from the killshot track is removed and different points are assigned to the other players according to the amount of damage each of them inferred to the dead player. The objective of the game is to get the most points. 
More detailed game rules can be found [here](https://github.com/user/repo/blob/branch/other_file.md).
