package mission;

public class Record {
	private int taskId;
	private String title;
	private String name;
	private int starTakeMission;
	private int starPostMission;

	private boolean take;

	public Record(int taskId, String title, String name, int starTakeMission, int starPostMission, boolean take) {
		super();
		this.taskId = taskId;
		this.title = title;
		this.name = name;
		this.starTakeMission = starTakeMission;
		this.starPostMission = starPostMission;
		this.take = take;
	}
	
	
}
