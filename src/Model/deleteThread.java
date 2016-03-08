package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


//É¾³ý¹¦ÄÜ
public class deleteThread implements Runnable{
	
	Book book = new Book();
	//flag 0,1,2
	private int flag = 3;
	public  deleteThread(String name,int flag) {
		if(flag == 0){
			book.setBookNum(name);
		}
		else if(flag == 1){
			book.setBookName(name);
		}
		else if(flag == 2){
			book.setAuthor(name);
		}
		this.flag = flag;
	}
	
	public deleteThread(String num,String name,String author,double price) {
		book.setBookNum(num);
		book.setBookName(name);
		book.setAuthor(author);
		book.setPrice(price);
	}

	public void run(){
		
		
		FileReader fr = null;
		
		BufferedReader br = null;
		
		try{
			
			double price = book.getPrice();
			fr = new FileReader("book.txt");
			
			br = new BufferedReader(fr);
			
			StringBuffer sb = new StringBuffer();
			
			String string = new String();
			
			while((string=br.readLine())!=null){
				
				String[] string2 = string.split(" ");
				if(flag == 0){
					if(!string2[0].equals(book.getBookNum()))
						/*string = string2[0]+" "+string2[1]+" "+string2[2]+" "+price;
						
						sb.append(string).append("\r\n");*/
						sb.append(string).append("\r\n");
					
				}
				else if(flag == 1){
					if(!string2[1].equals(book.getBookName()))
						sb.append(string).append("\r\n");
					
				}
				else if(flag == 2){
					if(!string2[2].equals(book.getAuthor())) 
						sb.append(string).append("\r\n");
				}
				else if(flag == 3){
					if(!string2[0].equals(book.getBookNum())) 
						sb.append(string).append("\r\n");
				}
			}
				
			FileOutputStream fos = new FileOutputStream("C://b.txt");
			PrintWriter pw = new PrintWriter(fos);
			
			pw.write(sb.toString().toCharArray());
			pw.flush();
			pw.close();
			
			
				
		}catch(IOException e){
			e.printStackTrace();
		}
		
				
	}
	

}
