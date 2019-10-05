package com.mhkb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AnnotatedLinkedList extends JFrame implements ActionListener, NodeListener {
	private JPanel topPanel;
	private JPanel controls;
	private JTextArea console;
	private Queue<String> consoleQueue;

	private int addValue;
	private int addFirstValue;
	private int getIndex;
	private int setValue;
	private int setIndex;
	private int removeIndex;
	private int shiftRightIndex;

	private LinkedIntList list;

	public AnnotatedLinkedList() {
		super("Annotated linked list");
		
		for (Map.Entry<Object, Object> entry : javax.swing.UIManager.getDefaults().entrySet()) {
		    Object key = entry.getKey();
		    Object value = javax.swing.UIManager.get(key);
		    if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
		        javax.swing.plaf.FontUIResource fr=(javax.swing.plaf.FontUIResource)value;
		        javax.swing.plaf.FontUIResource f = 
		        		new javax.swing.plaf.FontUIResource((fr.deriveFont(fr.getSize2D() * 2.0f)));
		        javax.swing.UIManager.put(key, f);
		    }
		}

		this.list = new LinkedIntList();
		this.consoleQueue = new ArrayDeque<String>();
		this.addValue = 0;

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		this.topPanel = new JPanel();
		this.topPanel.setLayout(new BorderLayout(5, 5));
		this.topPanel.setMinimumSize(new Dimension(800, 600));
		this.topPanel.setPreferredSize(new Dimension(800, 600));
		this.console = new JTextArea("[empty]\n");
		this.topPanel.add(this.console);
		this.console.setMinimumSize(this.console.getParent().getMinimumSize());
		this.console.setBackground(Color.WHITE);
		this.console.setOpaque(true);
		this.getContentPane().add(this.topPanel);

		this.controls = new JPanel();
		this.controls.setLayout(new BoxLayout(this.controls, BoxLayout.Y_AXIS));
		// this.controls.setPreferredSize(new Dimension(800, 600));
		this.getContentPane().add(this.controls);

		this.controls.add(this.makeAddControls());
		this.controls.add(this.makeAddFirstControls());
		this.controls.add(this.makeGetControls());
		this.controls.add(this.makeSetControls());
		this.controls.add(this.makeRemoveFirstControls());
		this.controls.add(this.makeRemoveLastControls());
		this.controls.add(this.makeRemoveControls());
		this.controls.add(this.makeShiftRightControls());

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();

		Node.setListener(this);
	}

	private JPanel makeAddControls() {
		JPanel p = new JPanel();
		FlowLayout f = new FlowLayout(FlowLayout.LEFT);
		p.setLayout(f);
		JButton go = new JButton("Go!");
		go.setActionCommand("ADD");
		go.addActionListener(this);
		SpinnerModel model = new SpinnerNumberModel(0, -10, 10, 1);
		JSpinner spin = new JSpinner(model);
		spin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				SpinnerNumberModel m = (SpinnerNumberModel) s.getModel();
				AnnotatedLinkedList.this.addValue = m.getNumber().intValue();
			}
		});
		p.add(go);
		p.add(new JLabel("add("));
		p.add(spin);
		p.add(new JLabel(")"));
		return p;
	}

	private JPanel makeAddFirstControls() {
		JPanel p = new JPanel();
		FlowLayout f = new FlowLayout(FlowLayout.LEFT);
		p.setLayout(f);
		JButton go = new JButton("Go!");
		go.setActionCommand("ADD_FRONT");
		go.addActionListener(this);
		SpinnerModel model = new SpinnerNumberModel(0, -10, 10, 1);
		JSpinner spin = new JSpinner(model);
		spin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				SpinnerNumberModel m = (SpinnerNumberModel) s.getModel();
				AnnotatedLinkedList.this.addFirstValue = m.getNumber().intValue();
			}
		});
		p.add(go);
		p.add(new JLabel("addFirst("));
		p.add(spin);
		p.add(new JLabel(")"));
		return p;
	}

	private JPanel makeGetControls() {
		JPanel p = new JPanel();
		FlowLayout f = new FlowLayout(FlowLayout.LEFT);
		p.setLayout(f);
		JButton go = new JButton("Go!");
		go.setActionCommand("GET");
		go.addActionListener(this);
		SpinnerModel model = new SpinnerNumberModel(0, 0, 4, 1);
		JSpinner spin = new JSpinner(model);
		spin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				SpinnerNumberModel m = (SpinnerNumberModel) s.getModel();
				AnnotatedLinkedList.this.getIndex = m.getNumber().intValue();
			}
		});
		p.add(go);
		p.add(new JLabel("get("));
		p.add(spin);
		p.add(new JLabel(")"));
		return p;
	}

	private JPanel makeSetControls() {
		JPanel p = new JPanel();
		FlowLayout f = new FlowLayout(FlowLayout.LEFT);
		p.setLayout(f);
		JButton go = new JButton("Go!");
		go.setActionCommand("SET");
		go.addActionListener(this);
		SpinnerModel model = new SpinnerNumberModel(0, 0, 4, 1);
		JSpinner spin = new JSpinner(model);
		spin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				SpinnerNumberModel m = (SpinnerNumberModel) s.getModel();
				AnnotatedLinkedList.this.setIndex = m.getNumber().intValue();
			}
		});
		SpinnerModel model2 = new SpinnerNumberModel(0, -10, 10, 1);
		JSpinner spin2 = new JSpinner(model2);
		spin2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				SpinnerNumberModel m = (SpinnerNumberModel) s.getModel();
				AnnotatedLinkedList.this.setValue = m.getNumber().intValue();
			}
		});
		p.add(go);
		p.add(new JLabel("set("));
		p.add(spin);
		p.add(new JLabel(", "));
		p.add(spin2);
		p.add(new JLabel(")"));
		return p;
	}

	private JPanel makeRemoveFirstControls() {
		JPanel p = new JPanel();
		FlowLayout f = new FlowLayout(FlowLayout.LEFT);
		p.setLayout(f);
		JButton go = new JButton("Go!");
		go.setActionCommand("REMOVE_FIRST");
		go.addActionListener(this);
		p.add(go);
		p.add(new JLabel("removeFirst()"));
		return p;
	}

	private JPanel makeRemoveLastControls() {
		JPanel p = new JPanel();
		FlowLayout f = new FlowLayout(FlowLayout.LEFT);
		p.setLayout(f);
		JButton go = new JButton("Go!");
		go.setActionCommand("REMOVE_LAST");
		go.addActionListener(this);
		p.add(go);
		p.add(new JLabel("removeLast()"));
		return p;
	}

	private JPanel makeRemoveControls() {
		JPanel p = new JPanel();
		FlowLayout f = new FlowLayout(FlowLayout.LEFT);
		p.setLayout(f);
		JButton go = new JButton("Go!");
		go.setActionCommand("REMOVE");
		go.addActionListener(this);
		SpinnerModel model = new SpinnerNumberModel(0, 0, 4, 1);
		JSpinner spin = new JSpinner(model);
		spin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				SpinnerNumberModel m = (SpinnerNumberModel) s.getModel();
				AnnotatedLinkedList.this.removeIndex = m.getNumber().intValue();
			}
		});
		p.add(go);
		p.add(new JLabel("remove("));
		p.add(spin);
		p.add(new JLabel(")"));
		return p;
	}

	private JPanel makeShiftRightControls() {
		JPanel p = new JPanel();
		FlowLayout f = new FlowLayout(FlowLayout.LEFT);
		p.setLayout(f);
		JButton go = new JButton("Go!");
		go.setActionCommand("SHIFT_RIGHT");
		go.addActionListener(this);
		SpinnerModel model = new SpinnerNumberModel(0, 0, 4, 1);
		JSpinner spin = new JSpinner(model);
		spin.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner s = (JSpinner) e.getSource();
				SpinnerNumberModel m = (SpinnerNumberModel) s.getModel();
				AnnotatedLinkedList.this.shiftRightIndex = m.getNumber().intValue();
			}
		});
		p.add(go);
		p.add(new JLabel("shiftRight("));
		p.add(spin);
		p.add(new JLabel(")"));
		return p;
	}

	@Override
	public void nodeCreated(NodeEvent e) {
		Node.setListener(null);
		Node n = e.getSource();
		String next = "";
		if (e.getCurrNext() == null) {
			next = "null";
		} else {
			next = "" + n.getNext().getData();
		}
		String s = String.format("node created with data: %d, next node: %s\n", e.getCurrData(), next);
		this.consoleQueue.add(s);
		Node.setListener(this);
	}

	@Override
	public void dataAccessed(NodeEvent e) {
		Node.setListener(null);
		String s = String.format("node data: %d retrieved\n", e.getOldData());
		this.consoleQueue.add(s);
		Node.setListener(this);
	}

	@Override
	public void dataModified(NodeEvent e) {
		Node.setListener(null);
		String s = String.format("node data changed from: %d to: %d\n", e.getOldData(), e.getCurrData());
		this.consoleQueue.add(s);
		Node.setListener(this);
	}

	@Override
	public void linkAccessed(NodeEvent e) {
		Node.setListener(null);
		String next = "";
		if (e.getCurrNext() == null) {
			next = "null";
		} else {
			next = "" + e.getCurrNext().getData();
		}
		String s = String.format("following link from node with data: %d to node with data: %s\n", e.getCurrData(),
				next);
		this.consoleQueue.add(s);
		Node.setListener(this);
	}

	@Override
	public void linkModified(NodeEvent e) {
		Node.setListener(null);
		String oldNext = "";
		if (e.getOldNext() == null) {
			oldNext = "null";
		} else {
			oldNext = "" + e.getOldNext().getData();
		}
		String next = "";
		if (e.getCurrNext() == null) {
			next = "null";
		} else {
			next = "" + e.getCurrNext().getData();
		}
		String s = String.format("changing link at node with data: %s from node: %s to node: %s\n", e.getSource().getData(),
				oldNext, next);
		this.consoleQueue.add(s);

		Node.setListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		this.console.setText("");
		Node.setListener(null);
		this.consoleQueue.add("list.toString():\n" + this.list.toString() + "\n\n");
		Node.setListener(this);
		String action = event.getActionCommand();
		try {
			switch (action) {
			case "ADD":
				this.list.add(this.addValue);
				break;
			case "ADD_FRONT":
				this.list.addFirst(this.addFirstValue);
				break;
			case "GET":
				this.list.get(this.getIndex);
				break;
			case "SET":
				this.list.set(this.setIndex, this.setValue);
				break;
			case "REMOVE_FIRST":
				this.list.removeFirst();
				break;
			case "REMOVE_LAST":
				this.list.removeLast();
				break;
			case "REMOVE":
				this.list.remove(this.removeIndex);
				break;
			case "SHIFT_RIGHT":
				this.list.shiftRight(this.shiftRightIndex);
				break;
			case "":
				break;
			}
		} 
		catch (Exception x) {
			this.consoleQueue.add("exception of type: " + x.getClass().getName() + " was thrown\n");
		}
		while (!this.consoleQueue.isEmpty()) {
			this.console.append(this.consoleQueue.remove());
		}
		Node.setListener(null);
		this.console.append("\nlist.toString():\n" + this.list.toString());
		Node.setListener(this);
	}

	public static void main(String[] args) {
		AnnotatedLinkedList ani = new AnnotatedLinkedList();
		ani.setVisible(true);
	}

}
