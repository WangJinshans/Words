package com.example.service;

import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizerListener;

public class SpeakService {
	private SynthesizerListener mSynListener;
	private RecognizerListener mRecoListener;
	private SpeechSynthesizer mTts;
	private SpeechRecognizer mIat;
	
	public SynthesizerListener getmSynListener() {
		return mSynListener;
	}
	public void setmSynListener(SynthesizerListener mSynListener) {
		this.mSynListener = mSynListener;
	}
	
	
	public SpeakService() {
		mTts= SpeechSynthesizer.createSynthesizer();
		mIat= SpeechRecognizer.createRecognizer();
	
		mRecoListener=new RecognizerListener() {
			
			@Override
			public void onVolumeChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onResult(RecognizerResult results, boolean isLast) {
				// TODO Auto-generated method stub
				System.out.println("Result:"+results.getResultString ());
			}
			
			@Override
			public void onEvent(int arg0, int arg1, int arg2, String arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(SpeechError arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onEndOfSpeech() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onBeginOfSpeech() {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		mSynListener=new SynthesizerListener() {

			public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
				
			}
			//开始播放
			public void onSpeakBegin() {
				
			}
			//暂停播放
			public void onSpeakPaused() {
				
			}
			//播放进度回调
			
			//percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
			public void onSpeakProgress(int percent, int beginPos, int endPos) {
				
			}
			//恢复播放回调接口
			public void onSpeakResumed() {
				
			}
			@Override
			public void onCompleted(SpeechError arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4, Object arg5) {
				// TODO Auto-generated method stub
				
			}
		};
		
	}
	public void speak(String text){
        	SpeechUtility.createUtility(SpeechConstant.APPID +"=57a0b95e"); 
			mTts.setParameter(SpeechConstant.VOICE_NAME, "小燕");//设置发音人
			mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
			mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100
			mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./tts_test.pcm");
			//3.开始合成
			mTts.startSpeaking(text, mSynListener);
	}
	public void listen(){
		mIat.setParameter(SpeechConstant.DOMAIN, "iat");
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
		mIat.startListening(mRecoListener);
	}
}
