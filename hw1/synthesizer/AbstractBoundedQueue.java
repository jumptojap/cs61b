package synthesizer;

/**
 * ClassName: AbstractBoundedQueue
 * Package: synthesizer
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/15 - 14:41
 * Version: v1.0
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;

    public int capacity() {
        return capacity;
    }

    public int fillCount() {
        return fillCount;
    }
}
