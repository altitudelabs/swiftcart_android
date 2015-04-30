package com.altitudelabs.swiftcart.model;

import com.altitudelabs.swiftcart.R;

public class ProductData {

	private static ProductData instance = null;

	public static ProductData getInstance() {
		if(instance == null) {
			instance = new ProductData();
		}
		return instance;
	}
	
	protected ProductData() {
		
	}
	
	public int[] getProductTypeIds() {
		int[] productTypes = {1,2};
		return productTypes;
	}
	
	/**
	 * 
	 * @param id product type id
	 * @return name of product type id
	 */
	public String getProductType(int id) {
		switch (id) {
		case (1) : {
			return "Vegetables";
		}
		case (2) : {
			return "Fruits";
		}
		default: {
			return null;
		}
		}
	}
	
	public int[] getProductId(int productTypeId) {
		switch (productTypeId) {
		case (1) : {
			return new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18};
		}
		case (2) : {
			return new int[]{20, 21, 22, 23, 24, 25, 26, 27, 28};
		}
		default: {
			return null;
		}
		}
	}
	
	/**
	 * Check product type by product id
	 * @param productId
	 * @return
	 */
	public int getProductTypeId(int productId) {
		return productId/10;
	}
	
	public String getActionBarTitle(int productTypeId) {
		if (productTypeId == 1 || productTypeId == 2) {
			return "Fresh Produce";
		}
		return null;
	}
	
	/**
	 * 
	 * @param productId
	 * @return product item from product id
	 */
	public ProductItem getProductItem(int productId) {
		ProductItem item = null;
		switch (productId) {
		case (10) : {
//			new ProductItem(id, isSoldOut, name, numberPerPack, priceNow, isDiscounted, priceOriginal, imageResId)
			item = new ProductItem(10, false, "Artichoke", "3 per pack", 40.00f, false, 35.50f, R.drawable.category_vegetables_artichoke);
			break;
		}
		case (11) : {
			item = new ProductItem(11, false, "Asparagus", "500g", 15.00f, false, 12.0f, R.drawable.category_vegetables_asparagus);
			break;
		}
		case (12) : {
			item = new ProductItem(12, false, "Bak Choy", "500g", 13.5f, true, 15.0f, R.drawable.category_vegetables_bokchoy);
			break;
		}
		case (13) : {
			item = new ProductItem(13, false, "Broccoli", "2 per pack", 10.0f, false, 14.5f, R.drawable.category_vegetables_brocolli);
			break;
		}
		case (14) : {
			item = new ProductItem(14, false, "Brussel Sprouts", "1 pack", 19.00f, false, 14.5f, R.drawable.category_vegetables_brussel_sprouts);
			break;
		}
		case (15) : {
			item = new ProductItem(15, false, "Cabbage", "1 per pack", 12.00f, false, 14.5f, R.drawable.category_vegetables_cabbage);
			break;
		}
		case (16) : {
			item = new ProductItem(16, false, "Carrots", "500g", 11.0f, false, 14.5f, R.drawable.category_vegetables_carrots);
			break;
		}
		case (17) : {
			item = new ProductItem(17, false, "Cauliflower", "2 per pack", 12.00f, false, 10.50f, R.drawable.category_vegetables_cauliflower);
			break;
		}
		case (18) : {
			item = new ProductItem(18, false, "Celery", "1 pack", 14.0f, false, 14.5f, R.drawable.category_vegetables_celery);
			break;
		}
		
		case (20) : {
			item = new ProductItem(20, false, "Gala Apples", "4 per pack", 24.00f, false, 14.5f, R.drawable.food_category_gala_apple);
			break;
		}
		case (21) : {
			item = new ProductItem(21, false, "Avocado", "3 per pack", 28.00f, false, 25.00f, R.drawable.category_fruits_avocado);
			break;
		}
		case (22) : {
			item = new ProductItem(22, false, "Banana", "5 per pack", 15.0f, false, 14.5f, R.drawable.category_fruits_banana);
			break;
		}
		case (23) : {
			item = new ProductItem(23, false, "Strawberry", "1 box", 32.00f, false, 29.00f, R.drawable.category_fruits_strawberry);
			break;
		}
		case (24) : {
			item = new ProductItem(24, false, "Green Grapes", "1 box", 18.0f, false, 14.5f, R.drawable.category_fruits_grapes);
			break;
		}
		case (25) : {
			item = new ProductItem(25, false, "Kiwifruit", "3 per pack", 13.5f, true, 15.0f, R.drawable.category_fruits_kiwifruit);
			break;
		}
		case (26) : {
			item = new ProductItem(26, false, "Orange", "3 per pack", 10.0f, false, 14.5f, R.drawable.category_fruits_orange);
			break;
		}
		case (27) : {
			item = new ProductItem(27, false, "Peach", "3 per pack", 36.00f, false, 32.00f, R.drawable.category_fruits_peach);
			break;
		}
		case (28) : {
			item = new ProductItem(28, false, "Grapefruit", "1 per pack", 10.4f, false, 8.0f, R.drawable.category_fruits_grapefruit);
			break;
		}
		}
		return item;
	}
	
	public ProductItemDetail getProductItemDetail(int productId) {
		ProductItemDetail item = null;
		
		switch (productId) {
		case (10) : {
			item = new ProductItemDetail("Artichoke", R.drawable.big_vegetables_artichoke, "Product of USA. Artichoke is one of the popular winter season, edible flower bud of the Mediterranean origin. Its use as a vegetable is well known since ancient times for its medicinal and health benefiting qualities. ", 
					100, 47, 0.2f, 0f, 0.1f, 0f, 0f, 94, 370, 11f, 5f);
		}
		break;
		case (11) : {
			item = new ProductItemDetail("Asparagus", R.drawable.big_vegetables_asparagus, "Product of China. Asparagus is a commonly eaten vegetable in many parts of the world and is well known for its unique, savory taste. Asparagus ranks among the top 20 foods in regards to ANDI score (Aggregate Nutrient Density Index), which measures vitamin, mineral and phytonutrient content in relation to the caloric content. To earn a high ANDI rank, food must provide a high amount of nutrients for a small amount of calories.", 
					100, 20, 0.1f, 0f, 0f, 0f, 0f, 2, 202, 3.9f, 2.1f);
		}
		break;
		case (12) : {
			item = new ProductItemDetail("Bak Choy", R.drawable.big_vegetables_bokchoy, "Product of Hong Kong. An increasingly popular member of the cruciferous vegetable family, bok choy is being recognized more and more often for its standout nutrient richness. This member of the cabbage family is one of our highest nutritionally ranked vegetables and it provides good, very good, or excellent amounts of 21 nutrients. Unlike some other members of the cabbage family, these ranked nutrients include omega-3s, as well as the antioxidant mineral zinc.", 
					100, 13, 0.2f, 0f, 0.1f, 0f, 0f, 65, 252, 2.2f, 1.0f);
		}
		break;
		case (13) : {
			item = new ProductItemDetail("Broccoli", R.drawable.big_vegetables_broccoli, "Product of China. These little mini trees are notorious for being pushed off the plates of kids around the world, but broccoli's reputation as one of the healthiest veggies still rings true. Broccoli belongs to the cruciferous vegetable family, which includes kale, cauliflower, Brussels sprouts, bok choy, cabbage, collard greens, rutabaga and turnips. These nutrition powerhouses supply loads of nutrients for little calories.", 
					100, 34, 0.4f, 0f, 0f, 0f, 0f, 33, 316, 7f, 2.6f);
		}
		break;
		case (14) : {
			item = new ProductItemDetail("Brussel Sprouts", R.drawable.big_vegetables_brussel_sprouts, "Product of USA. Brussels sprouts can provide you with some special cholesterol-lowering benefits if you will use a steaming method when cooking them. The fiber-related components in Brussels sprouts do a better job of binding together with bile acids in your digestive tract when they've been steamed. When this binding process takes place, it's easier for bile acids to be excreted, and the result is a lowering of your cholesterol levels. Raw Brussels sprouts still have cholesterol-lowering ability Ñ just not as much as steamed Brussels sprouts.", 
					100, 9, 0.30f, 0.0f, 0f, 0f, 0f, 25, 289, 8.95f, 3.8f);
		}
		break;
		case (15) : {
			item = new ProductItemDetail("Cabbage", R.drawable.big_vegetables_cabbage, "Product of Hong Kong. Cabbage, which is often lumped into the same category as lettuce because of their similar appearance, is actually a part of the cruciferous vegetable family. Cruciferous vegetables like cabbage, kale and broccoli are notorious for being chock-full of beneficial nutrients. If you are trying to improve your diet, cruciferous vegetables should be at the very top of your grocery list. Cabbage can vary in color from green to red and purple, and the leaves can be smooth or crinkled. With less than 20 calories per half cup cooked, it is a vegetable worth making room on your plate for.", 
					100, 25, 0.1f, 0f, 0f, 0f, 0f, 18, 170, 6f, 2.5f);
		}
		break;
		case (16) : {
			item = new ProductItemDetail("Carrots", R.drawable.big_vegetables_carrots, "Product of Hong Kong. Carrots are often thought of as the ultimate health food. It is believed that the carrot was first cultivated in the area now known as Afghanistan thousands of years ago as a small forked purple or yellow root with a woody and bitter flavor, resembling nothing of the carrot we know today. It is the antioxidant beta-carotene that gives carrots their bright orange color. Beta-carotene is absorbed in the intestine and converted into vitamin A during digestion. Carrots also contain fiber, vitamin K, potassium, folate, manganese, phosphorous, magnesium, vitamin E and zinc.", 
					100, 41, 0.2f, 0f, 0.1f, 0f, 0f, 69, 320, 10f, 2.8f);
		}
		break;
		case (17) : {
			item = new ProductItemDetail("Cauliflower", R.drawable.big_vegetables_cauliflower, "Product of China. Cauliflower even ranks among the top 20 foods in regards to ANDI score (Aggregate Nutrient Density Index), which measures vitamin, mineral and phytonutrient content in relation to caloric content. To earn high rank, a food must provide a high amount of nutrients for a small amount of calories. As part of the brassica family, more commonly known as cruciferous vegetables, cauliflower contains antioxidants and phytonutrients that can protect against cancer, fiber that helps with satiety, weight loss and a healthy digestive tract, choline that is essential for learning and memory as well as many other important nutrients.", 
					100, 14, 0f, 0f, 0f, 0f, 0f, 30, 303, 3, 1);
		}
		break;
		case (18) : {
			item = new ProductItemDetail("Celery", R.drawable.big_vegetables_celery, "Product of USA. Celery is a vegetable belonging to the Apiaceae family. It is well known for its crunchy stalks, which people often consume as a low calorie snack. However, celery is not only a good low calorie food. There are a number of other reasons why you might want to include this vegetable in your diet. Celery is thought to be beneficial for the digestive tract and cardiovascular system. In addition, the seeds of the plant are also commonly used in medicine to help relieve pain.", 
					100, 16, 0.2f, 0f, 0.1f, 0f, 0f, 80, 260, 3f, 1.6f);
		}
		break;
		
		case (20) : {
			item = new ProductItemDetail("Gala Apples", R.drawable.big_fruits_gala_apple, "Product of the USA. Gala apples are usually small and red with a portion being greenish or yellow-green, vertically striped. These apples are fairly resistant to bruising and are sweet, vanilla tasting, grainy and with a mild flavor and a thinner skin than most apples. Quality apples will include firmness, crispness and sweetness.", 
					242, 130, 0f, 0f, 0f, 0f, 0f, 0, 260, 34, 5);
		}
		break;
		case (21) : {
			item = new ProductItemDetail("Avocado", R.drawable.big_fruits_avocado, "Product of the USA. California avocados are prized with high nutrient value and a great complement to all sorts of dishes due to good flavor and rich texture. It is a popular food among health conscious individuals who refer them to one of the superfoods. The yellow-green inside the fruit is eaten but the skin and seed are discarded. The fruit contains a wide variety of nutrients including 20 different vitamins and minerals.", 
					100, 160, 15f, 2.1f, 1.8f, 10f, 0f, 7, 485, 9, 7);
		}
		break;
		case (22) : {
			item = new ProductItemDetail("Banana", R.drawable.big_fruits_banana, "Product from Indonesia. Bananas have a thick outer yellow skin, usually elongated and curved, grainy and sticky in texture. A favorite among infants to elders, they are good in fiber, and potassium lowering blood pressure. Bananas have cardiovascular, and digestive benefits.", 
					100, 89, 0.1f, 0.1f, 0f, 0f, 0f, 1, 358, 23f, 2.6f);
		}
		break;
		case (23) : {
			item = new ProductItemDetail("Strawberry", R.drawable.big_fruits_strawberry, "Product of the USA. Ranked as one of the 50 best antioxidant sources, strawberries are commonly eaten fruits with a fresh aroma, and are bright red in color. Whether consumed in large quantities by itself, strawberries can be used to prepare pies, fruit juice, milkshakes and paired with ice cream. Strawberries can improve blood sugar regulation and provide anti-inflammatory benefits.", 
					100, 33, 0.3f, 0f, 0.2f, 0f, 0f, 1, 153, 8, 2);
		}
		break;
		case (24) : {
			item = new ProductItemDetail("Green Grapes", R.drawable.big_fruits_green_grapes, "Product of the USA. Grapes are high in water content, and are good for hydration. Green, red and black in color, the fruit is high in antioxidants for eye health, skin and several chronic illnesses. Grapes also boast the power of the flavanoids myricetin and quercetin which help the body counter-act harmful free radical formation.", 
					100, 67, 0.4f, 0.1f, 0.1f, 0f, 0f, 2, 191, 17, 0.9f);
		}
		break;
		case (25) : {
			item = new ProductItemDetail("Kiwifruit", R.drawable.big_fruits_kiwifruit, "Product of New Zealand. Kiwis are a nutrient dense food, meaning they are high in nutrients and low in calories. The possible health benefits of consuming kiwis include maintaining healthy skin tone and texture, reducing blood pressure and preventing heart disease and stroke.", 
					100, 61, 0.5f, 0f, 0.3f, 0f, 0f, 3, 312, 15, 3);
		}
		break;
		case (26) : {
			item = new ProductItemDetail("Orange", R.drawable.big_fruits_orange, "Product of USA. Oranges are rich in citrus limonoids, proven to help fight a number of varieties of cancer including that of the skin, lung, breast, stomach and colon. Drinking orange juice regularly prevents kidney diseases and reduces the risk of kidney stones. Oranges are full of potassium, an electrolyte mineral is responsible for helping the heart function well. When potassium levels get too low, you may develop an abnormal heart rhythm, known as an arrhythmia.", 
					100, 47, 0.1f, 0f, 0f, 0f, 0f, 0, 181, 12, 2.4f);
		}
		break;
		case (27) : {
			item = new ProductItemDetail("Peach", R.drawable.big_fruits_peach, "Product of China. Peaches are a characteristically fuzzy fruit native to northwest China. They are a member of the stone fruit family, meaning that they have one large middle seed, along with cherries, apricots, plums and nectarines. The inner flesh of a peach can range in color from white to yellow or orange. There are two different varieties of peaches: freestone and clingstone, which refer to whether the flesh sticks to the inner seed or easily comes apart.", 
					100, 39, 0.2f, 0f, 0.1f, 0.1f, 0f, 0, 190, 0, 1.5f);
		}
		break;
		case (28) : {
			item = new ProductItemDetail("Grapefruit", R.drawable.big_fruits_grapefruit, "Product of USA. The grapefruit was bred in the 18th century as a cross between a pomelo and an orange. They were given the name grapefruit because of the way they grew in clusters similar to grapes. Grapefruits vary in hue from white or yellow to pink and red and can range in taste very acidic and even bitter or sweet and sugary. Grapefruits are low in calories but full of nutrients. They support clear, healthy skin, help to lower our risk for many diseases and conditions and may even help with weight loss as part of an overall healthy and varied diet.", 
					100, 42, 0.1f, 0f, 0f, 0f, 0f, 0, 135, 11, 1.6f);
		}
		break;
		}
		return item;
	}
}
