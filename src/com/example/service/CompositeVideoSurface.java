package com.example.service;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurface;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapter;

public class CompositeVideoSurface extends VideoSurface {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Composite composite;

	private final VideoSurfaceAdapter videoSurfaceAdapter;
	
    public CompositeVideoSurface(Composite composite, VideoSurfaceAdapter videoSurfaceAdapter) {
        super(videoSurfaceAdapter);
        this.composite = composite;
        composite.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.println("videoSurface Mouse Down");
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        this.videoSurfaceAdapter=videoSurfaceAdapter;
    }

    @Override
    public void attach(LibVlc libvlc, MediaPlayer mediaPlayer) {
        long componentId = composite.handle;
        videoSurfaceAdapter.attach(libvlc, mediaPlayer, componentId);
    }
}