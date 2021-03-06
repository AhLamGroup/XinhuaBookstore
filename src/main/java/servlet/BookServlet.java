package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.Book;
import service.BookService;
import service.CategoryService;

public class BookServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String type = request.getParameter("type");
		BookService bookService = new BookService();
		System.out.println(type);
		/* 前台 */
		if ("getAllBooks".equals(type)) {
			System.out.println("getAllBooks");
			List<Book> books = bookService.getAllBooks();
			Gson gson = new Gson();
			String booksJson=gson.toJson(books);//转换为json字符串再送回前台
			request.setAttribute("books", booksJson);
			getServletContext().getRequestDispatcher("/book/showAllBooks.jsp").forward(request, response);
		} else if ("getBooksByName".equals(type)) {
			System.out.println("getBooksByName");
			String name = request.getParameter("name");
			List<Book> books = bookService.getBooksByName(name);
			Gson gson = new Gson();
			String booksJson=gson.toJson(books);//转换为json字符串再送回前台
			request.setAttribute("books", booksJson);
			getServletContext().getRequestDispatcher("/book/showAllBooks.jsp").forward(request, response);
		} else if ("getBookById".equals(type)) {
			System.out.println("getBookById");
			String id = request.getParameter("id");
			List<Book> books = bookService.getBookById(Integer.parseInt(id));
			request.setAttribute("books", books);
			getServletContext().getRequestDispatcher("xxx.jsp").forward(request, response);
		}
		/* 后台 */
		else if ("getAllBooksForManager".equals(type)) {
			System.out.println("getAllBooksForManager");
			List<Book> books = bookService.getAllBooks();
			request.setAttribute("books", books);
			getServletContext().getRequestDispatcher("/admin/book/showAllBooks.jsp").forward(request, response);
		} else if ("getBooksByNameForManager".equals(type)) {
			System.out.println("getBooksByNameForManager");
			String name = request.getParameter("name");
			List<Book> books = bookService.getBooksByName(name);
			request.setAttribute("books", books);
			getServletContext().getRequestDispatcher("xxx.jsp").forward(request, response);
		} else if ("getBookByIdForManager".equals(type)) {
			System.out.println("getAllBooks");
			String id = request.getParameter("id");
			List<Book> books = bookService.getBookById(Integer.parseInt(id));
			//request.setAttribute("books", books);
			request.setAttribute("book", books.get(0));
			CategoryService categoryService = new CategoryService();
			List<String> categories = categoryService.getAllCategory();
			request.setAttribute("categories", categories);
			getServletContext().getRequestDispatcher("/admin/book/editBook.jsp").forward(request, response);
		} else if ("updateBook".equals(type)) {
			System.out.println("updateBook");
			bookService.updateBook(request);
			response.sendRedirect("/XinhuaBookstore/servlet/BookServlet?type=getAllBooksForManager");
		} else if ("deleteById".equals(type)) {
			System.out.println("deleteById");
			String id = request.getParameter("id");
			bookService.deleteBookById(Integer.parseInt(id));
			response.sendRedirect("/XinhuaBookstore/servlet/BookServlet?type=getAllBooksForManager");
		} else if ("addBook".equals(type)) {
			System.out.println("addBook");
			bookService.addBook(request);
			response.sendRedirect("/XinhuaBookstore/servlet/BookServlet?type=getAllBooksForManager");
		} else if ("toAddBook".equals(type)) {
			System.out.println("toAddBook");
			CategoryService categoryService = new CategoryService();
			List<String> categories = categoryService.getAllCategory();
			request.setAttribute("categories", categories);
			getServletContext().getRequestDispatcher("/admin/book/addABook.jsp").forward(request, response);
		}
	}

}
