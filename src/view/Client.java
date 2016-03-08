package view;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.io.PrintWriter;

import java.net.Socket;

//¿Í»§¶Ë
public class Client {

	public Socket socket;
	
	public Client() throws Exception {
		
		this.socket = new Socket("localhost", 88);

	}


	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	public void sendInfo(String info) {

		try {

			PrintWriter pw = getWriter(socket);
			pw.print(info);
			pw.flush();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public String getSearchMessage() {
		String sb = "";
		try {
			
			InputStream is = socket.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String string = new String();
			
			while ((string = br.readLine()) != null) {
				sb+=string;
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}
	
	public String getDeleteMessage() {
		String result = "";
		try{
			
			InputStream is = socket.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			result = br.readLine();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public String getModifyMessage() {
		String result = "";
		String str = null;
		try{
			InputStream is = socket.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			System.out.println("ylds");
			while((str = br.readLine())!=null){
				result += str;
				break;
			}
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public String getAddMessage(){
		String result = "";
		try{
			InputStream is = socket.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			result = br.readLine();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;		
	}
	

}
