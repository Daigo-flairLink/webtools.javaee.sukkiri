/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.flexible.project.fvtests;
import junit.framework.Test;
import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jst.j2ee.flexible.project.tests.Migrate07EJBTest;
import org.eclipse.jst.j2ee.project.facet.IJavaProjectMigrationDataModelProperties;
import org.eclipse.jst.j2ee.project.facet.JavaProjectMigrationDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.tests.SimpleTestSuite;

public  class ProjectMigrationTest extends TestCase {
	
	public static String DEFAULT_PROJECT_NAME = "TestJavaProj";	
	
	public ProjectMigrationTest(String name) {
		super(name);
	}
	
	public ProjectMigrationTest() {
		super();
	}	

    public static Test suite() {
        //return new SimpleTestSuite(ProjectMigrationTest.class);
        return new SimpleTestSuite(Migrate07EJBTest.class);
    }
	
    
	public void testProjectMigration() throws Exception {
		runAll();
	}
	
	private void createJavaProject(IProgressMonitor monitor) throws CoreException {
		
		IProject project = ProjectUtilities.getProject(DEFAULT_PROJECT_NAME);
		
		if( project.exists()){
			project.delete( true, true, null );
		}
		
//		IDataModel model = DataModelFactory.createDataModel(new TestJavaProjectCreationDataModelProvider());	
//		model.setProperty(ITestJavaProjectCreationProperties.PROJECT_NAME, DEFAULT_PROJECT_NAME);
//		
//		String[] srcFolder = new String[2];
//		srcFolder[0] = new String("Src1");
//		srcFolder[1] = new String("Src2");
//		
//		model.setProperty(ITestJavaProjectCreationProperties.SOURCE_FOLDERS, srcFolder);
//		try {
//			model.getDefaultOperation().execute(monitor, null);
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}

	}
	
	private IPath getOutputPath(IProject project) {
		String outputLocation = "bin";
		return project.getFullPath().append(outputLocation);
	}	
	
	
	public void runAll(){
		try {
			IProgressMonitor monitor = new NullProgressMonitor();
			createJavaProject(monitor);
			migrateProject(DEFAULT_PROJECT_NAME);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void migrateProject(String projectName){	
		IDataModel model = DataModelFactory.createDataModel(new JavaProjectMigrationDataModelProvider());
		model.setProperty( IJavaProjectMigrationDataModelProperties.PROJECT_NAME, projectName);

		try {
			model.getDefaultOperation().execute( null, null );
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}
