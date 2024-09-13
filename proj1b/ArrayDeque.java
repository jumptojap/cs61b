/**
 * ClassName: ArrayDeque
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/12 - 15:28
 * Version: v1.0
 */
public class ArrayDeque<T> implements Deque<T> {
    private T[] arr;
    private int size;
    private int capacity;
    private int first;
    private int last;

    public ArrayDeque() {
        arr = (T[]) new Object[8];
        size = 0;
        capacity = 8;
        first = 0;
        last = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int st = (first + 1) % capacity;
        for (int i = 0; i < size; i++) {
            System.out.print(arr[st]);
            st = (st + 1) % capacity;
            if (i != size - 1) {
                System.out.print(' ');
            }
        }
    }

    public T get(int index) {
        int st = (first + 1) % capacity;
        for (int i = 0; i < index; i++) {
            st = (st + 1) % capacity;
        }
        return arr[st];
    }

    private void resize(int nsize) {
        int ccapacity = capacity;
        int st = (first + 1) % ccapacity;
        capacity = nsize * 2;
        T[] temp = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = arr[st];
            st = (st + 1) % ccapacity;
        }
        first = capacity - 1;
        last = size;
        arr = temp;
    }

    public void addFirst(T item) {
        int nsize = size + 1;
        if (nsize > capacity) {
            resize(nsize);
        }
        arr[first] = item;
        first = (first - 1 + capacity) % capacity;
        if (nsize == 1) {
            last = (last + 1) % capacity;
        }
        size = nsize;
    }

    public void addLast(T item) {
        int nsize = size + 1;
        if (nsize > capacity) {
            resize(nsize);
        }
        arr[last] = item;
        last = (last + 1) % capacity;
        if (nsize == 1) {
            first = (first - 1 + capacity) % capacity;
        }
        size = nsize;
    }

    public T removeFirst() {
        int nsize = size - 1;
        if (nsize < 0) {
            return null;
        }
        if (capacity >= 16 && 4 * nsize < capacity) {
            resize(nsize);
        }
        first = (first + 1) % capacity;
        if (nsize == 0) {
            last = first;
        }
        size = nsize;
        return arr[first];
    }

    public T removeLast() {
        int nsize = size - 1;
        if (nsize < 0) {
            return null;
        }
        if (capacity >= 16 && 4 * nsize < capacity) {
            resize(nsize);
        }
        last = (last - 1 + capacity) % capacity;
        if (nsize == 0) {
            first = last;
        }
        size = nsize;
        return arr[last];
    }

}
