package synthesizer;

import java.util.Iterator;

/**
 * ClassName: BoundedQueue
 * Package: synthesizer
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/15 - 14:36
 * Version: v1.0
 */
public interface BoundedQueue<T> extends Iterable<T> {
    int capacity();     // return size of the buffer

    int fillCount();    // return number of items currently in the buffer

    void enqueue(T x);  // add item x to the end

    T dequeue();        // delete and return item from the front

    T peek();           // return (but do not delete) item from the front

    default boolean isEmpty() {
        return fillCount() == 0;
    }

    default boolean isFull() {
        return capacity() == fillCount();
    }

    @Override
    Iterator<T> iterator();
}
