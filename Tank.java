import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.*;

public class Tank{
	public static final int XSPEED=10;
	public static final int YSPEED=10;
	
	public static final int WIDTH=30;
	public static final int HEIGHT=30;
	
	public boolean live=true;//定义坦克的死活
	
	private BloodBar bb=new BloodBar();
	
	private int life=100;
	
 	public int getLife() {
		return life;
	}



	public void setLife(int life) {
		this.life = life;
	}



	public boolean isLive() {
		return live;
	}

	
	
	public void setLive(boolean live) {
		this.live = live;
	}
	TankClient tc; 
	
	private boolean good;
	
	public boolean isGood() {
		return good;
	}
	private int x,y;
	private int oldX,oldY;
	
	private static Random r=new Random();//随机数产生  每个坦克都用
	
	private boolean bL=false,bU=false,bR=false,bD=false;
	enum Direction{L,LU,U,RU,R,RD,D,LD,STOP};//枚举方向
	
	//子弹方向
	private Direction dir=Direction.STOP;
	
	//炮筒方向
	private Direction ptDir=Direction.D;
	
	private int step=r.nextInt(12)+3;//最小值移动3步最大移动12步
	
	
	public Tank(int x,int y,boolean good) {
		  this.x=x;
		  this.y=y;
		  this.oldX=x;
		  this.oldY=y;
		  this.good=good;
		

		
	}
 	   
     public Tank(int x,int y,boolean good,Direction dir,TankClient tc) {
	    	this(x,y,good);
	    	this.dir=dir;
	    	this.tc=tc;
	    }
 	    
	public void draw(Graphics g) {
		    if(!live){//若坦克被打中，则不必再画坦克
		             if(!good) {
		    	        tc.tanks.remove(this);
		       }
		       return;
		    }
		    
		    
			Color c=g.getColor();
			if(good) g.setColor(Color.RED);//自己控制的坦克
			else g.setColor(Color.BLUE);
			//敌方坦克的颜色
			//g.setColor(Color.BLUE);//自己坦克颜色
			g.fillOval(x,y, WIDTH, HEIGHT);
			g.setColor(c);	
			
			bb.draw(g);
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
			
			
			
			  this.oldX=x;//记录坦克上一部位置
			  this.oldY=y;
			
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
		
		    if(!good) {//坏坦克每走一步随机产生一个方向
		    	Direction[] dirs=Direction.values(); //转换成一个数组
		    	if(step==0) {
		    		step=r.nextInt(12)+3;
		    		int rn=r.nextInt(dirs.length);//若当前是七，随机产生一个0~6的数
			    	dir=dirs[rn];//随机从0~6取一个选择方向
		    	}
		    	
		    	
		    	step --;//每移动一次减一
		    	if(r.nextInt(20)>10)this.fire();
		    }
		 
		    
		    
		}
		
		 
		private void stay() {
			x=oldX;
			y=oldY;
		}
		public void Keypressed(KeyEvent e) {
			int key=e.getKeyCode();
		     switch(key){
		    case KeyEvent.VK_F2:
		    	if(this.live) {
		    		this.live=true;
		    		this.life=100;
		    	}
		    	break;
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
			  case KeyEvent.VK_A:
			        superFire();
			        break;
			}
		  locateDirection();
		}
		public  Missile fire() {
			if(!live)return null; 
			int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
			int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		   Missile m=new Missile(x,y,good,ptDir,this.tc );
		   tc.missiles.add(m);
		   return m;
		}
		public Missile fire(Direction dir){//写一个fire方法
			if(!live)return null; 
			int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
			int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		   Missile m=new Missile(x,y,good,dir,this.tc );
		   tc.missiles.add(m);
		   return m; 
		
		}
		 public Rectangle getRect( ) {
		    	return new Rectangle(x,y,WIDTH,HEIGHT);
		    }
		public boolean collidesWithWall(Wall w) {
			if(this.live&&this.getRect().intersects(w.getRect())) {
	    		this.stay();
	    		return true;
	    	}
	    	return false;
		}
		 
	    public boolean collidesWithTanks(java.util.List<Tank> tanks) {
	    	for(int i=0;i<tanks.size();i++) {
	    		Tank t=tanks.get(i);
	    		if(this!=t) {
	    			if(this.live && t.isLive()&&this.getRect().intersects(t.getRect())) {
	    	    		this.stay();
	    	    		t.stay();
	    	    		return true;
	    		}
	    	}
	    }
	    	return false;
	 }
	    private void superFire() {
	    	Direction[] dirs=Direction.values(); 
	    	for(int i=0;i<8;i++){//8个方向
	    	    tc.missiles.add(fire(dirs[i]));
	    	}
	    		
	    }
	   private class BloodBar{
		   public void draw(Graphics g){
 	         Color c =g.getColor();
		         g.setColor(Color.RED);
		       g.drawRect(x,y-20,WIDTH,20);//血条的长宽高
			  int w=WIDTH*life/100;//宽度
			  g.fillRect(x, y-20,w,20);
			  g.setColor(c);
		   }
	   }
      public boolean eat(Blood b) {
    	  if(this.live&&b.isLive()&&this.getRect().intersects(b.getRect())){
      		this.life=20;//吃了就加1/5血
      		b.setLive(false);
      		return true;
      	}
      	return false;
      }
}
 


























