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

    private boolean check(int l, int s) {
        if (s > l || s < 0) {
            return false;
        } else {
            if (l < 16) {
                return true;
            } else {
                return s * 4 >= l;
            }

        }
    }

    private void copyArr() {
        T[] temp = (T[]) new Object[len];
        int t = first;
        first = first % len;
        last = (t + size - 1) % len;
        for (int i = 0; i < size; i++) {
            temp[(t + i) % len] = arr[(i + t) % arr.length];
        }
        arr = temp;
    }

    public void addFirst(T item) {
        if (size == 0) {
            arr[first] = item;
            size = 1;
            return;
        }
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
        if (size == 0) {
            arr[last] = item;
            size = 1;
            return;
        }
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
        if (size == 1) {
            size = 0;
            return arr[first];
        }
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
        if (size == 1) {
            size = 0;
            return arr[first];
        }
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
        for (int i = first; i != last; i = (i + 1) % len) {
            System.out.print(arr[i]);
            System.out.println(' ');
        }
        System.out.print(arr[last]);
    }

    public T get(int index) {
        if (index < 0 || index >= len) {
            return null;
        }
        if (first > last && index > last && index < first) {
            return null;
        }
        if (first <= last && index < first || index > last) {
            return null;
        }
        return arr[(first + index) % len];
    }
}
