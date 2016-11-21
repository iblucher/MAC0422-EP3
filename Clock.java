/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  CLASSE CLOCK
 *
*******************************************************************************/

public class Clock<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private Node<Item> pointer;
    private int n;

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Clock() {
        first = null;
        last  = null;
        pointer = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void firstPoint() {
        pointer = first;
    }

    public void nextPoint() {
        pointer = pointer.next;
        if (pointer == null) pointer = first;
    }

    public Item pointedItem() {
        return pointer.item;
    }

    public void changeItem(Item item) {
        pointer.item = item;
    }

    public void insert(Item item) {
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
            firstPoint();
        }
        else           oldlast.next = last;
        n++;
    }
}