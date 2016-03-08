package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import Model.Book;
import Model.ReadThread;
import Model.ReadThreadPool;
import Model.WriteThread;
import Model.deleteThread;
import Model.modifyThread;

public class Service {
	
	
	
	public Socket clientSocket ;
	public ReadThreadPool readThreadPool = new ReadThreadPool(new LinkedBlockingQueue<ReadThread>());
	
	public Service(Socket clientSocket) {
		
		this.clientSocket = clientSocket;
	
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException{
        OutputStream socketOut=socket.getOutputStream();
        return new PrintWriter(socketOut,true);
    }
	
	public void reponse(List<Book> books) {
		
    	try{
    			
    		 PrintWriter pw = getWriter(clientSocket);
    			
    			String sb = new String();
    			for(int i=0;i<books.size();i++){
    				sb+=(books.get(i).getBookNum()+' '+books.get(i).getBookName()+' '+books.get(i).getAuthor()
    						+' '+books.get(i).getPrice());
    				if(i!=books.size()-1)
    					sb+='/';
    			}
    			
    			pw.write(sb+'\n');
    			pw.flush();
    			pw.close();
    			
    		}catch(Exception e){
    			e.printStackTrace();
    		}	
    }
	//����ɾ������ӣ��޸ĺ�Ľ��
	public void reponseInfo(String s){
		
		try{
			PrintWriter pw =getWriter(clientSocket);
			pw.write(s);
			pw.flush();
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	
 	//ʵ����ӣ�д����
		public String add(String info) {
			String result = "success";
			String[] s = info.split("/");
			try{
				
				
				
				WriteThread writeThread = new WriteThread(s[0], s[1], s[2], Double.parseDouble(s[3]));
				Thread thread = new Thread(writeThread);
				thread.start();
				
				
			}catch(Exception e){
				result = "failed";
				e.printStackTrace();
			}
			return result+'\n';
			
		}
		//ʵ��������������
		public List<Book> search(String info) {
			
			
			
			List<Book> list = new ArrayList<Book>();
			
			String[] s = info.split("/");
			try{
				
				
				readThreadPool.init();
				ReadThread readThread = readThreadPool.getThread();
				
				
				readThread = new ReadThread(s[0], Integer.parseInt(s[1]));
				Thread thread = new Thread(readThread);
				thread.start();
				
				
				//ģ���߳�����1����
				Thread.sleep(1000);
				boolean f = readThreadPool.returnThread(readThread);
				if(f)
					System.out.println("�ɹ����̳߳ط��ض���");
				else {
					System.out.println("�����̳߳�ʧ��");
				}
				
				list = readThread.getList();
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			//System.out.println(list.get(0).getAuthor());
			
			return list;
		}
		
		//ʵ���޸ģ������̣�д����
		public String modify(String info) {
			String result = "success";
			String[] strings = info.split("/");
			try{
				
				modifyThread modifyThread = new modifyThread(strings[0], Integer.parseInt(strings[1]),Double.parseDouble(strings[2]));
				Thread thread = new Thread(modifyThread);
				thread.start();
				
			}catch(Exception e){
				result = "failed";
				e.printStackTrace();
			}
			return result+'\n';
		}
		
		//ʵ��ɾ����������->д����
		public String delete(String info) {
			String result = "success";
			String[] strings = info.split("/");
			try{
				deleteThread deleteThread = new deleteThread(strings[0], Integer.parseInt(strings[1]));
				Thread thread = new Thread(deleteThread);
				thread.start();
			}catch(Exception e){
				result = "failed";
				e.printStackTrace();
			}
			return result+'\n';
		}
		
		//ʵ�ֵ�½
		public String login(String username,String password) {
			
			String result = "failed";
			FileReader fr = null;
			BufferedReader br = null;
			
			try{
				
				fr = new FileReader("user.txt");
				br = new BufferedReader(fr);
				String string = new String();
				while((string=br.readLine())!=null){
					String[] s = string.split(" ");
					if(username.equals(s[0])&&password.equals(s[1])){
						result = "success";
					}
				}
				fr.close();
				br.close();
				
			}catch(IOException e){
				e.printStackTrace();
			}
			
			return result;

		}
		
		//ע�Ṧ��
		public String register(String username,String password) {
			String result = "success";
			FileWriter fw = null;
			BufferedWriter bw = null;
			
			try{
				fw = new FileWriter("user.txt",true);
				bw = new BufferedWriter(fw);
				String string = username+" "+password;
				/*��������*/
				bw.newLine();
				bw.write(string,0,string.length());
				/*��ջ�����*/
				bw.flush();
				fw.close();
				bw.close();
	 			
			}catch(IOException e){
				result = "failed";
				e.printStackTrace();
			}
			return result;
			
		}
	

}
