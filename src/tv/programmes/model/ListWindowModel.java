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

	public ListWindowModel(ArrayList<String> list){
		dataList = list;
	}

	public ArrayList<String> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<String> dataList) {
		this.dataList = dataList;
		controller.updateScene();
	}
}
