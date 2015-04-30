package com.altitudelabs.swiftcart.model;

public class ProductItemDetail {
	
	public String name;
	public int image;
	public String description;
	public int amtPerServing;
	public int calories;
	public float totalFat;
	public float fat1;
	public float fat2;
	public float fat3;
	public float cholesterol;
	public int sodium;
	public int potassium;
	public float totalCarb;
	public float fiber;
	
	public ProductItemDetail() {
		
	}
	
	public ProductItemDetail(String name, int image, String description, int amtPerServing, int calories, 
			float totalFat, float fat1, float fat2, float fat3, 
			float cholesterol, int sodium, int potassium, float totalCarb, float fiber) {
		this.name = name;
		this.image = image;
		this.description = description;
		this.amtPerServing = amtPerServing;
		this.calories = calories;
		this.totalFat = totalFat;
		this.fat1 = fat1;
		this.fat2 = fat2;
		this.fat3 = fat3;
		this.cholesterol = cholesterol;
		this.sodium = sodium;
		this.potassium = potassium;
		this.totalCarb = totalCarb;
		this.fiber = fiber;
	}
	
}
