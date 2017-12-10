package com.example.view;

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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.example.constant.Constant;
import com.example.interfaces.ChangeBackGroundColor;
import com.example.util.ImageRegistryUtil;
import com.example.util.NetWorkStatus;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;


public class ConfigWindow {

	protected static Shell shell;
	private ToolBar toolBar;//�رհ�ť��
	private static Display display;
	private Composite mainComposite;//����ʾ����
	protected static StackLayout stackLayout = new StackLayout();//����ʾ����Ĳ���
	
	private Composite mainPanel;//����������
	private Composite listPanel;//ѡ���
	
	//ѡ���Ӧ�Ĳ�������
	private Composite baseConfigComposite;//
	private Composite functionConfigComposite;
	private Composite offLineDictionaryComposite;
	private Composite hotKeyConfigComposite;
	private Composite getWordConfigComposite;
	private Composite netWorkConfigComposite;
	private Composite aboutComposite;
	
	private Composite moveComposite;//�϶���
	private Composite leftMoveComposite;//
	private Composite rightMoveComposite;//
	
	private SashForm sashFormVer;//����
	private SashForm sashFormHor;//����
	private SashForm topsashFormHor;//�����������ƶ���
	
	private CLabel baseConfig;//��������
	private CLabel functionConfig;//��������
	private CLabel offLineDictionary;//���ߴʵ�
	private CLabel hotKeyConfig;//�ȼ�����
	private CLabel getWordConfig;//ȡ�ʻ���
	private CLabel netWorkConfig;//��������
	private CLabel about;//���ڽ���

	
	private static Boolean blnMouseDown=false;
	private static int xPos=0;
	private static int yPos=0;
	private Button btnCheckButton;
	private CLabel lbcloseWindow;
	private CLabel lbnetwork;
	private CLabel lbType;
	private CLabel lbAddress;
	private CLabel lbPort;
	private CLabel lbUserName;
	private CLabel lbpassWord;
	private Button btNetWorkCheck;
	private StyledText port_styledText;
	private StyledText userName_styledText;
	private StyledText userPassWord_styledText;
	private CLabel lbNetWorkResult;
	private StyledText styledText;
	
	private Color myColor=new Color(display,242,242,242);
	
	//�ص������ set
	private ChangeBackGroundColor change;
	private Button btgreen;
	private Button btblue;
	
