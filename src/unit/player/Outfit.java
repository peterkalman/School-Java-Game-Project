package unit.player;

import ultilityTools.FileChooser;
import ultilityTools.FileFinder;
import unit.customizeScreen.CustomizeScreen;

/**
 * Outfit represents the general outfit of a character
 * 
 * @author Peter
 */
public class Outfit {
	private FileChooser _fc;
	private ApparelSet _figure;
	private ApparelSet _face;
	private ApparelSet _bottom;
	private ApparelSet _top;
	private ApparelSet _hair;
	private ApparelSet _headgear;
	private ApparelSet _weapon;

	/**
	 * Constructor prepares file selector ( will be replaced with selection panel) initialitezes all
	 * apparel sets
	 */
	public Outfit() {
		_fc = new FileChooser();
		_figure = new ApparelSet();
		_face = new ApparelSet();
		_bottom = new ApparelSet();
		_top = new ApparelSet();
		_hair = new ApparelSet();
		_headgear = new ApparelSet();
		_weapon = new ApparelSet();
	}
	/**
	 * fills all of the lists with a selection from the Clothing\Weapons folder that all ends with front
	 */
	public void fillAllApparels() {
		_figure.addFrontSet("Pawns\\Humans\\", FileFinder.getAllFiles("srcImages\\Pawns\\Humans\\", "_front.png"));
		_face.addFrontSet("Pawns\\Clothing\\Faces\\",
				FileFinder.getAllFiles("srcImages\\Pawns\\Clothing\\Faces", "_front.png"));
		_bottom.addFrontSet("Pawns\\Clothing\\Bottom\\",
				FileFinder.getAllFiles("srcImages\\Pawns\\Clothing\\Bottom", "_front.png"));
		_top.addFrontSet("Pawns\\Clothing\\Top\\",
				FileFinder.getAllFiles("srcImages\\Pawns\\Clothing\\Top", "_front.png"));
		_hair.addFrontSet("Pawns\\Clothing\\Hair\\",
				FileFinder.getAllFiles("srcImages\\Pawns\\Clothing\\Hair", "_front.png"));
		_headgear.addFrontSet("Pawns\\Clothing\\HeadGear\\",
				FileFinder.getAllFiles("srcImages\\Pawns\\Clothing\\HeadGear", "_front.png"));
		if (CustomizeScreen.getInstance() == null)
			_weapon.addFrontSet("Pawns\\Weapons\\Rifles\\",
					FileFinder.getAllFiles("srcImages\\Pawns\\Weapons\\Rifles", ""));
		else
			_weapon.addFrontSet("Pawns\\Weapons\\" + CustomizeScreen.getInstance().getWeaponSelected() + "\\",
					FileFinder.getAllFiles(
							"srcImages\\Pawns\\Weapons\\" + CustomizeScreen.getInstance().getWeaponSelected(), ""));
		//System.out.println(_weapon.getAllFrontSets());
	}

	/**
	 * Selects the files for each apparel accordiongly
	 */
	/*
	 * public void SelectAllApparels() {
	 * setFigureFile("Pawns\\Humans\\" + _fc.choosePngFile("srcImages\\Pawns\\Humans").getName());
	 * setFaceFile("Pawns\\Clothing\\Faces\\" + _fc.choosePngFile("srcImages\\Pawns\\Clothing\\Faces
	 * ").getName());
	 * setBottomFile("Pawns\\Clothing\\Bottom\\" + _fc.choosePngFile("srcImages\\Pawns\\Clothing\\
	 * Bottom").getName());
	 * setTopFile("Pawns\\Clothing\\Top\\" + _fc.choosePngFile("srcImages\\Pawns\\Clothing\\Top").
	 * getName());
	 * setHairFile("Pawns\\Clothing\\Hair\\" + _fc.choosePngFile("srcImages\\Pawns\\Clothing\\Hair")
	 * .getName()); setHeadgearFile(
	 * "Pawns\\Clothing\\HeadGear\\" + _fc.choosePngFile("srcImages\\Pawns\\Clothing\\HeadGear").
	 * getName()); // setWeaponFile(weaponFile); //
	 * _weapon.setSide(_fc.choosePngFile("src\\images\\srcImages\\Pawns\\Weapons").getPath()); }
	 */
	/**
	 * Set default apearal
	 */
	public void SelectAllDefault() {
		setFigureFile("Pawns\\Humans\\Hum1_front.png");
		setFaceFile("Pawns\\Clothing\\Faces\\Face1_front.png");
		setBottomFile("Pawns\\Clothing\\Bottom\\MilitaryPants_Male_front.png");
		setTopFile("Pawns\\Clothing\\Top\\Combat_Male_front.png");
		setHairFile("Pawns\\Clothing\\Hair\\crisis_front.png");
		setHeadgearFile("Pawns\\Clothing\\HeadGear\\Addyhat_front.png");
		setWeaponFile("Pawns\\Weapons\\Rifles\\MD50.png");
	}
	/**
	 * set a new Figure instead  and isOutfit is true since its not weapon related.
	 * @param figureFile - the new figure file path
	 */
	public void setFigureFile(String figureFile) {
		_figure.setFile(figureFile, true);
	}
	/**
	 * set a new Face instead and isOutfit is true since its not weapon related.
	 * @param faceFile - the new face file path
	 */
	public void setFaceFile(String faceFile) {
		_face.setFile(faceFile, true);
	}
	/**
	 * set a new Bottom instead and isOutfit is true since its not weapon related.
	 * @param bottomFile -the new bottom file path
	 */
	public void setBottomFile(String bottomFile) {
		_bottom.setFile(bottomFile, true);
	}
	/**
	 * set a new top instead and isOutfit is true since its not weapon related.
	 * @param topFile -the new top file path
	 */
	public void setTopFile(String topFile) {
		_top.setFile(topFile, true);
	}
	/**
	 * set a new hair instead and isOutfit is true since its not weapon related.
	 * @param hairFile -the new hair file path
	 */
	public void setHairFile(String hairFile) {
		_hair.setFile(hairFile, true);
	}
	/**
	 * set a new headgear instead and isOutfit is true since its not weapon related.
	 * @param headgearFile -the new headgear file path
	 */
	public void setHeadgearFile(String headgearFile) {
		_headgear.setFile(headgearFile, true);
	}
	/**
	 * set a new weapon instead and isOutfit 
	 * @param weaponFile -the new weapon file path
	 */
	public void setWeaponFile(String weaponFile) {
		_weapon.setMatchingNoDirections(weaponFile);
	}
	/**
	 * @return the FileChooser that helps select files
	 */
	public FileChooser getFc() {
		return _fc;
	}
	/**
	 * @return the figure set
	 */
	public ApparelSet getFigure() {
		return _figure;
	}
	/**
	 * @return the face set
	 */
	public ApparelSet getFace() {
		return _face;
	}
	/**
	 * @return the bottom set
	 */
	public ApparelSet getBottom() {
		return _bottom;
	}
	/**
	 * @return the top set
	 */
	public ApparelSet getTop() {
		return _top;
	}
	/**
	 * @return the hair set
	 */
	public ApparelSet getHair() {
		return _hair;
	}
	/**
	 * @return the headgear set
	 */
	public ApparelSet getHeadgear() {
		return _headgear;
	}
	/**
	 * @return the weapon set
	 */
	public ApparelSet getWeapon() {
		return _weapon;
	}

}
