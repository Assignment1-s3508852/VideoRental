package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import utilities.SQLAdapter;
import model.Video;

public class CheckVideoEventListener implements ActionListener {

	public CheckVideoEventListener() {
		super();
	}
	
	public boolean checkoutVideo() {
		return true;
	}
	
	public boolean checkReturnVideo() {
		return true;
	}
	
	public boolean rentVideo(Video aVideo) {		
		SQLAdapter sqlAdapter = SQLAdapter.getInstance();

		int copyID = aVideo.getVideoCopy().getCopyID();
		List<Object> objects = new ArrayList<Object>();
		objects.add(false);
		objects.add(copyID);
		if (sqlAdapter.updateTable("UPDATE VIDEO_COPY SET available = ? WHERE copyID = ?", objects))
			return true;
		return false;
	}
	
	public boolean returnVideo() {
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
