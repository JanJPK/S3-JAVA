
// Subclass thread way.
public class ThreadTwo extends Thread
{
    public void run()
    {
        System.out.println("ThreadTwo here");
    }

    public static void main(String args[])
    {
        (new ThreadTwo()).start();
    }
}
