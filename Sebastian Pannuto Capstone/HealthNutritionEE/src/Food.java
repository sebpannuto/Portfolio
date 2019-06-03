//Created by Seb Pannuto
public class Food {
    private String name;
    private String foodGroup;
    private double protein;
    private double calcium;
    private double sodium;
    private double fiber;
    private double vitaminc;
    private double potassium;
    private double carbohydrate;
    private double sugars;
    private double fat;
    private double water;
    private int calories;
    private double saturated;
    private double monounsat;
    private double polyunsat;
    private int id;
    
    private boolean selected;
    
    public Food(){}
    
    public Food(String N, String FG, double pn, double c, double s, double f, double vc, double pm,
            double cb, double sg, double ft, double w, int cs, double sat, double mono, double poly, int I){
        name = N;
        foodGroup = FG;
        protein = pn;
        calcium = c;
        sodium = s;
        fiber = f;
        vitaminc = vc;
        potassium = pm;
        carbohydrate = cb;
        sugars = sg;
        fat = ft;
        water = w;
        calories = cs;
        saturated = sat;
        monounsat = mono;
        polyunsat = poly;
        id = I;
      
    }
    
    //Name
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    //Food Group
    public String getFoodGroup() {
    	return foodGroup;
    }
    
    public void setFoodGroup(String foodGroup) {
    	this.foodGroup = foodGroup;
    }
    
    //Protein
    public double getProtein() {
    	return protein;
    }
    
    public void setProtein(double protein) {
    	this.protein = protein;
    }
    
    //Calcium
    public double getCalcium() {
    	return calcium;
    }
    
    public void setCalcium(double calcium) {
    	this.calcium = calcium;
    }
    
    //Sodium
    public double getSodium() {
    	return sodium;
    }
    
    public void setSodium(double sodium) {
    	this.sodium = sodium;
    }
    
    //Fiber
    public double getFiber() {
    	return fiber;
    }
    
    public void setFiber(double fiber) {
    	this.fiber = fiber;
    }
    
    //VitaminC
    public double getVitaminC() {
    	return vitaminc;
    }
    
    public void setVitaminC(double vitaminc) {
    	this.vitaminc = vitaminc;
    }
    
    //Potassium
    public double getPotassium() {
    	return potassium;
    }
    
    public void setPotassium(double potassium) {
    	this.potassium = potassium;
    }
    
    //Carbohydrates
    public double getCarbohydrate() {
    	return carbohydrate;
    }
    
    public void setCarbohydrate(double carbohydrate) {
    	this.carbohydrate = carbohydrate;
    }
    
    //Sugar
    public double getSugars() {
    	return sugars;
    }
    
    public void setSugars(double sugars) {
    	this.sugars = sugars;
    }
    
    //Fat
    public double getFat() {
    	return fat;
    }
    
    public void setFat(double fat) {
    	this.fat = fat;
    }
    
    //Water
    public double getWater() {
    	return water;
    }
    
    public void setWater(double water) {
    	this.water = water;
    }
    
    //Calories
    public int getCalories() {
    	return calories;
    }
    
    public void setCalories(int calories) {
    	this.calories = calories;
    }
    
    //Saturated
    public double getSaturated() {
    	return saturated;
    }
    
    public void setSaturated(double saturated) {
    	this.saturated = saturated;
    }
    
    //Monounsat
    public double getMonounsat() {
    	return monounsat;
    }
    
    public void setMonounsat(double monounsat) {
    	this.monounsat = monounsat;
    }
    
    //Polyunsat
    public double getPolyunsat() {
    	return polyunsat;
    }
    
    public void setPolyunsat(double polyunsat) {
    	this.polyunsat = polyunsat;
    }
    
    //ID
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public boolean isSelected() {
    	return selected;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public String toString() {
    	return name + "*" + foodGroup + "*" + protein + "*" + calcium + "*" + sodium + "*" + fiber + "*" + vitaminc + "*" + potassium + 
    			"*" + carbohydrate + "*" + sugars + "*" + fat + "*" + water + "*" + calories + "*" + saturated + "*" + 
    			monounsat + "*" + polyunsat + "*" + id + "*";
    }
    
}
