package pi;
import java.rmi.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.MalformedURLException;

public class Cliente extends Thread{
    public static ArrayList<Integer> divisionTrabajo = new ArrayList<>();
    public static Integer numServidores = 1;
    public static Integer cantidadNumeros = 0;
    public static String ip;
    public static Integer puertoRMI = 6789;
    public Cliente() {
        super();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce la IP del servidor: ");
        ip = scanner.nextLine();
        System.out.println("Introduce el puerto del servidor: ");
        puertoRMI = scanner.nextInt();
        System.out.print("Ingrese el numero de servidores: ");
        numServidores = scanner.nextInt();
        System.out.print("Ingrese la cantidad de puntos a generar: ");
        int n = scanner.nextInt();
        scanner.close();
        
        // Dividimos el trabajo entre los servidores
        Integer iteracionesExtra = n % numServidores;
        for (int i = 0; i < numServidores; i++){
            divisionTrabajo.add(n/numServidores);
        }

        // En el caso de que la division no sea exacta, se le asigna
        // un punto mas a los primeros servidores
        for (int i = 0; i < iteracionesExtra; i++){
            divisionTrabajo.set(i, divisionTrabajo.get(i) + 1);
        }

        // Iniciamos el cliente
        Cliente[] clientes = new Cliente[numServidores];
        for (int i = 0; i < numServidores; i++){
            clientes[i] = new Cliente();
            clientes[i].start();
        }

        // Esperamos a que el cliente termine
        try {
            for (int i = 0; i < numServidores; i++){
                clientes[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("Error al esperar al hilo cliente");
        }
        System.out.println("Pi = " + (4.0*cantidadNumeros/n));
    }

    private synchronized Integer trabajo(){
        Integer returnValue = new Integer(divisionTrabajo.get(0));
        divisionTrabajo.remove(0);
        return returnValue;
    }

    private synchronized void addToResult(Integer x){
        cantidadNumeros += x;
    }
    @Override
    public void run(){
        System.setProperty("java.rmi.server.hostname", "rmi://" + ip + ":" + puertoRMI + "/interfaz");
        String URLRegistro = "rmi://" + ip + ":" + puertoRMI + "/interfaz";
        try {
            Interfaz objetoRemoto = (Interfaz) Naming.lookup(URLRegistro);
            // Obtenemos el trabajo a realizar e invocamos el metodo remoto
            addToResult(objetoRemoto.crearNumeros(trabajo()));
        } catch (RemoteException e) {
            e.getMessage();
        } catch (NotBoundException e) {
            e.getMessage();
        } catch (MalformedURLException e) {
            e.getMessage();
        }
    }
}





