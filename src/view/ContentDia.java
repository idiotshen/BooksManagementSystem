package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ContentDia extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame frame;
	private JPanel body;
	private JPanel foot;
	
	private JLabel key;
	private String id;
	private String name;
	private String author;
	private String price;
	private JTextField idtf;
	private JTextField nametf;
	private JTextField authortf;
	private JTextField pricetf;
	private JButton del;
	private JButton modify;
	
	public ContentDia(MainFrame frame) {
		
		super(frame,"修改/删除",true);
		this.frame = frame;
		body = new JPanel();
		foot = new JPanel();
		del = new JButton("删除");
		modify = new JButton("修改");
		idtf = new JTextField(30);
		nametf = new JTextField(30);
		authortf = new JTextField(30);
		pricetf = new JTextField(30);
	}

	public void init(String id,String name,String author,String price){
		
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
		
		//删除
		del.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				/*
				 * idtf.getText()
				 * nametf.getText()
				 * authortf.getText()
				 * pricetf.getText()
				 */
				
				/**
				 * 找到要删除的索引信息
				 */
				String info1 ;
				int index;
				if(idtf.getText()!=null){
					info1 = idtf.getText();
					index = 0;
				}
				else if(nametf.getText()!=null){
					info1 = nametf.getText();
					index = 1;
				}
				else if(authortf.getText()!=null){
					info1 = authortf.getText();
					index = 2;
				}
				else{
					info1 = pricetf.getText();
					index = 3;
				}
				
				String msg = "";
				try{
					Client clientTest = new Client();
					clientTest.sendInfo("delete/"+info1+"/"+index+"\n");
					msg = clientTest.getDeleteMessage();
				}catch(Exception e1){
					
				}
				if(msg.equals("success")){
					/*
					 * 弹出成功的界面
					 */
					new SuccessDia(frame).init();
					dispose();
				}else{
					/*
					 * 弹出失败的界面
					 */
					new ErrorDia(frame).init();
				}
				frame.resetData(frame.getBookObject());
			}
			
		});
		
		//修改
		modify.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
				String msg = "";
				/*sr+= idtf.getText()+" ";
				sr+= nametf.getText()+" ";
				sr+= authortf.getText()+" ";
				sr+= pricetf.getText();*/
				//处理
				
				String info ="";
				
				try{
					Client clientTest = new Client();
					clientTest.sendInfo("modify/"+idtf.getText()+"/"+"0/"+pricetf.getText()+'\n');
					msg = clientTest.getModifyMessage();
					System.out.print(msg+"dhhdh");
				}catch(Exception e1){
					e1.printStackTrace();
				}
				if(msg.equals("success")){
					/*
					 * 弹出成功界面
					 * 
					 */
					new SuccessDia(frame).init();
					dispose();
				}else{
					/*
					 * 弹出失败界面
					 */
					new ErrorDia(frame).init();
				}
				//重写Mainframe内容
				frame.resetData(frame.getBookObject());
			}
		});
		
		body.setLayout(new GridLayout(4,2));
		addRow("书号", id);
		addRow("书名",name);
		addRow("作者",author);
		addRow("价格",price);
	
		foot.setLayout(new FlowLayout(FlowLayout.RIGHT));
		foot.add(del);
		foot.add(modify);
	
		this.add(body);
		this.add(foot,BorderLayout.SOUTH);
	
		this.setLocation(530, 200);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void addRow(String key,String value){
		JPanel jp = new JPanel();
		JLabel jl = new JLabel(key);
		jp.add(jl);
		if(key.equals("书号")){
			idtf.setText(value);
			jp.add(idtf);
		}
		else if(key.equals("书名"))
		{
			nametf.setText(value);
			jp.add(nametf);
		}
		else if(key.equals("作者")){
			authortf.setText(value);
			jp.add(authortf);
		}
		else{
			pricetf.setText(value);
			jp.add(pricetf);
		}
		body.add(jp);
	}
	
	
}
