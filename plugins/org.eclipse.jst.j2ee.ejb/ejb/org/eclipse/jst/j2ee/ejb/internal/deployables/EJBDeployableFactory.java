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

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.j2ee.internal.deployables.J2EEDeployableFactory;
import org.eclipse.jst.j2ee.internal.project.IEJBNatureConstants;
import org.eclipse.jst.j2ee.internal.project.J2EENature;
import org.eclipse.wst.common.modulecore.ModuleCoreNature;
import org.eclipse.wst.server.core.IModule;

import com.ibm.wtp.common.logger.proxy.Logger;

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

	/*
	 * @see DeployableProjectFactoryDelegate#getFactoryID()
	 */
	public String getFactoryId() {
		return ID;
	}

	/*
	 * @see J2EEDeployableFactory#getNatureID()
	 */
	public String getNatureID() {
		return IEJBNatureConstants.NATURE_ID;
	}


	
    public IModule createModule(J2EENature nature) {
        if (nature == null)
            return null;
        EJBDeployable moduleDelegate = null;
        IModule module = nature.getModule();
        if (module == null) {
            try {
                moduleDelegate = new EJBDeployable(nature, ID);
                module = createModule(moduleDelegate.getId(), moduleDelegate.getName(), moduleDelegate.getType(), moduleDelegate.getVersion(), moduleDelegate.getProject());
                nature.setModule(module);
                moduleDelegate.initialize(module);
            } catch (Exception e) {
                Logger.getLogger().write(e);
            } finally {
                moduleDelegates.add(moduleDelegate);
            }
        }
        return module;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jst.j2ee.internal.deployables.J2EEDeployableFactory#createModules(org.eclipse.wst.common.modulecore.ModuleCoreNature)
     */
    protected List createModules(ModuleCoreNature nature) {
        // TODO Auto-generated method stub
        return null;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.wst.server.core.model.ModuleFactoryDelegate#getModules()
	 */
	public IModule[] getModules() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected boolean isValidModule(IProject project) {

		return false;
	}



}