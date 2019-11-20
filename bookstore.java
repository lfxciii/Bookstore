
/*** 	Data Science, Algorithms and Advanced Software Engineering Task 7
 	Ruben Louw, [2019-06-16]
 	a book store application manager. create, edit, delete and search a book using a variety of search parameters
***/

package BookStoreApp;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

import BookStoreApp.BookRepository;

public class bookstore {

	public static void SearchBook(BookRepository _bookRepo) throws SQLException {

		boolean quit = false;

		while (!quit) {

			// wizzard
			MessagePrompt("***************************************************************");
			MessagePrompt("************ Follow the wizzard to search for a book **********");
			MessagePrompt("***************************************************************");
			MessagePrompt("To search, supply a value or leave blank to skip to next field:");

			Book _bookForSearch = null;

			MessagePrompt("Please enter the book id: ");
			int id = new Scanner(System.in).nextInt();

			// if invalid value, move to next field to search against
			if (id != 0) {
				_bookForSearch = _bookRepo.GetById(id);

				if (_bookForSearch == null) {
					MessagePrompt("Unable to find your book");
				} else {
					MessagePrompt("Book found! See below for details:");
					MessagePrompt(_bookForSearch.toString());
				}

				quit = DialogMessage("Would you like to quit?");
				continue;
			}

			MessagePrompt("Please enter the book title: ");
			String title = new Scanner(System.in).nextLine();

			// if invalid value, move to next field to search against
			if (!title.isEmpty()) {
				_bookForSearch = _bookRepo.GetByTitle(title);

				if (_bookForSearch == null) {
					MessagePrompt("Unable to find your book");
				} else {
					MessagePrompt("Book found! See below for details:");
					MessagePrompt(_bookForSearch.toString());
				}

				quit = DialogMessage("Would you like to quit?");
				continue;
			}

			MessagePrompt("Please enter the book author:");
			String author = new Scanner(System.in).nextLine();

			// if invalid value, move to next field to search against
			if (!author.isEmpty()) {
				_bookForSearch = _bookRepo.GetByAuthor(author);

				if (_bookForSearch == null) {
					MessagePrompt("Unable to find your book");
				} else {
					MessagePrompt("Book found! See below for details:");
					MessagePrompt(_bookForSearch.toString());
				}
				quit = DialogMessage("Would you like to quit?");
				continue;
			}

			// try quit if no fields searched on
			if (id == 0 && title.isEmpty() && author.isEmpty()) {
				quit = DialogMessage("Would you like to quit?");
				continue;
			}
		}

		MessagePrompt("Exit Enter book.");
	}

	/***
	 * Display book list
	 * 
	 * @param _bookRepo
	 * @throws SQLException
	 */
	public static void BookList(BookRepository _bookRepo) throws SQLException {

		// wizzard
		MessagePrompt("***************************************************************");
		MessagePrompt("*********** Below is a full book list of the database *********");
		MessagePrompt("***************************************************************");

		List<Book> _bookList = _bookRepo.GetAll();
		
		if(_bookList.size() == 0){
			MessagePrompt("There are no books in the system, please add sum!");
			return;
		}
		
		for (Book book : _bookList) {
			MessagePrompt(book.toString() + "\n");
		}
	}

	/***
	 * Update book UI logic
	 * 
	 * @param _bookRepo
	 * @throws SQLException
	 */
	public static void UpdateBook(BookRepository _bookRepo) throws SQLException {

		boolean quit = false;

		while (!quit) {

			// get list of books to display
			MessagePrompt("***************************************************************");
			MessagePrompt("*********** Please select a book from the list below **********");
			MessagePrompt("***************************************************************");

			List<Book> _bookList = _bookRepo.GetAll();
			for (Book book : _bookList) {
				MessagePrompt(book.toString() + "\n");
			}

			// wizzard
			MessagePrompt("***************************************************************");
			MessagePrompt("************** Follow the wizzard to update a book ************");
			MessagePrompt("***************************************************************");
			MessagePrompt("Please enter the book id you want to modify:");
			int id = new Scanner(System.in).nextInt();

			// try quit if field is empty
			if (id != 0) {
				// get book from db, ensuring its always up to date
				// (concurrency)
				Book _bookForUpdate = _bookRepo.GetById(id);

				if (_bookForUpdate == null) {
					quit = DialogMessage("Your book could not be found, Would you like to quit?");
				} else {
					MessagePrompt("Please enter the book title: ");
					String title = new Scanner(System.in).nextLine();

					// if different, update db model
					if (title != _bookForUpdate.getTitle()) {
						_bookForUpdate.setTitle(title);
					}

					MessagePrompt("Please enter the book author:");
					String author = new Scanner(System.in).nextLine();

					// if different, update db model
					if (author != _bookForUpdate.getAuthor()) {
						_bookForUpdate.setAuthor(author);
					}
					MessagePrompt("Please enter book quantity:");
					int qty = new Scanner(System.in).nextInt();

					// if different, update db model
					if (qty != _bookForUpdate.getQty()) {
						_bookForUpdate.setQty(qty);
					}

					// save book to db
					MessagePrompt("Updating book");
					if (_bookRepo.UpdateBook(_bookForUpdate) == 0) {
						MessagePrompt("Unable to update book");
					} else {
						MessagePrompt("Book successfuly updated!");
					}

					quit = DialogMessage("Would you like to quit?");
				}
			} else {
				quit = DialogMessage("You did not supply a value for the field Id, would you like to quit?");
			}
		}

		MessagePrompt("Exit Enter book.");
	}

