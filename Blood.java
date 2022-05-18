import java.awt.*;
public class Blood {
     int x,y,w,h;
     TankClient tc;
     int step=0;
     private boolean live=true;
     public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isLive() {
		return live;
	}
	private int[][] pos =  {
    		 {100,200},{100,150},{200,200},{200,230},{245,275},{260,270},{250,290}
     };
     public Blood() {
    	 x=pos[0][0];
    	 y=pos[0][1];
     }
     public void draw(Graphics g) {
    	 if(!live) return ;
    	 Color c=g.getColor();
    	 g.setColor(Color.MAGENTA);
    	 g.fillRect(x, y, w, h);
    	 g.setColor(c);
    	 
    	 
    	 move();
     }
     private void move() {
    	 step++;
    	 if(step==pos.length )
    	 {
    		 step=0;
    		 
    	 }
    	 x=pos[step][0];
    	 y=pos[step][1];
    	 w=h=20;
    }
     public Rectangle getRect() {
    	 return new Rectangle(x,y,w,h);
     }
}
