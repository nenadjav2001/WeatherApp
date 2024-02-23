package WeatherFrame; // Paketdeklaration für die Wetter-App

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Die Klasse Frame stellt den Frame für die Wetter-App bereit
 */
public class Frame extends JFrame { // Die Klasse Frame erbt von JFrame

    // Klassenvariablen für Bildschirmbreite und Bildschirmhöhe
    final int screenWitdh = 1080;
    final int screenHeight = 720;

    // Objekte für die Wetter App
    JButton searchButton;
    JTextField searchField;
    JLabel label;
    WeatherApi weatherApi;
    Font font = new Font("Arial", Font.ITALIC, 17);

    // Labels für verschiedene Wetterinformationen
    JLabel labelTemp, labelMinTemp, labelMaxTemp;
    JLabel labelWeatherDescrip;
    JLabel getLabelWeatherDescripImage;
    BackgroundImage backgroundImage;
    JLabel labelFeuchtikgkeit;
    JLabel labelFeuchtikgkeitImage, labelWindSpeedImage;

    JLabel labelSunnyImage, labelColdImage, labelThunderStormImage, labelWindSpeed, labelWindDeg;

    JLabel labelCityAndCountry;

    JLabel labelSunRise, labelSunSet;
    JLabel labelSunRiseImage, labelSunSetImage;

    // Bilder
    private ImageIcon serachIcon;

    /**
     * Konstruktor der Klasse Frame
     */
    public Frame() {

        // Aufruf der Superklasse JFrame und Titelsetzung
        super("Weather-APP");

        // Erstellung der Objekte
        searchButton = new JButton();
        searchField = new JTextField();
        label = new JLabel();
        weatherApi = new WeatherApi();

        // Erstellung der Labels für Wetterinformationen
        labelTemp = new JLabel();
        labelTemp.setFont(font);

        labelMaxTemp = new JLabel();
        labelMaxTemp.setFont(font);

        labelMinTemp = new JLabel();
        labelMinTemp.setFont(font);

        labelWeatherDescrip = new JLabel();
        labelWeatherDescrip.setBounds(150, 500, 400, 20);
        labelWeatherDescrip.setFont(font);

        getLabelWeatherDescripImage = new JLabel();

        labelFeuchtikgkeit = new JLabel();
        labelFeuchtikgkeit.setFont(font);

        labelColdImage = new JLabel();
        labelSunnyImage = new JLabel();

        labelWindSpeed = new JLabel();
        labelWindSpeed.setFont(font);

        labelWindSpeedImage = new JLabel();

        labelWindDeg = new JLabel();

        labelCityAndCountry = new JLabel();

        labelSunRise = new JLabel();
        labelSunRise.setFont(font);

        labelSunRiseImage = new JLabel();

        labelSunSet = new JLabel();
        labelSunSet.setFont(font);

        labelSunSetImage = new JLabel();

        serachIcon = new ImageIcon("\"C:\\Users\\bewer\\Downloads\\2946467-200.png\""); // Initialisierung des Suchsymbols

    }

