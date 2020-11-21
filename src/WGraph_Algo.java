package ex1.src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph1;
    /**
     * constructor for the WGraph_Algo,
     */
    public WGraph_Algo() {
        graph1 = new WGraph_DS();
    }
    /**
     * Init the graph on which this set of algorithms operates on.
     * point on a graph to operates on
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        graph1 = g;
    }
    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return graph1;
    }
    /**
     * Compute a deep copy of this weighted graph.
     * the function do it by go over the graph vertices and add each
     * vertex to the new graph , than go over the neighbors of each vertex
     * and add it to the graph, than connect between the vertex and the neighbor.
     * than return the new graph
     * @return
     */
    @Override
    public weighted_graph copy() throws ArithmeticException {
        int node_key, neighbor_key;
        // crate a new graph
        weighted_graph cop_graph = new WGraph_DS(graph1);
        if (graph1 == null)
            return null;
        // go over the graph vertices
        for (node_info current_node : graph1.getV()) {
            node_key = current_node.getKey();
            // add each vertex to the new graph , if it already in it do nothing
                cop_graph.addNode(node_key);
                // go over each vertex neighbors
            for (node_info current_neighbor : graph1.getV(node_key)) {
                neighbor_key = current_neighbor.getKey();
                // add the neighbor to the graph, if already in the graph do nothing
                    cop_graph.addNode(neighbor_key);
                    // connect between a vertex and it neighbor int new graph
                cop_graph.connect(node_key, neighbor_key, graph1.getEdge(node_key, neighbor_key));
            }
        }
        return cop_graph;
    }
    /**
     * Returns true if and only if there is a valid path from EVREY node to each
     * other node. NOTE: assume undirectional graph.
     * the function does it by activate an inner function with one of the vertices
     * in the graph
     * the inner function set every vertex tag that can be reached in any path
     * to the shortest number of vertices from the src vertex
     * than go over the graph vertices, if one of their tag contain -1 (that is
     * mean that is no path to the specific vertex ) the graph is not connected
     * if all had a tag different from -1 the graph is connected.
     * @return
     */
    @Override
    public boolean isConnected() {
        node_info node_temp;
        if (graph1.nodeSize() == 1 || graph1.nodeSize() == 0)
            return true;
        Iterator<node_info> first = this.graph1.getV().iterator();
        node_temp = first.next();
        // activates ann inner function to find the distance from the node_temp
        path_to_all(node_temp.getKey());
        for (node_info current_node : graph1.getV()) {
            if (current_node.getTag() != 0)
                return false;
        }
        return true;
    }
    /**
     * it is an inner function that used by is connected.
     * the function first reset every vertex in the graph , sets the vertex tag to contain -1,
     * then insert the vertex to a queue .
     * than go over the vertex neighbors and if they haven't been visited before ,change their information to grey
     * and their tag to 0 and insert the neighbor to the queue.
     * the function do the same util all the vertices that can be in any path have been fixed .
     * (the queue is empty)
     * if a vertex can be reached at any path his tag and information stay don't change
     * @param src
     */
    private void path_to_all(int src) {
        int node_key;
        node_info temp_node;
        // operate ass a queue
        LinkedList<node_info> list_of_nodes = new LinkedList<node_info>();
        // go over the graph nodes and set their tag
        for (node_info current_node : graph1.getV()) {
            current_node.setTag(-1);
        }
        // set first node and add it to queue
        graph1.getNode(src).setTag(0);
        list_of_nodes.add(graph1.getNode(src));
        // go over the nodes added to the queue until all been handled
        while (!list_of_nodes.isEmpty()) {
            temp_node = list_of_nodes.getFirst();
            node_key = temp_node.getKey();
            list_of_nodes.remove(list_of_nodes.getFirst());
            if (graph1.getV(node_key).size() > 0) {
                // if the specific node have neighbors go over them
                for (node_info current_node : graph1.getV(node_key)) {
                    // if node tag not been changed set it and add the node to the queue
                    if (current_node.getTag()==-1) {
                        current_node.setTag(0);
                        list_of_nodes.add(current_node);
                    }
                }
            }
        }
    }
    /**
     * returns the length of the shortest path between src to dest
     * if no such path --> returns -1
     * the function checks first if there any vertex in the graph and if src and dest
     * vertices are in the graph , if one is incorrect return -1
     * than the function call an inner function that sets every vertex tag, that can
     * be reached by any path from the src vertex, to the short path from src vertex
     * than the function checks if there any path to dest vertex by check that his tag
     * is different from -1.
     * than return the short path from src to dest or -1 if there is no path
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph1 == null)
            return -1;
        if (graph1.nodeSize() == 0)
            return -1;
        // if one of the vertices not in the graph return -1
        if (graph1.getNode(src) == null || graph1.getNode(dest) == null)
            return -1;
        short_path_by_edge(src, dest);
        // return the dest vertex shortest path from src
        if (graph1.getNode(dest).getTag() == Integer.MAX_VALUE)
            return -1;
        else
            return graph1.getNode(dest).getTag();
    }
    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * if no such path --> returns null;
     * the function checks first if there any vertex in the graph and if src and dest
     * vertices are in the graph , if one is incorrect return null
     * than the function call an inner function that returns a collection with the vertices
     * in the path from src to dest
     * the function create a list of the vertices in the path from dest to src
     * and than reverse it
     * than return the list with the vertices in the shortest path from src to dest
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        LinkedList<node_info> path_list1 = new LinkedList<node_info>();
        LinkedList<node_info> path_list2 = new LinkedList<node_info>();
        HashMap<Integer,String> full_path = new HashMap<Integer,String>();
        String prev;
        if (graph1 == null)
            return path_list1;
        if (graph1.getNode(src) == null || graph1.getNode(dest) == null)
            return path_list1;
        full_path = short_path_by_edge(src, dest);
        if (graph1.getNode(dest).getTag() == Integer.MAX_VALUE)
            return path_list1;
        prev = full_path.get(dest);
        path_list1.add(graph1.getNode(dest));
        while (!prev.equals("")) {
            path_list1.add(graph1.getNode(Integer.parseInt(prev)));
            prev = full_path.get(Integer.parseInt(prev));
        }
        while (!path_list1.isEmpty()) {
            path_list2.add(path_list1.getLast());
            path_list1.remove(path_list1.getLast());
        }
        return path_list2;

    }
    /**
     * returns an hashmap collection that hold the information about the vertices in the
     * shortest path from src to dest
     * the function reset all the tag vertices in the graph to Integer MAX VALUE and insert them to a Priority Queue
     * than set the src tag to 0 , and go over his neighbors and set their tag to the weight of the edge between the vertices
     * than pull the next vertex with the smallest tag, and go over his neighbors
     * checks if their tag is bigger than his tag + the edge weight between them.
     * if true then update the neighbor tag and add his information (key and prev) to the hashmap
     * do the same until the queue is empty
     * then return the hashmap collection that holds the information of the vertices
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    private HashMap<Integer,String> short_path_by_edge(int src, int dest) {
        node_info temp_node;
        int node_key, neighbor_key;
        HashMap<Integer,String> prev_contain =  new HashMap<Integer,String>();
        // a priority queue that holds the graph vertices
        PriorityQueue<node_info> node_prior = new PriorityQueue<node_info>();
        // if the dest and src are equal return just one node in the collection
        if (src == dest) {
            graph1.getNode(src).setTag(0);
            prev_contain.put(src,"");
            return prev_contain;

        }
        // rest every node node in the graph , tag to int max value
        for (node_info current_node : graph1.getV()) {
            current_node.setTag(Integer.MAX_VALUE);
            node_prior.add(current_node);
        }// sets the src node and insert it to the queue and the collection
        node_prior.remove(graph1.getNode(src));
        graph1.getNode(src).setTag(0);
        node_prior.add(graph1.getNode(src));
        prev_contain.put(src,"");
        // run until sets every node in the path
        while (!node_prior.isEmpty()) {
            // get the first node in queue
            temp_node = node_prior.poll();
            node_key = temp_node.getKey();
            // check if the node have neighbors if it had go over them and sets them
            if (graph1.getV(node_key).size() > 0) {
                // go over the node neighbors
                for (node_info neighbor_node : graph1.getV(node_key)) {
                    neighbor_key = neighbor_node.getKey();
                    if (neighbor_node.getTag() > temp_node.getTag() + graph1.getEdge(node_key, neighbor_key)) {
                        // if the vertex already in the collection update it
                        if(prev_contain.get(neighbor_key)!=null)
                        {prev_contain.remove(neighbor_key);}
                        prev_contain.put(neighbor_key,"" + node_key);
                        neighbor_node.setTag( temp_node.getTag() + graph1.getEdge(node_key, neighbor_key));
                        node_prior.remove(neighbor_node);
                        node_prior.add(neighbor_node);
                    }
                }
            }
        }
        return  prev_contain;
    }
    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved, if not false
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream myFile = new FileOutputStream(file);
            ObjectOutputStream object_os = new ObjectOutputStream(myFile);
            object_os.writeObject(this.graph1);
            object_os.close();
            myFile.close();
        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream myFile = new FileInputStream(file);
            ObjectInputStream object_is = new ObjectInputStream(myFile);
            weighted_graph graph_temp = (weighted_graph) object_is.readObject();
            this.init(graph_temp);
            object_is.close();
            myFile.close();
        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }
        return true;
    }
}
