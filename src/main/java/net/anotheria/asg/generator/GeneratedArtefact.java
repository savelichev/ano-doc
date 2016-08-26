package net.anotheria.asg.generator;

/**
 * Base class for artefacts generated by the ASG.
 * @author lrosenberg
 */
public abstract class GeneratedArtefact {
	
	/**
	 * Constant for line separator (CR + LF).
	 */
	public static final String CRLF = AbstractGenerator.CRLF;

	/**
	 * Name of the artefact.
	 */
	private String name;
	/**
	 * Creates the content of the file.
	 * @return
	 */
	public abstract String createFileContent();
	/**
	 * Returns the file type (i.e. .java or .jsp).
	 * @return
	 */
	public abstract String getFileType();
	/**
	 * Returns the relative path where the artefact must be stored on the filesystem. 
	 * @return
	 */
	public abstract String getPath();
	/**
	 * Returns the body of the generated artefact.
	 * @return
	 */
	public abstract StringBuilder getBody();
	
	/**
	 * Returns the name of the artefact.
	 * @return
	 */
	public String getName(){
		return name;
	}
	/**
	 * Sets the name of the artefact.
	 * @param aName
	 */
	public void setName(String aName){
		name = aName;
	}
}