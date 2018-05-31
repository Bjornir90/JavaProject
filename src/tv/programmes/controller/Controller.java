package tv.programmes.controller;

import tv.programmes.App;
import tv.programmes.Emission;
import tv.programmes.Role;
import tv.programmes.utils.Date;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Controller {
    protected App app;

    public abstract void initialize();
    public abstract void updateScene();

    public void setApp(App app) {
        this.app = app;
    }

    protected void switchToChannelList(){
		app.getRoot().setScene(app.getScenes().get("MainWindow"));
	    app.setCurrentController(app.getControllers().get("MainWindow"));
	    app.getRoot().show();
    }

    protected void switchToCalendar(){
		app.getRoot().setScene(app.getScenes().get("List"));
		ListWindowController controller = (ListWindowController) app.getControllers().get("List");
		app.setCurrentController(controller);
	    ArrayList<String> dataToPass = new ArrayList<>();
		for(Date d : app.getDays()){
			dataToPass.add(d.getPrintableDay());
		}
		controller.getModel().setDataList(dataToPass);
    }

    protected void switchToActorList(){
    	app.getRoot().setScene(app.getScenes().get("List"));
    	ListWindowController controller = (ListWindowController) app.getControllers().get("List");
    	app.setCurrentController(controller);
	    ArrayList<String> dataToPass = new ArrayList<>();
	    HashMap<Role, Integer> actors = new HashMap<>();//Contains every actor, the values are the number of times he is credited
	    Role actorMostCredited = null;//The most credited actor for now, when we go through the HashMap one by one. We start by the first
	    for(Emission e : app.getEmissions()){
	    	for(Role role : e.getCredits()){
	    		if(role.isActor()){
				    System.out.println("role.getName() = " + role.getName());
	    			if(actors.containsKey(role)){
	    				Integer numberOfCredit = actors.get(role);
	    				numberOfCredit++;
	    				actors.replace(role, numberOfCredit);
				    } else {
	    				actors.put(role, 1);
				    }
				    System.out.println("actors.get(role) = " + actors.get(role));
			    }
		    }
	    }
	    while(!actors.isEmpty()) {
	    	actorMostCredited = (Role) actors.keySet().toArray()[0];//get first actor
		    for (Role role : actors.keySet()) {
		    	if(role == null) System.out.println("Role is null");
		    	if(actorMostCredited == null) System.out.println("Actor is null");
		    	if(actors.get(actorMostCredited) == null) System.out.println("Actor in actors is null");
		    	if(actors.get(role) == null) System.out.println("Role in actors is null");
			    if (actors.get(actorMostCredited) < actors.get(role)) {
				    actorMostCredited = role;
			    }
		    }
		    actors.remove(actorMostCredited);//remove so we can search for the next highest actor
		    dataToPass.add(actorMostCredited.getName());//Finally add the actor name to the data to pass to the model
	    }
	    controller.getModel().setDataList(dataToPass);
    }
}
