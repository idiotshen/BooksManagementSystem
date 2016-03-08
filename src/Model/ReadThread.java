package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


//��������
public class ReadThread implements Runnable{
	
	Book book = new Book();
	
	//���λ��0��1��2��3�ֱ������ţ����������ߣ��۸�
	private int flag;
	
	//4�й��캯������������
	
	public List<Book> list = new ArrayList<Book>();

	
	public ReadThread(String name,int flag){
		if(flag == 0){
			book.setBookNum(name);
		}
		else if(flag == 1){
			
			book.setBookName(name);
			
		}
		else if(flag == 2){
			
			book.setAuthor(name);
		}
		else if(flag == 3){
			/*System.out.println("price::"+name);*/
			book.setPrice(Double.parseDouble(name));
		}
		this.flag = flag;
	}
	
	
	
	public List<Book> getList() {
		return list;
	}
	
	
	public void setList(List<Book> list) {
		this.list = list;
	}
	
	
	
	
	
	
	
	//���ж�����
	public void run() {
		
		//list.clear();
		
		FileReader fReader = null;
		
		BufferedReader br = null;
		
		try{
			fReader = new FileReader("book.txt");
			br = new BufferedReader(fReader);
			String string = new String();
			String num = new String();
			String name = new String();
			String author = new String();
			double price = 0;
			
			if(flag == 0){
				 num = book.getBookNum();
			}
			else if(flag == 1){
				
				 name = book.getBookName();
			}
			else if(flag == 2){
				 author = book.getAuthor();
				 
			}
			else{
				 price = book.getPrice();
			}
			
			
			
			while((string=br.readLine())!=null){
				
				String[] strings = string.split(" ");
				
					//System.out.println(strings[2]);
				
				if(flag == 0){
					
					if(strings[0].equals(num)){
						Book book1 = new Book();
						book1.setBookNum(strings[0]);
						book1.setBookName(strings[1]);
						book1.setAuthor(strings[2]);
						book1.setPrice(Double.parseDouble(strings[3]));
						list.add(book1);
					}
				}
				else if(flag == 1){
					//System.out.println(strings[0]);
					if(strings[1].equals(name)){
						Book book1 = new Book();
						book1.setBookNum(strings[0]);
						book1.setBookName(strings[1]);
						book1.setAuthor(strings[2]);
						book1.setPrice(Double.parseDouble(strings[3]));
						list.add(book1);
					}
					
				}
				else if(flag == 2){
					//System.out.println(strings[2]+"����");
					if(strings[2].equals(author)){
						//System.out.println(author);
						Book book1 = new Book();
						book1.setBookNum(strings[0]);
						book1.setBookName(strings[1]);
						book1.setAuthor(strings[2]);
						book1.setPrice(Double.parseDouble(strings[3]));
						list.add(book1);
					}
					//System.out.println(list.get(0).getBookName()+"hello world");
					
				}
				else if(flag == 3){
					Double ss = Double.parseDouble(strings[3]);
					price = price*1.0;
					
					if(ss==price){
						Book book1 = new Book();
						book1.setBookNum(strings[0]);
						book1.setBookName(strings[1]);
						book1.setAuthor(strings[2]);
						book1.setPrice(Double.parseDouble(strings[3]));
						list.add(book1);
					}
				}
				
			}
			fReader.close();
			br.close();
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}
	
}
