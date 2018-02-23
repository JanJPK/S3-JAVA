
// Runnable object way.
public class ThreadOne implements Runnable
{
    public void run()
    {
        System.out.println("ThreadOne here");


    }

    public static void main(String args)
    {
        (new Thread(new ThreadOne())).start();
    }
}
