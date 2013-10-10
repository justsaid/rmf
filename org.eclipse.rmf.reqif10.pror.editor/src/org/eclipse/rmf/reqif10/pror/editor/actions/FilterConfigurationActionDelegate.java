/*******************************************************************************
 * Copyright (c) 2011 Formal Mind GmbH and University of Dusseldorf.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Said Salem - initial API and implementation
 ******************************************************************************/
package org.eclipse.rmf.reqif10.pror.editor.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.rmf.reqif10.AttributeDefinition;
import org.eclipse.rmf.reqif10.AttributeDefinitionEnumeration;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.EnumValue;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.ReqIFToolExtension;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;
import org.eclipse.rmf.reqif10.impl.AttributeValueEnumerationImpl;
import org.eclipse.rmf.reqif10.pror.configuration.ConfigurationFactory;
import org.eclipse.rmf.reqif10.pror.configuration.ConfigurationPackage;
import org.eclipse.rmf.reqif10.pror.configuration.ProrDefaultFilter;
import org.eclipse.rmf.reqif10.pror.configuration.ProrFilterConfiguration;
import org.eclipse.rmf.reqif10.pror.configuration.ProrGeneralConfiguration;
import org.eclipse.rmf.reqif10.pror.configuration.ProrPresentationConfigurations;
import org.eclipse.rmf.reqif10.pror.configuration.ProrSpecViewConfiguration;
import org.eclipse.rmf.reqif10.pror.configuration.ProrToolExtension;
import org.eclipse.rmf.reqif10.pror.editor.presentation.Reqif10Editor;
import org.eclipse.rmf.reqif10.pror.editor.presentation.ReqifSpecificationEditorInput;
import org.eclipse.rmf.reqif10.pror.editor.presentation.SpecificationEditor;
import org.eclipse.rmf.reqif10.pror.util.ConfigurationUtil;
import org.eclipse.rmf.reqif10.pror.util.ProrUtil;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class FilterConfigurationActionDelegate implements IEditorActionDelegate {

	private SpecificationEditor editor;
	private Specification spec;
	private ProrToolExtension toolConfig;

	/**
	 * Retrieves the {@link EditingDomain} from the Editor if present.
	 */
	public void setActiveEditor(IAction action, IEditorPart editor) {
		if (editor instanceof SpecificationEditor) {
			this.editor = (SpecificationEditor) editor;
		} else {
			this.editor = null;
		}
	}

	/**
	 * Opens the {@link ReqIFToolExtension} for the current
	 * {@link EditingDomain}.
	 */
	public void run(IAction action) {
		if (editor == null)
			return;
		ReqIF reqif = (ReqIF) editor.getEditingDomain().getResourceSet()
				.getResources().get(0).getContents().get(0);

		Object input = editor.getEditorInput();
	
		
		spec = ((ReqifSpecificationEditorInput) input)
				.getSpec();
		// ProrToolExtension toolConfig = ConfigurationUtil
		// .createProrToolExtension(reqif, editor.getEditingDomain());
		// EList<ProrFilterConfiguration> configs = toolConfig
		// .getFilterConfigurations();
		// for (final ProrToolExtension config : configs) {
		// launchColumnDialog(config);
		// return;
		// }

		// We didn't find a configuration yet - create one.
		// ProrDefaultFilter defaultFilter =
		// ConfigurationFactory.eINSTANCE.create

		// defaultFilter.setName("Filter1");
		toolConfig = ConfigurationUtil
				.createProrToolExtension(reqif, editor.getEditingDomain());
		launchFilterDialog(toolConfig);
		
//		toolConfig.getSpecViewConfigurations().get(0).getSpecification();


		// SubtreeDialog dialog = new SubtreeDialog(editor, defaultFilter,
		// "Filter Configuration",
		// "org.eclipse.rmf.reqif10.pror.editor.FilterConfiguration");

		// dialog.addFilter(new ViewerFilter() {
		//
		// @Override
		// public boolean select(Viewer viewer, Object parentElement,
		// Object element) {
		// return !(element instanceof VirtualSpecificationsItemProvider
		// || element instanceof VirtualSpecObjectItemProvider
		// || element instanceof VirtualSpecRelationsItemProvider
		// || element instanceof VirtualSpecRelationGroupItemProvider);
		// }
		// });

		// dialog.open();
		
		
	}

	private Specification createFilteredSpec(ProrDefaultFilter filter,
			Specification oldSpec) {
		
		
		String regex = filter.getRegexp();
		AttributeDefinition attrDef = filter.getAttribute();
		
		Specification newSpec = EcoreUtil.copy(oldSpec);
		filterRecursive(newSpec.getChildren(), attrDef, regex);
		return newSpec;
	}

	private static void filterRecursive(EList<SpecHierarchy> children,
			AttributeDefinition attrDef, String regex) {
		Pattern pattern = Pattern.compile(regex);
//		EList<SpecHierarchy> filteredSpecHierarchies = (EList<SpecHierarchy>) EcoreUtil
//				.copyAll(children);

		for (SpecHierarchy child : children) {
			if (child.getObject() != null) {
				SpecObject specObject = child.getObject();

				AttributeValue av = ReqIF10Util.getAttributeValue(specObject,
						attrDef);
				if (av != null) {
					String value = ReqIF10Util.getTheValueString(av);
//					String avStr = value.toString();
					
					
					Matcher matcher = pattern.matcher(value);
					System.err.println(matcher.matches());
					if (!matcher.matches()) {
						child.setObject(null);
//						children.remove(specObject);
//						System.err.println("removed: "+children.remove(specObject));
					}
				}
				else {
					child.setObject(null);
				}
			}
			filterRecursive(child.getChildren(), attrDef, regex);
		}
		
//		 Specification newSpec = ReqIF10Factory.eINSTANCE.createSpecification();
//		 newSpec.getChildren().addAll(children);
		 
	}
	
	private void openSpec(Specification spec) {
		try {
			ProrSpecViewConfiguration specViewConf = ConfigurationUtil.createSpecViewConfiguration(this.spec, editor.getEditingDomain());
			ProrSpecViewConfiguration newSpecViewConf = EcoreUtil.copy(specViewConf);
			newSpecViewConf.setSpecification(spec);
			toolConfig.getSpecViewConfigurations().add(newSpecViewConf);
			
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			ReqifSpecificationEditorInput editorInput = new ReqifSpecificationEditorInput(
					editor.getReqifEditor(), spec);
			IDE.openEditor(page, editorInput, SpecificationEditor.EDITOR_ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		
	}

	private void launchFilterDialog(final ProrToolExtension config) {

		SubtreeDialog dialog = new SubtreeDialog(editor.getReqifEditor(), config,
				"Configure a Filter",
				"org.eclipse.rmf.reqif10.pror.editor.FilterConfiguration") {

			@Override
			protected void createButtonsForButtonBar(Composite parent) {
				// TODO Auto-generated method stub
				super.createButtonsForButtonBar(parent);

				createButton(parent, 11, "Preview", false);
			}

			@Override
			protected void buttonPressed(int buttonId) {
				if (buttonId == 11) {
					EList<ProrFilterConfiguration> filters = toolConfig.getFilterConfigurations();
					Specification newSpec = createFilteredSpec((ProrDefaultFilter)filters.get(0), spec);
					editor.getReqifEditor().getReqif().getCoreContent().getSpecifications().add(newSpec);
					
					
					close();
					openSpec(newSpec);
					
				} else {
					super.buttonPressed(buttonId);
				}

			}

		};

		dialog.setActions(new IAction[] { buildAddFilterAction(config) }, false);
		dialog.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				return !(element instanceof ProrSpecViewConfiguration
						|| element instanceof ProrGeneralConfiguration || element instanceof ProrPresentationConfigurations);
			}
		});

		dialog.open();
		return;
	}

	/**
	 * Creates a new Filter. If no Filters are present, this is the only way to
	 * add Filters.
	 */
	private IAction buildAddFilterAction(final ProrToolExtension config) {
		IAction addFilterAction = new Action("Add Filter") {
			@Override
			public void run() {
				// This is a compound command: We resize all existing columns to
				// squeeze in the new one
				CompoundCommand compoundCmd = new CompoundCommand("Add Filter");
				// int shrink = 0;
				// if (config.getColumns().size() > 0)
				// shrink = 100 / config.getColumns().size();

				// for (Column column : config.getColumns()) {
				// int newWidth = column.getWidth() - shrink;
				// Make sure Columns are at least 20 pixel, so they don't
				// disappear
				// if (newWidth < 20)
				// newWidth = 100;
				// Command command = SetCommand.create(editor
				// .getEditingDomain(), column,
				// ConfigurationPackage.Literals.COLUMN__WIDTH, newWidth);
				// compoundCmd.append(command);
				// }

				// TODO:via extension point -> getting more filters

				Command command = AddCommand
						.create(editor.getEditingDomain(),
								config,
								ConfigurationPackage.Literals.PROR_TOOL_EXTENSION__FILTER_CONFIGURATIONS,
								ConfigurationFactory.eINSTANCE
										.createProrDefaultFilter());
				// compoundCmd.append(command);
				editor.getEditingDomain().getCommandStack().execute(command);

			}
		};

		ImageDescriptor imgDescriptor = AbstractUIPlugin
				.imageDescriptorFromPlugin(
						"org.eclipse.rmf.reqif10.pror.editor.editor",
						"icons/full/obj16/AddColumn.png");
		addFilterAction.setImageDescriptor(imgDescriptor);
		return addFilterAction;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// No action required.
	}

}
