package test;

public class TestDriver {
	 
    ThreadPoolManager tpm = ThreadPoolManager.newInstance();
     
    public void sendMsg( String msg ) {
         
        tpm.addLogMsg( msg + "��¼һ����־ " );
    }
     
    public static void main(String[] args) {
        for(int i=0;i<1000;i++){
            new TestDriver().sendMsg( Integer.toString( i ) );
        }
         
        //new TestDriver().sendMsg("����һ������" );
    }
}