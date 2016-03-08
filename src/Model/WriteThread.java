package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



//添加功能
public class WriteThread implements Runnable{
	
	private Book book = new Book();
	
	public WriteThread(String num,String name,String author,double price) {
		
		book.setAuthor(author);
		book.setBookName(name);
		book.setBookNum(num);
		book.setPrice(price);
	}
	
	public void run(){
		
		FileWriter fWriter = null;
		
		BufferedWriter bWriter = null;
		
		try{
			fWriter = new FileWriter("book.txt",true);
			bWriter = new BufferedWriter(fWriter);
			String name = book.getBookName();
			String author = book.getAuthor();
			String num = book.getBookNum();
			double price = book.getPrice();
			/*清空缓冲流*/
			bWriter.flush();
			
			bWriter.write(String.valueOf(num)+" ");
			bWriter.write(name+" ");
			bWriter.write(author+" ");
			bWriter.write(String.valueOf(price));
			bWriter.newLine();
			
			bWriter.flush();
			
			fWriter.close();
			bWriter.close();
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}

}
