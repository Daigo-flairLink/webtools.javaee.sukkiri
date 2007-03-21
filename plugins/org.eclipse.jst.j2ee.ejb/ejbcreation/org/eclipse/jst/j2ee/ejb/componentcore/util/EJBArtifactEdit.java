/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.ejb.componentcore.util;

import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jst.j2ee.application.ApplicationPackage;
import org.eclipse.jst.j2ee.application.Module;
import org.eclipse.jst.j2ee.commonarchivecore.internal.Archive;
import org.eclipse.jst.j2ee.commonarchivecore.internal.CommonarchiveFactory;
import org.eclipse.jst.j2ee.commonarchivecore.internal.exception.OpenFailureException;
import org.eclipse.jst.j2ee.componentcore.EnterpriseArtifactEdit;
import org.eclipse.jst.j2ee.ejb.EJBJar;
import org.eclipse.jst.j2ee.ejb.EJBResource;
import org.eclipse.jst.j2ee.ejb.EjbFactory;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.common.CreationConstants;
import org.eclipse.jst.j2ee.internal.common.XMLResource;
import org.eclipse.jst.j2ee.internal.componentcore.EJBBinaryComponentHelper;
import org.eclipse.jst.j2ee.internal.componentcore.EnterpriseBinaryComponentHelper;
import org.eclipse.jst.j2ee.internal.ejb.archiveoperations.EJBComponentLoadStrategyImpl;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.wst.common.componentcore.ArtifactEdit;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.ModuleCoreNature;
import org.eclipse.wst.common.componentcore.UnresolveableURIException;
import org.eclipse.wst.common.componentcore.internal.ArtifactEditModel;
import org.eclipse.wst.common.componentcore.internal.BinaryComponentHelper;
import org.eclipse.wst.common.componentcore.internal.StructureEdit;
import org.eclipse.wst.common.componentcore.internal.impl.ModuleURIUtil;
import org.eclipse.wst.common.componentcore.internal.util.IArtifactEditFactory;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.common.componentcore.resources.IVirtualReference;
import org.eclipse.wst.common.componentcore.resources.IVirtualResource;

/**
 * <p>
 * EJBArtifactEdit obtains a EJB Deployment Descriptor metamodel specifec data from a
 * {@see org.eclipse.jst.j2ee.ejb.EJBResource}&nbsp; which stores the metamodel. The
 * {@see org.eclipse.jst.j2ee.ejb.EJBResource}&nbsp;is retrieved from the
 * {@see org.eclipse.wst.common.modulecore.ArtifactEditModel}&nbsp;using a constant {@see
 * J2EEConstants#EJBJAR_DD_URI_OBJ}. The defined methods extract data or manipulate the contents of
 * the underlying resource.
 * </p>
 * 
 */
public class EJBArtifactEdit extends EnterpriseArtifactEdit implements IArtifactEditFactory {

	/**
	 * <p>
	 * Identifier used to link EJBArtifactEdit to a EJBEditAdapterFactory {@see
	 * EJBEditAdapterFactory} stored in an AdapterManger (@see AdapterManager)
	 * </p>
	 */

	public static final Class ADAPTER_TYPE = EJBArtifactEdit.class;

	/**
	 * 
	 */
	public EJBArtifactEdit() {
		super();
	}

	public EJBArtifactEdit(IVirtualComponent aModule) {
		super(aModule);
	}

	protected BinaryComponentHelper initBinaryComponentHelper(IVirtualComponent binaryModule) {
		return new EJBBinaryComponentHelper(binaryModule);
	}

	/**
	 * @param aHandle
	 * @param toAccessAsReadOnly
	 * @throws IllegalArgumentException
	 */
	public EJBArtifactEdit(IProject aProject, boolean toAccessAsReadOnly) throws IllegalArgumentException {
		super(aProject, toAccessAsReadOnly);
	}

