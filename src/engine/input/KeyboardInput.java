package engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import map.EscapeButtonInterface;

/**
 * KeyboardInput is a class that is used for inputing keyboard input from the user , extends JPanel because it is an invisible Panel , and implements KeyListener because it listens to keyboard clicks.
 * <br/>
 * Since the class is a singleton , it uses getInstance to give out the static appearance. if an outer class wants to use the keyboard input it should use addListener and send itselves there.
 * @category Singleton - only contains one instance
 * @author Peter
 * 
 */
public class KeyboardInput extends JPanel implements KeyListener {
	/** singleton */
	private static KeyboardInput _singleton = new KeyboardInput();
	/** the list of listeners */
	private LinkedList<Object> _listenerList;
	/** the list of all keyboard keys */
	private LinkedList<Boolean> _keys;

	/**
	 * the constructor creates a new Listener list and adds 256 keys according to ascii code.<br/>
	 * constructor is private because of singleton.
	 */
	private KeyboardInput() {
		_listenerList = new LinkedList<Object>();
		_keys = new LinkedList<Boolean>();
		for (int i = 0; i < 256; i++) {
			_keys.add(false);
		}
	}

	/**
	 * get the Singleton instance
	 * 
	 * @return KeyboardInput only instance
	 */
	public static KeyboardInput getInstance() {
		return _singleton;
	}
	
	public void resetAllKeys(){
		for (int i = 0; i < 256; i++) {
			setValue(i,false);
		}
	}
	/**
	 * the function receives an object which could be anything therefore the conversions to frame or panel ,and adds to that object a key Listener which is the current KeyboardInput
	 * 
	 * @param listener
	 *                - the object that wants to listen to events
	 */
	public void addListener(Object listener) {
		if (listener instanceof JFrame)
			((JFrame) listener).addKeyListener(this);
		else if (listener instanceof JPanel)
			((JPanel) listener).addKeyListener(this);
		_listenerList.addLast(listener);
	}

	/**
	 * set the value the key in the list
	 */
	public void setValue(int index, boolean value) {
		_keys.set(index, value);
	}

	/**
	 * the function alert all listeners of the required interfaces
	 */
	public void alertListeners() {
		/**
		 * if we need to alert all EscapeButtonListeners check that they actually want to listen to EscapeButtons
		 */
		if (_keys.get(KeyEvent.VK_ESCAPE))
			for (int i = 0; i < _listenerList.size(); i++)
				if (_listenerList.get(i) instanceof EscapeButtonInterface) {
					((EscapeButtonInterface) _listenerList.get(i)).EscapeButtonClicked();
					_keys.set(KeyEvent.VK_ESCAPE, false);
				}
	}

	/**
	 * activate the key accordiongly to the key pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		try {
			_keys.set(e.getKeyCode(), true);
		} catch (IndexOutOfBoundsException b) {
		}
		alertListeners();
	}

	/**
	 * disable the key accordiongly to the key released
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		try {
			_keys.set(arg0.getKeyCode(), false);
		} catch (IndexOutOfBoundsException e) {
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	/**
	 * @return the list of all boolean keyboard keys
	 */
	public LinkedList<Boolean> get_keys() {
		return _keys;
	}

	public void cleanListenerList() {
		_listenerList.clear();
	}
	
}
