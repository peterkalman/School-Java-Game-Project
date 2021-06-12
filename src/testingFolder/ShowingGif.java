package testingFolder;

import java.awt.Graphics;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ShowingGif extends JPanel {

	//Image image;

	public ShowingGif() throws MalformedURLException {

		ClassLoader cldr = this.getClass().getClassLoader();
		URL imageURL = cldr.getResource("images//srcImages//Menu//scene1.gif");
		ImageIcon imageIcon = new ImageIcon(imageURL);
		// new ImageIcon

		JLabel progress = new JLabel(imageIcon);
		imageIcon.setImageObserver(progress);
		progress.setBounds(5, 20, 66, 66);
		this.add(progress);

	}

	@Override
	public void paintComponent(Graphics g) {
		/*super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, this);
		}*/
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame();
				try {
					frame.add(new ShowingGif());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(400, 400);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}