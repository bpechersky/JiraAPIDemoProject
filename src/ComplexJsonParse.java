import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js = new JsonPath(payload.CoursePrice());
		//print number of courses returned by API
		
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//Print purchase amount
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print title for the first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//Print all course titles and their respective prices
		for (int i=0;i<count;i++) {
			String courseTitle = js.get("courses["+i+"].title");
			int coursePrice = js.get("courses["+i+"].price");
			System.out.println(js.get("courses["+i+"].price").toString());
			System.out.println(courseTitle + " " +coursePrice);
		}
		System.out.println("print number of courses sold by RPA course");
		for (int i=0; i< count; i++) {
			String courseTitle = js.getString("courses["+i+"].title");
			if (courseTitle.equalsIgnoreCase("RPA"))
					{
						//return copies sold by RPA
						int copies = js.get("courses["+i+"].copies");
						System.out.println(copies);
						break;
					}
		}
		

		
		
		
	}

}
