package ro.fasttrackit.tema4exerc1;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

public record Student(String name, LocalDate birthdate, int grade)
{
	// constructorul canonic poate fi personalizat la nevoie, (de ex. pentru protectie fata de argumente ilegale)
	public Student(String name, LocalDate birthdate, int grade)
	{
		if(name != null)
			this.name = name;
		else
			this.name = "n/a";
		
		if(birthdate != null)
			this.birthdate = birthdate;
		else
			this.birthdate = LocalDate.of(1900, Month.JANUARY, 1);
		
		if(grade > 0)
			this.grade = grade;
		else
			this.grade = 1;
	}
	
	public Student(String name, int age)
	{
		// acest constructor functioneaza cu ajutorul metodei interne utilitare dateByAge(int)
		this(name, dateByAge(age), 0);
	}
	
	// metoda ce calculeaza varsta studentului (in ani)
	public int getAgeInYears()
	{
		int age = 0;
		Period currentAge = Period.between(this.birthdate, LocalDate.now());
		age = currentAge.getYears();
		
		return age;
	}
	
	// metoda utilitara ce genereaza o data de nastere bazata pe varsta si data curenta,
	// unde luna si ziua sunt generate aleator; metoda este statica pt. a putea fi apelata ca argument in constructor
	protected static LocalDate dateByAge(int age)
	{
		LocalDate birthdate = null;
		
		if(age >= 0)
		{
			// luna zi ziua nasterii se genereaza in mod aleator
			Random rand = new Random();
			int day = rand.nextInt(1, 28); // se genereaza o zi intre 1 si 28 (zi maxima garantata in orice luna)
			int month = rand.nextInt(1, 12); // se genereaza o luna intre 1 si 12
			
			// se genereaza data nasterii bazata pe luna si ziua curenta
			birthdate = LocalDate.now().minusYears(age);
			
			// se genereaza data nasterii bazata pe luna si ziua generate aleator
			birthdate = birthdate.withMonth(month);
			birthdate = birthdate.withDayOfMonth(day);
		}
		else
			// ca sa nu se returneza null, se va returna o data predefinita
			birthdate = LocalDate.of(1900, Month.JANUARY, 1);
		
		return birthdate;
	}
}
