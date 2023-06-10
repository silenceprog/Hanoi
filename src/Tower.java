import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Tower extends JPanel implements MouseListener,MouseMotionListener{
    private Stack<Rectangle2D.Double> towerStack[]=new Stack[3];
    private Stack<Color> disk_color[]=new Stack[3];
    private static Rectangle2D.Double top=null;
    private Color top_color=null;
    private double ax,ay, widthRect, heightRect;
    private boolean draggable=false,firstTime=false;

    public Tower() {
        firstTime=true;
        init(4);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void init(int val){
        Color c[]={Color.yellow,Color.red,Color.blue,Color.pink,Color.cyan,Color.magenta,Color.green,Color.orange,Color.lightGray};

        towerStack[0]=new Stack<Rectangle2D.Double>();
        towerStack[1]=new Stack<Rectangle2D.Double>();
        towerStack[2]=new Stack<Rectangle2D.Double>();

        disk_color[0]=new Stack<Color>();
        disk_color[1]=new Stack<Color>();
        disk_color[2]=new Stack<Color>();

        for (int i = 0; i<val; i++) {
            Rectangle2D.Double r=new Rectangle2D.Double();

            double x = getWidth()/6 ;
            x = (x == 0)?109 : x;
            double widthRectangle = val*25-20*i;
            r.setFrame(x- widthRectangle /2,190-i*20, widthRectangle,20);
            towerStack[0].push(r);
            disk_color[0].push(c[i]);


        }

        top=null;
        top_color=null;
        ax=0.0; ay=0.0;  widthRect =0.0;  heightRect =0.0;
        draggable=false;
        repaint();
    }

    public void mouseClicked(MouseEvent ev){}

    public void mousePressed(MouseEvent ev){
        Point pos=ev.getPoint();
        int n=current_tower(pos);
        if(!towerStack[n].empty()){
            top= towerStack[n].peek();
            if(top.contains(pos)){
                top= towerStack[n].pop();
                top_color=disk_color[n].pop();
                ax=top.getX();    ay=top.getY();
                widthRect =pos.getX()-ax;   heightRect =pos.getY()-ay;
                draggable=true;
            }
            else{
                top=null;
                top_color=Color.black;
                draggable=false;
            }
        }
    }

    public void mouseReleased(MouseEvent ev){
        if(top!=null && draggable==true){
            int activeTower=current_tower(ev.getPoint());
            double x,y;
            if(!towerStack[activeTower].empty()){
                if(towerStack[activeTower].peek().getWidth()>top.getWidth())
                    y= towerStack[activeTower].peek().getY()-20;
                else{
                    JOptionPane.showMessageDialog(this,"Wrong Move","Tower Of Hanoi",JOptionPane.ERROR_MESSAGE);
                    activeTower=current_tower(new Point((int)ax,(int)ay));
                    if(!towerStack[activeTower].empty())
                        y= towerStack[activeTower].peek().getY()-20;
                    else
                        y=getHeight()-40;

                }
            }
            else
                y=getHeight()-40;  //if no previous disk in activeTower

            x=(int)(getWidth()/6+(getWidth()/3)*activeTower-top.getWidth()/2);
            top.setFrame(x,y,top.getWidth(),top.getHeight());
            towerStack[activeTower].push(top);
            disk_color[activeTower].push(top_color);

            top=null;
            top_color=Color.black;
            draggable=false;
            repaint();
        }
    }

    public void mouseEntered(MouseEvent ev){}

    public void mouseExited(MouseEvent ev){}

    public void mouseMoved(MouseEvent ev){}

    public void mouseDragged(MouseEvent ev){
        int cx=ev.getX();
        int cy=ev.getY();
        if(top!=null && draggable==true){
            top.setFrame(cx- widthRect,cy- heightRect,top.getWidth(),top.getHeight());
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d=(Graphics2D)g;
        g2d.setColor(Color.black);
        g2d.fillRect(0,0,getWidth(),getHeight());

        int holder_x=getWidth()/6;
        int holder_y1=getHeight()-10*20,holder_y2=getHeight()-20;

        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(holder_x,holder_y1,holder_x,holder_y2);
        g2d.drawLine(3*holder_x,holder_y1,3*holder_x,holder_y2);
        g2d.drawLine(5*holder_x,holder_y1,5*holder_x,holder_y2);
        g2d.drawLine(0,holder_y2,getWidth(),holder_y2);

        g2d.setStroke(new BasicStroke(1));

        g2d.setColor(top_color);

        if(draggable==true && top!=null)
            g2d.fill(top);  //drawing dragged disk

        drawtower(g2d,0);
        drawtower(g2d,1);
        drawtower(g2d,2);
    }

    private void drawtower(Graphics2D g2d,int n){
        if(!towerStack[n].empty()){
            for(int i = 0; i < towerStack[n].size(); i++){
                g2d.setColor(disk_color[n].get(i));
                g2d.fill(towerStack[n].get(i));
            }
        }
    }

    private int current_tower(Point p){
        Rectangle2D.Double
                rA=new Rectangle2D.Double(),
                rB=new Rectangle2D.Double(),
                rC=new Rectangle2D.Double();

        rA.setFrame(0,0,getWidth()/3,getHeight());
        rB.setFrame(getWidth()/3,0,getWidth()/3,getHeight());
        rC.setFrame(2*getWidth()/3,0,getWidth()/3,getHeight());

        if(rA.contains(p))
            return 0;
        else if(rB.contains(p))
            return 1;
        else if(rC.contains(p))
            return 2;
        else
            return -1;
    }

}