	public EJBArtifactEdit(IProject aProject, boolean toAccessAsReadOnly, boolean forCreate) throws IllegalArgumentException {
		super(aProject, toAccessAsReadOnly, forCreate, J2EEProjectUtilities.EJB);
	}

	/**
	 * <p>
	 * Creates an instance facade for the given {@see ArtifactEditModel}.
	 * </p>
	 * 
	 * @param anArtifactEditModel
	 */
	public EJBArtifactEdit(ArtifactEditModel model) {
		super(model);
	}

	/**
	 * <p>
	 * Creates an instance facade for the given {@see ArtifactEditModel}
	 * </p>
	 * 
	 * <p>
	 * Note: This method is for internal use only. Clients should not call this method.
	 * </p>
	 * 
	 * @param aNature
	 *            A non-null {@see ModuleCoreNature}for an accessible project
	 * @param aModule
	 *            A non-null {@see WorkbenchComponent}pointing to a module from the given
	 *            {@see ModuleCoreNature}
	 */
	public EJBArtifactEdit(ModuleCoreNature aNature, IVirtualComponent aModule, boolean toAccessAsReadOnly) {
		super(aNature, aModule, toAccessAsReadOnly);
	}

	/**
	 * 
	 * @return EJBResource from (@link getDeploymentDescriptorResource())
	 * 
	 */

	public EJBResource getEJBJarXmiResource() {
		return (EJBResource) getDeploymentDescriptorResource();
	}

	/**
	 * Experimental API subject to change
	 * 
	 * @return IVirtualFolder that contains the deployment descriptor resource.
	 * @throws CoreException
	 */

	public IVirtualFolder getDeploymentDescriptorFolder() throws CoreException {
		IVirtualResource[] resources = getComponent().getRootFolder().members();
		if (resources != null && resources.length > 0) {
			for (int i = 0; i < resources.length; i++) {
				IVirtualResource resource = resources[i];
				if (resource.getType() == IVirtualResource.FOLDER) {
					IVirtualFolder folder = (IVirtualFolder) resource;
					IVirtualResource ddResource = folder.findMember(J2EEConstants.EJBJAR_DD_SHORT_NAME);
					if (ddResource != null)
						return folder;
				}
			}
		}
		return null;
	}

	/**
	 * Experimental API subject to change
	 * 
	 * @return String form of the ejbModule folder path relative to the full path.
	 * @throws CoreException
	 */

	public String getEjbModuleRelative(String fullPath) throws CoreException {
		if (fullPath != null) {
			if (getDeploymentDescriptorFolder() != null) {
				String modulePath;
				if (fullPath.indexOf('/') == 0)
					modulePath = getDeploymentDescriptorFolder().getProjectRelativePath().removeTrailingSeparator().removeLastSegments(1).toOSString();
				else
					modulePath = getDeploymentDescriptorFolder().getProjectRelativePath().removeTrailingSeparator().removeLastSegments(1).makeRelative().toOSString();
				int indx = (fullPath.indexOf(modulePath) + modulePath.length() + 1);
				if (fullPath.indexOf(modulePath) != -1)
					return fullPath.substring(indx);
			}
		}
		return fullPath;
	}


	/**
	 * <p>
	 * Retrieves J2EE version information from EJBResource.
	 * </p>
	 * 
	 * @return an integer representation of a J2EE Spec version
	 * 
	 */

	public int getJ2EEVersion() {
		return getEJBJarXmiResource().getJ2EEVersionID();
	}

	/**
	 * <p>
	 * Checks is a EJB Client Jar exists for the ejb module project
	 * </p>
	 * 
	 * @return boolean
	 * 
	 */

	public boolean hasEJBClientJARProject() {

		if (getEJBClientJarModule() != null)
			return true;
		return false;
	}

	/**
	 * <p>
	 * Creates a new EJB module
	 * </p>
	 * 
	 * @return
	 */
	public Module createNewModule() {
		if (isBinary()) {
			throwAttemptedBinaryEditModelAccess();
		}
		return ((ApplicationPackage) EPackage.Registry.INSTANCE.getEPackage(ApplicationPackage.eNS_URI)).getApplicationFactory().createEjbModule();
	}


