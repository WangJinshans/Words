package com.example.wangjiinshan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.example.util.ImageRegistryUtil;

public class ConfigWindowDialog extends Dialog {

	protected static Shell shell;
	private ToolBar toolBar;//关闭按钮栏
	private static Display display;
	private Composite mainComposite;//主显示界面
	protected static StackLayout stackLayout = new StackLayout();//主显示界面的布局
	
	private Composite mainPanel;//操作栏布局
	private Composite listPanel;//选项布局
	
	//选项对应的布局容器
	private Composite baseConfigComposite;//
	private Composite functionConfigComposite;
	private Composite offLineDictionaryComposite;
	private Composite hotKeyConfigComposite;
	private Composite getWordConfigComposite;
	private Composite netWorkConfigComposite;
	private Composite aboutComposite;
	
	private Composite moveComposite;//拖动栏
	private Composite leftMoveComposite;//
	private Composite rightMoveComposite;//
	
	private SashForm sashFormVer;//上下
	private SashForm sashFormHor;//左右
	private SashForm topsashFormHor;//顶部的左右移动栏
	
	private CLabel baseConfig;//基本设置
	private CLabel functionConfig;//功能设置
	private CLabel offLineDictionary;//离线词典
	private CLabel hotKeyConfig;//热键设置
	private CLabel getWordConfig;//取词划译
	private CLabel netWorkConfig;//网络设置
	private CLabel about;//关于界面

	
	private static Boolean blnMouseDown=false;
	private static int xPos=0;
	private static int yPos=0;
	protected Object result;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ConfigWindowDialog(Shell parent, int style) {
		super(parent, style);
		this.shell=parent;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		display = getParent().getDisplay();
		while (!shell.isDisposed()) {
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
		shell = new Shell(SWT.NONE);
		shell.setSize(450, 300);
		shell.setBounds(400, 200, 500, 300);
		shell.setLayout(new FormLayout());
		
		
		/**
		 * 主窗口
		 */
		
		//竖直
		sashFormVer = new SashForm(shell, SWT.VERTICAL);
		sashFormVer.setLayout(new FormLayout());
		FormData sashFormVerData = new FormData();
		sashFormVerData.top = new FormAttachment(0,0);
		sashFormVerData.left = new FormAttachment(0,0);
		sashFormVerData.right = new FormAttachment(100,0);
		sashFormVerData.bottom = new FormAttachment(100,0);
		sashFormVer.setLayoutData(sashFormVerData);
		sashFormVer.setBackground(new Color(null, 242,242,242));
		
		Composite topPanel = new Composite(sashFormVer, SWT.NONE);
		topPanel.setLayout(new FormLayout());
		FormData topPanelData = new FormData();
		topPanelData.top = new FormAttachment(0,0);
		topPanelData.left = new FormAttachment(0,0);
		topPanelData.right = new FormAttachment(100,0);
		topPanelData.bottom = new FormAttachment(100,0);
		topPanel.setLayoutData(topPanelData);
		topPanel.setBackground(new Color(null, 242,242,242));

		sashFormHor = new SashForm(sashFormVer, SWT.HORIZONTAL);
		
		
		listPanel = new Composite(sashFormHor, SWT.NONE);
		listPanel.setLayout(new FormLayout());
		mainPanel = new Composite(sashFormHor, SWT.NONE);
		mainPanel.setLayout(new FormLayout());
		
		sashFormHor.setWeights(new int[]{3,17});
		sashFormVer.setWeights(new int[]{35,265});
		
		
		/**
		 * 顶部关闭栏
		 */
		CreateCloseButton(topPanel);
		createMainPanel(mainPanel);
		
		
//		//词典
		baseConfig=new CLabel(listPanel, SWT.NONE);
		FormData dictionarydata=new FormData();
		dictionarydata.left = new FormAttachment(0,0);
		dictionarydata.top=new FormAttachment(listPanel,0);
		dictionarydata.right=new FormAttachment(100,0);
		dictionarydata.height=35;
		baseConfig.setLeftMargin(0);
		baseConfig.setRightMargin(0);
		baseConfig.setLayoutData(dictionarydata);
		baseConfig.setAlignment(SWT.CENTER);
		baseConfig.setText(" 基本设置");
		baseConfig.setImage(ImageRegistryUtil.getRegistry().get("dictionary"));
		baseConfig.setBackground(new Color(null, 242,242,242));
		baseConfig.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));

