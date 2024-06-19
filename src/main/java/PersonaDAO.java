import java.sql.*;
import java.util.HashSet;
import java.util.Set;



public class PersonaDAO implements IPersonaProxy {
    private final static String conexion = "jdbc:mysql://localhost:8081/TP-PD-PROXY";
    private final static String usuario = "tiago";
    private final static String clave = "12345678";

    private Connection connection;

    public PersonaDAO (Connection connection){
        try {
            connection = DriverManager.getConnection(conexion, usuario, clave);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public Set<Telefono> telefonos(int idPersona){
        Set<Telefono> telefonos = new HashSet<>();
        String query = "SELECT id, numero FROM telefonos WHERE idPersona = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPersona);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String numero = resultSet.getString("numero");
                telefonos.add(new Telefono(numero));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return telefonos;
    }
    public Persona personaPorId(int id) {
        String sql = "SELECT id, nombre FROM personas WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int personaId = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                return new Persona(personaId, nombre, telefonos(id));
            }
        } catch(SQLException e) {
            throw new RuntimeException("Error en la conexion");
        }
        throw new RuntimeException("No hay conexion");
    }
}
