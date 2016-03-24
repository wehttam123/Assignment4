package cpsc457;

import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.*;
import org.hamcrest.CoreMatchers.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.*;

@RunWith(JUnit4.class)
public class RandomTests {


    // This test provides the raw materials for profiling your merge sort
    @Test
    public void test_a_big_random_list() throws Exception {
		Random r = new Random();
        LinkedList<Integer> list = new LinkedList<Integer>();


        list.append(3);
        list.append(2);
        list.append(1);
        list.append(21);
        //list.append(2); // If you put more then one elements in it freezes the program

        long start = System.currentTimeMillis();

       /*for(int i=0; i<2e5; i++) {
            list.append(r.nextInt());
        }*/


		//Iterator itr = list.iterator();
		System.err.println("===Starting List===");
		System.err.println(list.get(0));
		for(Integer num : list) {
			System.err.println(num);
		}

		//LinkedList.sort(list);
      LinkedList.par_sort(list);
      long end = System.currentTimeMillis();

        System.err.println("===Sorted List===");
		System.err.println(list.get(0));
		for(Integer num : list) {
			System.err.println(num);
		}

        System.err.println();
        System.err.println("Processors: "+Runtime.getRuntime().availableProcessors());
        System.err.println(end - start + " ms");
        System.err.println();

		int i = 0;
        Integer prev = Integer.MIN_VALUE;

	// fyi: this style of for loop use the result of getIterator()
	//   hence the initial NullPointerException
        for(Integer num : list) {
			assertTrue(num + " found before " + prev + " at index " + i, num >= prev);
            prev = num;
			i++;
    }

}}
    // these tests are primarilly (but not exclusively)
    // how we will evaluate the correctness of your code

/*
    @Test
    public void sort_lots_of_small_random_lists_in_serial() {
        Random r = new Random();
        for(int k=0; k<1e5; k++) {
            int count = 15 + r.nextInt(3);
            int [] input = new int [count];
            LinkedList<Integer> list = new LinkedList<Integer>();

            for (int i = 0; i < count; i++) {
                int rval = r.nextInt(count);
                input[i] = rval;
                list.append(rval);
            }

            LinkedList.sort(list);

            Integer prev = Integer.MIN_VALUE;
            for (int i=0; i<list.size(); i++) {
                if(list.get(i) < prev) {
                    dumpTest(input);
                    assertTrue(list.get(i) + " found before " + prev + " at index " + i, list.get(i) >= prev);
                }

                prev = list.get(i);

            }
        }
    }

    @Test
    public void testEmptyInput() {
        testFailedInput(new int[]{});
    }

    private void testFailedInput(int [] input){
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < input.length; i++) {
            list.append(input[i]);
        }

        LinkedList.sort(list);
        Integer prev = Integer.MIN_VALUE;
        for (int i = 0; i<list.size(); i++) {
            assertTrue(list.get(i) + " found before " + prev + " at index " + i, list.get(i) >= prev);
            prev = list.get(i);
            i++;
        }
    }
  }

    private void dumpTest(int [] input) {
        StringBuilder buf = new StringBuilder();
        buf.append("=================================================================================================\n");
        buf.append("Test failed with randomly generated input: {");
        for(int i=0; i<input.length; i++) {
            buf.append(" ").append(input[i]);
        }
        buf.append(" }\n");
        buf.append("=================================================================================================\n");
        buf.append(" We recommend that you add the following code to your test file: \n");
        buf.append("=================================================================================================\n");

        buf.append("\n\t@Test\n\tpublic void test");
        for(int i=0; i<input.length; i++) {
            buf.append("_").append(input[i]);
        }
        buf.append("(){\n\t\ttestFailedInput(new int [] {");
        for(int i=0; i<input.length; i++) {
            if(i>0)buf.append(",");
            buf.append(input[i]);
        }
        buf.append("});\n\t}\n\n");
        buf.append("=================================================================================================\n");

        System.out.println(buf.toString());
    }


}
*/
