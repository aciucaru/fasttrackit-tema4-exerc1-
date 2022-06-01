package ro.fasttrackit.tema4exerc1;

import java.time.LocalDate;
import java.time.Month;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

// import static pt. a reduce lungimea numelor metodelor statice publice
import static java.util.stream.Collectors.teeing;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.averagingInt;

public class StudentRepository 
{
	private List<Student> students = new ArrayList<Student>();
	
	public StudentRepository()
	{
		// in constructor se vor genera si adauga obiecte de tip 'Student'
		students.add( new Student("George Filip", LocalDate.of(1990, Month.JANUARY, 2), 4) );
		students.add( new Student("Andrei Balea", LocalDate.of(1986, Month.DECEMBER, 5), 3) );
		students.add( new Student("Vasile Baciut", LocalDate.of(1992, Month.MARCH, 1), 1) );
		students.add( new Student("Valentina Caldararu", LocalDate.of(1991, Month.JULY, 10), 1) );
		students.add( new Student("Marius Ciobanu", LocalDate.of(1986, Month.MARCH, 14), 2) );
		
		students.add( new Student("Andreea Danca", LocalDate.of(1991, Month.JUNE, 8), 4) );
		students.add( new Student("Valentina Dica", LocalDate.of(1998, Month.MARCH, 17), 3) );
		students.add( new Student("Alex Deleanu", LocalDate.of(1970, Month.MARCH, 12), 2) );
		students.add( new Student("Radu Elciu", LocalDate.of(1973, Month.NOVEMBER, 20), 1) );
		students.add( new Student("Ana Deleanu", LocalDate.of(1993, Month.FEBRUARY, 12), 4) );
	}
	
	public List<Student> getStudents() { return students; }
	
	// deoarece clasa Repository are acces la toti studentii, se va pune metoda ce calculeaza media anului
	// in aceasta clasa (pentru a nu mari numarul de clase nejustificat)
	public String generateGradeReport()
	{
		String gradeReport = "";
		
		// se obtine string-ul report-ului printr-un colector 'teeing'
		// un colector teeing permite rularea a doi colectori in acelasi timp si combinarea rezultatului lor
		// un colector teeing are 3 argumente: cei 2 colectori si o functie ce combina rezultatele celor doi colectori
		gradeReport = students.stream()
							.collect( teeing(
											// colectorul downstream1, acesta converteste elementele 'Student' in String ce contine numele
						                      mapping((Student s) -> s.name(), toList()),
						                    // colectorul downstream2, acest caluleaza media aritmetica a varstelor elementelor 'Student'
						                      averagingInt(Student::grade),
						                    // functia ce combina cele doua stream-uri rezultate 'downstrm1' si 'downstrm2'
						                      (downstrm1, downstrm2) ->
						                      { return "" + downstrm1 + "\n" + "have an average grade of " + downstrm2;}
									));
		
		return gradeReport;
	}
	
	// metoda utilitara pentru uz intern ce returneaza un sir de cursuri in format JSON
	protected static String getJSONCourses()
	{
		// string ce contine obiecte in formatul JSON
		// obiectele sunt de fapt elemente ale unui sir JSON
		String coursesJSON =
				"""
					[
						{
						    "course":"Math",
							"semester":2
						},
						{
						    "course":"Chemistry",
							"semester":1
						},
						{
						    "course":"Programming",
							"semester":2
						},
						{
						    "course":"Physics",
							"semester":1
						},
						{
						    "course":"Database",
							"semester":2
						}
					]
				""";
		
		return coursesJSON;
	}
	
