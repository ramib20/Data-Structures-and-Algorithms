import java.util.NoSuchElementException;
/**
 * Your implementation of an ArrayQueue.
 *
 * @author Rami Bouhafs
 * @version 1.0
 * @ userid rbouhafs3
 * @ GTID 903591700
 *
 * Collaborators: none
 *
 * Resources: none
 * @param <T> is generic data type
 */
public class ArrayStack<T> {

    /*
     * The initial capacity of the ArrayStack.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack with a backing array with capacity
     * INITIAL_CAPACITY.
     */
    public ArrayStack() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the data to the top of the stack.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current length.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null and cannot be added.");
        } else {
            if (size == backingArray.length) {
                T[] temp = (T[]) new Object[size * 2]; //create temp array large enough to push new data
                for (int i = 0; i < size; i++) { //copy data from original array to temp array
                    if (backingArray[i] != null) {
                        temp[i] = backingArray[i];
                    }
                }
                backingArray = temp; //switches memory location so backingArray points to new array
            }
            backingArray[size] = data; //adds the data to top of stack
            size++; //increments size
        }
    }

    /**
     * Removes and returns the data from the top of the stack.
     *
     * Do not shrink the backing array.
     *
     * Replace any spots that you pop from with null.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The stack is already empty");
        }
        T topData = backingArray[size - 1]; //data on top is final index
        backingArray[size - 1] = null; //sets index to null
        size--; //decrements size
        return topData;
    }

    /**
     * Returns the data from the top of the stack without removing it.
     *
     * Must be O(1).
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty");
        } else {
            return backingArray[size - 1];
        }
    }

    /**
     * Returns the backing array of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the stack
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
