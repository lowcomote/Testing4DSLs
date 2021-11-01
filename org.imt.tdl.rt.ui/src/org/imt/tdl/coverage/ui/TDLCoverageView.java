package org.imt.tdl.coverage.ui;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.part.ViewPart;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.testResult.TDLMessageResult;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestPackageResult;

public class TDLCoverageView extends ViewPart{

	public static final String ID = "org.imt.tdl.rt.ui.coverageView"; //$NON-NLS-1$
	
	private TreeViewer m_treeViewer;
	
	private static final Color RED = new Color(Display.getCurrent(), 255, 102, 102);

	private static final Color GREEN = new Color(Display.getCurrent(), 102, 255, 102);
	
	private static final Color YELLOW = new Color(Display.getCurrent(), 255, 255, 102);

	
	private static int filterIndex = -1;
	
	@Override
	public void createPartControl(Composite parent) {
		Composite contents = new Group(parent, SWT.FILL);
	    GridLayout layout = new GridLayout();
		contents.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
	    GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
	    gd.horizontalAlignment = SWT.FILL;
	    contents.setLayoutData(gd);
	    
	    Group filter = new Group(contents, SWT.FILL);
	    layout = new GridLayout();
		filter.setLayout(layout);
		layout.numColumns = 1;
		layout.verticalSpacing = 9;
	    filter.setText("Filter");
	    gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.ON_TOP;
		gd.widthHint = 100;
	    filter.setLayoutData(gd);
        final Combo filterCombo = new Combo(filter, SWT.NONE);
        filterCombo.add("All");
        filterCombo.add("Covered");
        filterCombo.add("Not-Covered");
        filterCombo.add("Uncoverable");
		filterCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				filterIndex = filterCombo.getSelectionIndex();
				m_treeViewer.collapseAll();
				m_treeViewer.refresh();
			}
		});
		
		Group testCoverage = new Group(contents, SWT.FILL);
		FillLayout fill = new FillLayout(SWT.VERTICAL);
		testCoverage.setLayout(fill);
		layout.numColumns = 1;
		layout.verticalSpacing = 9;
		testCoverage.setText("Coverage");
		gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		gd.horizontalAlignment = SWT.FILL;
		testCoverage.setLayoutData(gd);
		
	    final Tree addressTree = new Tree(testCoverage, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		addressTree.setHeaderVisible(true);
		addressTree.setLinesVisible(true);
		m_treeViewer = new TreeViewer(addressTree);

		TreeColumn modelColumn = new TreeColumn(addressTree, SWT.LEFT);
		modelColumn.setAlignment(SWT.LEFT);
		modelColumn.setText("Model Element");
		modelColumn.setWidth(200);
		
		int colNum = TDLCoverageUtil.getInstance().getTestSuiteCoverage().getTCCoverages().size();
		for (int i=0; i<colNum; i++) {
			TreeColumn column = new TreeColumn(addressTree, SWT.LEFT);
			column.setAlignment(SWT.LEFT);
			column.setText("Test " + (i+1));
			column.setWidth(130);
		}
		
		TreeColumn tsColumn = new TreeColumn(addressTree, SWT.LEFT);
		tsColumn.setAlignment(SWT.LEFT);
		tsColumn.setText("Test Suite");
		tsColumn.setWidth(130);
		
		//calculating the coverage
		TDLCoverageUtil.getInstance().getTestSuiteCoverage().calculateTSCoverage();

		m_treeViewer.setContentProvider(new TDLCoverageContentProvider());
		m_treeViewer.setLabelProvider(new TableLabelProvider());
		m_treeViewer.setInput(TDLCoverageUtil.getInstance().getModelContents().next());
		m_treeViewer.addFilter(new DataFilter());
		m_treeViewer.collapseAll();
	}

	private class TDLCoverageContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
