import java.util.ArrayList;
import java.util.Collections;

public class Course implements Comparable<Course>
{


    private String code;
    private int ects;
    private String courseName;
    private ArrayList<Student> listOfAttendingStudents;

    public Course()
    {

    }

    public Course(String code, String courseName, int ects)
    {
        listOfAttendingStudents = new ArrayList<>();
        this.code = code;
        this.courseName = courseName;
        this.ects = ects;

    }

    // ************************************ GETTERY I SETTERY
    public String getCourseName()
    {
        return this.courseName;
    }

    public void setCourseName(String CourseName)
    {
        this.courseName = CourseName;
    }

    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public int getECTS()
    {
        return this.ects;
    }

    public void setECTS(int ects)
    {
        this.ects = ects;
    }

    // ************************************ TOSTRING
    @Override
    public String toString()
    {
        return this.courseName + " / " + this.ects + " ECTS / KOD GRUPY " + this.code;
    }

    // ************************************ COMPARETO
    @Override
    public int compareTo(Course course)
    {
        int comparedNames = courseName.compareTo(course.courseName);

        if(comparedNames == 0)
        {
            return courseName.compareTo(course.courseName);
        }
        else
        {
            return comparedNames;
        }

    }

    // ************************************ PRZYPISANIE STUDENTA DO KURSU
    public void addStudent(Student student)
    {
        this.listOfAttendingStudents.add(student);
    }

    // ************************************ USUWANIE STUDENTA Z KURSU
    public void removeStudent(Student student)
    {
        this.listOfAttendingStudents.remove(student);
    }

    // ************************************ ZWRACA LISTĘ STUDENTÓW CHODZĄCYCH NA KURS
    public String theirStudents()
    {
        String studentString = this.toString()+"\n";
        if(listOfAttendingStudents.isEmpty())
        {
            studentString += "Kurs nie ma żadnych studentów.\n";
            return studentString;
        }
        else
        {

            Collections.sort(listOfAttendingStudents);
            for(int i =0; i<listOfAttendingStudents.size();i++)
            {
                studentString += "  - " + listOfAttendingStudents.get(i).toString()+"\n";
            }
            return studentString;
        }

    }


}
