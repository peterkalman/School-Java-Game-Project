package ultilityTools;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * FileChooser is a class that represents a file browser extends JFileChooser
 * because it is choosing
 * 
 * @author Peter
 *
 */
public class FileChooser extends JFileChooser {
	private JFileChooser _jfc;
	private String[] _filter = { ".png" };
	/**
	 * Constructor initiates the JFileChooser
	 */
	public FileChooser() {
		_jfc = new JFileChooser();

	}

	/**
	 * choose png file from the given startingFolderPath
	 * 
	 * @param startingFolderPath
	 *            - the starting browsing point
	 * @return the selected file
	 */
	public File choosePngFile(String startingFolderPath) {
		_jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		_jfc.setAcceptAllFileFilterUsed(false);
		/** set a filter */
		_jfc.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "Png files from clothing category";
			}

			@Override
			public boolean accept(File f) {
				for (int i = 0; i < _filter.length; i++)
					if (f.getName().endsWith(_filter[i]))
						return true;
				return f.isDirectory();
			}
		});
		File f = new File(startingFolderPath);
		_jfc.setCurrentDirectory(f);
		_jfc.showOpenDialog(null);
		return _jfc.getSelectedFile();
	}
}
