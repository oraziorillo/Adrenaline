# Adrenaline

Adrenaline is a strategic board game from Cranio Creations. You will be called to fight in the bloody Arena, where the resolution of the conflict among 5 different Factions will be solved in order to avoid the destruction of what remained of the planet after a big war. You will engage with this fight against other heroes running, shooting, collecting weapons and ammos in a maze that will challenge your spirit of survival.

In agreement with the company Cranio Creations, our goal was to develop the digital version of this game. This matters a lot for us as it is our first big project as software engineers and our Bachelor's thesis.

#### Structure of the repository
As we structured our work in a client-server architecture, we needed to keep these entities separated in two different directories. The directory ```common``` contains the files that need to be shared between them.  The client directory is further divided into `cli` and `gui`, as this project is intended to work both with a command-line interface and with a graphic user interface.

#### Getting started
In order to start the game we need to launch the server with the command ```java -jar server.jar```. You can do it on either a machine connected to your LAN or on the same computer you will play on. You will see the sentence "Server listening" when everything will be ready.

**Guide for *cli* users.** Each player that wants to play with the command-line has to launch the client on a different terminal (or machine connected in the same LAN) by using the command ```java -jar cli.jar```. 

**Guide for *gui* users.**  Each player that wants to play with the graphic interface has to launch the client on a different terminal (or machine connected in the same LAN) by using the command ```java -jar gui.jar```. 

#### Setting up the game 

Each game hosts from 3 to 5 players. Each player that requests to play is put in a lobby and, when the minimum number of players to create a game is reached, a countdown starts. When the time is over the game starts. If a new player joins the lobby before the time is up, the countdown is restarted.
At the beginning of the game there are some setup steps that need to be done. They are performed by the first player who joined the lobby. They involve choosing:

- the map
- the number of skulls to put on the killshot track (when that number of kills is reached the game ends)

Then, each player has to choose its own character. Each choice made in the game (both in the setup phase and in the core one) must be confirmed by sending an *ok* command to the server. This lets you revert a wrong choice by using the command  *undo* at almost any time. 

Throughout the course of the game you will be guided as the client will suggest you the command to use in some situations. However, make sure to have a instruction book at hand (and a printed game board if you play by command-line)   

At this point the game can start.

#### General overview of game rules

On his very first turn, each player starts by where its character will spawn: each player draws 2 power ups from the power ups deck, then he/she chooses one of them to be kept and discards the other. Its character will spawn on the spawn point having the same colour of the discarded card. 

After this setup phase, each turn will be structured in the following way.

Each player executes 2 actions on his turn. The possible actions are:  

- *run around*
- *grab stuff*
- *shoot*

The *run around* action allows the player to move 1, 2 or 3 squares away on adjacent squares (not diagonally). You can move through a door but not through a wall.  
The *grab stuff* action allows you to grab stuff on a square that is at most one square away from your position (if you chose a square different from your current one, you will move there). The spawn points contain weapons while the others contain ammunition. 

The *shoot* action is the most exciting part. This action is also the most singular one as the way you execute the shooting is strongly connected with the weapon you are using.  In order to shoot you need to:  

1. Select one of your loaded weapons  
2. Select one or more targets  
3. (Optional) Pay an additional cost if you want to strengthen your attack if the weapon allows it
4. (Optional) Switch fire mode if your weapon has multiple ones
5. Resolve the effects involved by your attack

Every time a player is shot he looses life points. When a player is particularly weak, the adrenaline level increases and this leads to more performing actions. In particular: 

- If you took 3 or more damage points, you can *grab stuff* up to 2 squares away from your position  

- If you took 6 or more damage points, your *shoot* action also improves. As part of that action, you can move 1 square before shooting.  

Whenever a character dies, it re-spawns on a new square. In addition, a skull from the killshot track is removed and different points are assigned to the other players according to the amount of damage each of them inferred to the dead player. 

When the last skull is removed from the killshot track the game ends and the player who has the most points wins.

More detailed game rules can be found [here](https://github.com/oraziorillo/adrenalina/blob/master/handbooks/adrenaline-rules.pdf), while details about the weapons are available [here](https://github.com/oraziorillo/adrenalina/blob/master/handbooks/adrenaline-rules-weapons.pdf).

#### [Update]

Up to now, the *cli* is complete and fully usable. However, during the last update, we noticed a bug in the *gui* that prevents it to correctly launch the game after the setup phase. We are working in order to solve this issue as soon as possible.
