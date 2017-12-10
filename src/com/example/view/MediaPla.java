package com.example.view;

import org.eclipse.jface.bindings.keys.SWTKeySupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import com.example.service.CompositeVideoSurface;
import com.example.service.SwtEmbeddedMediaPlayer;
import com.example.util.ImageRegistryUtil;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.linux.LinuxVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.mac.MacVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.windows.WindowsVideoSurfaceAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class MediaPla extends Dialog {

	protected Object result;
	protected static Shell shell;
	private static SwtEmbeddedMediaPlayer mediaPlayer;
	
	private static Display display;
	private static Composite videoSurface;//视屏界面
	private static Composite operateComposite;//操作界面
	private static ProgressBar progressBar;//视屏进度条
	private static CLabel playOrPause;
	private static boolean noMoving=false;
	private static boolean ok=false;
	private static ProgressBar voiceProgressBar;
	private static CLabel stop;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public MediaPla(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
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
		display=getParent().getDisplay();
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 300);
		shell.addShellListener(new ShellAdapter() {
				public void shellClosed(final ShellEvent e) {
	                System.exit(0);
	            }
			});
		
		shell.setLayout(new FormLayout());
        
        
        //检测ESC键
		display.addFilter(SWT.KeyDown, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (SWTKeySupport.convertEventToUnmodifiedAccelerator(e) == SWT.ESC) {
					Control focusControl = display.getFocusControl();
					boolean fullScreen = shell.getFullScreen();
					if (fullScreen) {
						FormData maindata=new FormData();
				        maindata.top=new FormAttachment(0,0);
				        maindata.left=new FormAttachment(0,0);
				        maindata.right=new FormAttachment(100,0);
				        maindata.bottom=new FormAttachment(100,-35);
				        videoSurface.setLayoutData(maindata);
						shell.setFullScreen(false);
					}
					if (null != focusControl)
						focusControl.forceFocus();
					e.doit = false;
				}
				if (SWTKeySupport.convertEventToUnmodifiedAccelerator(e) == SWT.SPACE) {
					if(mediaPlayer.isPlaying()){
						mediaPlayer.pause();
				        playOrPause.setImage(ImageRegistryUtil.getRegistry("play"));
					}else{
						mediaPlayer.pause();
				        playOrPause.setImage(ImageRegistryUtil.getRegistry("pause"));
					}
				}
			}
		});
        
        //视屏区域
        videoSurface = new Composite(shell, SWT.NONE);
        videoSurface.setLayout(new FormLayout());
        FormData videoSurfacedata=new FormData();
        videoSurfacedata.top=new FormAttachment(0,0);
        videoSurfacedata.left=new FormAttachment(0,0);
        videoSurfacedata.right=new FormAttachment(100,0);
        videoSurfacedata.bottom=new FormAttachment(100,-35);
        videoSurface.setLayoutData(videoSurfacedata);
        
        
        //操作区域
        operateComposite=new Composite(shell, SWT.BORDER);
        operateComposite.setLayout(new FormLayout());
        FormData operateCompositeData=new FormData();
        operateCompositeData.top=new FormAttachment(videoSurface,0);
        operateCompositeData.left=new FormAttachment(0,0);
        operateCompositeData.right=new FormAttachment(100,0);
        operateCompositeData.bottom=new FormAttachment(100,0);
        operateComposite.setLayoutData(operateCompositeData);
        
        shell.setBackgroundMode(SWT.INHERIT_FORCE);
        
        
        Composite videoSurfaces = new Composite(videoSurface, SWT.INHERIT_DEFAULT);
        videoSurfaces.setLayout(new FormLayout());
        FormData videoSurfacesdata=new FormData();
        videoSurfacesdata.top=new FormAttachment(videoSurface,0);
        videoSurfacesdata.left=new FormAttachment(0,0);
        videoSurfacesdata.right=new FormAttachment(100,0);
        videoSurfacesdata.bottom=new FormAttachment(100,0);
        videoSurfaces.setLayoutData(videoSurfacesdata);
        
        //videoSurface 视屏区域的鼠标操作
        videoSurface.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				if(mediaPlayer!=null){
					if(mediaPlayer.isPlaying()){
						mediaPlayer.pause();
				        playOrPause.setImage(ImageRegistryUtil.getRegistry("play"));
					}else{
						mediaPlayer.pause();
				        playOrPause.setImage(ImageRegistryUtil.getRegistry("pause"));
					}
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if(shell.getFullScreen()){
					FormData maindata=new FormData();
			        maindata.top=new FormAttachment(0,0);
			        maindata.left=new FormAttachment(0,0);
			        maindata.right=new FormAttachment(100,0);
			        maindata.bottom=new FormAttachment(100,-35);
			        videoSurface.setLayoutData(maindata);
					shell.setFullScreen(false);
				}else{
//					int iSreenWith = 0;
//					int iSreenHeight = 0;
//					iSreenWith = Toolkit.getDefaultToolkit().getScreenSize().width;
//					iSreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
//					shell.setSize(iSreenWith,iSreenHeight);
//					shell.setLocation(0, 0);
					FormData maindata=new FormData();
			        maindata.top=new FormAttachment(0,0);
			        maindata.left=new FormAttachment(0,0);
			        maindata.right=new FormAttachment(100,0);
			        maindata.bottom=new FormAttachment(100,0);
			        videoSurface.setLayoutData(maindata);
					shell.setFullScreen(true);
				}
			}
		});
        
        videoSurface.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				noMoving=true;
			}
		});
        
        LibVlc libvlc = LibVlc.INSTANCE;
        libvlc_instance_t instance = libvlc.libvlc_new(0, null);

        mediaPlayer = new SwtEmbeddedMediaPlayer(libvlc, instance);
        videoSurfaces.setEnabled(false);  
        mediaPlayer.setVideoSurface(new CompositeVideoSurface(videoSurfaces, getVideoSurfaceAdapter()));
        
        createOperatePanel();
        
        shell.open();
        progressBar.setMaximum(10000);
        progressBar.setSelection(1);
        
        
        
        new Thread(new Runnable() {
            public void run() {   
              while (noMoving==false) {   
                try {   
                  Thread.sleep(5000);
                  ok=true;
                } catch (InterruptedException e) {}   
                display.asyncExec(new Runnable() {   
                  public void run() {
                	 if(shell.getFullScreen()){
                		 if(ok){
                     		noMoving=false;
                     		ok=false;
                     		
                     	  }
                	 }
                  }   
                });   
              }   
            }   
          }).start();   
        
		new Thread(new Runnable() {

			@Override
			public void run() {
				mediaPlayer.playMedia("D:\\b.mkv");
				display.asyncExec(new Runnable() {

					@Override
					public void run() {
						for (int i = 0; i < progressBar.getMaximum(); i++) {
							System.out.println(mediaPlayer.getTime()/(mediaPlayer.getLength()+1));
							progressBar.setSelection((int)(mediaPlayer.getTime()/(mediaPlayer.getLength()+1)));
						}
					}
				});
			}
		}).start();
	}
	 private static void createOperatePanel() {
	    	
	    	progressBar = new ProgressBar(operateComposite, SWT.BORDER);
	        FormData fd_progressBar = new FormData();
	        fd_progressBar.top=new FormAttachment(operateComposite,0);
	        fd_progressBar.left = new FormAttachment(0,0);
	        fd_progressBar.right = new FormAttachment(100, 0);
	        fd_progressBar.height=5;
	        progressBar.setLayoutData(fd_progressBar);
	        
	        playOrPause=new CLabel(operateComposite, SWT.NONE);
	        FormData buttondata=new FormData();
	        playOrPause.setAlignment(SWT.CENTER);
	        playOrPause.setBackgroundMode(SWT.INHERIT_DEFAULT);
	        buttondata.top = new FormAttachment(progressBar,0);
	        buttondata.left=new FormAttachment(0,0);
	        buttondata.bottom=new FormAttachment(100,0);
	        buttondata.width=40;
	        playOrPause.setLayoutData(buttondata);
	        playOrPause.setImage(ImageRegistryUtil.getRegistry("pause"));
	        playOrPause.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent e) {
					
				}
				
				@Override
				public void mouseDown(MouseEvent e) {
					if(mediaPlayer.isPlaying()){
						mediaPlayer.pause();
						playOrPause.setImage(ImageRegistryUtil.getRegistry("play"));
					}
					else{
						mediaPlayer.start();
						playOrPause.setImage(ImageRegistryUtil.getRegistry("pause"));
					}
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					
				}
			});
	  
	        stop=new CLabel(operateComposite, SWT.NONE);
	        FormData fd_stop=new FormData();
	        stop.setImage(ImageRegistryUtil.getRegistry("stop"));
	        stop.setAlignment(SWT.CENTER);
	        fd_stop.top = new FormAttachment(progressBar,0);
	        fd_stop.left = new FormAttachment(playOrPause, 10);
	        buttondata.bottom=new FormAttachment(100,0);
	        fd_stop.width=40;
	        stop.setLayoutData(fd_stop);
	        stop.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent e) {
					
				}
				
				@Override
				public void mouseDown(MouseEvent e) {
					mediaPlayer.stop();
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					
				}
			});
	        
	        
	        
	        CLabel voice = new CLabel(operateComposite, SWT.NONE);
	        voice.setAlignment(SWT.CENTER);
	        FormData voiceData = new FormData();
	        voiceData.top=new FormAttachment(progressBar,0);
	        voiceData.left = new FormAttachment(stop, 10);
	        voiceData.width=40;
	        voiceData.bottom = new FormAttachment(100,0);
	        voice.setLayoutData(voiceData);
	        voice.setImage(ImageRegistryUtil.getRegistry("volume_max"));
	        voice.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDown(MouseEvent e) {
					if(mediaPlayer.getVolume()==0){
						mediaPlayer.setVolume(0);
						voice.setImage(ImageRegistryUtil.getRegistry("volume_off"));
					}else{
						voice.setImage(ImageRegistryUtil.getRegistry("volume_max"));
					}
					
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
	        
	        voiceProgressBar = new ProgressBar(operateComposite, SWT.BORDER);
	        FormData fd_voiceProgressBar = new FormData();
	        fd_voiceProgressBar.right = new FormAttachment(stop, 180, SWT.RIGHT);
	        fd_voiceProgressBar.top = new FormAttachment(progressBar);
	        fd_voiceProgressBar.left = new FormAttachment(voice, 0);
	        fd_voiceProgressBar.bottom = new FormAttachment(100);
	        voiceProgressBar.setLayoutData(fd_voiceProgressBar);
	        voiceProgressBar.setSelection(50);
	        
	        
	        
	        CLabel full=new CLabel(operateComposite, SWT.NONE);
	        FormData fulldata=new FormData();
	        full.setAlignment(SWT.CENTER);
	        full.setImage(ImageRegistryUtil.getRegistry("fullscreen"));
	        fulldata.top = new FormAttachment(progressBar, 0);
	        fulldata.right = new FormAttachment(100,0);
	        fulldata.width=50;
	        fulldata.bottom=new FormAttachment(100,0);
	        full.setLayoutData(fulldata);
	        full.addMouseListener(new MouseListener() {
	        	
				@Override
				public void mouseUp(MouseEvent e) {
					
				}
				
				@Override
				public void mouseDown(MouseEvent e) {
					FormData maindata=new FormData();
			        maindata.top=new FormAttachment(0,0);
			        maindata.left=new FormAttachment(0,0);
			        maindata.right=new FormAttachment(100,0);
			        maindata.bottom=new FormAttachment(100,0);
			        videoSurface.setLayoutData(maindata);
					shell.setFullScreen(true);
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					
				}
			});
		}


		/*
	     *  generate Adapter
	     */
	    private static VideoSurfaceAdapter getVideoSurfaceAdapter() {
	        VideoSurfaceAdapter videoSurfaceAdapter;
	        if(RuntimeUtil.isNix()) {
	            videoSurfaceAdapter = new LinuxVideoSurfaceAdapter();
	        }
	        else if(RuntimeUtil.isWindows()) {
	            videoSurfaceAdapter = new WindowsVideoSurfaceAdapter();
	        }
	        else if(RuntimeUtil.isMac()) {
	            videoSurfaceAdapter = new MacVideoSurfaceAdapter();
	        }
	        else {
	            throw new RuntimeException("Unable to create a media player - failed to detect a supported operating system");
	        }
	        return videoSurfaceAdapter;
	    }
}
