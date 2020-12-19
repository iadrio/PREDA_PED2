public class Inicio
{
    public static void main(String args[])
    {
        int i =0;
        boolean argH=false;
        boolean argT=false;
        String log,datos;
        
        for(i=0;i<args.length;i++){
            if(args[i].toLowerCase().equals("-t")){
                argT=true;                
            }
            if(args[i].toLowerCase().equals("-h")){
                argH=true;
            }
        }  
        i=0;
        if(argH==true){
            i++;
        }
        
        if(argT==true){
            i++;
        }
        
        datos=args[i];
        i++;
        
        if(args.length>i){
            log=args[i];
        }else{
            log="-1";
        }       
        
        if(argH){
            System.out.println("SINTAXIS:");
            System.out.println("java suma [-t] [-h] [fichero_entrada] [fichero_salida]");
            System.out.println("-t                     Traza la selección de subconjuntos");
            System.out.println("-h                     Muestra esta ayuda");
            System.out.println("fichero_entrada        Nombre del fichero de entrada");
            System.out.println("fichero_salida         Nombre del fichero de salida");
        }
        
        try{
            AlgoritmoDinamico algoritmo = new AlgoritmoDinamico(datos,log,argT);
            algoritmo.fillMatrix();
            algoritmo.getOperations();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
