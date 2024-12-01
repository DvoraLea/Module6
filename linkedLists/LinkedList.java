package linkedLists;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> first; // Reference to the first node in the list (the head)

    // Constructor: Initializes an empty list
    public LinkedList() {
        first = null;
    }

    // Method to check if the list is empty
    public boolean isEmpty() {
        return first == null;
    }

    // Method to add a node at the beginning of the list
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(first);
        first = newNode;
    }

    // Method to add a node at the end of the list
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            first = newNode;
        } else {
            Node<T> current = first;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }
    // Method to remove the first node from the list
    public void removeFirst() {
        if (first == null) {
            throw new UnderFlowException();
        }
        first = first.getNext();
    }

    // Method to remove the last node from the list
    public void removeLast() {
        if (first == null) {
            throw new UnderFlowException();
        }
        if (first.getNext() == null) {
            first = null;
        } else {
            Node<T> current = first;
            while (current.getNext().getNext() != null) {
                current = current.getNext();
            }
            current.setNext(null);
        }
    }

    // Method to get the data of the first node
    public T getFirst() {
        if (first == null) {
            throw new UnderFlowException();
        }
        return first.getInfo();
    }

    // Method to get the data of the last node
    public T getLast() {
        if (first == null) {
            throw new UnderFlowException();
        }
        Node<T> current = first;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current.getInfo();
    }

    // Method to remove a specific element
    public boolean remove(T data) {
        if (first == null) {
            throw new UnderFlowException();
        }
        if (first.getInfo().equals(data)) {
            removeFirst();
            return true;
        }
        Node<T> current = first;
        Node<T> prev = null;
        while (current != null) {
            if (current.getInfo().equals(data)) {
                if (prev != null) {
                    prev.setNext(current.getNext());
                }
                return true;
            }
            prev = current;
            current = current.getNext();
        }
        return false;
    }

    // Method to get the size of the list
    public int size() {
        int count = 0;
        Node<T> current = first;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    // Method to insert an element at a specific position
    public void insertAt(int position, T data) {
        if (position < 0 || position > size()) {
            throw new IndexOutOfBoundsException();
        }
        if (position == 0) {
            addFirst(data);
        } else {
            Node<T> newNode = new Node<>(data);
            Node<T> current = first;
            Node<T> prev = null;
            for (int i = 0; i < position; i++) {
                prev = current;
                current = current.getNext();
            }
            prev.setNext(newNode);
            newNode.setNext(current);
        }
    }

    // Forward iterator 
    @Override
    public Iterator<T> iterator() {
        return new ForwardIterator();
    }

    private class ForwardIterator implements Iterator<T> {
        private Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.getInfo();
            current = current.getNext();
            return data;
        }
    }

    // Reverse iterator using a stack
    public class LLReverseIterator implements Iterator<T> {
        private Stack<T> stack = new Stack<>();

        public LLReverseIterator() {
            Node<T> current = first;
            while (current != null) {
                stack.push(current.getInfo());
                current = current.getNext();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return stack.pop();
        }
    }

    // Method to return the reverse iterator
    public LLReverseIterator reverseIterator() {
        return new LLReverseIterator();
    }

    // String representation of the list
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = first;
        while (current != null) {
            sb.append(current.getInfo());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}
