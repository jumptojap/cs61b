/**
 * ClassName: OffByOne
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/13 - 13:51
 * Version: v1.0
 */
public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        int dif = x - y;
        return dif == -1 || dif == 1 || dif == 0;
    }
}
