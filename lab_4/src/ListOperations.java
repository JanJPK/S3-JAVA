import javax.swing.*;
import java.awt.*;
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
                    JOptionPane.showMessageDialog(null,
                            "Dodano.");

                }
                else
                {
                    // Błąd student chodzi już na taki kurs.
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "Student nie może chodzić na dwa takie same kursy!",
                            "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
            else
            {
                // Błąd ID większe od wielkośći tablicy.
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,
                        "Nie ma takiego indeksu na liście!",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            // Bład tablica jest pusta.
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Nie ma żadnych kursów lub studentów!",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null,
                            "Usunięto.");
                }
                else
                {
                    // Błąd student nie chodzi na taki kurs.
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "Student nie chodzi na taki kurs!",
                            "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                // Błąd ID większe od wielkości tablicy.
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,
                        "Nie ma takiego indeksu na liście!",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            // Błąd tablica jest pusta.
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Nie ma żadnych kursów lub studentów!",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
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