	public void setChange(ChangeBackGroundColor change) {
		this.change = change;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ConfigWindow window = new ConfigWindow();
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
		shell = new Shell(SWT.NONE);
		shell.setBounds(400, 200, 500, 300);
		shell.setLayout(new FormLayout());
		/**
		 * ������
		 */
		
		//��ֱ
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
		
		listPanel.setBackground(myColor);
		/**
		 * �����ر���
		 */
		CreateCloseButton(topPanel);
		createMainPanel(mainPanel);
		
		
//		//�ʵ�
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
		baseConfig.setText(" ��������");
		baseConfig.setBackground(myColor);
		baseConfig.setImage(ImageRegistryUtil.getRegistry().get("dictionary"));
//		baseConfig.setBackground(new Color(null, 242,242,242));
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
		
		//����
		functionConfig=new CLabel(listPanel, SWT.NONE);
		FormData translateiondata=new FormData();
		translateiondata.left = new FormAttachment(0);
		translateiondata.top=new FormAttachment(baseConfig,0);
		translateiondata.right=new FormAttachment(100,0);
		translateiondata.height=35;
		functionConfig.setLayoutData(translateiondata);
		functionConfig.setImage(ImageRegistryUtil.getRegistry().get("translation"));
		functionConfig.setAlignment(SWT.CENTER);
		functionConfig.setText(" ��������");
		functionConfig.setBackground(myColor);
//		functionConfig.setBackground(new Color(null, 242,242,242));
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
		
		offLineDictionary=new CLabel(listPanel, SWT.None);
		FormData notedata=new FormData();
		notedata.left = new FormAttachment(0);
		notedata.top=new FormAttachment(functionConfig,0);
		notedata.right=new FormAttachment(100,0);
		notedata.height=35;
		offLineDictionary.setAlignment(SWT.CENTER);
		offLineDictionary.setText(" ���ߴʵ�");
		offLineDictionary.setBackground(myColor);
		offLineDictionary.setImage(ImageRegistryUtil.getRegistry().get("note"));
		offLineDictionary.setLayoutData(notedata);
		//offLineDictionary.setBackground(new Color(null, 242,242,242));
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
		
		
		//������
		hotKeyConfig=new CLabel(listPanel, SWT.None);
		FormData worddata=new FormData();
		worddata.left = new FormAttachment(0);
		worddata.top=new FormAttachment(offLineDictionary,0);
		worddata.right=new FormAttachment(100,0);
		worddata.height=35;
		hotKeyConfig.setAlignment(SWT.CENTER);
		hotKeyConfig.setText(" �ȼ�����");
		hotKeyConfig.setBackground(myColor);
		hotKeyConfig.setImage(ImageRegistryUtil.getRegistry().get("word"));
		hotKeyConfig.setLayoutData(worddata);
		//hotKeyConfig.setBackground(new Color(null, 242,242,242));
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
		
		//�Ķ�
		getWordConfig=new CLabel(listPanel, SWT.None);
		FormData readingdata=new FormData();
		readingdata.left = new FormAttachment(0);
		readingdata.top=new FormAttachment(hotKeyConfig,0);
		readingdata.right=new FormAttachment(100,0);
		readingdata.height=35;
		getWordConfig.setAlignment(SWT.CENTER);
		getWordConfig.setText(" ȡ�ʻ���");
		getWordConfig.setBackground(myColor);
		getWordConfig.setImage(ImageRegistryUtil.getRegistry().get("reading"));
		getWordConfig.setLayoutData(readingdata);
//		getWordConfig.setBackground(new Color(null, 242,242,242));
		getWordConfig.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		
		getWordConfig.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=getWordConfigComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
			}
		});
		//����
		netWorkConfig=new CLabel(listPanel, SWT.None);
		FormData listeningdata=new FormData();
		listeningdata.left = new FormAttachment(0);
		listeningdata.top=new FormAttachment(getWordConfig,0);
		listeningdata.right=new FormAttachment(100,0);
		listeningdata.height=35;
		netWorkConfig.setAlignment(SWT.CENTER);
		netWorkConfig.setText(" ��������");
		netWorkConfig.setImage(ImageRegistryUtil.getRegistry().get("listening"));
		netWorkConfig.setLayoutData(listeningdata);
		netWorkConfig.setBackground(myColor);
//		netWorkConfig.setBackground(new Color(null, 242,242,242));
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
		//���
		about=new CLabel(listPanel, SWT.None);
		FormData bookStoresdata=new FormData();
		bookStoresdata.left = new FormAttachment(0);
		bookStoresdata.top=new FormAttachment(netWorkConfig,0);
		bookStoresdata.right=new FormAttachment(100,0);
		bookStoresdata.height=35;
		about.setAlignment(SWT.CENTER);
		about.setText(" �������");
		about.setBackground(myColor);
		about.setImage(ImageRegistryUtil.getRegistry().get("bookstory"));
		about.setLayoutData(bookStoresdata);