	/**
	 * @param project
	 * @return WorkbenchComponent
	 */
	public IVirtualComponent getEJBClientJarModule() {
		EJBJar jar = getEJBJar();
		IVirtualComponent ejbComponent, ejbClientComponent = null;
		ejbComponent = ComponentCore.createComponent(getProject());
		if (ejbComponent == null)
			return null;
		Properties props = ejbComponent.getMetaProperties();
		String clientCompName = props.getProperty(CreationConstants.EJB_CLIENT_NAME);
		if (clientCompName != null && !clientCompName.equals("")) { //$NON-NLS-1$
			IVirtualReference vRef = ejbComponent.getReference(clientCompName);
			if (vRef != null)
				ejbClientComponent = vRef.getReferencedComponent();
		} else {
			String clientJAR = null;
			if (jar != null)
				clientJAR = jar.getEjbClientJar();
			if (clientJAR != null) {
				clientJAR = clientJAR.substring(0, clientJAR.length() - 4);
				ejbComponent = ComponentCore.createComponent(getProject());
				if (ejbComponent == null)
					return null;
				IVirtualReference ref = ejbComponent.getReference(clientJAR);
				if (ref != null)
					ejbClientComponent = ref.getReferencedComponent();
			}
		}
		return ejbClientComponent;
	}


	/**
	 * <p>
	 * Retrieves the underlying resource from the ArtifactEditModel using defined URI.
	 * </p>
	 * 
	 * @return Resource
	 * 
	 */

	public Resource getDeploymentDescriptorResource() {
		if (isBinary()) {
			return getBinaryComponentHelper().getResource(J2EEConstants.EJBJAR_DD_URI_OBJ);
		}
		return getArtifactEditModel().getResource(J2EEConstants.EJBJAR_DD_URI_OBJ);
	}

	/**
	 * 
	 * @return EJBJar from (@link getDeploymentDescriptorRoot())
	 * 
	 */
	public EJBJar getEJBJar() {
		return (EJBJar) getDeploymentDescriptorRoot();
	}

	/**
	 * <p>
	 * Obtains the EJBJar (@see EJBJar) root object from the EJBResource. If the root object does
	 * not exist, then one is created (@link addEJBJarIfNecessary(getEJBJarXmiResource())). The root
	 * object contains all other resource defined objects.
	 * </p>
	 * 
	 * @return EObject
	 * 
	 */
	public EObject getDeploymentDescriptorRoot() {
		List contents = getDeploymentDescriptorResource().getContents();
		if (contents.size() > 0)
			return (EObject) contents.get(0);
		if (isBinary()) {
			return null;
		}
		addEJBJarIfNecessary((EJBResource) getDeploymentDescriptorResource());
		return (EObject) contents.get(0);
	}

	/**
	 * Returns the deployment descriptor type of the EJB module.
	 * 
	 * @return int
	 */

	public int getDeploymentDescriptorType() {
		return XMLResource.EJB_TYPE;
	}

