package BookStoreApp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BookStoreApp.Data;

public class BookRepository {

	Data data = null;

	public BookRepository() throws Exception {

		data = Data.getInstance();
	}

	/**
	 * Creates the database, tables and populates with some dummy data
	 * allows function search, delete, update create
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int SetupDatabase() throws SQLException {

		String query = "drop database if exists ebookstore; " + "create database if not exists ebookstore; "
				+ "use ebookstore; " +

				"DROP TABLE IF EXISTS books; " +

				"CREATE TABLE books( " + "id int, " + "title varchar(50), " + "author varchar(50), " + "qty int, "
				+ "primary key(id) " + "); " +

				"insert into books values (3001, 'A Tale of Two Cities', 'Charles Dickens', 30); "
				+ "insert into books values (3002, 'Harry Potter and the Philosopher stone', 'J.K. Rowling', 40); "
				+ "insert into books values (3003, 'The Lion, the Witch and the Wardrobe', 'C.S. Lewis', 25); "
				+ "insert into books values (3004, 'The Lord of the Rings', 'J.R.R. Tolkein', 37); "
				+ "insert into books values (3005, 'Alice in Wonderland', 'Lewis Carrol', 12); ";

		return data.Cud(query);
	}

	public int AddBook(Book book) throws SQLException {
		String query = String.format("insert into books (id, title, author, qty) " + " values(%s, '%s', '%s', %s)",
				book.getId(), book.getTitle(), book.getAuthor(), book.getQty());

		return data.Cud(query);
	}

	/***
	 * Get all books in database
	 * @return
	 * @throws SQLException
	 */
	public List<Book> GetAll() throws SQLException {
		String query = "Select * from books;";
		ResultSet _rawSql = data.Select(query);
		List<Book> _bookList = new ArrayList<Book>();

		// build models
		while (_rawSql.next()) {
			_bookList.add(new Book(_rawSql.getInt("id"), _rawSql.getString("title"), _rawSql.getString("author"),
					_rawSql.getInt("qty")));
		}

		return _bookList;
	}
	
	/***
	 * Get books by its unique id
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Book GetById(int id) throws SQLException{
		String query = String.format("Select * from books where id = %s;", id);
		ResultSet _rawSql = data.Select(query);
		Book _book = null;

		// build model
		while (_rawSql.next()) {
			_book = new Book(_rawSql.getInt("id"), _rawSql.getString("title"), _rawSql.getString("author"),
					_rawSql.getInt("qty"));
		}

		return _book;
	}
	
	/***
	 * Get book by its title, matches on exact name, no contains/like filter implemented
	 * @param title
	 * @return
	 * @throws SQLException
	 */
	public Book GetByTitle(String title) throws SQLException{
		String query = String.format("Select * from books where Title = '%s';", title);
		ResultSet _rawSql = data.Select(query);
		Book _book = null;

		// build model
		while (_rawSql.next()) {

			_book = new Book(_rawSql.getInt("id"), _rawSql.getString("title"), _rawSql.getString("author"),
					_rawSql.getInt("qty"));
		}

		return _book;
	}
	
	/***
	 * Get book by its author, matches on exact name, no contains/like filter implemented
	 * @param author
	 * @return
	 * @throws SQLException
	 */
	public Book GetByAuthor(String author) throws SQLException{
		String query = String.format("Select * from books where Author = '%s';", author);
		ResultSet _rawSql = data.Select(query);
		Book _book = null;

		// build model
		while (_rawSql.next()) {

			_book = new Book(_rawSql.getInt("id"), _rawSql.getString("title"), _rawSql.getString("author"),
					_rawSql.getInt("qty"));
		}

		return _book;
	}
	
	/***
	 * Modified a book based on parameter
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public int UpdateBook(Book book) throws SQLException{
		String query = String.format("Update books set Title = '%s' , Author = '%s', Qty = '%s' where id = %s", book.getTitle(), book.getAuthor(), book.getQty(), book.getId());		
		int _res = data.Cud(query);					
		return _res;				
	}
	
	/***
	 * Remove book based on id passed in. Note the object will allow for ORM implementations
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public int DeleteBook(Book book) throws SQLException{
		String query = String.format("delete from books where id = %s", book.getId());		
		int _res = data.Cud(query);					
		return _res;				
	}
}
