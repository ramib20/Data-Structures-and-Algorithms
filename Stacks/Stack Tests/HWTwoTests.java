import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;

import java.util.stream.Stream;

import java.util.Stack;
import java.util.ArrayDeque;
import java.util.LinkedList;

/**
 * Simple random operations test for the Stack implementation for this week's homework.
 * I could not do a queue test because java does not have in built support for circular
 * queues in its JRE. I have left whatever code I started with out here so that you can 
 * build on it yourself incase you feel that you would know how to make the random operation
 * testing for the queue. If you look at my last 2 random operation tests, you will see that 
 * they follow the same structure.
 * THESE TESTS ARE IN NO WAY REPRESENTATIVE OF HOW CORRECT YOUR SUBMISSION IS.
 */
public class HWTwoTests {

    // Number of Random Operations to perform for the tests
    public static final int NUMBER_ROPS = 999999;

    // Length of the item (String) that will be placed in ADT
    public static final int ITEM_LENGTH = 15;

    // Should there be an equality check after each random operation?
    // If turned off, it will only compare once all NUMBER_ROPS operations
    // have been completed. I reccomend you start with set to false, and if you don't pass
    // all the tests, then make this true and see which ROP is giving the error
    public static final boolean CHECK_AFTER_EACH_ROP = true;

    // Dump all your data from the different structures so that you can see what went wrong
    public static final boolean DEBUG_PRINT = true;

    // DO NOT MODIFY
    // Will use this variable to add exception testing as well
    private static final int STACK_OPS = 7; 
    private static final int QUEUE_OPS = 7;
    /*
    @Test
    public void testRandomOpsQueue() {
        LinkedList<String> internalLink = new LinkedList<String>();
        ArrayQueue<String> answerArr = new ArrayQueue<String>();
        LinkedQueue<String> answerLink = new LinkedQueue<String>();

        for (int i = 0; i < 100; i++) {
            System.out.printf("%d, ", i);
            internalLink.add("");
            answerArr.enqueue("");
        }
    }

    static <T> boolean equals(LinkedList<T> internalImpl, ArrayQueue<T> answerArr, LinkedQueue<T> answerLink) {
        if (!(internalImpl.size() == arrayQueue.size() && internalImpl.size() == answerLink.size())) {
            System.out.printf("--->[EQUALITY CHECK] FAILED TO CHECK SIZE Actual: %8s Arr: %8s Linked:%8s%n", internalImpl.size(), answerArr.size(), answerLink.size());
            return false;
        }
        
        Iterator<T> internalIter = internalImpl.listIterator();
        T[] answerArrIter = answerArr.getBackingArr();
        LinkedNode<T> answerLinkIter = answerLink.getHead();
        
        int i = 0; // scope needed at method level
        for (; i < internalImpl.size() && internalIter.hasNext() && answerLink.getNext() != null; i++) {
            T internalData = internalIter.next();
            
        }

        for ()
    } 
*/
    /**
     * Stack class to keep track of the actual data/ size/ capacity
     * Since I cannot give a proper array based code implementation,
     * I have extended the java.util.Stack class and changed its default behaviour to 
     * share the test code without writing out any algorithms.
     */
    private class AnanaysStack<T> extends Stack<T> {
        public AnanaysStack () {
            super(); setSize(9); trimToSize(); ensureCapacity(9); setSize(0);
        }
    }

