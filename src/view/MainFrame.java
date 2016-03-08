package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;




public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private JPanel head;
	private JPanel body;
	private JPanel foot;
	private ButtonGroup buttons;
	private  JRadioButton  idrb;
	private JRadioButton  namerb;
	private JRadioButton  authorrb;
	private JRadioButton pricerb;
	private JTextField searchField;
	private JButton searchButton;
	private JButton addButton;
	private JButton freshButton;
	private JLabel headLabel;
	private JTable bookTable;
	private int tab;
	private String searchContent;
	
	Object [][] tableData = getBookObject();
	Vector columnTitle = new Vector();
	Vector tabledata  = new Vector();
	//
	public String string ;
	
	public MainFrame() {
		
		
		columnTitle.add("书号");
		columnTitle.add("书名");
		columnTitle.add("作者");
		columnTitle.add("单价");
		
		// TODO Auto-generated constructor stub
		frame = new JFrame("图书管理系统");
		headLabel = new JLabel("欢迎使用图书管理系统");
		head = new JPanel();
		body = new JPanel();
		foot = new JPanel();
		
		buttons = new ButtonGroup();
		idrb = new JRadioButton("书号",true);
		namerb = new JRadioButton("书名",false);
		authorrb = new JRadioButton("作者",false);
		pricerb = new JRadioButton("价格",false);
		buttons.add(idrb);
		buttons.add(namerb);
		buttons.add(authorrb);
		buttons.add(pricerb);
		
		searchField = new JTextField(30);
		searchButton = new JButton("搜索");
		addButton = new JButton("添加");
		freshButton = new JButton("刷新");
		setData(tableData);
		
		
	}
	public void init(){
		/*
		 * search
		 * 
		 */
		
		
		idrb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tab = 0;
			}
		});
		namerb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tab = 1;
			}
		});
		authorrb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tab = 2;
			}
		});
		pricerb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tab= 3;
			}
		});
		
		MouseListener mouseListener = new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount()==2){
					if(bookTable!=null)
						getContent();
				}
			}
		};
		
		bookTable.addMouseListener(mouseListener);
		/*
		 * seach content
		 */
		searchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				searchContent = searchField.getText();
				String msg="";
				try {
					Client	client = new Client();

					client.sendInfo("search/"+searchContent+'/'+tab+'\n');
					
					
					msg=client.getSearchMessage();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				if(msg.equals("")){
					/**
					 * 
					 * 搜索失败
					 */
					System.out.println("error");
					new ErrorDia(frame).init();
				}
				else{
				String[] ss = msg.split("/");
				Object[][] objects = new Object[ss.length][4];
				for(int i=0;i<ss.length;i++){
					for(int j=0;j<4;j++){
						
						objects[i][j] = ss[i].split(" ")[j];
						System.out.println(objects[i][j]+" ");
					}
				}
				
				resetData(objects);
				}
				
			}
		});
		
		addButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				AddDia ad = new AddDia(MainFrame.this);
				ad.init();
			}
		});
		freshButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/*setData(tableData);*/
				resetData(getBookObject());
			}
		});
		
		head.add(headLabel);
		frame.add(head,BorderLayout.NORTH);
		
		JPanel container = new JPanel();
		container.add(idrb);
		container.add(namerb);
		container.add(authorrb);
		container.add(pricerb);
		container.add(searchField);
		container.add(searchButton);
		body.setLayout(new BorderLayout());
		body.add(container,BorderLayout.NORTH);
		body.add(new JScrollPane(bookTable));
		frame.add(body);
		
		foot.add(addButton);
		foot.add(freshButton);
		frame.add(foot,BorderLayout.SOUTH);
		frame.setBounds(380,100,700,500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		LoginDia welcomeDia = new LoginDia(frame,"用户登录");
		welcomeDia.init();
	}
	//设置数据
	public Vector format(Object [][] ob){
		for(int i = 0;i< (ob.length);i++){
			Vector tmp = new Vector();
			for(int j = 0;j<(ob[0].length);j++){
				tmp.add(ob[i][j]);
			}
			tabledata.add(tmp);
		}
		return tabledata;
	}
	public void setData(Object[][] data){
		/*
		 * 
		 */
		tabledata = format(data);
		bookTable  = new JTable(new MytableModel(tabledata,columnTitle));	
		
	}
	public void resetData(Object[][] data){
		tabledata.clear();
		format(data);
		bookTable.updateUI();
	}
	//显示所有书的信息
	public Object[][] getBookObject() {
		
		
		FileReader fr = null;
		BufferedReader br = null;
		Object[][] objects = new Object[10000][4];
		try{
			
			
			fr = new FileReader("book.txt");
			br = new BufferedReader(fr);
			String s = new String();
			int index = 0;
			while((s=br.readLine())!=null){
				String[] strings = s.split(" ");
				for(int i=0;i<4;i++){
					objects[index][i] = strings[i]; 	
				}
				index++;
			}
			fr.close();
			br.close();
			
			
		}catch(IOException e){
			e.printStackTrace();
			
		}
		
		return objects;
	}
	
	
	
	
	
	public void getContent(){
		int row = bookTable.getSelectedRow();
		String bookId = bookTable.getValueAt(row, 0).toString();
		String bookName = bookTable.getValueAt(row, 1).toString();
		String bookAuthor = bookTable.getValueAt(row, 2).toString();
		String bookPrice = bookTable.getValueAt(row, 3).toString();
		ContentDia cd = new ContentDia(this);
		cd.init(bookId,bookName,bookAuthor,bookPrice);
		
		
	}
	
	
	
	public void setList(String s){
		string = s;
		System.out.println(s);
		
		
	}
	
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.init();

	}
}
