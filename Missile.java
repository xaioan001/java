import java.awt.*;
import java.util.List;
public class Missile {
	
	public static final int XSPEED=20;
	public static final int YSPEED=20;
	
	
	public static final int WIDTH=10;
	public static final int HEIGHT=10;
	
	int x,y;
    Tank.Direction dir;
    
    private boolean good;
 
    private boolean Live=true;
	  

    private boolean live=true;
	 
    private TankClient tc;

    public Missile(int x,int y,Tank.Direction dir) {
    	this.x=x;
    	this.y=y;
    	this.dir=dir;
    }
    
    public Missile (int x,int y,boolean good,Tank.Direction dir,TankClient tc) {
    	     this(x,y,dir);
    	    this.tc=tc;
    	    this.good=good; 
    }
	
	
	public void draw(Graphics g) {
		//�ڻ�̹��֮ǰ�ж�̹���Ƿ����
		if(!live) {
			tc.missiles.remove(this);
			
			return;
		}
		
		Color c=g.getColor();
		g.setColor(Color.BLACK);//�ӵ�����ɫ
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		
		move();
	}
     
    private void move() {
    	
    	switch(dir) {
		case L:                //����
			x-=XSPEED;
			break;
		case LU:               //����
			x-=XSPEED;
			y-=YSPEED;
			break;
		case U:              //����
			y-=XSPEED;
			break;
		case RU:             //����
			x+=XSPEED;
			y-=YSPEED;
			break;
		case R:               //����
			x+=XSPEED;
			break;
		case RD:              //����
			x+=XSPEED;
			y+=YSPEED;
			break;
		case  D:                  //����
			y+=YSPEED;
			break;
		case LD:
			x-=XSPEED;
		    y+=YSPEED;
		    break;
		case STOP:
			break;
		
		}
    	//�����ж�     
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
    
    public boolean hitTank(Tank t) {//����õ���װ���ӵ���Χ�ķſ����Rectangle
    	if(this.live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood()) {//��֤�Լ�̹�˻���
        t.setLive(false);//�����Ƿ񱻴��
        this.live=false;
        Explode e=new Explode(x,y,tc);//�ӵ��ģ�x��y
        tc.explode.add(e);
    	return true;//������Ϸ���true
    	}//���򷵻�false
    	return false;
    }
    public boolean hitTanks(List<Tank> tanks) {
    	for(int i=0;i<tanks.size();i++) {
    		if(hitTank(tanks.get(i))) {
    			return true;
    		}
    	}
    	return false;
    }
     
}






















