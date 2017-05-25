/*******************************************************************************
 * Copyright (c) 2014, 2016 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jordan Deyton (UT-Battelle, LLC.) - initial API and implementation and/or initial documentation
 *    Jay Jay Billings (UT-Battelle, LLC.) - fixed class author tag
 *******************************************************************************/
package org.eclipse.january.form;

import java.util.Stack;

/**
 * This class implements a pre-order traversal for a {@link TreeComposite}. To
 * use this, instantiate it with a root TreeComposite and use the standard
 * iterator commands, e.g.:
 * 
 * <pre>
 * <code>
 * TreeComposite root;
 * // Set up your tree here...
 * 
 * Iterator<TreeComposite> iterator = new PreOrderTreeCompositeIterator(root);
 * while (iterator.hasNext()) {
 *     TreeComposite child = iterator.next();
 * 
 *     // Do something with the child tree here...
 * }
 * </code>
 * </pre>
 * 
 * @author Jordan H. Deyton
 * 
 */
public class PreOrderTreeCompositeIterator
		extends AbstractTreeCompositeIterator {

	/**
	 * A stack used to maintain state information about the position of the
	 * iterator. If empty, there is no remaining TreeComposite to visit.
	 */
	private final Stack<TreeComposite> stack;

	/**
	 * The default constructor.
	 * 
	 * @param root
	 *            The root TreeComposite that is the starting point for this
	 *            iterator.
	 */
	public PreOrderTreeCompositeIterator(TreeComposite root) {

		// Call the super constructor. Send a non-null root TreeComposite. We
		// must make sure this class is completely initialized before throwing
		// an exception for a null argument.
		super((root != null ? root : new TreeComposite()));

		// Create a new, empty stack.
		stack = new Stack<TreeComposite>();

		// If the root TreeComposite is not null, it is the first element to
		// iterate over.
		if (root != null) {
			stack.push(root);
		}
		// Otherwise, now that the class is initialized, we can throw an
		// exception for a null argument.
		else {
			throw new IllegalArgumentException(
					"PreOrderTreeCompositeIterator error: "
							+ "Root cannot be null.");
		}

		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.january.form.AbstractTreeCompositeIterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.january.form.AbstractTreeCompositeIterator#next()
	 */
	@Override
	public TreeComposite next() {

		// Set the default return value.
		TreeComposite next = super.next();

		// If we have a TreeComposite to iterate over, proceed.
		next = stack.pop();
		for (int i = next.getNumberOfChildren() - 1; i >= 0; i--) {
			stack.push(next.getChildAtIndex(i));
		}

		return next;
	}

}
