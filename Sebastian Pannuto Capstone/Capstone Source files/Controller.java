
import javax.faces.bean.ManagedBean;
import java.sql.*;
import javax.faces.bean.SessionScoped;

import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SebPannuto
 */
@ManagedBean(name = "searchController")
@SessionScoped
public class Controller {
    
    private String search;
    private String unm = "";
    private String pwd = "";
    private String pwd2 = "";
    
    private User currentUser;
    
    private List<Food> Results = new ArrayList<Food>();
    private Map<Integer, Boolean> checked = new HashMap<Integer, Boolean>();
    private Map<Integer, Boolean> checked2 = new HashMap<Integer, Boolean>();
    private List<Food> MealPlanItems = new ArrayList<Food>();
    private String newMealPlanName = "";
    private boolean creatingMP = false;
    
    public String executeSearch() {
        Results.clear();
        checked.clear();
        try{
        	System.out.println("Executing Search");
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs;
            
            
            System.out.println("Working");
            
            //Pulls Data from database
            String query = "SELECT * FROM spannuto10.FoodData WHERE name LIKE '" + search + "%'";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            System.out.println("1");
            
            while ( rs.next() ) {
                Results.add(new Food(rs.getString("name"), rs.getString("group"), rs.getDouble("protein (g)"), rs.getDouble("calcium (g)")
                		, rs.getDouble("sodium (g)"), rs.getDouble("fiber (g)"), rs.getDouble("vitaminc (g)"), rs.getDouble("potassium (g)")
                		, rs.getDouble("carbohydrate (g)"), rs.getDouble("sugars (g)"), rs.getDouble("fat (g)"), rs.getDouble("water (g)")
                		, rs.getInt("calories"), rs.getDouble("saturated (g)"), rs.getDouble("monounsat (g)"), rs.getDouble("polyunsat (g)")
                		, rs.getInt("id")));
            }
           
            for (int i = 0; i < Results.size(); i++) {
            	System.out.println(Results.get(i).toString());
            }
            System.out.println("Finished");
        }
        catch(Exception e){
            System.out.println("ERROR IN EXECUTION");
            e.printStackTrace();
        }
        
        //Used for setting checkbox values to false
        for (int i = 0; i < Results.size(); i++) {
        	checked.put(Results.get(i).getId(), Boolean.FALSE);
        }
        
        for(Integer id: checked.keySet()) {
        	String key = id.toString();
        	String value = checked.get(id).toString();
        	System.out.println(key + " " + value);
        }
        if(creatingMP) {
        	return "addFoods";
        }
        else {
        	return "result";
        }
    }
    
    //Login Data
    public String validateUsernamePassword() {
		String query = "Select username, password from spannuto10.Users where username = '" + unm + "' and password = '" + pwd +"'";
		
		try {
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				stmt.close();
				currentUser = new User(unm,pwd);
				createCurrentUser(currentUser);
				System.out.println("Retrieving DB");
				List<Food> M = new ArrayList<Food>();
				M = getDBMealPlan(con);
				System.out.println("Returned");
				if (M != null) {
					currentUser.setMealPlans(new MealPlan(newMealPlanName, currentUser));
					currentUser.getMealPlan().setFoodList(M);
					currentUser.getMealPlan().setTotals();
				}
				getGoals(con);
				return "userPage";
			}
			
			
		} 
		catch (SQLException ex) { 
			System.out.println("Login error -->" + ex.getMessage());
			return ""; 
		}
		
