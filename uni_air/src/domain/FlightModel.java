package domain;

import gui.CellButtonRendererEditor;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 7420003808520688709L;
    private final TipoVentana tipoVentana;
    private final ArrayList<Flight> flights;

    public FlightModel(List<Flight> flights, TipoVentana tipoVentana) {
        this.flights = new ArrayList<>(flights);
        this.tipoVentana = tipoVentana;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Código";
            case 1:
                return "Origen";
            case 2:
                return "Destino";
            case 3:
                return "Salida";
            case 4:
                return "Llegada";
            case 5:
                if (this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
                    return "Pasajeros";
                } else {
                    return "";
                }
            case 6:
                if (this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
                    return "";
                } else if (this.tipoVentana.equals(TipoVentana.ADMIN)) {
                    return "";
                }
            default:
                throw new IllegalArgumentException("Unexpected value: " + column);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 3 || columnIndex == 4) {
            return LocalDate.class;
        } else if (columnIndex == 5 || columnIndex == 6 && this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
            return CellButtonRendererEditor.class;
        } else {
            return String.class;
        }
    }

    @Override
    public int getRowCount() {
        return flights.size();
    }

    @Override
    public int getColumnCount() {
        if (this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
            return 7;
        } else if (this.tipoVentana.equals(TipoVentana.ADMIN)) {
            return 5;
        } else {
            return 6;
        }

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Flight v = flights.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return v.getCodigo();
            case 1:
                return v.getOrigen().getName();
            case 2:
                return v.getDestino().getName();
            case 3:
                return v.getFechaDespegue();
            case 4:
                return v.getFechaAterrizaje();
            case 5:
                if (this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
                    return "0";
                } else {
                    return "→";
                }
            case 6:
                if (this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
                    return "→";
                } else {
                    return null;
                }
            default:
                throw new IllegalArgumentException("Unexpected value: " + columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public enum TipoVentana {
        ADMIN, EMPLOYEE, USER
    }


}
