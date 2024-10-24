package main;

import java.time.LocalDate;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import gui.CellButtonRendererEditor;

public class ModeloVuelo extends AbstractTableModel {

	/**
	 * 
	 */
	public static final String BUTTON = "";

	private static final long serialVersionUID = 7420003808520688709L;
	
	public enum TipoVentana{
		ADMIN, EMPLOYEE, LOGIN, USER
	}
	private TipoVentana tipoVentana;
	
	private List<Vuelo> vuelos;


	// NOTE: 	¿podría ser útil pasar un parámetro para mostrar 
	//			datos diferentes en función de la tabla a mostrar?
	public ModeloVuelo(List<Vuelo> vuelos, TipoVentana tipoVentana) {
		this.vuelos = vuelos;
		this.tipoVentana = tipoVentana;
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
			if(this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
				return "Pasajeros";
			} else {
				return "";
			}
		case 6:
			if(this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
				return "";
			} else {
				return null;
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
		return vuelos.size();
	}

	@Override
	public int getColumnCount() {
		if (this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
				return 7;
			} else {
				return 6;
			}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Vuelo v = vuelos.get(rowIndex);
		switch (columnIndex) {
		case 0: 
			return v.getCodigo();
		case 1: 
			return v.getOrigen();
		case 2:
			return v.getDestino();
		case 3:
			return v.getFechaDespegue();
		case 4:
			return v.getFechaAterrizaje();
		case 5:
			if(this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
				return v.getPasajeros();
			} else {
				return "→";
			}
		case 6:
			if(this.tipoVentana.equals(TipoVentana.EMPLOYEE)) {
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
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
