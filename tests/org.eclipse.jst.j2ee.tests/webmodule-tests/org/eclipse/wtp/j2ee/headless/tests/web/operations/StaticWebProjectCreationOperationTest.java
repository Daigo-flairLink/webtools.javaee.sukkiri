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
package org.eclipse.wtp.j2ee.headless.tests.web.operations;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetProjectCreationDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.project.facet.SimpleWebFacetProjectCreationDataModelProvider;
import org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleProjectCreationOperationTest;

public class StaticWebProjectCreationOperationTest extends ModuleProjectCreationOperationTest {

	public StaticWebProjectCreationOperationTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public static Test suite() {
        return new TestSuite(StaticWebProjectCreationOperationTest.class);
    }

    /* (non-Javadoc)
     * @see org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleProjectCreationOperationTest#getProjectCreationDataModel()
     */
    public IDataModel getComponentCreationDataModel() {
        return DataModelFactory.createDataModel(new SimpleWebFacetProjectCreationDataModelProvider());
    }
    
    public void createSimpleModule(String componentName) throws Exception {
        IDataModel dataModel = getComponentCreationDataModel();
        dataModel.setProperty(IFacetProjectCreationDataModelProperties.FACET_PROJECT_NAME, componentName);
       runAndVerify(dataModel,false,true);
    }
    
    public IDataModel getComponentCreationDataModelWithEar() {
    	return null;
    } 
}
