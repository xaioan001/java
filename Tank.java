import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

public class Tank{
	public static final int XSPEED=10;
	public static final int YSPEED=10;
	
	public static final int WIDTH=30;
	public static final int HEIGHT=30;
	
	
	
	TankClient tc;
	
	
	private int x,y;
	
	private boolean bL=false,bU=false,bR=false,bD=false;
	enum Direction{L,LU,U,RU,R,RD,D,LD,STOP};
	private Direction dir=Direction.STOP;
	public Tank(int x,int y) {
		this.x=x;
		this.y=y;
	}
	    public Tank(int x,int y,TankClient tc) {
	    	this(x,y);
	    	this.tc=tc;
	    }
 	    
		public void draw(Graphics g) {
			Color c=g.getColor();
			//坦克的颜色
			g.setColor(Color.RED);
			g.fillOval(x,y, WIDTH, HEIGHT);
			g.setColor(c);	
			
			move();
		}
		
		void move() {
			switch(dir) {
			case L:                //向左
				x-=XSPEED;
			case LU:               //左上
				x-=XSPEED;
				y-=YSPEED;
			case U:              //向上
				x-=XSPEED;
				
			case RU:             //右上
				x-=XSPEED;
				y-=YSPEED;
			case R:               //向右
				x+=XSPEED;
				y-=YSPEED;
			case RD:              //右下
				x+=XSPEED;
				y+=YSPEED;
			case  D:                  //向下
				
				y+=YSPEED;
			case LD:
			    x-=XSPEED;
			    y-=YSPEED;
			case STOP:
				break;
			
			}
		}
		public void Keypressed(KeyEvent e) {
			int key=e.getKeyCode();
		     switch(key){
		    case KeyEvent.VK_CONTROL:
		         fire();
		    	
			case KeyEvent.VK_LEFT:
			       bL=true;
			       break;
			case KeyEvent.VK_UP:
			       bU=true;
			       break;
			case KeyEvent.VK_RIGHT:
			      bR=true;
			       break;
			case KeyEvent.VK_DOWN:
                  bD=true;
			       break;
			}
		  locateDirection();
		}
		void locateDirection() {
			if(bL&&!bU&&!bR&&!bD)dir=Direction.L;
			else if(bL&&bU&&!bR&&!bD)dir=Direction.LU;
			else if(!bL&&bU&&!bR&&!bD)dir=Direction.U;
			else if(!bL&&bU&&bR&&!bD)dir=Direction.RU;
			else if(!bL&&!bU&&bR&&!bD)dir=Direction.R;
			else if(!bL&&!bU&&bR&&bD)dir=Direction.RD;
			else if(!bL&&!bU&&!bR&&bD)dir=Direction.D;
			else if(!bL&&!bU&&!bR&&bD)dir=Direction.LD;
			else if(!bL&&!bU&&!bR&&!bD)dir=Direction.STOP;
			
		}


		public void KeyReleased(KeyEvent e) {
			
			int key=e.getKeyCode();
		     switch(key){
		      case KeyEvent.VK_CONTROL:
		    	  tc.m=fire();
		    	   break;
			  case KeyEvent.VK_LEFT:
			       bL=false;
			       break;
			  case KeyEvent.VK_UP:
			       bU=false;
			       break;
			  case KeyEvent.VK_RIGHT:
			      bR=false;
			       break;
			  case KeyEvent.VK_DOWN:
                  bD=false;
			       break;
			}
		  locateDirection();
		}
		public  Missile fire() {
			int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
			int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		   Missile m=new Missile(x,y,dir);
		   return m;
		}
	
}
 


























