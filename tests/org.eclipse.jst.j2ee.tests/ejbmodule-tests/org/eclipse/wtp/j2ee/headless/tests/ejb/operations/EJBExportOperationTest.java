/*
 * Created on Jan 6, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.eclipse.wtp.j2ee.headless.tests.ejb.operations;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jst.j2ee.commonarchivecore.internal.EJBJarFile;
import org.eclipse.jst.j2ee.ejb.componentcore.util.EJBArtifactEdit;
import org.eclipse.jst.j2ee.internal.ejb.project.operations.EJBComponentExportDataModelProvider;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wtp.headless.tests.savestrategy.EJBImportOperationTest;
import org.eclipse.wtp.headless.tests.savestrategy.ModuleImportOperationTestCase;
import org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleExportOperationTestCase;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EJBExportOperationTest extends ModuleExportOperationTestCase {  
	
	public EJBExportOperationTest(String name) {
		super(name);
	}
	 
	public static Test suite() {
		return new TestSuite(EJBExportOperationTest.class);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleExportOperationTestCase#getExportModel()
	 */
	protected IDataModel getModelInstance() {
		return DataModelFactory.createDataModel(new EJBComponentExportDataModelProvider());
	}
	/* (non-Javadoc)
	 * @see org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleExportOperationTestCase#getImportTestCase()
	 */
	protected ModuleImportOperationTestCase getImportTestCase() {
		return new EJBImportOperationTest("");
	}
	
	public void testExport(IVirtualComponent component, String filename) throws Exception {
		super.testExport(component,filename);
		//verify ejb dd
		testDDExported(component);
	}
	
	protected void testDDExported(IVirtualComponent component) throws Exception {
		EJBArtifactEdit ejbEdit = null;
		EJBJarFile ejbJarFile = null;
		try {
			ejbEdit = EJBArtifactEdit.getEJBArtifactEditForRead(component);
			if (ejbEdit != null) {
				ejbJarFile = (EJBJarFile)ejbEdit.asArchive(true);
				try {
				Resource res = ejbJarFile.getDeploymentDescriptorResource();
				assertNotNull(res);
				} catch (Exception e) {
					throw e;
				}
			}
		} finally {
			if (ejbEdit !=null)
				ejbEdit.dispose();
			if(ejbJarFile != null){
				ejbJarFile.close();
			}
		}
	}

	protected IProject[] getExportableProjects() throws Exception {
		IProject[] projs = super.getExportableProjects();
		List filteredProjs = new ArrayList();
		for (int i = 0; i < projs.length; i++) {
			IProject project = projs[i];
			
			IVirtualComponent comp = ComponentCore.createComponent(project);
			if(J2EEProjectUtilities.isEJBProject(comp.getProject()))
				filteredProjs.add(project);
		}
		return (IProject[]) filteredProjs.toArray(new IProject[filteredProjs.size()]);
		
	}
	

}
