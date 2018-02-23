import java.util.ArrayList;
import java.util.Random;

public class ExampleCourses
{
    // Tworzy i zwraca listę kilku przykładowych kursów.
    private static int id = 0;
    private static Random rng = new Random();
    private static String[] nameCourse = {"Fizyka 1","Programowanie","Statystyka","Analiza 1","Analiza 2",
            "Miernictwo 1","Miernictwo 2","Architektura Komputerów","Logika Układów Cyfrowych","Algebra"};

    public static ArrayList<Course> exampleListOfCourses()
    {
        ArrayList<Course> listOfExampleCourses = new ArrayList<>();
        for(int i = 0; i<nameCourse.length;i++)
        {
            listOfExampleCourses.add(new Course(Integer.toString(id),nameCourse[i],(rng.nextInt(8))));
            id++;
        }
        return listOfExampleCourses;
    }
}
