package com.mhkb;

import java.util.EventObject;

public class NodeEvent extends EventObject {

	private static final long serialVersionUID = -9102127028903432520L;

	private int oldData;
	private int data;
	private Node oldNext;
	private Node next;
	
	public NodeEvent(Node source, int oldData, int data, Node oldNext, Node next) {
		super(source);
		this.oldData = oldData;
		this.data = data;
		this.oldNext = oldNext;
		this.next = next;
	}
	
	@Override
	public Node getSource() {
		return (Node) super.getSource();
	}
	
	public int getOldData() {
		return this.oldData;
	}
	
	public int getCurrData() {
		return this.data;
	}
	
	public Node getOldNext() {
		return this.oldNext;
	}
	
	public Node getCurrNext() {
		return this.next;
	}
}
