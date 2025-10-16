package a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Main {
	public static void main(String[] args) {

	Map<String, WikipediaPage> aM = new HashMap<String, WikipediaPage>();
	Map<String, WikipediaPage> bM = new HashMap<String, WikipediaPage>();
	Map<String, WikipediaPage> cM = new HashMap<String, WikipediaPage>();
	WikipediaPage a;
	WikipediaPage b;
	WikipediaPage c;
	
	
	
	a = new WikipediaPage("La Plata", Arrays.asList());
	b = new WikipediaPage("La Paloma", Arrays.asList(a));
	c = new WikipediaPage("El Pato", Arrays.asList(a,b));
	
	a.addProp("Puente", c);
	b.addProp("Puente", c);
	b.addProp("Perros", c);
	c.addProp("Pajaro", a);
	
	Filtro aF = new PropiedadEnComun();
	
	
	for(WikipediaPage f : aF.getSimilarPages(b, Arrays.asList(c,a))) {
		System.out.println(f.getTitle());
	}
}
}

abstract class Filtro {
	
	public List<WikipediaPage> getSimilarPages(WikipediaPage page, List<WikipediaPage> wikipedia){
		
		return wikipedia.stream().filter(f -> this.filtro(f, page)).collect(Collectors.toList());
		
		/*
		List<WikipediaPage> filterPages = new ArrayList<WikipediaPage>();
		for(WikipediaPage f : wikipedia) {
			if (this.filtro(f, page)) {
				filterPages.add(f);
			}
		}
		
		return filterPages;
		*/
	}
	
	public abstract boolean filtro(WikipediaPage page, WikipediaPage pageC);
}
	
class MismaLetraInicial extends Filtro {

	@Override
	public boolean filtro(WikipediaPage page, WikipediaPage pageC) {
		return pageC.getTitle().charAt(0) == page.getTitle().charAt(0);
	}
	
}

class LinkEnComun extends Filtro {

	@Override
	public boolean filtro(WikipediaPage page, WikipediaPage pageC) {
		
		for(WikipediaPage link : page.getLinks()) {
			if(pageC.getLinks().contains(link)) {
				return true;
			}
		}
		
		return false;
	}
	
}

class PropiedadEnComun extends Filtro {

	@Override
	public boolean filtro(WikipediaPage page, WikipediaPage pageC) {
		for(String prop: page.getInfobox().keySet()) {
			if(pageC.getInfobox().containsKey(prop)) {
				return true;
			}
		}
		
		return false;
	}
}


class WikipediaPage {
	private String title;
	private List<WikipediaPage> links = new ArrayList<WikipediaPage>();
	private Map<String, WikipediaPage> infobox = new HashMap<String, WikipediaPage>();
	
	public WikipediaPage(String title, List<WikipediaPage> links) {
		this.title = title;
		this.links = links;
	}
	
	public String getTitle() {
	/*retorna el título de la página.*/
		return title;
	}
	
	public List<WikipediaPage> getLinks(){
/*retorna una Lista de las páginas deWikipedia con las que se conecta.*/
		return links;
	}
	
	public Map<String, WikipediaPage> getInfobox(){
		/*retorna un Map con un valor en texto y la pagina que describe ese valor que aparecen en los infobox de la
	página de Wikipedia.*/
		return infobox;
	}
	
	public void addProp(String prop, WikipediaPage page) {
		infobox.put(prop, page);
	}
	
	
}


