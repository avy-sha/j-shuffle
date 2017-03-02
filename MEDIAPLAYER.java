
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;


public class MEDIAPLAYER extends Application {

    private String MEDIA_URL =
            "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
    private Timeline timeline;
    private DoubleProperty timeSeconds = new SimpleDoubleProperty(),
            splitTimeSeconds = new SimpleDoubleProperty();
    private Duration time = Duration.ZERO;
    private Duration last ;
    Scene scene;
    Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
       VBox layout1 = new VBox(5);Scene scene3 = new Scene(layout1, 700, 500);time = Duration.ZERO;timeline = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {@Override public void handle(ActionEvent t) {Duration duration = ((KeyFrame)t.getSource()).getTime();time = time.add(duration);timeSeconds.set(time.toSeconds());last=Duration.seconds(3);if(last.equals(time)){
            window.setScene(scene);timeline.stop();
        }}}));timeline.setCycleCount(Timeline.INDEFINITE);timeline.play();
        layout1.getStylesheets().add("Neeche.css");
        primaryStage.setTitle("j-Shuffle (Beta 1.0)");
        window.setOnCloseRequest(e->{
            e.consume();
            closeProgram();});
        Group root = new Group();
        scene = new Scene(root, 700, 500);
        primaryStage.getIcons().add(new Image("icon.png"));
        scene.getStylesheets().add("peeche.css");
        // create media player
        Media media = new Media (MEDIA_URL);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        MediaControl mediaControl = new MediaControl(mediaPlayer,primaryStage);
        scene.setRoot(mediaControl);

        primaryStage.setScene(scene3);
        primaryStage.show();
        primaryStage.setResizable(false);


    }
    private void closeProgram(){
        Boolean answer=ConfirmBox.display("Confirm Exit", "Sure you want to exit");
        if(answer)
            window.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
