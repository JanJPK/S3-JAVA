package jplab.threads;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceGrid
{
    private JButton buttonDeleteSnail;
    private JButton buttonAddSnail;
    private JPanel panelMain;
    private JPanel panelGrass;
    private JTextField textFieldEatingAmount;
    private JTextField textFieldXCoordinate;
    private JTextField textFieldYCoordinate;
    private JTextField textFieldEatingSpeed;

    private static Image grass;
    private static Image noGrass;
    private static Image snailGrass;
    private static Image snailNoGrass;
    private static Image snailDeadGrass;
    private static Image snailDeadNoGrass;
    private static Image snailIcon;
    private static Image snailManual;

    private int width, height; // Szerokość i wysokość pola trawy (GrassGrid).
    GrassCell[][] grassCells; // Tablica komórek trawy.
    ArrayList<Snail> snails = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger( ResourceGrid.class.getName() );

    private static ResourceGrid rG;
    // ************************************************************************************************ RESOURCEGRID
    // ************************************************************************ KONSTRUKTOR
    public ResourceGrid(int height, int width)
    {
        this.width = width;
        this.height = height;
        LoadImages(); // Wczytuje obrazki dla GrassCell.
        grassCells = new GrassCell[this.height][this.width];
        panelGrass.setLayout(new BorderLayout());
        panelGrass.add(new GrassGrid());

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            LOGGER.log(Level.WARNING,ex.toString(),ex);
        }

        // Dodawanie ślimaka.
        buttonAddSnail.addActionListener(e ->
        {
            // Sprawdzanie czy pola nie są puste.
            if(!textFieldEatingAmount.getText().isEmpty() && !textFieldEatingSpeed.getText().isEmpty()
                    && !textFieldXCoordinate.getText().isEmpty() && !textFieldYCoordinate.getText().isEmpty())
            {

                try
                {
                    // Sprawdzanie czy da się parsować.
                    int xCoordinate = Integer.parseInt(textFieldXCoordinate.getText());
                    int yCoordinate = Integer.parseInt(textFieldXCoordinate.getText());
                    int eatingSpeed = Integer.parseInt(textFieldEatingSpeed.getText());
                    int eatingAmount = Integer.parseInt(textFieldEatingAmount.getText());
                    // Sprawdzanie czy podane dane nie wychodza poza obszar GrassGrid
                    if(xCoordinate < width && xCoordinate >= 0
                            && yCoordinate < height && yCoordinate >=0
                            && eatingSpeed >0 && eatingAmount > 0)
                    {
                        // Synchronizacja pola na którym ma wylądować ślimak.
                        synchronized(grassCells[yCoordinate][xCoordinate])
                        {
                            if(!grassCells[yCoordinate][xCoordinate].getSnail())
                            {
                                // Ślimak zostaje dodany a jego wątek startuje.
                                Snail snail = new Snail(
                                        yCoordinate, xCoordinate,
                                        eatingSpeed, eatingAmount, rG);
                                snails.add(snail);
                                new Thread(snail).start();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"Pole zajęte przez innego ślimaka",
                                        "Błąd",JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Niepoprawne dane startowe ślimaka",
                                "Błąd",JOptionPane.ERROR_MESSAGE);
                    }

                }
                catch(NumberFormatException ex)
                {
                    LOGGER.log(Level.WARNING,ex.toString(),ex);
                    JOptionPane.showMessageDialog(null,"Niepoprawne dane, to nie jest liczba.",
                            "Błąd",JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Pola nie mogą być puste.",
                        "Błąd",JOptionPane.ERROR_MESSAGE);

            }

        });

        // Usuwanie ślimaka.
        buttonDeleteSnail.addActionListener(e ->
        {
            if(snails.size()>0)
            {
                Random rng = new Random();
                int deadSnail = rng.nextInt(snails.size());
                snails.get(deadSnail).setTimeToDie(true);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Nie ma żadnych ślimaków, dziś nikt nie zginie.",
                        "Błąd",JOptionPane.ERROR_MESSAGE);
            }



        });
    }

    // ************************************************************************ MAIN
    public static void main(String[] args)
    {
        // Wprowadzanie wielkości pola trawy.
        int heightStart, widthStart;

        while(true)
        {
            try
            {
                heightStart = Integer.parseInt(JOptionPane.showInputDialog("Podaj wysokość pola z trawą."));
                if(heightStart>0)
                {
                    break;
                }
            }
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null,"Niepoprawne dane.","Błąd",JOptionPane.ERROR_MESSAGE);
            }

        }
        while(true)
        {
            try
            {
                widthStart = Integer.parseInt(JOptionPane.showInputDialog("Podaj szerokość pola z trawą."));
                if(widthStart>0)
                {
                    break;
                }
            }
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null,"Niepoprawne dane.","Błąd.",JOptionPane.ERROR_MESSAGE);
            }

        }

        rG = new ResourceGrid(heightStart, widthStart);
        // Instrukcja dla programu.
        JDialog dialog = new JDialog();
        JLabel label = new JLabel( new ImageIcon(snailManual));
        Button button = new Button();
        dialog.add( label );
        dialog.pack();
        dialog.setVisible(true);

        JFrame frame = new JFrame("Symulator ślimaka");
        frame.setIconImage(snailIcon);
        frame.setContentPane(rG.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        GrowingGrass grassIterator = new GrowingGrass(2,250,rG);
        new Thread(grassIterator).start();
    }

    // ************************************************************************ GET/SET
    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    // ************************************************************************************************ GRASSGRID
    public class GrassGrid extends JPanel
    {

        public GrassGrid()
        {
            // Tworzenie pola z komórkami trawy.
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    gbc.gridx = j;
                    gbc.gridy = i;

                    grassCells[i][j] = new GrassCell();

                    // Tworzenie brzegów.
                    Border border = null;
                    if (i < (height-1))
                    {

                        if (j < (width-1))
                        {
                            border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        } else
                        {
                            border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                        }
                    }
                    else
                    {

                        if (j < (width-1))
                        {
                            border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                        } else
                        {
                            border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                        }
                    }

                    // Dodawanie komórki z brzegiem.
                    grassCells[i][j].setBorder(border);
                    add(grassCells[i][j], gbc);

                }
            }

        }
    }

    // ************************************************************************************************ GRASSCELL
    public class GrassCell extends JPanel
    {

        boolean snail = false; // Czy jest tutaj ślimak?
        boolean deadSnail = false; // Czy jest tutaj martwy ślimak?
        JLabel grassLabel;

        // ************************************************ KONSTRUKTOR
        public GrassCell()
        {
            grassLabel = new JLabel("10");
            grassLabel.setForeground(Color.orange);
            this.setBackground(Color.GREEN);
            this.add(grassLabel);

        }

        // ************************************************ OVERRIDE
        // ************************ WIELKOŚĆ JPANEL
        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(70, 70);
        }

        // ************************ TŁO
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            // Ustawia odpowiedni obraz który jest zwracany przez metodę BackgroundImage().
            g.drawImage(BackgroundImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // ************************************************ TRAWA
        // ************************ SET/GET WYSOKOŚCI TRAWY
        public void setGrassSize(int size)
        {
            grassLabel.setText(String.valueOf(size));
            this.repaint();
        }

        // Zamiast trzymać int z wielkością trawy za każdym razem wywołuje parsowanie tego co jest czyli JLabel.
        // Może być to głupie rozwiązanie pochłaniające więcej zasobów, nie jestem pewien.
        public int getGrassSize()
        {
            return Integer.parseInt(grassLabel.getText());
        }

        // ************************ USTAWIANIE OBRAZKA NA TŁO
        public Image BackgroundImage()
        {
            if(!deadSnail)
            {
                if (getGrassSize() > 0)
                {
                    if (snail)
                    {
                        return snailGrass;
                    } else
                    {
                        return grass;
                    }
                } else
                {
                    if (snail)
                    {
                        return snailNoGrass;
                    } else
                    {
                        return noGrass;
                    }
                }
            }
            else
            {
                if(getGrassSize()>0)
                {
                    return snailDeadGrass;
                }
                else
                {
                    return snailDeadNoGrass;
                }
            }
        }

        // ************************ SPRAWDZANIE CZY JEST TRAWA
        public boolean CheckGrass()
        {
            if(getGrassSize()>0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        // ************************ DODAWANIE/ODEJMOWANIE TRAWY
        // Dodawanie i usuwanie zadanej ilości trawy.
        public void addGrass(int amount)
        {
            synchronized(this)
            {
                if(getGrassSize()<10)
                {
                    // Trawa nie może przekroczyć 10.
                    if(getGrassSize()+amount >10)
                    {
                        setGrassSize(10);
                    }
                    else
                    {
                        setGrassSize(getGrassSize()+amount);
                    }

                }
            }

        }

        public void removeGrass(int amount)
        {
            synchronized(this)
            {
                if(amount>getGrassSize())
                {
                    setGrassSize(0);
                }
                else
                {
                    setGrassSize(getGrassSize()-amount);
                }
            }


        }

        // ************************************************ ŚLIMAK
        public boolean getSnail()
        {
            return this.snail;
        }

        public void setSnail(boolean snail)
        {
            this.snail = snail;
            if(snail)
            {
                deadSnail = false;
            }
            this.repaint();
        }

        public boolean getDeadSnail()
        {
            return deadSnail;
        }

        public void setDeadSnail(boolean deadSnail)
        {
            this.deadSnail = deadSnail;
            this.repaint();
        }

    }

    // ****************************************************************** GET GRASSCELL
    public GrassCell getCellPane(int height, int width)
    {
        return grassCells[height][width];
    }

    // ************************************************************************************************ ŁADOWANIE OBRAZKÓW
    // Wczytywanie obrazów z katalogu /resources
    private void LoadImages()
    {
        try
        {
            snailGrass = ImageIO.read(getClass().getResource("/snailgrasstrue.png"));
            snailNoGrass = ImageIO.read(getClass().getResource("/snailgrassfalse.png"));
            snailDeadGrass = ImageIO.read(getClass().getResource("/deadsnailgrasstrue.png"));
            snailDeadNoGrass = ImageIO.read(getClass().getResource("/deadsnailgrasstrue.png"));
            noGrass = ImageIO.read(getClass().getResource("/grassfalse.png"));
            grass = ImageIO.read(getClass().getResource("/grasstrue.png"));
            snailIcon = ImageIO.read(getClass().getResource("/snailicon.png"));
            snailManual = ImageIO.read(getClass().getResource("/snailmanual.png"));
        }
        catch (IOException ex)
        {
            LOGGER.log(Level.WARNING,ex.toString(),ex);
        }
    }

    // ************************************************************************************************ OPERACJE ŚLIMAKA
    // ************************************************ PRZESUŃ ŚLIMAKA
    public synchronized void MoveSnail(Snail snail, int yDestination, int xDestination)
    {
        grassCells[snail.getyCoordinate()][snail.getxCoordinate()].setSnail(false); // Usuwa slimaka z obecnej lokalizacji.
        grassCells[yDestination][xDestination].setSnail(true); // Dodaje do nowej.
        grassCells[yDestination][xDestination].removeGrass(snail.getEatingAmount()); // Zjada trawę.

    }
}
