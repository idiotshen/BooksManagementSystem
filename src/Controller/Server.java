package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Model.Book;

public class Server {

	public ServerSocket ss = new ServerSocket(88);

	public Server() throws IOException {
		while(true) {
		Socket socket = ss.accept();
		new CreateServerThread(socket).start();
		}
	}

	public static void main(String[] args) throws IOException {

		new Server();

	}

}

class CreateServerThread extends Thread {

	private Socket client;
	private BufferedReader bufferedReader = null;
	private PrintWriter printWriter = null;

	private int MUTEX = 1;
	//读者的数量
	private int COUNT = 0;

	public CreateServerThread(Socket s) throws IOException {
		client = s;
		bufferedReader = new BufferedReader(new InputStreamReader(
				client.getInputStream()));
		printWriter = new PrintWriter(client.getOutputStream(), true);
		System.out.println("Client(" + getName() + ") come in...");
	}

	public synchronized void reader(String info, Socket clientSocket)
			throws InterruptedException {
	
		while (MUTEX == 0 && COUNT == 0) {
			wait();
		}

		if (COUNT == 0) {
			MUTEX--;
		}

		COUNT++;
		Service cs = new Service(clientSocket);
		List<Book> books = new ArrayList<Book>();
		
		System.out.println("info :"+info);
		books = cs.search(info);
		
		/*
		 * 
		 * 搜索
		 */
		//System.out.println(books.get(0).getBookName());
		//System.out.println(info);
		cs.reponse(books);
		COUNT--;
		if (COUNT == 0) {
			MUTEX++;
			notifyAll();
		}
	}

	public synchronized void writer(String info, Socket clientSocket,
			String flag) throws InterruptedException {
		while (MUTEX == 0) {
			wait();
		}

		MUTEX--;
		Service cs = new Service(clientSocket);

		if (flag == "add") {
			cs.reponseInfo(cs.add(info));
			
			
		}

		else if (flag == "delete") {
			cs.reponseInfo(cs.delete(info));
			
		}

		else if (flag == "modify") {
			String str = cs.modify(info);
			cs.reponseInfo(str);
		}
		MUTEX++;
		notifyAll();

	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}

	public void run() {
		BufferedReader br = null;
		try {
			System.out.println(client.getRemoteSocketAddress() + " connect");

			br = getReader(client);

			String string = new String();
			StringBuffer sb = new StringBuffer();

			while ((string = br.readLine()) != null) {

				int len = string.indexOf("/");
				String s = string.substring(0, len);
				String info = string.substring(len + 1);
				
				if (s.equals("search")) {
					this.reader(info, client);
				} else {
					if (s.equals("add")) {
						this.writer(info, client, "add");
					} else if (s.equals("delete")) {
						this.writer(info, client, "delete");
					} else {
						this.writer(info, client, "modify");
					}
				}
				break;
			}
			
			
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
}