    /**
     * Methode zur Erstellung des Frames
     *
     * @throws NullPointerException falls eine NullPointerException auftritt
     * @throws IOException          falls ein E/A-Fehler auftritt
     */
    public void createFrame() throws NullPointerException, IOException {

        // Weitere Methoden hinzufügen
        try {
            addingButtonToFrame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //  addingTextField();
        addSearchField();

        backgroundImage = new BackgroundImage();

        this.add(backgroundImage); // Hintergrundbild zum Frame hinzufügen
        this.setSize(screenWitdh, screenHeight); // Größe des Frames festlegen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Verhalten bei Schließen des Frames festlegen
        this.setLayout(null); // Layout-Manager deaktivieren
        this.setResizable(false); // Größenänderung deaktivieren
        this.setLocationRelativeTo(null); // Position des Frames zentrieren
        this.setVisible(true); // Frame sichtbar machen
    }

    // Methode zum Hinzufügen des Suchbuttons zum Frame
    public void addingButtonToFrame() throws IOException {
        searchButton.setBounds(650, 50, 30, 30); // Position und Größe des Suchbuttons festlegen
        searchButton.setFont(new Font("Arial", Font.BOLD, 15)); // Schriftart und -größe festlegen
        searchButton.setFocusable(false); // Fokusierbarkeit deaktivieren
        searchButton.setBackground(Color.white); // Hintergrundfarbe festlegen
        searchButton.setBorder(BorderFactory.createLineBorder(Color.gray, 1)); // Rahmenstil festlegen
        buttonListener(searchButton); // ActionListener für den Suchbutton hinzufügen
        loadingButtonImage(searchButton); // Bild für den Suchbutton laden
        this.add(searchButton); // Suchbutton zum Frame hinzufügen
    }

    // ActionListener für den Button
    public void buttonListener(JButton button) throws IOException {

        // Lamda Ausdruck für den ActionListener
        button.addActionListener(e -> {

            labelTemp.setText(""); // Textfelder für Wetterinformationen zurücksetzen
            labelMinTemp.setText("");
            labelMaxTemp.setText("");

            try {

                // Wettermethoden aufrufen
                luftFeuchtigkeit(e, searchButton);
                windSpeed(e);
                System.out.println(weatherApi.fetchData(getText())); // Wetterdaten abrufen

                // Bildmethoden aufrufen
                drawImagesSunnyAndCold();
                wetterDescription(getLabelWeatherDescripImage, e);
                setCountryAndCity();
                sunRise(e);
                sunSet(e);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            repaint();
        });
    }

    // Laden des Bildes für den Button
    public void loadingButtonImage(JButton button) {

        // Bild laden und dem Button zuweisen
        try {
            Image img = ImageIO.read(new File("C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\2946467-200.png"));
            Image icon = img.getScaledInstance(40, 40, 3); // Bild skalieren
            button.setIcon(new ImageIcon(icon)); // Icon für den Button festlegen
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Suchfeld zum Frame hinzufügen
    public void addSearchField() {
        // Textfeld nur hinzufügen, wenn es noch nicht vorhanden ist
        if (searchField.getParent() == null) {
            getContentPane().add(searchField); // Suchfeld zum Frame hinzufügen
            searchField.setBounds(350, 50, 300, 30); // Position und Größe des Suchfelds festlegen
            searchField.setFont(new Font("Arial", Font.ITALIC, 15)); // Schriftart und -größe festlegen
            searchField.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1)); // Rahmenstil festlegen
        }
    }


    // Methode zum Anzeigen der Bilder je nach Wetterbedingungen
// Methode zum Anzeigen der Bilder je nach Wetterbedingungen
    public void drawImagesSunnyAndCold() throws IOException {
        double temp = weatherApi.getTemperature(getText());
        String weather = weatherApi.getWeatherDescription(getText());

        // Kälte
        if (temp >= -30 && temp <= 10) {
            loadingImages(labelColdImage, 100, 100, "C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\images (1).png", 50, 100, 200, 200, 3);
            labelColdImage.setVisible(true);
            labelSunnyImage.setVisible(false);

        } else if (temp >= 10 && temp <= 50) {
            loadingImages(labelSunnyImage, 100, 100, "C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\Download (1).png", 50, 100, 200, 200, 3);
            labelSunnyImage.setVisible(true);
            labelColdImage.setVisible(false);
        } else {
            // Bilder ausblenden, wenn keine Bedingung erfüllt ist
            labelSunnyImage.setVisible(false);
            labelColdImage.setVisible(false);
        }

        addingLabelsAndText();

        // Repaint des Hintergrundpanels
        backgroundImage.repaint();
    }

    // Land und Stadt setzen
    public void setCountryAndCity() throws IOException {

        labelCityAndCountry.setBounds(700, 50, 400, 30);
        labelCityAndCountry.setText(weatherApi.getCounty(getText()) + " , " + getText());
        labelCityAndCountry.setFont(new Font("Arial", Font.ITALIC, 30));
        addingComponentsToPanel(labelCityAndCountry);

    }

    /**
     * Labels und Text hinzufügen
     *
     * @throws IOException falls ein E/A-Fehler auftritt
     */
    public void addingLabelsAndText() throws IOException {
        labelTemp.setBounds(150, 150, 400, 20);
        labelMaxTemp.setBounds(150, 200, 400, 20);
        labelMinTemp.setBounds(150, 250, 400, 20);

        // Texte in den Labels aktualisieren
        labelTemp.setText("Temperatur: " + getTempString() + " °C");
        labelMaxTemp.setText("Maximaltemperatur heute: " + getMaxTempString() + " °C");
        labelMinTemp.setText("Minimaltemperatur heute: " + getMinTempString() + " °C");


        // Labels zum Hintergrundpanel hinzufügen
        addingComponentsToPanel(labelMaxTemp);
        addingComponentsToPanel(labelMinTemp);
        addingComponentsToPanel(labelTemp);
        addingComponentsToPanel(labelSunnyImage);
        addingComponentsToPanel(labelColdImage);
    }

    // Wetterbeschreibung
    public void wetterDescription(JLabel label, ActionEvent e) throws IOException {

        String weather = weatherApi.getWeatherDescription(getText());
        Image img;
        label.setBounds(50, 400, 200, 200);
        addingComponentsToPanel(labelWeatherDescrip);

        if (weather.equals("overcast clouds") || weather.equals("broken clouds") || weather.equals("few clouds")) {

            File file = new File("C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\img_4.png");
            img = ImageIO.read(file);
            Image icon = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(icon));
            labelWeatherDescrip.setText("Bewölkt");

        } else if (weather.equals("clear sky")) {

            loadingImages(getLabelWeatherDescripImage, 100, 100, "C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\3222800 (1).png", 50, 400, 200, 200, 3);
            labelWeatherDescrip.setText("Klarer Himmel");

        } else if (weather.equals("light rain") || weather.equals("heavy rain")) {

            loadingImages(getLabelWeatherDescripImage, 100, 100, "C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\Rain.png",
                    50, 400, 200, 200, 3);
            labelWeatherDescrip.setText("Leichter-Regen");
        }

        addingComponentsToPanel(label);

    }

    // Gewitter zeichnen
    public void drawThunderStorm(JLabel label) throws IOException {

        Image img;

        double minTemp = weatherApi.getTemperature(getText());
        String cloundsFormation = weatherApi.getWeatherDescription(getText());

        if (cloundsFormation.equals("Thunderstorm") || cloundsFormation.equals("Heavy thunderstorm") || cloundsFormation.equals("Thunderstorms with heavy rain") || cloundsFormation.equals("Thunderstorms with rain") || cloundsFormation.equals("Thunderstorms with showers")) {

            File file = new File("C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\11947221.png");
            img = ImageIO.read(file);
            Image icon = img.getScaledInstance(200, 200, 3);
            label.setIcon(new ImageIcon(icon));
        }

    }

    // Luftfeuchtigkeit anzeigen

    /**
     *
     * @param e für das sicherstellen damit der Knopf gedrückt werden kann.
     * @param button später soll der searchbutton als übergeben werden
     *
     */
    public void luftFeuchtigkeit(ActionEvent e, JButton button) throws IOException {

        String luftFeuchtigkeit = getLuftfeuchtigkeit();

        if (e.getSource() == button) {
            labelFeuchtikgkeitImage = new JLabel();
            loadingImages(labelFeuchtikgkeitImage, 100, 100, "C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\3143217.png", 50, 270, 200, 200, 3);

        }

        setLuftFeuchtigkeitLabel(luftFeuchtigkeit);
        addingComponentsToPanel(labelFeuchtikgkeitImage);
        addingComponentsToPanel(labelFeuchtikgkeit);
    }

    // Luftfeuchtigkeit Label setzen
    public void setLuftFeuchtigkeitLabel(String luftFeuchtigkeit) {
        labelFeuchtikgkeit.setBounds(150, 360, 250, 20);
        labelFeuchtikgkeit.setText(" Luftfeuchtigkeit " + luftFeuchtigkeit + " % ");
    }

    // Windgeschwindigkeit anzeigen
    public void windSpeed(ActionEvent e) throws IOException {

        String windSpeed = getWindSpeed();
        String windDeg = getWindDeg();

        if (e.getSource() == searchButton) {
            loadingImages(labelWindSpeedImage, 80, 80, "C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\54298 (1).png", 500, 150, 100, 100, 3);
        }

        setLabelWindSpeed(windSpeed);
        setLabelWindDeg(windDeg);
        addingComponentsToPanel(labelWindSpeedImage);

        repaint();

    }

    // Windrichtung setzen und Texte der Labels und als Parameter den String windDeg
    public void setLabelWindDeg(String windDeg) throws IOException {
        labelWindDeg.setBounds(600, 220, 250, 20);
        labelWindDeg.setText("Wind Grad : " + getWindDeg());
        labelWindDeg.setFont(font);
        addingComponentsToPanel(labelWindDeg);
    }

    // Windgeschwindigkeit setzen
    public void setLabelWindSpeed(String windSpeed) {
        labelWindSpeed.setBounds(600, 195, 250, 20);
        labelWindSpeed.setText(" Wind Geschwindkeit : " + windSpeed + " km/h");
        addingComponentsToPanel(labelWindSpeed);
    }

    // Sonnenaufgang anzeigen

    /**
     *
     * @param e für das sicherstellen damit der Knopf gedrückt werden kann.
     * @throws IOException soll eine IOException erzeugen wenn das Bild nicht geladen werden kann
     */
    public void sunRise(ActionEvent e) throws IOException {


        labelSunRise.setBounds(600, 350, 250, 20);
        String sunRise = weatherApi.getSunRise(getText());

        if (e.getSource() == searchButton) {

            loadingImages(labelSunRiseImage, 100, 100, "C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\8179067.png", 500, 300, 100, 100, 3);
            labelSunRise.setText(" Sonnenaufgang : " + sunRise);

        }

        addingComponentsToPanel(labelSunRiseImage);
        addingComponentsToPanel(labelSunRise);
    }

    // Sonnenuntergang anzeigen

    /**
     *
     * @param e wird sichergestellt das der Button gedrückt werden kann
//     * @throws IOException soll eine IOException erzeugen wenn das Bild nicht geladen werden kann
     */
    public void sunSet(ActionEvent e) throws IOException {

        labelSunSet.setBounds(600, 500, 250, 20);
        String sunSet = weatherApi.getSunSet(getText());
        if (e.getSource() == searchButton) {
            loadingImages(labelSunSetImage, 100, 100, "C:\\Users\\bewer\\Downloads\\WeatherApp\\src\\climate_forecast_weather_sun_evening_night_dusk_sunset_icon_226639.png", 490, 460, 100, 100, 3);
            labelSunSet.setText("Sonnen-Untergang : " + sunSet);
        }

        addingComponentsToPanel(labelSunSetImage);
        addingComponentsToPanel(labelSunSet);
    }

    /**
     *
     * @return es wird windspeed in ein String umgewandelt und uzrückgegeben
     * @throws IOException
     */
    public String getWindSpeed() throws IOException {
        return String.valueOf(weatherApi.windSpeed(getText()));
    }

    // Eingegebenen Text abrufen
    public String getText() {
        return searchField.getText();
    }

    // Aktuelle Temperatur abrufen und als String zurückgeben
    public String getTempString() throws IOException {
        double roundedTemp = Math.round(weatherApi.getTemperature(getText()));
        return String.valueOf(roundedTemp);
    }

    // Minimaltemperatur abrufen und als String zurückgeben
    public String getMinTempString() throws IOException {
        double roundedMinTemp = Math.round(weatherApi.getMinTemp(getText()));
        return String.valueOf(roundedMinTemp);
    }

    // Maximaltemperatur abrufen und als String zurückgeben
    public String getMaxTempString() throws IOException {
        double roundedMaxTemp = Math.round(weatherApi.getMaxTemp(getText()));
        return String.valueOf(roundedMaxTemp);
    }

    // Luftfeuchtigkeit abrufen und als String zurückgeben
    public String getLuftfeuchtigkeit() throws IOException {
        return String.valueOf(weatherApi.getHumidity(getText()));
    }

    // Windrichtung abrufen und als String zurückgeben
    public String getWindDeg() throws IOException {
        return String.valueOf(weatherApi.windDeg(getText()));
    }

    // Komponente zum Panel hinzufügen
    public void addingComponentsToPanel(Component component) {
        backgroundImage.add(component);
    }

    // Bilder laden und anzeigen
    public void loadingImages(JLabel label, int targetWitdh, int targetHeight, String path, int x, int y, int witdh, int height, int hints) throws IOException {


        File file = new File(path);
        Image img = ImageIO.read(file);
        Image icon = img.getScaledInstance(targetWitdh, targetHeight, hints);
        label.setIcon(new ImageIcon(icon));
        label.setBounds(x, y, witdh, height);

    }
}
