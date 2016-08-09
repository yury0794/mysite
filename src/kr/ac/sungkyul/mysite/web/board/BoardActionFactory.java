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
		} else if ("modifyform".equals(actionName)) {
			action = new ModifyFormAction();
		} else if ("view".equals(actionName)) {
			action = new ViewAction();
		} else if ("modify".equals(actionName)) {
			action = new ModifyAction();
		}
		
		
		else {
			action = new ListAction();
		}

		return action;
	}
}