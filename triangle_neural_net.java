/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nuralnet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ce
 */
public class triangle_neural_net {
    
    
    public static final double e = Math.E;
    
    public static final double A = 5f;
    
    
    
    public static double w1[],w2[],w3[],wO[];
    
    
    public static double wc1[],wc2[],wc3[],wcO[];
    
    
    public static double h1[],h2[],h3[],out[];
    
    
    public static int a,b,c,Y;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       
        
        int j;
        

        
       Init(); 
        
      BufferedReader br = null;
      try { br = new BufferedReader(new FileReader("triDataBig.csv"));} catch(IOException e){System.out.println("error");}
      String line; String[] block;  
        
      
      double cost=0;
      
      
        int n =0;
        line=br.readLine();
        while(line!=null)
        {
            block = line.split(",");
            a = Integer.parseInt(block[0]);   b = Integer.parseInt(block[1]);   c = Integer.parseInt(block[2]);  Y = Integer.parseInt(block[3]);
            
            
            
            forward(a,b,c);
            cost+= (Y-out[1]) * (Y-out[1]) ;
            backward();
            
            
            
            
            
            
            line=br.readLine(); n++;
        }
        br.close();
    
        System.out.println(cost/n);
        
        
        for(int i=0;i<3;i++)
        {
            wc1[i]/= n ;
            wc2[i]/= n;
            wc3[i]/= n;
            wcO[i]/= n;
        }
    
       
        
        save();
    
    
   
    }
    
  
    

    public static void Init() throws IOException
    {
        
         w1 = new double[3];   ///////// for h1
         w2 = new double[3];  ////////// for h2
         w3 = new double[3];  ////////// for h3
         wO = new double[3]; 
         
         
         BufferedReader br = null;
      try { br = new BufferedReader(new FileReader("weights.txt"));} catch(IOException e){System.out.println("error");}
      String line; String[] block;  
      
      line=br.readLine();
      block=line.split(" ");
      w1[0]=Double.parseDouble(block[0]);
      w1[1]=Double.parseDouble(block[1]);
      w1[2]=Double.parseDouble(block[2]);
      
      line=br.readLine();
      block=line.split(" ");
      w2[0]=Double.parseDouble(block[0]);
      w2[1]=Double.parseDouble(block[1]);
      w2[2]=Double.parseDouble(block[2]);
      
      line=br.readLine();
      block=line.split(" ");
      w3[0]=Double.parseDouble(block[0]);
      w3[1]=Double.parseDouble(block[1]);
      w3[2]=Double.parseDouble(block[2]);
      
      line=br.readLine();
      block=line.split(" ");
      wO[0]=Double.parseDouble(block[0]);
      wO[1]=Double.parseDouble(block[1]);
      wO[2]=Double.parseDouble(block[2]);
      
      

         wc1 = new double[3];   ///////// change in weights for h1
         wc2 = new double[3];  ////////// change in weights for h2
         wc3 = new double[3];  ////////// change in weights for h3
         wcO = new double[3]; ////////// change in weights for output
        
        for(int i=0;i<3;i++)
        {
            wc1[i]= 0;
            wc2[i]= 0;
            wc3[i]= 0;
            wcO[i]= 0;
        }
        
        
         h1 = new double[2];   
         
         h2 = new double[2];  
         
         h3 = new double[2];  
         
         out = new double[2]; 
        
        
        
    }
    
    

    public static void forward(int a,int b,int c)
    {
        h1[0]= Net(w1,a,b,c);
        h2[0]= Net(w2,a,b,c);
        h3[0]= Net(w3,a,b,c);
        
        h1[1]=Sig(h1[0]);
        h2[1]=Sig(h2[0]);
        h3[1]=Sig(h3[0]);
        
        out[0] = Net(wO,h1[1],h2[1],h3[1]);
        
        out[1] = Sig(out[0]);
        
    }
    
      
    public static double Net(double w[], double a, double b, double c )
    {
        
        return  w[0]*a + w[1]*b + w[2]*c;
        
    }
    
    public static double Sig(double z)
    {
        return 1d/(1d+Math.pow(e, -z));
    }
    
    
    public static void backward()
    {
        
        double dlO =  (Y - out[1]) * out[1]*(1 - out[1]) ;    ////////// delta output
        
        
        
        wcO[0]  += A* dlO * h1[1];      /////////////  change in w41
        
        wcO[1] += A* dlO * h2[1];     ////////////////  change in w42
        
        wcO[2] += A* dlO * h3[1];     /////////////////  change in w43
        
        ////////////////////////////////////////////////////////////////////
        
        double dlh1 = dlO * wO[0] * h1[1] * (1d - h1[1]);        ///////// delta h1
                
        double dlh2 = dlO * wO[1] * h2[1] * (1d - h2[1]);       ////////////   delta h2
        
        double dlh3 = dlO * wO[2] * h3[1] * (1d - h3[1]);       /////////    delta h3
        
        
        
        wc1[0] += A*dlh1*a;
        wc1[1] += A*dlh1*b;
        wc1[2] += A*dlh1*c;
        
        wc2[0] += A*dlh2*a;
        wc2[1] += A*dlh2*b;
        wc2[2] += A*dlh2*c;
        
        wc3[0] += A*dlh3*a;
        wc3[1] += A*dlh3*b;
        wc3[2] += A*dlh3*c;
        
        
        
    }
    
    
    
 public static void save() throws IOException 
 {   
      for(int i=0;i<3;i++)
        {
            w1[i] = w1[i]+wc1[i] ;
            
            w2[i] = w2[i]+wc2[i] ;
            
            w3[i] = w3[i]+wc3[i] ;
            
            wO[i] = wO[i]+wcO[i] ;
        }
     
     
     
      File file = new File("weights.txt");
       BufferedWriter writer= null;
       writer=new BufferedWriter(new FileWriter(file));
       
       writer.write(w1[0]+" "+w1[1]+" "+w1[2]+"\n");
       writer.write(w2[0]+" "+w2[1]+" "+w2[2]+"\n");
       writer.write(w3[0]+" "+w3[1]+" "+w3[2]+"\n");
       writer.write(wO[0]+" "+wO[1]+" "+wO[2]+"\n");
       
       writer.close();
 } 
    
    
    
}
