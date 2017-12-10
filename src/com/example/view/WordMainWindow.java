package com.example.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.example.constant.Constant;
import com.example.http.SearchWordHttp;
import com.example.http.Translation;
import com.example.interfaces.ChangeBackGroundColor;
import com.example.modle.Word;
import com.example.provider.TableViewerContentProvider;
import com.example.provider.TableViewerLabelProvider;
import com.example.service.SpeakService;
import com.example.util.DataTimeNowStringUtil;
import com.example.util.GetPronunciationUtil;
import com.example.util.ImageRegistryUtil;
import com.example.util.PronunciationUtil;
import com.example.util.TranslationAnalyzeUitl;
import com.example.util.WordAnalyzeJsonUtil;


public class WordMainWindow {

	public static Shell shell;
	private Table table;
	private SpeakService tts;
	private SWTMediaPlayer mediaPlayers;//播放器
	
	private ToolBar toolBar;//关闭按钮栏
	private SashForm moveComposite;//顶部移动栏
	private Composite rightmoveComposite;//顶部移动栏
	private Composite leftmoveComposite;//顶部移动栏
	private static Display display;
	private Composite mainComposite;//主显示界面
	private Font leftPanelFont;
	protected static StackLayout stackLayout = new StackLayout();//主显示界面的布局
	
	private Composite mainPanel;//右上布局
//	private Composite tablePanel;//右下布局
	
	//选项对应的布局容器
	private Composite dictionaryComposite;//
	private Composite translateionComposite;
	private Composite noteComposite;
	private Composite wordComposite;
	private Composite readingComposite;
	private Composite listeningComposite;
	private Composite bookStoresComposite;
	
	private Composite leftPanel;//左侧面板
	
	private SashForm sashFormHor;//左侧
	private SashForm sashFormVer;//右侧
	private CLabel dictionary;//词典
	private CLabel translateion;//翻译
	private CLabel note;//生词本
	private CLabel word;//背单词
	private CLabel reading;//阅读
	private CLabel listening;//听力
	private CLabel bookStores;//书城
	
	
	private StyledText searchlist=null;
	private Composite disPlayComposite=null;//查询结果显示面板
	private Composite everyDayEnglishComposite=null;//每日一句面板
	private StyledText searchText=null; 
	private Composite searchComposite=null;
	
	
	private static WordAnalyzeJsonUtil wordArray=new WordAnalyzeJsonUtil();
	
	private static Boolean blnMouseDown=false;
	private static int xPos=0;
	private static int yPos=0;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WordMainWindow window = new WordMainWindow();
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
		leftPanelFont=new Font(display, "宋体", 13, SWT.NORMAL);
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
		shell.setText("金山词霸Personal");
		shell.setBounds(150, 50, 1000, 600);
		shell.setLayout(new FormLayout());
		shell.setImage(ImageRegistryUtil.getRegistry("application"));
		
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(final ShellEvent e) {
                MessageBox messageBox = new MessageBox(shell,SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
                messageBox.setText("确定");
                messageBox.setMessage("你确定要退出吗？");
                int rc = messageBox.open();
                if (rc == SWT.CANCEL) {
                    e.doit = false;
                }else{
                	e.doit = true;
                    System.exit(0);
                }
            }
		});
		
		/**
		 * 主窗口
		 */
		sashFormHor = new SashForm(shell, SWT.HORIZONTAL);
		FormData sashFormHorData = new FormData();
		sashFormHorData.top = new FormAttachment(0,0);
		sashFormHorData.left = new FormAttachment(0,0);
		sashFormHorData.right = new FormAttachment(100,0);
		sashFormHorData.bottom = new FormAttachment(100,-25);
		sashFormHor.setLayoutData(sashFormHorData);
		sashFormHor.setBackground(new Color(null, 30,144,208));
		
		leftPanel = new Composite(sashFormHor, SWT.NONE);
		leftPanel.setBackground(new Color(null, 30,144,255));

		sashFormVer = new SashForm(sashFormHor, SWT.VERTICAL);
		
		moveComposite=new SashForm(sashFormVer, SWT.HORIZONTAL);
		moveComposite.setLayout(new FormLayout());
		FormData moveCompositeData = new FormData();
		moveCompositeData.top = new FormAttachment(0,0);
		moveCompositeData.left = new FormAttachment(0,0);
		moveCompositeData.right = new FormAttachment(100,0);
		moveCompositeData.height=20;
		moveComposite.setLayoutData(moveCompositeData);
		
		mainPanel = new Composite(sashFormVer, SWT.NONE);
		mainPanel.setLayout(new FormLayout());
