package tv.programmes.controller;

import tv.programmes.App;

public abstract class Controller {
    protected App app;

    protected abstract void initialize();

    public void setApp(App app) {
        this.app = app;
    }
}
