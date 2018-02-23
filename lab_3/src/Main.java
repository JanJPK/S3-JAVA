
import sun.security.pkcs11.wrapper.CK_SSL3_RANDOM_DATA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main
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
    **************************************************************
    */




    public static void main(String[] args)
    {

        ConsoleMode.consoleMenu();

    }

}
