import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
public class GreatBooksProgramRevised {

	public static void main(String[] args) {
		
		ArrayList<LibraryBook> books = new ArrayList<LibraryBook>(50);
		useGreatBooks(books);
		
	}
	
	public static void useGreatBooks(ArrayList<LibraryBook> books) {
		clearScreen();
		playFirstScreen(books);
		playHomeScreen(books);
	}
	
	public static void playHomeScreen(ArrayList<LibraryBook> books) {
		
		printHomeScreen();
		int choice = receiveUserSelection(books);
		
		switch (choice) {
		
		case 1: clearScreen();
				displayAllBookRecords(books);
				break;
		
		case 2: searchForBook(books);
		
		case 3: System.out.println("\n\tGoodbye.  Have a nice day. :-)");
				System.exit(0);
				
		}
		
	}
	

	public static void playFirstScreen(ArrayList<LibraryBook> books) {
		
		printLongGreatBooks();
		printFirstPageLine();
		printAskWhatFile();
		printFilesList();
		inputFiles(books);
		selectionSortBooks(books);
		returnToContinue(books);
		
	}
	
	public static void printHomeScreen() {
		
		printJaggedLine();
		printGreatBooks();
		printJaggedLine();
		printOptionOne();
		printOptionTwo();
		printOptionThree();
		printJaggedLine();
		
	}
	
	public static void selectionSortBooks (ArrayList<LibraryBook> books) {
		
		int minIndex, index, j;
		
		for (index = 0; index < books.size(); index++) {
			minIndex = index;
			
			for (j = minIndex + 1; j < books.size(); j++) 
				
				if (books.get(j).compareTo(books.get(minIndex)) < 0)
					minIndex = j;
			
			if (books.get(index).compareTo(books.get(minIndex)) != 0) {
				LibraryBook temp = books.get(index);
				books.set(index, books.get(minIndex));
				books.set(minIndex, temp);
			}
		}
	}
	
	public static int binarySearchBooks(ArrayList<LibraryBook> books, String key) {
		int first = 0, last = books.size() - 1, middle, location;
		
		boolean found = false;
		
		do {
			middle = (first + last) / 2;
			
			if (key.equalsIgnoreCase(books.get(middle).getTitle().toString()))
				
				found = true;
			
			else if (key.compareTo(books.get(middle).getTitle().toString()) < 0)
				
				last = middle - 1;
		
			else
				
				first = middle + 1;
			
		} while ((! found) && (first <= last));
		
		location = middle;
		
		return (found ? location : -1);
				
	}
	
	public static void searchForBook(ArrayList<LibraryBook> books) {
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("\n\tSearch Title > ");
		
		String search = input.nextLine();
		
		int location = binarySearchBooks(books, search);
		
		if (location == -1) {
			
			System.out.println("Sorry, the books was not found.\n");
			returnToContinue(books);
			
		}
		
		if (location >= 0) {
		
			clearScreen();
			System.out.println("\tBook Found in Alphabetized List in :\n");
			printRecord(books, location);
			returnToContinue(books);
				
		}
	}
	
	public static void displayAllBookRecords(ArrayList<LibraryBook> books) {
		
		Scanner input = new Scanner(System.in);
		for (int location = 0; location < books.size(); location++) {
			
			printRecord(books, location);
			System.out.println("Please Hit Return to Continue or M for Menu...");
			
			String enterKey = input.nextLine();
			
			if (enterKey.equalsIgnoreCase("m")) {
				
				clearScreen();
				playHomeScreen(books);
				
			}
			
			else if (enterKey == "") {
				
				clearScreen();
				continue;
				
			}
			
			else if (location + 1 == books.size()) {
				
				clearScreen();
				playHomeScreen(books);
			
			}
			
			else {
				
				clearScreen();
				continue;
				
			}
			clearScreen();
		}
	}
	
	public static void printRecord (ArrayList<LibraryBook> books, int location) {
		
		System.out.println("\tRecord #" + (location + 1) + " :");
		printJaggedLine();
		
		DecimalFormat df = new DecimalFormat ("####0.00");
		System.out.println("\t            Title:         " + books.get(location).getTitle());
		System.out.println("\t    Author's Name:         " + books.get(location).getAuthor());
		System.out.println("\t        Copyright:         " + books.get(location).getCopyright());
		System.out.println("\t            Price:         " + df.format(books.get(location).getPrice()));
		System.out.println("\t            Genre:         " + books.get(location).getGenre() + "\n");
		
		printJaggedLine();
		System.out.println();
		
	}
	
