package com.example.wangjiinshan;

import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class MainFrm {

	protected Shell shell;
	protected Display display;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainFrm window = new MainFrm();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
//		shell.setSize(450, 300);
		shell.setMaximized(true);
		shell.setText("Sql Management Studio");
		
//		shell.setLayout(new GridLayout());
		shell.setLayout(new FormLayout());
		
		/**
		 * 菜单栏
		 */
		CreateMenubar();
		
		
		/**
		 * 工具栏
		 */
		CreateToolbar(shell,display);

		/**
		 * 主窗口
		 */
		SashForm sashFormHor = new SashForm(shell, SWT.HORIZONTAL);
		FormData sashFormHorData = new FormData();
		sashFormHorData.top = new FormAttachment(0,30);
		sashFormHorData.left = new FormAttachment(0,0);
		sashFormHorData.right = new FormAttachment(100,0);
		sashFormHorData.bottom = new FormAttachment(100,-20);
		sashFormHor.setLayoutData(sashFormHorData);
		
//		sashFormHor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite leftPanel = new Composite(sashFormHor, SWT.BORDER);
//		Composite rightPanel = new Composite(sashForm, SWT.BORDER);
		
		SashForm sashFormVer = new SashForm(sashFormHor, SWT.VERTICAL);
		Composite mainPanel = new Composite(sashFormVer, SWT.BORDER);
		Composite tablePanel = new Composite(sashFormVer, SWT.BORDER);
		
		sashFormVer.setWeights(new int[]{3,1});
		sashFormHor.setWeights(new int[]{2,8});
		
		/**
		 * 状态栏
		 */
		Composite statusBar = new Composite(shell, SWT.BORDER);
		FormData statusBarData = new FormData();
		statusBarData.top = new FormAttachment(sashFormHor,0);
		statusBarData.left = new FormAttachment(0,0);
		statusBarData.right = new FormAttachment(100,0);
		statusBarData.bottom = new FormAttachment(100,0);
		statusBar.setLayoutData(statusBarData);
		
		
		
	}
	
	private void CreateMenubar(){
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("Management");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		MenuItem mntmLogin = new MenuItem(menu_1, SWT.NONE);
		mntmLogin.setText("New Instance");
		mntmLogin.setImage(new Image(null, "icons/doc_16px.png"));
		mntmLogin.setToolTipText("新建数据库连接实例");
		mntmLogin.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				NewSqlConnectionInstance login = new NewSqlConnectionInstance(shell, SWT.TITLE | SWT.CLOSE);
				login.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		MenuItem mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText("编辑");
		MenuItem mntmView = new MenuItem(menu, SWT.CASCADE);
		mntmView.setText("视图");
	}
	
	private void CreateToolbar(Composite parent,Display d){
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT);
		RGB rgb=new RGB(0, 0, 0);
		//toolBar.setBackground(new org.eclipse.swt.graphics.Color(null, rgb));
		FormData toolbarFormData = new FormData();
		toolbarFormData.top = new FormAttachment(0,0);
		toolbarFormData.left = new FormAttachment(0,0);
		toolbarFormData.right = new FormAttachment(100,0);
		toolbarFormData.height = 30;
		toolBar.setLayoutData(toolbarFormData);

		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setImage(new Image(null, "icons/user_24px.png"));
		//toolItem.setText("登录");
		toolItem.setToolTipText("登录");
		ToolItem toolItems = new ToolItem(toolBar, SWT.PUSH);
		toolItems.setImage(new Image(null, "icons/configure_24px.png"));
		//toolItems.setText("测试");
		toolItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				NewSqlConnectionInstance login = new NewSqlConnectionInstance(shell, SWT.TITLE | SWT.CLOSE);
				login.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
