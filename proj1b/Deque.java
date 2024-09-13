/**
 * ClassName: Deque
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/13 - 13:21
 * Version: v1.0
 */
public interface Deque<T> {
    public void addFirst(T item);

    public void addLast(T item);

    public boolean isEmpty();

    public int size();

    public void printDeque();

    public T removeFirst();

    public T removeLast();

    public T get(int index);
}
