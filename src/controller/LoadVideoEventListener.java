package controller;

import utilities.SQLAdapter;
import view.VideoSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Video;
import model.VideoCopy;
import model.Video.Categories;
import model.ObjectEvent;

public class LoadVideoEventListener implements ActionListener {
	VideoSystem _videoSystem;
	private Map<String, Video> _mVideo = new HashMap<String, Video>();
	
	public LoadVideoEventListener(VideoSystem aSystem) { 
		super();
		/*
		Video video1 = new Video();
		video1.init(1, "XXX", 100, Video.Categories.Comedy, 10, "2004", 10, null);
		
		Video video2 = new Video();
		video2.init(2, "LoveStory", 100, Video.Categories.Drama, 10, "2005", 10, null);
		
		Video video3 = new Video();
		video3.init(3, "SexyBoy", 100, Video.Categories.Family, 10, "2006", 10, null);
		
		_mVideo.put(Integer.toString(video1.getVideoID()), video1);
		_mVideo.put(Integer.toString(video2.getVideoID()), video2);
		_mVideo.put(Integer.toString(video3.getVideoID()), video3);
		/*/
		SQLAdapter sqlAdapter = SQLAdapter.getInstance();
		String[] mappingList = {"videoID", "title", "rentalcharge", "category", "rentalperiod", "yearrelease", "charge"};

		ArrayList<Map<String, String>> dataList = sqlAdapter.getData(mappingList, "select * from VIDEO");
		for (Map<String, String> mapVideo : dataList) {
			Video video = new Video();
			video.init(Integer.parseInt(mapVideo.get("videoID")), 
					mapVideo.get("title"), 
					Float.parseFloat(mapVideo.get("rentalcharge")), 
					Categories.valueOf(mapVideo.get("category")), 
					Integer.parseInt(mapVideo.get("rentalperiod")), 
					mapVideo.get("yearrelease"), 
					Float.parseFloat(mapVideo.get("charge")), 
					null);
			if (video != null)
				_mVideo.put(Integer.toString(video.getVideoID()), video);
		}
	}
	
	public Map<String, Video> getListOfVideos() {
		return this._mVideo;
	}
	
	public Map<String, Video> getSearchByKeywordListOfVideos(String aKeyword) {
		Map<String, Video> mVideoSearched = new HashMap<String, Video>();
		for (Map.Entry<String, Video> entry : this._mVideo.entrySet()) {
			Video video = entry.getValue();
			if (video.getTitle().contains(aKeyword)) {
				mVideoSearched.put(entry.getKey(), video);
			}
		}
		return mVideoSearched;
	}
	
	public Map<String, Video> getSearchByCategoryListOfVideos(Categories aCategory) {
		Map<String, Video> mVideoSearched = new HashMap<String, Video>();
		for (Map.Entry<String, Video> entry : this._mVideo.entrySet()) {
			Video video = entry.getValue();
			if (video.getCategories() == aCategory) {
				mVideoSearched.put(entry.getKey(), video);
			}
		}
		return mVideoSearched;
	}
	
	public ObjectEvent getVideoCopyFromVideoID(String aVideoID) {
		Video videoSelected = this._mVideo.get(aVideoID); 
		
		SQLAdapter sqlAdapter = SQLAdapter.getInstance();
		String[] mappingList = {"copyID", "datePurchase", "available", "damage", "noOfRent", "videoID"};

		ArrayList<Map<String, String>> dataList = sqlAdapter.getData(mappingList, "SELECT * FROM VIDEO_COPY WHERE videoID = " + aVideoID);
		
		VideoCopy copy = new VideoCopy();

		ObjectEvent objEvent = new ObjectEvent(); 
		objEvent.resultMessage = "no item";
		
		for (Map<String, String> mapVideo_Copy : dataList) {
			boolean isAvailable = mapVideo_Copy.get("available").equals("1")? true : false;
			boolean isDamaged = mapVideo_Copy.get("damage").equals("1")? true : false;
			int noOfRent = Integer.parseInt(mapVideo_Copy.get("noOfRent"));
			int copyID = Integer.parseInt(mapVideo_Copy.get("copyID"));
			videoSelected.setVideoCopy(copy);
			
			copy.init(copyID, mapVideo_Copy.get("datePurchase"), isDamaged, isAvailable, noOfRent);
			
			//check for available
			if (isAvailable) {
				//check for damage
				if (!isDamaged) {
					//check for over rental
					if (noOfRent < 100) {
						objEvent.isSuccessful = true;
						objEvent.objResult = videoSelected;
						return objEvent;

					} else {
						objEvent.resultMessage = "item is over rental";
						continue;
					}
				} else {
					objEvent.resultMessage = "item is damaged";
					continue;
				}
			} else {
				objEvent.resultMessage = "item unavailable";
				continue;
			}
		}
		return objEvent;
	}
	
	public boolean addVideo(String aTitle, float aRentalCharge, Categories aCategories, int aRentPeriod, String aYearRelease, float aOverdueCharge, VideoCopy aVideoCopy) {
		Video video = new Video();
		video.init(this._mVideo.size() + 1, aTitle, aRentalCharge, aCategories, aRentPeriod, aYearRelease, aOverdueCharge, aVideoCopy);
		_mVideo.put(Integer.toString(video.getVideoID()), video);
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
