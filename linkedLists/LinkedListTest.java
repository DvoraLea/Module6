package linkedLists;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

class LinkedListTest {
    private LinkedList<Integer> theList;

    @BeforeEach
    void setUp() {
        theList = new LinkedList<>();
    }

    @Test
    void testInitialState() {
        assertTrue(theList.isEmpty());
        assertEquals(0, theList.size());
    }

    // Tests for adding elements
    @Test
    void testAddFirst() {
        theList.addFirst(100);
        assertEquals(100, theList.getFirst());
        assertEquals(1, theList.size());
        theList.addFirst(200);
        assertEquals(200, theList.getFirst());
        assertEquals(2, theList.size());
        assertEquals("[200, 100]", theList.toString());
    }

    @Test
    void testAddLast() {
        theList.addLast(300);
        assertEquals(300, theList.getLast());
        assertEquals(1, theList.size());
        theList.addLast(400);
        assertEquals(400, theList.getLast());
        assertEquals(2, theList.size());
        assertEquals("[300, 400]", theList.toString());
    }

    // Tests for removing elements
    @Test
    void testRemoveFirst() {
        theList.addFirst(100);
        theList.addFirst(200);
        theList.removeFirst();
        assertEquals(100, theList.getFirst());
        assertEquals(1, theList.size());
        theList.removeFirst();
        assertTrue(theList.isEmpty());
        assertThrows(UnderFlowException.class, () -> theList.removeFirst());
    }

    @Test
    void testRemoveLast() {
        theList.addLast(100);
        theList.addLast(200);
        theList.removeLast();
        assertEquals(100, theList.getLast());
        assertEquals(1, theList.size());
        theList.removeLast();
        assertTrue(theList.isEmpty());
        assertThrows(UnderFlowException.class, () -> theList.removeLast());
    }

    // Tests for insert at specific position
    @Test
    void testInsertAtValidPosition() {
        theList.insertAt(0, 50);
        assertEquals(50, theList.getFirst());
        theList.insertAt(1, 100);
        assertEquals(100, theList.getLast());
        theList.insertAt(1, 75);
        assertEquals("[50, 75, 100]", theList.toString());
    }

    @Test
    void testInsertAtInvalidPosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> theList.insertAt(-1, 50));
        assertThrows(IndexOutOfBoundsException.class, () -> theList.insertAt(1, 100));
    }

    // Tests for edge cases with empty list
    @Test
    void testEmptyListOperations() {
        assertThrows(UnderFlowException.class, () -> theList.getFirst());
        assertThrows(UnderFlowException.class, () -> theList.getLast());
        assertThrows(UnderFlowException.class, () -> theList.removeFirst());
        assertThrows(UnderFlowException.class, () -> theList.removeLast());
    }

    // Test for size
    @Test
    void testSize() {
        assertEquals(0, theList.size());
        theList.addFirst(100);
        assertEquals(1, theList.size());
        theList.addLast(200);
        assertEquals(2, theList.size());
        theList.removeFirst();
        assertEquals(1, theList.size());
        theList.removeLast();
        assertEquals(0, theList.size());
    }

    // Test for forward iterator
    @Test
    void testForwardIterator() {
        theList.addLast(1);
        theList.addLast(2);
        theList.addLast(3);

        Iterator<Integer> it = theList.iterator();
        assertTrue(it.hasNext());
        assertEquals(1, it.next());
        assertEquals(2, it.next());
        assertEquals(3, it.next());
        assertFalse(it.hasNext());
    }

    // Test for reverse iterator
    @Test
    void testReverseIterator() {
        theList.addLast(1);
        theList.addLast(2);
        theList.addLast(3);

        LinkedList<Integer>.LLReverseIterator reverseIt = theList.reverseIterator();
        assertTrue(reverseIt.hasNext());
        assertEquals(3, reverseIt.next());
        assertEquals(2, reverseIt.next());
        assertEquals(1, reverseIt.next());
        assertFalse(reverseIt.hasNext());
    }

    // Test for toString
    @Test
    void testToString() {
        assertEquals("[]", theList.toString());
        theList.addFirst(100);
        assertEquals("[100]", theList.toString());
        theList.addLast(200);
        assertEquals("[100, 200]", theList.toString());
    }
}
