
import java.util.ArrayList;
import java.util.Collections;

public class Student implements Comparable<Student>
{
    private int id;
    private String name;
    private String surname;
    private ArrayList<Course> listOfTheirCourses;

    public Student()
    {
        listOfTheirCourses = new ArrayList<>();
    }

    public Student(int id, String name, String surname)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
    // ************************************ GETTERY I SETTERY
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setSurname(String nameSecond)
    {
        this.surname = nameSecond;
    }

    public String getSurname()
    {
        return this.surname;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public int getID()
    {
        return this.id;
    }

    public ArrayList<Course> getListOfTheirCourses()
    {
        return this.listOfTheirCourses;
    }

    // ************************************ TOSTRING
    @Override
    public String toString()
    {
        return this.name + " / " + this.surname + " / " + this.id;
    }

    // ************************************ COMPARETO
    @Override
    public int compareTo(Student student)
    {
        int comparedNames = name.compareTo(student.name);

        if(comparedNames == 0)
        {
            return name.compareTo(student.name);
        }
        else
        {
            return comparedNames;
        }

    }

    // ************************************ PRZYPISANIE KURSU DO STUDENTA
    public void addCourse(Course course)
    {
        this.listOfTheirCourses.add(course);
    }

    // ************************************ USUWANIE KURSU OD STUDENTA
    public void removeCourse(Course course)
    {
        this.listOfTheirCourses.remove(course);
    }

    // ************************************ SPRAWDZANIE

    // Czy student już uczęszcza na kurs?
    public boolean isAlreadyAttending(Course course)
    {
        return listOfTheirCourses.contains(course);
    }

    // ************************************ ZWRACA LISTĘ KURSÓW STUDENTA
    public String theirCourses()
    {
        String courseString = this.toString()+"\n";
        if(listOfTheirCourses.isEmpty())
        {
            courseString += "Student nie uczęszcza na żaden kurs.\n";
            return courseString;
        }
        else
        {
            Collections.sort(listOfTheirCourses);
            for(int i =0; i<listOfTheirCourses.size();i++)
            {
                courseString += "  - " + listOfTheirCourses.get(i).toString()+"\n";
            }
            return courseString;
        }

    }


}
