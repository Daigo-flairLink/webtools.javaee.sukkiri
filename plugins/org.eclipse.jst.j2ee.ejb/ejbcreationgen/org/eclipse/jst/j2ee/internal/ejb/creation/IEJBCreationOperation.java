/*******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.internal.ejb.creation;


import org.eclipse.jst.j2ee.ejb.EnterpriseBean;
import org.eclipse.wst.common.frameworks.internal.operations.IHeadlessRunnableWithProgress;

/**
 * @deprecated
 * @author jsholl
 */
public interface IEJBCreationOperation extends IHeadlessRunnableWithProgress {
	/**
	 * Return the newly created EnterpriseBean.
	 */
	EnterpriseBean getNewEnterpriseBean();
}