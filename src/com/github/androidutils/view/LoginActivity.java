package com.github.androidutils.view;

import com.github.androidutils.R;
import com.github.androidutils.presenter.ILoginPresenter;
import com.github.androidutils.presenter.LoginPresenterCompl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener,ILoginView{
	
	private EditText et_userName,et_userPass;
	private Button btn_login;
	private ILoginPresenter loginPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		et_userName = (EditText) findViewById(R.id.userName);
		et_userPass = (EditText) findViewById(R.id.userPass);
		btn_login = (Button) findViewById(R.id.login);
		btn_login.setOnClickListener(this);
		loginPresenter = new LoginPresenterCompl(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			String userName = et_userName.getText().toString().trim();
			String userPass = et_userPass.getText().toString().trim();
			loginPresenter.doLogin(userName, userPass);
			break;
		}
	}

	@Override
	public void onLoginResult(Boolean result, int code) {
		Toast.makeText(this, result ? "success" : "faile", Toast.LENGTH_SHORT).show();
	}
}
