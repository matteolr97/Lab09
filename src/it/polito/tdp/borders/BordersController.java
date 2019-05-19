/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader
	@FXML
	private ComboBox<Country> cmbBox;

	    @FXML
	    private Button btnTrovaVicini;

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		String annoInserito = txtAnno.getText();
		int anno = Integer.parseInt(annoInserito);
		txtResult.appendText(model.createGraph(anno).toString());
	  	txtResult.appendText("\n");
	  	txtResult.appendText(model.getListaStatiConGrado().toString());
	  	txtResult.appendText("\n");
	  	txtResult.appendText(String.format("Numero componenti connessi:%d\n" , model.getNumberOfConnectedComponents()));
	  	txtResult.appendText(model.getNumeroStatieArchi());

}
	  @FXML
	    void doTrovaVicini(ActionEvent event) {

		  txtResult.clear();

		  Country c = cmbBox.getValue();
		  if (c == null) {
				txtResult.setText("Select a country first.");
			}

			try {
				List<Country> reachableCountries = model.getStatiConfinanti(c);
				for (Country country : reachableCountries) {
					txtResult.appendText(String.format("%s\n", country));
				}
			} catch (RuntimeException e) {
				// If the countries are inserted in the ComboBox when the graph is created,
				// this should never happen.
				txtResult.setText("Selected country is not in the graph. They are islands");
			}

		}
			
			
		  
		  //txtResult.appendText(model.getStatiConfinanti(c).toString());
	    

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert cmbBox != null : "fx:id=\"cmbBox\" was not injected: check your FXML file 'Borders.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}

	public void setModel(Model model2) {
		this.model = model2;
		cmbBox.getItems().addAll(model.getCountries());
	
}}
