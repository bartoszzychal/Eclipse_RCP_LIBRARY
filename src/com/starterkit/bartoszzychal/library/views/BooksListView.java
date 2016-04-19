package com.starterkit.bartoszzychal.library.views;


import java.util.logging.Logger;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.starterkit.bartoszzychal.library.dataProvider.data.Book;
import com.starterkit.bartoszzychal.library.filters.AuthorFilter;
import com.starterkit.bartoszzychal.library.filters.TitleFilter;
import com.starterkit.bartoszzychal.library.messages.Messages;
import com.starterkit.bartoszzychal.library.model.LibraryModel;
import org.eclipse.swt.widgets.Button;

public class BooksListView extends ViewPart {

	private final Logger LOG = Logger.getLogger(getClass().getSimpleName());
	private final String bundlePath = getClass().getPackage().getName();
	private final Messages messages = new Messages(bundlePath,getClass().getSimpleName());
	
	private String[] columns = new String[] { messages.getString("BooksListView.id"), 
			messages.getString("BooksListView.title"), messages.getString("BooksListView.authors") };
	private String[] columns_prop = new String[] { messages.getString("BooksListView.id_prop"), 
			messages.getString("BooksListView.title_prop"), messages.getString("BooksListView.authors_prop") };

	private LibraryModel model = LibraryModel.INSTANCE;
	private TableViewer viewer;
	private Text searchTitleText;
	private Text searchAuthorText;
	public BooksListView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(1, false);
		parent.setLayout(layout);
		
		Label searchTitleLabel = new Label(parent, SWT.NONE);
		searchTitleLabel.setText(messages.getString("BooksListView.search_by_title"));
		searchTitleText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchTitleText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		Label searchBookLabel = new Label(parent, SWT.NONE);
		searchBookLabel.setText(messages.getString("BooksListView.search_by_author"));
		searchAuthorText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchAuthorText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		createViewer(parent);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
	}

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		createColumns(parent, viewer);

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		ViewerSupport.bind(viewer, model.getBooksModel(), BeanProperties.values(Book.class, columns_prop));

		LibraryModel.INSTANCE.update();

	    addFilters();
	    addRowSelectedListener();
		getSite().setSelectionProvider(viewer);
		
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}

	private void addRowSelectedListener() {
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
		    public void selectionChanged(final SelectionChangedEvent event) {
		        IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		        Book bookDetails = (Book)selection.getFirstElement();
		        if (bookDetails != null) {
		        	model.setBookDetailsModel(bookDetails);					
				}
		  }
		});
	}

	private void addFilters() {
		TitleFilter titleFilter = new TitleFilter(searchTitleText);
		viewer.addFilter(titleFilter);
		titleFilter.getObservedText().addValueChangeListener(new IValueChangeListener() {
			
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				viewer.refresh();
			}
		});
	    AuthorFilter authorFilter = new AuthorFilter(searchAuthorText);
		viewer.addFilter(authorFilter);
		authorFilter.getObservedText().addValueChangeListener(new IValueChangeListener() {
			
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				viewer.refresh();
			}
		});
		
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		int[] bounds = { 50, 150, 150 };

		TableViewerColumn col = createTableViewerColumn(columns[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Book book = (Book) element;
				return Long.toString(book.getId());
			}
		});

		col = createTableViewerColumn(columns[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Book book = (Book) element;
				return book.getTitle();
			}
		});

		col = createTableViewerColumn(columns[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Book book = (Book) element;
				return book.getAuthors();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
