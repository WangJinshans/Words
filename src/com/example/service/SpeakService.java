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
			//��ʼ����
			public void onSpeakBegin() {
				
			}
			//��ͣ����
			public void onSpeakPaused() {
				
			}
			//���Ž��Ȼص�
			
			//percentΪ���Ž���0~100,beginPosΪ������Ƶ���ı��п�ʼλ�ã�endPos��ʾ������Ƶ���ı��н���λ��.
			public void onSpeakProgress(int percent, int beginPos, int endPos) {
				
			}
			//�ָ����Żص��ӿ�
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
			mTts.setParameter(SpeechConstant.VOICE_NAME, "С��");//���÷�����
			mTts.setParameter(SpeechConstant.SPEED, "50");//��������
			mTts.setParameter(SpeechConstant.VOLUME, "100");//������������Χ0~100
			mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./tts_test.pcm");
			//3.��ʼ�ϳ�
			mTts.startSpeaking(text, mSynListener);
	}
	public void listen(){
		mIat.setParameter(SpeechConstant.DOMAIN, "iat");
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
		mIat.startListening(mRecoListener);
	}
}
