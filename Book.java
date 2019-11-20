package BookStoreApp;

public class Book {
	private int id = 0;
	private String title = "";
	private String author = "";
	private int qty = 0;
	
	public Book(){
		
	}
	
	public Book(int id, String title, String author, int qty){
		this.id = id;
		this.title = title;
		this.author = author;
		this.qty = qty;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getAuthor(){
		return this.author;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public void setQty(int qty){
		this.qty = qty;
	}
	
	public int getQty(){
		return this.qty;
	}
	
	public String toString() {
		
		String output = "Id: " + getId();
		output += "\nTitle: " + getTitle();
		output += "\nAuthor: " + getAuthor();
		output += "\nQty: " + getQty();		

		return output;
	}
}
