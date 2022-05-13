import java.awt.*;
public class Explode {
    int x,y;
    private boolean live=true;
    
    private TankClient tc;
    int []diameter= {5,7,10,20,30,45,50,30,20,10,3,};
    int step=0;
    
    public Explode(int x,int y,TankClient tc) {
    	this.x=x;
    	this.y=y;
    	this.tc=tc;
    }
    
    
    public void draw(Graphics g) {
       if(!live)return ;
       
       
       if(step==diameter.length) {
    	   live=false;
    	   step=0;
    	   return ;
       }
       Color c=g.getColor();
       g.setColor(Color.ORANGE);
       g.fillOval( x, y,diameter[step],diameter[step]);
       g.setColor(c);
       step++;
    }
}
