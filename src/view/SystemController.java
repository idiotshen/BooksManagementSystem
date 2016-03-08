package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SystemController {
	
	
	//实现登陆
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
	
	//注册功能
	public String register(String username,String password) {
		String result = "success";
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try{
			fw = new FileWriter("user.txt",true);
			bw = new BufferedWriter(fw);
			String string = username+" "+password;
			/*换行输入*/
			bw.newLine();
			bw.write(string,0,string.length());
			/*清空缓冲区*/
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
