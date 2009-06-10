package org.eclipse.jst.j2ee.ejb.internal.operations;

public class RemoteBusinessInterfaceTemplate
{
  protected static String nl;
  public static synchronized RemoteBusinessInterfaceTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    RemoteBusinessInterfaceTemplate result = new RemoteBusinessInterfaceTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl; //$NON-NLS-1$
  protected final String TEXT_1 = "package "; //$NON-NLS-1$
  protected final String TEXT_2 = ";"; //$NON-NLS-1$
  protected final String TEXT_3 = NL + "import javax.ejb.Remote;" + NL + "" + NL + "@Remote" + NL + "public interface "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
  protected final String TEXT_4 = " {" + NL + "" + NL + "}"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  protected final String TEXT_5 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     CreateSessionBeanTemplateModel model = (CreateSessionBeanTemplateModel) argument; 
    
	if (model.getBusinessInterfaceJavaPackageName() != null && model.getBusinessInterfaceJavaPackageName().length() > 0) {

    stringBuffer.append(TEXT_1);
    stringBuffer.append(model.getBusinessInterfaceJavaPackageName());
    stringBuffer.append(TEXT_2);
    
	}

    stringBuffer.append(TEXT_3);
    stringBuffer.append(model.getBusinessInterfaceClassName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}
