package conexionBBDD;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBBDD {

    private Connection conexion = null;
    private static final String URL = "jdbc:postgresql://10.0.2.2:5432/pistaya";
    private static final String USUARIO  = "postgres";
    private static final String PASSWORD = "1234";

    public Connection conectarBBDD() {
        try {
            android.os.StrictMode.ThreadPolicy policy =
                    new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
            android.os.StrictMode.setThreadPolicy(policy);

            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a PistaYa");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }
}