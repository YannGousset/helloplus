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

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainView extends Application {

	/**
	 * Attribut privé static final permettant de Logger l'application
	 */
	private static final Logger LOG = Logger.getLogger(App.class.getName());
	
	/**
	 * Attribut privé de type String représentant le nom du fichier
	 */
	private String filename;
	
	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		BorderPane blayout = new BorderPane();
		TextArea ta = new TextArea();
		Button btn = new Button("Dire bonjour");
		btn.setOnAction((e) -> {this.click(ta);;});
		blayout.setBottom(btn);
		blayout.setCenter(ta);
		BorderPane.setAlignment(btn, Pos.BOTTOM_CENTER);
		Scene scene = new Scene(blayout, 500, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Application HelloWorld");
		primaryStage.show();
	}

	/**
	 * Méthode de lancement de l'application
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
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
        
    }
	
	/**
	 * Méthode de click du bouton de la GUI
	 * @param ta
	 */
	public void click(TextArea ta) {
		filename = "names.csv";
		try {
        	CSVParser p = this.buildCSVParser();
        	for(CSVRecord r: p) {
        		ta.appendText("Hello " + r.get(0) + " " + r.get(1) + " !\n");
        		
        	}
        }catch (IOException e) {
        	LOG.severe("Erreur de lecture dans le fichier CSV");
        }
	}
	
	
	/**
	 * Méthode de parse pour la fichier CSV
	 * @return
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