//			if (parentElement instanceof List<?>) {
//				return ((List<?>) parentElement).toArray();
//			}
//			if (parentElement instanceof TDLTestPackageResult) {
//				return ((TDLTestPackageResult) parentElement).getResults().toArray();
//			}
//			if (parentElement instanceof TDLTestCaseResult) {
//				return ((TDLTestCaseResult) parentElement).getTdlMessages().toArray();
//			}
			return new Object[0]; 
		}

		@Override
		public Object getParent(Object element) {
//			if (element instanceof String) {
//				return (String) element;
//			}
//			if (element instanceof TDLTestPackageResult) {
//				return ((TDLTestPackageResult) element).getTestPackageName();
//			}
//			if (element instanceof TDLTestCaseResult) {
//				return ((TDLTestCaseResult) element).getTestCaseName();
//			}
//			if (element instanceof TDLMessageResult) {
//				return ((TDLMessageResult) element).getTdlMessageName();
//			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
//			if (element instanceof List<?>) {
//				return ((List<?>) element).size() > 0;
//			}
//			if (element instanceof TDLTestPackageResult) {
//				return ((TDLTestPackageResult) element).getResults().size() > 0;
//			}
//			if (element instanceof TDLTestCaseResult) {
//				return ((TDLTestCaseResult) element).getTdlMessages().size() > 0;
//			}
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
		
		private Color getBackground(Object element) {
//			if (element instanceof TDLTestPackageResult) {
//				TDLTestPackageResult result = (TDLTestPackageResult) element;
//				if (result.getNumOfFailedTestCases() == 0) { 
//					return GREEN;
//				}
//				else return RED;
//			}
//			if (element instanceof TDLTestCaseResult) {
//				TDLTestCaseResult result = (TDLTestCaseResult) element;
//				if (result.getValue().equals("PASS")) {
//					return GREEN;
//				}
//				else if(result.getValue().equals("INCONCLUSIVE")) {
//					return YELLOW;
//				}
//				else return RED;
//			}
//			if (element instanceof TDLMessageResult) {
//				TDLMessageResult result = (TDLMessageResult) element;
//				if (result.getFailure() == false) {
//					return GREEN;
//				}
//				else {
//					return RED;
//				}
//			}
			return null;
		}

		@Override
		public Color getBackground(Object element, int columnIndex) {
			return getBackground(element);
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
				String result = (String) element;
				switch (columnIndex) {
				case 0:
					columnText = result;
					break;
				}
			}
//			if (element instanceof TDLTestPackageResult) {
//				TDLTestPackageResult result = (TDLTestPackageResult) element;
//				switch(columnIndex) {
//				case 0:
//					columnText = result.getTestPackageName();
//					break;
//				case 1:
//					if (result.getNumOfFailedTestCases() == 0) {
//						columnText = "PASS";
//					}else if (result.getNumOfInconclusiveTestCases() > 0) {
//						columnText = "INCONCLUSIVE";
//					}else {
//						columnText = "FAIL";
//					}
//					break;
//				case 2:
//					columnText = "";
//					break;
//				}
//			}
//			if (element instanceof TDLTestCaseResult) {
//				TDLTestCaseResult result = (TDLTestCaseResult) element;
//				switch (columnIndex) {
//				case 0:
//					columnText = result.getTestCaseName();
//					break;
//				case 1:
//					columnText = result.getValue();
//					break;
//				case 2:
//					columnText = result.getDescription();
//					break;
//				}
//			}
//			if (element instanceof TDLMessageResult) {
//				TDLMessageResult result = (TDLMessageResult) element;
//				switch (columnIndex) {
//				case 0:
//					columnText = result.getTdlMessageName();
//					break;
//				case 1:
//					if (result.getFailure() == false) {
//						columnText = "PASS";
//					}else {
//						columnText = "FAIL";
//					}
//					break;
//				case 2:
//					columnText = result.getMessage();
//					break;
//				}
//			}
			return columnText; 
		}

	}
private class DataFilter extends ViewerFilter {
		
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (filterIndex == -1 || filterIndex == 0) {
				return true;
			}
			if (filterIndex == 1) {//covered elements
//				if (element instanceof TDLTestPackageResult) {
//					return true;
//				}
//				if (element instanceof TDLTestCaseResult) {
//					TDLTestCaseResult result = (TDLTestCaseResult) element;
//					return result.getValue().equals("PASS");
//				}
//				if (element instanceof TDLMessageResult) {
//					TDLMessageResult result = (TDLMessageResult) element;
//					return result.getValue(); 
//				}
			}
			if (filterIndex == 2) {//not covered elements
//				if (element instanceof TDLTestPackageResult) {
//					return true;
//				}
//				if (element instanceof TDLTestCaseResult) {
//					TDLTestCaseResult result = (TDLTestCaseResult) element;
//					return result.getValue().equals("FAIL");
//				}
//				if (element instanceof TDLMessageResult) {
//					TDLMessageResult result = (TDLMessageResult) element;
//					return !result.getValue(); 
//				}
			}
			if (filterIndex == 3) {//uncoverable elements
//				if (element instanceof TDLTestPackageResult) {
//					return true;
//				}
//				if (element instanceof TDLTestCaseResult) {
//					TDLTestCaseResult result = (TDLTestCaseResult) element;
//					return result.getValue().equals("INCONCLUSIVE");
//				}
//				if (element instanceof TDLMessageResult) {
//					if (parentElement instanceof TDLTestCaseResult) {
//						TDLTestCaseResult result = (TDLTestCaseResult) parentElement;
//						return result.getValue().equals("INCONCLUSIVE");
//					}
//					return false;
//				}
			}
			return false;
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
