import javafx.application.Application;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.*;
import javafx.scene.Scene;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.io.*;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import javafx.beans.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sun.applet.Main;

public class MediaControl extends BorderPane {
    private Label artist;
    private Label album;
    private Label title;
    private Label year;
    private ImageView albumCover;
    private Timeline timeline;
    private DoubleProperty timeSeconds = new SimpleDoubleProperty(),
            splitTimeSeconds = new SimpleDoubleProperty();
    private Duration time = Duration.ZERO;
    private Duration last ;
    int i=0,prev=-1;
    Stage window;
    Button pp;
    Label np,nam;
    List<File>file;
    List<File>full;
    File temp;
    Random ra;
    ToggleButton rep;
    String playing;
    int j,kk=0,l,sw=0,shuff;
    Scene scene1, scene;
    int Maincount=-1;
    int currplay=-1;
    CheckMenuItem nex,ri,shuf;
    TableView<playlist> playlst=new TableView<>();
    ObservableList<playlist> data =
            FXCollections.observableArrayList();
    Media media;
    FadeTransition ft;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
   private HBox layout ;
   private HBox layout3 ;
   private HBox layout4 ;
    private GridPane meta;
    VBox yo;

    public MediaControl(MediaPlayer mediaPloyer,Stage primaryStage) {
       this.mediaPlayer = mediaPloyer;
        mediaView = new MediaView(mediaPlayer);
        ra=new Random();
         layout = new HBox(0);
         layout3 = new HBox(5);
         layout4 = new HBox(5);
        meta=new GridPane();
        file=new ArrayList<>();
        full=new ArrayList<>();
        Image imageplay = new Image(getClass().getResourceAsStream("media_playback_start.png"));
        Image imagepause = new Image(getClass().getResourceAsStream("media_playback_pause.png"));
        Image imageback = new Image(getClass().getResourceAsStream("media_seek_backward.png"));
        Image imageforward = new Image(getClass().getResourceAsStream("media_seek_forward.png"));
        Image imageprev = new Image(getClass().getResourceAsStream("media_skip_backward.png"));
        Image imagenext = new Image(getClass().getResourceAsStream("media_skip_forward.png"));
        Image imageload = new Image(getClass().getResourceAsStream("media_eject.png"));
        Image defaultpic=new Image(getClass().getResourceAsStream("defaultpicart.png"));
        Label space3=new Label("                   ");
        Label space4=new Label("                   ");
        albumCover=new ImageView();
        albumCover.setImage(defaultpic);
        albumCover.setFitWidth(220);
        GridPane.setConstraints(albumCover, 0,0);
        albumCover.setPreserveRatio(true);
        artist=new Label("-----");artist.setFont(new Font("Arial", 18)); artist.setTextFill(Color.web("#FFFFFF"));artist.setTextFill(Color.web("#FFFFFF"));
        album=new Label("-----");album.setFont(new Font("Arial", 18)); album.setTextFill(Color.web("#FFFFFF"));album.setTextFill(Color.web("#FFFFFF"));
        title=new Label("-----");title.setFont(new Font("Arial", 18)); title.setTextFill(Color.web("#FFFFFF"));title.setTextFill(Color.web("#FFFFFF"));
        year=new Label("-----");year.setFont(new Font("Arial", 18)); year.setTextFill(Color.web("#FFFFFF"));year.setTextFill(Color.web("#FFFFFF"));
        yo=new VBox(10);
        yo.getChildren().addAll(space4,artist,album,title,year);
        GridPane.setConstraints(yo,1,0);
        meta.getChildren().addAll(space3,yo,albumCover);
        TableColumn<playlist,String> name=new TableColumn<> ("Name");
        name.setMinWidth(710);
        name.setMaxWidth(710);
        name.setEditable(false);
        name.setSortable(false);

        pp=new Button();
        pp.setStyle("-fx-background-color: transparent");
        pp.setGraphic(new ImageView(imageplay));
        pp.setOnAction(e->{
            Status status = mediaPlayer.getStatus();
            if(i==0&&Maincount!=-1){
                mediaPlayer.pause();
                pp.setGraphic(new ImageView(imageplay));
                i=1;}
            else if(status == Status.PAUSED
                    || status == Status.READY
                    || status == Status.STOPPED){
                if (atEndOfMedia) {
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    atEndOfMedia = false;
                }
                mediaPlayer.play();
                pp.setGraphic(new ImageView(imagepause));
                i=0;
            }
            else if (Maincount==-1)
            {              try
            {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("music files(*.mp3)", "*.mp3");
                fileChooser.getExtensionFilters().add(extFilter);
                full = fileChooser.showOpenMultipleDialog(primaryStage);
                for(l=0;l<full.size();l++)
                    file.add(full.get(l));
                // System.out.println(file.get(0).toURI().toString());
                media = new Media(file.get(0).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaView = new MediaView(mediaPlayer);
                mediaPlayer.setAutoPlay(true);
                update();currplay++;
                if (sw == 0) {
                    np=new Label("Now Playing->");
                    nam =new Label(file.get(currplay).getName());
                    sw=1;}
                else if(sw==1){
                    nam.setText(file.get(currplay).getName());
                    np.setText("Now Playing->");
                }
                mediaView.setMediaPlayer(mediaPlayer);
                pp.setGraphic(new ImageView(imagepause));

                i=0;

                np.setFont(new Font("Arial", 15));
                np.setTextFill(Color.web("#FFFFFF"));
                np.setStyle("-fx-font-weight: bold;");
                nam.setFont(new Font("Arial", 12));
                nam.setTextFill(Color.web("#FFFFFF"));
                nam.setStyle("-fx-font-weight: bold;");
                layout3.getChildren().addAll(np,nam);
                for( l=-1;l<file.size();l++)
                    Maincount++;
                for( l=0;l<Maincount;l++)
                    data.add(new playlist(file.get(l).getName()));}
            catch (Exception ee){};}

        });

        // Add Play label
        playTime = new Label();
        // Add the volume label

        name.setCellValueFactory(new PropertyValueFactory<>("s"));
        playlst.getColumns().add(name);
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(100);
        volumeSlider.adjustValue(100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
                }
                else if(volumeSlider.isPressed()){
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });

        Button buttonLoad = new Button();
        playlst.setPlaceholder(new Label("No Songs In Playlist"));
        buttonLoad.setStyle("-fx-background-color: transparent");
        buttonLoad.setGraphic(new ImageView(imageload));
        buttonLoad.setOnAction((ActionEvent e)->{
            try
            {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("music files(*.mp3)", "*.mp3");
                fileChooser.getExtensionFilters().add(extFilter);
                full = fileChooser.showOpenMultipleDialog(primaryStage);
                for(l=0;l<full.size();l++)
                    file.add(full.get(l));
                System.out.println(file.get(0).toURI().toString());
                if(Maincount==-1)
                {media = new Media(file.get(0).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaView.setMediaPlayer(mediaPlayer);
                    mediaView = new MediaView(mediaPlayer);
                    mediaPlayer.setAutoPlay(true);
                    update();
                    currplay++;
                    pp.setGraphic(new ImageView(imagepause));
                    i=0;
                    if (sw == 0) {
                        nam =new Label(file.get(currplay).getName());
                        np=new Label("Now Playing->");
                        sw=1;}
                    else if(sw==1){
                        nam.setText(file.get(currplay).getName());
                        np.setText("Now Playing->");
                    }
                    np.setFont(new Font("Arial", 15));
                    np.setTextFill(Color.web("#FFFFFF"));
                    np.setStyle("-fx-font-weight: bold;");
                    nam.setFont(new Font("Arial", 12));
                    nam.setTextFill(Color.web("#FFFFFF"));
                    nam.setStyle("-fx-font-weight: bold;");
                    layout3.getChildren().addAll(np,nam);
                }
                prev=Maincount;
                for( l=prev;l<file.size();l++)
                    Maincount++;
                System.out.println(file.size());
                System.out.println(prev);
                if(prev==-1)
                    prev=0;
                for( l=prev;l<Maincount;l++)
                    data.add(new playlist(file.get(l).getName()));
                System.out.println(currplay);
                System.out.println(Maincount);}
            catch (Exception ee){};

        });
        VBox btm=new VBox(5);

// Add Play label
        Menu fileMenu=new Menu ("_After Playback                                                                                                                                                                     ");
        Menu About=new Menu("Help");
        MenuItem newMenuItem = new MenuItem("About");
        About.getItems().add(newMenuItem);
        newMenuItem.setOnAction(e->{
            Dialog.display("About","j-Shuffle version 1.0\nApplication made by: Abhinav Sharma\nFor any query/reporting bugs\nSend mail to:\n Sharma_abhinav@outlook.com\nAbhinav2061997@gmail.com");
        });
        shuf=new CheckMenuItem("Shuffle Play");
        nex=new CheckMenuItem("Play next item in playlist");
        shuf.setOnAction(e->{
            if(shuf.isSelected()){
                ri.setSelected(false);
                nex.setSelected(false);
                update();
            }
        });
        nex.setOnAction(e->{
            if(nex.isSelected()){
            ri.setSelected(false);
                shuf.setSelected(false);
                update();
            }});
        nex.setSelected(true);
        ri=new CheckMenuItem("Repeat song");
        ri.setOnAction(e->{
            if(ri.isSelected()){
                nex.setSelected(false);
                shuf.setSelected(false);
                update();
            }});
        fileMenu.getItems().addAll(nex,ri,shuf);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu,About);

        setTop(menuBar);

        Button rewind,forward,next,back;
        rewind=new Button();
        rewind.setOnAction((ActionEvent e) -> {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.seconds(5)));
        });
        forward=new Button() ;
        forward.setOnAction((ActionEvent e) -> {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(5)));
        });
        next=new Button();
        next.setOnAction((ActionEvent e) -> {
            try{
            if(shuf.isSelected()){
                shuff=ra.nextInt(file.size());
                currplay=shuff;
                mediaPlayer.stop();
                temp=file.get(currplay);
                media = new Media(temp.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaView = new MediaView(mediaPlayer);
                ft = new FadeTransition(Duration.millis(1000), layout);
                trans(layout);pp.setGraphic(new ImageView(imagepause));
                i=0;
                mediaPlayer.setAutoPlay(true);
                update();
                nam.setText(file.get(currplay).getName());
                np.setText("Now Playing->");
            }
            else {
            if(currplay+2<=Maincount){
            mediaPlayer.stop();
            currplay++;
            temp=file.get(currplay);
            media = new Media(temp.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView = new MediaView(mediaPlayer);
                trans(layout);pp.setGraphic(new ImageView(imagepause));
                i=0;
            mediaPlayer.setAutoPlay(true);
            update();
            nam.setText(file.get(currplay).getName());
            np.setText("Now Playing->");;}
        }}
        catch (Exception ee){;}});
        back=new Button();
        back.setOnAction((ActionEvent e) -> {try{
            if(mediaPlayer.getCurrentTime().toSeconds()>=3)
            {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                mediaPlayer.play();}
            else{
                if(shuf.isSelected()){
                    shuff=ra.nextInt(file.size());
                    currplay=shuff;
                    mediaPlayer.stop(); trans(layout);
                    temp=file.get(currplay);
                    media = new Media(temp.toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaView = new MediaView(mediaPlayer);
                    mediaPlayer.setAutoPlay(true);pp.setGraphic(new ImageView(imagepause));
                    i=0;
                    update();
                    nam.setText(file.get(currplay).getName());
                    np.setText("Now Playing->");

                }
                else {if(currplay-1>=0){
                    mediaPlayer.stop();
                    currplay--;
                    temp=file.get(currplay);
                    media = new Media(temp.toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaView = new MediaView(mediaPlayer);
                    mediaPlayer.setAutoPlay(true);pp.setGraphic(new ImageView(imagepause));
                    i=0;
                    update(); trans(layout);
                    nam.setText(file.get(currplay).getName());
                    np.setText("Now Playing->");}
                   }
            }


        }
        catch (Exception ee){}});
        rewind.setStyle("-fx-background-color: transparent");
        rewind.setGraphic(new ImageView(imageback));
        forward.setStyle("-fx-background-color: transparent");
        forward.setGraphic(new ImageView(imageforward));
        next.setStyle("-fx-background-color: transparent");
        next.setGraphic(new ImageView(imagenext));
        back.setStyle("-fx-background-color: transparent");
        back.setGraphic(new ImageView(imageprev));
        name.setCellFactory(new Callback<TableColumn<playlist, String>, TableCell<playlist, String>>() {
            @Override
            public TableCell<playlist, String> call(TableColumn<playlist, String> col) {
                final TableCell<playlist, String> cell = new TableCell<playlist, String>() {
                    @Override
                    public void updateItem(String firstName, boolean empty) {
                        super.updateItem(firstName, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(firstName);
                        }
                    }
                };
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() > 1) {
                            mediaPlayer.stop();
                            temp=file.get(cell.getIndex());
                            currplay=cell.getIndex();
                            media = new Media(temp.toURI().toString());
                            mediaPlayer = new MediaPlayer(media);
                            mediaView = new MediaView(mediaPlayer);
                            mediaPlayer.setAutoPlay(true);
                            update();
                            nam.setText(file.get(currplay).getName());
                            np.setText("Now Playing->");
                            // System.out.println("double click on "+cell.getIndex());
                        }
                    }
                });
                return cell ;
            }
        });
        Button list=new Button("Details");
        list.getStylesheets().add("but.css");
        list.setOnAction(e->{if(kk==1){
            list.setText("Details");
            ft = new FadeTransition(Duration.millis(1000), playlst);
            ft.setFromValue(0);
            ft.setToValue(1.0);
            ft.play();
            playlst.setVisible(true);
            setCenter(playlst);
            kk=0;}
        else if (kk==0){
            list.setText("Playlist");
            ft = new FadeTransition(Duration.millis(1000), meta);
            ft.setFromValue(0);
            ft.setToValue(1.0);
            ft.play();
            kk=1;
            setCenter(meta);

        }
        });


        VBox table=new VBox(5);
        playlst.setItems(data);
        table.getChildren().add(playlst);

        playlst.setVisible(true);
        setCenter(table);
        double sliderWidth = 700;
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider,Priority.ALWAYS);
        timeSlider.setMin(0);
        timeSlider.setMax(100);
        timeSlider.getStylesheets().add("SLIDER.css");

        ProgressBar pb = new ProgressBar();
        pb.setMinWidth(sliderWidth);
        pb.setMaxWidth(sliderWidth);

        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(timeSlider.getValue() / 100.0));
                }
                else if (timeSlider.isPressed()){
                    mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(timeSlider.getValue() / 100.0));
                }
            }
        });
        StackPane pane = new StackPane();

        pane.getChildren().addAll(pb, timeSlider);
        VBox lv=new VBox(0);
        HBox lvkeniche=new HBox();
        Label space=new Label("                                        ");
        Label space2=new Label("     ");
        Label space5=new Label("      ");
        lvkeniche.getChildren().addAll(space5,list);
        lv.getChildren().addAll(volumeSlider,lvkeniche);
        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.getChildren().addAll(pane);
        playTime.setFont(new Font("Arial", 15));
        playTime.setTextFill(Color.web("#FFFFFF"));
        playTime.setStyle("-fx-font-weight: bold;");

        layout.getChildren().addAll(back,rewind,pp,forward,next,buttonLoad,space2,lv,space,playTime);
        btm.getChildren().addAll(layout3,layout,pane);
        setBottom(btm);
    }

    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    playTime.setText(formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis()
                                * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume()
                                * 100));
                    }
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }
    void  update(){
        media.getMetadata().addListener(new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(Change<? extends String, ? extends Object> ch) {
                if (ch.wasAdded()) {
                    handleMetadata(ch.getKey(), ch.getValueAdded());
                }
            }
        });
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });
        mediaPlayer.setOnReady(new Runnable() {
            public void run() {
                duration = mediaPlayer.getMedia().getDuration();
                updateValues();
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run()
            {
                if(nex.isSelected()){
                    System.out.println("efrgrgrgrgrgrgrg");
                currplay++;
                temp=file.get(currplay);
                media = new Media(temp.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaView = new MediaView(mediaPlayer);
                mediaPlayer.setAutoPlay(true);
                    update();
                    np.setText("Now Playing->");
                    nam.setText(file.get(currplay).getName());
                    ri.setSelected(false);
            }
            else if(ri.isSelected()){
                nex.setSelected(false);
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    mediaPlayer.play();
                    update();
                }
            else if(shuf.isSelected()){
                    shuff=ra.nextInt(file.size());
                    System.out.println(shuff);
                    currplay=shuff;
                    mediaPlayer.stop();
                    temp=file.get(currplay);
                    media = new Media(temp.toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaView = new MediaView(mediaPlayer);
                    mediaPlayer.setAutoPlay(true);
                    update();
                    nam.setText(file.get(currplay).getName());
                    np.setText("Now Playing->");
                    layout3.getChildren().addAll(np,nam);
                }
        }});
    }
    private void handleMetadata(String key, Object value) {
        if (key.equals("album")) {
            album.setText("  Album--\n"+"  "+value.toString());
        } else if (key.equals("artist")) {
            artist.setText("  Artist--\n"+"  "+value.toString());
        } if (key.equals("title")) {
            title.setText("  Title--\n"+"  "+value.toString());
        } if (key.equals("year")) {
            year.setText("  Year--\n"+"  "+value.toString());
        } if (key.equals("image")) {
            albumCover.setImage((Image)value);
        }}
        void trans(Node x){
            ft = new FadeTransition(Duration.millis(1000), x);
            ft.setFromValue(0);
            ft.setToValue(1.0);
            ft.play();
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.play();
        }
}