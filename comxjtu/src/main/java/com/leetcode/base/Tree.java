package com.leetcode.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: Jay
 * @Date: Created in 17:06 2018/6/1
 * @Modified By:
 */
public class Tree {
    /**
     * 前序遍历
     *
     * @param root
     * @return
     */
    List<Integer> list = new LinkedList<>();
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) return list;
        preorder(root);
        return list;
    }
    private void preorder(TreeNode root) {
        if (root == null) return;
        list.add(root.val);
        preorder(root.left);
        preorder(root.right);
    }

    /**
     * 中序遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) return list;
        inorder(root);
        return list;
    }
    private void inorder(TreeNode root) {
        if (root == null) return;
        inorder(root.left);
        list.add(root.val);
        inorder(root.right);
    }

    /**
     * 后序遍历
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        postorder(root);
        return list;
    }
    private void postorder(TreeNode root) {
        if (root == null) return;
        postorder(root.left);
        postorder(root.right);
        list.add(root.val);
    }

    /**
     *层次遍历，原来是迭代方法
     * levelOrder1是递归方法
     */
    Queue<TreeNode> queue = new LinkedBlockingQueue<TreeNode>();
    List<List<Integer>> result = new LinkedList<>();
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) return result;
        queue.add(root);
        while (!queue.isEmpty()) {
            levelorder();
        }
        return result;
    }
    private void levelorder() {
        if (queue.isEmpty()) return;
        List<Integer> level = new LinkedList<>();
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = (TreeNode) queue.poll();
            level.add(node.val);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        result.add(level);
    }
    static List<List<Integer>> result1;
    public List<List<Integer>> levelOrder1(TreeNode root) {
        result1=new ArrayList<>();
        recursion(root, 0);
        return result1;
    }
    public void recursion(TreeNode node, int level){
        if(node==null)return;
        if(result1.size()<=level)result1.add(new ArrayList<Integer>());
        result1.get(level).add(node.val);
        recursion(node.left, level+1);
        recursion(node.right, level+1);
    }

    /**
     * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
     * 说明: 叶子节点是指没有子节点的节点。
     *
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        else if (root.left == null && root.right == null) {
            return sum == root.val;
        } else {
            return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
        }
    }

    /**
     * 根据一棵树的中序遍历与后序遍历构造二叉树。
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {

        if (inorder == null || inorder.length <= 0 || postorder == null || postorder.length <= 0) return null;
        return buildTree(inorder, postorder, 0, inorder.length - 1, postorder.length - 1);
    }

    private TreeNode buildTree(int[] inorder, int[] postorder, int start, int iend, int pend) {
        int value = postorder[pend];
        TreeNode treeNode = new TreeNode(value);
        int temp = iend;
        while (temp > start) {
            if (inorder[temp] == value) break;
            temp--;
        }
        if (start != temp) {
            treeNode.left = buildTree(inorder, postorder, start, temp - 1, pend - iend + temp - 1);
        }
        if (temp != iend) {
            treeNode.right = buildTree(inorder, postorder, temp + 1, iend, pend - 1);
        }
        return treeNode;
    }

    /**
     * 从前序与中序遍历序列构造二叉树
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0) {
            return null;
        }
        return buildCore(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildCore(int[] preorder, int preSt, int preEnd, int[] inorder, int inSt, int inEnd) {
        //前序遍历第一个节点是根节点
        int rootValue = preorder[preSt];
        TreeNode root = new TreeNode(rootValue);

        //前序序列只有根节点
        if (preSt == preEnd) {
            return root;
        }
        //遍历中序序列，找到根节点的位置
        int rootInorder = inSt;
        while (inorder[rootInorder] != rootValue && rootInorder <= inEnd) {
            rootInorder++;
        }

        //左子树的长度
        int leftLength = rootInorder - inSt;
        //前序序列中左子树的最后一个节点
        int leftPreEnd = preSt + leftLength;

        //左子树非空
        if (leftLength > 0) {
            //重建左子树
            root.left = buildCore(preorder, preSt + 1, leftPreEnd, inorder, inSt, inEnd);
        }
        //右子树非空
        if (leftLength < preEnd - preSt) {
            //重建右子树
            root.right = buildCore(preorder, leftPreEnd + 1, preEnd, inorder, rootInorder + 1, inEnd);
        }
        return root;
    }

    public void connect(TreeLinkNode root) {
        //递归的实现方式
        if (root == null)
            return;
        if (root.left != null){
            root.left.next = root.right;
            if (root.next != null){
                root.right.next = root.next.left;
            }

        }
        connect(root.left);
        connect(root.right);
    }

    /**
     * 给定一棵二叉树, 找到该树中两个指定节点的最近公共祖先。
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        if(root == p) return p;
        if(root == q) return q;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if(left != null && right != null) return root;
        return left != null ? left : right;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;

        TreeLinkNode(int x) {
            val = x;
        }
    }
}