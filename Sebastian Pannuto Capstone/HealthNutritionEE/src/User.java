public class User {

	private String username;
	private String password;
	private int id;
	
	private MealPlan Plan;
	
	private Goal goals;

	private int weight;
	private int height;
	private double BMI;
	
	public User() {}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		weight = 0;
		height = 0;
	}
	
	//Username
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	//Password
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	//get ID
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	//Add Meal Plan
	
	public void addMealPlan(MealPlan item) {
		
	}
	/**
	//Remove Meal Plan
	public void removeMealPlan(MealPlan item) {
		Plans.remove(item);
	}
	**/
	
	public MealPlan getMealPlan() {
		return Plan;
	}
	
	public void setMealPlans(MealPlan Plan) {
		this.Plan = Plan;
	}
	

	//Weight
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getWeight() {
		return weight;
	}
	
	//Height
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setHeight(int feet, int inches) {
		this.height = feet * 12 + inches;
	}
	
	public int getHeight() {
		return height;
	}
	
	//BMI
	public void setBMI(double BMI) {
		this.BMI = BMI;
	}
	
	public void setBMI() {
		this.BMI = (weight / Math.pow(height, 2)) * 703;
	}
	
	public double getBMI() {
		return BMI;
	}
	
	public Goal getGoal() {
		return this.goals;
	}
	
	public void setGoal(Goal g) {
		this.goals = g;
	}
}
