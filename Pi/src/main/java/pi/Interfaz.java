package pi;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interfaz extends Remote {
    int crearNumeros(Integer num) throws RemoteException;
}
