import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JPK on 2016-11-21.
 */
public class RandomStudent
{
    private static String[] nameMale = {"Tomasz","Wiktor","Adrian","Marek","Zygmunt","Przemyslaw"};
    private static String[] nameFemale = {"Zuzanna","Monika","Klaudia","Karyna","Ola","Joanna"};
    private static String[] surname = {"Buk","Sroka","Leslaw","Koza","Krzak","Kowal","Zaba","Stanczyk","Dudek"};
    private static int id = 226000;
    private static Random rng = new Random();

    public RandomStudent()
    {
        rng = new Random();
    }

    // Tworzy losowego studenta.
    public static Student randomStudent()
    {
        Student Student = new Student();

        if(rng.nextBoolean())
        {
            Student.setName(nameMale[rng.nextInt(nameMale.length)]);
        }
        else
        {
            Student.setName(nameFemale[rng.nextInt(nameFemale.length)]);
        }
        Student.setSurname(surname[rng.nextInt(surname.length)]);
        Student.setID(id);
        id++;

        return Student;
    }

    // Tworzy zadaną ilość losowych studentów i zwraca jako ArrayList.
    public static ArrayList<Student> randomListOfStudents(int randomStudentAmount)
    {
        ArrayList<Student> listOfRandomStudents = new ArrayList<Student>();

        for(int i = 0; i<randomStudentAmount;i++)
        {
            listOfRandomStudents.add(randomStudent());
        }

        return listOfRandomStudents;
    }
}
