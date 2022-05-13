import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.*;

public class Tank{
	public static final int XSPEED=10;
	public static final int YSPEED=10;
	
	public static final int WIDTH=30;
	public static final int HEIGHT=30;
	
	public boolean live=true;//定义坦克的死活
	
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	TankClient tc; 
	
	private boolean good;
	
	private int x,y;
	
	private boolean bL=false,bU=false,bR=false,bD=false;
	enum Direction{L,LU,U,RU,R,RD,D,LD,STOP};
	
	//子弹方向
	private Direction dir=Direction.STOP;
	
	//炮筒方向
	private Direction ptDir=Direction.D;	
	
	
	public Tank(int x,int y,boolean good) {

		this.x=x;
		this.y=y;
		this.good=good;
	}
 	   
     public Tank(int x,int y,boolean good,TankClient tc) {
	    	this(x,y,good);
	    	this.tc=tc;
	    }
 	    
	public void draw(Graphics g) {
		    if(!live)return ;//若坦克被打中，则不必再画坦克
			Color c=g.getColor();
			if(good) g.setColor(Color.WHITE);//自己控制的坦克
			else g.setColor(Color.BLUE);
			//敌方坦克的颜色
			//g.setColor(Color.RED);
			g.fillOval(x,y, WIDTH, HEIGHT);
			g.setColor(c);	
			switch(ptDir) {
			case L:                //中心
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT/2);
			     break;
			case LU:             //左上
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y);
			      break;
			case U:             
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y);
				 break;
			case RU:             
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y);
				 break;
			case R:               
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT/2);
				 break;
			case RD:              
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT);
				 break;
			case  D:                  
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT);
				 break;
			case LD:
				g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT);
				 break;
			
			}
			move();
		}
		
		void move() {
			switch(dir) {
			case L:                //向左
				x-=XSPEED;
				break;
			case LU:               //左上
				x-=XSPEED;
				y-=YSPEED;
				break;
			case U:              //向上
				y-=XSPEED;
				break;
			case RU:             //右上
				x+=XSPEED;
				y-=YSPEED;
				break;
			case R:               //向右
				x+=XSPEED;
				break;
			case RD:              //右下
				x+=XSPEED;
				y+=YSPEED;
				break;
			case  D:                  //向下
				y+=YSPEED;
				break;
			case LD:                  //左下
				x-=XSPEED;
			    y+=YSPEED;
			    break;
			case STOP:
				break;
			
			}
			if(this.dir!=Direction.STOP) {
				this.ptDir=this.dir;
			}
			if(x<0)x=0;
			if(y<30)y=30;
			//出墙位置
			if(x+Tank.WIDTH>TankClient.GAME_WIDTH) x=TankClient.GAME_WIDTH-Tank.WIDTH;
	        if(y+Tank.HEIGHT>TankClient.GAME_HEIGHT)y=TankClient.GAME_HEIGHT-Tank.HEIGHT; 	
		}
		public void Keypressed(KeyEvent e) {
			int key=e.getKeyCode();
		     switch(key){
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
		           fire();
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
		   Missile m=new Missile(x,y,ptDir,this.tc );
		   tc.missiles.add(m);
		   return m;
		}
		 public Rectangle getRect( ) {
		    	return new Rectangle(x,y,WIDTH,HEIGHT);
		    }
	
}
 


























