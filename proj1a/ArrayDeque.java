/**
 * ClassName: ArrayDeque
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/12 - 15:28
 * Version: v1.0
 */
public class ArrayDeque<T> {
    private T[] arr;
    private int size;
    private int first;
    private int last;
    private int len;

    public ArrayDeque() {
        arr = (T[]) new Object[8];
        size = 0;
        first = 0;
        last = 0;
        len = 8;
    }

    private boolean check(int len, int size) {
        if (size > len || size < 0) {
            return false;
        }
        if (len < 16 || size * 4 >= len) {
            return true;
        } else {
            return false;
        }
    }

    private void copyArr() {
        T[] temp = (T[]) new Object[len];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }

    public void addFirst(T item) {
        int newSize = size + 1;
        if (!check(len, newSize)) {
            len = newSize * 4;
            copyArr();
        }
        first = (first - 1 + len) % len;
        arr[first] = item;
        size = newSize;
    }

    public void addLast(T item) {
        int newSize = size + 1;
        if (!check(len, newSize)) {
            len = newSize * 4;
            copyArr();
        }
        last = (last + 1) % len;
        arr[last] = item;
        size = newSize;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T removeFirst() {
        int newSize = size - 1;
        if (!check(len, newSize)) {
            if (size < 0) {
                return null;
            } else {
                len = newSize * 4;
                copyArr();
            }
        }
        T res = arr[first];
        first = (first + 1) % len;
        size = newSize;
        return res;
    }

    public T removeLast() {
        int newSize = size - 1;
        if (!check(len, newSize)) {
            if (size < 0) {
                return null;
            } else {
                len = newSize * 4;
                copyArr();
            }
        }
        T res = arr[last];
        last = (last - 1 + len) % len;
        size = newSize;
        return res;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = first; i == last; i = (i + 1) % len) {
            System.out.print(arr[i]);
            System.out.println(' ');
        }
        System.out.print(arr[last]);
    }

    public T get(int index) {
        return arr[(first + index) % index];
    }
}
