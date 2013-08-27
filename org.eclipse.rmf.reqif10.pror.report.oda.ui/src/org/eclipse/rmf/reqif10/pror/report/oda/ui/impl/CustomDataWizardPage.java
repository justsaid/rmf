package org.eclipse.rmf.reqif10.pror.report.oda.ui.impl;
import java.util.Properties;
import org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSourceWizardPage;
import org.eclipse.swt.widgets.Composite;

public class CustomDataWizardPage extends DataSourceWizardPage {
	private Properties dataSourceProperties;

	public CustomDataWizardPage(String pageName) {
		super(pageName);
	}

	public Properties collectCustomProperties() {
		if (this.dataSourceProperties == null) {
			return new Properties();
		}
		return this.dataSourceProperties;
	}

	public void createPageCustomControl(Composite inComposite) {
		new CustomPropertiesPage(inComposite, 0, this);
	}

	public void setInitialProperties(Properties dataSourceProperties) {
		setDataSourceProperties(dataSourceProperties);
	}

	public void setDataSourceProperties(Properties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}

	public Properties getDataSourceProperties() {
		return this.dataSourceProperties;
	}
}
