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
/*
 * Created on Nov 6, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.eclipse.jst.j2ee.internal.ejb.archiveoperations;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jst.j2ee.application.operations.J2EEComponentCreationDataModel;
import org.eclipse.jst.j2ee.application.operations.J2EEComponentCreationOperation;
import org.eclipse.jst.j2ee.ejb.internal.modulecore.util.EJBArtifactEdit;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.J2EEVersionUtil;
import org.eclipse.wst.common.modulecore.ModuleCore;
import org.eclipse.wst.common.modulecore.WorkbenchComponent;
import org.eclipse.wst.common.modulecore.internal.util.IModuleConstants;
import org.eclipse.wst.common.modulecore.resources.IVirtualContainer;
import org.eclipse.wst.common.modulecore.resources.IVirtualFolder;

public class EjbComponentCreationOperation extends J2EEComponentCreationOperation {
	public EjbComponentCreationOperation(EjbComponentCreationDataModel dataModel) {
		super(dataModel);
	}

	public EjbComponentCreationOperation() {
		super();
	}

    /* (non-Javadoc)
     * @see org.eclipse.jst.j2ee.application.operations.J2EEComponentCreationOperation#createAndLinkJ2EEComponents()
     */
    protected void createAndLinkJ2EEComponents() throws CoreException {
        IVirtualContainer component = ModuleCore.create(getProject(), getModuleDeployName());
        component.commit();
		//create and link ejbModule Source Folder
		IVirtualFolder appClientModuleFolder = component.getFolder(new Path("/ejbModule")); //$NON-NLS-1$		
		appClientModuleFolder.createLink(new Path("/" + getModuleName() + "/ejbModule"), 0, null);
		
		//create and link META-INF folder
		IVirtualFolder metaInfFolder = component.getFolder(new Path("/" + "ejbModule" + "/" + J2EEConstants.META_INF)); //$NON-NLS-1$		
		metaInfFolder.createLink(new Path("/" + getModuleName() + "/" + "ejbModule" + "/"  + J2EEConstants.META_INF), 0, null);
    } 

	protected void createDeploymentDescriptor(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {

		//should cache wbmodule when created instead of  searching ?
        ModuleCore moduleCore = null;
        WorkbenchComponent wbmodule = null;
        try {
            moduleCore = ModuleCore.getModuleCoreForRead(getProject());
            wbmodule = moduleCore.findWorkbenchModuleByDeployName(operationDataModel.getStringProperty(EjbComponentCreationDataModel.COMPONENT_DEPLOY_NAME));
        } finally {
            if (null != moduleCore) {
                moduleCore.dispose();
            }
        }		

        EJBArtifactEdit ejbEdit = null;
       	try{

       		ejbEdit = EJBArtifactEdit.getEJBArtifactEditForWrite( wbmodule );
       		String projPath = getProject().getLocation().toOSString();
   		
       		projPath += operationDataModel.getProperty( EjbComponentCreationDataModel.DD_FOLDER );
       		projPath +=IPath.SEPARATOR + J2EEConstants.EJBJAR_DD_SHORT_NAME;
       		
       		IPath ejbxmlPath = new Path(projPath);
       		boolean b = ejbxmlPath.isValidPath(ejbxmlPath.toString());
       		if(ejbEdit != null) {
       			int moduleVersion = operationDataModel.getIntProperty(EjbComponentCreationDataModel.COMPONENT_VERSION);
       			ejbEdit.createModelRoot( getProject(), ejbxmlPath, moduleVersion );
       		}
       	}
       	catch(Exception e){
            e.printStackTrace();
       	} finally {
       		if(ejbEdit != null)
       			ejbEdit.dispose();
       		ejbEdit = null;
       	}	
       	
	}

	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
		super.execute( IModuleConstants.JST_EJB_MODULE, monitor );
		runNestedDefaultOperation(((EjbComponentCreationDataModel)operationDataModel).getNestedEJBClientComponentDataModel() ,monitor);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jst.j2ee.application.operations.J2EEComponentCreationOperation#getVersion()
	 */
	protected String getVersion() {
		int version = operationDataModel.getIntProperty(J2EEComponentCreationDataModel.COMPONENT_VERSION);
		return J2EEVersionUtil.getEJBTextVersion(version);
	}
	
}