package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;
import java.util.Objects;

public class Board implements WorldState {
    private int[][] tiles;

    public Board(int[][] items) {
        this.tiles = new int[items.length][items[0].length];
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                this.tiles[i][j] = items[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i >= tiles.length || j < 0 || j >= tiles.length) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    public int size() {
        return tiles.length;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int res = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                if (tiles[i][j] != i * tiles.length + j + 1) {
                    res++;
                }
            }
        }
        return res;
    }

    public int manhattan() {
        int res = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                int val = tileAt(i, j);

                int row;
                int col;
                if (val == 0) {
                    continue;
                } else {
                    col = (val - 1) % tiles.length;
                    row = (val - 1) / tiles.length;
                }
                res = res + Math.abs(row - i) + Math.abs(col - j);
            }
        }
        return res;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board = (Board) o;
        return Objects.deepEquals(tiles, board.tiles);
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }
}