//		about.setBackground(new Color(null, 242,242,242));
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
		
		
		//�����¼�
		baseConfig.addMouseTrackListener(new myMouseAction());
		functionConfig.addMouseTrackListener(new myMouseAction());
		offLineDictionary.addMouseTrackListener(new myMouseAction());
		hotKeyConfig.addMouseTrackListener(new myMouseAction());
		getWordConfig.addMouseTrackListener(new myMouseAction());
		netWorkConfig.addMouseTrackListener(new myMouseAction());
		about.addMouseTrackListener(new myMouseAction());
		
	}

	
	/*
	 * �����ر���
	 */
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
		toolItem.setToolTipText("�ر�");
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
		
		//����ʾ��
		mainComposite =new Composite(parent, SWT.NONE);
		FormData mainCompositeData = new FormData();
		mainCompositeData.top = new FormAttachment(toolBar,0);
		mainCompositeData.left = new FormAttachment(0,0);
		mainCompositeData.right = new FormAttachment(100,0);
		mainCompositeData.bottom = new FormAttachment(100,0);
		mainComposite.setLayoutData(mainCompositeData);
		
		/*
		 * ����ѡ��Ĳ���
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
		
		btnCheckButton = new Button(comp, SWT.CHECK);
		FormData fd_btnCheckButton = new FormData();
		fd_btnCheckButton.left = new FormAttachment(0, 124);
		fd_btnCheckButton.right = new FormAttachment(100, -138);
		fd_btnCheckButton.height=30;
		btnCheckButton.setAlignment(SWT.CENTER);
		btnCheckButton.setLayoutData(fd_btnCheckButton);
		
		btnCheckButton.setText("��������");
		
		CLabel lblbaseConfig = new CLabel(comp, SWT.NONE);
		fd_btnCheckButton.top = new FormAttachment(lblbaseConfig, 19);
		FormData fd_lblbaseConfig = new FormData();
		fd_lblbaseConfig.bottom = new FormAttachment(0, 30);
		fd_lblbaseConfig.right = new FormAttachment(0, 150);
		fd_lblbaseConfig.top = new FormAttachment(0, 10);
		fd_lblbaseConfig.left = new FormAttachment(0, 20);
		lblbaseConfig.setLayoutData(fd_lblbaseConfig);
		lblbaseConfig.setFont(new Font(null, "����", 14, SWT.NORMAL));
		lblbaseConfig.setText("��������");
		
		lbcloseWindow = new CLabel(comp, SWT.NONE);
		FormData fd_lbcloseWindow = new FormData();
		fd_lbcloseWindow.top = new FormAttachment(btnCheckButton, 6);
		fd_lbcloseWindow.left = new FormAttachment(0, 122);
		lbcloseWindow.setLayoutData(fd_lbcloseWindow);
		lbcloseWindow.setText("�ر�������ʱ:");
		
		Button btnRadioButton = new Button(comp, SWT.RADIO);
		FormData fd_btnRadioButton = new FormData();
		fd_btnRadioButton.top = new FormAttachment(lbcloseWindow, 6);
		fd_btnRadioButton.left = new FormAttachment(0, 143);
		btnRadioButton.setLayoutData(fd_btnRadioButton);
		btnRadioButton.setText("������������");
		
		Button buttonexit = new Button(comp, SWT.RADIO);
		buttonexit.setText("��ȫ�˳�");
		FormData fd_buttonexit = new FormData();
		fd_buttonexit.top = new FormAttachment(btnRadioButton, 6);
		fd_buttonexit.left = new FormAttachment(btnRadioButton, 0, SWT.LEFT);
		buttonexit.setLayoutData(fd_buttonexit);
		
		CLabel lbskin = new CLabel(comp, SWT.NONE);
		lbskin.setText("Ƥ��");
		FormData fd_lbskin = new FormData();
		fd_lbskin.top = new FormAttachment(buttonexit, 6);
		fd_lbskin.left = new FormAttachment(btnCheckButton, 0, SWT.LEFT);
		lbskin.setLayoutData(fd_lbskin);
		
		btgreen = new Button(comp, SWT.RADIO);
		btgreen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(change!=null){
					change.backGroundColorChanged(display.getSystemColor(SWT.COLOR_GREEN));
				}
			}
		});
		FormData fd_btgreen = new FormData();
		fd_btgreen.bottom = new FormAttachment(buttonexit, 33, SWT.BOTTOM);
		fd_btgreen.top = new FormAttachment(buttonexit, 13);
		fd_btgreen.left = new FormAttachment(lbskin, 6);
		btgreen.setLayoutData(fd_btgreen);
		btgreen.setText("��ɫ");
		
		btblue = new Button(comp, SWT.RADIO);
		btblue.setText("��ɫ");
		FormData fd_btblue = new FormData();
		fd_btblue.top = new FormAttachment(btgreen, 2, SWT.TOP);
		fd_btblue.left = new FormAttachment(btgreen, 6);
		btblue.setLayoutData(fd_btblue);
		
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
		l.setText("��������");
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
		l.setText("���ߴʵ�");
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
		l.setText("�ȼ�����");
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
		l.setText("ȡ�ʻ���");
		return comp;
	}
	private Composite createlistening(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.NONE);
		comp.setLayout(new FormLayout());
		
		lbnetwork = new CLabel(comp, SWT.NONE);
		lbnetwork.setText("\u7F51\u7EDC\u8BBE\u7F6E");
		lbnetwork.setFont(SWTResourceManager.getFont("����", 14, SWT.NORMAL));
		FormData fd_lbnetwork = new FormData();
		fd_lbnetwork.top = new FormAttachment(0, 10);
		fd_lbnetwork.left = new FormAttachment(0, 57);
		lbnetwork.setLayoutData(fd_lbnetwork);
		
		lbType = new CLabel(comp, SWT.NONE);
		FormData fd_lbType = new FormData();
		fd_lbType.top = new FormAttachment(lbnetwork, 6);
		fd_lbType.right = new FormAttachment(lbnetwork, 0, SWT.RIGHT);
		fd_lbType.left = new FormAttachment(0, 109);
		lbType.setLayoutData(fd_lbType);
		lbType.setText("����");
		
		CCombo typeCombo = new CCombo(comp, SWT.BORDER|SWT.DROP_DOWN | SWT.READ_ONLY);
		typeCombo.setText("HTTP");
		FormData fd_typeCombo = new FormData();
		fd_typeCombo.right = new FormAttachment(100, -68);
		fd_typeCombo.left = new FormAttachment(lbType, 24);
		fd_typeCombo.top = new FormAttachment(lbType, 0, SWT.TOP);
		typeCombo.setLayoutData(fd_typeCombo);
		typeCombo.add("�������κδ���", 0);
		typeCombo.add("HTTP", 1);
		typeCombo.add("Socks5", 2);
		
		
		Composite typeComposite=new Composite(comp, SWT.NONE);
		typeComposite.setLayout(new FormLayout());
		FormData fd_typeComposite = new FormData();
		fd_typeComposite.bottom = new FormAttachment(lbType, 176, SWT.BOTTOM);
		fd_typeComposite.top = new FormAttachment(lbType, 16);
		fd_typeComposite.right = new FormAttachment(typeCombo, 0, SWT.RIGHT);
		fd_typeComposite.left = new FormAttachment(0, 99);
		typeComposite.setLayoutData(fd_typeComposite);
		
		lbAddress = new CLabel(typeComposite, SWT.NONE);
		FormData fd_lbAddress = new FormData();
		fd_lbAddress.top = new FormAttachment(0, 10);
		fd_lbAddress.left = new FormAttachment(0, 10);
		lbAddress.setLayoutData(fd_lbAddress);
		lbAddress.setText("��ַ");
		
		lbPort = new CLabel(typeComposite, SWT.NONE);
		FormData fd_lbPort = new FormData();
		fd_lbPort.top = new FormAttachment(lbAddress, 5);
		fd_lbPort.right = new FormAttachment(lbAddress, 0, SWT.RIGHT);
		lbPort.setLayoutData(fd_lbPort);
		lbPort.setText("�˿�");
		
		lbUserName = new CLabel(typeComposite, SWT.NONE);
		FormData fd_lbUserName = new FormData();
		fd_lbUserName.top = new FormAttachment(lbPort, 5);
		fd_lbUserName.left = new FormAttachment(lbAddress, 0, SWT.LEFT);
		lbUserName.setLayoutData(fd_lbUserName);
		lbUserName.setText("�û���");
		
		lbpassWord = new CLabel(typeComposite, SWT.NONE);
		FormData fd_lbpassWord = new FormData();
		fd_lbpassWord.top = new FormAttachment(lbUserName, 5);
		fd_lbpassWord.left = new FormAttachment(0, 10);
		lbpassWord.setLayoutData(fd_lbpassWord);
		lbpassWord.setText("����");
		
		btNetWorkCheck = new Button(typeComposite, SWT.NONE);
		FormData fd_btNetWorkCheck = new FormData();
		fd_btNetWorkCheck.top = new FormAttachment(lbpassWord, 6);
		fd_btNetWorkCheck.left = new FormAttachment(0);
		btNetWorkCheck.setLayoutData(fd_btNetWorkCheck);
		btNetWorkCheck.setText("��������");
		btNetWorkCheck.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				styledText.setText("���ڲ����������ӣ����Ժ�");
				//�¿�һ���߳�ȥ ��������������� ���ز��Խ�����ַ�����
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String result = NetWorkStatus.netWorkCheckOut();
						if(result.equals("��������")){
							display.syncExec(new Runnable() {
								
								@Override
								public void run() {
									styledText.setText("��������");
								}
							});
						}else
						{
							display.syncExec(new Runnable() {
								
								@Override
								public void run() {
									styledText.setText("�������");
								}
							});
						}
					}
				}).start();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		StyledText address_styledText = new StyledText(typeComposite, SWT.BORDER);
		FormData fd_address_styledText = new FormData();
		fd_address_styledText.top = new FormAttachment(lbAddress, 0, SWT.TOP);
		fd_address_styledText.right = new FormAttachment(100, -36);
		address_styledText.setLayoutData(fd_address_styledText);
		
		port_styledText = new StyledText(typeComposite, SWT.BORDER);
		FormData fd_port_styledText = new FormData();
		fd_port_styledText.top = new FormAttachment(lbPort, 0, SWT.TOP);
		fd_port_styledText.left = new FormAttachment(address_styledText, 0, SWT.LEFT);
		port_styledText.setLayoutData(fd_port_styledText);
		
		userName_styledText = new StyledText(typeComposite, SWT.BORDER);
		FormData fd_userName_styledText = new FormData();
		fd_userName_styledText.top = new FormAttachment(lbUserName, 0, SWT.TOP);
		fd_userName_styledText.left = new FormAttachment(address_styledText, 0, SWT.LEFT);
		userName_styledText.setLayoutData(fd_userName_styledText);
		
		userPassWord_styledText = new StyledText(typeComposite, SWT.BORDER);
		FormData fd_userPassWord_styledText = new FormData();
		fd_userPassWord_styledText.top = new FormAttachment(lbpassWord, 0, SWT.TOP);
		fd_userPassWord_styledText.left = new FormAttachment(address_styledText, 0, SWT.LEFT);
		userPassWord_styledText.setLayoutData(fd_userPassWord_styledText);
		
		styledText = new StyledText(typeComposite, SWT.NONE|SWT.READ_ONLY);
		FormData fd_styledText = new FormData();
		fd_styledText.top = new FormAttachment(btNetWorkCheck, 0, SWT.TOP);
		fd_styledText.left = new FormAttachment(address_styledText, 0, SWT.LEFT);
		styledText.setLayoutData(fd_styledText);
		
//		lbNetWorkResult = new CLabel(typeComposite, SWT.NONE);
//		FormData fd_lbNetWorkResult = new FormData();
//		fd_lbNetWorkResult.top = new FormAttachment(btNetWorkCheck, 0, SWT.TOP);
//		fd_lbNetWorkResult.right = new FormAttachment(address_styledText, 50, SWT.RIGHT);
//		lbNetWorkResult.setLayoutData(fd_lbNetWorkResult);
//		lbNetWorkResult.setText("");
		
		
		//�������ͼ���
		typeCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				int number=typeCombo.getSelectionIndex();
				if(number==0){
					typeComposite.dispose();
				}else if(number==1){
					//HTTP ��������
					if(typeComposite.isDisposed()){
						
					}
				}else if(number==2){
					
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
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
		l.setText(Constant.AppIntroduce.ABOUT_APPLICATION);
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

