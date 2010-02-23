package org.eclipse.jst.j2ee.ejb.internal.operations;

import java.util.*;
import org.eclipse.jst.j2ee.internal.common.operations.*;

public class SessionBeanTemplate
{
  protected static String nl;
  public static synchronized SessionBeanTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    SessionBeanTemplate result = new SessionBeanTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl; //$NON-NLS-1$
  protected final String TEXT_1 = "package "; //$NON-NLS-1$
  protected final String TEXT_2 = ";"; //$NON-NLS-1$
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL + "import "; //$NON-NLS-1$
  protected final String TEXT_5 = ";"; //$NON-NLS-1$
  protected final String TEXT_6 = NL;
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = "/**" + NL + " * Session Bean implementation class "; //$NON-NLS-1$ //$NON-NLS-2$
  protected final String TEXT_9 = NL + " */"; //$NON-NLS-1$
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = "("; //$NON-NLS-1$
  protected final String TEXT_12 = ", "; //$NON-NLS-1$
  protected final String TEXT_13 = " = \""; //$NON-NLS-1$
  protected final String TEXT_14 = "\""; //$NON-NLS-1$
  protected final String TEXT_15 = ")"; //$NON-NLS-1$
  protected final String TEXT_16 = NL + "@TransactionManagement(TransactionManagementType.BEAN)"; //$NON-NLS-1$
  protected final String TEXT_17 = NL + "@Local( { "; //$NON-NLS-1$
  protected final String TEXT_18 = ", "; //$NON-NLS-1$
  protected final String TEXT_19 = ".class"; //$NON-NLS-1$
  protected final String TEXT_20 = " })"; //$NON-NLS-1$
  protected final String TEXT_21 = NL + "@Remote( { "; //$NON-NLS-1$
  protected final String TEXT_22 = ", "; //$NON-NLS-1$
  protected final String TEXT_23 = ".class"; //$NON-NLS-1$
  protected final String TEXT_24 = " })"; //$NON-NLS-1$
  protected final String TEXT_25 = NL + "@LocalBean"; //$NON-NLS-1$
  protected final String TEXT_26 = NL + "@LocalHome("; //$NON-NLS-1$
  protected final String TEXT_27 = ".class)"; //$NON-NLS-1$
  protected final String TEXT_28 = NL + "@RemoteHome("; //$NON-NLS-1$
  protected final String TEXT_29 = ".class)"; //$NON-NLS-1$
  protected final String TEXT_30 = NL + "public "; //$NON-NLS-1$
  protected final String TEXT_31 = "abstract "; //$NON-NLS-1$
  protected final String TEXT_32 = "final "; //$NON-NLS-1$
  protected final String TEXT_33 = "class "; //$NON-NLS-1$
  protected final String TEXT_34 = " extends "; //$NON-NLS-1$
  protected final String TEXT_35 = " implements "; //$NON-NLS-1$
  protected final String TEXT_36 = ", "; //$NON-NLS-1$
  protected final String TEXT_37 = " {"; //$NON-NLS-1$
  protected final String TEXT_38 = NL + NL + "    /**" + NL + "     * Default constructor. " + NL + "     */" + NL + "    public "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
  protected final String TEXT_39 = "() {" + NL + "        // TODO Auto-generated constructor stub" + NL + "    }";  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
  protected final String TEXT_40 = NL + "       " + NL + "    /**" + NL + "     * @see "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  protected final String TEXT_41 = "#"; //$NON-NLS-1$
  protected final String TEXT_42 = "("; //$NON-NLS-1$
  protected final String TEXT_43 = ")" + NL + "     */" + NL + "    public "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  protected final String TEXT_44 = "("; //$NON-NLS-1$
  protected final String TEXT_45 = ") {" + NL + "        super("; //$NON-NLS-1$ //$NON-NLS-2$
  protected final String TEXT_46 = ");" + NL + "        // TODO Auto-generated constructor stub" + NL + "    }";  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
  protected final String TEXT_47 = NL + NL + "\t/**" + NL + "     * @see ";  //$NON-NLS-1$//$NON-NLS-2$
  protected final String TEXT_48 = "#"; //$NON-NLS-1$
  protected final String TEXT_49 = "("; //$NON-NLS-1$
  protected final String TEXT_50 = ")" + NL + "     */" + NL + "    public "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  protected final String TEXT_51 = " "; //$NON-NLS-1$
  protected final String TEXT_52 = "("; //$NON-NLS-1$
  protected final String TEXT_53 = ") {" + NL + "        // TODO Auto-generated method stub"; //$NON-NLS-1$ //$NON-NLS-2$
  protected final String TEXT_54 = NL + "\t\t\treturn "; //$NON-NLS-1$
  protected final String TEXT_55 = ";"; //$NON-NLS-1$
  protected final String TEXT_56 = NL + "    }"; //$NON-NLS-1$
  protected final String TEXT_57 = NL + NL + "}"; //$NON-NLS-1$
  protected final String TEXT_58 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     CreateSessionBeanTemplateModel model = (CreateSessionBeanTemplateModel) argument; 
    
	model.removeFlags(CreateJavaEEArtifactTemplateModel.FLAG_QUALIFIED_SUPERCLASS_NAME); 

    
	if (model.getJavaPackageName() != null && model.getJavaPackageName().length() > 0) {

    stringBuffer.append(TEXT_1);
    stringBuffer.append( model.getJavaPackageName() );
    stringBuffer.append(TEXT_2);
    
	}

    stringBuffer.append(TEXT_3);
     
