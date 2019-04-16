package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

public class LibraryImpl implements Library {

	BookRepository dao = new BookRepository();

	Map<Book, Member> borrowers = new HashMap<>();

	public LibraryImpl() {
		super();
	}

	// ***** getters and setters***************************
	public BookRepository getDao() {
		return dao;
	}

	public void setDao(BookRepository dao) {
		this.dao = dao;
	}

	public Map<Book, Member> getBorrowers() {
		return borrowers;
	}

	public void setBorrowers(Map<Book, Member> borrowers) {
		this.borrowers = borrowers;
	}

	// ******************* methods *********************************

	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
		isMemberLate(member);
		Book bookToBorrow = dao.findBook(isbnCode);
		if(member.isLate()) {
			throw new HasLateBooksException();
		}else if (bookToBorrow != null && !member.isLate()) {
			dao.saveBookBorrow(bookToBorrow, borrowedAt);
			borrowers.put(dao.removeBook(isbnCode), member);
		}

		return bookToBorrow;

	}

	@Override
	public void returnBook(Book book, Member member) {
		List<Book> list = new ArrayList<>();
		list.add(book);
		dao.addBooks(list);
		dao.getBorrowedBooks().remove(book);

	}

	@Override
	public void isMemberLate(Member member) {
		LocalDate today = LocalDate.now();
		int amountOfDaysAllowed = 0;

		if (member instanceof Student)
			amountOfDaysAllowed = 30;
		if (member instanceof Resident)
			amountOfDaysAllowed = 60;

		Iterator<Map.Entry<Book, Member>> iterator = borrowers.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Book, Member> entry = iterator.next();
			if (entry.getValue().equals(member)) {

				if (dao.findBorrowedBookDate(entry.getKey()).plusDays(amountOfDaysAllowed).isBefore(today)) {
					entry.getValue().setLate(true);
				}
			}
		}

	}

}
