/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makedataset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ce
 */
public class MakeDataSet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        
        
          BufferedWriter writer= null;
          
          
       File file = new File("triData.csv");
       
       
       
       writer=new BufferedWriter(new FileWriter(file));
       
       for(int i = 0; i<10000;i++)           
       {
           
           int a = (int) (3+ 12f*Math.random());
           int b = (int) (3+ 12f*Math.random());
           int c = (int) (3+ 12f*Math.random());
           
           int label =IsTriangle(a,b,c);
           
           System.out.println(a+","+b+","+c+","+label);
           writer.write(a+","+b+","+c+","+label+"\n");
       }
       
               
               
       
       
       
       writer.close();
        
        
        
    }
    
    
    
    
    public static int IsTriangle( int a, int b, int c)
    {
        
        if((a+b)>c)
            if((a+c)>b)
                if((b+c)>a)
                    return 1;
        
        
        return 0;
    }
    
    
    
}