	public static int receiveUserSelection(ArrayList<LibraryBook> books) {
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("\tPlease Enter Your Choice > ");
		
		int userChoice = 0;
		if (input.hasNextInt()) {
			userChoice = input.nextInt();
			boolean goodInput = checkIfUserChoiceApplicable(userChoice);
		
		while (goodInput == false) {
			
			System.out.println("\n\tYou have made an error. Please try again.");
			returnToContinue(books);
			printHomeScreen();
			userChoice = promptUserAction();
			goodInput = checkIfUserChoiceApplicable(userChoice);
			
		}
		}
		else {
			System.out.println("You have made an error. Please try again");
			returnToContinue(books);
			playHomeScreen(books);
			
		}
		return userChoice;
	}
	
	public static int promptUserAction() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please Enter Your Choice > ");
		
		int userChoice = input.nextInt();
	
		return userChoice;
	}
	
	public static boolean checkIfUserChoiceApplicable(int choice) {
		
		boolean applicable = false;
		
		if (choice > 0 && choice < 4) {
			applicable = true;
		}
		
		return applicable;
	}
	
	public static int inputBooks (String inputFile, ArrayList<LibraryBook> books) {
		
		int numBooks = 0;
		try {
			Scanner in = new Scanner(new File(inputFile));
			while (in.hasNext()) {
				Scanner lsc = new Scanner (in.nextLine()).useDelimiter(";");
				
				String title = lsc.next();
				String name = lsc.next();
				int copyright = lsc.nextInt();
				double price = lsc.nextDouble();
				String genre = lsc.next();
				if (genre.equals("f")) {
					genre = "Fiction";
				}
				if (genre.equals("p")) {
					genre = "Poetry";
				}
				if (genre.equals("d")) {
					genre = "Drama";
				}
				
				books.add(new LibraryBook(title, name, copyright, price, genre));
				numBooks++;
			}
		}
		catch (IOException e)  {
			System.out.println(e.getMessage());
		}
		return numBooks;
	}
	
	public static boolean checkForInputFile(String key) {
		
		boolean fileExists = false;
		
		String[] pathnames;
		File f = new File(".");
		pathnames = f.list();
		
		for (String pathname : pathnames) {
			
			if (pathname.endsWith(".dat")) {
			
				if (key.equals(pathname)) {
					
					fileExists = true;
				}
			}
		}
		return fileExists;
	}
	
	public static void inputFiles(ArrayList<LibraryBook> books) {
	
		Scanner input = new Scanner(System.in);
		
		System.out.print("\n\t Filename : ");
		String userInputFile = input.next();
		
		boolean exists = checkForInputFile(userInputFile);
		while (exists == false) {
			
			System.out.println("\n\n\t** Can't open input file.  Try again. **\n");
			printFilesList();
			System.out.print("\n\t Filename : ");
			userInputFile = input.next();
			exists = checkForInputFile(userInputFile);
		}
		
		int numBooks = inputBooks(userInputFile, books);
		System.out.println("\n\tA total of " + numBooks + " books have been input & sorted by title\n");

		
	}
	
	public static void clearScreen() {
		
		System.out.println("\u001b[H\u001b[2J");
		
	}
	
	public static void printFilesList() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("\n\t Here are the files in your current directory : \n");
		
		String[] pathnames;
		File f = new File(".");
		pathnames = f.list();
	
		for (String pathname : pathnames) {
		
			if (pathname.endsWith(".dat")) {
				System.out.print(pathname + "\t");
			}
		}

	System.out.println();

	}
	
	public static void printLongGreatBooks() {
		
		System.out.println("\t\t\t THE GREAT BOOKS PROGRAM");
		
	}
	public static void printFirstPageLine() {
		
		System.out.println("-------------------------------------------------------------------------");
	}
	
	public static void printAskWhatFile() {
		
		System.out.println("\n\t What file is your book data stored in?");
		
	}
	
	public static void printJaggedLine() {
		
		System.out.println("\t^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		
	}
	
	public static void printGreatBooks() {
		
		System.out.println("\t\tTHE GREAT BOOKS SEARCH PROGRAM\t");
		
	}
	
	public static void printOptionOne() {
		
		System.out.println("\t1)  Display all book records");
		
	}
	
	public static void printOptionTwo() {
		
		System.out.println("\t2)  Search for a Book by Title");
		
	}
	
	public static void printOptionThree() {
		
		System.out.println("\t3)  Exit Search Program");
		
	}
	
	public static void returnToContinue (ArrayList<LibraryBook> books) {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("\tPlease Hit Return to Continue...");
		
		String enterKey = input.nextLine();
		
		if (enterKey == "") {
			clearScreen();
			playHomeScreen(books);
		}
		else {
			clearScreen();
			playHomeScreen(books);
		}
	}

}