		baseConfig.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=baseConfigComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
			}
		});
		
		//翻译
		functionConfig=new CLabel(listPanel, SWT.NONE);
		FormData translateiondata=new FormData();
		translateiondata.left = new FormAttachment(0);
		translateiondata.top=new FormAttachment(baseConfig,0);
		translateiondata.right=new FormAttachment(100,0);
		translateiondata.height=35;
		functionConfig.setLayoutData(translateiondata);
		functionConfig.setImage(ImageRegistryUtil.getRegistry().get("translation"));
		functionConfig.setAlignment(SWT.CENTER);
		functionConfig.setText(" 功能设置");
		functionConfig.setBackground(new Color(null, 242,242,242));
		functionConfig.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		functionConfig.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {

			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=functionConfigComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//生词本
		offLineDictionary=new CLabel(listPanel, SWT.None);
		FormData notedata=new FormData();
		notedata.left = new FormAttachment(0);
		notedata.top=new FormAttachment(functionConfig,0);
		notedata.right=new FormAttachment(100,0);
		notedata.height=35;
		offLineDictionary.setAlignment(SWT.CENTER);
		offLineDictionary.setText(" 离线词典");
		offLineDictionary.setImage(ImageRegistryUtil.getRegistry().get("note"));
		offLineDictionary.setLayoutData(notedata);
		offLineDictionary.setBackground(new Color(null, 242,242,242));
		offLineDictionary.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		offLineDictionary.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=offLineDictionaryComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//背单词
		hotKeyConfig=new CLabel(listPanel, SWT.None);
		FormData worddata=new FormData();
		worddata.left = new FormAttachment(0);
		worddata.top=new FormAttachment(offLineDictionary,0);
		worddata.right=new FormAttachment(100,0);
		worddata.height=35;
		hotKeyConfig.setAlignment(SWT.CENTER);
		hotKeyConfig.setText(" 热键设置");
		hotKeyConfig.setImage(ImageRegistryUtil.getRegistry().get("word"));
		hotKeyConfig.setLayoutData(worddata);
		hotKeyConfig.setBackground(new Color(null, 242,242,242));
		hotKeyConfig.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		
		hotKeyConfig.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=hotKeyConfigComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {

			}
		});
		
		//阅读
		getWordConfig=new CLabel(listPanel, SWT.None);
		FormData readingdata=new FormData();
		readingdata.left = new FormAttachment(0);
		readingdata.top=new FormAttachment(hotKeyConfig,0);
		readingdata.right=new FormAttachment(100,0);
		readingdata.height=35;
		getWordConfig.setAlignment(SWT.CENTER);
		getWordConfig.setText(" 取词划译");
		getWordConfig.setImage(ImageRegistryUtil.getRegistry().get("reading"));
		getWordConfig.setLayoutData(readingdata);
		getWordConfig.setBackground(new Color(null, 242,242,242));
		getWordConfig.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		
		getWordConfig.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=getWordConfigComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		//听力
		netWorkConfig=new CLabel(listPanel, SWT.None);
		FormData listeningdata=new FormData();
		listeningdata.left = new FormAttachment(0);
		listeningdata.top=new FormAttachment(getWordConfig,0);
		listeningdata.right=new FormAttachment(100,0);
		listeningdata.height=35;
		netWorkConfig.setAlignment(SWT.CENTER);
		netWorkConfig.setText(" 网络设置");
		netWorkConfig.setImage(ImageRegistryUtil.getRegistry().get("listening"));
		netWorkConfig.setLayoutData(listeningdata);
		netWorkConfig.setBackground(new Color(null, 242,242,242));
		netWorkConfig.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		netWorkConfig.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=netWorkConfigComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		//书城
		about=new CLabel(listPanel, SWT.None);
		FormData bookStoresdata=new FormData();
		bookStoresdata.left = new FormAttachment(0);
		bookStoresdata.top=new FormAttachment(netWorkConfig,0);
		bookStoresdata.right=new FormAttachment(100,0);
		bookStoresdata.height=35;
		about.setAlignment(SWT.CENTER);
		about.setText(" 关于软件");
		about.setImage(ImageRegistryUtil.getRegistry().get("bookstory"));
		about.setLayoutData(bookStoresdata);
		about.setBackground(new Color(null, 242,242,242));
		about.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		about.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=aboutComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//监听事件
		baseConfig.addMouseTrackListener(new myMouseAction());
		functionConfig.addMouseTrackListener(new myMouseAction());
		offLineDictionary.addMouseTrackListener(new myMouseAction());
		hotKeyConfig.addMouseTrackListener(new myMouseAction());
		getWordConfig.addMouseTrackListener(new myMouseAction());
		netWorkConfig.addMouseTrackListener(new myMouseAction());
		about.addMouseTrackListener(new myMouseAction());
		
	}
	
	private void CreateCloseButton(Composite parent){
		
//		moveComposite=new Composite(parent, SWT.NONE);
//		moveComposite.setLayout(new FormLayout());
//		FormData moveCompositeData = new FormData();
//		moveCompositeData.top = new FormAttachment(parent,0);
//		moveCompositeData.left = new FormAttachment(0,0);
//		moveCompositeData.right = new FormAttachment(100,0);
//		moveCompositeData.bottom=new FormAttachment(100,0);
//		moveComposite.setLayoutData(moveCompositeData);
		topsashFormHor=new SashForm(parent, SWT.NONE|SWT.HORIZONTAL);
		topsashFormHor.setLayout(new FormLayout());
		FormData topsashFormHorData=new FormData();
		topsashFormHorData.top=new FormAttachment(0,0);
		topsashFormHorData.left=new FormAttachment(0,0);
		topsashFormHorData.right=new FormAttachment(100,0);
		topsashFormHorData.bottom=new FormAttachment(100,0);
		topsashFormHor.setLayoutData(topsashFormHorData);
		
		
		
		leftMoveComposite=new Composite(topsashFormHor, SWT.NONE);
		FormData leftMoveCompositeData=new FormData();
		leftMoveCompositeData.top=new FormAttachment(0,0);
		leftMoveCompositeData.left=new FormAttachment(0,0);
		leftMoveCompositeData.right=new FormAttachment(100,0);
		leftMoveCompositeData.bottom=new FormAttachment(100,0);
		leftMoveComposite.setLayoutData(leftMoveCompositeData);
		
		
		rightMoveComposite=new Composite(topsashFormHor, SWT.NONE);
		FormData rightMoveCompositeData=new FormData();
		rightMoveCompositeData.top=new FormAttachment(0,0);
		rightMoveCompositeData.left=new FormAttachment(leftMoveComposite,0);
		rightMoveCompositeData.right=new FormAttachment(100,0);
		rightMoveCompositeData.bottom=new FormAttachment(100,0);
		rightMoveComposite.setLayout(new FormLayout());
		rightMoveComposite.setLayoutData(rightMoveCompositeData);
		
		topsashFormHor.setWeights(new int[]{5,1});
		leftMoveComposite.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				blnMouseDown=false;
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				blnMouseDown=true;
				xPos=e.x;
				yPos=e.y;
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		leftMoveComposite.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if(blnMouseDown){
					 shell.setLocation(shell.getLocation().x+(e.x-xPos),shell.getLocation().y+(e.y-yPos));
					 shell.layout();
				}
			}
		});
		
		toolBar = new ToolBar(rightMoveComposite, SWT.BAR|SWT.RIGHT_TO_LEFT);
		//toolBar.setBounds(10, 10, 162, 10);
		FormData toolbarFormData = new FormData();
		toolbarFormData.top = new FormAttachment(0,0);
		toolbarFormData.left = new FormAttachment(leftMoveComposite,0);
		toolbarFormData.right = new FormAttachment(100,0);
		//toolbarFormData.height=50;
		toolbarFormData.bottom=new FormAttachment(100,0);
		toolBar.setLayoutData(toolbarFormData);

		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setImage(new Image(null, "E:\\EclipseRCPworkplace\\Words\\icons\\close.png"));
		toolItem.setToolTipText("关闭");
		toolItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
	}
	
	private void createMainPanel(Composite parent){
		
		//主显示框
		mainComposite =new Composite(parent, SWT.NONE);
		FormData mainCompositeData = new FormData();
		mainCompositeData.top = new FormAttachment(toolBar,0);
		mainCompositeData.left = new FormAttachment(0,0);
		mainCompositeData.right = new FormAttachment(100,0);
		mainCompositeData.bottom = new FormAttachment(100,0);
		mainComposite.setLayoutData(mainCompositeData);
		
		/*
		 * 各个选项的布局
		 */
		baseConfigComposite=createdictionary(mainComposite);
		functionConfigComposite=createtranslateion(mainComposite);
		offLineDictionaryComposite=createnote(mainComposite);
		hotKeyConfigComposite=createword(mainComposite);
		getWordConfigComposite=createreading(mainComposite);
		netWorkConfigComposite=createlistening(mainComposite);
		aboutComposite=createbookStores(mainComposite);
		
		stackLayout.topControl=baseConfigComposite;
		mainComposite.setLayout(stackLayout);
	}
	
	private Composite createdictionary(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.NONE);
		comp.setLayout(new FormLayout());
		FormData compData = new FormData();
		compData.top = new FormAttachment(displayComp,0);
		compData.left = new FormAttachment(0,0);
		compData.right = new FormAttachment(100,0);
		compData.bottom = new FormAttachment(100,0);
		comp.setLayoutData(compData);
		
		CLabel l=new CLabel(comp, SWT.NONE);
		FormData lData = new FormData();
		lData.top = new FormAttachment(displayComp,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,0);
		l.setAlignment(SWT.CENTER);
		l.setLayoutData(lData);
		l.setText("基本设置");
		return comp;
	}
	private Composite createtranslateion(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.NONE);
		comp.setLayout(new FormLayout());
		CLabel l=new CLabel(comp, SWT.NONE);
		FormData lData = new FormData();
		lData.top = new FormAttachment(displayComp,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,0);
		l.setLayoutData(lData);
		l.setAlignment(SWT.CENTER);
		l.setText("功能设置");
		return comp;
	}
	private Composite createnote(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.NONE);
		comp.setLayout(new FormLayout());
		CLabel l=new CLabel(comp, SWT.NONE);
		FormData lData = new FormData();
		lData.top = new FormAttachment(displayComp,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,0);
		l.setLayoutData(lData);
		l.setAlignment(SWT.CENTER);
		l.setText("离线词典");
		return comp;
	}
	private Composite createword(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.NONE);
		comp.setLayout(new FormLayout());
		CLabel l=new CLabel(comp, SWT.NONE);
		FormData lData = new FormData();
		lData.top = new FormAttachment(displayComp,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,0);
		l.setLayoutData(lData);
		l.setAlignment(SWT.CENTER);
		l.setText("热键设置");
		return comp;
	}
	private Composite createreading(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.NONE);
		comp.setLayout(new FormLayout());
		CLabel l=new CLabel(comp, SWT.NONE);
		FormData lData = new FormData();
		lData.top = new FormAttachment(displayComp,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,0);
		l.setLayoutData(lData);
		l.setAlignment(SWT.CENTER);
		l.setText("取词划译");
		return comp;
	}
	private Composite createlistening(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.NONE);
		comp.setLayout(new FormLayout());
		CLabel l=new CLabel(comp, SWT.NONE);
		FormData lData = new FormData();
		lData.top = new FormAttachment(displayComp,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,0);
		l.setLayoutData(lData);
		l.setAlignment(SWT.CENTER);
		l.setText("网络设置");
		return comp;
	}
	private Composite createbookStores(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.NONE);
		comp.setLayout(new FormLayout());
		CLabel l=new CLabel(comp, SWT.NONE);
		FormData lData = new FormData();
		lData.top = new FormAttachment(displayComp,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,0);
		l.setLayoutData(lData);
		l.setAlignment(SWT.CENTER);
		l.setText("关于面板");
		return comp;
	}
	
	
	private void refreshComposite(Composite composite){
		composite.layout();
	}
	
	 class myMouseAction implements MouseTrackListener{

		 @Override
			public void mouseHover(MouseEvent e) {
			 if(e.widget.getClass().equals(CLabel.class)){
					CLabel l=(CLabel) e.widget;
					l.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
				}
			}
			@Override
			public void mouseExit(MouseEvent e) {
				if(e.widget.getClass().equals(CLabel.class)){
					CLabel l=(CLabel) e.widget;
					l.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
				}
			}
			@Override
			public void mouseEnter(MouseEvent e) {
				if(e.widget.getClass().equals(CLabel.class)){
					CLabel l=(CLabel) e.widget;
					l.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
				}
			}
	 }

}
