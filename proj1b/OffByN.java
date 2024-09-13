/**
 * ClassName: OffByN
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/13 - 14:17
 * Version: v1.0
 */
public class OffByN implements CharacterComparator {
    private int N;

    public OffByN(int n) {
        N = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return x - y == N || x - y == -N;
    }
}
