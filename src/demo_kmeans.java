import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class demo_kmeans {

	public static double distance(float[]xy1,float[]xy2) {
		double distance=0;
		distance=Math.sqrt(Math.pow((xy1[0]-xy2[0]),2)+Math.pow((xy1[1]-xy2[1]),2));
		return distance;
	}
	public static int mini(Vector<Double> distances) {
		int i=0;
		double min=distances.get(0);
		 for(i=0;i<distances.size();i++){
			 if(distances.get(i)<min){
			  min=distances.get(i);
			 }
		}
		return IndexOf(min,distances); 
	}
	 public static int IndexOf(double min, Vector<Double> distances) {
	      int i=0;
	      for(i=0;i<distances.size();i++) {
	    	 if(min==distances.get(i))break; 
	      }
	      return i;
	    }
	public static boolean isEqual(Vector<float[]> c,Vector<float[]> t,int n)
	{
	int time=0;
	
	for(int i=0;i<n;i++)
		if(c.get(i)[0]==t.get(i)[0]&& c.get(i)[1]==t.get(i)[1])time++;
		
	if(time==n)return true;			
	else return false;
	
	}
	public static void main(String[] args) {
		 //vectors
		Vector<float[]>list=new Vector<float[]>();
		

		//read file
		try {
				File f=new File("src/read.in.txt");
			      FileReader fr=new FileReader(f);
			      BufferedReader br=new BufferedReader(fr);
			      String line;
			      while ((line=br.readLine()) != null) {
			    	
			    	float []node1=new float[2];
			  		float x=0;float y=0;
			    	String[] split_line = line.split(",");
			    	x=Float.parseFloat(split_line[0]);
			    	y=Float.parseFloat(split_line[1]);
					node1[0]=x;
					node1[1]=y;
					list.add(node1);//set up list
			        }
			}
		catch (IOException e) {System.out.println(e);}
		for(int i=0;i<list.size();i++)
			System.out.println(list.get(i)[0]+","+list.get(i)[1]);
		
		//the cluster number
		int n=3;
		
		//group
		Vector<Vector<float[]>> g=new Vector<Vector<float[]>>();
		
		//set up c
		Vector<float[]> c=new Vector<float[]>();
		for(int i=0;i<n;i++)
			{
			c.add(list.get(i));
			System.out.println("c"+i+":"+c.get(i)[0]+","+c.get(i)[1]);
			}
		
		//set up t;
		Vector<float[]> t=new Vector<float[]>();
		float []tt= {30,10};
		for(int i=0;i<n;i++)t.add(tt);

		while(!isEqual(c,t,n)) {
			
			//set up
			g.clear();
			for(int i=0;i<n;i++) {
				t.set(i,c.get(i));
				g.add(new Vector());
			}	
		
			//divide
			for(int j=0;j<list.size();j++)//list j
			{
				Vector<Double> distances=new Vector<Double>();
				for(int i=0;i<n;i++) //group i
				{
					double d=distance(c.get(i),list.get(j));
					distances.add(d);
				}
				((Vector) g.get(mini(distances))).add(list.get(j));
				distances.clear();
			}

		//算出一個group的平均值，並存入c
		for(int group_num=0;group_num<n;group_num++) 
		{
			float X=0;float Y=0;
			for(int i=0;i<g.get(group_num).size();i++) {
				X+=g.get(group_num).get(i)[0];
				Y+=g.get(group_num).get(i)[1];
			}
			X/=g.get(group_num).size();
			Y/=g.get(group_num).size();
			float []XY= {X,Y};
			c.set(group_num, XY);
		}
	}
		//show groups

		for(int group_num=0;group_num<n;group_num++) 
		{
			System.out.println("group "+(group_num+1));
			for(int i=0;i<g.get(group_num).size();i++) {
			System.out.println("<x,y> "+g.get(group_num).get(i)[0]+","+g.get(group_num).get(i)[1]);
			}
		}
	}
}
