<h1 align="center">Tugas Kecil 3 IF2211 Strategi Algoritma</h1>
<h1 align="center">13522047 - Farel Winalda</h3>
<h3 align="center">UCS Algorithm, Greedy Best First Search, and A* Implementation in Solving the Word Ladder Game</p>

## Table of Contents

- [Overview](#overview)
- [Abstraction](#abstraction)
- [Built With](#built-with)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [File Structures Overview](#file-structures-overview)
- [Links](#links)


## Overview
<p>Lecturer : Dr. Ir. Rinaldi Munir, M.T.</p>

Here is the purpose of making this project :
- To implement Uniform Cost Search Algorithm in making the optimized searching algorithm.
- To implement Greedy Best First Search Algorithm in making the optimized searching algorithm.
- To implement A* Algorithm in making the optimized searching algorithm.
- To implement and determine optimal heuristic algorithm

## Abstraction

In the project of creating a bot for the game Etimo Diamonds, we are using a greedy algorithm to acquire as many diamonds as possible in the most efficient way. We apply a greedy approach by considering efficiency through emptying the inventory back to the base without wasting any remaining diamonds in the inventory and by considering the presence of clusters (groups of diamonds). We are also contemplating the use of teleportation to reach targets more quickly and employing a red button if there are no more clusters or only a few diamonds left. In this implementation, our submission bot is named AkiongBot which implements Greedy by Clustering Efficiency Concept to obtain the highest score. AkiongBot takes account of the total value of a cluster which is then divided by the total distance of bot to cluster, cluster to base, and distance inside cluster.

## Built With

- [Java](https://www.java.com/en/)
- [JavaFX](https://openjfx.io/)

## Prerequisites

To run this project, you will need to perform several installations, including:
- `Java Development Kit (JDK)` : You'll need JDK 11 or later because JavaFX was decoupled from the JDK starting from Java 11.
- `JavaFX SDK` : JavaFX is no longer included with the JDK from Java 11 onward, you need to download it separately. For [Windows](https://download2.gluonhq.com/openjfx/22.0.1/openjfx-22.0.1_windows-x64_bin-sdk.zip) and for [Linux](https://download2.gluonhq.com/openjfx/22.0.1/openjfx-22.0.1_linux-x64_bin-sdk.zip)

## Installation

If you want to run this program you will have 2 terminal opened for the GameEngine and Bot

1. Clone this repository :
```shell
git clone https://github.com/FarelW/Tucil3_13522047
```

2. Open directory : 
```shell
cd Tucil3_13522047
```

3. Edit the MODULE_PATH in both build.bat and run.bat file & located it in same location that you installed the javafx library (for example i installed on D drive):
```shell
set MODULE_PATH=D:\Coding\Lib\javafx-sdk-22.0.1\lib
```

4. Compile the program:
```shell
./build
```

5. Run the program:
```shell
./build
```

## File Structures Overview
This repository contains main folder structure such as _doc_, _public_, and _src_.
- `doc`: This folder contains documents that provide accountability for the development of this project, as part of a major assignment. In other words, the "doc" folder will contain reports created for this project.
- `src`: This folder contains the primary codebase for building this project, consistings of GUI folder and Algorithm folder
- `bin`: This folder contains the compiled output of the project from the src folder
- `test`: This folder contains the result of several algorithm testing

```
.
├── bin
├── doc/
│   └── Tucil3_13522047.pdf
├── src/
│   ├── resources/
│   │   ├── MainController.java
│   │   ├── Mainscene.fxml
│   │   └── style.css
│   ├── utils/
│   │   ├── AStarSearch.java
│   │   ├── Generate2Words.java
│   │   ├── GreedyBestFirstSearch.java
│   │   ├── SearchResult.java
│   │   ├── UniformCostSearch.java
│   │   └── WordLadder.java
│   ├── Main.java
│   └── words.txt
├── test/
│   ├── TC1(dog to cat).png
│   ├── TC2(lamp to mice).png
│   ├── TC3(frown to smile).png
│   ├── TC4(charge to comedo).png
│   ├── TC5(cheddar to panther).png
│   ├── TC6(nose to swab).png
│   └── TC7(research to compound).png
├── .gitignore
├── README.md
├── build.bat
└── run.bat
```

## Links
- Repository : https://github.com/FarelW/Tucil3_13522047
- Issue tracker :
   - If you encounter any issues with the program, come across any disruptive bugs, or have any suggestions for improvement, please don't hesitate to tell the author