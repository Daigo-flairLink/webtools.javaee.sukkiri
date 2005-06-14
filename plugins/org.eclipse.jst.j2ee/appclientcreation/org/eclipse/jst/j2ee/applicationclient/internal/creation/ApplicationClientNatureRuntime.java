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
package org.eclipse.jst.j2ee.applicationclient.internal.creation;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jem.workbench.utility.JemProjectUtilities;
import org.eclipse.jst.j2ee.application.Module;
import org.eclipse.jst.j2ee.client.ApplicationClient;
import org.eclipse.jst.j2ee.commonarchivecore.internal.Archive;
import org.eclipse.jst.j2ee.commonarchivecore.internal.exception.OpenFailureException;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.J2EEEditModel;
import org.eclipse.jst.j2ee.internal.J2EEVersionConstants;
import org.eclipse.jst.j2ee.internal.application.ApplicationPackage;
import org.eclipse.jst.j2ee.internal.common.XMLResource;
import org.eclipse.jst.j2ee.internal.plugin.J2EEPlugin;
import org.eclipse.jst.j2ee.internal.project.J2EEModuleNature;
import org.eclipse.wst.common.internal.emfworkbench.integration.EditModel;

/**
 * @deprecated Use
 *             <p>
 *             AppClientArtifactEdit
 *             </p>
 */
public class ApplicationClientNatureRuntime extends J2EEModuleNature implements IApplicationClientNatureConstants {

	private static final String CLIENT_PROJECT_12_OVERLAY = "1_2_ovr"; //$NON-NLS-1$
	private static final String CLIENT_PROJECT_13_OVERLAY = "1_3_ovr"; //$NON-NLS-1$
	private static final String CLIENT_PROJECT_14_OVERLAY = "1_4_ovr"; //$NON-NLS-1$

	/**
	 * ApplicationClientNatureRuntime constructor comment.
	 */
	public ApplicationClientNatureRuntime() {
		super();
	}

	/**
	 * Return a "virtual" archive on this nature's project; used for export
	 */
	public Archive asArchive() throws OpenFailureException {
		return null;
	}

	/**
	 * Return a "virtual" archive on this nature's project; used for export
	 */
	public Archive asArchive(boolean shouldExportSource) throws OpenFailureException {
		return null;
	}

	protected EditModel createCacheEditModel() {
		return getAppClientEditModelForRead(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.j2eeproject.J2EENature#getJ2EEVersion()
	 */
	public int getJ2EEVersion() {
		// TODO Auto-generated method stub

		return getModuleVersion();
	}

	/**
	 * Method used for adding a j2ee project to an ear project; subclasses must override to create a
	 * new instance of the correct kind of Module
	 */
	public Module createNewModule() {
		return ((ApplicationPackage) EPackage.Registry.INSTANCE.getEPackage(ApplicationPackage.eNS_URI)).getApplicationFactory().createJavaClientModule();
	}

	/**
	 * Create a new nature runtime from the project info
	 */
	// public static ApplicationClientNatureRuntime
	// createRuntime(ApplicationClientProjectInfo info) throws CoreException {
	// IProject project = info.getProject();
	// if (!hasRuntime(project)) {
	// addNatureToProject(project, info.getNatureId());
	// ApplicationClientNatureRuntime runtime = getRuntime(project);
	// runtime.initializeFromInfo(info);
	// return runtime;
	// }
	// return getRuntime(project);
	// }
	public String getEditModelKey() {
		return EDIT_MODEL_ID;
	}

	public AppClientEditModel getAppClientEditModelForRead(Object accessorKey) {
		return (AppClientEditModel) getEditModelForRead(EDIT_MODEL_ID, accessorKey);
	}

	public AppClientEditModel getAppClientEditModelForWrite(Object accessorKey) {
		return (AppClientEditModel) getEditModelForWrite(EDIT_MODEL_ID, accessorKey);
	}

	/**
	 * Return the root object, the application, from the application.xml DD.
	 */
	public ApplicationClient getApplicationClient() {
		return ((AppClientEditModel) getCacheEditModel()).getApplicationClient();
	}

	public Resource getApplicationClientXmiResource() {
		return getResource(URI.createURI(J2EEConstants.APP_CLIENT_DD_URI));
	}

	protected String getDefaultSourcePathString() {
		return IApplicationClientNatureConstants.DEFAULT_SOURCE_PATH;
	}

	/**
	 * @see IJ2EENature
	 */
	public IContainer getModuleServerRoot() {
		return JemProjectUtilities.getJavaProjectOutputContainer(project);
	}

	/**
	 * Return the nature's ID.
	 */
	public String getNatureID() {
		return IApplicationClientNatureConstants.NATURE_ID;
	}

	/**
	 * Return the ID of the plugin that this nature is contained within.
	 */
	protected String getPluginID() {
		return J2EEPlugin.PLUGIN_ID;
	}

	/**
	 * Get a WebNatureRuntime that corresponds to the supplied project.
	 * 
	 * @return com.ibm.itp.wt.IWebNature
	 * @param project
	 *            com.ibm.itp.core.api.resources.IProject
	 */
	public static ApplicationClientNatureRuntime getRuntime(IProject project) {
		return (ApplicationClientNatureRuntime) getRuntime(project, IApplicationClientNatureConstants.APPCLIENT_NATURE_IDS);
	}

	/**
	 * Return whether or not the project has a runtime created on it.
	 * 
	 * @return boolean
	 * @param project
	 *            com.ibm.itp.core.api.resources.IProject
	 */
	public static boolean hasRuntime(IProject project) {
		return hasRuntime(project, IApplicationClientNatureConstants.APPCLIENT_NATURE_IDS);
	}

	/*
	 * @see J2EENature#canBeBinary()
	 */
	public boolean canBeBinary() {
		return true;
	}

	/**
	 * Get the module root folder.
	 * 
	 * @return IContainer
	 */
	public IContainer getModuleRoot() {
		return getSourceFolder();
	} // getModuleRoot

	public String getOverlayIconName() {
		switch (getJ2EEVersion()) {
			case J2EEVersionConstants.J2EE_1_2_ID :
				return CLIENT_PROJECT_12_OVERLAY;
			case J2EEVersionConstants.J2EE_1_3_ID :
				return CLIENT_PROJECT_13_OVERLAY;
			case J2EEVersionConstants.J2EE_1_4_ID :
			default :
				return CLIENT_PROJECT_14_OVERLAY;
		}
	}

	public int getDeploymentDescriptorType() {
		return XMLResource.APP_CLIENT_TYPE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.j2eeproject.J2EENature#getDeploymentDescriptorRoot()
	 */
	public EObject getDeploymentDescriptorRoot() {
		return getApplicationClient();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.j2eeproject.J2EENature#getVersionFromModuleFile()
	 */
	protected int getVersionFromModuleFile() {
		ApplicationClient ddRoot = getApplicationClient();
		if (ddRoot != null) {
			return ddRoot.getVersionID();
		}
		return J2EEVersionConstants.J2EE_1_4_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.j2eeproject.J2EENature#getJ2EEEditModelForRead(java.lang.Object)
	 */
	public J2EEEditModel getJ2EEEditModelForRead(Object accessorKey) {
		return getAppClientEditModelForRead(accessorKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.j2eeproject.J2EENature#getJ2EEEditModelForWrite(java.lang.Object)
	 */
	public J2EEEditModel getJ2EEEditModelForWrite(Object accessorKey) {
		return getAppClientEditModelForWrite(accessorKey);
	}

}