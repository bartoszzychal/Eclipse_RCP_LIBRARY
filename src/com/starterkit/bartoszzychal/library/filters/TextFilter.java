package com.starterkit.bartoszzychal.library.filters;

import java.util.logging.Logger;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

public abstract class TextFilter extends ViewerFilter {
	
	protected final Logger LOG = Logger.getLogger(getClass().getSimpleName());
	protected final WritableValue searchText = new WritableValue("", String.class);
	protected ISWTObservableValue observedText;
	
	public TextFilter(Text searchText){
		DataBindingContext dbc = new DataBindingContext();
		observedText = WidgetProperties.text(SWT.Modify).observe(searchText);
		dbc.bindValue(observedText,this.searchText);
	}

	public ISWTObservableValue getObservedText() {
		return observedText;
	}
		
}
