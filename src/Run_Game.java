import java.awt.event.*;
import javax.swing.*;

public class Run_Game implements ActionListener{
    private static JFrame f = new JFrame();
    private static Tower t;
    private static JMenuBar menu_bar=new JMenuBar();

    private JMenuItem
            new_game=new JMenuItem("New Game"),
            exit=new JMenuItem("Exit"),
            about=new JMenuItem("About.......");

    private JMenu
            help=new JMenu("Help"),
            game=new JMenu("Game");

    public Run_Game(String title) {
        f.setTitle(title);
        build();
    }

    public void actionPerformed(ActionEvent ev){
        if(ev.getSource()==new_game){
            Object values[]= { 3,4,5,6,7,8,9 };
            Object val=JOptionPane.showInputDialog(f,"No. Of Disks : ","Input",
                    JOptionPane.INFORMATION_MESSAGE,null,values,values[0]);
            if((int)val!=JOptionPane.CANCEL_OPTION)
                t.init((int)val);
        }
        else if(ev.getSource()==exit)
            System.exit(0);
    }

    private void build(){

        game.add(new_game);
        game.add(exit);
        help.add(about);

        menu_bar.add(game);
        menu_bar.add(help);

        new_game.addActionListener(this);
        exit.addActionListener(this);

        f.setJMenuBar(menu_bar);
        f.setSize(660, 280);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> {
                    Run_Game obj = new Run_Game("Tower Of Hanoi");
                    t = new Tower();
                    f.getContentPane().add(t);
                });
    }

}