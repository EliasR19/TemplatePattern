package b;

import java.util.ArrayList;
import java.util.List;
public abstract class CuentaBancaria {
	private String titular;
	private int saldo;
	private List<String> movimientos;
	
	public CuentaBancaria(String titular){
		this.titular=titular;
		this.saldo=0;
		this.movimientos=new ArrayList<String>();
	}
	public String getTitular(){
		return this.titular;
	}
	public int getSaldo(){
		return this.saldo;
	}
	protected void setSaldo(int monto){
		this.saldo=monto;
	}
	public void agregarMovimientos(String movimiento){
		this.movimientos.add(movimiento);
	}
	
	public void extraer(int monto) {
		if(this.puedeExtraer(monto)){
			this.setSaldo(this.getSaldo()-monto);
			this.agregarMovimientos("Extraccion");
		}
	}
		public abstract boolean puedeExtraer(int monto); 
		
}

class CuentaCorriente extends CuentaBancaria {
	private int descubierto;
	public CuentaCorriente(String titular, int descubierto){
		super(titular);
		this.descubierto=descubierto;
	}
	public int getDescubierto(){
	return this.descubierto;
}
	
	@Override
	public boolean puedeExtraer(int monto) {
		return this.getSaldo()+this.getDescubierto()>=monto;
	}
}
class CajaDeAhorro extends CuentaBancaria {
	private int limite;
	
	public CajaDeAhorro(String titular, int limite){
		super(titular);
		this.limite=limite;
	}
	public int getLimite(){
		return this.limite;
	}

	@Override
	public boolean puedeExtraer(int monto) {
		return this.getSaldo()>=monto && this.getLimite()>=monto;
	}
}