import java.awt.*;

public class Missile {
	
	public static final int XSPEED=20;
	public static final int YSPEED=20;
	
	
	public static final int WIDTH=10;
	public static final int HEIGHT=10;
	
	int x,y;
    Tank.Direction dir;
 
    private boolean Live=true;
	  

    private boolean live=true;
	 
    private TankClient tc;

	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
    
    public Missile (int x,int y,Tank.Direction dir,TankClient tc) {
    	this(x,y,dir);
    	this.tc=tc;
    }
	
	
	public void draw(Graphics g) {
		
		//在画坦克之前判断坦克是否存在
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		
		Color c=g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		
		move();
	}
    
    private void move() {
    	
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
		case LD:
			x-=XSPEED;
		    y+=YSPEED;
		    break;
		case STOP:
			break;
		
		}
    	//出界判断     
    	if(x < 0|| y < 0 || x > TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGHT) 
    	{
    		Live=false;
    		
    	}
    } 
    
    public boolean isLive() {
		return Live;
	} 
    
    public Rectangle getRect( ) {
    	return new Rectangle(x,y,WIDTH,HEIGHT);
    }
    
    public boolean hitTank(Tank t) {//如果拿到包装到子弹外围的放块调用Rectangle
    	if(this.getRect().intersects(t.getRect())&&t.isLive()) {//保证自己坦克活着
        t.setLive(false);//检验是否被打掉
        this.live=false;
    	return true;//如果交上返回true
    	}//负责返回else
    	return false;
    }
     
}






















