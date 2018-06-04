package tv.programmes.controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tv.programmes.*;
import tv.programmes.utils.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public abstract class Controller {
    protected App app;

    public abstract void initialize();
    public abstract void updateScene();

    public void setApp(App app) {
        this.app = app;
    }

	/**
	 * Switch to view MainWindowWindow to display the list of all channels.
	 */
    protected void switchToChannelList(){
		app.getRoot().setScene(app.getScenes().get("MainWindow"));
	    app.setCurrentController(app.getControllers().get("MainWindow"));
	    app.getRoot().show();
    }

	/**
	 * Switch to a listView, to display all days found in the xml file
	 */
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

	/**
	 * Switch to a listview, to display the list of actors, sorted by number of time credited in every emission
	 */
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

	/**
	 * Switch to listView to display the number of emission that has a certain rating per channel
	 */
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

	/**
	 * Switch to a listVie to display the number of emissions of a certain category, per day. It retrieves the list of days from the app.
	 */
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

	/**
	 * Switch to a listView to display the list of channels, but sorted by the medium age of films for the channel.
	 * The oldest the movies shown in a channel, the higher on the list the channel.
	 */
    protected void switchToSortedChannelList(){
	    app.getRoot().setScene(app.getScenes().get("List"));
	    ListWindowController controller = (ListWindowController) app.getControllers().get("List");
	    app.setCurrentController(controller);
    	int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    	HashMap<Channel, Integer> mediumAgeForEachChannel = new HashMap<>();
    	ArrayList<String> dataToPass = new ArrayList<>();


    	for(Channel c : app.getChannels()){
    		int sumOfAge= 0, numberOfFilms = 0;
    		for(Programmation p : c.getProgrammations()){
				if(p.getEmission().getCategory().equals("film") && p.getEmission().getYearOfRelease() != 0){
					numberOfFilms++;
					sumOfAge += currentYear - p.getEmission().getYearOfRelease();
				}
		    }
		    int mediumAge = (numberOfFilms != 0)? Math.round(sumOfAge/numberOfFilms):0;
		    mediumAgeForEachChannel.put(c, mediumAge);
	    }


	    while(!mediumAgeForEachChannel.isEmpty()) {
	    	Channel currentHighestChannel = (Channel) mediumAgeForEachChannel.keySet().toArray()[0];
		    for (Channel c : mediumAgeForEachChannel.keySet()){
			    if (mediumAgeForEachChannel.get(c) > mediumAgeForEachChannel.get(currentHighestChannel)) {
				    currentHighestChannel = c;
			    }
		    }
		    //System.out.println(" currentHighestChannel.getName() = " + currentHighestChannel.getName()+"\n size = "+channels.size());
		    dataToPass.add(currentHighestChannel.getName()+" medium age of movies : "+mediumAgeForEachChannel.get(currentHighestChannel)+" years");
		    mediumAgeForEachChannel.remove(currentHighestChannel);
	    }


	    controller.getModel().setDataList(dataToPass);
    }

	/**
	 * Internal function that is used to reuse a bit of code. It creates a new Stage, set it's owner as the app's root, and makes it modal.
	 * The goals is to have a popup.
	 * @return The Stage created.
	 */
    private Stage createPopup(){
	    Stage popupStage = new Stage();
	    popupStage.initOwner(app.getRoot());
	    popupStage.initModality(Modality.APPLICATION_MODAL);
	    return popupStage;
    }

	/**
	 * Creates a popup using createPopup(), instantiates it's controller, creates every elements of the popup and display them.
	 * Made specifically to ask for the name of someone.
	 */
	protected void createPopupCredit(){
		TextField input = new TextField();
		Button validateButton = new Button("Search");
		VBox popupBox = new VBox(20);
		popupBox.getChildren().addAll(new Label("Enter name :"), input, validateButton);
		Scene popupScene = new Scene(popupBox, 200, 120);
		Stage popupStage = createPopup();
		popupStage.setScene(popupScene);
		popupStage.show();
		PopUpControllerCredit popUpController = new PopUpControllerCredit(input, validateButton, this, popupStage);
	}

	/**
	 * Creates a popup using createPopup(), instantiates it's controller, creates every elements of the popup and display them.
	 * Made specifically to ask for the day and the month.
	 */
	protected void createPopupPeriod(){
		TextField dayInput = new TextField();
		TextField monthInput = new TextField();
		Button validateButton = new Button("Search");
		VBox popupBox = new VBox(15);
		HBox inputsBox = new HBox(5);
		inputsBox.getChildren().addAll(dayInput, monthInput);
		popupBox.getChildren().addAll(new Label("Enter period (dd MM) :"), inputsBox, validateButton);
		Stage popupStage = createPopup();
		Scene popupScene = new Scene(popupBox, 200, 120);
		popupStage.setScene(popupScene);
		popupStage.show();
		PopUpControllerPeriod popUpControllerPeriod = new PopUpControllerPeriod(dayInput, monthInput, validateButton, this, popupStage);
	}

	/**
	 * Switch to a listView to display every emission that has name in the credit.
	 * @param name the name to search for in the emission's credits.
	 */
	protected void switchToFindByCredit(String name){
		app.getRoot().setScene(app.getScenes().get("List"));
		ListWindowController controller = (ListWindowController) app.getControllers().get("List");
		app.setCurrentController(controller);
		ArrayList<String> dataToPass = new ArrayList<>();

		for(Emission e : app.getEmissions()){
			for(Role role : e.getCredits()){
				if(role.getName().contains(name)){
					dataToPass.add(role.getName() +" is credited as "+role.getRole()+" in " +e.shortDescription());
				}
			}
		}
		controller.getModel().setDataList(dataToPass);
	}

	/**
	 * Switch to a listView to display every programmations that is on the same day as entered by the user.
	 * @param day The day to search for
	 * @param month The month to search for
	 */
	protected void switchToFindByPeriod(int day, int month){
		app.getRoot().setScene(app.getScenes().get("List"));
		ListWindowController controller = (ListWindowController) app.getControllers().get("List");
		app.setCurrentController(controller);
		ArrayList<String> dataToPass = new ArrayList<>();

    	Date toSearch = new Date();
    	toSearch.setDay(day);
    	toSearch.setMonth(month);
    	toSearch.setYear(Calendar.getInstance().get(Calendar.YEAR));
		for(Programmation p : app.getProgrammations()){
			if(p.getStartDate().isSameDay(toSearch)){
				dataToPass.add(p.shortDescription());
			}
		}
		controller.getModel().setDataList(dataToPass);
	}
}
