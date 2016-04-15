package com.starterkit.bartoszzychal.library.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.MessageBox;

import com.starterkit.bartoszzychal.library.dialogs.AddBookDialog;

public class AddBookHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AddBookDialog addBookDialog = new AddBookDialog(null);
		addBookDialog.open();
		return null;
	}

}
