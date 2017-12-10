package com.example.service;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.player.DefaultMediaPlayer;


public class SwtEmbeddedMediaPlayer extends DefaultMediaPlayer {

    private CompositeVideoSurface videoSurface;

    public SwtEmbeddedMediaPlayer(LibVlc libvlc, libvlc_instance_t instance) {
        super(libvlc, instance);
    }

    public void setVideoSurface(CompositeVideoSurface videoSurface) {
        this.videoSurface = videoSurface;
    }

    public void attachVideoSurface() {
        videoSurface.attach(libvlc, this);
    }

    @Override
    protected final void onBeforePlay() {
        attachVideoSurface();
    }
    
}