	/**
	 * <p>
	 * Creates a deployment descriptor root object (EJBJar) and populates with data. Adds the root
	 * object to the deployment descriptor resource.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param aModule
	 *            A non-null pointing to a {@see XMLResource} Note: This method is typically used
	 *            for JUNIT - move?
	 *            </p>
	 */
	protected void addEJBJarIfNecessary(XMLResource aResource) {
		if (isBinary()) {
			throwAttemptedBinaryEditModelAccess();
		}
		if (aResource != null) {
			if (aResource.getContents() == null || aResource.getContents().isEmpty()) {
				EJBJar ejbJar = EjbFactory.eINSTANCE.createEJBJar();
				aResource.getContents().add(ejbJar);
			}
			EJBJar ejbJar = (EJBJar) aResource.getContents().get(0);
			URI moduleURI = getArtifactEditModel().getModuleURI();
			try {
				ejbJar.setDisplayName(StructureEdit.getDeployedName(moduleURI));
			} catch (UnresolveableURIException e) {
				// Ignore
			}
			aResource.setID(ejbJar, J2EEConstants.EJBJAR_ID);
			// TODO add more mandatory elements
			try {
				aResource.saveIfNecessary();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>
	 * Returns an instance facade to manage the underlying edit model for the given
	 * {@see WorkbenchComponent}. Instances of ArtifactEdit that are returned through this method
	 * must be {@see #dispose()}ed of when no longer in use.
	 * </p>
	 * <p>
	 * Use to acquire an ArtifactEdit facade for a specific {@see WorkbenchComponent}&nbsp;that
	 * will not be used for editing. Invocations of any save*() API on an instance returned from
	 * this method will throw exceptions.
	 * </p>
	 * <p>
	 * <b>The following method may return null. </b>
	 * </p>
	 * 
	 * @param aModule
	 *            A valid {@see WorkbenchComponent}&nbsp;with a handle that resolves to an
	 *            accessible project in the workspace
	 * @return An instance of ArtifactEdit that may only be used to read the underlying content
	 *         model
	 */
	public static EJBArtifactEdit getEJBArtifactEditForRead(IProject aProject) {
		EJBArtifactEdit artifactEdit = null;
		try {
			if (isValidEJBModule(ComponentCore.createComponent(aProject)))
				artifactEdit = new EJBArtifactEdit(aProject, true, false);
		} catch (Exception e) {
			artifactEdit = null;
		}
		return artifactEdit;
	}

	/**
	 * <p>
	 * Returns an instance facade to manage the underlying edit model for the given
	 * {@see WorkbenchComponent}. Instances of ArtifactEdit that are returned through this method
	 * must be {@see #dispose()}ed of when no longer in use.
	 * </p>
	 * <p>
	 * Use to acquire an ArtifactEdit facade for a specific {@see WorkbenchComponent}&nbsp;that
	 * will be used for editing.
	 * </p>
	 * <p>
	 * <b>The following method may return null. </b>
	 * </p>
	 * 
	 * @param aModule
	 *            A valid {@see WorkbenchComponent}&nbsp;with a handle that resolves to an
	 *            accessible project in the workspace
	 * @return An instance of ArtifactEdit that may be used to modify and persist changes to the
	 *         underlying content model
	 */
	public static EJBArtifactEdit getEJBArtifactEditForWrite(IProject aProject) {
		EJBArtifactEdit artifactEdit = null;
		try {
			if (isValidEJBModule(ComponentCore.createComponent(aProject)))
				artifactEdit = new EJBArtifactEdit(aProject, false, false);
		} catch (Exception e) {
			artifactEdit = null;
		}
		return artifactEdit;
	}


	/**
	 * <p>
	 * Returns an instance facade to manage the underlying edit model for the given
	 * {@see WorkbenchComponent}. Instances of EJBArtifactEdit that are returned through this
	 * method must be {@see #dispose()}ed of when no longer in use.
	 * </p>
	 * <p>
	 * Use to acquire an EJBArtifactEdit facade for a specific {@see WorkbenchComponent}&nbsp;that
	 * will not be used for editing. Invocations of any save*() API on an instance returned from
	 * this method will throw exceptions.
	 * </p>
	 * <p>
	 * <b>This method may return null. </b>
	 * </p>
	 * 
	 * <p>
	 * Note: This method is for internal use only. Clients should not call this method.
	 * </p>
	 * 
	 * @param aModule
	 *            A valid {@see WorkbenchComponent}&nbsp;with a handle that resolves to an
	 *            accessible project in the workspace
	 * @return An instance of EJBArtifactEdit that may only be used to read the underlying content
	 *         model
	 * @throws UnresolveableURIException
	 *             could not resolve uri.
	 */
	public static EJBArtifactEdit getEJBArtifactEditForRead(IVirtualComponent aModule) {
		if (aModule == null)
			return null;
		if (aModule.isBinary()) {
			return new EJBArtifactEdit(aModule);
		}
		return getEJBArtifactEditForRead(aModule.getProject());
	}

	/**
	 * <p>
	 * Returns an instance facade to manage the underlying edit model for the given
	 * {@see WorkbenchComponent}. Instances of EJBArtifactEdit that are returned through this
	 * method must be {@see #dispose()}ed of when no longer in use.
	 * </p>
	 * <p>
	 * Use to acquire an EJBArtifactEdit facade for a specific {@see WorkbenchComponent}&nbsp;that
	 * will be used for editing.
	 * </p>
	 * <p>
	 * <b>This method may return null. </b>
	 * </p>
	 * 
	 * <p>
	 * Note: This method is for internal use only. Clients should not call this method.
	 * </p>
	 * 
	 * @param aModule
	 *            A valid {@see WorkbenchComponent}&nbsp;with a handle that resolves to an
	 *            accessible project in the workspace
	 * @return An instance of EJBArtifactEdit that may be used to modify and persist changes to the
	 *         underlying content model
	 */
	public static EJBArtifactEdit getEJBArtifactEditForWrite(IVirtualComponent aModule) {
		if (aModule == null || aModule.isBinary())
			return null;
		return getEJBArtifactEditForWrite(aModule.getProject());
	}

	/**
	 * @param module
	 *            A {@see WorkbenchComponent}
	 * @return True if the supplied module
	 *         {@see ArtifactEdit#isValidEditableModule(WorkbenchComponent)}and the moduleTypeId is
	 *         a JST module
	 */
	public static boolean isValidEJBModule(IVirtualComponent aModule) throws UnresolveableURIException {
		if (!isValidEditableModule(aModule))
			return false;
		/* and match the JST_EJB_MODULE type */
		return J2EEProjectUtilities.isEJBProject(aModule.getProject());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.modulecore.util.EnterpriseArtifactEdit#createModelRoot()
	 */
	public EObject createModelRoot() {
		if (isBinary()) {
			throwAttemptedBinaryEditModelAccess();
		}

		return createModelRoot(getJ2EEVersion());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.modulecore.util.EnterpriseArtifactEdit#createModelRoot(int)
	 */
	public EObject createModelRoot(int version) {
		if (isBinary()) {
			throwAttemptedBinaryEditModelAccess();
		}

		EJBResource res = (EJBResource) getDeploymentDescriptorResource();
		res.setModuleVersionID(version);
		addEJBJarIfNecessary(res);
		return ((EJBResource) getDeploymentDescriptorResource()).getRootObject();
	}

	public ArtifactEdit createArtifactEditForRead(IVirtualComponent aComponent) {
		return getEJBArtifactEditForRead(aComponent);
	}

	public ArtifactEdit createArtifactEditForWrite(IVirtualComponent aComponent) {
		return getEJBArtifactEditForWrite(aComponent);
	}

	public Archive asArchive(boolean includeSource, boolean includeClasspathComponents) throws OpenFailureException {
		if (isBinary()) {
			return ((EnterpriseBinaryComponentHelper) getBinaryComponentHelper()).accessArchive();
		} else {
			EJBComponentLoadStrategyImpl loader = new EJBComponentLoadStrategyImpl(getComponent(), includeClasspathComponents);
			loader.setExportSource(includeSource);
			String uri = ModuleURIUtil.getHandleString(getComponent());
			return CommonarchiveFactory.eINSTANCE.openEJBJarFile(loader, uri);
		}
	}

	public static void createDeploymentDescriptor(IProject project, int version) {
		EJBArtifactEdit ejbEdit = new EJBArtifactEdit(project, false, true);
		try {
			ejbEdit.createModelRoot(version);
			ejbEdit.save(null);
		} finally {
			ejbEdit.dispose();
		}
	}
}
