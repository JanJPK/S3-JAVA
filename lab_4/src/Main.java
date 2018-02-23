
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import javax.swing.JOptionPane;

public class Main extends JFrame
{

    /*
    *************************** ZMIANY ***************************
    *   Praktycznie wszystko.
    *   Zmiana nazewnictwa.
    *   Student zawiera listę swoich kursów a kurs zawiera listę swoich studentów.
    *       Każdy student może sprawdzić gdzie jest zapisany, a prowadzący kurs wie jakich ma uczestników.
    *   Klasa statyczna ListOperations wykonuje operacje łączenia studenta z grupą i wypisywania.
    *   Klasa RandomStudent wypełnia listę zadaną ilością losowych studentów.
    *   Klasa ExampleCourses wypełnia liste kilkoma przykładowymi kursami.
    *   GUI tworzone ręcznie w oparciu o dokumentację Javy
    *   (https://docs.oracle.com/javase/tutorial/uiswing/layout/groupExample.html)
    *
    *
    *   Student ma pola int indeks, string name, string surname
    *   Kurs ma pola int ECTS, string coursename, string code (kod grupy)
    *
    *   Co można poprawić?
    *
    *   Zapomniałem i skutkiem czego nie zdążyłem zaimplementować sortowania po nazwisku
    *   w przypadku gdy dwie osoby mają takie samo imię.
    *
    *   Zmodyfikować dodawanie i usuwanie połączenia w ListOperations tak by było niezależne
    *   od metody wyświetlania programu (konsoli/GUI) - przenieść sprawdzanie warunków
    *   do oddzielnej funkcji która wyrzuca błędy w zależności od trybu; string dla konsoli a okienko dla GUI.
    *
    *   Dla estetyki przenieść wszystko co związane z GUI do osobnej klasy.
    *
    **************************************************************
    *
    */

    //************************************************************
    //*********** gdy test = true program tworzy  ****************
    //*********** przykładowych studentów i kursy ****************
    //************************************************************
    boolean test = true;


    private static ArrayList<Student> listOfStudents;
    private static ArrayList<Course> listOfCourses;

