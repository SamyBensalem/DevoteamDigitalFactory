package fr.d2factory.libraryapp.library;

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

public class LibraryTest {
	private Library library;
	private BookRepository bookRepository;

	@Before
	public void setup() {
		// TODO instantiate the library and the repository

		library = new LibraryImpl();
		bookRepository = new BookRepository();

		// ******************************************************************************

		// TODO add some test books (use BookRepository#addBooks)
		// TODO to help you a file called books.json is available in src/test/resources
		// --------> according to me this part have to be done in the DAO layer,
		// that's why i did it directly in BookRepository
		// *******************************************************************************

	}

	@Test
	public void test_if_a_book_is_available() {
		assertEquals(46578964513l, bookRepository.findBook(46578964513l).getIsbn().getIsbnCode());
	}

	@Test
	public void member_can_borrow_a_book_if_book_is_available() {
		Member studentTest = new Student("idTest", "Nicolas", "Etudiant");
		// we put a book that this student would have rent 30 days ago (we do it on
		// purpose to test that the limit day is still good)
		library.borrowBook(46578964513l, studentTest, LocalDate.now().minusDays(30));
		// we check that the book is not anymore available to be borrown
		assertEquals(null, bookRepository.findBook(46578964513l));
	}

	@Test
	public void equal_operator_between_2identical_students() {

		Member memberTest1 = new Student("idmemberTest1", "firstNamememberTest1", "lastNamememberTest1");
		Member memberTest2 = new Student("idmemberTest1", "firstNamememberTest1", "lastNamememberTest1");

		assertEquals(memberTest1, memberTest2);

	}

	@Test
	public void equal_operator_between_2identical_residents() {

		Member memberTest1 = new Resident("idmemberTest1", "firstNamememberTest1", "lastNamememberTest1");
		Member memberTest2 = new Resident("idmemberTest1", "firstNamememberTest1", "lastNamememberTest1");

		assertEquals(memberTest1, memberTest2);

	}

	@Test
	public void equal_operator_between_2identical_Isbn() {

		ISBN isbnTest1 = new ISBN(1234567l);
		ISBN isbnTest2 = new ISBN(1234567l);

		assertEquals(isbnTest1, isbnTest2);

	}

	@Test
	public void equal_operator_between_2identical_books() {

		Book bookTest1 = new Book("La peau de chagrin", "Balzac", new ISBN(465789453149l));
		Book bookTest2 = new Book("La peau de chagrin", "Balzac", new ISBN(465789453149l));

		assertEquals(bookTest1, bookTest2);

	}

	@Test
	public void borrowed_book_is_no_longer_available() {
		// according to the previous test the book with isbn 46578964513l is available
		Member memberTest = new Student();
		library.borrowBook(46578964513l, memberTest, LocalDate.now());
		assertEquals(false, bookRepository.getAvailableBooks().containsKey(46578964513l));
	}

	@Test
	public void residents_are_taxed_10cents_for_each_day_they_keep_a_book() {
		Member residentTest = new Resident();
		residentTest.setWallet(20);// he owns 20 dollars in wallet
		residentTest.payBook(1);// one day cost 0.10 (according that the student is in the range of the 30 first
								// days)
		assertEquals(19.9f, residentTest.getWallet(), 0f);

	}

	@Test
	public void students_pay_10_cents_the_first_30days() {
		Member studentTest = new Student();
		studentTest.setWallet(20);// he owns 20 dollars in wallet
		studentTest.payBook(1);// one day cost 0.10 (according that the student is in the range of the 30 first
								// days)
		assertEquals(19.9f, studentTest.getWallet(), 0f);

	}

	@Test
	public void students_in_1st_year_are_not_taxed_for_the_first_15days() {
		Member studentTest = new Student();
		studentTest.setWallet(20);// he owns 20 dollars in wallet
		studentTest.payBook(15);// 15 days cost 1.50 (according that the student is in the range of the 30 first
								// days)
		assertEquals(18.5f, studentTest.getWallet(), 0f);
	}

	@Test
	public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days() {
		Member studentTest = new Student();
		studentTest.setWallet(20);// he owns 20 dollars in wallet
		studentTest.payBook(31);// 30 days cost 3.0 + 0.15 for the first taxed day
		assertEquals(16.85f, studentTest.getWallet(), 0f);
	}

	@Test
	public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() {
		Member residentTest = new Resident();
		residentTest.setWallet(20);// he owns 20 dollars in wallet
		residentTest.payBook(61);// 60 days cost 6.0 + 0.20 for the first taxed day
		assertEquals(13.8f, residentTest.getWallet(), 0f);

	}

	//this test must send a HasLateBooksException
	@Test(expected=HasLateBooksException.class)
	public void student_members_cannot_borrow_book_if_they_have_late_books() {
		// Member residentTest = new Resident("idTest1","Thomas","Resident");
		Member studentTest = new Student("idTest", "Nicolas", "Etudiant");
		// we put a book that this student would have rent 31 days ago
		library.borrowBook(46578964513l, studentTest, LocalDate.now().minusDays(31));
		// this student tries to rent a book today
		library.borrowBook(968787565445l, studentTest, LocalDate.now());

	}

	//this test must send a HasLateBooksException
	@Test(expected=HasLateBooksException.class)
	public void resident_members_cannot_borrow_book_if_they_have_late_books() {
		Member residentTest = new Resident("idTest1", "Thomas", "Resident");
		// we put a book that this resident would have rent 61 days ago
		library.borrowBook(46578964513l, residentTest, LocalDate.now().minusDays(61));
		// this resident tries to rent a book today
		library.borrowBook(968787565445l, residentTest, LocalDate.now());
	
	}
}
