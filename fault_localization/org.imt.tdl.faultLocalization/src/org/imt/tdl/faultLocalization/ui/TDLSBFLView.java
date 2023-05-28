package org.imt.tdl.faultLocalization.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.imt.tdl.coverage.computation.TDLCoverageUtil;
import org.imt.tdl.faultLocalization.SBFLMeasures;
import org.imt.tdl.faultLocalization.SuspiciousnessRanking;
import org.imt.tdl.testResult.TDLTestResultUtil;

public class TDLSBFLView extends ViewPart{
	
	public static final String ID = "rt.ui.sbflView"; //$NON-NLS-1$
	
	private TreeViewer m_treeViewer;
	
	private static final Color RED = new Color(Display.getCurrent(), 255, 102, 102);

	private static final Color GREEN = new Color(Display.getCurrent(), 102, 255, 102);
	
	private static int elementFilterIndex = -1;
	private static int techniqueFilterIndex = -1;
	private static List<String> classFilters = new ArrayList<>();
	
	@Override
	public void createPartControl(Composite parent) {
		if (!TDLCoverageUtil.getInstance().getTestSuiteCoverage().isCoverageComputed()) {
			TDLCoverageUtil.getInstance().runCoverageComputation();
		}
		SuspiciousnessRanking suspComputing = new SuspiciousnessRanking();
		suspComputing.calculateMeasures();
		
		Composite contents = new Group(parent, SWT.NULL);
	    GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		contents.setLayout(layout);
	    GridData gd = new GridData();
	    contents.setLayoutData(gd);
	    
	    Group filter = new Group(contents, SWT.NULL);
	    layout = new GridLayout();
	    filter.setLayout(layout);
		filter.setText("Filters");
		gd = new GridData();
		gd.verticalAlignment = SWT.FILL;
		filter.setLayoutData(gd);
		
        Group elementFilter = new Group(filter, SWT.NULL);
	    layout = new GridLayout();
	    elementFilter.setLayout(layout);
	    elementFilter.setText("Type of Model Element");
		gd = new GridData();
		gd.verticalAlignment = SWT.ON_TOP;
		gd.widthHint = 250;
		elementFilter.setLayoutData(gd);
        final Combo elementFilterCombo = new Combo(elementFilter, SWT.NONE);
        elementFilterCombo.add("All");
        //add the meta-classes included in the coverage information as filter
        List<SBFLMeasures> elementsSBFLMeasures = suspComputing.getElementsSBFLMeasures();
        Set<EClass> metaClasses = new HashSet<EClass>();
        classFilters.clear();
        for (SBFLMeasures parameter: elementsSBFLMeasures) {
        	metaClasses.add(parameter.getMetaclass());      	
        }
        metaClasses.remove(null);
        for (EClass metaClass: metaClasses) {
        	classFilters.add(metaClass.getName());
        	elementFilterCombo.add(metaClass.getName());
        }
        elementFilterCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				elementFilterIndex = elementFilterCombo.getSelectionIndex();
				m_treeViewer.collapseAll();
				m_treeViewer.refresh();
			}
		});
		
        Group techniqueFilter = new Group(filter, SWT.NULL);
	    layout = new GridLayout();
	    techniqueFilter.setLayout(layout);
	    techniqueFilter.setText("SBFL Technique");
		gd = new GridData();
		gd.verticalAlignment = SWT.ON_TOP;
		gd.widthHint = 250;
		techniqueFilter.setLayoutData(gd);
        final Combo technqiueFilterCombo = new Combo(techniqueFilter, SWT.NONE);
        technqiueFilterCombo.add(SuspiciousnessRanking.ARITHMETICMEAN);
        technqiueFilterCombo.add(SuspiciousnessRanking.BARINEL);
        technqiueFilterCombo.add(SuspiciousnessRanking.BARONIETAL);
        technqiueFilterCombo.add(SuspiciousnessRanking.BRAUNBANQUET);
        technqiueFilterCombo.add(SuspiciousnessRanking.COHEN);
        technqiueFilterCombo.add(SuspiciousnessRanking.CONFIDENCE);
        technqiueFilterCombo.add(SuspiciousnessRanking.DSTAR);
        technqiueFilterCombo.add(SuspiciousnessRanking.KULCYNSKI2);
        technqiueFilterCombo.add(SuspiciousnessRanking.MOUNTFORD);
        technqiueFilterCombo.add(SuspiciousnessRanking.OCHIAI);
        technqiueFilterCombo.add(SuspiciousnessRanking.OCHIAI2);
        technqiueFilterCombo.add(SuspiciousnessRanking.OP2);
        technqiueFilterCombo.add(SuspiciousnessRanking.PHI);
        technqiueFilterCombo.add(SuspiciousnessRanking.PIERCE);
        technqiueFilterCombo.add(SuspiciousnessRanking.ROGERSTANIMOTO);
        technqiueFilterCombo.add(SuspiciousnessRanking.RUSSELRAO);
        technqiueFilterCombo.add(SuspiciousnessRanking.SIMPLEMATCHING);
        technqiueFilterCombo.add(SuspiciousnessRanking.TARANTULA);
        technqiueFilterCombo.add(SuspiciousnessRanking.ZOLTAR);
        technqiueFilterCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				techniqueFilterIndex = technqiueFilterCombo.getSelectionIndex();
				String selectedTechnique = technqiueFilterCombo.getItem(techniqueFilterIndex);
				for (SBFLMeasures measure:elementsSBFLMeasures) {
					measure.currentTechnique = selectedTechnique;
					double susp = suspComputing.getSuspiciousness(measure);
					measure.getSusp().put(selectedTechnique, susp);
				}
				suspComputing.calculateRanks();
				m_treeViewer.collapseAll();
				m_treeViewer.refresh();
			}
		});
        
		Group sbflInfo = new Group(contents, SWT.NULL);
		FillLayout fill = new FillLayout(SWT.VERTICAL);
		sbflInfo.setLayout(fill);
		sbflInfo.setText("SBFL Information");
		gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		sbflInfo.setLayoutData(gd);
		
	    final Tree addressTree = new Tree(sbflInfo, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		addressTree.setHeaderVisible(true);
		addressTree.setLinesVisible(true);
		addressTree.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Point point = new Point(event.x, event.y);
				TreeItem item = addressTree.getItem(point);
				if (item == null || item.getData() == null) {
					//do nothing
				}
				else if (item.getData() instanceof SBFLMeasures) {
					EObject eobjectToOpen = ((SBFLMeasures) item.getData()).getModelObject();		
					IFile fileToOpen = ResourcesPlugin.getWorkspace().getRoot().getFile(
							new Path(eobjectToOpen.eResource().getURI().toPlatformString(true)));
					IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().
							getDefaultEditor(fileToOpen.getName());
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						IEditorPart editor = page.openEditor(new FileEditorInput(fileToOpen), desc.getId());
						if (editor instanceof EcoreEditor) {
							TreeViewer tviewer = (TreeViewer) ((EcoreEditor) editor).getViewer();
							ResourceSet resSet =(ResourceSet) tviewer.getInput();
							EObject eobjectToOpen2 = resSet.getResources().get(0).getEObject(
									eobjectToOpen.eResource().getURIFragment(eobjectToOpen));
							tviewer.setSelection(new StructuredSelection(eobjectToOpen2));
						}else if (editor instanceof XtextEditor) {
							//TODO: how to reveal the object in the xtext editor
						}
					} catch (PartInitException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		m_treeViewer = new TreeViewer(addressTree);

		TreeColumn metaclassColumn = new TreeColumn(addressTree, SWT.LEFT);
		metaclassColumn.setAlignment(SWT.LEFT);
		metaclassColumn.setText("Meta-Class");
		metaclassColumn.setWidth(130);
		
		TreeColumn modelColumn = new TreeColumn(addressTree, SWT.LEFT);
		modelColumn.setAlignment(SWT.LEFT);
		modelColumn.setText("Model Element");
		modelColumn.setWidth(150);
		
		int colNum = elementsSBFLMeasures.get(0).getCoverage().size();
		for (int i=0; i<colNum; i++) {
			TreeColumn column = new TreeColumn(addressTree, SWT.LEFT);
			column.setAlignment(SWT.CENTER);
			column.setText("Test " + (i+1));
			column.setWidth(60);
		}
		
//		TreeColumn NCF = new TreeColumn(addressTree, SWT.LEFT | SWT.BOLD);
//		NCF.setAlignment(SWT.CENTER);
//		NCF.setText("NCF");
//		NCF.setWidth(50);
//		TreeColumn NUF = new TreeColumn(addressTree, SWT.LEFT | SWT.BOLD);
//		NUF.setAlignment(SWT.CENTER);
//		NUF.setText("NUF");
//		NUF.setWidth(50);
//		TreeColumn NCS = new TreeColumn(addressTree, SWT.LEFT | SWT.BOLD);
//		NCS.setAlignment(SWT.CENTER);
//		NCS.setText("NCS");
//		NCS.setWidth(50);
//		TreeColumn NUS = new TreeColumn(addressTree, SWT.LEFT | SWT.BOLD);
//		NUS.setAlignment(SWT.CENTER);
//		NUS.setText("NUS");
//		NUS.setWidth(50);
//		TreeColumn NC = new TreeColumn(addressTree, SWT.LEFT | SWT.BOLD);
//		NC.setAlignment(SWT.CENTER);
//		NC.setText("NC");
//		NC.setWidth(50);
//		TreeColumn NU = new TreeColumn(addressTree, SWT.LEFT | SWT.BOLD);
//		NU.setAlignment(SWT.CENTER);
//		NU.setText("NU");
//		NU.setWidth(50);
//		TreeColumn NS = new TreeColumn(addressTree, SWT.LEFT | SWT.BOLD);
//		NS.setAlignment(SWT.CENTER);
//		NS.setText("NS");
//		NS.setWidth(50);
//		TreeColumn NF = new TreeColumn(addressTree, SWT.LEFT | SWT.BOLD);
//		NF.setAlignment(SWT.CENTER);
//		NF.setText("NF");
//		NF.setWidth(50);
		
		TreeColumn susp = new TreeColumn(addressTree, SWT.LEFT | SWT.BOLD);
		susp.setAlignment(SWT.CENTER);
		susp.setText("Susp");
		susp.setWidth(65);
		
		TreeColumn rank = new TreeColumn(addressTree, SWT.LEFT | SWT.BOLD);
		rank.setAlignment(SWT.CENTER);
		rank.setText("Rank");
		rank.setWidth(55);
		
		m_treeViewer.setContentProvider(new TDLSBFLContentProvider());
		m_treeViewer.setLabelProvider(new TableLabelProvider());
		m_treeViewer.setInput(suspComputing);
		m_treeViewer.setFilters(new ElementFilter(), new TechniqueFilter());
		m_treeViewer.collapseAll();
	}

	private class TDLSBFLContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof List<?>) {
				return ((List<?>) parentElement).toArray();
			}
			if (parentElement instanceof SuspiciousnessRanking) {
				return ((SuspiciousnessRanking) parentElement).getElementsSBFLMeasures().toArray();
			}
			return new Object[0]; 
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof String) {
				return (String) element;
			}
			if (element instanceof SuspiciousnessRanking) {
				return "Model Element Suspiciousness";
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof List<?>) {
				return ((List<?>) element).size() > 0;
			}
			if (element instanceof SuspiciousnessRanking) {
				return ((SuspiciousnessRanking) element).getElementsSBFLMeasures().size() > 0;
			}
			return false;
		}
		
	}
	
	class TableLabelProvider implements ITableLabelProvider, ITableColorProvider {
		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Color getForeground(Object element, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Color getBackground(Object element, int columnIndex) {
			if (element instanceof SBFLMeasures) {
				 SBFLMeasures sbflMeasures =  (SBFLMeasures) element;
				if (columnIndex > 1 && columnIndex < sbflMeasures.getCoverage().size() + 2) {
					//the test case coverages
					String tcEntry = sbflMeasures.getCoverage().get(columnIndex-2);
					if (tcEntry == TDLCoverageUtil.COVERED || tcEntry == TDLTestResultUtil.PASS) {
						return GREEN;
					}
					else if (tcEntry == TDLCoverageUtil.NOT_COVERED || tcEntry == TDLTestResultUtil.FAIL) {
						return RED;
					}
				}
			}
			return null;
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String getColumnText(Object element, int columnIndex) {
			String columnText = "";
			if (element instanceof String) {
				switch (columnIndex) {
				case 0:
					columnText = (String) element;
					break;
				}
			}
			if (element instanceof SBFLMeasures) {
				SBFLMeasures sbflMeasures =  (SBFLMeasures) element;
				int sbflOperandsStartIndex = (sbflMeasures.getCoverage().size() + 2);
				if (columnIndex == 0 && sbflMeasures.getMetaclass() != null) {
					columnText = sbflMeasures.getMetaclass().getName();
				}
				else if (columnIndex == 1 && sbflMeasures.getModelObject() != null) {
					String metaclassName = sbflMeasures.getMetaclass().getName();
					columnText = this.eObjectLabelProvider(sbflMeasures.getModelObject()).replaceAll("\\s", "");
					columnText = columnText.substring(metaclassName.length());
				}
				else if (columnIndex > 1 && columnIndex < sbflOperandsStartIndex) {
					//the test case coverages and test case verdicts
					String tcEntry = sbflMeasures.getCoverage().get(columnIndex-2);
					if (tcEntry == TDLTestResultUtil.PASS) {
						columnText = TDLTestResultUtil.PASS;
					}
					else if (tcEntry == TDLTestResultUtil.FAIL) {
						columnText = TDLTestResultUtil.FAIL;
					}else {
						columnText = "";
					}	
				}
				else if (columnIndex >= sbflOperandsStartIndex) {
					//for the last row, the measures are zero, so not showing anything
					if (sbflMeasures.getMetaclass() == null) {
						columnText = "";
					}else {
//						if (columnIndex == sbflOperandsStartIndex) {
//							columnText = sbflMeasures.getNCF() + "";
//						}
//						else if (columnIndex == sbflOperandsStartIndex + 1) {
//							columnText = sbflMeasures.getNUF() + "";
//						}
//						else if (columnIndex == sbflOperandsStartIndex + 2) {
//							columnText = sbflMeasures.getNCS() + "";
//						}
//						else if (columnIndex == sbflOperandsStartIndex + 3) {
//							columnText = sbflMeasures.getNUS() + "";
//						}
//						else if (columnIndex == sbflOperandsStartIndex + 4) {
//							columnText = sbflMeasures.getNC() + "";
//						}
//						else if (columnIndex == sbflOperandsStartIndex + 5) {
//							columnText = sbflMeasures.getNU() + "";
//						}
//						else if (columnIndex == sbflOperandsStartIndex + 6) {
//							columnText = sbflMeasures.getNS() + "";
//						}
//						else if (columnIndex == sbflOperandsStartIndex + 7) {
//							columnText = sbflMeasures.getNF() + "";
//						}
						if (columnIndex == sbflOperandsStartIndex) {
							if (sbflMeasures.getSusp().get(sbflMeasures.currentTechnique) == null) {
								columnText = "";
							}else {
								columnText = sbflMeasures.getSusp().get(sbflMeasures.currentTechnique) + "";
							}	
						}
						else if (columnIndex == sbflOperandsStartIndex + 1) {
							if (sbflMeasures.getRank().get(sbflMeasures.currentTechnique) == null) {
								columnText = "";
							}else {
								columnText = sbflMeasures.getRank().get(sbflMeasures.currentTechnique) + "";
							}
						}
					}
					
				}
			}
			return columnText; 
		}
		
		public String eObjectLabelProvider(EObject object) {
			final Class<?> IItemLabelProviderClass = IItemLabelProvider.class;
			ArrayList<AdapterFactory> factories = new ArrayList<AdapterFactory>();
			factories.add(new ResourceItemProviderAdapterFactory());
			factories.add(new EcoreItemProviderAdapterFactory());
			factories.add(new ReflectiveItemProviderAdapterFactory());
				
			ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(factories);
			IItemLabelProvider itemLabelProvider;
		 	AdapterFactory adapterFactory = composedAdapterFactory;
		    	
		 	itemLabelProvider = (IItemLabelProvider)adapterFactory.adapt(object, IItemLabelProviderClass);
			String objectLabel = itemLabelProvider.getText(object) ;
		        
			return (objectLabel);
		}

	}

private class ElementFilter extends ViewerFilter {
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (elementFilterIndex == -1 || elementFilterIndex == 0) {
			return true;
		}else {
			if (element instanceof SBFLMeasures) {
				SBFLMeasures parameters =  (SBFLMeasures) element;
				if (parameters.getMetaclass() == null) {
					return false;
				}else {
					String filter = classFilters.get(elementFilterIndex-1);
					String objectType = parameters.getMetaclass().getName();
					if (objectType.equals(filter)) {
						filter = objectType;
						//true
					}
					return parameters.getMetaclass().getName().equals(classFilters.get(elementFilterIndex-1));
				}
			}
		}
		return false;
	}
}

private class TechniqueFilter extends ViewerFilter {
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return true;
	}
}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
