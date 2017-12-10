package com.example.wangjiinshan;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LoginDialog extends Dialog {

	protected Object result;
	protected Shell shlLogin;
	private Text textUser;
	private Text textPassword;
	private MainFrm main;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public LoginDialog(Shell parent, int style, MainFrm main) {
		super(parent, style);
		this.main = main;
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
		
		center(main.shell.getDisplay(), shlLogin);
		
		Label lblUser = new Label(shlLogin, SWT.NONE);
		lblUser.setBounds(59, 49, 57, 17);
		lblUser.setText("User\uFF1A");
		
		textUser = new Text(shlLogin, SWT.BORDER);
		textUser.setBounds(140, 46, 296, 23);
		
		Label lblPassword = new Label(shlLogin, SWT.NONE);
		lblPassword.setBounds(59, 94, 68, 17);
		lblPassword.setText("Password\uFF1A");
		
		textPassword = new Text(shlLogin, SWT.PASSWORD);
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
		if(userName.equals("admin") && password.equals("admin")){
//			System.out.println("登录成功");
			MessageDialog.openInformation(shlLogin, "info", "登录成功");
			shlLogin.dispose();
		}else{
//			System.out.println("用户名或密码不正确，请重新输入!");
			MessageDialog.openInformation(shlLogin, "info", "用户名或密码不正确");
			return;
		}
	}
	
    
    /**
     * 设置窗口位于屏幕中间
     * @param display 设备
     * @param shell 要调整位置的窗口对象
     */
    public static void center(Display display, Shell shell)
    {
        Rectangle bounds = display.getPrimaryMonitor().getBounds();
        Rectangle rect = shell.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        shell.setLocation(x, y);
    } 
}
