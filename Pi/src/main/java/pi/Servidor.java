package pi;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Servidor extends Thread{
    public Servidor() {
        super();
    }

    public static void main(String[] args){
        Servidor servidor = new Servidor();
        servidor.start();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce la IP del servidor: ");
        String ip = scanner.nextLine();
        System.out.println("Introduce el puerto del servidor: ");
        Integer puertoRMI = scanner.nextInt();
        scanner.close();
        String URLRegistro = "rmi://" + ip +":" + puertoRMI + "/interfaz";
        try {
            startRegistry(puertoRMI);
            InterfazImpl objeto = new InterfazImpl();
            Naming.rebind(URLRegistro, objeto);
            // System.out.println("Servidor registrado. El registro contiente: ");
            // listRegistry(URLRegistro);
        } catch (RemoteException e) {
            e.getMessage();
        } catch (MalformedURLException e) {
            e.getMessage();
        }
    }

     // Método que inicia un registro RMI en el puerto especificado
    private synchronized static void startRegistry(int PuertoRMI) throws RemoteException{
        try {
            Registry registry = LocateRegistry.getRegistry(PuertoRMI);
            registry.list(); 
        }
        catch (RemoteException e) { 
            System.out.println("El registro de RMI no se puede localizar en el puerto " + PuertoRMI);
            LocateRegistry.createRegistry(PuertoRMI);
            System.out.println("Registro RMI creado en el puerto " + PuertoRMI);
        }
    }
}
