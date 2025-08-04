package HillCypher;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cypher extends JFrame{

	private JTextField message;
	private JTextField key;
	private JTextField answer;
	private JButton Decrypt;
	private JButton Encrypt;
	Queue<String> splitQ = new LinkedList<>();
	Queue<int[][]> splitM = new LinkedList<>();

	public Cypher()
	{
		super("Reids Hill Cypher");
		setBounds(25,25,550,125);
		
		JPanel panel = new JPanel();
		
		JLabel num1Label = new JLabel();
		num1Label.setFont(new Font("Courier", Font.BOLD, 12));
		num1Label.setText("Message:");
		message = new JTextField(10);
		panel.add(num1Label);
		panel.add(message);

		JLabel num2Label = new JLabel();
		num2Label.setFont(new Font("Courier", Font.BOLD, 12));
		num2Label.setText("Key:");
		key = new JTextField(10);
		panel.add(num2Label);
		panel.add(key);
		
		JLabel answerLabel = new JLabel();
		answerLabel.setFont(new Font("Courier", Font.BOLD, 12));
		answerLabel.setText("Output:");
		answer = new JTextField(10);
		panel.add(answerLabel);
		panel.add(answer);
		answer.setEditable(false);
		
		add(panel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		
		Decrypt = new JButton("Decrypt");
		Encrypt = new JButton("Encrypt");
		buttonPanel.add(Decrypt);
		buttonPanel.add(Encrypt);
		add(buttonPanel, BorderLayout.SOUTH);
		
		Decrypt.addActionListener(new ButtonListener());
		Encrypt.addActionListener(new ButtonListener());
		
		setResizable(false);
		setVisible(true);
	}
	
	private class ButtonListener implements ActionListener
	{
		public void messageSplit(){
			String messageS = message.getText().toUpperCase().replaceAll("[^A-Z]", "");
			splitQ.clear();
			for(int i = 0; i < messageS.length(); i+=3) {
				String splitS = messageS.substring(i, Math.min(i + 3, messageS.length()));
				while (splitS.length() < 3) {
			        splitS += "X";
			    }
				splitQ.add(splitS);
			}
		}
		
		public matrixCreate messageToMatrix() {
		    int[][] data = new int[3][1];
		    String messageS = message.getText().toUpperCase().replaceAll("[^A-Z]", "");
		    splitM.clear();
		    
			if(messageS.length() > 3) {
				messageSplit();
				
				while(!splitQ.isEmpty()) {
					String poll = splitQ.poll();
					int[][] block = new int[3][1];
					for(int i = 0; i < 3; i++) {
						char letter = poll.charAt(i);
					    block[i][0] = letter - 'A';
					}
					splitM.add(block);
				}
				return null;
		    }
			else{
			    while (messageS.length() < 3) {
			        messageS += "X";
			    }
			   
			    for(int i = 0; i < 3; i++) {
			        char letter = messageS.charAt(i);
			        data[i][0] = letter - 'A';
			    }
			}
			return new matrixCreate(data);
		}
		
		public matrixCreate keyToMatrix() {
		    int[][] data = new int[3][3];
		    String keyS = key.getText().toUpperCase().replaceAll("[^A-Z]", "");
		    while (keyS.length() < 9) {
		        keyS += "X";
		    }

		    int index = 0;
		    for (int i = 0; i < 3; i++) {
		        for (int j = 0; j < 3; j++) {
		            data[i][j] = keyS.charAt(index) - 'A';
		            index++;
		        }
		    }

		    return new matrixCreate(data);
		}
		
		public void actionPerformed(ActionEvent ae)
		{	
			if(ae.getSource() == Decrypt)
			{
				try
				{
					StringBuilder result = Decrypt(messageToMatrix(), keyToMatrix());
					answer.setText(result + "");
				}
				catch(Exception e)
				{
					String test = e.getMessage();
					answer.setText(test);
				}
			}
			if(ae.getSource() == Encrypt)
			{
				try
				{
					StringBuilder result = Encrypt(messageToMatrix(), keyToMatrix());
					answer.setText(result + "");
				}
				catch(Exception e)
				{
					String test = e.getMessage();
					answer.setText(test);
				}
			}
		}
	}
	
	public StringBuilder Decrypt(matrixCreate messageM, matrixCreate keyM)
	{
		StringBuilder decrypt = new StringBuilder();
		matrixCreate transpose = keyM.transpose();
		matrixCreate invMod26 = transpose.inverseMod26();
		if(messageM == null) {
			while (!splitM.isEmpty()) {
				int[][] block = splitM.poll();
				matrixCreate temp = new matrixCreate(block);
				matrixCreate product = invMod26.multiply(temp).mod26();
				for(int i = 0; i < product.getRows(); i++) {
					decrypt.append((char)(product.get(i, 0) + 'A'));
				}
			}		
		}
		else {
			matrixCreate product = invMod26.multiply(messageM).mod26();
			for(int i = 0; i < product.getRows(); i++) {
				decrypt.append((char)(product.get(i, 0) + 'A'));
			}
		}
//		System.out.println("Encrypting with key:");
//		keyM.print();
//
//		System.out.println("Message matrix:");
//		messageM.print();
//
//		System.out.println("Encrypted matrix:");
//		product.print(); 
//
//		System.out.println("Decrypting with inverse:");
//		invMod26.print();
//
//		System.out.println("Decrypted matrix:");
//		System.out.println(decrypt); 
		
		return decrypt;
	}
	
	public StringBuilder Encrypt(matrixCreate messageM, matrixCreate keyM)
	{
		StringBuilder encrypt = new StringBuilder();
		if(messageM == null) {
			while (!splitM.isEmpty()) {
				int[][] block = splitM.poll();
				matrixCreate temp = new matrixCreate(block);
				matrixCreate product = keyM.multiply(temp).mod26();
				for(int i = 0; i < product.getRows(); i++) {
					encrypt.append((char)(product.get(i, 0) + 'A'));
				}
			}
		}
		else {
			matrixCreate product = keyM.multiply(messageM).mod26();
			for(int i = 0; i < product.getRows(); i++) {
				encrypt.append((char)(product.get(i, 0) + 'A'));
			}
		}
		return encrypt;
	}
	
	public static void main(String[] args) 
	{
		Cypher frame = new Cypher();
	}

}
