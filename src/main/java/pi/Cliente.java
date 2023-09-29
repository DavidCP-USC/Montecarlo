package pi;
import java.rmi.*;
import java.util.ArrayList;
import java.net.MalformedURLException;

public class Cliente extends Thread{
    static Integer[] divisionTrabajo = null;
    public Cliente() {
        super();
    }

    public static void main(String[] args) {
        /*Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el numero de servidores: ");
        int numServidores = scanner.nextInt();
        System.out.print("Ingrese la cantidad de puntos a generar: ");
        int n = scanner.nextInt();
        scanner.close();
        */
        int numServidores = 1;
        int n = 10;
        
        // Dividimos el trabajo entre los servidores
        divisionTrabajo = new Integer[numServidores];
        Integer iteracionesExtra = n%numServidores;
        for (int i = 0; i < numServidores; i++){
            divisionTrabajo[i] = n/numServidores;
        }

        // En el caso de que la division no sea exacta, se le asigna
        // un punto mas a los primeros servidores
        for (int i = 0; i < iteracionesExtra; i++){
                divisionTrabajo[i] += 1;
        }

        // Iniciamos los servidores y creamos los hilos clientes que 
        // los escucharán

        // Iniciamos los servidores
        Cliente[] clientes = new Cliente[numServidores];
        Servidor[] servidores = new Servidor[numServidores];
        
            for (int i = 0; i < numServidores; i++){
                servidores[i] = new Servidor("Servidor " + i);
                servidores[i].start();
            }
        try{
            for (Servidor s : servidores){
                s.join();
            }
        } catch (InterruptedException e) {
                e.getMessage();
        }

        // Iniciamos los clientes
        for (int i = 0; i < numServidores; i++){
            clientes[i] = new Cliente();
            clientes[i].start();
        }
        // Esperamos a que los clientes terminen
        try {
            for (Cliente c: clientes){
            c.join();
        }
        } catch (InterruptedException e) {
            System.out.println("Error al esperar a los hilos");
        }
        System.out.println("Hilo principal terminado");
    }
    @Override
    public void run(){
        int puertoRMI = 6789;
        String URLRegistro = "rmi://localhost:" + puertoRMI + "/interfaz";
        
        try {
            Interfaz objetoRemoto = (Interfaz) Naming.lookup(URLRegistro);
            System.out.println("Lookup completado");
            // Invocamos el metodo remoto
            ArrayList<Float> lista = objetoRemoto.crearPares(divisionTrabajo[0]);
            System.out.println("El servidor ha devuelto: ");
            for (int i = 0; i < lista.size(); i++){
                System.out.println(lista.get(i));
            }
        } catch (RemoteException e) {
            e.getMessage();
        } catch (NotBoundException e) {
            e.getMessage();
        } catch (MalformedURLException e) {
            e.getMessage();
        }
        System.out.println("Hilo cliente terminado");

    }
}





