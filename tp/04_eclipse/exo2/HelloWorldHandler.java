package hmin304.tp.eclipse;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;

public class HelloWorldHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) 
				   throws ExecutionException {
				
		MessageDialog.openInformation(null, "Hello World", "Hello World!");
		
		return null;
	}

}