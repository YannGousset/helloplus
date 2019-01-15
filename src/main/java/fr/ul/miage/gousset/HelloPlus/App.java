package fr.ul.miage.gousset.HelloPlus;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Application Hello Plus!
 *
 */
public class App 
{
	/**
	 * Attribut privé static final permettant de Logger l'application
	 */
	private static final Logger LOG = Logger.getLogger(App.class.getName());
	
	/**
	 * Attribut privé de type String représentant le nom du fichier
	 */
	private String filename;
	
	
	/**
	 * Constructeur de la classe App
	 * @param filename : type String représentant le nom du fichier
	 */
	public App(String filename) {
		setFilename(filename);
	}
	
	/**
	 * Getter du nom du fichier
	 * @return un String qui représente le nom du fichier
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Setter du nom du fichier
	 * @param filename qui représente le nom du fichier
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Méthode main
	 * @param args qui représente les arguments en entrée de l'application
	 */
    public static void main( String[] args )
    {
        String filename = null;
        
        Options options = new Options();
        Option input = new Option("i", "input", true, "names.csv");
        input.setRequired(true);
        options.addOption(input);
        
        CommandLineParser parser = new DefaultParser();
        try {
        	CommandLine line = parser.parse(options, args);
        	if(line.hasOption("i")) {
        		filename = line.getOptionValue("i");
        	}
        }
        catch(ParseException exp) {
        	LOG.severe("Erreur dans la ligne de commande");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("App", options);
            System.exit(1);
        }
        
        
        App app = new App(filename);
        try {
        	CSVParser p = app.buildCSVParser();
        	for(CSVRecord r: p) {
        		String nom = r.get(0);
        		String prenom = r.get(1);
        		System.out.println("Hello " + nom + " " + prenom + " !");
        	}
        }catch (IOException e) {
        	LOG.severe("Erreur de lecture dans le fichier CSV");
        }
    }
    
    /**
     * Méthode permettant de faire le parser du csv
     * @return le csvParser
     * @throws IOException
     */
    public CSVParser buildCSVParser() throws IOException{
    	CSVParser res = null;
    	Reader in;
    	in = new FileReader(filename);
    	CSVFormat csvf = CSVFormat.DEFAULT.withCommentMarker('#').withDelimiter(';');
    	res = new CSVParser(in, csvf);
    	return res;
    }
}
