import java.util.*;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class MealPlan {
	private String name;
	private User user;
	private List<Food> FoodList = new ArrayList<Food>();
	
    private double totalProtein = 0;
    private double totalCalcium = 0;
    private double totalSodium = 0;
    private double totalFiber = 0;
    private double totalVitaminc = 0;
    private double totalPotassium = 0;
    private double totalCarbohydrate = 0;
    private double totalSugars = 0;
    private double totalFat = 0;
    private double totalWater = 0;
    private int totalCalories = 0;
    private double totalSaturated = 0;
    private double totalMonounsat = 0;
    private double totalPolyunsat = 0;
	
	
	public MealPlan() {}
	
	public MealPlan(String name, User user) {
		this.name = name;
		this.user = user;
		FoodList.clear();
	}
	
	//Name
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	//User
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	//Food List
	public int addFood(Food item) {
		if(FoodList.add(item)) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public int removeFood(Food item) {
		if(FoodList.remove(item)) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public void clearFoodList() {
		FoodList.clear();
	}
	
	public List<Food> getFoodList(){
		return FoodList;
	}
	
	public void setFoodList(List<Food> FoodList) {
		this.FoodList = FoodList;
	}
	
	//Totals
	public double getTotalProtein() {
		return this.totalProtein;
	}
	
	public double getTotalCalcium() {
		return this.totalCalcium;
	}
	
	public double getTotalSodium() {
		return this.totalSodium;
	}
	
	public double getTotalFiber() {
		return this.totalFiber;
	}
	
	public double getTotalVitaminC() {
		return this.totalVitaminc;
	}
	
	public double getTotalPotassium() {
		return this.totalPotassium;
	}
	
	public double getTotalCarbohydrates() {
		return this.totalCarbohydrate;
	}
	
	public double getTotalSugars() {
		return this.totalSugars;
	}
	
	public double getTotalFat() {
		return this.totalFat;
	}
	
	public double getTotalWater() {
		return this.totalWater;
	}
	
	public int getTotalCalories() {
		return this.totalCalories;
	}
	
	public double getTotalSaturated() {
		return this.totalSaturated;
	}
	
	public double getTotalMonounsat() {
		return this.totalMonounsat;
	}
	
	public double getTotalPolyunsat() {
		return this.totalPolyunsat;
	}
	
	public void setTotals() {
		for(int i = 0; i < FoodList.size(); i++) {
			totalProtein += FoodList.get(i).getProtein();
			totalCalcium += FoodList.get(i).getCalcium();
		    totalSodium += FoodList.get(i).getSodium();
		    totalFiber += FoodList.get(i).getFiber();
		    totalVitaminc += FoodList.get(i).getVitaminC();
		    totalPotassium += FoodList.get(i).getPotassium();
		    totalCarbohydrate += FoodList.get(i).getCarbohydrate();
		    totalSugars += FoodList.get(i).getSugars();
		    totalFat += FoodList.get(i).getFat();
		    totalWater += FoodList.get(i).getWater();
		    totalCalories += FoodList.get(i).getCalories();
		    totalSaturated += FoodList.get(i).getSaturated();
		    totalMonounsat += FoodList.get(i).getMonounsat();
		    totalPolyunsat += FoodList.get(i).getPolyunsat();
		}
		
	}
}
