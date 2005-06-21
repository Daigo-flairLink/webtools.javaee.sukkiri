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
package org.eclipse.jst.j2ee.ejb.internal.deployables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jem.util.logger.proxy.Logger;
import org.eclipse.jst.j2ee.internal.deployables.J2EEDeployableFactory;
import org.eclipse.jst.j2ee.internal.project.IEJBNatureConstants;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.componentcore.resources.IFlexibleProject;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.server.core.IModule;

/**
 * @version 1.0
 * @author
 */
public class EJBDeployableFactory extends J2EEDeployableFactory {
	private static final String ID = "com.ibm.wtp.server.ejb"; //$NON-NLS-1$

	/**
	 * Constructor for EJBDeployableFactory.
	 */
	public EJBDeployableFactory() {
		super();
	}

	public String getFactoryId() {
		return ID;
	}


	public String getNatureID() {
		return IEJBNatureConstants.NATURE_ID;
	}


	protected List createModuleDelegates(IVirtualComponent[] components) throws CoreException {
		EJBFlexibleDeployable moduleDelegate = null;
		IModule module = null;
		List moduleList = new ArrayList(components.length);
		for (int i = 0; i < components.length; i++) {
			IVirtualComponent component = components[i];
			try {
				if(IModuleConstants.JST_EJB_MODULE.equals(component.getComponentTypeId())) {
					moduleDelegate = new EJBFlexibleDeployable(component.getProject(), ID, component);
					module = createModule(component.getName(), component.getName(), moduleDelegate.getType(), moduleDelegate.getVersion(), moduleDelegate.getProject());
					moduleList.add(module);
					moduleDelegate.initialize(module);
				}
			} catch (Exception e) {
				Logger.getLogger().write(e);
			} finally {
				if (module != null) {
					if (getModuleDelegate(module) == null)
						moduleDelegates.add(moduleDelegate);
				}
			}
		}
		return moduleList;

	}

	public IModule[] getModules() {
		cacheModules(false);
		ArrayList moduleList = new ArrayList();
		for (Iterator iter = projects.values().iterator(); iter.hasNext();) {
			IModule[] element = (IModule[]) iter.next();
			for (int j = 0; j < element.length; j++) {
				moduleList.add(element[j]);
			}

		}
		IModule[] modules = new IModule[moduleList.size()];
		moduleList.toArray(modules);
		return modules;

	}

	protected boolean isValidModule(IProject project) {
		if (isFlexibleProject(project)) {
	        IFlexibleProject flex = ComponentCore.createFlexibleProject(project);
	        IVirtualComponent[] comps = flex.getComponents();
	        for (int i = 0; i < comps.length; i++) {
                if(comps[i].getComponentTypeId().equals(IModuleConstants.JST_EJB_MODULE))
                    return true;
            }
        }
        return false;
	}



}