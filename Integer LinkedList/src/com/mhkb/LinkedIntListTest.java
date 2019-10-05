package com.mhkb;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.Rule;
import org.junit.rules.Timeout;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LinkedIntListTest {
	
	private static boolean ranTestFields = false;
	private static boolean fieldsPasses = false;
	private static boolean ranTestAdd = false;
	private static boolean addPasses = false;
	
	@Before
	public void checkAddPasses() {
		if (!ranTestFields || !ranTestAdd) {
			return;
		}
		if (!fieldsPasses) {
			fail("there are fields in your class that are not required");
		}
		if (!addPasses) {
			fail("cannot run tests without a working add method");
		}
	}
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(1);

	
	/**
	 * Checks that the structure of the linked list got matches
	 * the structure of the list exp. Checks that the linked list
	 * has the correct size, head node, and tail node, and that
	 * the elements in the linked list equal the elements the expected
	 * list. 
	 * 
	 * @param exp the expected list
	 * @param got the list to check
	 */
	private void checkStructure(List<Integer> exp, LinkedIntList got) {
		if (exp.isEmpty()) {
			if (got.size() != 0) {
				fail("expected an empty list; got a list of size: " + got.size());
			}
			if (got.head() != null) {
				fail("expected an empty list; got a list with a non-null head node");
			}
			if (got.tail() != null) {
				fail("expected an empty list; got a list with a non-null tail node");
			}
		}
		else {
			if (exp.size() != got.size()) {
				String err = String.format("list has the wrong size; expected size: %d, got size: %d", 
						exp.size(), got.size());
				fail(err);
			}
			Node n = got.head();
			for (int i = 0; i < exp.size(); i++) {
				int expData = exp.get(i);
				int gotData = n.getData();
				if (expData != gotData) {
					String err = String.format(
							"node: %d has the wrong data; expected: %d but got: %d", 
							i, expData, gotData);
					fail(err);
				}
				if (i == exp.size() - 1) {
					// at the tail node
					if (got.tail() != n) {
						fail("tail() method returns the wrong tail node; did you remember to set this.tail?");
					}
				}
				n = n.getNext();
				if (n == null && i != exp.size() - 1) {
					String err = String.format(
							"node: %d has an unexpected null next link", 
							i);
					fail(err);
				}
			}
			if (n != null) {
				fail("the last node of the list has a non-null next link");
			}
		}
	}
	
	/**
	 * Tests that there are exactly 3 fields and that the types of the
	 * fields are either int or Node.
	 */
	@Test
	public void test00_fields() {
		ranTestFields = true;
		// get all non-static fields
		// make sure fields include only this.size, this.first, and this.last
		try {
			Field[] fields = LinkedIntList.class.getDeclaredFields();
			if (fields.length != 3) {
				fail("the class has the wrong number of fields");
			}
			for (Field f : fields) {
				Class<?> c = f.getType();
				if (c != com.mhkb.Node.class && c != int.class) {
					fail("there is a field whose type is not Node or int");
				}
			}
		}
		catch (Exception x) {
			fail("exception occurred trying to get the fields of this class");
		}
		fieldsPasses = true;
	}
	
	/**
	 * Tests adding to the end of the list.
	 */
	@Test
	public void test01_add() {
		ranTestAdd = true;
		List<Integer> exp = new ArrayList<>();
		LinkedIntList t = new LinkedIntList();
		
		Random rng = new Random();
		for (int i = 0; i < 10; i++) {
			int val = rng.nextInt(101) - 50;
			exp.add(val);
			t.add(val);
			checkStructure(exp, t);
		}
		
		addPasses = true;
	}
	
	/**
	 * Tests getting the size of the list.
	 */
	@Test
	public void test02_size() {
		LinkedIntList t = new LinkedIntList();
		for (int i = 0; i < 1000; i++) {
			assertEquals(i, t.size());
			t.add(i);
		}
	}
	
	/**
	 * Tests the isEmpty method.
	 */
	@Test
	public void test03_isEmpty() {
		LinkedIntList t = new LinkedIntList();
		assertEquals(true, t.isEmpty());
		t.add(0);
		assertEquals(false, t.isEmpty());
		t.add(1);
		assertEquals(false, t.isEmpty());
	}
	
	/**
	 * Tests the getNode method; includes testing of the expected
	 * exception.
	 */
	@Test
	public void test04_getNode() {
		List<Integer> exp = new ArrayList<>();
		LinkedIntList t = new LinkedIntList();
		
		Random rng = new Random();
		for (int i = 0; i < 10; i++) {
			try {
				t.getNode(i);
				fail(String.format("getNode(%d) should have thrown an exception for list: %s", 
						i, exp));
			}
			catch (IndexOutOfBoundsException x) {
				// ok
			}
			catch (Exception x) {
				fail(String.format("getNode(%d) threw an unexpected exception of type %s", 
						i, x.getClass().getName()));
			}
			int val = rng.nextInt(101) - 50;
			exp.add(val);
			t.add(val);
			for (int j = 0; j <= i; j++) {
				int expVal = exp.get(j);
				int gotVal = t.getNode(j).getData();
				assertEquals(String.format("getNode(%d) failed for list: %s", j, exp.toString()),
						expVal, gotVal);
				checkStructure(exp, t);
			}
			try {
				t.getNode(i + 1);
				fail(String.format("getNode(%d) should have thrown an exception for list: %s", 
						i + 1, exp));
			}
			catch (IndexOutOfBoundsException x) {
				// ok
			}
			catch (Exception x) {
				fail(String.format("getNode(%d) threw an unexpected exception of type %s", 
						i + 1, x.getClass().getName()));
			}
		}
	}
	
	/**
	 * Tests the get method; includes testing of the expected
	 * exception.
	 */
	@Test
	public void test05_get() {
		List<Integer> exp = new ArrayList<>();
		LinkedIntList t = new LinkedIntList();
		
		Random rng = new Random();
		for (int i = 0; i < 10; i++) {
			try {
				t.get(i);
				fail(String.format("get(%d) should have thrown an exception for list: %s", 
						i, exp));
			}
			catch (IndexOutOfBoundsException x) {
				// ok
			}
			catch (Exception x) {
				fail(String.format("get(%d) threw an unexpected exception of type %s", 
						i, x.getClass().getName()));
			}
			int val = rng.nextInt(101) - 50;
			exp.add(val);
			t.add(val);
			for (int j = 0; j <= i; j++) {
				int expVal = exp.get(j);
				int gotVal = t.get(j);
				assertEquals(String.format("get(%d) failed for list: %s", j, exp.toString()),
						expVal, gotVal);
				checkStructure(exp, t);
			}
			try {
				t.get(i + 1);
				fail(String.format("get(%d) should have thrown an exception for list: %s", 
						i + 1, exp));
			}
			catch (IndexOutOfBoundsException x) {
				// ok
			}
			catch (Exception x) {
				fail(String.format("get(%d) threw an unexpected exception of type %s", 
						i + 1, x.getClass().getName()));
			}
		}
	}
	
	/**
	 * Tests the set method; includes testing of the expected
	 * exception.
	 */
	@Test
	public void test06_set() {
		List<Integer> exp = new ArrayList<>();
		LinkedIntList t = new LinkedIntList();
		
		Random rng = new Random();
		for (int i = 0; i < 10; i++) {
			try {
				t.set(i, 0);
				fail(String.format("set(%d, 0) should have thrown an exception for list: %s", 
						i, exp));
			}
			catch (IndexOutOfBoundsException x) {
				// ok
			}
			catch (Exception x) {
				fail(String.format("set(%d, 0) threw an unexpected exception of type %s", 
						i, x.getClass().getName()));
			}
			exp.add(-100);
			t.add(-100);
			for (int j = 0; j <= i; j++) {
				int val = rng.nextInt(101) - 50;
				int expReturn = exp.set(j, val);
				int gotReturn = t.set(j, val);
				int expVal = exp.get(j);
				int gotVal = t.get(j);
				assertEquals(String.format("set(%d, %d) failed for list: %s", j, val, exp.toString()),
						expVal, gotVal);
				assertEquals(String.format("set(%d, %d) returned the wrong value", j, val),
						expReturn, gotReturn);
				checkStructure(exp, t);
			}
			try {
				t.set(i + 1, 0);
				fail(String.format("set(%d, 0) should have thrown an exception for list: %s", 
						i + 1, exp));
			}
			catch (IndexOutOfBoundsException x) {
				// ok
			}
			catch (Exception x) {
				fail(String.format("set(%d, 0) threw an unexpected exception of type %s", 
						i + 1, x.getClass().getName()));
			}
		}
	}
	
	/**
	 * Tests the addFirst method
	 */
	@Test
	public void test07_addFirst() {
		LinkedList<Integer> exp = new LinkedList<>();
		LinkedIntList t = new LinkedIntList();
		
		Random rng = new Random();
		for (int i = 0; i < 10; i++) {
			int val = rng.nextInt(101) - 50;
			exp.addFirst(val);
			t.addFirst(val);
			checkStructure(exp, t);
		}
	}

	
	/**
	 * Add to an empty list.
	 */
	@Test
	public void test08_add() {
		LinkedList<Integer> exp = new LinkedList<>();
		LinkedIntList t = new LinkedIntList();
		
		Random rng = new Random();
		int val = rng.nextInt(101) - 50;
		exp.add(0, val);
		t.add(0, val);
		checkStructure(exp, t);
	}
	
	
	/**
	 * Add to front of a list of size 1.
	 */
	@Test
	public void test09_add() {
		LinkedList<Integer> exp = new LinkedList<>();
		LinkedIntList t = new LinkedIntList();
		
		exp.add(0);
		t.add(0);
		Random rng = new Random();
		int val = rng.nextInt(101) - 50;
		exp.add(0, val);
		t.add(0, val);
		checkStructure(exp, t);
	}
	

	/**
	 * Add to end of a list of size 1.
	 */
	@Test
	public void test10_add() {
		LinkedList<Integer> exp = new LinkedList<>();
		LinkedIntList t = new LinkedIntList();
		
		exp.add(0);
		t.add(0);
		Random rng = new Random();
		int val = rng.nextInt(101) - 50;
		exp.add(1, val);
		t.add(1, val);
		checkStructure(exp, t);
	}
	
	/**
	 * Add to middle of a list.
	 */
	@Test
	public void test11_add() {
		LinkedList<Integer> exp = new LinkedList<>();
		LinkedIntList t = new LinkedIntList();
		
		Random rng = new Random();
		for (int i = 0; i < 10; i++) {
			int val = rng.nextInt(101) - 50;
			exp.add(val);
			t.add(val);
		}
		
		for (int i = 0; i < 5; i++) {
			int val = rng.nextInt(101) - 50;
			exp.add(i, val);
			t.add(i, val);
			checkStructure(exp, t);
		}
		
		for (int i = 0; i < 5; i++) {
			int val = rng.nextInt(101) - 50;
			int idx = exp.size() - 4 + i;
			exp.add(idx, val);
			t.add(idx, val);
			checkStructure(exp, t);
		}
	}
	
	/**
	 * Tests the removeFirst method; includes testing of the expected
	 * exception.
	 */
	@Test
	public void test12_removeFirst() {
		LinkedList<Integer> exp = new LinkedList<>();
		LinkedIntList t = new LinkedIntList();
		
		try {
			t.removeFirst();
			fail("removeFirst should have thrown an exception for the empty list");
		}
		catch (NoSuchElementException x) {
			// ok
		}
		catch (Exception x) {
			fail(String.format("removeFirst() threw an unexpected exception of type %s", 
					x.getClass().getName()));
		}
		
		Random rng = new Random();
		for (int i = 0; i < 10; i++) {
			int val = rng.nextInt(101) - 50;
			exp.addFirst(val);
			t.addFirst(val);
		}
		for (int i = 0; i < 10; i++) {
			int expReturn = exp.removeFirst();
			int gotReturn = t.removeFirst();
			checkStructure(exp, t);
			assertEquals("removeFirst() returned the wrong value",
					expReturn, gotReturn);
			
		}
	}
	
	/**
	 * Tests the removeLast method; includes testing of the expected
	 * exception.
	 */
	@Test
	public void test13_removeLast() {
		LinkedList<Integer> exp = new LinkedList<>();
		LinkedIntList t = new LinkedIntList();
		
		try {
			t.removeLast();
			fail("removeLast should have thrown an exception for the empty list");
		}
		catch (NoSuchElementException x) {
			// ok
		}
		catch (Exception x) {
			fail(String.format("removeLast() threw an unexpected exception of type %s", 
					x.getClass().getName()));
		}
		
		Random rng = new Random();
		for (int i = 0; i < 10; i++) {
			int val = rng.nextInt(101) - 50;
			exp.addFirst(val);
			t.addFirst(val);
		}
		for (int i = 0; i < 10; i++) {
			int expReturn = exp.removeLast();
			int gotReturn = t.removeLast();
			checkStructure(exp, t);
			assertEquals("removeLast() returned the wrong value",
					expReturn, gotReturn);
			
		}
	}
	
	
	/**
	 * Remove from an empty list
	 */
	@Test
	public void test14_remove() {
		LinkedIntList t = new LinkedIntList();
		try {
			t.remove(0);
			fail("remove(0) should have thrown an exception for the empty list");
		}
		catch (IndexOutOfBoundsException x) {
			// ok
		}
		catch (Exception x) {
			fail(String.format("remove(0) threw an unexpected exception of type %s", 
					x.getClass().getName()));
		}
	}
	
	/**
	 * Remove at t.size()
	 */
	@Test
	public void test15_remove() {
		LinkedIntList t = new LinkedIntList();
		t.add(0);
		t.add(1);
		t.add(2);
		t.add(3);
		try {
			t.remove(4);
			fail("remove(4) should have thrown an exception for a list of size 4");
		}
		catch (IndexOutOfBoundsException x) {
			// ok
		}
		catch (Exception x) {
			fail(String.format("remove(4) threw an unexpected exception of type %s", 
					x.getClass().getName()));
		}
	}
	
	/**
	 * Remove from a list of size 1
	 */
	@Test
	public void test16_remove() {
		LinkedList<Integer> exp = new LinkedList<>();
		LinkedIntList t = new LinkedIntList();
		
		exp.add(-1);
		t.add(-1);
		exp.remove(0);
		t.remove(0);
		checkStructure(exp, t);
	}
	
	/**
	 * Remove from head, tail, and middle of a list
	 */
	@Test
	public void test17_remove() {
		LinkedList<Integer> exp = new LinkedList<>();
		LinkedIntList t = new LinkedIntList();
		Random rng = new Random();
		for (int i = 0; i < 20; i++) {
			int val = rng.nextInt(101) - 50;
			exp.add(val);
			t.add(val);
		}
		
		// remove from head
		exp.remove(0);
		t.remove(0);
		checkStructure(exp, t);
		
		// remove from tail
		int idx = exp.size() - 1;
		exp.remove(idx);
		t.remove(idx);
		checkStructure(exp, t);
		
		// remove from random index
		for (int i = 0; i < 10; i++) {
			idx = rng.nextInt(exp.size());
			int expReturn = exp.remove(idx);
			int gotReturn = t.remove(idx);
			checkStructure(exp, t);
			assertEquals(String.format("remove(%d) returned the wrong value", idx),
					expReturn, gotReturn);
			
		}
	}
	
	
	/**
	 * Shift right by 0, 1, ..., t.size() 
	 */
	@Test
	public void test18_shiftRight() {
		final int[] VAL = { 0, 1, 2, 3, 4 };
		final Integer[][] EXP = {
				{0, 1, 2, 3, 4},
				{4, 0, 1, 2, 3},
				{3, 4, 0, 1, 2},
				{2, 3, 4, 0, 1},
				{1, 2, 3, 4, 0},
				{0, 1, 2, 3, 4}
		};
		for (int i = 0; i < EXP.length; i++) {
			LinkedList<Integer> exp = new LinkedList<>(Arrays.asList(EXP[i]));
			LinkedIntList t = new LinkedIntList();
			for (int j = 0; j < VAL.length; j++) {
				t.add(VAL[j]);
			}
			t.shiftRight(i);
			checkStructure(exp, t);
		}
	}
}
