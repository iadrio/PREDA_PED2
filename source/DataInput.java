import java.util.*;
import java.io.*;
/**
 * Write a description of class DataInput here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DataInput
{
    private File fichero;
    private List<String> lineas;
    private Scanner scanner;
    private Scanner lector;
   

    /**
     * Constructor for objects of class DataInput
     */
    public DataInput (String ruta) throws Exception
    {
        fichero=new File(ruta);
        lineas=new ArrayList();
        
        try{
            scanner = new Scanner(fichero);
        }catch(IOException e){
            lector=new Scanner(System.in);
            throw new Exception("No existe el fichero de datos");
        }   
        
        readLines();
    }
    
    public DataInput ()
    {      
        lector=new Scanner(System.in);
    }
       
    
    public void readLines() throws Exception{
        int i=0;
        while(scanner.hasNextLine()){
            lineas.add(scanner.nextLine());
            i++;
        }
        
        if(i>2){
            throw new Exception("El archivo contiene más de 2 lineas");
        }
    }    
    
    public String getLine(int i){
        return lineas.get(i);
    }
       
    public String getInicio() throws Exception{
        Scanner scanner2=new Scanner(getLine(0));
        String inicio=scanner2.next();
        if(scanner2.hasNext()){
            throw new Exception("La primera línea contiene más de una palabra");
        }
        return inicio;          
    }
    
    public String getFin() throws Exception{
        Scanner scanner2=new Scanner(getLine(1));
        String inicio=scanner2.next();
        if(scanner2.hasNext()){
            throw new Exception("La segunda línea contiene más de una palabra");
        }
        return inicio;           
    }
    
    public int getM() throws Exception{
        int m=0;
        int i=0;
        Scanner scanner2=new Scanner(getLine(1));
        scanner2.useDelimiter("\\s");
        try{        
        m=scanner2.nextInt();
        }catch(Exception e){
            throw new Exception("El archivo de datos no es correcto: La segunda linea indica el valor del parametro m. Debe estar formada solo por un entero. Ejemplo: 10");
        }
        
        while(scanner2.hasNext()){
            i++;
            scanner2.next();
        }
        if(i!=0){
            throw new Exception("El archivo de datos no es correcto: La segunda linea indica el valor del parametro m. Debe estar formada solo por un entero. Ejemplo: 10");
        }
                
        return m;
    }
}
