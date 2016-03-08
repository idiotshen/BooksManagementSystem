package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginDia extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel userlb;
	private JLabel passwordlb;
	private JTextField username;
	private JPasswordField password;
	private JButton login;
	private JButton exit;
	
	private JFrame frame;
	private JPanel head;
	private JPanel body;
	private JPanel foot;
	
	
	public LoginDia(JFrame frame,String title){
		super(frame, title,true);
		this.frame = frame;
		userlb = new JLabel("用户名");
		passwordlb = new JLabel("  密 码  ");
		username = new JTextField(15);
		password = new JPasswordField(15);
		login = new JButton("登陆");
		exit  = new JButton("退出");
		
		head = new JPanel();
		body = new JPanel();
		foot = new JPanel();
		
		
	}
	public void init(){
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * 
				 */
				
				
				
				if(new SystemController().login(username.getText(), String.valueOf(password.getPassword())).
						equals("success")){
					
					dispose();
					frame.setVisible(true);
				}
				else {
					System.out.println("jjja ");
					
				}
			}
		});
		exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		this.addWindowListener (new WindowAdapter()
        {
            public void windowClosing ( WindowEvent e )
            {
                System.exit(0);
            }
        });
		
		head.add(new JLabel("欢迎使用图书管理系统"));
		
		JPanel container = new JPanel();
		JPanel userpl = new JPanel();
		JPanel pswpl = new JPanel();
		container.setLayout(new GridLayout(2,1));
		userpl.add(userlb);
		userpl.add(username);
		pswpl.add(passwordlb);
		pswpl.add(password);
		container.add(userpl);
		container.add(pswpl);
		body.add(container);
		
		container = new JPanel();
		container.setLayout(new FlowLayout(FlowLayout.CENTER,30,5));
		container.add(login);
		container.add(exit);
		foot.add(container);
		
		this.add(head,BorderLayout.NORTH);
		this.add(body,BorderLayout.CENTER);
		this.add(foot,BorderLayout.SOUTH);
		
		this.setLocation(600, 250);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	
}
