package org.eclipse.rmf.reqif10.pror.reporting.jasper.tests;

import java.math.BigInteger;

import org.eclipse.rmf.reqif10.AttributeDefinitionString;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.DatatypeDefinitionString;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.ReqIFContent;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.SpecObjectType;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.pror.configuration.Column;
import org.eclipse.rmf.reqif10.pror.configuration.ConfigurationFactory;
import org.eclipse.rmf.reqif10.pror.configuration.ProrSpecViewConfiguration;
import org.eclipse.rmf.reqif10.pror.util.ConfigurationUtil;

public class JasperReportTests extends AbstractJasperReportTests {

	private ReqIF createReqIF(int numRows) {

		ReqIF root = ReqIF10Factory.eINSTANCE.createReqIF();

		ReqIFContent content = ReqIF10Factory.eINSTANCE.createReqIFContent();
		root.setCoreContent(content);

		// Add a DatatypeDefinition
		DatatypeDefinitionString ddString = ReqIF10Factory.eINSTANCE
				.createDatatypeDefinitionString();
		ddString.setLongName("T_String32k");
		ddString.setMaxLength(new BigInteger("32000"));
		content.getDatatypes().add(ddString);

		// Add a SpecObjectType
		SpecObjectType specObjectType = ReqIF10Factory.eINSTANCE
				.createSpecObjectType();
		specObjectType.setLongName("Requirement Type");
		content.getSpecTypes().add(specObjectType);

		// Add an AttributeDefinition
		AttributeDefinitionString ad1 = ReqIF10Factory.eINSTANCE
				.createAttributeDefinitionString();
		ad1.setType(ddString);
		ad1.setLongName("Description");
		specObjectType.getSpecAttributes().add(ad1);

		// Add a Specification
		Specification spec = ReqIF10Factory.eINSTANCE.createSpecification();
		spec.setLongName("Specification Document");
		content.getSpecifications().add(spec);
		ProrSpecViewConfiguration config = ConfigurationUtil
				.createSpecViewConfiguration(spec, editingDomain);
		Column column = ConfigurationFactory.eINSTANCE.createColumn();
		column.setLabel("Description");
		config.getColumns().add(column);

		for (int i = 0; i < numRows; i++) {
			SpecHierarchy specH = ReqIF10Factory.eINSTANCE
					.createSpecHierarchy();
			SpecObject specObj = ReqIF10Factory.eINSTANCE.createSpecObject();
			specObj.setType(specObjectType);
			content.getSpecObjects().add(specObj);

			AttributeValueString value2 = ReqIF10Factory.eINSTANCE
					.createAttributeValueString();
			value2.setTheValue("Value-" + i);
			value2.setDefinition(ad1);
			specObj.getValues().add(value2);

			specH.setObject(specObj);
			spec.getChildren().add(specH);

			// ProrUtil.createAddTypedElementCommand(parent, childFeature,
			// newSpecElement, typeFeature, specType, index, resultIndex,
			// domain, adapterFactory);
		}
		return root;
	}
	
}
