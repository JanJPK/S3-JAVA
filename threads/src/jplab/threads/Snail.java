package jplab.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



// ****************************************************************** ŚLIMAK
    public class Snail implements Runnable
    {

        private int yCoordinate, xCoordinate;
        private int eatingFrequency; // Co ile ślimak je
        private int eatingAmount = 2; // Ile ślimak zjada
        private ResourceGrid rG;
        private static final Logger LOGGER = Logger.getLogger( Snail.class.getName() );
        volatile boolean timeToDie = false;

        // ************************************************ GET/SET
        public int getyCoordinate()
        {
            return yCoordinate;
        }

        public void setyCoordinate(int yCoordinate)
        {
            this.yCoordinate = yCoordinate;
        }

        public int getxCoordinate()
        {
            return xCoordinate;
        }

        public void setxCoordinate(int xCoordinate)
        {
            this.xCoordinate = xCoordinate;
        }

        public int getEatingFrequency()
        {
            return eatingFrequency;
        }

        public void setEatingFrequency(int eatingFrequency)
        {
            this.eatingFrequency = eatingFrequency;
        }

        public int getEatingAmount()
        {
            return eatingAmount;
        }

        public void setEatingAmount(int eatingAmount)
        {
            this.eatingAmount = eatingAmount;
        }

        public boolean isTimeToDie()
        {
            return timeToDie;
        }

        public void setTimeToDie(boolean timeToDie)
        {
            this.timeToDie = timeToDie;
        }

        // ************************************************ KONSTRUKTOR
        public Snail(int yCoordinate, int xCoordinate, int eatingFrequency, int eatingAmount, ResourceGrid resourceGrid)
        {
            this.yCoordinate = yCoordinate;
            this.xCoordinate = xCoordinate;
            this.eatingFrequency = eatingFrequency;
            this.eatingAmount = eatingAmount;
            rG = resourceGrid;
            rG.grassCells[yCoordinate][xCoordinate].setSnail(true);
        }

        // ************************************************ RUN
        public void run()
        {
            try
            {
                while(!timeToDie)
                {
                    EatGrass();
                    Thread.sleep(eatingFrequency);
                }
                Die();


            } catch (InterruptedException ex)
            {
                LOGGER.log(Level.SEVERE,ex.toString(),ex);
            }

        }

        // ************************************************ JEDZ TRAWĘ
        private void EatGrass()
        {
            // Jeżeli jest trawa, to ją zjedz => removeGrass(ileZjadł)
            if(rG.grassCells[yCoordinate][xCoordinate].CheckGrass())
            {
                rG.grassCells[yCoordinate][xCoordinate].removeGrass(eatingAmount);
            }
            else
            {
                // Nie ma trawy?
                SearchForGrass();
            }
        }

        // ************************************************ GIŃ
        private void Die()
        {
            rG.grassCells[yCoordinate][xCoordinate].setSnail(false);
            rG.grassCells[yCoordinate][xCoordinate].setDeadSnail(true);
        }

        // ************************************************ SZUKAJ TRAWY BO JEJ NIE MA
        private void SearchForGrass()
        {
            // Bardzo prymitywne rozwiązanie, jak wymyślę coś lepszego to zmienię.
            // Każdy kierunek jest sprawdzany, gdy któryś jest OK zostaje on dodany do listy.
            // Następnie rng losuje numer z listy a switch robi na podstawie tego numeru ruch.
            boolean searchContinues = true;
            List<Integer> directions = new ArrayList<Integer>();

            // Szukanie w każdym kierunku.
            while(searchContinues)
            {

                // Synchronizuje tylko komórki na których szuka trawy.
                if(yCoordinate < rG.getHeight()-1)
                {
                    synchronized(rG.grassCells[yCoordinate +1][xCoordinate])
                    {
                        if(rG.grassCells[yCoordinate +1][xCoordinate].CheckGrass()
                                && !rG.grassCells[yCoordinate +1][xCoordinate].getSnail())
                        {
                            directions.add(1);
                        }
                    }

                }

                if(yCoordinate>0)
                {
                    synchronized(rG.grassCells[yCoordinate -1][xCoordinate])
                    {
                        if(rG.grassCells[yCoordinate -1][xCoordinate].CheckGrass()
                                && !rG.grassCells[yCoordinate -1][xCoordinate].getSnail())
                        {
                            directions.add(2);
                        }
                    }

                }


                if(xCoordinate <rG.getWidth()-1)
                {
                    synchronized(rG.grassCells[yCoordinate][xCoordinate +1])
                    {
                        if(rG.grassCells[yCoordinate][xCoordinate +1].CheckGrass()
                                && !rG.grassCells[yCoordinate][xCoordinate +1].getSnail())
                        {
                            directions.add(3);
                        }
                    }

                }

                if(xCoordinate>0)
                {
                    synchronized(rG.grassCells[yCoordinate][xCoordinate -1])
                    {
                        if(rG.grassCells[yCoordinate][xCoordinate -1].CheckGrass()
                                && !rG.grassCells[yCoordinate][xCoordinate -1].getSnail())
                        {
                            directions.add(4);
                        }
                    }

                }

                if(directions.isEmpty())
                {
                    // Nie znalazł trawy w swoim otoczenmiu a nie chce mu się iść dalej więc idzie spać.
                    try
                    {
                        Thread.sleep(400);
                    }
                    catch(InterruptedException ex)
                    {
                        LOGGER.log(Level.SEVERE,ex.toString(),ex);
                    }
                }
                else
                {
                    // Slimak przemieszcza się.
                    Random rng = new Random();
                    int chosenDirection;
                    chosenDirection = directions.get(rng.nextInt(directions.size()));

                    switch (chosenDirection)
                    {
                        // Prototypowa synchronizacja w tym miejscu została zakomentowana gdyż synchronizacja w MoveSnail
                        // powinna być wystarczająca.
                        case 1:
                        {
                            //synchronized(rG.grassCells[yCoordinate +1][xCoordinate])
                            //{
                                rG.MoveSnail(this, yCoordinate +1, xCoordinate);
                                yCoordinate +=1;
                            //}
                            break;
                        }
                        case 2:
                        {
                            //synchronized(rG.grassCells[yCoordinate -1][xCoordinate])
                            //{
                                rG.MoveSnail(this, yCoordinate -1, xCoordinate);
                                yCoordinate -=1;
                            //}
                            break;
                        }
                        case 3:
                        {
                            //synchronized(rG.grassCells[yCoordinate][xCoordinate +1])
                            //{
                                rG.MoveSnail(this, yCoordinate, xCoordinate +1);
                                xCoordinate +=1;
                            //}
                            break;
                        }
                        case 4:
                        {
                            //synchronized(rG.grassCells[yCoordinate][xCoordinate -1])
                            //{
                                rG.MoveSnail(this, yCoordinate, xCoordinate -1);
                                xCoordinate -=1;
                            //}
                            break;
                        }
                    }
                    directions.clear();
                    searchContinues = false;
                }
            }

        }
    }
