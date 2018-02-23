public class Main {

    /*
    *************************** ZMIANY/INFORMACJE ****************
    *   Program obecnie nie robi praktycznie nic, sprawdzałem tylko jak działają różne funkcje.
    *
    *
    *
    **************************************************************
    */
    public static void main(String[] args)

        throws InterruptedException
        {String sleepMessage[] = {"Pierwsza wiadomość.","Druga wiadomość.","Trzecia wiadomość","Czwarta wiadomość."};



	        //ThreadOne threadOne = new ThreadOne(); // implements Runnable
            //ThreadTwo threadTwo = new ThreadTwo(); // extends Thread

            //threadOne.run();
            //threadTwo.run();


            for(int i = 0; i < sleepMessage.length;i++)
            {
                try
                {
                    Thread.sleep(4000); // Wypisuje wiadomość co 4 sekundy
                }
                catch(InterruptedException e)
                {
                    return;
                }

                System.out.println(sleepMessage[i]);
            }


            // interrupt - rozkazuje wątkowi porzucić to co robi i zacząć robić coś innego
            // przy tworzeniu uwaga - musi być start(). jeżeli będzie tylko run() to wtedy wątek który tworzy nowy wykonuje
            // również jego instrukcje
            //
    }
}
