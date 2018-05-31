package tv.programmes.controller;

import tv.programmes.App;

public abstract class Controller {
    protected App app;

    public abstract void initialize();
    public abstract void updateScene();

    public void setApp(App app) {
        this.app = app;
    }

    protected void switchToChannelList(){
		app.getRoot().setScene(app.getScenes().get("MainWindow"));
		app.getRoot().show();
    }
}
