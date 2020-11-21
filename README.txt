weighted graph :
This repository represents a structure of a weighted graph,
with some algorithms that can be used on the graph.

uses of the program:
1) can be used as a GPS, can contain many cities locations,
 and calculate the shortest path from one destination to another
2) can be used as a shape creator, create mane nodes and edges for a shape,
and calculate the shortest path from one node to another..

structure:
the graph is built from vertices that are represented by the NodeInfo class,
that implements from the node_info interface.
the graph is represented by the WGraph_DS class that implements from the weighted_graph interface.
the algorithms of the graph are represented by the WGraph_Algo,
that implements from the weighted_graph_algorithms interface.

the graph:
the graph class includes functions such as:

* add node -  add a new node to the graph
* has an edge - return if is an edge between two vertices
* connect - add a new edge to the graph between two vertices
* get v - return a full collection of the graph vertices
* remove node - remove a vertex from the graph
* remove edge - remove an edge between two vertices
* node size - return the number of vertices in the graph
* edge size - return the number of edges in the graph

the graph algorithms :
the graph algorithms class includes functions such as:

* init - initiate the graph algorithm to point on a graph
* copy - Compute a deep copy of the graph in the graph algorithms
* is connected - return true if all of the graph vertices have a path from one to another.
* shortest path Dist - return the shortest path by weight from one vertex to another
* shortest Path - return a list with the information of the vertices in the path from one vertex to another
* save - save a graph from the graph algorithms as an object to file
* load - load a graph as an object to the graph algorithms

particulars functions that based on an external code:

* is connected function base on the "breadth-first search" algorithm.
* shortest path Dist and shortest Path based on the "Dijkstra's " algorithm.

links:
* Dijkstra's algorithm: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
* breadth-first search algorithm: https://en.wikipedia.org/wiki/Breadth-first_search

How To Run :
to run this program you should pull this repository to your git
by the next commend -
$ git clone https://github.com/LielBerniker/Weighted-Graph.git
and to use the program on your computer create a main class,
and use the class in the weighted graph program