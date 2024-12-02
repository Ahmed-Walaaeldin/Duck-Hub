package duckHub;

import duckHub.backend.*;
import duckHub.backend.database.Load;
import duckHub.backend.database.Save;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainDuck extends Application {
    private ArrayList<User> users = new ArrayList<>();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        User zoz = new User("zoz@zozTheKing.com","ZKING","3ab7amid3absalam", LocalDate.now());
        User naggar = new User("naggar@naggarElTop.com","NAGGAR","3ab7amid3absalam", LocalDate.now());
        User roupha = new User("roupha@elRegoula.com","ROUPHA","3ab7amid3absalam", LocalDate.now());
        User walaa = new User("walaa@01101011 01100101 01110011 01110011 01101111.com","WALAA@ElFoka3a","666666666666", LocalDate.now());
        users.add(zoz);
        users.add(naggar);
        users.add(roupha);
        users.add(walaa);

        Save save = new Save();
        save.saveToFile(users);

        Load load = new Load();
//        users = load.loadFromFile();
        System.out.println(users);
        for (User user : users) {
            System.out.println(user.getUserId());
            for (Post post : user.getPosts()) {
                System.out.println(post.getAuthorId());
                System.out.println(post.getContentImage().getUrl());
            }
        }
        Image image = new Image("/duckhub/frontend/soura.jpg");
        naggar.createContent(true,"This is a post",image);
//        MainScene mainScene = new MainScene();
//        mainScene.displayScene(naggar);

    }
}
