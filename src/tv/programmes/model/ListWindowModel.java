package tv.programmes.model;

import tv.programmes.controller.ListWindowController;

import java.util.ArrayList;

public class ListWindowModel {
	private ArrayList<String> dataList;
	private ListWindowController controller;

	public ListWindowModel(ListWindowController controller){
		dataList = new ArrayList<>();
		this.controller = controller;
	}

	public ArrayList<String> getDataList() {
		return dataList;
	}

	/**
	 * Setter for the data that will be displayed in a list view. It calls it's controller's updateScene(), that set the scene of the application.
	 * @param dataList the list of Strings to display
	 */
	public void setDataList(ArrayList<String> dataList) {
		this.dataList = dataList;
		controller.updateScene();
	}
}
