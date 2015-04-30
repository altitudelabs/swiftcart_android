package com.altitudelabs.swiftcart.model;

public class ProductItem {
	
	public int id;
	public boolean isSoldOut;
	public String name;
	public String numberPerPack;
	public float priceNow;
	
	public boolean isDiscounted;
	public float priceOriginal;
	public int imageResId;
	public int weight;
	
	public ProductItem() {
		
	}
	
	/**
	 * 
	 * @param id
	 * @param isSoldOut
	 * @param name
	 * @param numberPerPack
	 * @param priceNow
	 * @param isDiscounted
	 * @param priceOriginal
	 */
	public ProductItem(int id, boolean isSoldOut, String name, String numberPerPack, float priceNow, boolean isDiscounted, float priceOriginal, int imageResId) {
		this.id = id;
		this.isSoldOut = isSoldOut;
		this.name = name;
		this.numberPerPack = numberPerPack;
		this.priceNow = priceNow;
		this.isDiscounted = isDiscounted;
		this.priceOriginal = priceOriginal;
		this.imageResId = imageResId;
	}
	
	public int getSavedPercentFromDiscount() {
		return Math.abs((int)((priceOriginal - priceNow) / priceOriginal * 100));
	}
}