		return "";
	}
    
    public String createUser() {
    	try {
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			if(pwd.contentEquals(pwd2)) {
				String query = "insert into spannuto10.Users values('" + unm + "','" + pwd + "')";
				stmt.executeUpdate(query);
				currentUser = new User(unm,pwd);
				createCurrentUser(currentUser);
				return "userPage";
			}
			else {
				System.out.println("Passwords did not match up");
			}
			
		} 
		catch (SQLException ex) { 
			System.out.println("Login error -->" + ex.getMessage());
			return "";
		}

    	
    	return "userPage";
    }
    
    //Adds checkboxed items to mealplan list
    public String addToMealPlan() {

    	for(Integer id: checked.keySet()) {
        	String key = id.toString();
        	String value = checked.get(id).toString();
        	System.out.println(key + " " + value);
        }                                                                                                                                                                                                                                                                                                                                                                                  
    	
    	List<Integer> ids = new ArrayList<Integer>();
    	
    	/**
    	for (Entry<Integer,Boolean> entry : checked.entrySet()) {
    		if(entry.getValue()) {
    			ids.add(entry.getKey());
    		}
    	}
    	**/
    	
    	for(Integer id: checked.keySet()) {
        	if(checked.get(id)) {
        		ids.add(id); 
        	}
        }
    	
    	for (int i = 0; i < ids.size(); i++) {
    		int j = 0;
    		boolean added = false;
    		while (j < Results.size() && !added) {
    			if(ids.get(i).intValue() == Results.get(j).getId()) {
        			MealPlanItems.add(Results.get(j));
        			added = true;
        		}
    			j++;
    		}
    	}
    	
    	System.out.println("Added Items:");
    	for (int i = 0; i < MealPlanItems.size(); i++) {
    		System.out.println(MealPlanItems.get(i).toString());
    	}
    	ids.clear();
    	checked.clear();
        return "addFoods";
    }
    
    public String removeFromMealPlan() {
    	for(Integer id: checked2.keySet()) {
        	String key = id.toString();
        	String value = checked2.get(id).toString();
        	System.out.println(key + " " + value);
        }                                                                                                                                                                                                                                                                                                                                                                                  
    	
    	List<Integer> ids = new ArrayList<Integer>();
    	
    	/**
    	for (Entry<Integer,Boolean> entry : checked.entrySet()) {
    		if(entry.getValue()) {
    			ids.add(entry.getKey());
    		}
    	}
    	**/
    	
    	for(Integer id: checked2.keySet()) {
        	if(checked2.get(id)) {
        		ids.add(id); 
        	}
        }
    	
    	for (int i = 0; i < ids.size(); i++) {
    		int j = 0;
    		while (j < MealPlanItems.size()) {
    			if(ids.get(i).intValue() == MealPlanItems.get(j).getId()) {
    				MealPlanItems.remove(j);
        		}
    			j++;
    		}
    	}
    	
    	for (int i = 0; i < MealPlanItems.size(); i++) {
    		System.out.println(MealPlanItems.get(i).toString());
    	}
    	
    	ids.clear();
    	checked2.clear();
    	return "addFoods";
    }
    
    //Returns Connection with Database
    public static Connection getConnection(){
    	String URL = "jdbc:mysql://healthnutritiondb.c1vujb8ox4hr.us-east-1.rds.amazonaws.com:3306/spannuto10";
        try{
            System.out.println("Starting Connection");
            
            Class.forName("com.mysql.jdbc.Driver");
            //Connection conn = DriverManager.getConnection(URL, "spannuto10", "Baseball10");

            Connection conn = DriverManager.getConnection(URL, "spannuto10", "Baseball10");


            return conn;
        }
        catch(Exception e){
            System.out.println("ERORR IN CONNECTION");
            e.printStackTrace();
        }
        return null;
    }
    
    //Getter and Setter for Search
    public String getSearch(){
        return search;
    }
    public void setSearch(String search){
        this.search = search;
    }
    
    public List<Food> getResults(){
        return this.Results;
    }
    
    public String printResult(){
        return "Done";
    }
    
    public String getUnm() {
    	return this.unm;
    }
    
    public void setUnm(String unm) {
    	this.unm = unm;
    }

    public String getPwd() {
    	return this.pwd;
    }
    
    public void setPwd(String pwd) {
    	this.pwd = pwd;
    }
    
    public String getPwd2() {
    	return this.pwd2;
    }
    
    public void setPwd2(String pwd2) {
    	this.pwd2 = pwd2;
    }
    
    public Map<Integer, Boolean> getChecked(){
    	return this.checked;
    }
    
    public Map<Integer, Boolean> getChecked2(){
    	return this.checked2;
    }
    
    public User getUser() {
    	return currentUser;
    }
    
    public String getNewMealPlanName() {
    	return this.newMealPlanName;
    }
    
    public void setNewMealPlanName(String newMealPlanName) {
    	this.newMealPlanName = newMealPlanName;
    }
    
    public List<Food> getMealPlanItems() {
    	return this.MealPlanItems;
    }
    
    public String FinishMealPlan() {
    	currentUser.getMealPlan().clearFoodList();
    	
    	for (int i = 0; i < MealPlanItems.size(); i++) {
    		currentUser.getMealPlan().addFood(MealPlanItems.get(i));
    	}
    	
    	
    	MealPlanItems.clear();
    	currentUser.getMealPlan().setTotals();
    	newMealPlanName = "";
    	
    	creatingMP = false;
    	
    	//adds meal plan to database
    	String result = prepFood();
    	
    	try {
    		String query = "update spannuto10.Users set mealplan = '" + result + "', mpname = '" + currentUser.getMealPlan().getName() + "' where username = '" + currentUser.getUsername() + "'";
    		Connection con = getConnection();
    		Statement stmt = con.createStatement();
    		stmt.executeUpdate(query);
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	
    	System.out.println(result);
    	
    	return "mealPlan";
    }
    
    
    //Updates values from database to use in program
    public void createCurrentUser(User user) {
    	try {
    		String query = "select weight, height, idUsers from spannuto10.Users where username = '" + unm + "'";
    		Connection con = getConnection();
    		Statement stmt1 = con.createStatement();
    		ResultSet rs1 = stmt1.executeQuery(query);
    		
    		if(rs1.next()) {
    			currentUser.setWeight(rs1.getInt("weight"));
    			currentUser.setHeight(rs1.getInt("height"));
    			currentUser.setId(rs1.getInt("idUsers"));
    		}
    		currentUser.setBMI();
    	}
    	catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public void UpdateUserDatabase(int weight, int height) {
		currentUser.setWeight(weight);
		currentUser.setHeight(height);
		currentUser.setBMI();
    	try {
    		String query = "update spannuto10.Users set weight = " + currentUser.getWeight() + ", height = " + currentUser.getHeight() + 
    				" where username = '" + currentUser.getUsername() + "'" ;
    		Connection con = getConnection();
    		Statement stmt = con.createStatement();
    		stmt.execute(query);
    	}
    	catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }

    public String createNewMealPlan(String name) {
    	
    	 if(currentUser.getMealPlan() != null) {
    		currentUser.setMealPlans(null);
    	}
    	currentUser.setMealPlans(new MealPlan(name, currentUser));
    	creatingMP = true;
    	
    	return "addFoods";
    }

    public String editMealPlan() {
    	List<Food> foods = getUser().getMealPlan().getFoodList();
    	for (int i = 0; i < foods.size(); i++) {
    		MealPlanItems.add(foods.get(i));
    	}
    	creatingMP = true;
    	return "addFoods";
    }

    public void deleteMealPlan() {
    	try {
    		String query = "update spannuto10.Users set mpname = null, mealplan = null where mpname = '" + currentUser.getMealPlan().getName() + "'";
    		Connection con = getConnection();
    		Statement stmt = con.createStatement();
    		stmt.executeUpdate(query);
    	}
    	catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	currentUser.setMealPlans(null);
    }

    public String prepFood() {
    	String input = "";
    	for(int i = 0; i < currentUser.getMealPlan().getFoodList().size(); i++) {
    		input += currentUser.getMealPlan().getFoodList().get(i).toString();
    	}
    	return input;
    }

    public List<Food> getDBMealPlan(Connection con) {
    	String result = "";
    	String query = "Select mealplan, mpname from spannuto10.Users where username = '" + currentUser.getUsername() + "'";
    	try {
    		Statement stmt = con.createStatement();
    		ResultSet rs = stmt.executeQuery(query);
    		if(rs.next()) {
    			result = rs.getString("mealplan");
    			newMealPlanName = rs.getString("mpname");
    		}
    	}
    	catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	System.out.println("returning null");
    	if(result == null) {
    		return null;
    	}
    	
    	System.out.println("Starting parsing");
    	boolean completeWord = false;
    	int index = 0;
    	int counter = 0;
    	String name = "";
        String foodGroup = "";
        double protein = 0;
        double calcium = 0;
        double sodium = 0;
        double fiber = 0;
        double vitaminc = 0;
        double potassium = 0;
        double carbohydrate = 0;
        double sugars = 0;
        double fat = 0;
        double water = 0;
        int calories = 0;
        double saturated = 0;
        double monounsat = 0;
        double polyunsat = 0;
        int id = 0;
        
        List<Food> M = new ArrayList<Food>();
        String temp = "";
    	while (index < result.length()) {
    		System.out.println("in loop");
    		completeWord = false;
    		//grabs the food from the string
    		while (!completeWord && index < result.length()) {
    			System.out.println(index);
    			if(result.substring(index, index+1).equals("*")) {
    				switch(counter) {
    				case 0:
    					name = temp;
    					System.out.println("Complete: " + name);
    					break;
    				case 1:
    					foodGroup = temp;
    					System.out.println("Complete: " + foodGroup);
    					break;
    				case 2:
    					protein = Double.parseDouble(temp);
    					System.out.println("Complete: " + protein);
    					break;
    				case 3:
    					calcium = Double.parseDouble(temp);
    					System.out.println("Complete: " + calcium);
    					break;
    				case 4:
    					sodium = Double.parseDouble(temp);
    					System.out.println("Complete: " + sodium);
    					break;
    				case 5:
    					fiber = Double.parseDouble(temp);
    					System.out.println("Complete: " + fiber);
    					break;
    				case 6:
    					vitaminc = Double.parseDouble(temp);
    					System.out.println("Complete: " + vitaminc);
    					break;
    				case 7:
    					potassium = Double.parseDouble(temp);
    					System.out.println("Complete: " + potassium);
    					break;
    				case 8:
    					carbohydrate = Double.parseDouble(temp);
    					System.out.println("Complete: " + carbohydrate);
    					break;
    				case 9:
    					sugars = Double.parseDouble(temp);
    					System.out.println("Complete: " + sugars);
    					break;
    				case 10:
    					fat = Double.parseDouble(temp);
    					System.out.println("Complete: " + fat);
    					break;
    				case 11:
    					water = Double.parseDouble(temp);
    					System.out.println("Complete: " + water);
    					break;
    				case 12:
    					calories = Integer.parseInt(temp);
    					System.out.println("Complete: " + calories);
    					break;
    				case 13:
    					saturated = Double.parseDouble(temp);
    					System.out.println("Complete: " + saturated);
    					break;
    				case 14:
    					monounsat = Double.parseDouble(temp);
    					System.out.println("Complete: " + monounsat);
    					break;
    				case 15:
    					polyunsat = Double.parseDouble(temp);
    					System.out.println("Complete: " + polyunsat);
    					break;
    				case 16:
    					id = Integer.parseInt(temp);
    					System.out.println("Complete: " + id);
    					break;
    				default:
    					break;
    				}
    				System.out.println("Counter: " + counter);
    				if (counter >= 16) {
    					counter = 0;
    					completeWord = true;
    				}
    				else {
    					counter++;
    				}
    				temp = "";
    			}
    			else {
    				temp += result.substring(index, index+1);
    			}
    			index++;
    			System.out.println(temp);
    		}
    		M.add(new Food(name, foodGroup, protein, calcium, sodium, fiber, vitaminc, potassium, carbohydrate, sugars, fat, water, calories, saturated,
    				monounsat, polyunsat, id));
    		System.out.println("Foods: ");
    		System.out.println(name + " " + foodGroup + " " + id);
    	}
    	
    	System.out.println("Foods Retrieved:");
    	for (int i = 0; i < M.size(); i++) {
    		System.out.println(M.get(i).toString());
    	}
    	
    	return M;
    }

    public void getGoals(Connection con) {
    	try {
    		String query = "select goalProtein, goalCalcium, goalSodium, goalFiber, goalVitaminc, goalPotassium, goalCarbs, goalSugars, goalFat, goalWater, goalCalories, goalSaturated, goalMono, goalPoly from spannuto10.Users where username = '"
    				+ currentUser.getUsername() + "'";
    		Statement stmt = con.createStatement();
    		System.out.println("HELLO");
    		ResultSet rs = stmt.executeQuery(query);
    		if(rs.next()) {
    			Goal G = new Goal(rs.getDouble("goalProtein"), rs.getDouble("goalCalcium"), rs.getDouble("goalSodium"), rs.getDouble("goalFiber"), rs.getDouble("goalVitaminc"), rs.getDouble("goalPotassium")
    				, rs.getDouble("goalCarbs"), rs.getDouble("goalSugars"), rs.getDouble("goalFat"), rs.getDouble("goalWater"), rs.getInt("goalCalories"), rs.getDouble("goalSaturated"), rs.getDouble("goalMono"), rs.getDouble("goalPoly"));
    			System.out.println("HELLO");
    			currentUser.setGoal(G);
    		}
    	}
    	catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
    public void sendGoals() {
    	try {
    		currentUser.getGoal().setGoals(currentUser.getGoal().getGoalProtein(), currentUser.getGoal().getGoalCalcium(), currentUser.getGoal().getGoalSodium(), currentUser.getGoal().getGoalFiber(), currentUser.getGoal().getGoalVitaminc(), currentUser.getGoal().getGoalPotassium(), 
    				currentUser.getGoal().getGoalCarbs(), currentUser.getGoal().getGoalSugars(), currentUser.getGoal().getGoalFat(), currentUser.getGoal().getGoalWater(), currentUser.getGoal().getGoalCalories(), currentUser.getGoal().getGoalSaturated(), 
    				currentUser.getGoal().getGoalMono(), currentUser.getGoal().getGoalPoly());
    		String query = "update spannuto10.Users set goalProtein = " + currentUser.getGoal().getGoalProtein() + ", goalCalcium = " + currentUser.getGoal().getGoalCalcium() + ", goalSodium = "  + currentUser.getGoal().getGoalSodium() + ", goalFiber = " +
    				 currentUser.getGoal().getGoalFiber() + ", goalVitaminc = " + currentUser.getGoal().getGoalVitaminc() + ", goalPotassium = "  + currentUser.getGoal().getGoalPotassium() + ", goalCarbs = " + currentUser.getGoal().getGoalCarbs() + ", goalSugars = "
    						 + currentUser.getGoal().getGoalSugars() + ", goalFat = " + currentUser.getGoal().getGoalFat() + ", goalWater = " + currentUser.getGoal().getGoalWater() + ", goalCalories = " + currentUser.getGoal().getGoalCalories() + ", goalSaturated = "
    						 + currentUser.getGoal().getGoalSaturated() + ", goalMono = " + currentUser.getGoal().getGoalMono() + ", goalPoly = " + currentUser.getGoal().getGoalPoly() + " where username = '" + currentUser.getUsername() + "'";
    		Connection con = getConnection();
    		Statement stmt = con.createStatement();
    		stmt.executeUpdate(query);
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }

    public String getColor(double total, double goal) {
    	if (total > goal) {
    		return "color:red";
    	}
    	else if (total == goal) {
    		return "color:green";
    	}
    	else {
    		return "color:black";
    	}
    }

}












