import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.Color;

public class Options extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public Options() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 899, 577);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Move Speed");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(50, 27, 116, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(50, 126, 116, 24);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(50, 233, 116, 24);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(50, 336, 116, 24);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(50, 426, 116, 24);
		contentPane.add(lblNewLabel_4);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(125, 31, 107, 20);
		contentPane.add(editorPane);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setBounds(125, 130, 107, 20);
		contentPane.add(editorPane_1);
		
		JEditorPane editorPane_2 = new JEditorPane();
		editorPane_2.setBounds(125, 233, 107, 20);
		contentPane.add(editorPane_2);
		
		JEditorPane editorPane_3 = new JEditorPane();
		editorPane_3.setBounds(125, 336, 107, 20);
		contentPane.add(editorPane_3);
		
		JEditorPane editorPane_4 = new JEditorPane();
		editorPane_4.setBounds(125, 430, 107, 20);
		contentPane.add(editorPane_4);
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(784, 11, 89, 40);
		contentPane.add(btnNewButton);
		
		JSlider slider = new JSlider();
		slider.setMajorTickSpacing(5);
		slider.setBounds(253, 426, 200, 26);
		contentPane.add(slider);
		btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		System.out.println("Closing Options");
        		TitleScreen frame = new TitleScreen();
				frame.setVisible(true);
        	}
        });
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Options frame = new Options();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
