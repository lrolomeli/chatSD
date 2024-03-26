package chatinterface;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote {

	// esta funcion consulta que la informacion de la contrasena ingresada
	// por el usuario concuerde con la registrada previamente y coloca la
	// bandera de estatus en online si la sesion fue iniciada con exito
    int authenticateUser(String user, String password) throws RemoteException;
    
    // recibe los parametros de nombre de usuario y contrasena y los registra
    // en la base de datos como un nuevo usuario
    boolean registerUser(String user, String password) throws RemoteException;
    
    // Esta funcion coloca nuevas entradas en la tabla de de mensajes
    // registrando el mensaje el emisor y el chat
    boolean sendMessage(String message, int user_id, int chat_id) throws RemoteException;
    
    // Retorna los ultimos 10 mensajes de la conversacion
    String loadMess(int chat_id) throws RemoteException;
    
    // open chat hace una consulta a la base de datos
    // para saber si existe un chat entre el usuario actual
    // y el usuario o grupo con quien se quiere comunicar
    // en caso de que ya exista la conversacion (chat) 
    // la funcion retornara el id del chat existente.
    // Si la conversacion no existe, se va a crear una
    // nueva entrada en la base de datos y retornara el numero
    // de chat
    int openChat(int user_id, int chat) throws RemoteException;
    
    // Esta funcion hara una consulta de todos los usuarios registrados en 
    // la tabla de usuarios y devolvera el status [des]conectado de cada uno
    String userStatus() throws RemoteException;
    
    // Esta funcion modifica el status del usuario en la base de datos
    boolean changeStatus(int user_id, boolean status) throws RemoteException;
    
}
