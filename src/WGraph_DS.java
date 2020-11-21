package ex1.src;



import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, node_info> nodes_of_graph; // collection of the graph vertices
    private HashMap<Integer,HashMap<Integer,Double>> edges_of_graph ;
    private int edges_number; // number of edges in the graph
    private int MC; // number of changes in the graph (remove node or edge, add node or edge) .


    public WGraph_DS()
    {
        this.nodes_of_graph = new HashMap<Integer, node_info>();
        this.edges_of_graph = new HashMap<Integer,HashMap<Integer,Double>>();
        this.edges_number=0;
        this.MC= 0;

    }
    public WGraph_DS(weighted_graph graph_const)
    {
        this.nodes_of_graph = new HashMap<Integer, node_info>();
        this.edges_of_graph = new HashMap<Integer,HashMap<Integer,Double>>();
        this.edges_number=0;
        // subtract the number of edges and nodes from the copy MC to get the exactly MC copy
        this.MC= graph_const.getMC()-graph_const.nodeSize()-graph_const.edgeSize();
    }
    private class NodeInfo implements node_info,Comparable,Serializable
    {
        private int Key; // Unique ey of node
        private String Information; // Information about the node
        private double Tag; //Temporal data which can be used by algorithms

        /** constructor for the vertex
         * @param key */
        public NodeInfo(int key) {

            this.Key = key;
            this.Information = "" ;
            this.Tag = -1;
        }
        /** Return the key (id) associated with this node.
         * @return */
        @Override
        public int getKey() {
            return this.Key;
        }
        /** return the information associated with this node.
         * @return
         */
        @Override
        public String getInfo() {
            return this.Information;
        }
        /** Allows changing the information associated with this node.
         * @param s */
        @Override
        public void setInfo(String s) {
            this.Information = s;
        }
        /** Temporal data for the vertex (aka distance, color, or state)
         * used by the graph algorithms
         * @return */
        @Override
        public double getTag() {
            return this.Tag;
        }
        /** Allow setting the "tag" value for temporal marking an node - common
         * @param t - the new value of the tag */
        @Override
        public void setTag(double t) {
            this.Tag = t;
        }
        @Override
        /** return true if the vertices are equal by their keys
         * @param other_n - a vertex to compare with
         * @return */
        public boolean equals (Object other_n)
        {// if other is not from the same object return false
            if(!(other_n instanceof node_info))
                return false;
           node_info node_other = (node_info)other_n;
            // return true equal if their keys are equal
            return this.getKey() == node_other.getKey();
        }
        /** return 1 if the node tag is bigger than other_n node tag
         * if smaller return -1 , and if equal return 0;
         * @param other_n - a vertex to compare with
         * @return */
        @Override
        public int compareTo(Object other_n) {
            node_info node_other = (node_info)other_n;
            if(this.getTag()<node_other.getTag())
                return -1;
            else if(this.getTag()>node_other.getTag())
                return 1;
            else
                return 0;
        }
    }
    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none. */
    @Override
    public node_info getNode(int key){
        return nodes_of_graph.get(key);
    }

    /** return true if and only if there is an edge between node1 and node2
     * the function checks if both of the vertices have any edge
     * than checks if there is an edge between the to nodes
     * @param node1
     * @param node2
     * @return */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(node1==node2||edges_of_graph.get(node1)==null||edges_of_graph.get(node2)==null)
            return false;
        if((edges_of_graph.get(node1).get(node2)!=null)&&(edges_of_graph.get(node2).get(node1)!=null))
                return true;

        return false;
    }
    /** return the weight if their ia an edge between node1 to node2.
     * In case there is no such edge - should return -1
     * the function checks if both of the vertices have any edge
     * and if node1 not equal to node2
     * than checks if the vertices have an edge between them, if they do return it
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!(this.hasEdge(node1,node2)))
            return -1;
        return edges_of_graph.get(node1).get(node2);
    }
    /**
     * add a new node to the graph with the given key.
     * if there is already a node with such a key -> no action should be performed.
     * use the inner class NodeInfo ass a new mode to the graph
     * @param key
     */
    @Override
    public void addNode(int key) {
    node_info node_add;
    // checks if the vertex doesn't exist
    if(getNode(key)==null)
    {node_add = new NodeInfo(key);
    // add the vertex to the graph vertices
        nodes_of_graph.put(key,node_add);
    MC++;}
    }
    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     * if the weight is lower than 0 than the function throws an error
     * the graph contain an hashmap inside an hashmap , that represent the edges
     * of the graph and their neighbors.
     * the function checks if the vertex node1 have any neighbors if not it add the vertex
     * to the edge hashmap, and add to his neighbors node2 , do the same to vertex node2
     * if the vertex have edges already , just add node2 to his neighbors. the same to node2
     *
     * @param node1
     * @param node2
     */
    @Override
    public void connect(int node1, int node2, double w) throws ArithmeticException{
        HashMap<Integer,Double> edge_new1 = new HashMap<Integer,Double>();
        HashMap<Integer,Double> edge_new2 = new HashMap<Integer,Double>();
        boolean check_edge=true,check_mc=true;
        // if the size of the edge is lower than one do nothing
        try {
            if(w<0)
                throw  new ArithmeticException();
        }
        catch (Exception e)
        {
            return;
        }
        // if is the same node ,or one of the nodes not in the graph do nothing;
      if(node1==node2||getNode(node1)==null||getNode(node2)==null)
          return;
      // if node1 got edges already
      if(edges_of_graph.get(node1)!=null)
      { // if node1 already connect to node2
          if(edges_of_graph.get(node1).get(node2)!=null) {
              // if the size of edge is different from w
              if (edges_of_graph.get(node1).get(node2) != w)
              {edges_of_graph.get(node1).remove(node2);
                  edges_of_graph.get(node1).put(node2,w);}
              else
              { check_mc=false;}
              check_edge= false;
          }
          else
              //add the edge if not exist
              edges_of_graph.get(node1).put(node2,w);
              }
      else
      {// if there is no previous edges create a collection of edges
          edge_new1.put(node2,w);
          edges_of_graph.put(node1,edge_new1);
      }
        // if node2 got edges already
        if(edges_of_graph.get(node2)!=null)
        { // if node2 already connect to node1
            if(edges_of_graph.get(node2).get(node1)!=null) {
                // if the size of edge is different from w
                if (edges_of_graph.get(node2).get(node1) != w)
                {edges_of_graph.get(node2).remove(node1);
                    edges_of_graph.get(node2).put(node1,w);}
                else
                    check_mc=false;
                check_edge= false;
            }
            else
                //add the edge if not exist
                edges_of_graph.get(node2).put(node1,w);
        }
        else
        {// if there is no previous edges create a collection of edges
            edge_new2.put(node1,w);
            edges_of_graph.put(node2,edge_new2);
        }
        if(check_edge)
            edges_number++;
        if(check_mc)
            MC++;
    }
    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return nodes_of_graph.values();
    }
    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * the function create a collection that contain all the neighbors of node_id
     * and return it
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        HashSet<node_info> neighbors_values = new HashSet<node_info>();
        if(edges_of_graph.get(node_id)!=null) {
            for (int current_neighbor : edges_of_graph.get(node_id).keySet()) {
                neighbors_values.add(getNode(current_neighbor));
            }
        }
        return neighbors_values;
    }
    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * the function go iver the current vertex neighbors and delete from
     * their neighbors collection the current vertex , than delete the vertex from
     * the graph vertices
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_info removeNode(int key) {
        if(getNode(key)!=null)
        {// go over the vertex neighbors
            for (node_info current_neighbor:getV(key)) {
                // remove the vertex from the collection of neighbors , of each neighbor
                edges_of_graph.get(current_neighbor.getKey()).remove(key);
                edges_number--;
                MC++;
            }
            // remove the vertex from the graph
            edges_of_graph.remove(key,edges_of_graph.get(key));
            MC++;
            return nodes_of_graph.remove(key);
        }
        return null;
    }
    /**
     * Delete the edge from the graph
     * if the edge does not exist does nothing
     * the function checks if the edge exist, if it does it remove each vertex
     * from the other vertex neighbors collection
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        // checks if the vertices have edge between them
        if(this.hasEdge(node1,node2))
        {
            edges_of_graph.get(node1).remove(node2);
            edges_of_graph.get(node2).remove(node1);
            MC++;
            edges_number--;
        }
    }
    /** return the number of vertices (nodes) in the graph.
     * @return
     */
    @Override
    public int nodeSize() {
        return nodes_of_graph.size();
    }
    /**
     * return the number of edges (unidirectional graph).
     * @return
     */
    @Override
    public int edgeSize() {
        return edges_number;
    }
    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph cause an increment in the ModeCount
     * @return
     */
    @Override
    public int getMC() {
        return MC;
    }
    /**
     * return true if the graphs are equal
     * the function compare first the uber of vertices and edges in the graphs
     * if one of them is not equal return false.
     * than checks that each vertex contained in the other_g graph,
     * and that each vertex contain the same neighbors
     * this function used by the assert equals in the junit test
     * and can be used in any equal check between two graphs
     * @param other_g
     * @return - true if the graphs are equal, if not false.
     */
    @Override
 public boolean equals (Object other_g)
 {
     int current_key,neighbor_key;
     // if other is from a different object
     if(!(other_g instanceof weighted_graph))
         return false;
     weighted_graph graph_other = (weighted_graph)other_g;
     // if one or more of the following is not equal : number of edges,of vertices , of changes in graph
     if(graph_other.nodeSize()!=this.nodeSize()||graph_other.edgeSize()!=this.edgeSize())
         return false;
     // go over the graph and check if other graph had the same vertices
     for (node_info current_node:this.getV()) {
         current_key = current_node.getKey();
         if(graph_other.getNode(current_key)==null)
             return false;
         // go over the current vertex neighbors and checks if it had the same neighbors as the vertex in other graph
         for (node_info neighbor_node:this.getV(current_key)) {
             neighbor_key = neighbor_node.getKey();
             if(!graph_other.hasEdge(current_key,neighbor_key))
                 return false;
             // if the size of the edge is different
             if(this.getEdge(current_key,neighbor_key)!=graph_other.getEdge(current_key,neighbor_key))
                 return false;
         }
     }
return true;
 }
}
