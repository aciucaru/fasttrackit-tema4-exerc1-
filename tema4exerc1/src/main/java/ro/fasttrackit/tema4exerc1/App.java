package ro.fasttrackit.tema4exerc1;

/*
A Student  has the following fields: name, birthDate(LocalDate), grade.
Provide a method that returns the age (calculated from birth date) HINT: use Period.between.
HINT: Use records!

The student has an additional constructor that receives the age and randomly sets the day and month.

create a service object that has a list of Students. 

1) [teeing collectors]: create a method that through a single iteration of students will return the following result: 

"Name1, Name2, Name3 have an average grade of X"
 
2) [text blocks]: have a list of minimum 3 courses represented by json : eg.
{
    "course":"Math 101",
    "semester":2
}

3) [switch expression] : create a method that will return 
  - for the first 3 students : "1st grade" 
  - for the 4th student: "5th grade"
  - for rest of them : "7th grade"
*/
	

public class App 
{
    public static void main( String[] args )
    {
        StudentRepository repo = new StudentRepository();
        
        System.out.println("1) Teeing collector generated report:");
        System.out.println(repo.generateGradeReport());
        System.out.println("");
        
        System.out.println("2) JSON randomly allocated course:");
        repo.allocateAndPrintCourses();
        System.out.println("");
        
        System.out.println("3) Switch expression returned grades:");
        repo.generateAndPrintGrade();
    }
}
