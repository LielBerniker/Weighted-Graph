package ex1.tests;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    private static Random _rnd = null;
    /**
     * check node size when insert nodes with same key
     * and multiply nodes
     */
    @Test
    public void valid_node_add_test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(0);
        assertEquals(1,graph_test.nodeSize());
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        assertEquals(4,graph_test.nodeSize());
    }
    /**
     * check if edge really exist ,when there is no edge
     * and when it is
     */
    @Test
    public void edge_existence_test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        assertFalse(graph_test.hasEdge(0,1));
        graph_test.connect(0,1,5.5);
        assertTrue(graph_test.hasEdge(0,1));
    }
    /**
     * check if return null when vertex key not exist
     * check if return not null if the vertex exist
     */
    @Test
    public void exist_node_test() {
        weighted_graph graph_test = new WGraph_DS();
        assertNull(graph_test.getNode(0));
        graph_test.addNode(0);
        assertNotNull(graph_test.getNode(0));
    }
    /** checks a proper edge connect and improper edge connect */
    @Test
    public void connect_Improper_and_proper_edge_size_test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.connect(0,1,5);
        assertTrue(graph_test.hasEdge(0,1));
        graph_test.addNode(2);
        graph_test.connect(2,1,-1);
        assertFalse(graph_test.hasEdge(1,2));
        graph_test.connect(2,0,0);
        assertTrue(graph_test.hasEdge(0,2));
    }
    /**
     * check if update edge size change edge size
     * also check if add the same edge don't affect the edge size
     * checks if both edges contain the edge
     */
    @Test
    public void connect_renew_edge_size_test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.connect(0,1,7);
        graph_test.connect(0,1,8);
        assertEquals(8,graph_test.getEdge(0,1));
        graph_test.connect(0,1,8);
        assertEquals(1,graph_test.edgeSize());
        assertTrue(graph_test.hasEdge(0,1)&&graph_test.hasEdge(1,0));
    }

    /**
     * check that the node size is correct
     * and if getv return all of the vertices in the graph
     */

    @Test
    void all_vertices_test() {
        weighted_graph graph_test = new WGraph_DS();
        boolean check_v =true;
        graph_test.addNode(0);
        graph_test.addNode(1);
        assertEquals(2,graph_test.getV().size());
        graph_test.addNode(2);
        graph_test.addNode(3);
        for (node_info current_node: graph_test.getV()) {
            if(graph_test.getNode(current_node.getKey())==null){
                check_v = false;
                break;
            }
        }
        assertTrue(check_v);
    }
    /**
     * check that the neighbors size is correct
     * and if getv return all of the neighbors vertices
     */
    @Test
    void all_vertex_neighbor_test() {
        weighted_graph graph_test = new WGraph_DS();
        boolean check_v = true;
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.connect(0, 1, 2);
        assertEquals(1,graph_test.getV(1).size());
        graph_test.connect(0, 2, 2);
        graph_test.connect(0, 3, 2);
        for (node_info current_node : graph_test.getV(0)) {
            if (graph_test.hasEdge(0, current_node.getKey()) == false) {
                check_v = false;
                break;
            }
        }
        assertTrue(check_v);
    }
    /**
     * checks if remove keep the node size correct
     * and the edge size correct
     */
    @Test
    void remove_Node_size_edge_test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.removeNode(0);
        assertEquals(0,graph_test.nodeSize());
        graph_test.addNode(0);
        graph_test.removeNode(0);
        assertEquals(0,graph_test.nodeSize());
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.removeNode(0);
        graph_test.removeNode(0);
        assertEquals(1,graph_test.nodeSize());
        graph_test.addNode(0);
        graph_test.connect(0,1,7);
        graph_test.removeNode(0);
        assertEquals(0,graph_test.edgeSize());
    }
    /**
     * checks if vertex contain the same edge with his neighbor
     */
    @Test
    public void edge_contain_same_size_test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.connect(0,1,4.7);
        assertEquals(4.7,graph_test.getEdge(1,0));
        assertEquals(4.7,graph_test.getEdge(0,1));
    }
    /**
     * checks if remove a node delete all the edge connected to it */
    @Test
    void remove_Node_0_edge_test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.addNode(4);
        graph_test.addNode(5);
        for (int i =1;i<=5;i++)
        {
            graph_test.connect(0,i,i);
        }
        graph_test.removeNode(0);
        assertEquals(0,graph_test.edgeSize());
    }
    /**
     * checks remove effect on the edge size */
    @Test
    void remove_one_Edge_test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.removeEdge(0,1);
        assertEquals(0,graph_test.edgeSize());
        graph_test.connect(0,1,5);
        graph_test.removeEdge(0,1);
        assertEquals(0,graph_test.edgeSize());
    }
    /**
     * checks node size when adding vertices */
    @Test
    void node_Size_regular_test() {
        weighted_graph graph_test = new WGraph_DS();
        assertEquals(0,graph_test.nodeSize());
        graph_test.addNode(0);
        assertEquals(1,graph_test.nodeSize());
        for (int i =1; i<100;i++)
        {
          graph_test.addNode(i);
        }
        assertEquals(100,graph_test.nodeSize());
    }
    /**
     * checks edge size when adding and connecting vertices */
    @Test
    void edge_Size_regular_test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        assertEquals(0,graph_test.edgeSize());
        graph_test.connect(0,1,4);
        assertEquals(1,graph_test.edgeSize());
        for (int i = 2; i<=300;i++)
        {
            graph_test.addNode(i);
            graph_test.connect(0,i,4);
        }
        assertEquals(300,graph_test.edgeSize());
    }
    /**
     * checks the changes counter in a variety of function(add,remove,connect..)
     */
    @Test
    void MC_regular_test() {
        weighted_graph graph_test = new WGraph_DS();
        assertEquals(0,graph_test.getMC());
        graph_test.addNode(0);
        assertEquals(1,graph_test.getMC());
        graph_test.addNode(1);
        assertEquals(2,graph_test.getMC());
        graph_test.connect(0,1,3);
        assertEquals(3,graph_test.getMC());
        graph_test.connect(1,0,3);
        assertEquals(3,graph_test.getMC());
        graph_test.addNode(2);
        graph_test.connect(0,2,6);
        graph_test.removeNode(0);
        assertEquals(8,graph_test.getMC());

    }
    /**
     * checks if the time of creating 1 million vertices and 10 million edges
     * is under 10 seconds */
    @Test
    void million_nodes_ten_million_edge_runtime_test() {
        weighted_graph graph_test = new WGraph_DS();
      int mil = 1000000;
      double run_time_s;
      long endTime,finalTime,startTime = System.currentTimeMillis();
      boolean check=false;
       for (int i = 0; i <mil;i++)
       {
           graph_test.addNode(i);
       }
       for (int i = 0, j=mil-1; i <mil;i++)
       {
           for (int k=0;k<10;k++ ) {

               graph_test.connect(i, j-k, 2);
           }
       }
        endTime = System.currentTimeMillis();
       finalTime = endTime - startTime;
       run_time_s =((double)finalTime)/1000;
       if (run_time_s<10.0)
           check=true;
        System.out.println("node size: " + graph_test.nodeSize() +" edge size: "
                + graph_test.edgeSize() + " runtime seconds: " + run_time_s);
       assertTrue(check);
    }
}