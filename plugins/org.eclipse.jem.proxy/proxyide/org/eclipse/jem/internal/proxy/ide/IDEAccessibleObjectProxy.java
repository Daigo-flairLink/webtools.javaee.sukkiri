/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/*


 */
package org.eclipse.jem.internal.proxy.ide;

import java.lang.reflect.AccessibleObject;

import org.eclipse.jem.internal.proxy.core.*;
 
/**
 * 
 * @since 1.0.0
 */
public class IDEAccessibleObjectProxy extends IDEBeanProxy implements IAccessibleObjectProxy {

	protected IDEAccessibleObjectProxy(IDEProxyFactoryRegistry aProxyFactoryRegistry) {
		super(aProxyFactoryRegistry);
	}

	protected IDEAccessibleObjectProxy(IDEProxyFactoryRegistry aProxyFactoryRegistry, Object anObject) {
		super(aProxyFactoryRegistry, anObject);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jem.internal.proxy.core.IAccessibleObjectProxy#isAccessible()
	 */
	public boolean isAccessible() throws ThrowableProxy {
		return ((AccessibleObject) getBean()).isAccessible();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jem.internal.proxy.core.IAccessibleObjectProxy#setAccessible(boolean)
	 */
	public void setAccessible(boolean flag) throws ThrowableProxy {
		((AccessibleObject) getBean()).setAccessible(flag);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jem.internal.proxy.core.IBeanProxy#getTypeProxy()
	 */
	public IBeanTypeProxy getTypeProxy() {
		return ((IDEMethodProxyFactory) fProxyFactoryRegistry.getMethodProxyFactory()).accessibleType;
	}

}
