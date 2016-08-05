package kr.ac.sungkyul.mysite.web.guestbook;

import kr.ac.sungkyul.mysite.web.main.MainAction;
import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.ActionFactory;

public class GuestBookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if ("list".equals(actionName)){
			action = new listAction();
		} else if ("deleteform".equals(actionName)) {
			action = new DeleteFormAction();
		} else if ("add".equals(actionName)) {
			action = new AddAction();
		} else if ("delete".equals(actionName)) {
			action = new DeleteAction();
		} else {
			action = new MainAction();
		}
		return action;
	}
}