	Collection<String> imports = model.getImports();
	for (String anImport : imports) { 

    stringBuffer.append(TEXT_4);
    stringBuffer.append( anImport );
    stringBuffer.append(TEXT_5);
     
	}

    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    stringBuffer.append( model.getClassName() );
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append( model.getClassAnnotation() );
    
	Map<String, String> params = model.getClassAnnotationParams();
	if (!params.isEmpty()) { 

    stringBuffer.append(TEXT_11);
    
		Set<String> keys = params.keySet();
		boolean needComma = false;
		for (String key : keys) {
			if (needComma) {

    stringBuffer.append(TEXT_12);
    
			}
			
			String value = params.get(key);

    stringBuffer.append( key );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( value );
    stringBuffer.append(TEXT_14);
    
			needComma = true;
  		}

    stringBuffer.append(TEXT_15);
    
	}

	if (!model.isContainerType()) {

    stringBuffer.append(TEXT_16);
    
	}

	List<BusinessInterface> localInterfaces = model.getExistingLocalBusinessInterfaces();
	if (!localInterfaces.isEmpty()) {

    stringBuffer.append(TEXT_17);
    
		boolean needComma = false;
		for (BusinessInterface iface : localInterfaces) {
			if (needComma) {

    stringBuffer.append(TEXT_18);
    
			}

    stringBuffer.append( iface.getSimpleName() );
    stringBuffer.append(TEXT_19);
    
			needComma = true;
 		}

    stringBuffer.append(TEXT_20);
    
	}

	List<BusinessInterface> remoteInterfaces = model.getExistingRemoteBusinessInterfaces();
	if (!remoteInterfaces.isEmpty()) {

    stringBuffer.append(TEXT_21);
    
		boolean needComma = false;
		for (BusinessInterface iface : remoteInterfaces) {
			if (needComma) {

    stringBuffer.append(TEXT_22);
    
			}

    stringBuffer.append( iface.getSimpleName() );
    stringBuffer.append(TEXT_23);
    
			needComma = true;
		}

    stringBuffer.append(TEXT_24);
    
	}
	
	if (model.isNoInterfaceChecked()) {

    stringBuffer.append(TEXT_25);
    
	}
		
	if (model.isLocalHomeChecked()) {

    stringBuffer.append(TEXT_26);
    stringBuffer.append(model.getLocalHomeClassSimpleName());
    stringBuffer.append(TEXT_27);
    
	}

	if (model.isRemoteHomeChecked()) {

    stringBuffer.append(TEXT_28);
    stringBuffer.append(model.getRemoteHomeClassSimpleName());
    stringBuffer.append(TEXT_29);
    
	}

    
	if (model.isPublic()) { 

    stringBuffer.append(TEXT_30);
     
	} 

	if (model.isAbstract()) { 

    stringBuffer.append(TEXT_31);
    
	}

	if (model.isFinal()) {

    stringBuffer.append(TEXT_32);
    
	}

    stringBuffer.append(TEXT_33);
    stringBuffer.append( model.getClassName() );
    
	String superClass = model.getSuperclassName();
 	if (superClass != null && superClass.length() > 0) {

    stringBuffer.append(TEXT_34);
    stringBuffer.append( superClass );
    
	}

	List<String> interfaces = model.getInterfaces(); 
 	if ( interfaces.size() > 0) { 

    stringBuffer.append(TEXT_35);
    
	}
	
 	for (int i = 0; i < interfaces.size(); i++) {
   		String INTERFACE = interfaces.get(i);
   		if (i > 0) {

    stringBuffer.append(TEXT_36);
    
		}

    stringBuffer.append( INTERFACE );
    
	}

    stringBuffer.append(TEXT_37);
     
	if (!model.hasEmptySuperclassConstructor()) { 

    stringBuffer.append(TEXT_38);
    stringBuffer.append( model.getClassName() );
    stringBuffer.append(TEXT_39);
     
	} 

	if (model.shouldGenSuperclassConstructors()) {
		List<Constructor> constructors = model.getConstructors();
		for (Constructor constructor : constructors) {
			if (constructor.isPublic() || constructor.isProtected()) { 

    stringBuffer.append(TEXT_40);
    stringBuffer.append( model.getSuperclassName() );
    stringBuffer.append(TEXT_41);
    stringBuffer.append( model.getSuperclassName() );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( constructor.getParamsForJavadoc() );
    stringBuffer.append(TEXT_43);
    stringBuffer.append( model.getClassName() );
    stringBuffer.append(TEXT_44);
    stringBuffer.append( constructor.getParamsForDeclaration() );
    stringBuffer.append(TEXT_45);
    stringBuffer.append( constructor.getParamsForCall() );
    stringBuffer.append(TEXT_46);
    
			} 
		} 
	} 

    
	if (model.shouldImplementAbstractMethods()) {
		for (Method method : model.getUnimplementedMethods()) { 

    stringBuffer.append(TEXT_47);
    stringBuffer.append( method.getContainingJavaClass() );
    stringBuffer.append(TEXT_48);
    stringBuffer.append( method.getName() );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( method.getParamsForJavadoc() );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( method.getReturnType() );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( method.getName() );
    stringBuffer.append(TEXT_52);
    stringBuffer.append( method.getParamsForDeclaration() );
    stringBuffer.append(TEXT_53);
     
			String defaultReturnValue = method.getDefaultReturnValue();
			if (defaultReturnValue != null) { 

    stringBuffer.append(TEXT_54);
    stringBuffer.append( defaultReturnValue );
    stringBuffer.append(TEXT_55);
    
			} 

    stringBuffer.append(TEXT_56);
     
		}
	} 

    stringBuffer.append(TEXT_57);
    stringBuffer.append(TEXT_58);
    return stringBuffer.toString();
  }
}
