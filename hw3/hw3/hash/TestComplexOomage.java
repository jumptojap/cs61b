package hw3.hash;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    //      Create a list of Complex Oomages called deadlyList
    //     * that shows the flaw in the hashCode function.
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        deadlyList.add(new ComplexOomage(Arrays.asList(1)));
        deadlyList.add(new ComplexOomage(Arrays.asList(2)));
        deadlyList.add(new ComplexOomage(Arrays.asList(3)));
        deadlyList.add(new ComplexOomage(Arrays.asList(4)));
        deadlyList.add(new ComplexOomage(Arrays.asList(5)));
        deadlyList.add(new ComplexOomage(Arrays.asList(6)));
        deadlyList.add(new ComplexOomage(Arrays.asList(7)));
        deadlyList.add(new ComplexOomage(Arrays.asList(8)));
        deadlyList.add(new ComplexOomage(Arrays.asList(9)));
        deadlyList.add(new ComplexOomage(Arrays.asList(0)));

        // Your code here.
        for (int i = 0; i < 10; i++) {
            List<Integer> ls = new ArrayList<>();
            ls.add(new Random().nextInt(10));
            for (int j = 0; j < 6; j++) {
                ls.add(0);
            }
            deadlyList.add(new ComplexOomage(ls));
        }


        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /**
     * Calls tests for SimpleOomage.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
