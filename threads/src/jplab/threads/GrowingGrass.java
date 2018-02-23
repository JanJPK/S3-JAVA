package jplab.threads;

public class GrowingGrass implements Runnable
{
    private ResourceGrid resourceGrid;
    private int growFrequency;
    private int growAmount;

    // ************************************************ KONSTRUKTOR
    public GrowingGrass(int growAmount, int growFrequency, ResourceGrid resourceGrid)
    {
        this.resourceGrid = resourceGrid;
        this.growAmount = growAmount;
        this.growFrequency = growFrequency;
    }

    // ************************************************ RUN
    public void run()
    {

        try
        {

            while(true)
            {
                GrowGrass();
                Thread.sleep(growFrequency);

            }

        }
        catch(InterruptedException e)
        {
            //ex
        }
    }

    // ************************************************ ROSNIECIE TRAWY
    private void GrowGrass()
    {

        for (int i = 0; i < resourceGrid.getHeight(); i++)
        {
            for (int j = 0; j < resourceGrid.getWidth(); j++)
            {
                synchronized (resourceGrid.grassCells[i][j])
                {
                    if (resourceGrid.grassCells[i][j].getGrassSize() < 10)
                    {
                        resourceGrid.grassCells[i][j].addGrass(growAmount);
                    }
                }
            }
        }
    }
}
