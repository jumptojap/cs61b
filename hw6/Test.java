import java.util.List;
import java.util.Set;

/**
 * ClassName: Test
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/21 - 17:23
 * Version: v1.0
 */
public class Test {
    @org.junit.Test
    public void test1() {
        Boggle.getStrList("./words.txt");

    }

    @org.junit.Test
    public void test2() {
        Set<String> strList = Boggle.getStrList("./wssords.txt");
        Tries tries = new Tries();
        for (String s : strList) {
            tries.insert(s);
        }
        tries.insert("");
        System.out.println(tries.search(""));

    }

    @org.junit.Test
    public void test3() {
        char[][] board = Boggle.getBoard("./exampleBoard.txt");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    @org.junit.Test
    public void test4() {
        List<String> solve = Boggle.solve(7, "./exampleBoard2.txt");
        for (String s : solve) {
            System.out.println(s);
        }
    }
}
