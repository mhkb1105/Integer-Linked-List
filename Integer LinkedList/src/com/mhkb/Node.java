package com.mhkb;

/**
 * Nodes for a linked list of int values. A node stores an int value (called the
 * data of the node) and a reference to the next node in a linked sequence.
 */
public class Node {
	
	private static NodeListener listener = null;

	private Node next;
	private int data;
	
	/**
	 * Initializes this node so that its data is 0 and its next node is null.
	 */
	public Node() {
		this(0, null);
	}
	
	/**
	 * Initializes this node so that its data and next node are equal to
	 * the specified data and next node.
	 * 
	 * @param data an int value to store in this node
	 * @param next the next node of this node. next can be equal to null.
	 */
	public Node(int data, Node next) {
		this.data = data;
		this.next = next;
		if (Node.listener != null) {
			Node.listener.nodeCreated(new NodeEvent(this, data, data, null, next));
		}
	}
	
	/**
	 * Sets the next node of this node to the specified node.
	 * 
	 * @param next the next node of this node. next can be equal to null.
	 */
	public void setNext(Node next) {
		Node oldNext = this.next;
		this.next = next;
		if (Node.listener != null) {
			Node.listener.linkModified(new NodeEvent(this, data, data, oldNext, next));
		}
	}
	
	/**
	 * Sets the int value stored in this node to the specified value.
	 * 
	 * @param data the int value to store in this node
	 */
	public void setData(int data) {
		int oldData = this.data;
		this.data = data;
		if (Node.listener != null) {
			Node.listener.dataModified(new NodeEvent(this, oldData, data, next, next));
		}
	}
	
	/**
	 * Get the next node of this node.
	 * 
	 * @return the next node of this node. The returned value can be equal to null.
	 */
	public Node getNext() {
		if (Node.listener != null) {
			Node.listener.linkAccessed(new NodeEvent(this, data, data, next, next));
		}
		return this.next;
	}
	
	/**
	 * Get the int value stored in this node.
	 * 
	 * @return the int value stored in this node
	 */
	public int getData() { 
		if (Node.listener != null) {
			Node.listener.dataAccessed(new NodeEvent(this, data, data, next, next));
		}
		return this.data;
	}
	
	/**
	 * Set the listener of node events; students should not use this method.
	 * 
	 * @param listener the listener of node events
	 */
	public static void setListener(NodeListener listener) {
		Node.listener = listener;
	}
	
	/**
	 * Get the listener of node events; students should not use this method.
	 * 
	 * @return the listener of node events
	 */
	public static NodeListener getListener() {
		return Node.listener;
	}
}
