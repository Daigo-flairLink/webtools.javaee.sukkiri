package org.eclipse.jst.jee.model.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.j2ee.model.IModelProvider;
import org.eclipse.jst.j2ee.model.IModelProviderFactory;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;

public class Ejb3ModelProviderFactory implements IModelProviderFactory {

	public IModelProvider create(IProject project) {
		return new Ejb3ModelProvider(project);
	}

	public IModelProvider create(IVirtualComponent component) {
		return new Ejb3ModelProvider(component.getProject());
	}

}
