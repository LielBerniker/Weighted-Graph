package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_AlgoTest {
   /** create a graph with 8 vertices and no edges **/
    private weighted_graph create_graph1()
    {
        weighted_graph graph_create = new WGraph_DS();
        for (int i = 0;i<8;i++)
        {
            graph_create.addNode(i);
        }

        return graph_create;
    }
    /** create a graph with 8 vertices and 14 edges **/
    private weighted_graph create_graph2()
    {
        weighted_graph graph_create = new WGraph_DS();
        for (int i = 1;i<9;i++)
        {
            graph_create.addNode(i);
        }
        graph_create.connect(1,2,8);
        graph_create.connect(1,3,2);
        graph_create.connect(1,4,5);
        graph_create.connect(2,6,13);
        graph_create.connect(2,4,2);
        graph_create.connect(3,4,2);
        graph_create.connect(3,5,5);
        graph_create.connect(4,5,1);
        graph_create.connect(4,6,6);
        graph_create.connect(4,7,3);
        graph_create.connect(5,7,1);
        graph_create.connect(6,7,2);
        graph_create.connect(6,8,3);
        graph_create.connect(7,8,6);
        return graph_create;
    }
    /** create a connected graph with 10000 vertices and 10000 edges **/
    private weighted_graph create_graph3()
    {
        weighted_graph graph_create = new WGraph_DS();
        int num = 10000;
        for (int i = 0;i<num;i++)
        {
            graph_create.addNode(i);
            if(i<4500)
            graph_create.connect(i, (int)i*2, 2);
            else
                graph_create.connect(i, (int)i/3, 2);
        }

        return graph_create;
    }
    /** test for the init function **/
    @Test
    void init_test() {
        weighted_graph_algorithms algo_test = new WGraph_Algo();
        weighted_graph graph_test1 = create_graph1();
        weighted_graph graph_test2 = create_graph2();
        algo_test.init(graph_test1);
        assertEquals(graph_test1,algo_test.getGraph());
        graph_test1.removeNode(0);
        assertEquals(graph_test1,algo_test.getGraph());
        assertNotEquals(graph_test2,algo_test.getGraph());
        algo_test.init(graph_test2);
        assertEquals(graph_test2,algo_test.getGraph());
        assertNotEquals(graph_test1,algo_test.getGraph());
        graph_test2.addNode(20);
        assertEquals(graph_test2,algo_test.getGraph());

    }
    /** test for copy function, bt compare equal graph and not equal graph  **/
    @Test
    void copy_test() {
        weighted_graph_algorithms algo_test = new WGraph_Algo();
        algo_test.init( create_graph1());
        weighted_graph graph_test2,graph_test1 = algo_test.copy();
        assertEquals(algo_test.getGraph(),graph_test1);
        graph_test1.removeNode(0);
        assertNotEquals(graph_test1,algo_test.getGraph());
        algo_test.init( create_graph2());
        graph_test2 = algo_test.copy();
        assertEquals(algo_test.getGraph(),graph_test2);
        graph_test2.connect(1,8,2);
        assertNotEquals(graph_test2,algo_test.getGraph());

    }
    /** test for copy function, by compare equal graph and not equal graph  **/
    @Test
    void isConnected_test() {
        weighted_graph_algorithms algo_test = new WGraph_Algo();
        algo_test.init( create_graph1());
        assertFalse(algo_test.isConnected());
        algo_test.getGraph().connect(0,1,3);
        assertFalse(algo_test.isConnected());
        for (int i=1 ; i<6;i++)
        {
            algo_test.getGraph().connect(i,i+1,3);
            assertFalse(algo_test.isConnected());
        }
        algo_test.getGraph().connect(6,7,3);
        assertTrue(algo_test.isConnected());
        algo_test.init( create_graph2());
        assertTrue(algo_test.isConnected());

    }
    /** test for shortest path function , checks the function by create a graph ,
     * and check the shortest path from different vertices in the graph  **/
    @Test
    void shortestPathDist_test() {
        weighted_graph_algorithms algo_test = new WGraph_Algo();
        algo_test.init( create_graph1());
        assertEquals(-1,algo_test.shortestPathDist(0,1));
        algo_test.getGraph().connect(0,1,3.4);
        assertEquals(3.4,algo_test.shortestPathDist(0,1));
        algo_test.init( create_graph2());
        assertEquals(0,algo_test.shortestPathDist(1,1));
        assertEquals(6,algo_test.shortestPathDist(1,2));
        assertEquals(2,algo_test.shortestPathDist(1,3));
        assertEquals(4,algo_test.shortestPathDist(1,4));
        assertEquals(5,algo_test.shortestPathDist(1,5));
        assertEquals(8,algo_test.shortestPathDist(1,6));
        assertEquals(6,algo_test.shortestPathDist(1,7));
        assertEquals(11,algo_test.shortestPathDist(1,8));

        assertEquals(11,algo_test.shortestPathDist(8,1));
        assertEquals(9,algo_test.shortestPathDist(8,2));
        assertEquals(9,algo_test.shortestPathDist(8,3));
        assertEquals(7,algo_test.shortestPathDist(8,4));
        assertEquals(6,algo_test.shortestPathDist(8,5));
        assertEquals(3,algo_test.shortestPathDist(8,6));
        assertEquals(5,algo_test.shortestPathDist(8,7));
        assertEquals(0,algo_test.shortestPathDist(8,8));

        assertEquals(4,algo_test.shortestPathDist(4,1));
        assertEquals(2,algo_test.shortestPathDist(4,2));
        assertEquals(2,algo_test.shortestPathDist(4,3));
        assertEquals(0,algo_test.shortestPathDist(4,4));
        assertEquals(1,algo_test.shortestPathDist(4,5));
        assertEquals(4,algo_test.shortestPathDist(4,6));
        assertEquals(2,algo_test.shortestPathDist(4,7));
        assertEquals(7,algo_test.shortestPathDist(4,8));

    }
    /** test for shortest path function that return a list of the vertices in the pah.
     * checks the function by create a graph ,and check if the list contain the vertices in the short path
     * by the wright order **/
    @Test
    void shortestPath_test() {
        int[] vertices_path_1_8 = {1,3,4,5,7,6,8};
        int[] vertices_path_8_1 = {8,6,7,5,4,3,1};
        int[] vertices_path_4_1 = {4,3,1};
        int count = 0;
        weighted_graph_algorithms algo_test = new WGraph_Algo();
        algo_test.init( create_graph1());
        List<node_info> list_temp = new LinkedList<node_info>();
        assertEquals(list_temp,algo_test.shortestPath(0,1));
        algo_test.init( create_graph2());
        List<node_info> vertices_list1= algo_test.shortestPath(1,8);
        for (node_info current_node: vertices_list1){
            assertEquals(vertices_path_1_8[count],current_node.getKey());
            count++;
        }
        List<node_info> vertices_list2= algo_test.shortestPath(8,1);
         count =0;
        for (node_info current_node: vertices_list2){
            assertEquals(vertices_path_8_1[count],current_node.getKey());
            count++;
        }
        List<node_info> vertices_list3= algo_test.shortestPath(4,1);
        count =0;
        for (node_info current_node: vertices_list3){
            assertEquals(vertices_path_4_1[count],current_node.getKey());
            count++;
        }

    }
    /** test for save and load function, the test create a graph add it to the graph algo and save it to a file
     * than load the graph and check if it equal to the original graph **/
    @Test
    void save_and_load_graph_test() {
        String file1="graph_file_0",file2="graph_file_1";
        weighted_graph graph_temp1 = create_graph2();
        weighted_graph_algorithms algo_test = new WGraph_Algo();
        algo_test.init(graph_temp1);
        algo_test.save(file1);
        algo_test.load(file1);
        assertEquals(graph_temp1,algo_test.getGraph());
        weighted_graph graph_temp2 = create_graph3();
        algo_test.init(graph_temp2);
        algo_test.save(file2);
        algo_test.load(file2);
        assertEquals(graph_temp2,algo_test.getGraph());
        assertNotEquals(graph_temp1,algo_test.getGraph());
        algo_test.load(file1);
        assertEquals(graph_temp1,algo_test.getGraph());
        assertNotEquals(graph_temp2,algo_test.getGraph());
    }

}
