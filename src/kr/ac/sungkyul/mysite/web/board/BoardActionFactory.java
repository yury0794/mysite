package kr.ac.sungkyul.mysite.web.board;

import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;

		if ("list".equals(actionName)) {
			action = new ListAction();
		} else if ("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if ("viewform".equals(actionName)) {
			action = new ViewFormAction();
		} else if ("modifyform".equals(actionName)) {
			action = new ModifyFormAction();
		}
		
		
		else {
			action = new ListAction();
		}

		return action;
	}
}