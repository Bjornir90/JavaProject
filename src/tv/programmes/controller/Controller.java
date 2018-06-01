package tv.programmes.controller;

import tv.programmes.*;
import tv.programmes.utils.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
	    Role actorMostCredited;//The most credited actor for now, when we go through the HashMap one by one. We start by the first
	    for(Emission e : app.getEmissions()){
	    	for(Role role : e.getCredits()){
	    		if(role.isActor()){
	    			if(actors.containsKey(role)){
	    				Integer numberOfCredit = actors.get(role);
	    				numberOfCredit++;
	    				actors.replace(role, numberOfCredit);
				    } else {
	    				actors.put(role, 1);
				    }
			    }
		    }
	    }
	    while(!actors.isEmpty()) {//TODO optimize reading when the number of emission reach one, since then they are all equals
	    	actorMostCredited = (Role) actors.keySet().toArray()[0];//get first actor
		    for (Role role : actors.keySet()) {
			    if (actors.get(actorMostCredited) < actors.get(role)) {
				    actorMostCredited = role;
			    }
		    }
		    dataToPass.add(actorMostCredited.getName()+ " appears in "+ actors.get(actorMostCredited) +" emissions");//Finally add the actor name to the data to pass to the model
		    actors.remove(actorMostCredited);//remove so we can search for the next highest actor
	    }
	    controller.getModel().setDataList(dataToPass);
    }

    protected void switchToRatingList(){
	    app.getRoot().setScene(app.getScenes().get("List"));
	    ListWindowController controller = (ListWindowController) app.getControllers().get("List");
	    app.setCurrentController(controller);
	    ArrayList<String> dataToPass = new ArrayList<>();
	    HashMap<Channel, HashMap<String, Integer>> numberOfRatings = new HashMap<>();

	    for(Channel c : app.getChannels()){
	    	HashMap<String, Integer> ratingsForAChannel = new HashMap<>();
	    	for(Programmation p : c.getProgrammations()){
	    		String rating = p.getEmission().getRating();
	    		if(ratingsForAChannel.containsKey(rating)){
	    			Integer numberOfRating = ratingsForAChannel.get(rating);
	    			ratingsForAChannel.replace(rating, ++numberOfRating);
			    } else {
	    			ratingsForAChannel.put(rating, 1);
			    }
		    }
		    numberOfRatings.put(c, ratingsForAChannel);
	    }

	    for(Channel c : app.getChannels()){
	    	dataToPass.add(c.getName());
	    	for(String rating : numberOfRatings.get(c).keySet()){
	    		dataToPass.add("\t"+rating+" : "+numberOfRatings.get(c).get(rating));
		    }
	    }
	    controller.getModel().setDataList(dataToPass);
    }

    protected void switchToCategoryList(){
	    app.getRoot().setScene(app.getScenes().get("List"));
	    ListWindowController controller = (ListWindowController) app.getControllers().get("List");
	    app.setCurrentController(controller);
	    ArrayList<String> dataToPass = new ArrayList<>();
	    HashMap<Date, HashMap<String, Integer>> categoriesForEachDay = new HashMap<>();


	    ArrayList<Programmation> programmations = (ArrayList<Programmation>) app.getProgrammations().clone();//This will allow us to remove each element as it is treated, thus gaining a lot of time
	    for(Date d : app.getDays()) {
		    HashMap<String, Integer> categoriesForADay = new HashMap<>();
		    for (Iterator<Programmation> iterator = programmations.iterator(); iterator.hasNext();) {
		    	Programmation p = iterator.next();
			    String category = p.getEmission().getCategory();
			    Date dayForThisEmission = p.getStartDate().copyDay();//we only get the day because we want to compare only the days, not the exact time
			    if(dayForThisEmission.isSameDay(d)) {
				    if (categoriesForADay.containsKey(category)) {
					    Integer numberOfCategory = categoriesForADay.get(category);
					    categoriesForADay.replace(category, ++numberOfCategory);
				    } else {
					    categoriesForADay.put(category, 1);
				    }
				    iterator.remove();//Don't do it for each day, as a programmation can only exists for one day
			    }
		    }
		    categoriesForEachDay.put(d, categoriesForADay);
	    }
	    for(Date d : app.getDays()){
	    	dataToPass.add(d.getPrintableDay());
	    	for(String category : categoriesForEachDay.get(d).keySet()){
	    		dataToPass.add("\t"+ category +" : "+ categoriesForEachDay.get(d).get(category));
		    }
	    }
	    controller.getModel().setDataList(dataToPass);
    }
}
