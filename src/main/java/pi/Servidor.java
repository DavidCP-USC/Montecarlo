package pi;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor extends Thread{
    int valor;
    public Servidor(Integer valor) {
        super();
        this.valor = valor;
    }
    @Override
    public void run() {
        int puertoRMI = 6789;
        String URLRegistro = "rmi://localhost:" + puertoRMI + "/interfaz"+valor;
        try {
            // Creamos el objeto remoto
            startRegistry(puertoRMI);
            InterfazImpl objeto = new InterfazImpl();
            Naming.rebind(URLRegistro, objeto);

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

    // Método que lista los registros RMI 
    private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
        System.out.println("Registro " + registryURL + " contiene: ");
        String [ ] nombres = Naming.list(registryURL);
        for (int i=0; i < nombres.length; i++)
            System.out.println(nombres[i]);
    }
}