	/***
	 * Delete book UI logic
	 * 
	 * @param _bookRepo
	 * @throws SQLException
	 */
	public static void DeleteBook(BookRepository _bookRepo) throws SQLException {

		boolean quit = false;

		while (!quit) {

			// get list of books to display
			MessagePrompt("***************************************************************");
			MessagePrompt("*********** Please select a book from the list below **********");
			MessagePrompt("***************************************************************");

			// display books
			List<Book> _bookList = _bookRepo.GetAll();
			for (Book book : _bookList) {
				MessagePrompt(book.toString() + "\n");
			}

			// start wizzard
			MessagePrompt("***************************************************************");
			MessagePrompt("************** Follow the wizzard to delete a book ************");
			MessagePrompt("***************************************************************");
			MessagePrompt("Please enter the book id you want to delete:");
			int id = new Scanner(System.in).nextInt();

			if (id != 0) {
				// get book from db, ensuring its always up to date
				// (concurrency)
				Book _bookForDelete = _bookRepo.GetById(id);

				if (_bookForDelete == null) {
					quit = DialogMessage("Your book could not be found, Would you like to quit?");
				} else {

					// remove book from db
					if (_bookRepo.DeleteBook(_bookForDelete) > 0) {
						MessagePrompt("Book deleted");
					} else {
						MessagePrompt("UNable to delete book");
					}

					quit = DialogMessage("Would you like to quit?");
				}
			} else {
				quit = DialogMessage("You did not supply a value for the field Id, would you like to quit?");
			}
		}

		MessagePrompt("Exit Enter book.");
	}

	/***
	 * Create book UI logic
	 * 
	 * @param _bookRepo
	 * @throws SQLException
	 */
	public static void CreateBook(BookRepository _bookRepo) throws SQLException {

		boolean quit = false;

		Book _newBook = new Book();

		while (!quit) {
			MessagePrompt("***************************************************************");
			MessagePrompt("************** Follow the wizzard to add a new book ***********");
			MessagePrompt("***************************************************************");
			MessagePrompt("Please enter the book id:");
			int id = new Scanner(System.in).nextInt();

			// try quit if field is empty
			if (id != 0) {
				_newBook.setId(id);
				MessagePrompt("Please enter the book title: ");
				String title = new Scanner(System.in).nextLine();

				// try quit if field is empty
				if (!title.isEmpty()) {
					_newBook.setTitle(title);
					MessagePrompt("Please enter the book author:");
					String author = new Scanner(System.in).nextLine();

					// try quit if field is empty
					if (!author.isEmpty()) {
						_newBook.setAuthor(author);
						MessagePrompt("Please enter book quantity:");
						int qty = new Scanner(System.in).nextInt();

						_newBook.setQty(qty);

						// save book to db
						MessagePrompt("Adding book");
						if (_bookRepo.AddBook(_newBook) == 0) {

							MessagePrompt("Unable to add book");
						}

						MessagePrompt("Book successfuly added!");
						// try quit
						quit = DialogMessage("Would you like to quit?");
					} else {
						quit = DialogMessage(
								"You did not supply a value for the field Author, would you like to quit?");
					}
				} else {
					quit = DialogMessage("You did not supply a value for the field Title, would you like to quit?");
				}
			} else {
				quit = DialogMessage("You did not supply a value for the field Id, would you like to quit?");
			}
		}

		MessagePrompt("Exit Enter book.");
	}

	/***
	 * After main called, this loads.
	 * 
	 * @param _bookRepo
	 * @throws SQLException
	 */
	public static void MainMenu(BookRepository _bookRepo) throws SQLException {

		boolean quit = false;

		while (!quit) {

			// display menu items
			MessagePrompt("***************************************************************");
			MessagePrompt("****************** Welcome to Bookstore Version 1 *************");
			MessagePrompt("***************************************************************");
			MessagePrompt("Please select an option from the below: ");
			MessagePrompt("\n" + "(1) Enter book\n" + "(2) Update book\n" + "(3) Delete book\n" + "(4) Search books\n"
					+ "(5) Book list\n" + "(0) Exit\n");

			// get option
			int option = new Scanner(System.in).nextInt();

			if (option == 1) {
				CreateBook(_bookRepo);
			} else if (option == 2) {
				UpdateBook(_bookRepo);
			} else if (option == 3) {
				DeleteBook(_bookRepo);
			} else if (option == 4) {
				SearchBook(_bookRepo);
			} else if (option == 5) {
				BookList(_bookRepo);
			} else if (option == 0) {
				quit = true;
			}
		}

		MessagePrompt("Quitting application");
	}

	public static boolean DialogMessage(String dialogMessage) {
		boolean quit = false;

		MessagePrompt(dialogMessage + "\n" + "(1) Yes\n" + "(2) No\n");

		// get option
		int option = new Scanner(System.in).nextInt();

		if (option == 1) {
			quit = true;
		}

		return quit;
	}

	public static void MessagePrompt(String message) {
		System.out.println(message);
	}

	public static void main(String[] args) {

		MessagePrompt("Setting up database...");
		try {
			BookRepository _bookRepo = new BookRepository();

			// uncomment to populate data.
			// _bookRepo.SetupDatabase();
			MainMenu(_bookRepo);

			MessagePrompt("Application ended");

		} catch (Exception e) {
			e.printStackTrace();
			main(args);
		}
	}
}
