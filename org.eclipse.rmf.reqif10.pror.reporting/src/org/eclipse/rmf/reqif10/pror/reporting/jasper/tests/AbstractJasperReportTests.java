package org.eclipse.rmf.reqif10.pror.reporting.jasper.tests;

import static org.junit.Assert.*;

import java.net.URISyntaxException;

import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.common.util.ReqIFToolExtensionUtil;
import org.eclipse.rmf.reqif10.pror.configuration.ConfigurationFactory;
import org.eclipse.rmf.reqif10.pror.configuration.ProrSpecViewConfiguration;
import org.eclipse.rmf.reqif10.pror.configuration.ProrToolExtension;
import org.eclipse.rmf.reqif10.pror.editor.agilegrid.ProrAgileGridContentProvider;
import org.eclipse.rmf.reqif10.pror.testframework.AbstractItemProviderTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link ProrAgileGridContentProvider}
 * 
 * @author salem
 */
public abstract class AbstractJasperReportTests extends AbstractItemProviderTest {

	protected ProrAgileGridContentProvider contentProvider;
	protected Specification specification;
	protected ProrSpecViewConfiguration specViewConfig;
	protected ReqIF reqif;
	protected SpecObject specObject;
	protected SpecHierarchy specHierarchy;

	@Before
	public void setupTestProrAgileGridContentProvider()
			throws URISyntaxException {
		reqif = this.getTestReqif("simple.reqif");
		specification = reqif.getCoreContent().getSpecifications().get(0);
		specObject = reqif.getCoreContent().getSpecObjects().get(0);
		specHierarchy = specification.getChildren().get(0);
		
		
		// Build up the data structures that hold specViewConfig
		ProrToolExtension prorToolExtension = ConfigurationFactory.eINSTANCE.createProrToolExtension();
		specViewConfig = ConfigurationFactory.eINSTANCE.createProrSpecViewConfiguration();
		prorToolExtension.getSpecViewConfigurations().add(specViewConfig);
		ReqIFToolExtensionUtil.addToolExtension(reqif, prorToolExtension);
		
		contentProvider = new ProrAgileGridContentProvider(specification, specViewConfig);
	}
	
	@After
	public void teardownTestProrAgileGridContentProvidert() {
		reqif = null;
		specification = null;
		specViewConfig = null;
		contentProvider = null;
	}
	
	
	@Test
	public void testInitialRowCount() {
		assertEquals(1, contentProvider.getRowCount());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testNonexistingRow() {
		contentProvider.getContentAt(1, 0);
	}

	@Test
	public void testSpecViewConfigContent() {
		assertEquals(0, specViewConfig.getColumns().size());
	}

	@Test
	public void testInitialCellValue() {
		assertEquals(specification.getChildren().get(0).getObject(),
				contentProvider.getContentAt(0, 0));
	}

}
