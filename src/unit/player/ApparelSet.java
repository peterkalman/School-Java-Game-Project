package unit.player;

import java.io.File;
import java.util.LinkedList;

/**
 * ApparelSet is a class that represents a certain ware(clothing) at all directions
 * 
 * @author Peter
 */
public class ApparelSet {
	private LinkedList<String> _allFrontSets;
	private int _currentSetIndex;
	private String _front;
	private String _back;
	private String _side;

	/**
	 * constructor empty sets
	 */
	public ApparelSet() {
		this(null, null, null);
	}

	/**
	 * Constructor
	 * 
	 * @param front
	 *            - front Img Path
	 * @param back
	 *            - back Img Path
	 * @param side
	 *            - side Img Path
	 */
	public ApparelSet(String front, String back, String side) {
		_currentSetIndex = 0;
		_front = front;
		_back = back;
		_side = side;
	}

	/**
	 * adds to allFrontSets all of the items on the list with the previous path that is received
	 * 
	 * @param prePath
	 *            - the previous path that is received
	 * @param frontPaths
	 *            - list that contains Strings that path to a front img
	 */
	public void addFrontSet(String prePath, LinkedList<String> frontPaths) {
		_allFrontSets = new LinkedList<String>();
		_currentSetIndex = 0;
		for (int i = 0; i < frontPaths.size(); i++) {
			_allFrontSets.add(prePath + frontPaths.get(i));
		}
	}

	/**
	 * which front img is being used right now by comparing it to every single one and setting the
	 * currentSetIndex to it.
	 */
	public void findCurrentSetIndex() {
		for (int i = 0; i < _allFrontSets.size(); i++) {
			if (_allFrontSets.get(i).equals(_front)) {
				_currentSetIndex = i;
				return;
			}
		}
	}

	/**
	 * set to default (first) option for both weapons or clothing.
	 */
	public void resetCurrent() {
		_front = new String(_allFrontSets.getFirst());
		if (!_front.contains("Weapons")) {
			setFront(_allFrontSets.get(_currentSetIndex), true);
			putMatching(0);
		} else
			setMatchingNoDirections(_allFrontSets.get(_currentSetIndex));
		//System.out.println(_front);
	}

	/**
	 * exclusivly used for weapons since i have only 1 model for a weapon
	 * 
	 * @param file
	 *            - the file name
	 */
	public void setMatchingNoDirections(String file) {
		File f = new File("srcImages\\" + file);
		if (f.isFile()) {
			_front = new String(file);
			_back = new String(file);
			_side = new String(file);
		}

	}

	/**
	 * picking next item on the list or previous depends on the variable received
	 * 
	 * @param isNext
	 *            - if true than pick the next item on list , else pick the previous.
	 */
	public void switchTo(boolean isNext) {

		if (isNext) {
			_currentSetIndex = (_currentSetIndex + 1) % _allFrontSets.size();

		} else {
			if (_currentSetIndex == 0)
				_currentSetIndex = _allFrontSets.size();
			_currentSetIndex = (_currentSetIndex - 1) % _allFrontSets.size();
		}
		//System.out.println("Apperal " + _allFrontSets.get(_currentSetIndex));
		if (!_front.contains("Weapons")) {
			setFront(_allFrontSets.get(_currentSetIndex), true);
			putMatching(0);
		} else
			setMatchingNoDirections(_allFrontSets.get(_currentSetIndex));
	}

	/**
	 * the function puts matching ImgPaths for the side that is forced.0-front,1-back,2-side
	 * 
	 * @param force
	 *            - the force decides who exactly to force the check
	 */
	public void putMatching(int force) {
		if (_front != null && force == 0) {
			_back = new String(_front.substring(0, _front.lastIndexOf('_')) + "_back.png");
			_side = new String(_front.substring(0, _front.lastIndexOf('_')) + "_side.png");
			return;
		}
		if (_back != null && force == 1) {
			_front = new String(_back.substring(0, _back.lastIndexOf('_')) + "_front.png");
			_side = new String(_back.substring(0, _back.lastIndexOf('_')) + "_side.png");
			return;
		}
		if (_side != null && force == 2) {
			_back = new String(_side.substring(0, _side.lastIndexOf('_')) + "_back.png");
			_front = new String(_side.substring(0, _side.lastIndexOf('_')) + "_front.png");
			return;
		}
	}

	/**
	 * the function checks if all 3 pieces exist , and if they String Paths contain the proper
	 * endings
	 * 
	 * @return true if everyting is fine ,else false
	 */
	public boolean checkIfSamePiece() {
		if (_front == null || _back == null || _side == null)
			return false;
		File frontFile = new File(_front);
		File backFile = new File(_back);
		File sideFile = new File(_side);
		if (!frontFile.exists() || !backFile.exists() || !sideFile.exists())
			return false;
		String frontName = frontFile.getName();
		String backName = backFile.getName();
		String sideName = sideFile.getName();
		String subStrArr[] = { frontName.substring(0, frontName.lastIndexOf('_')),
				backName.substring(0, backName.lastIndexOf('_')), sideName.substring(0, sideName.lastIndexOf('_')) };

		return (subStrArr[0] == subStrArr[1] && subStrArr[1] == subStrArr[2]);
	}

	/**
	 * the function receives a file , doesnt know which ending it has. decides which set to do ,
	 * based on the ending of the file path
	 * 
	 * @param file
	 *            - the file path of the img
	 */
	public void setFile(String file, boolean isOutfit) {
		if (file.endsWith("_front.png"))
			setFront(file, isOutfit);
		else if (file.endsWith("_back.png"))
			setBack(file);
		else if (file.endsWith("_side.png"))
			setSide(file);
		//else
			//System.out.println(file + " BAD FILE");

	}

	/**
	 * set the front Image,forces matching for front
	 * 
	 * @param front
	 *            - the front imgPath
	 */
	public void setFront(String front, boolean isOutfit) {
		_front = front;
		if (isOutfit)
			findCurrentSetIndex();
		putMatching(0);
	}

	/**
	 * set the back Image,forces matching for back
	 * 
	 * @param back
	 *            - the back imgPath
	 */
	public void setBack(String back) {
		_back = back;
		putMatching(1);
	}

	/**
	 * set the side Image,forces matching for side
	 * 
	 * @param side
	 *            - the side imgPath
	 */
	public void setSide(String side) {
		_side = side;
		putMatching(2);
	}
	/**
	 * @return the front direction img path
	 */
	public String getFront() {
		return _front;
	}
	/**
	 * @return the back direction img path
	 */
	public String getBack() {
		return _back;
	}
	/**
	 * @return the side direction img path
	 */
	public String getSide() {
		return _side;
	}
	/** 
	 * @return the list containing all front direction img paths
	 */
	public LinkedList<String> getAllFrontSets() {
		return _allFrontSets;
	}

}
