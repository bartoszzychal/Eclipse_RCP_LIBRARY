package com.starterkit.bartoszzychal.library.views;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.starterkit.bartoszzychal.library.dataProvider.data.Book;
import com.starterkit.bartoszzychal.library.messages.Messages;
import com.starterkit.bartoszzychal.library.model.LibraryModel;

public class BookDetailsView extends ViewPart {

	private final String bundlePath = getClass().getPackage().getName();
	private final Messages messages = new Messages(bundlePath,getClass().getSimpleName());

	private LibraryModel model = LibraryModel.INSTANCE;
	
	public BookDetailsView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		DataBindingContext dbc = new DataBindingContext();
		String []labels = new String[] {"BookDetailsView.id","BookDetailsView.title","BookDetailsView.authors"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		for (String label : labels) {
			addLabel(parent, messages.getString(label));
			Text text = addText(parent);
			ISWTObservableValue textField = WidgetProperties.text(SWT.Modify).observe(text);
			IObservableValue observedValue = BeanProperties.value(Book.class, messages.getString(label+"_prop")).observe(model.getBookDetailsModel());
			dbc.bindValue(textField, observedValue);
		}
	}

	Text addText(Composite parent) {
		Text text = new Text(parent, SWT.BORDER);
		text.setEditable(false);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		return text;
	}

	Label addLabel(Composite parent, String label) {
		Label lbl = new Label(parent, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl.setText(label);
		return lbl;
	}

	@Override
	public void setFocus() {
	}

}
