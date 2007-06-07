/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/*
 * Created on Jan 5, 2004
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code
 * Generation - Code and Comments
 */
package org.eclipse.wtp.headless.tests.savestrategy;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IProject;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jst.j2ee.componentcore.EnterpriseArtifactEdit;
import org.eclipse.jst.j2ee.ejb.componentcore.util.EJBArtifactEdit;
import org.eclipse.jst.j2ee.internal.ejb.project.operations.EJBComponentImportDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.tests.ProjectUtility;
import org.eclipse.wtp.j2ee.headless.tests.plugin.HeadlessTestsPlugin;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code
 * Generation - Code and Comments
 */
public class EJBImportOperationTest extends ModuleImportOperationTestCase {

	public static Test suite() {
		return new TestSuite(EJBImportOperationTest.class);
	}

	public EJBImportOperationTest(String name) {
		super(name);
	}

	public void testEJB20Import() throws Exception {
		String projectName = "Test13EJB";
		String fileName = getFullPathForEJBJar("Test13EJB.jar");
		testImport(projectName, fileName);
		IProject proj = ProjectUtilities.getProject(projectName);
		EnterpriseArtifactEdit ejbEdit = null;
		try {
			//ejbEdit = EJBArtifactEdit.getEJBArtifactEditForRead(proj);
			ejbEdit = new EJBArtifactEdit(proj,true,true);
			ejbEdit.getJ2EEVersion();
		} finally {
			if (ejbEdit != null)
				ejbEdit.dispose();
		}
	}

	public String getFullPathForEJBJar(String jarName) {
		try {
			return ProjectUtility.getFullFileName(HeadlessTestsPlugin.getDefault(), TESTS_PATH + jarName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jarName;
	}

	public void testEJB21Import() throws Exception {
		testImport("Test14EJB", getFullPathForEJBJar("Test14EJB.jar"));
	}

	protected String getDirectory() {
		return "EJBImportTests";
	}

	protected IDataModel getModelInstance() {
		return DataModelFactory.createDataModel(new EJBComponentImportDataModelProvider());
	}

}
