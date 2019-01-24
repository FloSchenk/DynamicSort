import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestSortAlgorithm {

    DynamicSort ds = new DynamicSort();
    ArrayList<Integer> sorted,testData = new ArrayList<>();
    @Before
    public void init() {

        ds = new DynamicSort();

        testData.add(2);
        testData.add(3);
        testData.add(5);
        testData.add(3);
        testData.add(2);
        testData.add(7);
        testData.add(2);
        testData.add(9);
        testData.add(12);
        testData.add(13);
        testData.add(2);
        testData.add(6);
        testData.add(88);
        testData.add(5);

        ds.createSortPortInstance();
        sorted = ds.execute(testData);
    }

    @Test
    public void testNotNull() {
        assertNotNull(sorted);
    }

    @Test
    public void testLength() {
        assertEquals(14, sorted.size());
    }

    //this is the only Test needed for Elements, checks for Duplicates, and all if all genes from Parents are existing
    @Test
    public void checkIfSorted() {

        int temp = sorted.get(0);
        boolean isSorted = true;
        for (int i: sorted) {
            System.out.println(i);
            if (i < temp){
                isSorted = false;
                break;
            }
            temp = i;
        }
        assertTrue(isSorted);
    }
}