package controller;

import utilities.SQLAdapter;
import view.VideoSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Video;
import model.VideoCopy;
import model.Video.Categories;
import model.ObjectEvent;

public class LoadVideoEventListener implements ActionListener {
	VideoSystem _videoSystem;
	private Map<String, Video> _mVideo = new HashMap<String, Video>();
	private Map<String, VideoCopy> _mVideoCopy = new HashMap<String, VideoCopy>();
	
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
		this.loadVideo(sqlAdapter);
		this.loadVideoCopy(sqlAdapter);		
		//*/
	}
	
	public Map<String, Video> getListOfVideos() {
		return this._mVideo;
	}
	
	public Map<String, VideoCopy> getListOfVideoCopys () {  
		return this._mVideoCopy;
	}
	
	public Video getVideo(String aVideoID) {
		if (this._mVideo.containsKey(aVideoID))
			return this._mVideo.get(aVideoID);
		return null;
	}
	
	public VideoCopy getVideoCopy (String aVideoCopy) {
		if (this._mVideoCopy.containsKey(aVideoCopy))
			return this._mVideoCopy.get(aVideoCopy);
		return null;
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
				
		ObjectEvent objEvent = new ObjectEvent(); 
		objEvent.resultMessage = "no item";
		
		for (Map.Entry<String, VideoCopy> entry : this._mVideoCopy.entrySet()) {
			VideoCopy videoCopy = entry.getValue();
			if (videoSelected.getVideoID() == videoCopy.getVideoID()) {
				boolean isAvailable = videoCopy.isAvailale();
				boolean isDamaged = videoCopy.isDamage();
				int noOfRent = videoCopy.getNoOfRent();
				videoSelected.setVideoCopy(videoCopy);
				
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
							objEvent.resultMessage = " is over rental";
							continue;
						}
					} else {
						objEvent.resultMessage = " is damaged";
						continue;
					}
				} else {
					objEvent.resultMessage = " unavailable";
					continue;
				}	
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

	private void loadVideo(SQLAdapter aAdapter) {
		String[] mappingVideoList = {"videoID", "title", "rentalcharge", "category", "rentalperiod", "yearrelease", "charge"};

		ArrayList<Map<String, String>> dataVideoList = aAdapter.getData(mappingVideoList, "select * from VIDEO");
		for (Map<String, String> mapVideo : dataVideoList) {
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
	
	private void loadVideoCopy(SQLAdapter aAdapter) {
		String[] mappingVideoCopyList = {"copyID", "datePurchase", "available", "damage", "noOfRent", "videoID"};

		ArrayList<Map<String, String>> dataVideoCopyList = aAdapter.getData(mappingVideoCopyList, "SELECT * FROM VIDEO_COPY");
				
		for (Map<String, String> mapVideo_Copy : dataVideoCopyList) {
			VideoCopy videoCopy = new VideoCopy();
			boolean isAvailable = mapVideo_Copy.get("available").equals("1")? true : false;
			boolean isDamaged = mapVideo_Copy.get("damage").equals("1")? true : false;
			int noOfRent = Integer.parseInt(mapVideo_Copy.get("noOfRent"));
			int copyID = Integer.parseInt(mapVideo_Copy.get("copyID"));
			videoCopy.init(copyID, 
					mapVideo_Copy.get("datePurchase"), 
					isDamaged, 
					isAvailable, 
					noOfRent, 
					Integer.parseInt(mapVideo_Copy.get("videoID")));
			if (videoCopy != null)
				_mVideoCopy.put(Integer.toString(videoCopy.getCopyID()), videoCopy);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
