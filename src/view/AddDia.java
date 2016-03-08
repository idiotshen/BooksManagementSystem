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

public class AddDia extends JDialog{

	/**
	 * 
	 */
	
	
	@SuppressWarnings("unused")
	private MainFrame frame;
	private JPanel body;
	private JPanel foot;
	
	private JTextField id;
	private JTextField name;
	private JTextField author;
	private JTextField price;
	private JButton ok;
	
	public AddDia(MainFrame frame) {
		
		super(frame,"�����Ŀ",true);
		this.frame = frame;
		body = new JPanel();
		foot = new JPanel();
		id = new JTextField(30);
		name = new JTextField(30);
		author = new JTextField(30);
		price = new JTextField(30);
		ok = new JButton("ȷ��");
	}
	public String addBook(){
		
		String str = id.getText()+'/'+name.getText()+'/'+author.getText()+'/'+price.getText()+'\n';
		//System.out.println(str);
		return str;
	}
	public void init(){
		
		/*
		 * add 
		 */
		ok.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
				
				//frame.setData();
				/*
				 * ���룿
				 */
				String msg = "";
				String info = addBook();
				try{
					Client clientTest = new Client();
					clientTest.sendInfo("add/"+info);
					msg = clientTest.getDeleteMessage();
					
				}catch(Exception e1){
					e1.printStackTrace();
				}
				if(msg.equals("success")){
					SuccessDia successDia = new SuccessDia(frame);
					successDia.init();
					dispose();
					
				}
				else{
					ErrorDia errorDia = new ErrorDia(frame);
					errorDia.init();
				}
				frame.resetData(frame.getBookObject());
				
			}
		});
		
		body.setLayout(new GridLayout(4,2));
		addRow("���", id);
		addRow("����",name);
		addRow("����",author);
		addRow("�۸�",price);
		
		foot.setLayout(new FlowLayout(FlowLayout.RIGHT));
		foot.add(ok);
		
		this.add(body);
		this.add(foot,BorderLayout.SOUTH);
		
		this.setLocation(530, 200);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	
	
	public void addRow(String key,JTextField value){
		JPanel jp = new JPanel();
		JLabel jl = new JLabel(key);
		jp.add(jl);
		jp.add(value);
		body.add(jp);
	}
}