	// metoda utilitara ce transforma orice text JSON ca cel de mai sus, intr-un List de String-uri coresp. fiecarui curs
	// aceasta metoda functioneaza doar pt. JSON de tip curs, identic cu cel de mai sus, nu functioneaza pentru reprezentare
	// in format JSON a altor obiecte
	protected static List<String> parseJSONCourses(String coursesJSON)
	{
		// pentru simplitate, aceasta metoda face niste presupuneri despre formatarea string-ului JSON
		// nu va functiona daca string-ul JSON este scris putin diferit, chiar daca el ar reprezenta acelasi sir de elemente
		
		// o lista ce va contine la final cate un String pt. fiecare curs din textul JSON de mai sus
		List<String> parsedCourses = new ArrayList<String>(0);
		
		if(coursesJSON != null)
		{
			// se elimina parantelezele drepte ce reprezinta un sir, prin inlocuirea lor cu nimic
			coursesJSON = coursesJSON.replace("[", "").replace("]", "");
			// se elimina acoladele, prin inlocuirea lor cu nimic
			coursesJSON = coursesJSON.replace("{", "").replace("}", "");
			// se elimina toate spatiile albe, prin inlocuirea lor cu nimic
			coursesJSON = coursesJSON.replaceAll("\\s+","");
			// se elimina caracterele "
			coursesJSON = coursesJSON.replaceAll("\"", "");
			
			// acum se poate sparge string-ul, a.i. sa se obtina un sir de string-uri, cate 2 string-uri pt. fiecare curs
			// string-ul se sparge cu split​(String regex, int limit)
			/* string-ul repetat dupa care se face spargea este "," - virgula*/
			// asta inseamna ca nu se vor obtine cursuri intregi, ci cele doua componente ale unui curs: "course" si "semester"
			String[] splittedCourses = coursesJSON.split(",", Integer.MAX_VALUE);
			
			// se reasambleaza cursurile, folosind cate 2 string-uri consecutive pt. fiecare curs ("course" si "semester")
			int numOfCourses = splittedCourses.length / 2;
			parsedCourses = new ArrayList<String>(numOfCourses);
			String currentCourse = null;
			for(int i=0; i<numOfCourses-1; i++)
			{
				// se genereaza String-ul aferent cursului curent
				currentCourse = splittedCourses[2*i] + " - " + splittedCourses[2*i + 1];
				
				// se adauga String-ul aferent cursului curent corect generat
				parsedCourses.add(currentCourse);
			}
		}
		
		return parsedCourses;
	}
	
	// metoda ce aloca in mod aleator un curs fiecarui student si printeaza perechile student-curs
	public void allocateAndPrintCourses()
	{
		List<String> parsedCourses = parseJSONCourses(getJSONCourses());
		
		Random random = new Random();
		int randomLimit = parsedCourses.size() - 1;
		int studentsSize = students.size();
		int currentRandomInt = 0;
		for(int i=0; i<studentsSize; i++)
		{
			// se genereza un index al cursului ce va fi atribuit studentului curent
			currentRandomInt = random.nextInt(0, randomLimit);
			
			// se printeaza mesajul corespunzator studentului curent
			System.out.println(students.get(i).name() + " will participate to: " + parsedCourses.get(currentRandomInt));
		}
	}
	
	// metoda cu switch expression
	public void generateAndPrintGrade()
	{
		// se foloseste un iterator pt. a accesa indexul fiecarui student (ca indexul sa fie citit nu generat)
		// indexul putea fi cunoscut si de dinainte cu ajutorul unei bucle for clasice
		ListIterator<Student> studentIterator = students.listIterator();
		String currentGrade = "";
		// atata timp cat mai sunt studenti
		while(studentIterator.hasNext())
		{
			// se determina ce grade corespunde studentului curent
			currentGrade = 
					switch(studentIterator.nextIndex()) // se face switch in functie de indexul studentului in sir
					{
						// pentru primii 3 studenti din sir corespunde "1st grade"
						case 0, 1, 2 -> "1st grade";
						// pentru al 4-lea student din sir corespunde "5th grade"
						case 3 -> "5th grade";
						// pentru restul studentilor din sir corespunde "7th grade"
			            default -> "7th grade";
					};
			
			// se afiseaza numele studentului plus grade-ul conform switch-ului cu expresii
			System.out.println(studentIterator.next() + " " + currentGrade);
		}
	}
}
