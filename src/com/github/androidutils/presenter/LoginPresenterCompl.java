package com.github.androidutils.presenter;

import com.github.androidutils.model.User;
import com.github.androidutils.view.ILoginView;

public class LoginPresenterCompl implements ILoginPresenter{
	
	private ILoginView loginView;
	private User user;
	
	public LoginPresenterCompl(ILoginView view) {
		loginView = view;
		user = new User("123", "123456");
	}

	@Override
	public void doLogin(String name, String password) {
		boolean result = false;
		int code = 0;
		if(name.equals(user.getName()) && password.equals(user.getPassword())){
			result = true;
			code = 1;
		}else{
			result = false;
			code = 0;
		}
		loginView.onLoginResult(result, code);
	}

}
