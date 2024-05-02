package udelp.edu.PooGrafico.model;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
	private  static Singleton s=null;
	private  List<String>lista;
	private Singleton() {
		lista=new ArrayList();
	}
	public static Singleton getInstance(){
		if(null==s) {
			s=new Singleton();
		}
		return s;
	}
	public void agregar(String s) {
		lista.add(s);
	}
	public void mostrarLista() {
		for(String v:lista) {
			System.out.println(v);
		}
	}
}
