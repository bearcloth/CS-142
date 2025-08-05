
public class Document {
	/**
	 * Everything public should have a JavaDoc
	 */
	public final String title;
	
	private final String author;
	
	public final int publicationYear;
	
	public String genre;
	
	public String docType;
	
	public int volume;
	
	public int number;
	
	public int edition;
	
	public static int totalNumDocs = 0;
	
	/**
	 * Constructor.
	 * @param title Title of document
	 * @param author Author of doc
	 * @param publicationYear When doc was published
	 */
	public Document(String title, String author, int publicationYear) {
		
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
		totalNumDocs++;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getTitle() {
		return this.title;
	}
	
}
