import java.util.*;

public class Clipping_main
{
    public static byte A[]=new byte[2];
    public static byte B[]=new byte[2];
    public static byte CodeA[]=new byte[4];
    public static byte CodeB[]=new byte[4];
    public static byte Xmin=0,Xmax=0,Ymin=0,Ymax=0;
    public static double M=0.0; 
    public static void main(String args[]){
       Scanner Read = new Scanner(System.in);
       
       //A(x,y)
       System.out.println("Enter A");
        System.out.print("Enter X ==> ");
         A[0]=Read.nextByte ();
        System.out.print("Enter Y ==> ");
         A[1]=Read.nextByte();
       //B(x,y)  
       System.out.println("Enter B");
        System.out.print("Enter X ==> ");
         B[0]=Read.nextByte ();
        System.out.print("Enter Y ==> ");
         B[1]=Read.nextByte();
       //Xmax,Xmin  
       System.out.println("Enter Xmax and Xmin");
        System.out.print("Enter Xmin ==> ");
         Xmin=Read.nextByte();
        System.out.print("Enter Xmax ==> ");
         Xmax=Read.nextByte();
       //Ymax,Ymin  
       System.out.println("Enter Ymax and Ymin");
        System.out.print("Enter Ymin ==> ");
         Ymin=Read.nextByte();
        System.out.print("Enter Ymax ==> ");
         Ymax=Read.nextByte();
         
       //M
       M=M();  
       System.out.println("M = "+M);
       
       //Codes A,B
       CodeA=Code(A);
       CodeB=Code(B);
       
       // print codes A,B
       System.out.print("Code A = ");
       for(int i=0;i<4;i++)
         System.out.print(CodeA[i]);
       System.out.print("\n");
       System.out.print("Code B = ");
       for(int i=0;i<4;i++)
         System.out.print(CodeB[i]);
       System.out.println("\n");
       
       //Testing
       Testing(CodeA,CodeB);
       //Clipping
       aginClipping();
    }
    
    public static double M(){
       double m1 =B[1]-A[1];
       double m2 =B[0]-A[0];
       return(m1/m2);
    }
    
    public static byte[] Code(byte C[]){//ABRL C (x,y)
       byte Code[]=new byte[4];
       
        if(C[0]<Xmin)//L
        {
          if(C[1]<Ymin)//LB 0101
          {
            Code[0]=0;
            Code[1]=1;
            Code[2]=0;
            Code[3]=1;
            
          }
          else
           if(C[1]>Ymax)//LA 1001
           {
            Code[0]=1;
            Code[1]=0;
            Code[2]=0;
            Code[3]=1;
            
           }
           else//L 0001
           {
            Code[0]=0;
            Code[1]=0;
            Code[2]=0;
            Code[3]=1;
            
           }
       }
       else
        if(C[0]>Xmax)//R
        {
           if(C[1]<Ymin)//RB 0110
          {
            Code[0]=0;
            Code[1]=1;
            Code[2]=1;
            Code[3]=0;
            
          }
          else
           if(C[1]>Ymax)//RA 1010
           {
            Code[0]=1;
            Code[1]=0;
            Code[2]=1;
            Code[3]=0;
           
           }
           else//R 0010
           {
            Code[0]=0;
            Code[1]=0;
            Code[2]=1;
            Code[3]=0;
           
           }
       }
       else
       if(C[1]<Ymin)//B 0100
       {
          Code[0]=0;
          Code[1]=1;
          Code[2]=0;
          Code[3]=0;
          
       }
       else
       if(C[1]>Ymax)//A 1000
       {
          Code[0]=1;
          Code[1]=0;
          Code[2]=0;
          Code[3]=0;
          
       }
       else
       {
          Code[0]=0;
          Code[1]=0;
          Code[2]=0;
          Code[3]=0;
            
       }
       return(Code);
    } 
    
    public static boolean Testing_And(byte t1[],byte t2[]) { 
       boolean F_and=true; //clipping
       for(int i=0;i<4;i++)
         if((t1[i]==1)&&(t2[i]==1))
         {
            F_and=false;//invisible
         }
       return (F_and);
    } 
    
    public static boolean Testing_OR(byte t1[],byte t2[]) { 
       boolean F_OR=true;//in window
       for(int i=0;i<4;i++)
         if((t1[i]!=0)||(t2[i]!=0))
         {
            F_OR=false;//clipping
         }
       return (F_OR);
    }
    
