package db;

import domain.Vuelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VueloDAO implements Dao<Vuelo>{
    private static VueloDAO vueloDAO;

    private final Connection conn;
    private final PreparedStatement getByIdStatement;
    private final PreparedStatement getAllStatement;
    private final PreparedStatement saveStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    private VueloDAO() {
        this.conn = DBManager.getDBManager().conn;
        try {
            this.getByIdStatement = conn.prepareStatement("SELECT * FROM FLIGHT WHERE ID=?");
            this.getAllStatement = conn.prepareStatement("SELECT * FROM FLIGHT");
            this.saveStatement = conn.prepareStatement("INSERT INTO FLIGHT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            this.updateStatement = conn.prepareStatement("UPDATE FLIGHT SET NAME=? WHERE ID=?");
            this.deleteStatement = conn.prepareStatement("DELETE FROM FLIGHT WHERE ID=?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static VueloDAO getVueloDAO(){
        if (vueloDAO == null){
            vueloDAO = new VueloDAO();
        }
        return vueloDAO;
    }

    @Override
    public Vuelo get(Object param) {
        int in = (int) param;
        try {
            getByIdStatement.setInt(1, in);
            ResultSet rs = getByIdStatement.executeQuery();
            if (rs.next()) {
                return setResult(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Vuelo> getAll() {
        ArrayList<Vuelo> lista = new ArrayList<>();
        try {
            ResultSet rs = getAllStatement.executeQuery();
            while (rs.next()){
                Vuelo vuelo = setResult(rs);
                lista.add(vuelo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public void save(Vuelo vuelo) {
        try {
            saveStatement.setInt(1, vuelo.getCodigo());
            saveStatement.setString(2, vuelo.getOrigen().getName());
            saveStatement.setString(3, vuelo.getDestino().getName());
            saveStatement.setString(4, vuelo.getAirline().getName());
            saveStatement.setString(5, vuelo.getFechaDespegue().toString());
            saveStatement.setString(6, vuelo.getFechaAterrizaje().toString());
            saveStatement.setInt(7, vuelo.getPasajeros());
            saveStatement.setInt(8, vuelo.getMaxPasajeros());
            saveStatement.setInt(9, vuelo.getPrecio());
            saveStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Vuelo vuelo) {

    }

    @Override
    public void delete(Vuelo vuelo) {

    }

    private Vuelo setResult(ResultSet rs){
        Vuelo vuelo = new Vuelo();
        try {
            vuelo.setCodigo(rs.getInt("ID"));
            // TODO Añadir un AirportDAO para poder añadir origen y destino
//          vuelo.setDestino(rs.getString("DEST_AIRPORT"));
//          vuelo.setOrigen(rs.getString("ORIG_AIRPORT"));
            vuelo.setAirline(AirlineDAO.getAirlineDAO().get(rs.getString("AIRLINE_CODE")));
            vuelo.setFechaDespegue(LocalDateTime.parse(rs.getString("DEPARTURE")));
            vuelo.setFechaAterrizaje(LocalDateTime.parse(rs.getString("ARRIVAL")));
            vuelo.setPasajeros(rs.getInt("PASAJEROS"));
            vuelo.setPrecio(rs.getInt("PRECIO"));
            vuelo.setMaxPasajeros(rs.getInt("MAXPASAJEROS"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vuelo;
    }
}