    public Main()
    {

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

        // ************************************ TEXTFIELD + LABEL + TEXTAREA
        JTextField textFieldStudentName = new JTextField();
        JTextField textFieldStudentSurname = new JTextField();
        JTextField textFieldStudentID = new JTextField();
        JTextField textFieldCourseName = new JTextField();
        JTextField textFieldCourseECTS = new JTextField();
        JTextField textFieldCourseCode = new JTextField();
        JTextField textFieldConnectStudent = new JTextField();
        JTextField textFieldConnectCourse = new JTextField();

        JLabel labelStudentName = new JLabel("Imię:");
        JLabel labelStudentSurname = new JLabel("Nazwisko:");
        JLabel labelStudentID = new JLabel("Indeks:");
        JLabel labelCourseName = new JLabel("Nazwa:");
        JLabel labelCourseECTS = new JLabel("ECTS:");
        JLabel labelCourseCode = new JLabel("Kod:");
        JLabel labelConnect = new JLabel("Dodaj studenta do kursu");
        JLabel labelConnectStudent = new JLabel("ID studenta:");
        JLabel labelConnectCourse = new JLabel("ID kursu:");

        // Scrollowalny textArea/
        JTextArea textArea = new JTextArea ();
        textArea.setEditable ( false );
        JScrollPane scroll = new JScrollPane ( textArea );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        // ************************************ PRZYCISKI
        JButton buttonAddStudent = new JButton("Dodaj studenta");
        buttonAddStudent.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!textFieldStudentName.getText().isEmpty() && !textFieldStudentSurname.getText().isEmpty()
                        && !textFieldStudentID.getText().isEmpty())
                {

                    try
                    {
                        if(ListOperations.checkForUniqueIndex(listOfStudents,Integer.parseInt(textFieldStudentID.getText())))
                        {
                            Student student = new Student();
                            student.setName(textFieldStudentName.getText());
                            student.setSurname(textFieldStudentSurname.getText());
                            student.setID(Integer.parseInt(textFieldStudentID.getText()));
                            int answer = JOptionPane.showConfirmDialog(null, "Czy chcesz dodać ["+student.toString()+"]");
                            if (answer == JOptionPane.YES_OPTION) {
                                listOfStudents.add(student);
                            } else if (answer == JOptionPane.NO_OPTION) {

                            }
                        }
                        else
                        {
                            Toolkit.getDefaultToolkit().beep();
                            JOptionPane.showMessageDialog(null,
                                    "Indeksy nie mogą się powtarzać!",
                                    "Błąd",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    catch (NumberFormatException n)
                    {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null,
                                "Niepoprawny indeks studenta!",
                                "Błąd",
                                JOptionPane.ERROR_MESSAGE);

                    }
                }
                else
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "Pola tekstowe nie są wypełnione!",
                            "Błąd",
                            JOptionPane.ERROR_MESSAGE);

                }

            }
        });

        JButton buttonAddCourse = new JButton("Dodaj kurs");
        buttonAddCourse.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!textFieldCourseName.getText().isEmpty() && !textFieldCourseCode.getText().isEmpty()
                        && !textFieldCourseECTS.getText().isEmpty())
                {
                    if(ListOperations.checkForUniqueCode(listOfCourses,textFieldCourseCode.getText()))
                    {
                        Course course = new Course();
                        try
                        {

                            course.setCourseName(textFieldCourseName.getText());
                            course.setCode(textFieldCourseCode.getText());
                            course.setECTS(Integer.parseInt(textFieldCourseECTS.getText()));
                            int answer = JOptionPane.showConfirmDialog(null, "Czy chcesz dodać ["+course.toString()+"]");
                            if (answer == JOptionPane.YES_OPTION) {
                                listOfCourses.add(course);
                            } else if (answer == JOptionPane.NO_OPTION) {

                            }

                        }
                        catch (NumberFormatException n)
                        {
                            Toolkit.getDefaultToolkit().beep();
                            JOptionPane.showMessageDialog(null,
                                    "Niepoprawna liczba ECTS!",
                                    "Błąd",
                                    JOptionPane.ERROR_MESSAGE);

                        }
                    }
                    else
                    {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null,
                                "Kod kursu nie może się powtarzać!",
                                "Błąd",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }
                else
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "Pola tekstowe nie są wypełnione!",
                            "Błąd",
                            JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        JButton buttonShowStudents = new JButton("Pokaż studentów");
        buttonShowStudents.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textArea.setText(ListOperations.studentIndexes(listOfStudents));
            }
        });

        JButton buttonShowCourses = new JButton("Pokaż kursy");
        buttonShowCourses.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textArea.setText(ListOperations.courseIndexes(listOfCourses));
            }
        });

        JButton buttonShowStudentCourses = new JButton("Pokaż studentów i ich kursy");
        buttonShowStudentCourses.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textArea.setText(ListOperations.theirCourses(listOfStudents));
            }
        });

        JButton buttonShowCourseStudents = new JButton("Pokaż kursy i ich studentów");
        buttonShowCourseStudents.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textArea.setText(ListOperations.theirStudents(listOfCourses));
            }
        });

        JButton buttonAddConnection = new JButton("Dodaj");
        buttonAddConnection.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!textFieldConnectStudent.getText().isEmpty() && !textFieldConnectCourse.getText().isEmpty())
                {
                    try
                    {
                        ListOperations.addConnection(listOfStudents,listOfCourses,
                                Integer.parseInt(textFieldConnectStudent.getText()),
                                Integer.parseInt(textFieldConnectCourse.getText()));

                    }
                    catch (NumberFormatException n)
                    {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null,
                                "Niepoprawny indeks! To nie jest liczba!",
                                "Błąd",
                                JOptionPane.ERROR_MESSAGE);

                    }

                }
                else
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "Oba pola muszą być wypełnione!",
                            "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton buttonRemoveConnection = new JButton("Usuń");
        buttonRemoveConnection.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!textFieldConnectStudent.getText().isEmpty() && !textFieldConnectCourse.getText().isEmpty())
                {
                    try
                    {
                        ListOperations.removeConnection(listOfStudents,listOfCourses,
                                Integer.parseInt(textFieldConnectStudent.getText()),
                                Integer.parseInt(textFieldConnectCourse.getText()));

                    }
                    catch (NumberFormatException n)
                    {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null,
                                "Niepoprawny indeks! To nie jest liczba!",
                                "Błąd",
                                JOptionPane.ERROR_MESSAGE);

                    }
                }
                else
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "Oba pola muszą być wypełnione!",
                            "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        // ************************************ GROUP LAYOUT
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        layout.setHorizontalGroup(layout.createSequentialGroup()

                // false zabrania rozciągania się przy powiększaniu okna.
               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
                    .addComponent(labelStudentName)
                    .addComponent(labelStudentSurname)
                    .addComponent(labelStudentID)
                    .addComponent(labelCourseName)
                    .addComponent(labelCourseECTS)
                    .addComponent(labelCourseCode))

               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
                    .addComponent(textFieldStudentName)
                    .addComponent(textFieldStudentSurname)
                    .addComponent(textFieldStudentID)
                    .addComponent(buttonAddStudent)
                    .addComponent(textFieldCourseName)
                    .addComponent(textFieldCourseECTS)
                    .addComponent(textFieldCourseCode)
                    .addComponent(buttonAddCourse))

               .addComponent(scroll)

               .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
                            .addComponent(buttonShowStudents)
                            .addComponent(buttonShowCourses)
                            .addComponent(buttonShowStudentCourses)
                            .addComponent(buttonShowCourseStudents)
                            .addComponent(labelConnect))

                        .addGroup(layout.createSequentialGroup()

                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
                                .addComponent(labelConnectStudent)
                                .addComponent(textFieldConnectStudent)
                                .addComponent(buttonAddConnection))
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
                                .addComponent(labelConnectCourse)
                                .addComponent(textFieldConnectCourse)
                                .addComponent(buttonRemoveConnection))))




        );

        layout.setVerticalGroup(layout.createParallelGroup()
                .addComponent(scroll)

                .addGroup(layout.createSequentialGroup()

                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(labelStudentName)
                        .addComponent(textFieldStudentName)
                        .addComponent(buttonShowStudents))

                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelStudentSurname)
                        .addComponent(textFieldStudentSurname)
                        .addComponent(buttonShowCourses))

                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelStudentID)
                        .addComponent(textFieldStudentID)
                        .addComponent(buttonShowStudentCourses))

                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonAddStudent)
                        .addComponent(buttonShowCourseStudents))

                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelCourseName)
                        .addComponent(textFieldCourseName)
                        .addComponent(labelConnect))

                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelCourseCode)
                        .addComponent(textFieldCourseCode)
                        .addComponent(labelConnectStudent)
                        .addComponent(labelConnectCourse))

                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelCourseECTS)
                        .addComponent(textFieldCourseECTS)
                        .addComponent(textFieldConnectStudent)
                        .addComponent(textFieldConnectCourse))

                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonAddCourse)
                        .addComponent(buttonAddConnection)
                        .addComponent(buttonRemoveConnection)))


        );

        setTitle("miniEdukacja");
        pack();
        setSize(800,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }



    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(
                            "javax.swing.plaf.metal.MetalLookAndFeel");
                    // "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    //UIManager.getCrossPlatformLookAndFeelClassName());
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                new Main().setVisible(true);
            }
        });
    }
}