package Model;

import java.util.concurrent.LinkedBlockingQueue;

public class ReadThreadPool {
	
	//���еĳ��������
    private int BASE_NUM = 100;
    
    //private int MAX_NUM = 500;
    public LinkedBlockingQueue<ReadThread> blockingQueue;
    
    public ReadThreadPool(LinkedBlockingQueue<ReadThread> blockingQueue){
    	this.blockingQueue = blockingQueue;
    	
    }
    
    public void init() {
        for (int i = 0; i < BASE_NUM; i++) {
            //blockingQueue.add();
        	blockingQueue.add(new ReadThread("",0));
        	
        }
    }

    public ReadThread getThread() {
        ReadThread object = blockingQueue.poll();
        System.out.println("�Ӷ���ȡ��һ���߳�");
        return object;
    }

    public boolean returnThread(ReadThread o) {
        boolean flag = true;

        try {
            blockingQueue.add(o);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        
        return flag;
    }

}
