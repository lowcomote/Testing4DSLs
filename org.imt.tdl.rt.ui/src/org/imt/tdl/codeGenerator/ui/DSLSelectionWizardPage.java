package org.imt.tdl.codeGenerator.ui;

import java.awt.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gemoc.xdsmlframework.api.extensions.languages.LanguageDefinitionExtensionPoint;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class DSLSelectionWizardPage extends WizardPage{
	private Composite parent;
	private Group tdlProjectArea;
	private Group languageArea;
	
	private org.eclipse.swt.widgets.Combo _projectCombo;
	private org.eclipse.swt.widgets.Combo _languageCombo;
	private IPath selectedProjectPath;
	private String selectedDSLPath;
	
	protected DSLSelectionWizardPage(String pageName) {
		super(pageName);
		super.setTitle("Generate TDL code from DSL definition");
	}

	@Override
	public void createControl(Composite parent) {
		this.parent = parent;
		Composite area = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout(1, false);
		gl.marginHeight = 0;
		area.setLayout(gl);
		area.layout();
		setControl(area);
		this.tdlProjectArea = createGroup(area, "Select a TDL Project");
		this.languageArea = createGroup(area, "Select a language");
		createProjectLayout (tdlProjectArea, null);
		createLanguageLayout (languageArea, null);
	}
	protected Group createGroup(Composite parent, String text) {
		Group group = new Group(parent, SWT.NULL);
		group.setText(text);
		GridLayout locationLayout = new GridLayout();
		locationLayout.numColumns = 3;
		locationLayout.marginHeight = 10;
		locationLayout.marginWidth = 10;
		group.setLayout(locationLayout);
		return group;
	}
	public Composite createProjectLayout(Composite parent, Font font) {
		createTextLabelLayout(parent, "TDL Projects");
		_projectCombo = new Combo(parent, SWT.NONE);
		_projectCombo.setLayoutData(createStandardLayout());

		Set<String> projectNames = (Set<String>) getAllTDLProjects().keySet();
		String[] empty = {};
		_projectCombo.setItems(projectNames.toArray(empty));
		_projectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedProjectPath = getAllTDLProjects().get(_projectCombo.getText());
			}
		});	
		createTextLabelLayout(parent, "");
		return parent;
	}
	public Composite createLanguageLayout(Composite parent, Font font) {
		// Language
		createTextLabelLayout(parent, "Languages");
		_languageCombo = new Combo(parent, SWT.NONE);
		_languageCombo.setLayoutData(createStandardLayout());
		
		final HashMap<String, String> languagesPaths = getAllLanguages();
		Set<String> languagesNames = (Set<String>) languagesPaths.keySet();
		String[] empty = {};
		_languageCombo.setItems(languagesNames.toArray(empty));
		_languageCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedDSLPath = languagesPaths.get(_languageCombo.getText());
				if (!selectedDSLPath.contains("platform:/plugin")) {
					selectedDSLPath = "platform:/plugin" + languagesPaths.get(_languageCombo.getText());
				}
			}
		});
		createTextLabelLayout(parent, "");

		return parent;
	}
	protected void createTextLabelLayout(Composite parent, String labelString) {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		parent.setLayoutData(gd);
		Label inputLabel = new Label(parent, SWT.NONE);
		inputLabel.setText(labelString); // $NON-NLS-1$
	}
	private GridData createStandardLayout() {
		return new GridData(SWT.FILL, SWT.CENTER, true, false);
	}
	public HashMap<String, String> getAllLanguages() {
		HashMap<String, String> languagesPaths = new HashMap<String, String>();

		IConfigurationElement[] languages = Platform
				.getExtensionRegistry().getConfigurationElementsFor(
						LanguageDefinitionExtensionPoint.GEMOC_LANGUAGE_EXTENSION_POINT);
		for (IConfigurationElement lang : languages) {
			String xdsmlPath = lang.getAttribute("xdsmlFilePath");
			String xdsmlName = lang.getAttribute("name");
			if (xdsmlPath.endsWith(".dsl")) {
				languagesPaths.put(xdsmlName, xdsmlPath);
			}
		}
		return languagesPaths;
	}
	public HashMap<String, IPath> getAllTDLProjects() {
		HashMap<String, IPath> projects = new HashMap<>();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] tdlProjects = root.getProjects();
		for (int i=0; i<tdlProjects.length; i++) {
			if (tdlProjects[i].isAccessible()) {
				projects.put(tdlProjects[i].getName(), tdlProjects[i].getFullPath());
			}
		}
		return projects;
	}
	public IPath getSelectedProjectPath() {
		return this.selectedProjectPath;
	}
	public String getSelectedDSLFilePath() {
		return this.selectedDSLPath;
	}
}
