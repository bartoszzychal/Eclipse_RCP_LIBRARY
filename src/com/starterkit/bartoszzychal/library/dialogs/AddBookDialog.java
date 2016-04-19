package com.starterkit.bartoszzychal.library.dialogs;

import java.util.logging.Logger;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.starterkit.bartoszzychal.library.dataProvider.data.Book;
import com.starterkit.bartoszzychal.library.messages.Messages;
import com.starterkit.bartoszzychal.library.model.LibraryModel;

public class AddBookDialog extends Dialog {
	
	private final String bundlePath = getClass().getPackage().getName();
	private final Messages messages = new Messages(bundlePath,getClass().getSimpleName());
	
	private Text titleText;
	private Text authorText;
	private LibraryModel model = LibraryModel.INSTANCE;
	private final Logger LOG = Logger.getLogger(getClass().getSimpleName());
	private final DataBindingContext dbc = new DataBindingContext();
	private Button buttokOK;

	public AddBookDialog(Shell parentShell) {
		super(parentShell);
	}

	IValidator validator = new IValidator() {

		@Override
		public IStatus validate(Object value) {
			String word = (String) value;
			if (word.length() != 0) {
				return ValidationStatus.ok();
			}
			return ValidationStatus.error(messages.getString("AddBookDialog.Field_is_empty!")); 
		}
	};

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		Label lblTitle = new Label(container, SWT.NONE);
		lblTitle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTitle.setText(messages.getString("AddBookDialog.Title")); //$NON-NLS-1$

		titleText = new Text(container, SWT.BORDER);
		titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblAuthor = new Label(container, SWT.NONE);
		lblAuthor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAuthor.setText(messages.getString("AddBookDialog.Author")); //$NON-NLS-1$

		authorText = new Text(container, SWT.BORDER);
		authorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		UpdateValueStrategy strategy = new UpdateValueStrategy();
		strategy.setBeforeSetValidator(validator);
		
		ISWTObservableValue titleObservable = WidgetProperties.text(SWT.Modify).observe(titleText);
		ISWTObservableValue authorObservable = WidgetProperties.text(SWT.Modify).observe(authorText);
		
		IObservableValue title = BeanProperties.value(Book.class, messages.getString("AddBookDialog.title_prop")).observe(model.getBookToAddModel()); //$NON-NLS-1$
		IObservableValue authors = BeanProperties.value(Book.class, messages.getString("AddBookDialog.authors_prop")).observe(model.getBookToAddModel()); //$NON-NLS-1$
		
		Binding bindTitle = dbc.bindValue(titleObservable, title, strategy, null);
		Binding bindAuthor = dbc.bindValue(authorObservable, authors, strategy, null);
		
		ControlDecorationSupport.create(bindTitle, SWT.TOP | SWT.LEFT);
		ControlDecorationSupport.create(bindAuthor, SWT.TOP | SWT.LEFT);
		
		
		addValidateListener(titleObservable);
		addValidateListener(authorObservable);
		
		return container;
	}

	private void addValidateListener(IObservableValue value) {
		value.addValueChangeListener(new IValueChangeListener() {

			@Override
			public void handleValueChange(ValueChangeEvent event) {
				boolean validate = true;
				for (Object o : dbc.getBindings()) {
					Binding binding = (Binding) o;
					IStatus status = (IStatus) binding.getValidationStatus().getValue();
					validate = validate && status.isOK();
				}
				buttokOK.setEnabled(validate);
			}
		});
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(messages.getString("AddBookDialog.Add_new_book")); //$NON-NLS-1$
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 165);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		buttokOK = createButton(parent, IDialogConstants.OK_ID, messages.getString("AddBookDialog.OK"), true); //$NON-NLS-1$
		buttokOK.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
}

