import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class ConsoleMode
{
    private static int choice;
    private static Scanner scanner;
    private static ArrayList<Student> listOfStudents;
    private static ArrayList<Course> listOfCourses;

    // ************************************ MENU
    public static void consoleMenu()
    {
        System.out.println("Witaj w systemie miniEdukacja - tryb konsolowy.");
        scanner = new Scanner(System.in);

        //************************************************************
        //*********** gdy test = true program tworzy  ****************
        //*********** przykładowych studentów i kursy ****************
        //************************************************************
        boolean test = true;

        if(test)
        {
            listOfStudents = RandomStudent.randomListOfStudents(10);
            listOfCourses = ExampleCourses.exampleListOfCourses();
            Collections.sort(listOfCourses);
            Collections.sort(listOfStudents);
        }
        else
        {
            listOfStudents = new ArrayList<Student>();
            listOfCourses = new ArrayList<Course>();
        }


        boolean exitMainMenu = false;
        while(!exitMainMenu)
        {
            System.out.println("************ MENU");
            System.out.print("1 - dodawanie studenta \n2 - dodawanie kursu\n3 - tworzenie połączenia\n" +
                            "4 - usuwanie połączenia\n5 - wyświetlanie\n6 - wyjśćie\n");

            // Samo nextInt nie "zjada" symbolu newline więc następny scanner z nextLine go wyłapywał.
            choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 1:
                {
                    // ************************************ DODAWANIE STUDENTA
                    Student student = new Student();
                    System.out.println("Podaj imię studenta:");
                    student.setName(scanner.nextLine());
                    System.out.println("Podaj nazwisko studenta:");
                    student.setSurname(scanner.nextLine());
                    if(student.getName().isEmpty()||student.getSurname().isEmpty())
                    {
                        System.out.println("Niepoprawne dane!");
                        break;
                    }
                    System.out.println("Podaj indeks studenta:");
                    try
                    {
                        student.setID(Integer.parseInt(scanner.nextLine()));
                        if(!ListOperations.checkForUniqueIndex(listOfStudents,student.getID()))
                        {
                            System.out.println("Indeks studenta nie może się powtarzać!");
                            break;
                        }
                    }
                    catch (NumberFormatException n)
                    {
                        System.out.println("Niepoprawne dane!");
                        break;
                    }
                    listOfStudents.add(student);
                    Collections.sort(listOfStudents);
                    System.out.println(student + " dodany do listy.\n");
                    break;
                }
                case 2:
                {
                    // ************************************ DODAWANIE KURSU
                    Course course = new Course();
                    System.out.println("Podaj nazwę kursu:");
                    course.setCourseName(scanner.nextLine());
                    System.out.println("Podaj kod grupy:");
                    course.setCode(scanner.nextLine());
                    if(course.getCourseName().isEmpty()||course.getCode().isEmpty())
                    {
                        System.out.println("Niepoprawne dane!");
                        break;
                    }
                    if(!ListOperations.checkForUniqueCode(listOfCourses,course.getCode()))
                    {
                        System.out.println("Kody kursów nie mogą się powtarzać!");
                        break;
                    }
                    System.out.println("Podaj wartość ECTS kursu:");
                    try
                    {
                        course.setECTS(Integer.parseInt(scanner.nextLine()));
                    }
                    catch (NumberFormatException n)
                    {
                        System.out.println("Niepoprawne dane!");
                        break;
                    }
                    listOfCourses.add(course);
                    Collections.sort(listOfCourses);
                    System.out.println(course + " dodany do listy.\n");
                    break;
                }
                case 3:
                {
                    // ************************************ LĄCZENIE KURSU ZE STUDENTEM
                    int studentN, courseN;
                    System.out.println(ListOperations.studentIndexes(listOfStudents));
                    System.out.println(ListOperations.courseIndexes(listOfCourses));

                    System.out.println("Podaj numer studenta:");
                    try
                    {
                        studentN = Integer.parseInt(scanner.nextLine());
                    }
                    catch(NumberFormatException n)
                    {
                        System.out.println("Niepoprawne dane!");
                        break;
                    }
                    System.out.println("Podaj numer kursu:");
                    try
                    {
                        courseN = Integer.parseInt(scanner.nextLine());
                    }
                    catch(NumberFormatException n)
                    {
                        System.out.println("Niepoprawne dane!");
                        break;
                    }
                    ListOperations.addConnection(listOfStudents,listOfCourses,studentN,courseN);
                    break;
                }
                case 4:
                {
                    // ************************************ USUWANIE POLACZENIA
                    int studentN, courseN;
                    System.out.println(ListOperations.studentIndexes(listOfStudents));
                    System.out.println("Podaj numer studenta:");
                    try
                    {
                        studentN = Integer.parseInt(scanner.nextLine());
                    }
                    catch(NumberFormatException n)
                    {
                        System.out.println("Niepoprawne dane!");
                        break;
                    }

                    if(studentN<=listOfStudents.size())
                    {
                        // Wyświetlanie kursów danego studenta.
                        System.out.println(ListOperations.courseIndexes(listOfStudents.get(studentN).getListOfTheirCourses()));
                        System.out.println("Podaj numer kursu:");
                        courseN = Integer.parseInt(scanner.nextLine());
                        ListOperations.removeConnection(listOfStudents,listOfCourses,studentN,courseN);
                        break;
                    }
                    else
                    {
                        break;
                    }

                }
                case 5:
                {
                    // ************************************ PRZEJŚĆIE DO MENU WYŚWIETLANIA
                    displayMenu();
                    break;
                }
                case 6:
                {
                    // ************************************ WYJŚCIE
                    exitMainMenu = true;
                    break;
                }
            }

        }

    }

    private static void displayMenu()
    {
        boolean exit = false;
        while(!exit)
        {
            System.out.println("************ WYSWIETLANIE");
            System.out.print("1 - studentów \n2 - kursów\n3 - studentów i ich kursów\n" +
                    "4 - kursów i ich studentów\n5 - powrót\n");

            choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 1:
                {
                    // ************************************ WYPISUJE STUDENTA [MIEJSCE W LIŚCIE] - INFORMACJE O STUDENCIE
                    System.out.println(ListOperations.studentIndexes(listOfStudents));
                    break;
                }
                case 2:
                {
                    // ************************************ WYPISUJE KURS [MIEJSCE W LIŚCIE] - INFORMACJE O KURSIE
                    System.out.println(ListOperations.courseIndexes(listOfCourses));
                    break;
                }
                case 3:
                {
                    // ************************************ WYPISUJE STUDENTÓW I ICH KURSY
                    System.out.println(ListOperations.theirCourses(listOfStudents));
                    break;
                }
                case 4:
                {
                    // ************************************ WYPISUJE KURSY I ICH STUDENTÓW
                    System.out.println(ListOperations.theirStudents(listOfCourses));
                    break;
                }
                case 5:
                {
                    // ************************************ WYJŚCIE
                    exit = true;
                    break;
                }
            }
        }

    }
}
