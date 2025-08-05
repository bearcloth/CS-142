
public class DocumentTest {
	
	public static void main(String[] args) {
		Document doc1 = new Document("The Lathe of Heaven", "Ursula Leguin", 1963);
		System.out.println(doc1.getAuthor());
		System.out.println(doc1.edition);
		
//		doc1.author = "Freud Man";
//		doc1.title = "The Psychological Tin Man";
//		doc1.publicationYear = 1949;
		doc1.genre = "NonFiction";
		doc1.docType = "Thesis";
		doc1.edition = 1;
		
		System.out.println(doc1.getAuthor());
		
		Document doc2 = new Document("On Computable Numbers with Applicaitons", "Alan Turing", 1936);
//		doc2.author = "ALan Turing";
//		doc2.title = "On Computable Numbers with Applications";
//		doc2.publicationYear = 1936;
	}
}
