package BlueJCode; 

import bluej.extensions2.BlueJ;
import bluej.extensions2.PreferenceGenerator;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

class Preferences implements PreferenceGenerator
{
    private Pane myPane;
    private TextField color;
    private BlueJ bluej;
    public static final String PROFILE_LABEL = "Favorite-Colour";

    // Construct the panel, and initialise it from any stored values
    public Preferences(BlueJ bluej)
    {
        this.bluej = bluej;
        myPane = new Pane();
        HBox hboxContainer = new HBox();
        hboxContainer.getChildren().add(new Label("Favorite Colour: "));
        color = new TextField();
        hboxContainer.getChildren().add(color);
        myPane.getChildren().add(hboxContainer);
        // Load the default value
        loadValues();
    }

    public Pane getWindow()
    {
        return myPane;
    }

    public void saveValues()
    {
        // Save the preference value in the BlueJ properties file
        bluej.setExtensionPropertyString(PROFILE_LABEL, color.getText());
    }

    public void loadValues()
    {
        // Load the property value from the BlueJ properties file,
        // default to an empty string
        color.setText(bluej.getExtensionPropertyString(PROFILE_LABEL, ""));
    }
}