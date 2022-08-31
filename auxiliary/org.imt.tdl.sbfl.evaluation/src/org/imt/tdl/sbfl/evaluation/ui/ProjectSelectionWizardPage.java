package org.imt.tdl.sbfl.evaluation.ui;

import java.awt.Font;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
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

public class ProjectSelectionWizardPage extends WizardPage{
	
	private Composite parent;
	private Group mutantProjectArea;
	private Group testProjectArea;
	
	private org.eclipse.swt.widgets.Combo mutantProjectCombo;
	private org.eclipse.swt.widgets.Combo testProjectCombo;
	private IProject mutantProject;
	private IProject testProject;
	
	protected ProjectSelectionWizardPage(String pageName) {
		super(pageName);
		super.setTitle("Running SBFL Techniques on Mutants");
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
		mutantProjectArea = createGroup(area, "Select a project containing mutants and their registries");
		testProjectArea = createGroup(area, "Select the project that contains test suites for the mutants");
		createMutantProjectLayout (mutantProjectArea, null);
		createTestProjectLayout (testProjectArea, null);
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
	
	public Composite createMutantProjectLayout(Composite parent, Font font) {
		mutantProjectCombo = new Combo(parent, SWT.NONE);
		mutantProjectCombo.setLayoutData(createStandardLayout());

		Set<String> projectNames = (Set<String>) getAllOpenProjects().keySet();
		String[] empty = {};
		mutantProjectCombo.setItems(projectNames.toArray(empty));
		mutantProjectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mutantProject = getAllOpenProjects().get(mutantProjectCombo.getText());
			}
		});	
		createTextLabelLayout(parent, "");
		return parent;
	}
	
	public Composite createTestProjectLayout(Composite parent, Font font) {
		testProjectCombo = new Combo(parent, SWT.NONE);
		testProjectCombo.setLayoutData(createStandardLayout());
		
		Set<String> projectNames = (Set<String>) getAllOpenProjects().keySet();
		String[] empty = {};
		testProjectCombo.setItems(projectNames.toArray(empty));
		testProjectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				testProject = getAllOpenProjects().get(testProjectCombo.getText());
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
	
	public HashMap<String, IProject> getAllOpenProjects() {
		HashMap<String, IProject> projects = new HashMap<>();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] tdlProjects = root.getProjects();
		for (int i=0; i<tdlProjects.length; i++) {
			if (tdlProjects[i].isAccessible()) {
				projects.put(tdlProjects[i].getName(), tdlProjects[i]);
			}
		}
		return projects;
	}
	
	public IProject getMutantProject() {
		return this.mutantProject;
	}
	
	public IProject getTestProject() {
		return this.testProject;
	}
}

