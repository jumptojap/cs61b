/**
 * ClassName: TestArrayDequeGold
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/13 - 21:06
 * Version: v1.0
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void test1() {
        String mes = "";
        StudentArrayDeque<Integer> qTest = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> qCorrect = new ArrayDequeSolution<>();
        while (true) {
            int num = StdRandom.uniform(4);
            Integer item = StdRandom.uniform(10000);
            if (num == 0) {
                qTest.addFirst(item);
                qCorrect.addFirst(item);
                mes = mes + "addFirst(" + item + ")\n";
            } else if (num == 1) {
                qTest.addLast(item);
                qCorrect.addLast(item);
                mes = mes + "addLast(" + item + ")\n";
            } else if (num == 2) {
                mes = mes + "removeFirst()";
                assertEquals(mes, qTest.removeFirst(), qCorrect.removeFirst());
                mes = mes + "\n";
            } else {
                mes = mes + "removeLast()";
                assertEquals(mes, qTest.removeLast(), qCorrect.removeLast());
                mes = mes + "\n";
            }
        }
    }
}