    @Test
    public void testRandomOpsStack() {
        AnanaysStack<String> internal = new AnanaysStack<String>();
        assertEquals(9, internal.capacity());

        System.out.printf("%n-------[Random Operation Testing]-------%n");
        ArrayStack<String> answerArr = new ArrayStack<String>();
        LinkedStack<String> answerLink = new LinkedStack<String>();

        boolean stop = false;
        
        ROPs_iterator: for (int i = 1, op = (int) (STACK_OPS * Math.random()) ; i <=  NUMBER_ROPS && !stop; i++, op = (int) (STACK_OPS * Math.random())) {
            String item = randomString();
            int index = (int) (Math.random() * internal.size());
            String internalRet = null, answerArrRet = null, answerLinkRet = null;
            // WHICH ITEMS FAILED AND WHICH DID NOT
            // [bin repr would be 1 for fail and 0 for pass. if all 3 fail, then it's 7
            // if internal is saved but arr and link fail, its 3 etc]
            int failType = 0; 
            switch (op) {
            case 0: 
                internal.push(item);
                answerArr.push(item);
                answerLink.push(item);
                break;
            case 1:
                if (internal.size() == 0) {
                    --i;
                    continue ROPs_iterator;
                }
                internalRet = internal.pop();
                answerArrRet = answerArr.pop();
                answerLinkRet = answerLink.pop();
                if (!internalRet.equals(answerArrRet) || !internalRet.equals(answerLinkRet)) {
                    System.out.printf("TEST FAILED [Op %8d, OPCODE %3s]: Stack Pop not equal, internal(%15s), array(%15s), linked(%15s)%n", i, op, internalRet, answerArrRet, answerLinkRet);
                    stop = true;
                }
                break;
            case 2:
                if (internal.size() == 0) {
                    continue ROPs_iterator;
                }
                internalRet = internal.peek();
                answerArrRet = answerArr.peek();
                answerLinkRet = answerLink.peek();
                if (!internalRet.equals(answerArrRet) || !internalRet.equals(answerLinkRet)) {
                    System.out.printf("TEST FAILED [Op %8d, OPCODE %3s]: Stack Peek not equal, internal(%15s), array(%15s), linked(%15s)%n", i, op, internalRet, answerArrRet, answerLinkRet);
                    stop = true;
                }
                break;
            case 4:
                if (!equals(internal, answerArr, answerLink)) { 
                    System.out.printf("TEST FAILED [Op %8d, OPCODE %3s]: Stack list representations not equal%n", i, op);
                    debugPrint(internal, answerArr, answerLink);
                    stop = true;
                }
                break;
            case 5:
                if (!(internal.size() == answerArr.size()) || !(internal.size() == answerLink.size())) {
                    System.out.printf("TEST FAILED [Op %8d, OPCODE %3s]: Stack sizes not equal, internal(%8d), array(%8d), linked(%8d)%n", i, op, internal.size(), answerArr.size(), answerLink.size());
                    stop = true;
                }
                break;
            case 6:
                int arrExcepted = -1;
                try {
                    answerArr.push(null);
                } catch (java.lang.IllegalArgumentException e) {
                    arrExcepted = 0;
                } catch (Exception e) {
                    arrExcepted = 1;
                } finally {
                    if (arrExcepted != 0) {
                        System.out.printf("TEST FAILED [Op %8d, OPCODE %3s]: Array Stack did not throw expected exception%n", i , op);
                        stop = true;
                    }
                }
                int linkExcepted = -1;
                try {
                    answerLink.push(null);
                } catch (java.lang.IllegalArgumentException e) {
                    linkExcepted = 0;
                } catch (Exception e) {
                    linkExcepted = 1;
                } finally {
                    if (linkExcepted != 0) {
                        System.out.printf("TEST FAILED [Op %8d, OPCODE %3s]: Linked Stack did not throw expected exception%n", i , op);
                        stop = true;
                    }
                }
                break;
            }
            if (CHECK_AFTER_EACH_ROP) {
                boolean eq = equals(internal, answerArr, answerLink);
                System.out.printf("Op %8d] OPCODE: %3s, STATUS: %8s%n", i, op, eq ? "FINE": "ERROR");
                if (!eq) {
                    if (DEBUG_PRINT) {
                        debugPrint(internal, answerArr, answerLink);
                    }
                    assertEquals(eq, true);
                }
            }
        }
        if (!CHECK_AFTER_EACH_ROP || stop) {
            boolean eq = equals(internal, answerArr, answerLink);
            System.out.printf("[FINAL] STATUS: %s%n", eq ? "FINE" : "ERROR");
            if (!eq) {
                if (DEBUG_PRINT) {
                    debugPrint(internal, answerArr, answerLink);
                }
                assertEquals(eq, true);
            }
        }
        System.out.printf("-------[PASSED %8d RANDOM OPERATIONS]--------", NUMBER_ROPS);
        assertEquals(true, true);
    }

