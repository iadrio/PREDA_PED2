import java.util.*;
import java.io.*;
import java.lang.*;
/**
 * Write a description of class AlgoritmoDinamico here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AlgoritmoDinamico
{
    private String inicio;
    private String fin;
    private Matrix<Integer> matrix;
    private Matrix<String> operations;
    private LogWriter writer;
    private DataInput dataInput;
    private String rutaOut;
    private String rutaIn;
    private Boolean activo;
    /**
     * Constructor for objects of class AlgoritmoDinamico
     */
    public AlgoritmoDinamico(String rutaIn,String rutaOut,Boolean activo) throws Exception
    {
        this.rutaOut=rutaOut;
        this.rutaIn=rutaIn;
        this.activo=activo;
        
        try{
            dataInput= new DataInput(rutaIn);
            writer=new LogWriter(rutaOut,this.activo); 
            inicio=dataInput.getInicio();
            fin=dataInput.getFin();
            matrix=new Matrix(fin.length()+1,inicio.length()+1);
            operations=new Matrix(fin.length()+1,inicio.length()+1);
        } catch (Exception e){
            throw e;
        }                        
    }
    
    public int fill(int i, int j){   
        
        if(i==0){
            writer.write("i=0    =>    M("+i+","+j+")="+j);
            return j;
        }else{
            if(j==0){
                writer.write("j=0    =>    M("+i+","+j+")="+i);
                return i;
            }else{
                if(inicio.charAt(i-1)!=(fin.charAt(j-1))){
                    int z = Math.min(Math.min(matrix.get(i-1,j),matrix.get(i,j-1)),matrix.get(i-1,j-1)); 
                    writer.write(inicio.charAt(i-1)+"!="+(fin.charAt(j-1))+"    =>    M("+i+","+j+")=1+min(M("+(i-1)+","+j+"),M("+i+","+(j-1)+")),M("+(i-1)+","+(j-1)+"))="+(1+z));
                    return 1+z;
                }else{
                    writer.write(inicio.charAt(i-1)+"="+(fin.charAt(j-1))+"    =>    M("+i+","+j+")=min(M("+(i-1)+","+j+")+1,M("+i+","+(j-1)+")+1),M("+(i-1)+","+(j-1)+")");
                    return Math.min(Math.min(matrix.get(i-1,j)+1,matrix.get(i,j-1)+1),matrix.get(i-1,j-1));
                }
            }
        }
    }
                    
    
    public void fillMatrix(){
        writer.write("DATOS INICIALES");
        writer.write("Palabra inicial: "+inicio);
        writer.write("Palabra final: "+fin);
        writer.write("");
        writer.write("RELLENAMOS MATRIZ M");
        writer.write("");        
        writer.write("");
        int i=0;        
        while(i<inicio.length()+1){
            int j=0;
            while(j<fin.length()+1){
                matrix.add(i,j,fill(i,j));
                j++;
                printMatrix(matrix);
                writer.write("");
            }
            i++;
        }        
        writer.write("");
        writer.write("");
        writer.write("");
    }
    
    public void printMatrix(Matrix matrix){
        int i=0;        
        while(i<inicio.length()+1){
            int j=0;
            ArrayList row = new ArrayList();
            while(j<fin.length()+1){
                row.add(matrix.get(i,j));
                j++;
            }
            writer.write(row.toString());
            i++;
        }
    }
    
    public int getResult(){
        return matrix.get(fin.length()+1,inicio.length()+1);
    }    
    
    public void getOperations(){
        writer.write("BUSCAMOS SOLUCIONES");
        writer.write("");
        writer.write("");
        int i = inicio.length();
        int j = fin.length();

        Solucion solucion= new Solucion();
        ArrayList<Solucion> soluciones= new ArrayList();                      
        getOperationsAux(i,j,solucion,soluciones);
        writer.write("BUSQUEDA FINALIZADA");
        writer.write("");
        writer.setActive(true);
        writer.write("__________________________________________________________________________________________________________________________________");
        writer.write("DATOS INICIALES");
        writer.write("Palabra inicial: "+inicio);
        writer.write("Palabra final: "+fin);
        writer.write("");
        writer.write("SOLUCIONES");
        printer(soluciones);
        writer.write("__________________________________________________________________________________________________________________________________");
        writer.close();
    }
    
    public ArrayList<Solucion>  getOperationsAux(Integer i, Integer j,Solucion sol, ArrayList<Solucion> soluciones){
       Solucion solucion= new Solucion(sol);;
       String palabra ="";
              
       
       if(i==0){
           soluciones.add(solucion);
           writer.write("Nueva solución encontrada");
           writer.write(soluciones.size()+" soluciones almacenadas hasta el momento:");
           writer.write("");
           printer(soluciones);
       }else{
           writer.write("");
           writer.write("");
           writer.write("Posición actual: "+"i="+i+"  j="+j);
           writer.write("Analizando la solucion:");
           solPrinter(sol);
           writer.write("");
       
           if(matrix.get(i,j)==(matrix.get(i-1,j))+1){
               palabra=new String(fin.substring(0,j)+inicio.substring(i,inicio.length()));              
               solucion.push("borrado "+i.toString(),palabra); 
               writer.write("probamos realizando la operación:");
               writer.write(solucion.peek());
               soluciones=getOperationsAux(i-1,j,solucion,soluciones);
               solucion.pop();
           }
           
           if(j!=0){
               if(matrix.get(i,j)==(matrix.get(i,j-1))+1){
                   palabra=new String(fin.substring(0,j)+inicio.substring(i,inicio.length()));
                   solucion.push("insercion "+j.toString(),palabra); 
                   writer.write("probamos operación:");
                   writer.write(solucion.peek());
                   soluciones=getOperationsAux(i,j-1,solucion,soluciones);
                   solucion.pop();
               }               
               
               if(matrix.get(i,j)==(matrix.get(i-1,j-1))){                  
                   if(inicio.charAt(i-1)==fin.charAt(j-1)){
                       writer.write("M("+i+","+j+")=(M("+(i-1)+","+(j-1)+")    =>    avanzamos en la matriz M sin hacer nada");
                       soluciones=getOperationsAux(i-1,j-1,solucion,soluciones);                       
                   }                    
               }
               
               if(matrix.get(i,j)==(matrix.get(i-1,j-1)+1)){
                   palabra=new String(fin.substring(0,j)+inicio.substring(i,inicio.length()));
                   solucion.push("sustitucion "+j.toString(),palabra);
                   writer.write("probamos operación:");
                   writer.write(solucion.peek());
                   soluciones=getOperationsAux(i-1,j-1,solucion,soluciones);
                   solucion.pop();
               }
           }
           
       }
       
       writer.write("Esta solución no es valida, retrocedemos en la matriz y probamos con otra operación");
       return soluciones;
    }
       
    public void printer(ArrayList<Solucion> sol){  
        int n=1;
        for(Solucion solucion:sol){
            writer.write("Solucion número "+n);
            writer.write(matrix.get(inicio.length(),fin.length()).toString());
            solPrinter(solucion);
            writer.write("");
            n++;
        }
    }
    
    public void solPrinter(Solucion sol){
        ArrayList<String> lista = sol.toArrayList();
       
        for(String s:lista){
            writer.write(s);
        }
    }
}
