package fr.inria.RDFConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FormController {
	private App master;

	public static final List<RDFFormat> RDFFormats = Arrays.asList(new RDFFormat[] {
			RDFFormat.BINARY,
			RDFFormat.JSONLD,
			RDFFormat.N3,
			RDFFormat.NQUADS,
			RDFFormat.NTRIPLES,
			RDFFormat.RDFA,
			RDFFormat.RDFJSON,
			RDFFormat.RDFXML,
			RDFFormat.TRIG,
			RDFFormat.TURTLE
	});

	@FXML
	private TextField  inputFile;
	@FXML
	private ComboBox<RDFFormat> inputFileFormat;
	@FXML
	private ComboBox<RDFFormat> outputFileFormat;

	public void initialize() {
		inputFileFormat.getItems().addAll(RDFFormats);
		outputFileFormat.getItems().addAll(RDFFormats);

	}

	public void selectInputFile() {
		File res = master.createFileChooser("Select rdf file");
		if (res!=null)
			this.inputFile.setText(res.getAbsolutePath().toString());
	}

	public void close() throws Exception {
		this.master.stop();
	}

	public void convert() {
		System.out.println("Convert "+this.inputFile.getText());
		System.out.println("From format "+inputFileFormat.getSelectionModel().getSelectedItem());
		System.out.println("To format "+outputFileFormat.getSelectionModel().getSelectedItem());

		RDFFormat inFormat = inputFileFormat.getSelectionModel().getSelectedItem();
		RDFFormat outFormat = outputFileFormat.getSelectionModel().getSelectedItem();
		if (inFormat == null) {
			master.createAlertError("Error", "Error", "No input file format.");
			return ;
		}
		if (outFormat == null) {
			master.createAlertError("Error", "Error", "No output file format.");
			return ;
		}
		FileInputStream fin=null;
		File fp = Paths.get(this.inputFile.getText()).toFile();
		if (!fp.exists()){
			master.createAlertError("Error", "Error", "File does not exist.");
			return ;
		}
		try {
			fin = new FileInputStream(fp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			master.createAlertError("Error", "Error creating inputstream", e.getMessage());
			return ;
		}
		Model model;
		try {
			model = Rio.parse(fin, "", inFormat);
		} catch (RDFParseException | UnsupportedRDFormatException | IOException e) {
			e.printStackTrace();
			master.createAlertError("Error", "Error parsing input file", e.getMessage());
			return ;
		}
		File saveFile = master.createFileChooserSave("Select rdf file");
		if (saveFile== null) {
			master.createAlertError("Error", "Error", "No destination selected.");
			return ;
		}
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			master.createAlertError("Error", "Error creating outputstream", e.getMessage());
			return ;
		}
		Rio.write(model, fout, outFormat);		
		master.createAlertSuccess("Success", "Convertion", "Convertion done.");

	}

	public void setMaster(App app) {
		this.master = app;
	}
}
