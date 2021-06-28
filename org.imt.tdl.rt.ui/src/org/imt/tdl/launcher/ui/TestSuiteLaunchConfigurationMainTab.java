/*******************************************************************************
 * Copyright (c) 2016, 2017 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.imt.tdl.launcher.ui;

import java.util.ArrayList;



import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.commons.eclipse.ui.dialogs.SelectAnyIFileDialog;
import org.eclipse.gemoc.execution.eventBasedEngine.EventBasedRunConfiguration;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.ui.launcher.tabs.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.Activator;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.LauncherMessages;

public class TestSuiteLaunchConfigurationMainTab extends AbstractLaunchConfigurationTab {

	private final String implemRelId = "org.eclipse.gemoc.executionframework.event.implementationrelationship";

	private final String subtypeRelId = "org.eclipse.gemoc.executionframework.event.subtypingrelationship";

	private final List<IConfigurationElement> selectedImplementationRelationships = new ArrayList<>();

	private final List<IConfigurationElement> selectedSubtypingRelationships = new ArrayList<>();

	protected Composite _parent;

	protected Text _mutLocationText;
	protected Combo _languageCombo;
	private org.eclipse.swt.widgets.List _implementationRelationshipList;
	private org.eclipse.swt.widgets.List _subtypingRelationshipList;

	public int gridDefaultWidth = 200;

	@Override
	public void createControl(Composite parent) {
		_parent = parent;
		Composite area = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout(1, false);
		gl.marginHeight = 0;
		area.setLayout(gl);
		area.layout();
		setControl(area);

		Group modelArea = createGroup(area, "Model Under Test:");
		createModelLayout(modelArea, null);

		Group languageArea = createGroup(area, "Language:");
		createLanguageLayout(languageArea, null);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("MUT_URI", "");
		configuration.setAttribute("LANGUAGE_NAME", "");
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			_mutLocationText.setText(configuration.getAttribute("MUT_URI", ""));
			_languageCombo.setText(configuration.getAttribute("LANGUAGE_NAME", ""));
			configuration.getAttribute(EventBasedRunConfiguration.IMPL_REL_IDS, Collections.emptySet()).stream()
					.forEach(id -> {
						final IConfigurationElement[] relationships = Platform.getExtensionRegistry()
								.getConfigurationElementsFor(implemRelId);
						Arrays.asList(relationships).stream().filter(r -> r.getAttribute("id").equals(id))
								.forEach(r -> {
									_implementationRelationshipList.add(r.getAttribute("name"));
									selectedImplementationRelationships.add(r);
								});
					});

			configuration.getAttribute(EventBasedRunConfiguration.SUBTYPE_REL_IDS, Collections.emptySet()).stream()
					.forEach(id -> {
						final IConfigurationElement[] relationships = Platform.getExtensionRegistry()
								.getConfigurationElementsFor(subtypeRelId);
						Arrays.asList(relationships).stream().filter(r -> r.getAttribute("id").equals(id))
								.forEach(r -> {
									_subtypingRelationshipList.add(r.getAttribute("name"));
									selectedSubtypingRelationships.add(r);
								});
					});
		} catch (CoreException e) {
			Activator.error(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("MUT_URI", _mutLocationText.getText());
		configuration.setAttribute("LANGUAGE_NAME", _languageCombo.getText());
		configuration.setAttribute(EventBasedRunConfiguration.IMPL_REL_IDS, selectedImplementationRelationships.stream()
				.map(r -> r.getAttribute("id")).collect(Collectors.toSet()));
		configuration.setAttribute(EventBasedRunConfiguration.SUBTYPE_REL_IDS,
				selectedSubtypingRelationships.stream().map(r -> r.getAttribute("id")).collect(Collectors.toSet()));
	}

	@Override
	public String getName() {
		return "Main";
	}

	// -----------------------------------

	private ModifyListener fBasicModifyListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent arg0) {
			updateLaunchConfigurationDialog();
		}
	};

	// -----------------------------------

	public Composite createModelLayout(Composite parent, Font font) {
		createTextLabelLayout(parent, "Model to execute");
		// Model location text
		_mutLocationText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		_mutLocationText.setLayoutData(createStandardLayout());
		_mutLocationText.setFont(font);
		_mutLocationText.addModifyListener(fBasicModifyListener);
		Button modelLocationButton = createPushButton(parent, "Browse", null);
		modelLocationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				SelectAnyIFileDialog dialog = new SelectAnyIFileDialog();
				if (dialog.open() == Dialog.OK) {
					String modelPath = ((IResource) dialog.getResult()[0]).getFullPath().toPortableString();
					_mutLocationText.setText(modelPath);
					updateLaunchConfigurationDialog();
				}
			}
		});
		createTextLabelLayout(parent, "");

		return parent;
	}

	private GridData createStandardLayout() {
		return new GridData(SWT.FILL, SWT.CENTER, true, false);
	}

	public Composite createLanguageLayout(Composite parent, Font font) {
		// Language
		createTextLabelLayout(parent, "Languages");
		_languageCombo = new Combo(parent, SWT.READ_ONLY);
		_languageCombo.setLayoutData(createStandardLayout());

		List<String> languagesNames = DslHelper.getAllLanguages();
		String[] empty = {};
		_languageCombo.setItems(languagesNames.toArray(empty));
		_languageCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		createTextLabelLayout(parent, "");

		_implementationRelationshipList = new org.eclipse.swt.widgets.List(parent, SWT.V_SCROLL);
		_implementationRelationshipList.setLayoutData(new GridData(GridData.FILL_BOTH));
		_implementationRelationshipList.setFont(parent.getFont());

		final Button implementationRelationshipsBrowseButton = createPushButton(parent, "Browse", null);
		implementationRelationshipsBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (_languageCombo.getText() == null) {
					setErrorMessage("Please select a language.");
				} else {
					final ElementListSelectionDialog dialog = getRelationshipSelectionDialog(implemRelId,
							selectedImplementationRelationships);
					int res = dialog.open();
					if (res == WizardDialog.OK) {
						selectedImplementationRelationships.clear();
						_implementationRelationshipList.removeAll();
						Arrays.asList(dialog.getResult()).stream().filter(o -> o instanceof IConfigurationElement)
								.forEach(o -> {
									final IConfigurationElement r = (IConfigurationElement) o;
									selectedImplementationRelationships.add(r);
									_implementationRelationshipList.add((r).getAttribute("name"));
								});
						updateLaunchConfigurationDialog();
					}
				}
			}
		});

		new Label(parent, SWT.NONE).setText("");

		_subtypingRelationshipList = new org.eclipse.swt.widgets.List(parent, SWT.V_SCROLL);
		_subtypingRelationshipList.setLayoutData(new GridData(GridData.FILL_BOTH));
		_subtypingRelationshipList.setFont(parent.getFont());

		final Button subtypingRelationshipsBrowseButton = createPushButton(parent, "Browse", null);
		subtypingRelationshipsBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (_languageCombo.getText() == null) {
					setErrorMessage("Please select a language.");
				} else {
					final ElementListSelectionDialog dialog = getRelationshipSelectionDialog(subtypeRelId,
							selectedSubtypingRelationships);
					int res = dialog.open();
					if (res == WizardDialog.OK) {
						selectedSubtypingRelationships.clear();
						_subtypingRelationshipList.removeAll();
						Arrays.asList(dialog.getResult()).stream().filter(o -> o instanceof IConfigurationElement)
								.forEach(o -> {
									final IConfigurationElement r = (IConfigurationElement) o;
									selectedSubtypingRelationships.add(r);
									_subtypingRelationshipList.add((r).getAttribute("name"));
								});
						updateLaunchConfigurationDialog();
					}
				}
			}
		});

		new Label(parent, SWT.NONE).setText("");

		return parent;
	}

	private ElementListSelectionDialog getRelationshipSelectionDialog(String extensionPointId,
			List<IConfigurationElement> preselected) {
		final IConfigurationElement[] relationships = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(extensionPointId);
		final ILabelProvider labelProvider = new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IConfigurationElement) {
					return ((IConfigurationElement) element).getAttribute("name");
				}
				return "";
			};
		};

		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), labelProvider);
		dialog.setElements(relationships);
		dialog.setMultipleSelection(true);
		dialog.setInitialSelections(preselected);

		return dialog;
	}

	@Override
	protected void updateLaunchConfigurationDialog() {
		super.updateLaunchConfigurationDialog();
	}

	private Resource currentModelResource;

	private Resource getModel() {
		URI modelURI = URI.createPlatformResourceURI(_mutLocationText.getText(), true);
		if (currentModelResource == null || !currentModelResource.getURI().equals(modelURI)) {
			currentModelResource = PlainK3ExecutionEngine.loadModel(modelURI);
		}
		return currentModelResource;
	}

	@Override
	public boolean isValid(ILaunchConfiguration config) {
		setErrorMessage(null);
		setMessage(null);
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String modelName = _mutLocationText.getText().trim();
		if (modelName.length() > 0) {

			IResource modelIResource = workspace.getRoot().findMember(modelName);
			if (modelIResource == null || !modelIResource.exists()) {
				setErrorMessage(
						NLS.bind(LauncherMessages.SequentialMainTab_model_doesnt_exist, new String[] { modelName }));
				return false;
			}
			if (modelName.equals("/")) {
				setErrorMessage(LauncherMessages.SequentialMainTab_Model_not_specified);
				return false;
			}
			if (!(modelIResource instanceof IFile)) {
				setErrorMessage(
						NLS.bind(LauncherMessages.SequentialMainTab_invalid_model_file, new String[] { modelName }));
				return false;
			}
		}
		if (modelName.length() == 0) {
			setErrorMessage(LauncherMessages.SequentialMainTab_Model_not_specified);
			return false;
		}

		String languageName = _languageCombo.getText().trim();
		if (languageName.length() == 0) {
			setErrorMessage(LauncherMessages.SequentialMainTab_Language_not_specified);
			return false;
		}

		return true;
	}
}
