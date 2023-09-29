package pi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Interfaz extends Remote {
    ArrayList<Float> crearPares(Integer num) throws RemoteException;
}
