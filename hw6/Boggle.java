import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class Boggle {

    // File path of dictionary file
    static String dictPath = "trivial_words.txt";

    /**
     * Solves a Boggle puzzle.
     *
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     * The Strings are sorted in descending order of length.
     * If multiple words have the same length,
     * have them in ascending alphabetical order.
     */
    private static Set<String> dict;
    private static char[][] board;
    private static PriorityQueue<String> pq;
    private static Tries root;

    public static List<String> solve(int k, String boardFilePath) {
        // YOUR CODE HERE
        if (k <= 0) {
            throw new IllegalArgumentException();
        }
        dict = getStrList(dictPath);
        board = getBoard(boardFilePath);
        root = buildTries(dict);
        pq = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() != o2.length()) {
                    return o2.length() - o1.length();
                } else {
                    return o1.compareTo(o2);
                }
            }
        });
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                StringBuilder sb = new StringBuilder();
                boolean[][] flag = new boolean[board.length][board[i].length];
                dfs(i, j, sb, flag);
            }
        }
        List<String> ans = new ArrayList<>();
        while (!pq.isEmpty() && k > 0) {
            String word = pq.poll();
            ans.add(word);
            k--;
        }
        return ans;
    }

    private static boolean check(int i, int j) {
        return i >= 0 && i < board.length && j >= 0 && j < board[i].length;
    }

    private static int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
    private static int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};

    private static boolean dfs(int i, int j, StringBuilder str, boolean[][] flag) {
        if (!check(i, j) || flag[i][j]) {
            return false;
        }
        str.append(board[i][j]);
        String word = str.toString();
        if (!root.startsWith(word)) {
            str.deleteCharAt(str.length() - 1);
            return false;
        } else {
            if (word.length() >= 3 && root.search(word) && !pq.contains(word)) {
                pq.add(word);
            }
            int x;
            int y;
            boolean found = false;
            flag[i][j] = true;
            for (int k = 0; k < 8; k++) {
                x = i + dx[k];
                y = j + dy[k];
                found = found || dfs(x, y, str, flag);
            }
            flag[i][j] = false;
            str.deleteCharAt(str.length() - 1);
            return found;
        }

    }

    public static Set<String> getStrList(String path) {
        In in = new In(path);
        Set<String> set = new HashSet<>();
        while (!in.isEmpty()) {
            String word = in.readLine();
            set.add(word);
        }
        return set;

    }

    public static char[][] getBoard(String boardFilePath) {
        In in = new In(boardFilePath);
        List<String> list = new ArrayList<>();
        while (!in.isEmpty()) {
            String word = in.readLine();
            list.add(word);
        }
        char[][] boardd = new char[list.size()][list.get(0).length()];
        for (int i = 0; i < boardd.length; i++) {
            if (list.get(i).length() != boardd[i].length) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < boardd[i].length; j++) {
                boardd[i][j] = list.get(i).charAt(j);
            }
        }
        return boardd;
    }

    public static Tries buildTries(Set<String> list) {
        Tries res = new Tries();
        for (String word : list) {
            res.insert(word);
        }
        return res;
    }
}
