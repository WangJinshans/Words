package test;

public class AccessDBThread implements Runnable {
	 
    private String msg;
     
    public AccessDBThread() {
        super();
    }
 
    public AccessDBThread(String msg) {
        this.msg = msg;
    }
 
    public String getMsg() {
        return msg;
    }
 
    public void setMsg(String msg) {
        this.msg = msg;
    }
 
    @Override
    public void run() {
         
        // 向数据库中添加Msg变量值
        System.out.println("Added the message: "+msg+" into the Database");
    }
 
}
