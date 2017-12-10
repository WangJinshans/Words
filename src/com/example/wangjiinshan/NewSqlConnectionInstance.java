package com.example.wangjiinshan;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

import com.example.db.DBManager;

import org.eclipse.swt.widgets.Button;

public class NewSqlConnectionInstance extends Dialog {

	protected Object result;
	protected Shell shlLogin;
	private Text textUser;
	private Text textPassword;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public NewSqlConnectionInstance(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlLogin.open();
		shlLogin.layout();
		Display display = getParent().getDisplay();
		while (!shlLogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlLogin = new Shell(getParent(), getStyle());
		shlLogin.setSize(485, 268);
		shlLogin.setText("Login");
		
		Label lblUser = new Label(shlLogin, SWT.NONE);
		lblUser.setBounds(59, 49, 57, 17);
		lblUser.setText("User\uFF1A");// \uFF1A特殊符号 ：
		
		textUser = new Text(shlLogin, SWT.BORDER);
		textUser.setBounds(140, 46, 296, 23);
		
		Label lblPassword = new Label(shlLogin, SWT.NONE);
		lblPassword.setBounds(59, 94, 68, 17);
		lblPassword.setText("Password\uFF1A");
		
		textPassword = new Text(shlLogin, SWT.PASSWORD|SWT.BORDER);
		textPassword.setBounds(140, 91, 296, 23);
		
		Button btnOK = new Button(shlLogin, SWT.NONE);
		btnOK.setBounds(118, 170, 80, 27);
		btnOK.setText("OK");
		
		btnOK.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnOKwidgetSelected();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btnCancel = new Button(shlLogin, SWT.NONE);
		btnCancel.setBounds(266, 170, 80, 27);
		btnCancel.setText("Cancel");
		
		btnCancel.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
					shlLogin.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void btnOKwidgetSelected(){
		String userName = textUser.getText();
		String password = textPassword.getText();
		DBManager dbManager=new DBManager();
		if(userName.equals("admin") && password.equals("admin")){
			
			MessageDialog.openInformation(shlLogin, "info", "登录成功!");
		}else{
			MessageDialog.openInformation(shlLogin, "info", "用户名或密码不正确，请重新输入!");
			return;
		}
	}
	
	private boolean logIn(String userName,String userPassWord)
	{
		boolean loginOK=false;
		
		return loginOK;
	}
}
