/*******************************************************************************
 * Copyright (c) 2001, 2019 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.commonarchivecore.internal.exception;



public class NotSupportedException extends ArchiveWrappedException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6690631631593101382L;

	/**
	 * NotSupportedException constructor comment.
	 */
	public NotSupportedException() {
		super();
	}

	/**
	 * NotSupportedException constructor comment.
	 * 
	 * @param e
	 *            java.lang.Exception
	 */
	public NotSupportedException(Exception e) {
		super(e);
	}

	/**
	 * NotSupportedException constructor comment.
	 * 
	 * @param s
	 *            java.lang.String
	 */
	public NotSupportedException(String s) {
		super(s);
	}

	/**
	 * NotSupportedException constructor comment.
	 * 
	 * @param s
	 *            java.lang.String
	 * @param e
	 *            java.lang.Exception
	 */
	public NotSupportedException(String s, Exception e) {
		super(s, e);
	}
}