//		tablePanel = new Composite(sashFormVer, SWT.NONE);
//		tablePanel.setLayout(new FormLayout());
		sashFormVer.setWeights(new int[]{31,544});
		sashFormHor.setWeights(new int[]{3,17});
		
		//顶部栏
		moveComposite.addMouseListener(new shellMouseListener());
		moveComposite.addMouseMoveListener(new shellMouseMoveListener());
		leftmoveComposite=new Composite(moveComposite, SWT.NONE);
		rightmoveComposite=new Composite(moveComposite, SWT.NONE);
		rightmoveComposite.setLayout(new FormLayout());
		moveComposite.setWeights(new int[]{4,1});
		
		leftmoveComposite.addMouseListener(new shellMouseListener());
		leftmoveComposite.addMouseMoveListener(new shellMouseMoveListener());
		
		/**
		 * 顶部关闭栏
		 */
		CreateCloseButton(rightmoveComposite);
		createMainPanel(mainPanel);
		
		//词典
		dictionary=new CLabel(leftPanel, SWT.NONE);
		leftPanel.setLayout(new FormLayout());
		FormData dictionarydata=new FormData();
		dictionarydata.left = new FormAttachment(0,0);
		dictionarydata.top=new FormAttachment(leftPanel,0);
		dictionarydata.right=new FormAttachment(100,0);
		dictionarydata.height=50;
		dictionary.setLeftMargin(0);
		dictionary.setRightMargin(0);
		dictionary.setLayoutData(dictionarydata);
		dictionary.setAlignment(SWT.CENTER);
		
		//获取单词
		//http://dict-co.iciba.com/api/dictionary.php?w=good&type=json&key=379BEFEF668844B088FBA310F375EB28 
		dictionary.setText(" 词典");
		dictionary.setFont(leftPanelFont);
		dictionary.setImage(ImageRegistryUtil.getRegistry().get("dictionary"));
		dictionary.setBackground(new Color(null, 30,144,255));
		dictionary.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		dictionary.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=dictionaryComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
			}
		});
		
		//翻译
		translateion=new CLabel(leftPanel, SWT.NONE);
		FormData translateiondata=new FormData();
		translateiondata.left = new FormAttachment(0);
		translateiondata.top=new FormAttachment(dictionary,0);
		translateiondata.right=new FormAttachment(100,0);
		translateiondata.height=50;
		translateion.setLayoutData(translateiondata);
		translateion.setImage(ImageRegistryUtil.getRegistry().get("translation"));
		translateion.setAlignment(SWT.CENTER);
		translateion.setText(" 翻译");
		translateion.setFont(leftPanelFont);
		translateion.setBackground(new Color(null, 30,144,255));
		translateion.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		translateion.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=translateionComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//生词本
		note=new CLabel(leftPanel, SWT.None);
		FormData notedata=new FormData();
		notedata.left = new FormAttachment(0);
		notedata.top=new FormAttachment(translateion,0);
		notedata.right=new FormAttachment(100,0);
		notedata.height=50;
		note.setAlignment(SWT.CENTER);
		note.setText(" 生词本");
		note.setFont(leftPanelFont);
		note.setImage(ImageRegistryUtil.getRegistry().get("note"));
		note.setLayoutData(notedata);
		note.setBackground(new Color(null, 30,144,255));
		note.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		note.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=noteComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//背单词
		word=new CLabel(leftPanel, SWT.NONE);
		FormData worddata=new FormData();
		worddata.left = new FormAttachment(0);
		worddata.top=new FormAttachment(note,0);
		worddata.right=new FormAttachment(100,0);
		worddata.height=50;
		word.setAlignment(SWT.CENTER);
		word.setText(" 背单词");
		word.setFont(leftPanelFont);
		word.setImage(ImageRegistryUtil.getRegistry().get("word"));
		word.setLayoutData(worddata);
		word.setBackground(new Color(null, 30,144,255));
		word.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		
		word.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=wordComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//阅读
		reading=new CLabel(leftPanel, SWT.NONE);
		FormData readingdata=new FormData();
		readingdata.left = new FormAttachment(0);
		readingdata.top=new FormAttachment(word,0);
		readingdata.right=new FormAttachment(100,0);
		readingdata.height=50;
		reading.setAlignment(SWT.CENTER);
		reading.setText(" 阅读");
		reading.setFont(leftPanelFont);
		reading.setImage(ImageRegistryUtil.getRegistry().get("reading"));
		reading.setLayoutData(readingdata);
		reading.setBackground(new Color(null, 30,144,255));
		reading.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		
		reading.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=readingComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
			}
		});
		//听力
		listening=new CLabel(leftPanel, SWT.NONE);
		FormData listeningdata=new FormData();
		listeningdata.left = new FormAttachment(0);
		listeningdata.top=new FormAttachment(reading,0);
		listeningdata.right=new FormAttachment(100,0);
		listeningdata.height=50;
		listening.setAlignment(SWT.CENTER);
		listening.setText(" 听力");
		listening.setFont(leftPanelFont);
		listening.setImage(ImageRegistryUtil.getRegistry().get("listening"));
		listening.setLayoutData(listeningdata);
		listening.setBackground(new Color(null, 30,144,255));
		listening.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		listening.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=listeningComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		//书城
		bookStores=new CLabel(leftPanel, SWT.NONE);
		FormData bookStoresdata=new FormData();
		bookStoresdata.left = new FormAttachment(0);
		bookStoresdata.top=new FormAttachment(listening,0);
		bookStoresdata.right=new FormAttachment(100,0);
		bookStoresdata.height=50;
		bookStores.setAlignment(SWT.CENTER);
		bookStores.setText(" 书城");
		bookStores.setFont(leftPanelFont);
		bookStores.setImage(ImageRegistryUtil.getRegistry().get("bookstory"));
		bookStores.setLayoutData(bookStoresdata);
		bookStores.setBackground(new Color(null, 30,144,255));
		bookStores.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		bookStores.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {

			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				stackLayout.topControl=bookStoresComposite;
				refreshComposite(mainComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//监听事件
		dictionary.addMouseTrackListener(new myMouseAction());
		translateion.addMouseTrackListener(new myMouseAction());
		note.addMouseTrackListener(new myMouseAction());
		word.addMouseTrackListener(new myMouseAction());
		reading.addMouseTrackListener(new myMouseAction());
		listening.addMouseTrackListener(new myMouseAction());
		bookStores.addMouseTrackListener(new myMouseAction());

		/**
		 * 状态栏
		 */
		Composite statusBar = new Composite(shell, SWT.BORDER);
		statusBar.setLayout(new FormLayout());
		FormData statusBarData = new FormData();
		statusBarData.top = new FormAttachment(sashFormHor,0);
		statusBarData.left = new FormAttachment(0,0);
		statusBarData.right = new FormAttachment(100,0);
		statusBarData.bottom = new FormAttachment(100,0);
		statusBar.setLayoutData(statusBarData);
		
		
		/**
		 * 状态栏信息
		 */
		CLabel statusLabel=new CLabel(statusBar, SWT.FLAT|SWT.RIGHT);
		statusLabel.setLayout(new FormLayout());
		FormData statusLabelData = new FormData();
		statusLabelData.top = new FormAttachment(statusBar,0);
		statusLabelData.left = new FormAttachment(0,0);
		statusLabelData.right = new FormAttachment(100,0);
		statusLabelData.height=25;
		statusLabel.setLayoutData(statusLabelData);
		statusLabel.setText("Author:Wang Jinshanshan");
		
		
	}

	
	/*
	 * 顶部关闭栏
	 */
	private void CreateCloseButton(Composite parent){

		toolBar = new ToolBar(parent, SWT.BAR|SWT.RIGHT_TO_LEFT);
		FormData toolbarFormData = new FormData();
		toolbarFormData.top = new FormAttachment(0,0);
		toolbarFormData.left = new FormAttachment(leftmoveComposite,0);
		toolbarFormData.right = new FormAttachment(100,0);
		toolbarFormData.bottom=new FormAttachment(100,0);
		toolBar.setLayoutData(toolbarFormData);
		
		ToolItem toolItem = new ToolItem(toolBar, SWT.FLAT|SWT.RIGHT_TO_LEFT );
		toolItem.setImage(ImageRegistryUtil.getRegistry("close"));
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
		ToolItem minToolItem = new ToolItem(toolBar, SWT.FLAT|SWT.RIGHT_TO_LEFT );
		minToolItem.setImage(ImageRegistryUtil.getRegistry("Minimize_24px"));
		minToolItem.setToolTipText("最小化");
		minToolItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				shell.setMinimized(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ToolItem toolItems = new ToolItem(toolBar, SWT.FLAT|SWT.RIGHT_TO_LEFT );
		toolItems.setImage(ImageRegistryUtil.getRegistry("config"));
		toolItems.setToolTipText("设置");
		toolItems.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				ConfigWindow config=new ConfigWindow();
				config.setChange(new ChangeBackGroundColor() {
					
					@Override
					public void backGroundColorChanged(Color color) {
						leftPanel.setBackground(color);
					}
				});
				config.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void createMainPanel(Composite parent){
		//主显示框
		mainComposite =new Composite(parent, SWT.NONE);
		FormData mainCompositeData = new FormData();
		mainCompositeData.top = new FormAttachment(moveComposite,0);
		mainCompositeData.left = new FormAttachment(0,0);
		mainCompositeData.right = new FormAttachment(100,0);
		mainCompositeData.bottom = new FormAttachment(100,0);
		mainComposite.setLayoutData(mainCompositeData);
		
		/*
		 * 各个选项的布局
		 */
		dictionaryComposite=createdictionary(mainComposite);
		translateionComposite=createtranslateion(mainComposite);
		noteComposite=createnote(mainComposite);
		wordComposite=createword(mainComposite);
		readingComposite=createreading(mainComposite);
		listeningComposite=createlistening(mainComposite);
		bookStoresComposite=createbookStores(mainComposite);
		
		stackLayout.topControl=dictionaryComposite;
		mainComposite.setLayout(stackLayout);
		
		
		
	}
	private Composite createdictionary(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.BORDER);
		comp.setLayout(new FormLayout());
		FormData compData = new FormData();
		compData.top = new FormAttachment(displayComp,0);
		compData.left = new FormAttachment(0,0);
		compData.right = new FormAttachment(100,0);
		compData.bottom = new FormAttachment(100,0);
		comp.setLayoutData(compData);
		comp.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(searchlist!=null&&!searchlist.isDisposed()){
					searchlist.dispose();
				}
				if(everyDayEnglishComposite!=null&&everyDayEnglishComposite.isDisposed()){
					//重新创建
					if(searchText.getText().isEmpty()){
						//每日一句的创建
						everyDayEnglish(comp);
						//TODO 
					}
					if(!searchText.getText().isEmpty()){
						//查词显示面板的创建
						disPlaySearchResult(comp);
					}
					comp.layout();
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
			}
		});
		
		searchComposite=new Composite(comp, SWT.NONE);
		searchComposite.setLayout(new FormLayout());
		FormData searchCompositeData = new FormData();
		searchCompositeData.top = new FormAttachment(displayComp,30);
		searchCompositeData.left = new FormAttachment(0,50);
		searchCompositeData.right = new FormAttachment(100,-50);
		searchCompositeData.height=35;
		searchComposite.setBackground(new Color(display, 118,209,127));
		searchComposite.setLayoutData(searchCompositeData);
		
		
		/*
		 * 
		 * 搜索框
		 */
		searchText=new StyledText(searchComposite, SWT.NONE);
		FormData searchTextData = new FormData();
		searchTextData.top = new FormAttachment(searchComposite,2);
		searchTextData.left = new FormAttachment(0,2);
		searchTextData.right = new FormAttachment(100,-50);
		searchTextData.bottom=new FormAttachment(100,-2);
		searchText.setLayoutData(searchTextData);
		searchText.setAlignment(SWT.CENTER);

		searchText.addMouseListener(new StyledTextMoveListener(comp,searchComposite,searchlist));
		
		
		/*
		 * 
		 * 搜索按钮
		 */
		CLabel search=new CLabel(searchComposite, SWT.NONE);
		FormData searchdata=new FormData();
		searchdata.top=new FormAttachment(searchComposite,2);
		searchdata.left = new FormAttachment(searchText,0);
		searchdata.right = new FormAttachment(100,-2);
		searchdata.bottom=new FormAttachment(100,-2);
		search.setLayoutData(searchdata);
		search.setAlignment(SWT.CENTER);
		search.setBackground(new Color(display, 118,209,127));
		search.setImage(ImageRegistryUtil.getRegistry("searchs_32"));
		search.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				search.setBackground(new Color(display, 118,209,127));
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				if(searchlist!=null&&!searchlist.isDisposed()){
					searchlist.dispose();
				}
				if(disPlayComposite!=null&&disPlayComposite.isDisposed()){
					disPlayComposite=new Composite(comp, SWT.NONE);
					FormData relustdata=new FormData();
					relustdata.top=new FormAttachment(searchComposite,2);
					relustdata.left = new FormAttachment(0,50);
					relustdata.right = new FormAttachment(100,-100);
					relustdata.bottom=new FormAttachment(100,0);
					disPlayComposite.setLayoutData(relustdata);
					comp.layout();
				}
				String resultString="";
				String analyzeString="";
				if(!searchText.getText().toString().trim().equals("")){//每日一句销毁
					if(everyDayEnglishComposite!=null&&!everyDayEnglishComposite.isDisposed()){
						everyDayEnglishComposite.dispose();
					}
					resultString=SearchWordHttp.searchWordFromJinshan(searchText.getText().toString().trim());
					wordArray=WordAnalyzeJsonUtil.getElements(resultString);
					disPlaySearchResult(comp);
				}
				if(searchText.getText().toString().trim().equals("")){
					
					MessageDialog.openInformation(shell, "Info", "请输入单词");
				}
				//处理查询结果
				search.setBackground(new Color(null, 50,205,50));
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
			}
		});
		search.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent e) {
				search.setBackground(new Color(null, 144,238,144));
			}
			
			@Override
			public void mouseExit(MouseEvent e) {
				search.setBackground(new Color(display, 118,209,127));
			}
			
			@Override
			public void mouseEnter(MouseEvent e) {
				search.setBackground(new Color(null, 144,238,144));
			}
		});
		
		/*
		 * 搜索结果显示面板
		 */

		everyDayEnglishComposite=new Composite(comp, SWT.BORDER);
		FormData everyDayEnglishCompositedata=new FormData();
		everyDayEnglishCompositedata.top=new FormAttachment(searchComposite,2);
		everyDayEnglishCompositedata.left = new FormAttachment(0,50);
		everyDayEnglishCompositedata.right = new FormAttachment(100,-100);
		everyDayEnglishCompositedata.bottom=new FormAttachment(100,-50);
		everyDayEnglishComposite.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		everyDayEnglishComposite.setLayoutData(everyDayEnglishCompositedata);
		
		
		return comp;
	}
	private Composite createtranslateion(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.BORDER);
		comp.setLayout(new FormLayout());
		
		StyledText upText=new StyledText(comp, SWT.NONE);
		FormData upTextData = new FormData();
		upTextData.top = new FormAttachment(displayComp,0);
		upTextData.left = new FormAttachment(0,0);
		upTextData.right = new FormAttachment(100,0);
		upTextData.bottom = new FormAttachment(45,0);
		upText.setLayoutData(upTextData);
		upText.setAlignment(SWT.CENTER);
		upText.setFont(new Font(display, "宋体", 13, SWT.NORMAL));
		
		Combo combo = new Combo(comp, SWT.READ_ONLY|SWT.DROP_DOWN ); 
		FormData comboData = new FormData();
		comboData.top = new FormAttachment(45,0);
		comboData.left = new FormAttachment(0,0);
		comboData.right = new FormAttachment(100,0);
		comboData.height=20;
		combo.setLayoutData(comboData);
		combo.add("EN-CH");
		combo.add("CH-EN");
		combo.select(1); // 默认选中第一行
		
		StyledText downText=new StyledText(comp, SWT.NONE);
		FormData downTextData = new FormData();
		downTextData.top = new FormAttachment(combo,0);
		downTextData.left = new FormAttachment(0,0);
		downTextData.right = new FormAttachment(100,0);
		downTextData.bottom = new FormAttachment(100,0);
		downText.setLayoutData(downTextData);
		downText.setAlignment(SWT.CENTER);
		downText.setFont(new Font(display, "宋体", 13, SWT.NORMAL));
		upText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				if(!upText.getText().trim().equals("")){
					String from="";
					int index=combo.getSelectionIndex();
					if(index==0){
						from="en";
					}else{
						from="zh";
					}
					final String form=from;
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							display.asyncExec(new Runnable() {
								@Override
								public void run() {
									downText.setText(TranslationAnalyzeUitl.init(Translation.sendToTranslate(upText.getText().trim(),form)).get("dst"));
								}
							});
						}
					}).start();
				}
			}
		});

		return comp;
	}
	private Composite createnote(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.BORDER);
		comp.setLayout(new FormLayout());
		CLabel l=new CLabel(comp, SWT.NONE);
		FormData lData = new FormData();
		lData.top = new FormAttachment(displayComp,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,0);
		l.setLayoutData(lData);
		l.setAlignment(SWT.CENTER);
		l.setText("进入我的生词本");
		return comp;
	}
	private Composite createword(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.BORDER);
		comp.setLayout(new FormLayout());

		TableViewer tableViewer = new TableViewer(comp, SWT.NONE | SWT.FULL_SELECTION);
		FormData tabledata=new FormData();
		tabledata.top=new FormAttachment(displayComp,0);
		tabledata.left=new FormAttachment(0,0);
		tabledata.right=new FormAttachment(100,0);
		tabledata.bottom=new FormAttachment(100,-30);
		table = tableViewer.getTable();
		table.setFont(new Font(display, "宋体", 10, SWT.NORMAL));
		table.setLayoutData(tabledata);
		table.setHeaderVisible(true);//设置表格头 ， 部是否可见  
	    table.setLinesVisible(false);//设置线条是否可见  
	    
	    final TableColumn newColumnTableColumn = new TableColumn(table, SWT.CENTER);  
        newColumnTableColumn.setWidth(100);  
        newColumnTableColumn.setText("ID");  
  
        final TableColumn newColumnTableColumn_1 = new TableColumn(table, SWT.CENTER);  
        newColumnTableColumn_1.setWidth(100);  
        newColumnTableColumn_1.setText("姓名");  
          
        final TableColumn newColumnTableColumn_2 = new TableColumn(table, SWT.CENTER);  
        newColumnTableColumn_2.setWidth(100);  
        newColumnTableColumn_2.setText("性别");  
  
        final TableColumn newColumnTableColumn_3 = new TableColumn(table, SWT.CENTER);  
        newColumnTableColumn_3.setWidth(100);  
        newColumnTableColumn_3.setText("年龄");  
  
        final TableColumn newColumnTableColumn_4 = new TableColumn(table, SWT.CENTER);  
        newColumnTableColumn_4.setWidth(126);  
        newColumnTableColumn_4.setText("创建日期");  
		
	    Button btnStartPlaying = new Button(comp, SWT.NONE);
	    FormData butdata=new FormData();
	    butdata.top=new FormAttachment(table,0);
	    butdata.left=new FormAttachment(0,0);
	    butdata.right=new FormAttachment(100,0);
	    butdata.bottom=new FormAttachment(100,0);
	    btnStartPlaying.setLayoutData(butdata);
	    btnStartPlaying.setText("Start Playing");
	    tableViewer.setContentProvider(new TableViewerContentProvider());
	    tableViewer.setLabelProvider(new TableViewerLabelProvider());
		List<Word> list = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Word word = new Word();
			word.setWord("Jerry");
			list.add(word);
			Word word1 = new Word();
			word1.setWord("English");
			list.add(word1);
			Word word2 = new Word();
			word2.setWord("One");
			list.add(word2);
			Word word3 = new Word();
			word3.setWord("Two");
			list.add(word3);
			Word word4 = new Word();
			word4.setWord("Three");
			list.add(word4);
			Word word5 = new Word();
			word5.setWord("Four");
			list.add(word5);
		}
	    tableViewer.setInput(list);
	    tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent arg0) {
				IStructuredSelection selection=(IStructuredSelection) arg0.getSelection();
				Word word=(Word) selection.getFirstElement();
				tts =new SpeakService();
				tts.speak(word.getWord());
			}
		});
	    btnStartPlaying.addSelectionListener(new SelectionAdapter() {
	    	@Override
			public void widgetSelected(SelectionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							List<Word> list=new ArrayList<>();
							list=(List<Word>) tableViewer.getInput();
							for (Word word : list) {
								tts = new SpeakService();
								tts.speak(word.getWord());
								Thread.sleep(4000);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						display.syncExec(new Runnable() {
							@Override
							public void run() {
								btnStartPlaying.setText("暂停");
							}
						});
					}
				}).start();
			}
		});
		return comp;
	}
	private Composite createreading(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.BORDER);
		comp.setLayout(new FormLayout());
		StyledText l=new StyledText(comp, SWT.NONE|SWT.MULTI|SWT.WRAP|SWT.V_SCROLL);
		FormData lData = new FormData();
		lData.top = new FormAttachment(displayComp,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,-20);
		l.setLayoutData(lData);
		l.setFont(new Font(display, "宋体", 13, SWT.NORMAL));
		l.setText("  As the other notable Los Angeles Lakers rookie started to run off the Capital One Arena floor about two hours before tipoff,"
				+ "fans of all ages began yelling,\"Kyle!\"and \"Kooz-Ma!\" to get his attention. Kyle Kuzma immediately stopped, came back and "
				+ "gladly signed every autograph and picture with his new fans,who probably had no clue who he was six months ago.I am handling "
				+ "the attention pretty well,Kuzma said before a game against the host Washington Wizards last week." +"I've never been "
				+ "one to live up to the hype. I've never had it before ever in my life. I'm just doing what got me here. Be humble. Stay hungry."
				+ "Whereas Lonzo Ballis known for his passing,vision and ability to handle the enormous expectations on him as the No. 2 overall pick,"
				+ "Kuzma has exceeded any expectations the Lakers or anyone had for him as a rookie.What has been the biggest change in your life "
				+ "of late?Everywhere I go now, people notice you. That's the biggest thing. Prior to this, I could walk around and no one knew me "
				+ "Nothing has really happened. It's just average type of fame. Everywhere I go, people want a picture or to talk to you. That's the "
				+ "biggest thing to adjust to because I never really had that before.Did you ever get treated differently because you are a mixed-race kid?"
				+ "I got, 'Are you white?' 'Are you black?' I was a mixed kid in a black area. You are raised black and all your friends are black,"
				+ "but you're white so you can't necessarily do certain things.What was the determining factor in you entering the draft after your"
				+ "junior year at Utah?It was really during the season. I averaged 16 points and nine rebounds last year. I played a lot of guys"
				+ "that were highly rated, and I played well against those guys. I was thinking, 'If these guys are on the draft board, why can't I be?'"
				+ "I took a leap of faith.");
		l.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				Button btn = new Button(displayComp, SWT.NONE);
                Menu popupMenu = new Menu(btn);
                MenuItem menuItem = new MenuItem(popupMenu,SWT.NONE);
                menuItem.setText("Search");
                btn.setMenu(popupMenu);
                btn.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent e) {
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								display.asyncExec(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										ConfigWindow window=new ConfigWindow();
										window.open();
									}
								});
							}
						}).start();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
                popupMenu.setVisible(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		
		CLabel leftPage=new CLabel(comp, SWT.NONE);
		FormData leftPageData = new FormData();
		leftPageData.top = new FormAttachment(l,0);
		leftPageData.left = new FormAttachment(0,0);
		leftPageData.right = new FormAttachment(45,0);
		leftPageData.bottom = new FormAttachment(100,0);
		leftPage.setAlignment(SWT.CENTER);
		leftPage.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		leftPage.setImage(ImageRegistryUtil.getRegistry("left_arrow"));
		leftPage.setToolTipText("上一页");
		leftPage.setLayoutData(leftPageData);
		
		StyledText go=new StyledText(comp, SWT.NONE);
		FormData goData = new FormData();
		goData.top = new FormAttachment(l,0);
		goData.left = new FormAttachment(leftPage,0);
		goData.right = new FormAttachment(55,0);
		goData.bottom = new FormAttachment(100,0);
		go.setAlignment(SWT.CENTER);
		go.setText("1");
		go.setToolTipText("当前页");
		go.setLayoutData(goData);
		
		CLabel rightPage=new CLabel(comp, SWT.NONE|SWT.RIGHT);
		FormData rightPageData = new FormData();
		rightPageData.top = new FormAttachment(l,0);
		rightPageData.left = new FormAttachment(go,0);
		rightPageData.right = new FormAttachment(100,0);
		rightPageData.bottom = new FormAttachment(100,0);
		rightPage.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		rightPage.setAlignment(SWT.CENTER);
		rightPage.setToolTipText("下一页");
		rightPage.setImage(ImageRegistryUtil.getRegistry("right_arrow"));
		rightPage.setLayoutData(rightPageData);
		
		return comp;

	}
	private Composite createlistening(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.BORDER);
		comp.setLayout(new FormLayout());
		
		CLabel l=new CLabel(comp, SWT.NONE);
		FormData lData = new FormData();
		lData.top = new FormAttachment(l,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,0);
		l.setLayoutData(lData);
		l.setAlignment(SWT.CENTER);
		l.setText("听力面板");
		l.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				if(mediaPlayers==null){
					mediaPlayers=new SWTMediaPlayer();
					mediaPlayers.open("D:\\b.mkv");
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		return comp;
	}
	private Composite createbookStores(Composite displayComp) {
		Composite comp = new Composite(displayComp, SWT.BORDER);
		comp.setLayout(new FormLayout());
		Browser browser=new Browser(comp, SWT.NONE);
		FormData lData = new FormData();
		lData.top = new FormAttachment(displayComp,0);
		lData.left = new FormAttachment(0,0);
		lData.right = new FormAttachment(100,0);
		lData.bottom = new FormAttachment(100,0);
		browser.setLayoutData(lData);
		browser.setUrl("https://book.tmall.com/"); 
		return comp;
	}
	
	
	private void refreshComposite(Composite composite){
		composite.layout();
	}
	
	//每日一句面板
	//没有查询的时候显示的是每日一句的面板 查询之后显示的是查询结果的面板
	private void everyDayEnglish(Composite composite){
		composite.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
	}
	
	
	//重新显示查询结果面板
	private void disPlaySearchResult(Composite composite){
		disPlayComposite=new Composite(composite, SWT.BORDER);
		FormData disPlayCompositedata=new FormData();
		disPlayCompositedata.top=new FormAttachment(searchComposite,2);
		disPlayCompositedata.left = new FormAttachment(0,50);
		disPlayCompositedata.right = new FormAttachment(100,-100);
		disPlayCompositedata.bottom=new FormAttachment(100,0);
		disPlayComposite.setLayoutData(disPlayCompositedata);
		
		CLabel lb_word = new CLabel(disPlayComposite, SWT.NONE);
		lb_word.setBounds(0, 20, 70, 30);
		lb_word.setAlignment(SWT.CENTER);
		lb_word.setFont(new Font(null, "宋体", 20, SWT.NORMAL));
		lb_word.setText(wordArray.wordArray.get("word_name"));
		
		CLabel lbBritain = new CLabel(disPlayComposite, SWT.NONE);
		lbBritain.setBounds(0, 50, 67, 23);
		lbBritain.setText("英");
		lbBritain.setAlignment(SWT.CENTER);
		
		CLabel lbBritain_P = new CLabel(disPlayComposite, SWT.NONE);
		lbBritain_P.setBounds(70, 50, 29, 23);
		lbBritain_P.setImage(ImageRegistryUtil.getRegistry("volume_16"));
		lbBritain_P.setText("");
		lbBritain_P.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				
				try{
					String url=wordArray.wordArray.get("ph_en_mp3");
					String word_name=wordArray.wordArray.get("word_name");
					if(word_name==null){
						return;
					}
					final String file_name=word_name+"en"+DataTimeNowStringUtil.getTimeString()+".mp3";
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								GetPronunciationUtil.downLoadFromUrl(url, file_name);
							} catch (IOException e) {
								e.printStackTrace();
							}
							PronunciationUtil.play(Constant.Voice.VOICE_PATH+file_name);
						}
					}).start();
				}catch (Exception e1){
					return;
				}
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		CLabel lbAmerican = new CLabel(disPlayComposite, SWT.NONE);
		lbAmerican.setText("美");
		lbAmerican.setBounds(100, 50, 67, 23);
		lbAmerican.setAlignment(SWT.CENTER);
		
		CLabel lbAmerican_P = new CLabel(disPlayComposite, SWT.NONE);
		lbAmerican_P.setText("");
		lbAmerican_P.setImage(ImageRegistryUtil.getRegistry("volume_16"));
		lbAmerican_P.setBounds(180, 50, 29, 23);
		
		lbAmerican_P.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				String url=wordArray.wordArray.get("ph_am_mp3");
				String word_name=wordArray.wordArray.get("word_name");
				System.out.println(word_name+" "+url);
				if(word_name==null){
					return;
				}
				final String file_name=word_name+"am"+DataTimeNowStringUtil.getTimeString()+".mp3";
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							GetPronunciationUtil.downLoadFromUrl(url, file_name);
						} catch (IOException e) {
							e.printStackTrace();
						}
						PronunciationUtil.play(Constant.Voice.VOICE_PATH+file_name);
					}
				}).start();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		if(wordArray!=null&&!wordArray.wordArray.isEmpty()){
			int i=0;
			java.util.Iterator<Entry<String, String>> iter = ((Map<String, String>) wordArray.ciXingArray).entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				CLabel lblNewLabel = new CLabel(disPlayComposite, SWT.NONE);
				lblNewLabel.setBounds(30,  80+25*i, 70, 25);
				lblNewLabel.setAlignment(SWT.CENTER);
				lblNewLabel.setText(entry.getKey().toString());
				
				CLabel lblNewLabel_1 = new CLabel(disPlayComposite, SWT.NONE);
				lblNewLabel_1.setBounds(100,  80+25*i, 120, 25);
				lblNewLabel_1.setAlignment(SWT.CENTER);
				lblNewLabel_1.setText(entry.getValue().toString());
				i++;
			}
		}
		composite.layout();
		
	}
	
	 class myMouseAction implements MouseTrackListener{

		 @Override
			public void mouseHover(MouseEvent e) {
			 if(e.widget.getClass().equals(CLabel.class)){
					CLabel l=(CLabel) e.widget;
					l.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
				}
			}
			@Override
			public void mouseExit(MouseEvent e) {
				if(e.widget.getClass().equals(CLabel.class)){
					CLabel l=(CLabel) e.widget;
					l.setBackground(new Color(null, 30,144,255));
				}
			}
			@Override
			public void mouseEnter(MouseEvent e) {
				if(e.widget.getClass().equals(CLabel.class)){
					CLabel l=(CLabel) e.widget;
					l.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
				}
			}
	 }
	 class shellMouseListener implements MouseListener{

		@Override
		public void mouseDoubleClick(MouseEvent e) {

		}

		@Override
		public void mouseDown(MouseEvent e) {
			  blnMouseDown=true;
              xPos=e.x;
              yPos=e.y;
		}

		@Override
		public void mouseUp(MouseEvent e) {
			blnMouseDown=false;
		}
		 
	 }
	 class shellMouseMoveListener implements MouseMoveListener{
		 
		@Override
		public void mouseMove(MouseEvent e) {
			
			if(blnMouseDown){
				 shell.setLocation(shell.getLocation().x+(e.x-xPos),shell.getLocation().y+(e.y-yPos));
				 shell.layout();
			}
		}
		 
	 }
	 class StyledTextMoveListener implements MouseListener{
		 
		 private Composite comp;
		 private Composite searchComposite;
		 
		 public StyledTextMoveListener(Composite comp,Composite searchComposite,StyledText searchlist) {
			this.comp=comp;
			this.searchComposite=searchComposite;
		}
			@Override
			public void mouseUp(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				if(everyDayEnglishComposite!=null&&!everyDayEnglishComposite.isDisposed()){
					everyDayEnglishComposite.dispose();
				}
				if(disPlayComposite!=null&&!disPlayComposite.isDisposed()){
					disPlayComposite.dispose();
				}
				if(searchlist!=null&&!searchlist.isDisposed()){
					comp.layout();
					return;
				}
				searchlist=new StyledText(comp, SWT.BORDER);
				FormData listdata=new FormData();
				listdata.top=new FormAttachment(searchComposite,2);
				listdata.left = new FormAttachment(0,50);
				listdata.right = new FormAttachment(100,-100);
				listdata.bottom=new FormAttachment(100,-50);
				searchlist.setLayoutData(listdata);
				searchlist.setAlignment(SWT.CENTER);
				searchlist.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
				comp.layout();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
			}
	 }
}