import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Rami Bouhafs
 * @version 1.0
 * @param <T> is generic data type
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The elements should be added to the BST in the order in
     * which they appear in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for-loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            size = 0;
            for (T each : data) { //for loops don't work but for-each loops can
                add(each);
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is nul and cannot be added");
        } else {
            root = rcrsvAdd(data, root);
        }
    }

    /**
     * private recursive helper method for above add method
     *
     * @param data data being added
     * @param curr the current node
     * @return return node
     */
    private BSTNode<T> rcrsvAdd(T data, BSTNode<T> curr) {
        if (curr == null) { //creates new node if one doesn't already exist
            size++;
            return new BSTNode<>(data);
        }
        if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rcrsvAdd(data, curr.getLeft()));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(rcrsvAdd(data, curr.getRight()));
        } else if (curr.getData().compareTo(data) == 0) {
            System.out.println("Nodes are equal");
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is null");
        } else {
            BSTNode<T> removedNode = new BSTNode<>(null);
            root = recursiveRemoveHelper(data, root, removedNode);
            return removedNode.getData();
        }
    }

    /**
     * private recursive helper method for the above remove(T data) wrapper method
     *
     * @param data is the data we're removing
     * @param node is the current node
     * @param removedNode is the node we're removing
     * @return parent node of node that's getting removed
     */
    private BSTNode<T> recursiveRemoveHelper(T data, BSTNode<T> node, BSTNode<T> removedNode) {
        if (node == null) {
            throw new NoSuchElementException("Data was not found in the tree");
        } else {
            if (node.getData().compareTo(data) > 0) {
                node.setLeft(recursiveRemoveHelper(data, node.getLeft(), removedNode));
                return node;
            }
            if (node.getData().compareTo(data) < 0) {
                node.setRight(recursiveRemoveHelper(data, node.getRight(), removedNode));
                return node;
            }
            removedNode.setData(node.getData()); //copies data from current node to removed node
            if (node.getLeft() == null && node.getRight() == null) {
                size--;
                return null;
            }

            if (node.getRight() == null) {
                size--;
                return node.getLeft();
            }

            if (node.getLeft() == null) {
                size--;
                return node.getRight();
            }

            BSTNode<T> subnode = new BSTNode<>(null);
            node.setLeft(removePredecessor(node.getLeft(), subnode));
            node.setData(subnode.getData());
            return node;
        }
    }


    /**
     * private recursive helper method for remove(T data)
     * Finds and removes predecessor node
     *
     * @param node the current node
     * @param subnode the leaf/branch of a node that will be removed
     * @return preceding node that will be removed
     */
    private BSTNode<T> removePredecessor(BSTNode<T> node, BSTNode<T> subnode) {
        if (node.getRight() == null) {
            size--;
            subnode.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(removePredecessor(node.getRight(), subnode));
            return node;
        }
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The given search data was null");
        } else {
            return rcsrvGet(data, root);
        }
    }


    /*
     * private recursive helper method for get method
     *
     * @param data the data to search for
     * @param node the root node to inspect
     * @return data of a node that matches passed in parameter
     */
    private T rcsrvGet(T data, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Data is not found");
        } else {
            if (node.getData().compareTo(data) > 0) {
                return rcsrvGet(data, node.getLeft());
            } else if (node.getData().compareTo(data) < 0) {
                return rcsrvGet(data, node.getRight());
            }
            return (T) node.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is null");
        } else {
            return rcrsvContains(data, root);
        }
    }

    /**
     * private recursive helper method for contains method
     *
     * @param data given data we search the tree for
     * @param node the node
     * @return boolean if tree contains match
     */
    private boolean rcrsvContains(T data, BSTNode node) {
        if (node == null) {
            return false;
        } else {
            if (node.getData().compareTo(data) > 0) {
                return rcrsvContains(data, node.getLeft());
            }
            if (node.getData().compareTo(data) < 0) {
                return rcrsvContains(data, node.getRight());
            }
            return true;
        }
    }


    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new LinkedList<>();
        rcsrvPreorder(list, root);
        return list;
    }

    /**
     * private recursive helper method for preorder method
     *
     * @param list a list
     * @param node a node
     */
    private void rcsrvPreorder(List<T> list, BSTNode<T> node) {
        if (node == null) {
            return;
        } else {
            list.add(node.getData());
            rcsrvPreorder(list, node.getLeft());
            rcsrvPreorder(list, node.getRight());
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new LinkedList<>();
        rcsrvInorder(list, root);
        return list;
    }

    /**
     * private recursive helper method for inorder method
     *
     * @param list a list
     * @param node a BSTNode< T > node
     */
    private void rcsrvInorder(List<T> list, BSTNode<T> node) {
        if (node == null) {
            return;
        } else {
            rcsrvInorder(list, node.getLeft());
            list.add(node.getData());
            rcsrvInorder(list, node.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new LinkedList<>();
        rcsrvPostorder(list, root);
        return list;
    }

    /**
     * private recursive helper method for postorder method
     *
     * @param list a list
     * @param node a node
     */
    private void rcsrvPostorder(List<T> list, BSTNode<T> node) {
        if (node == null) {
            return;
        } else {
            rcsrvPostorder(list, node.getLeft());
            rcsrvPostorder(list, node.getRight());
            list.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use. You may import java.util.Queue as well as an implmenting
     * class.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new LinkedList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> adar = queue.remove();
            if (adar != null) {
                queue.add(adar.getLeft());
                queue.add(adar.getRight());
                list.add(adar.getData());
            }

        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {

        return rcsrvHeight(root);
    }

    /**
     * private recursive helper method for height method
     *
     * @param node a node
     * @return height
     */
    private int rcsrvHeight(BSTNode<T> node) {
        if (node == null) {
            return -1; //not zero because it's null not empty
        } else {
            return Math.max(rcsrvHeight(node.getLeft()), rcsrvHeight(node.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Hint: How can we use the order property of the BST to locate the deepest
     * common ancestor?
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if ((data1 == null) || (data2 == null)) {
            throw new IllegalArgumentException("One of the given data is null");
        }
        BSTNode<T> dca = findDCA(data1, data2, root);
        List<T> list = new LinkedList<>();
        findPathFrom(data1, dca, list);
        list.remove(list.size() - 1);
        findPathTo(data2, dca, list);
        return list;
    }

    /**
     * Finds the deepest common ancestor
     *
     * @param data1 given
     * @param data2 given
     * @param node given
     * @return deepest common ancestor
     */
    private BSTNode<T> findDCA(T data1, T data2, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Given node is null");
        } else {
            if ((node.getData().equals(data1)) || (node.getData() == data2)) {
                return node;
            }
            if ((node.getRight() == null)) {
                return findDCA(data1, data2, node.getLeft());
            }
            if ((node.getLeft() == null)) {
                return findDCA(data1, data2, node.getRight());
            }
            if ((node.getData().compareTo(data1) * node.getData().compareTo(data2)) > 0) {
                if ((node.getData().compareTo(data1)) > 0) {
                    return findDCA(data1, data2, node.getLeft());
                }
                return findDCA(data1, data2, node.getRight());
            }
            return node;
        }
    }

    /**
     * Finds the path from a given data point and to the current node
     *
     * @param data data
     * @param node node
     * @param list alist
     */
    private void findPathFrom(T data, BSTNode node, List<T> list) {
        if (node == null) {
            throw new NoSuchElementException("Search node is null");
        } else {
            list.add(0, (T) node.getData()); //adds to front of the list
            if (node.getData().compareTo(data) > 0) {
                findPathFrom(data, node.getLeft(), list);
            }
            if (node.getData().compareTo(data) < 0) {
                findPathFrom(data, node.getRight(), list);
            }
        }
    }

    /**
     * Finds the path from a given node to a data point
     *
     * @param data data
     * @param node node
     * @param list list
     */
    private void findPathTo(T data, BSTNode node, List<T> list) {
        if (node == null) {
            throw new NoSuchElementException("Search node is null");
        } else {
            list.add((T) node.getData()); //adds data to list
            if (node.getData().compareTo(data) > 0) {
                findPathTo(data, node.getLeft(), list);
            }
            if (node.getData().compareTo(data) < 0) {
                findPathTo(data, node.getRight(), list);
            }
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
    public BSTNode<T> getRoot() {
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
