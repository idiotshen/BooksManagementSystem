package view;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SuccessDia extends JDialog{

	private JPanel body;
	private JPanel foot;
	private JButton ok;
	
	public SuccessDia(JFrame frame) {
		super(frame,"��ʾ",true);
		body = new JPanel();
		foot = new JPanel();
		ok = new JButton("ok");
	}
	public void init(){
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		body.add(new Label("�����ɹ�"));
		
		foot.add(ok);
		
		this.add(body);
		this.add(foot,BorderLayout.SOUTH);
		
		this.pack();
		this.setLocation(600, 250);
		this.setVisible(true);
	}
}
