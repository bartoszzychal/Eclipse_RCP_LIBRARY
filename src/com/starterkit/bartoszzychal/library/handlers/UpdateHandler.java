package com.starterkit.bartoszzychal.library.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import com.starterkit.bartoszzychal.library.model.LibraryModel;

public class UpdateHandler extends AbstractHandler {
	
	private LibraryModel model = LibraryModel.INSTANCE;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		model.update();
		return null;
	}

	

}
