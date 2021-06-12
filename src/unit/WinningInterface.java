package unit;
/**
 * Interface of a winning unit (Player\Enemy) all units that play must implement.
 * @author Peter
 *
 */
public interface WinningInterface {
	public void pauseUnit();
	public void startUnit();
	//public void removeUnit();
	public void actionIfWin();
	public boolean isWin();
	//public int getRealX();
	//public int getRealY();
}

