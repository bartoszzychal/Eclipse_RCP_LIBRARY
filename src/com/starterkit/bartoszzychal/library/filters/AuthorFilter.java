package com.starterkit.bartoszzychal.library.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Text;

import com.starterkit.bartoszzychal.library.dataProvider.data.Book;

public class AuthorFilter extends TextFilter {

	public AuthorFilter(Text searchText) {
		super(searchText);
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
	
		Book book = (Book) element;
		String value = (String) searchText.getValue();
		if (value.length() == 0 || book.getAuthors().toLowerCase().contains(value.toLowerCase())){
			return true;
		}
		return false;
	}

}
