package airline.database;

public abstract class Login {

    abstract boolean checkPass(String pass, int ID);

    abstract void Login(String password, int ID);
}
