package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
	private static Map<ISBN, Book> availableBooks = new HashMap<>();
	private static Map<Book, LocalDate> borrowedBooks = new HashMap<>();
/*******************/
	

	private List<Book> listBookForTest;
	private Book book1 ;
	private Book book2 ;
	private Book book3 ;
	private Book book4 ;

	
	
	
	public BookRepository() {
		super();
		
		listBookForTest = new ArrayList<Book>();

		// TODO add some test books (use BookRepository#addBooks)
		book1 = new Book("Harry Potter", "J.K. Rowling", new ISBN(46578964513l));
		book2 = new Book("Around the world in 80 days", "Jules Verne", new ISBN(3326456467846l));
		book3 = new Book("Catch 22", "Joseph Heller", new ISBN(968787565445l));
		book4 = new Book("La peau de chagrin", "Balzac", new ISBN(465789453149l));

		// TODO to help you a file called books.json is available in src/test/resources
		listBookForTest.add(book1);
		listBookForTest.add(book2);
		listBookForTest.add(book3);
		listBookForTest.add(book4);

		this.addBooks(listBookForTest);

		
	}
	
	
	

	public Map<ISBN, Book> getAvailableBooks() {
		return availableBooks;
	}




	public void setAvailableBooks(Map<ISBN, Book> availableBooks) {
		BookRepository.availableBooks = availableBooks;
	}




	public Map<Book, LocalDate> getBorrowedBooks() {
		return borrowedBooks;
	}




	public void setBorrowedBooks(Map<Book, LocalDate> borrowedBooks) {
		BookRepository.borrowedBooks = borrowedBooks;
	}




	public void addBooks(List<Book> books) {
		for (Book b : books)
			availableBooks.put(b.isbn, b);

	}

	public Book findBook(long isbnCode) {
		Iterator<Map.Entry<ISBN, Book>> iterator = availableBooks.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<ISBN, Book> entry = iterator.next();
			if (entry.getKey().isbnCode == isbnCode )return entry.getValue();
		}
		return null;

	}
	public Book removeBook(long isbnCode) {
		
		Iterator<Map.Entry<ISBN, Book>> iterator = availableBooks.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<ISBN, Book> entry = iterator.next();
			if (entry.getKey().isbnCode == isbnCode ) {
				availableBooks.remove(entry.getKey());
				return entry.getValue();
				
			}
		}
		return null;
		
	}

	public void saveBookBorrow(Book book, LocalDate borrowedAt) {
		borrowedBooks.put(book, borrowedAt);
	}

	public LocalDate findBorrowedBookDate(Book book) {
		if(book!= null) {
			long isbn = book.isbn.isbnCode;
			Iterator<Map.Entry<Book, LocalDate>> iterator = borrowedBooks.entrySet().iterator();
			
			while (iterator.hasNext()) {
				Map.Entry<Book, LocalDate> entry = iterator.next();
				if (entry.getKey().isbn.isbnCode ==isbn)
					return entry.getValue();
			}
		}
		return null;
	}
	
	
}
