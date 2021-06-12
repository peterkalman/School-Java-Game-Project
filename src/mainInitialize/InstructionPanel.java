
package mainInitialize;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ultilityTools.ImageTools;
/**
 * this class represents the panel of instructions extends JPanel because it is panel
 * 
 * @author Peter
 * @category Singleton - only contains one instance
 */
public class InstructionPanel extends JPanel {
	private JButton _backBtn;
	private JLabel _wasdInstruction;
	private JLabel _arrowInstruction;
	private JLabel _escapeInstruction;
	private static InstructionPanel singleton;
	/**
	 * Constructor initiates buttuns and sets listener
	 */
	private InstructionPanel() {
		_backBtn = ImageTools.makeButton("srcImages\\Menu\\mainMenuIcon.png");
		_wasdInstruction = ImageTools.makeLabel("srcImages\\Menu\\WasdKeyInstruction.png");
		_arrowInstruction = ImageTools.makeLabel("srcImages\\Menu\\ArrowKeyInstruction.png");
		_escapeInstruction = ImageTools.makeLabel("srcImages\\Menu\\EscapeInstruction.png");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		setBackground(Color.black);
		_backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				BackgroundImgPanel.getButtonPanel().setVisible(true);
			}
		});

		add(_wasdInstruction);
		add(_arrowInstruction);
		add(_escapeInstruction);
		add(_backBtn);
	}
	/**
	 * initialize the singleton instance
	 * 
	 * @return InstructionPanel only instance
	 */
	public static InstructionPanel init() {
		if (singleton == null)
			singleton = new InstructionPanel();
		return singleton;
	}
	/**
	 * get the Singleton instance
	 * 
	 * @return InstructionPanel only instance
	 */
	public static InstructionPanel getInstance() {
		return singleton;
	}
}
