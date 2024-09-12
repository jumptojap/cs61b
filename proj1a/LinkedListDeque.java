import java.util.List;

/**
 * ClassName: LinkedListDeque
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/12 - 10:12
 * Version: v1.0
 */
public class LinkedListDeque<T> {
    private ListNode<T> first;
    private ListNode<T> last;
    private int size;

    public void addFirst(T front) {
        ListNode<T> node = new ListNode<>(first.next, first, front);
        first.getNext().setPrior(node);
        first.setNext(node);
        this.size++;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void addLast(T middle) {
        ListNode<T> node = new ListNode<>(last, last.prior, middle);
        last.getPrior().setNext(node);
        last.setPrior(node);
        this.size++;
    }

    public T removeFirst() {
        ListNode<T> node = this.first.getNext();
        this.first.setNext(node.next);
        node.next.setPrior(first);
        node.setPrior(null);
        node.setNext(null);
        this.size--;
        return node.getData();
    }
    public T removeLast() {
        ListNode<T> node = this.last.getPrior();
        this.last.setPrior(node.prior);
        node.prior.setNext(last);
        node.setPrior(null);
        node.setNext(null);
        this.size--;
        return node.getData();
    }

    public void printDeque() {
        ListNode temp = first.getNext();
        for(int i = 0; i < size; i++){
            System.out.print(temp.getData());
            temp = temp.getNext();
            if(i != size - 1){
                System.out.print(' ');
            }
        }
    }
    public T get(int index){
        ListNode<T> temp = first;
        for(int i = 0; i <= index; i++){
            temp = temp.getNext();
        }
        return temp.getData();
    }
    public T getRecursive(int index){
        return recur(index, first.getNext()).getData();
    }
    public ListNode<T> recur(int index, ListNode node){
        if(index == 0)
            return node;
        else
            return recur(index - 1, node.getNext());
    }


    public class ListNode<T>{
        ListNode<T> next;
        ListNode<T> prior;
        T data;

        public ListNode(ListNode<T> next, ListNode<T> prior, T data) {
            this.next = next;
            this.prior = prior;
            this.data = data;
        }

        public ListNode<T> getNext() {
            return next;
        }

        public void setNext(ListNode<T> next) {
            this.next = next;
        }

        public ListNode<T> getPrior() {
            return prior;
        }

        public void setPrior(ListNode<T> prior) {
            this.prior = prior;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
    public LinkedListDeque(){
        this.size = 0;
        this.first = new ListNode<>(null, null, null);
        this.last = new ListNode<>(null, null, null);
        this.first.setNext(this.last);
        this.last.setPrior(this.first);
    }
    public int size(){
        return this.size;
    }


}
