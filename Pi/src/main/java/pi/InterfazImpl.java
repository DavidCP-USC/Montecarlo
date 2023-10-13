package pi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class InterfazImpl extends UnicastRemoteObject implements Interfaz {
    public InterfazImpl() throws RemoteException {
        // Constructor por defecto
    }

    public int crearNumeros(Integer num) throws RemoteException {
        float x, y;
        int resultado = 0;
        for (int i = 0; i < num; i++) {
            x = (float) (Math.random());
            y = (float) (Math.random());
            if ((x*x + y*y) <= 1){
                resultado++;
            }
        }
        return resultado;
    }
    
}