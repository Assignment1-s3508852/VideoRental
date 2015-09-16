package controller;

import view.VideoSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import model.Video;
import model.Video.Categories;

public class LoadVideoEventListener implements ActionListener {
	VideoSystem _videoSystem;
	private Map<String, Video> _mVideo = new HashMap<String, Video>();
	
	public LoadVideoEventListener(VideoSystem aSystem) {
		super();
		Video video1 = new Video();
		video1.init(1, "XXX", 100, Video.Categories.Comedy, 10, "2004", 10, null);
		
		Video video2 = new Video();
		video2.init(2, "LoveStory", 100, Video.Categories.Drama, 10, "2005", 10, null);
		
		Video video3 = new Video();
		video3.init(3, "SexyBoy", 100, Video.Categories.Family, 10, "2006", 10, null);
		
		_mVideo.put(Integer.toString(video1.getVideoID()), video1);
		_mVideo.put(Integer.toString(video2.getVideoID()), video2);
		_mVideo.put(Integer.toString(video3.getVideoID()), video3);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
