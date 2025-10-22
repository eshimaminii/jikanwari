package model;

import java.time.LocalDate;
import java.util.List;

public class Event {
	private int event_id;
	private String title;
	private LocalDate date;
	private String description;
	private boolean repeat_flag;
	private String color_id;
	private boolean delete_flag;
	private int startHour; //開始時
	private int startMinute;//開始分
	private int durationMinutes; //継続時間（分）
	private String user_id;
	

	public Event() {}
	
	public Event(int event_id, String title, LocalDate date, String description, boolean repeat_flag, String color_id,
			boolean delete_flag) {
		this.event_id = event_id;
		this.title = title;
		this.date = date;
		this.description = description;
		this.repeat_flag = repeat_flag;
		this.color_id = color_id;
		this.delete_flag = delete_flag;
	}
	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isRepeat_flag() {
		return repeat_flag;
	}
	public void setRepeat_flag(boolean repeat_flag) {
		this.repeat_flag = repeat_flag;
	}
	public boolean isDelete_flag() {
		return delete_flag;
	}
	public void setDelete_flag(boolean delete_flag) {
		this.delete_flag = delete_flag;
	}
	
	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}

	public int getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public void setColor_id(String color_id) {
		this.color_id = color_id;
	}

	public String getColor_id() {
		return color_id;
	}
	
	private List<Integer> weekdayIds;

	public List<Integer> getWeekdayIds() {
		return weekdayIds;
	}
	public void setWeekdayIds(List<Integer> weekdayIds) {
		this.weekdayIds = weekdayIds;
	}

	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
