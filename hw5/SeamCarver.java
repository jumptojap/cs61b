import edu.princeton.cs.algs4.Picture;

/**
 * ClassName: SeamCarver
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/20 - 19:35
 * Version: v1.0
 */
public class SeamCarver {
    private Picture pic;
    int[][][] rgb;

    public SeamCarver(edu.princeton.cs.algs4.Picture picture) {
        this.pic = picture;
        rgb = new int[picture.height()][picture.width()][3];
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                rgb[i][j][0] = (pic.getRGB(j, i) >> 16) & 0xFF;
                rgb[i][j][1] = (pic.getRGB(j, i) >> 8) & 0xFF;
                rgb[i][j][2] = (pic.getRGB(j, i) >> 0) & 0xFF;
            }
        }
    }

    public Picture picture() {
        return pic;
    }
    public int width() {
        return pic.width();
    }
    public int height() {
        return pic.height();
    }
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= pic.width() || y >= pic.height()) {
            throw new IndexOutOfBoundsException();
        }
        double energyX = 0;
        double energyY = 0;
        for (int k = 0; k < 3; k++) {
            energyX = energyX + Math.pow(rgb[y][(x + 1) % width()][k] - rgb[y][(x - 1 + width()) % width()][k], 2);
        }
        for (int k = 0; k < 3; k++) {
            energyY = energyY + Math.pow(rgb[(y + 1) % height()][x][k] - rgb[(y - 1 + height()) % height()][x][k], 2);
        }
        return energyX + energyY;
    }
    private boolean check1(int i) {
        return i >= 0 && i < height();
    }

    public int[] findHorizontalSeam() {
        int[][] prev = new int[height()][width()];
        double[][] dp = new double[height()][width()];
        for (int i = 0; i < height(); i++) {
            dp[i][0] = energy(0, i);
        }
        for (int i = 1; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                double temp = dp[j][i - 1];
                prev[j][i] = j;
                if (check1(j - 1) && temp > dp[j - 1][i - 1]) {
                    temp = dp[j - 1][i - 1];
                    prev[j][i] = j - 1;
                }
                if (check1(j + 1) && temp > dp[j + 1][i - 1]) {
                    temp = dp[j + 1][i - 1];
                    prev[j][i] = j + 1;
                }
                dp[j][i] = temp + energy(i, j);
            }
        }
        double minNum = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < height(); i++) {
            if (minNum > dp[i][width() - 1]) {
                minNum = dp[i][width() - 1];
                index = i;
            }
        }
        int[] seam = new int[width()];
        seam[width() - 1] = index;
        for (int i = width() - 2; i >= 0; i--) {
            seam[i] = prev[seam[i + 1]][i + 1];
        }
        return seam;

    }
    private boolean check(int i) {
        return i >= 0 && i < pic.width();
    }

    public int[] findVerticalSeam() {
        int[][] prev = new int[pic.height()][pic.width()];
        double[][] dp = new double[pic.height()][pic.width()];
        for (int i = 0; i < width(); i++) {
            dp[0][i] = energy(i, 0);
        }
        for (int i = 1; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                double temp = dp[i - 1][j];
                prev[i][j] = j;
                if (check(j - 1) && temp > dp[i - 1][j - 1]) {
                    temp = dp[i - 1][j - 1];
                    prev[i][j] = j - 1;
                }
                if (check(j + 1) && temp > dp[i - 1][j + 1]) {
                    temp = dp[i - 1][j + 1];
                    prev[i][j] = j + 1;
                }
                dp[i][j] = temp + energy(j, i);
            }
        }
        double minNum = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < width(); i++) {
            if (minNum > dp[height() - 1][i]) {
                minNum = dp[height() - 1][i];
                index = i;
            }

        }
        int[] seam = new int[pic.height()];
        seam[pic.height() - 1] = index;
        for (int i = seam.length - 2; i >= 0; i--) {
            seam[i] = prev[i + 1][seam[i + 1]];
        }
        return seam;
    }             // sequence of indices for vertical seam

    private boolean checkSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                return false;
            }
        }
        return true;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width() || !checkSeam(seam)) {
            throw new IllegalArgumentException();
        }
        SeamRemover.removeHorizontalSeam(pic, seam);
    }
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height() || !checkSeam(seam)) {
            throw new IllegalArgumentException();
        }
        SeamRemover.removeVerticalSeam(pic, seam);
        // remove vertical seam from picture
    }
}