    static String randomString() {
        int size = 1 + (int) (Math.random() * (ITEM_LENGTH - 1));
        String ret = "";
        for (int i = 0; i < size; i++) {
            ret += (char) (97 + 26 * Math.random());
        }
        return ret;
    }

    static <T> void debugPrint(Stack<T> internalImpl, ArrayStack<T> answerArr, LinkedStack<T> answerLink) {
        System.out.printf("|-------[DUMPING DEBUG DATA]-------|%n");
        java.util.Iterator internalIter = internalImpl.iterator();
        LinkedNode answerLinkIter = answerLink.getHead();
        T[] answerArrIter = answerArr.getBackingArray();

        int max_iterations = (int) Math.max(Math.max(internalImpl.size(), answerLink.size()), answerArrIter.length);

        System.out.printf("Backing Array Capacity: Actual(%8s) Arr(%8s)%n", internalImpl.capacity(), answerArr.getBackingArray().length);
        for (int i = 0; i < max_iterations; i++) {
            System.out.printf("%8s] %15s, %15s, %15s%n",
                    i,
                    internalIter.hasNext() ? internalIter.next() : "$END",
                    i < answerArrIter.length ? answerArrIter[i] != null ? answerArrIter[i] : "$END" : "",
                    answerLinkIter != null ? answerLinkIter.getData() : ""
                    );
            answerLinkIter = answerLinkIter == null ? null : answerLinkIter.getNext();
        }
        System.out.printf("|-------[END DUMP]-------|%n");
    }

    static <T> boolean equals(Stack<T> internalImpl, ArrayStack<T> answerArr, LinkedStack<T> answerLink) {
        if (internalImpl.size() != answerArr.size() && internalImpl.size() != answerLink.size()) {
            System.out.printf("--->[EQUALITY CHECK] FAILED TO CHECK SIZE Actual: %8s Arr: %8s Linked:%8s%n", internalImpl.size(), answerArr.size(), answerLink.size());
            return false;
        }
        if (internalImpl.capacity() != answerArr.getBackingArray().length) {
            System.out.printf("--->[EQUALITY CHECK] FAILED TO CHECK CAPACITY ON ARRAY STACK Actual: %8s Arr: %8s Linked: %8s%n", internalImpl.capacity(), answerArr.getBackingArray().length, "N/A");
            return false;
        }
        java.util.Iterator<T> internalIter = internalImpl.iterator();
        LinkedNode<T> answerLinkIter = answerLink.getHead();
        T[] answerArrIter = answerArr.getBackingArray();

        int i = 0;
        for (; i < answerArrIter.length && internalIter.hasNext() && answerLinkIter.getNext() != null; i++) {
            T internalData = internalIter.next();
            if (i > internalImpl.size() && internalImpl.size() == answerLink.size()) {
                if (!internalData.equals(answerArrIter[i])) {
                    System.out.printf("--->[EQUALITY CHECK] STACK NODES DO NOT MATCH%n");
                    return false;
                } else if (i > internalImpl.size() && internalImpl.size() != answerLink.size()) {
                    System.out.printf("You should not be here. Exiting...%n");
                    System.exit(0);
                }
                continue;
            }
            if (!internalData.equals(answerLinkIter.getData()) && !internalData.equals(answerArrIter[i])) {
                System.out.printf("--->[EQUALITY CHECK] STACK NODES DO NOT MATCH%n");
                return false;
            }
            answerLinkIter = answerLinkIter.getNext();
        }
        if (!(internalIter.hasNext() && answerLinkIter == null && i == answerArrIter.length)) return true;
        System.out.printf("--->[EQUALITY CHECK] ONE STACK TERMINATED BEFORE THE OTHER (same sizes registered)%n");
        return false;
    }
}