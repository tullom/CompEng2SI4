import java.util.Scanner;
import java.util.Random;
public class testClass {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		Random r = new Random();
		char str;
		int bigNum = 482947929;
		
		HugeInteger hugeNum = new HugeInteger("99");
		HugeInteger hugeNum2 = new HugeInteger("26");
		
//		str = s.next().charAt(0);
//		bigNum = str;s
//		System.out.println(hugeNum.add(hugeNum2));
		HugeInteger sum = hugeNum.add(hugeNum2);
		System.out.println(sum.toString());
		System.out.println(hugeNum2.toString());
		s.close();
		
	}
}