    public static void Testing(byte t1[],byte t2[]) { 
       boolean F_and,F_OR;
       F_and=Testing_And(t1,t2);
       F_OR=Testing_OR(t1,t2);
       if(F_and==false)
       {
         System.out.println("Invisible");
       }
       else
       {
          if(F_OR==false)
          {
             System.out.println("Clipping");//i++
          }
          else
          {
             System.out.println("In window visible");
          }
        }
    } 
    
    public static void aginClipping(){
       byte clipArrayA[]=new byte[2];
       byte CodeclipA[]=new byte[4];
       byte clipArrayB[]=new byte[2];
       byte CodeclipB[]=new byte[4];
       clipArrayA=A;
       CodeclipA=CodeA;
       clipArrayB=B;
       CodeclipB=CodeB;
       //Testing and Clipping A
       while(true)
       {
          if(Clipping(CodeclipA)==false)
          { 
             clipArrayA=Clip(CodeclipA,clipArrayA);
             CodeclipA=Code(clipArrayA);
             Testing(CodeclipA,CodeclipB);
             System.out.println("A");
             Print(clipArrayA,CodeclipA,1);
          }
          else
            break;
       }
       
       //Testing and Clipping B
       while(true)
       {
          if(Clipping(CodeclipB)==false)
          { 
            clipArrayB=Clip(CodeclipB,clipArrayB);
            CodeclipB=Code(clipArrayB);
            Testing(CodeclipA,CodeclipB);
            System.out.println("B");
            Print(clipArrayB,CodeclipB,1);
          }
          else
            break;
       }
    }
    
    public static boolean Clipping(byte code[]) { 
       byte window[]=new byte[4]; 
       window[0]=0;
       window[1]=0;
       window[2]=0;
       window[3]=0;
       
       return(Check(code,window));
    } 
    
    public static byte[] LR(byte C[],byte AA[],byte key) { 
       byte newArray[]=new byte[2];double V=0.0,v1=0.0,v2=0.0,v3=0.0;;
       newArray[0]=key;
       v1=newArray[0]-AA[0];
       v2=v1*M;
       V=AA[1]+v2;
       v3=Math.round(V);
       newArray[1]=(byte) v3;
       return(newArray);
    } 
    
    public static byte[] AB(byte C[],byte AA[],byte key) { 
       byte newArray[]=new byte[2];double V=0.0,v1=0.0,v2=0.0,v3=0.0;
       newArray[1]=key;
       v1=newArray[1]-AA[1];
       v2=v1/M;
       V=AA[0]+v2;
       v3=Math.round(V);
       newArray[0]=(byte) v3;
       return(newArray);
    } 
    
    public static boolean Check(byte Array1[],byte Array2[]) { 
       boolean F=false,fina=true;
       boolean CheckArray[]=new boolean[4];
       for(int i=0;i<4;i++)
         if(Array1[i]==Array2[i])
         {
             F=true;//yes
             CheckArray[i]=F;
         }
       for(int i=0;i<4;i++)
         if(CheckArray[i]==false)
         {
           fina=false;
         }
       return(fina);
    } 
    
    public static byte[] Clip(byte C[],byte AA[]) { //C Code,AA array (new)
       byte newArray1[]=new byte[2];
       byte A[] = {1, 0, 0, 0};
       byte B[] = {0, 1, 0, 0};
       byte R[] = {0, 0, 1, 0};
       byte L[] = {0, 0, 0, 1};
       byte RA[] = {1, 0, 1, 0};
       byte RB[] = {0, 1, 1, 0};
       byte LA[] = {1, 0, 0, 1};
       byte LB[] = {0, 1, 0, 1};
      
       
       if(Check(C,L)==true)
       {
          newArray1=LR(C,AA,Xmin);
       }
       else
        if(Check(C,LA)==true)
        {
          newArray1=LR(C,AA,Xmin);
        }
       else
        if(Check(C,LB)==true)
        {
          newArray1=LR(C,AA,Xmin);
        }
       else
        if(Check(C,R)==true)
        {
          newArray1=LR(C,AA,Xmax);
        }
       else
        if(Check(C,RA)==true)
        {
          newArray1=LR(C,AA,Xmax);
        }
       else
        if(Check(C,RB)==true)
        {
          newArray1=LR(C,AA,Xmax);
        }
       else
        if(Check(C,B)==true)
        {
          newArray1=AB(C,AA,Ymin);
        }
       else
        if(Check(C,A)==true)
        {
          newArray1=AB(C,AA,Ymax);
        }

       return(newArray1);
    } 
     
    public static void Print(byte Array[],byte code[],int o) { //o is any array?
       System.out.println("Array X="+Array[0]+"    Y="+Array[1]);
       System.out.print("Code = ");
       for(int i=0;i<4;i++)
         System.out.print(code[i]);
    }
    
}
