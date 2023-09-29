package pi;
import java.rmi.*;
import java.util.ArrayList;
import java.net.MalformedURLException;

public class Cliente extends Thread{
    public static ArrayList<Integer> divisionTrabajo = new ArrayList<>();
    public static ArrayList<Float> resultado = new ArrayList<>();
    public static int numServidores = 0;
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
        numServidores = 5;
        int n = 1000000;
        
        // Dividimos el trabajo entre los servidores
        Integer iteracionesExtra = n%numServidores;
        for (int i = 0; i < numServidores; i++){
            divisionTrabajo.add(n/numServidores);
        }

        // En el caso de que la division no sea exacta, se le asigna
        // un punto mas a los primeros servidores
        for (int i = 0; i < iteracionesExtra; i++){
            divisionTrabajo.set(i, divisionTrabajo.get(i) + 1);
        }

        // Iniciamos los servidores y creamos los hilos clientes que 
        // los escucharán

        // Iniciamos los servidores
        Servidor[] servidores = new Servidor[numServidores];
        
        for (int i = 0; i < numServidores; i++){
            servidores[i] = new Servidor(i);
            servidores[i].start();
        }

        // Esperamos a que los servidores terminen
        try {
            for (Servidor s: servidores){
                s.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Error al esperar a los hilos");
        }

        Cliente cliente = new Cliente();
        // Iniciamos los clientes
        cliente = new Cliente();
        cliente.start();
        // Esperamos a que los clientes terminen
        try {
            cliente.join();
        } catch (InterruptedException e) {
            System.out.println("Error al esperar a los hilos");
        }
        //imprimirResultado();
        System.out.println("Pi = " + (4.0*resultado.size()/2/n));
    }

    private static synchronized void imprimirResultado(){
        for (int i = 0; i < resultado.size(); i+=2){
            System.out.println("Punto valido " + (i/2 + 1) + ": (" + resultado.get(i) + ", " + resultado.get(i+1) + ")");
        }
    }

    private synchronized Integer trabajo(){
        Integer returnValue = new Integer(divisionTrabajo.get(0));
        divisionTrabajo.remove(0);
        return returnValue;
    }

    private synchronized void addToList(ArrayList<Float> x){
        for (Float f: x){
            resultado.add(f);
        }
    }
    @Override
    public void run(){
        int puertoRMI = 6789;
        String URLRegistro = new String();
        int trabajo = 0;
        try {
            for (int i = 0; i < numServidores; i++){
                URLRegistro = "rmi://localhost:" + puertoRMI + "/interfaz"+i;
                System.out.println("Buscando en: " + URLRegistro);
                Interfaz objetoRemoto = (Interfaz) Naming.lookup(URLRegistro);
                // Invocamos el metodo remoto
                trabajo = trabajo();
                System.out.println("Trabajo asignado: " + trabajo);
                addToList(objetoRemoto.crearPares(trabajo));
            }
        } catch (RemoteException e) {
            e.getMessage();
        } catch (NotBoundException e) {
            e.getMessage();
        } catch (MalformedURLException e) {
            e.getMessage();
        }
    }
}





