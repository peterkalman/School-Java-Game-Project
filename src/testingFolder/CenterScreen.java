package testingFolder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class CenterScreen {

	public static void main(String[] args) {
		new CenterScreen();
	}

	public CenterScreen() {
		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel content = new JPanel(new GridBagLayout());
		content.setBackground(Color.GREEN);
		//content.setBorder(new EmptyBorder(20, 20, 20, 20));
		frame.setContentPane(content);
		frame.add(new LoginPane());
		frame.pack();
		//frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public class LoginPane extends JPanel {

		public LoginPane() {
			add(new JTextField(10));
			add(new JTextField(10));
		}

	}

}