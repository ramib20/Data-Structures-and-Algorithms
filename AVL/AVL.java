import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Rami Bouhafs
 * @version 1.0
 * @userid rbouhafs3
 * @GTID 903591700
 *
 * Collaborators: Tutoring on AVLs by an upperclassman friend not in the class, Zach Minoh
 *
 * Resources: None
 * @param <T> generics parameter
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Either the given data or at least one element in the data is null");
        } else {
            for (T each : data) {
                add(each);
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is null");
        } else {
            root = rcrsvAdd(data, root);
        }
    }

    /**
     * private recursive helper method for above add method
     *
     * @param data given data
     * @param curr current node
     * @return return
     */
    private AVLNode<T> rcrsvAdd(T data, AVLNode<T> curr) {
        if (curr == null) {
            size++;
            return helperUpdate(new AVLNode<>(data));
        }

        if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rcrsvAdd(data, curr.getLeft()));
        }

        if (curr.getData().compareTo(data) < 0) {
            curr.setRight(rcrsvAdd(data, curr.getRight()));
        }

        return helperUpdate(curr);
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the findSuccessor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the findSuccessor node. Do NOT use the provided public
     * predecessor method to remove a 2-child node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is null");
        }

        AVLNode<T> dummy = new AVLNode<>(null);
        root = rcsrvRemove(data, root, dummy);
        return dummy.getData();
    }

    /**
     * private recursive helper method for remove method
     *
     * @param data data
     * @param curr curr
     * @param dummy A dummy node so that we can keep data
     * @return return
     */
    private AVLNode<T> rcsrvRemove(T data, AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data was not found and thus cannot be removed");
        }

        if (curr.getData().compareTo(data) > 0) { //if data < curr
            curr.setLeft(rcsrvRemove(data, curr.getLeft(), dummy));
            return helperUpdate(curr);
        }

        if (curr.getData().compareTo(data) < 0) { // if data > curr
            curr.setRight(rcsrvRemove(data, curr.getRight(), dummy));
            return helperUpdate(curr);
        }

        dummy.setData(curr.getData());

        if (curr.getLeft() == null && curr.getRight() == null) { //if removed node is a leaf node
            size--;
            return null;
        }
        if (curr.getRight() == null) { //if removed node only has a left child
            size--;
            return helperUpdate(curr.getLeft());
        }
        if (curr.getLeft() == null) { //if removed node only has a right child
            size--;
            return helperUpdate(curr.getRight());
        }
        AVLNode<T> aqui = new AVLNode<>(null); // if removed node has two children
        curr.setRight(findSuccessor(curr.getRight(), aqui));
        curr.setData(aqui.getData());
        return helperUpdate(curr);
    }

    /**
     * private recursive helper method for remove method to find successor
     *
     * @param curr curr
     * @param aqui dummy node to save data
     * @return return
     */
    private AVLNode<T> findSuccessor(AVLNode<T> curr, AVLNode<T> aqui) {
        if (curr.getLeft() == null) {
            size--;
            aqui.setData(curr.getData());
            return helperUpdate(curr.getRight());
        }

        curr.setLeft(findSuccessor(curr.getLeft(), aqui));
        return helperUpdate(curr);
    }

    /**
     * private helper function to update height and balance of root
     *
     * @param curr node we're updating
     * @return return
     */
    private AVLNode<T> helperUpdate(AVLNode<T> curr) {
        if (curr == null) {
            return null;
        }
        curr.setHeight(Math.max(getHeight(curr.getLeft()), getHeight(curr.getRight())) + 1);
        curr.setBalanceFactor(getHeight(curr.getLeft()) - getHeight(curr.getRight()));
        if (curr.getBalanceFactor() >= 2) { //checks for right rotation
            if (curr.getBalanceFactor() * curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(rotLeft(curr.getLeft()));
                return rotRight(curr);
            }
            return rotRight(curr);
        }

        if (curr.getBalanceFactor() <= -2) { //checks for left rotation
            if (curr.getBalanceFactor() * curr.getRight().getBalanceFactor() < 0) {
                curr.setRight(rotRight(curr.getRight()));
                return rotLeft(curr);
            }
            return rotLeft(curr);
        }

        return curr; // if no rotation is needed
    }

    private void helperNoRot(AVLNode<T> curr) {
        curr.setHeight(Math.max(getHeight(curr.getLeft()), getHeight(curr.getRight())) + 1);

        curr.setBalanceFactor(getHeight(curr.getLeft()) - getHeight(curr.getRight()));
    }


    /**
     * rotates given node to the left
     *
     * @param curr node we're rotating
     * @return return
     */
    private AVLNode<T> rotLeft(AVLNode<T> curr) {
        AVLNode<T> ada = curr.getRight();
        curr.setRight(ada.getLeft());
        ada.setLeft(curr);
        helperNoRot(curr);
        helperNoRot(ada);
        return ada;
    }

    /**
     * rotates given node to the right
     *
     * @param curr node we're rotating
     * @return return
     */
    private AVLNode<T> rotRight(AVLNode<T> curr) {
        AVLNode<T> ada = curr.getLeft();
        curr.setLeft(ada.getRight());
        ada.setRight(curr);
        helperNoRot(curr);
        helperNoRot(ada);
        return ada;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The given data was null");
        } else {
            return rcsrvGet(data, root);
        }
    }

    /**
     * private recursive helper method for get method
     *
     * @param data data
     * @param curr curr
     * @return return
     */
    private T rcsrvGet(T data, AVLNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("The data could not be found in the tree");
        } else {
            if (curr.getData().compareTo(data) > 0) {
                return rcsrvGet(data, curr.getLeft());
            }

            if (curr.getData().compareTo(data) < 0) {
                return rcsrvGet(data, curr.getRight());
            }

            return ((T) curr.getData());
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The given data was null");
        } else {
            return rcsrvContains(data, root);
        }
    }

    /**
     * private helper recursive helper for contains method
     *
     * @param data data
     * @param curr curr
     * @return return
     */
    private boolean rcsrvContains(T data, AVLNode<T> curr) {
        if (curr == null) {
            return false;
        } else {
            if (curr.getData().compareTo(data) > 0) {
                return rcsrvContains(data, curr.getLeft());
            }

            if (curr.getData().compareTo(data) < 0) {
                return rcsrvContains(data, curr.getRight());
            }

            return true;
        }
    }

    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return getHeight(root);
    }


    /**
     * private helper method to get height
     *
     * @param node node to get height from
     * @return if null, -1, otherwise, the node's height
     */
    private int getHeight(AVLNode<T> node) {
        if (node == null) { //if tree is empty
            return -1;
        } else {
            return node.getHeight();
        }
    }


    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * In your BST homework, you worked with the concept of the predecessor, the
     * largest data that is smaller than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 3 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the deepest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     * 3: If the data passed in is the minimum data in the tree, return null.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The given data was null");
        } else {
            return rcsrvPredecessor(data, root, null);
        }
    }

    /**
     * private recursive helper method for predecessor
     *
     * @param data data
     * @param curr current node
     * @param returnPredecessor possible
     * @return return
     */
    private T rcsrvPredecessor(T data, AVLNode<T> curr, T returnPredecessor) {
        if (curr == null) {
            throw new NoSuchElementException("The given data could not be found in the tree");
        }
        if (curr.getData().compareTo(data) > 0) {
            return rcsrvPredecessor(data, curr.getLeft(), returnPredecessor);
        }
        if (curr.getData().compareTo(data) < 0) {
            returnPredecessor = curr.getData();
            return rcsrvPredecessor(data, curr.getRight(), returnPredecessor);
        }
        if (curr.getLeft() != null) {
            return predecessorLeft(curr.getLeft());
        }
        if (returnPredecessor == null) {
            return null;
        }

        return returnPredecessor;
    }

    /**
     * private recursive helper method
     * @param curr node
     * @return return
     */
    private T predecessorLeft(AVLNode<T> curr) {
        if (curr.getRight() == null) {
            return curr.getData();
        }
        return predecessorLeft(curr.getRight());
    }


    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("Given int was negative");
        }
        if (k > size) {
            throw new IllegalArgumentException("Given int was larger than the number of data in the AVL");
        } else {
            List<T> list = new LinkedList<>();
            rcsrvKSmallest(k, root, list);
            return list;
        }
    }

    /**
     * Recursive private helper method for kSmallest
     *
     * @param k given int of num of smallest integers wanted
     * @param curr curr node
     * @param list the list to search from
     */
    private void rcsrvKSmallest(int k, AVLNode<T> curr, List<T> list) {
        if (list.size() >= k) {
            return;
        }
        if (curr == null) {
            return;
        } else {
            rcsrvKSmallest(k, curr.getLeft(), list);
            if (list.size() >= k) {
                return;
            }

            list.add(curr.getData());
            rcsrvKSmallest(k, curr.getRight(), list);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
