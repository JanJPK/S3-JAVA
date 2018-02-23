import java.util.ArrayList;
public class ListOperations
{
    // ************************************ DODAWANIE POŁĄCZENIA
    public static void addConnection(ArrayList<Student> listOfStudents, ArrayList<Course> listOfCourses,
                              int studentID, int courseID)
    {
        if(!listOfStudents.isEmpty() && !listOfCourses.isEmpty())
        {
            if(studentID < listOfStudents.size() && courseID < listOfCourses.size())
            {
                // Sprawdza czy student jest już zapisany na ten kurs.
                if(!(listOfStudents.get(studentID)).isAlreadyAttending(listOfCourses.get(courseID)))
                {
                    (listOfStudents.get(studentID)).addCourse(listOfCourses.get(courseID));
                    (listOfCourses.get(courseID)).addStudent(listOfStudents.get(studentID));
                    System.out.println("Dodano.");

                }
                else
                {
                    // Błąd student chodzi już na taki kurs.
                    System.out.println("Student nie może chodzić na dwa takie same kursy!");
                }

            }
            else
            {
                // Błąd ID większe od wielkośći tablicy.
                System.out.println("Podany kurs/student nie istnieje!");
            }
        }
        else
        {
            // Bład tablica jest pusta.
            System.out.println("Nie ma żadnych kursów/studentów!");
        }

    }

    // ************************************ USUWANIE POŁĄCZENIA
    public static void removeConnection(ArrayList<Student> listOfStudents, ArrayList<Course> listOfCourses,
                                 int studentID, int courseID)
    {
        if(!listOfStudents.isEmpty() && !listOfCourses.isEmpty())
        {
            if(studentID < listOfStudents.size() && courseID < listOfCourses.size())
            {
                if(listOfStudents.get(studentID).isAlreadyAttending(listOfCourses.get(courseID)))
                {
                    (listOfStudents.get(studentID)).removeCourse(listOfCourses.get(courseID));
                    (listOfCourses.get(courseID)).removeStudent(listOfStudents.get(studentID));
                    System.out.println("Usunięto.");
                }
                else
                {
                    // Błąd student nie chodzi na taki kurs.
                    System.out.println("Student nie chodzi na taki kurs!");
                }
            }
            else
            {
                // Błąd ID większe od wielkości tablicy.
                System.out.println("Podany kurs/student nie istnieje!");
            }
        }
        else
        {
            // Błąd tablica jest pusta.
            System.out.println("Nie ma żadnych kursów/studentów!");
        }

    }

    // ************************************ WYPISYWANIE STUDENTÓW + ICH KURSÓW
    public static String theirCourses(ArrayList<Student> listOfStudents)
    {
        String coursesString = "";
        for(int i = 0; i<listOfStudents.size();i++)
        {
            coursesString += (listOfStudents.get(i)).theirCourses();
        }
        return coursesString;
    }

    // ************************************ WYPISYWANIE KURSÓW + ICH STUDENTÓW
    public static String theirStudents(ArrayList<Course> listOfCourses)
    {

        String studentsString = "";
        for(int i = 0; i<listOfCourses.size();i++)
        {
            studentsString += (listOfCourses.get(i)).theirStudents();
        }
        return studentsString;
    }

    // ************************************ WYPISYWANIE STUDENTÓW Z ICH INDEKSAMI W LIŚCIE
    public static String studentIndexes(ArrayList<Student> listOfStudents)
    {
        String studentString = "Studenci w systemie:\n";
        for(int i = 0; i < listOfStudents.size();i++)
        {
            studentString += "  ["+i+"] - " + listOfStudents.get(i).toString() + "\n";
        }
        return studentString;
    }

    // ************************************ WYPISYWANIE KURSÓW Z ICH INDEKSAMI W LIŚCIE
    public static String courseIndexes(ArrayList<Course> listOfCourses)
    {
        String courseString = "Dostępne kursy:\n";
        for (int i = 0; i < listOfCourses.size(); i++)
        {
            courseString += "  [" + i + "] - " + listOfCourses.get(i).toString() + "\n";
        }
        return courseString;
    }

    // ************************************ SPRAWDZA CZY JEST UNIKATOWY = TRUE JEŻELI NIE MA STUDENTA Z TAKIM INDEKSEM
    public static boolean checkForUniqueIndex(ArrayList<Student> listOfStudents, int checkingID)
    {
        if(listOfStudents.stream().anyMatch(student -> student.getID() == checkingID))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // ************************************ SPRAWDZA CZY JEST UNIKATOWY = TRUE JEŻELI NIE MA KURSU Z TAKIM KODEM
    public static boolean checkForUniqueCode(ArrayList<Course> listOfCourses, String checkingCode)
    {
        if(listOfCourses.stream().anyMatch(course -> course.getCode().equals(checkingCode)))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
