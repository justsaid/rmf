package org.eclipse.rmf.reqif10.pror.report.oda.ui.impl;
import java.util.Properties;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CustomPropertiesPage extends Composite {
	private Text text;
	WizardPage wizardPage;
	private CustomDataWizardPage dataSourceWizard;
	Properties propsCollector;
	
	public CustomPropertiesPage(Composite parent, int style, Properties propertyCollector)
	{
		super(parent, style);
	
		if (propertyCollector != null)
		{
			propsCollector = propertyCollector;
		}
		initLayout();
	}
	
	public CustomPropertiesPage(Composite parent, int style,
			CustomDataWizardPage customDataWizardPage) {
		super(parent, style);

		this.dataSourceWizard = customDataWizardPage;
		this.dataSourceWizard.setPageComplete(false);

		initLayout();
	}

	public void setDataSourceWizard(CustomDataWizardPage customDataWizardPage) {
		this.dataSourceWizard = customDataWizardPage;
	}

	private void initLayout() {
		Label lblFileName = new Label(this, 0);
		lblFileName.setBounds(10, 10, 55, 15);
		lblFileName.setText("File Name");

		this.text = new Text(this, 2048);
		this.text.setBounds(71, 10, 279, 21);

		final Properties props = null;
		if (this.dataSourceWizard != null) {
			propsCollector = this.dataSourceWizard.getDataSourceProperties();
		}
		
		if (this.propsCollector != null)
		{
			String doc = propsCollector.getProperty("ReqIF_file");
			this.text.setText(doc);
		}
	
		this.text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				Properties props = CustomPropertiesPage.this.propsCollector;

				if (props == null) {
					props = new Properties();
				}
				
				props.setProperty("ReqIF_file",
						CustomPropertiesPage.this.text.getText());

				if (CustomPropertiesPage.this.dataSourceWizard != null)
					CustomPropertiesPage.this.dataSourceWizard
							.setDataSourceProperties(props);
			}
		});
		Button btnBrowse = new Button(this, 0);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileSelectDialog = new FileDialog(
						CustomPropertiesPage.this.getShell());

				String fileName = fileSelectDialog.open();
				CustomPropertiesPage.this.text.setText(fileName);
			}
		});
		btnBrowse.setBounds(356, 10, 75, 25);
		btnBrowse.setText("Browse");

		if (this.dataSourceWizard != null) {
			Button btnSave = new Button(this, 0);
			btnSave.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (CustomPropertiesPage.this.dataSourceWizard != null)
						CustomPropertiesPage.this.dataSourceWizard
								.setPageComplete(true);
				}
			});
			btnSave.setBounds(356, 41, 75, 25);
			btnSave.setText("Save");
		}
	}
}
