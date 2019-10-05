package com.mhkb;

import java.util.EventListener;

public interface NodeListener extends EventListener {
	
	public void nodeCreated(NodeEvent e);

	public void dataAccessed(NodeEvent e);
	
	public void dataModified(NodeEvent e);
	
	public void linkAccessed(NodeEvent e);
	
	public void linkModified(NodeEvent e);
}
