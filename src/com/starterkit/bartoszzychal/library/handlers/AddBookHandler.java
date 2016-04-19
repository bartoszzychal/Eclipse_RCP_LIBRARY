package com.starterkit.bartoszzychal.library.handlers;

import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;

import com.starterkit.bartoszzychal.library.dialogs.AddBookDialog;
import com.starterkit.bartoszzychal.library.model.LibraryModel;

public class AddBookHandler extends AbstractHandler {

	private final Logger LOG = Logger.getLogger(getClass().getSimpleName());
	private LibraryModel model = LibraryModel.INSTANCE;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AddBookDialog addBookDialog = new AddBookDialog(null);
		int open = addBookDialog.open();
		if (open == Window.OK) {
			model.addNewBook();
		}else if(open == Window.CANCEL){
			model.clearAddNewBookModel();
		}
		LOG.info(Integer.toString(open));
		return null;
	}
}
