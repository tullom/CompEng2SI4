//Matteo Tullo
//400175089
//tullom
//CoE 2SI4

import java.util.Random;
public class HugeInteger {
	
	int[] data;
	boolean isNegative;
	
	//Constructors
	public HugeInteger(String val) {
		if(val.equals("")) {throw new IllegalArgumentException("No input");}
		if (val.charAt(0) == ('-')) {	//if the number is negative
			isNegative = true;
			data = new int[val.length()-1];
			for(int i=0;i<data.length;i++) {
				if(val.charAt(i+1) > 57 || val.charAt(i+1) < 48) {
					throw new IllegalArgumentException("Wrong input"); //throw an exception if the next character is not a number
				}
				data[i] = Integer.parseInt(String.valueOf(val.charAt(i+1))); //format the next character as an integer and store in the array
			}
		} else {	//if the number is positive
			isNegative = false;
			data = new int[val.length()];
			for(int i=0;i<data.length;i++) {
				if(val.charAt(i) > 57 || val.charAt(i) < 48) {
					throw new IllegalArgumentException("Wrong input");
				}
				data[i] = Integer.parseInt(String.valueOf(val.charAt(i))); //format the next character as an integer and store in the array
			}
		}
		
//		for (int i=0;i<data.length;i++) {
//			System.out.print(data[i]);
//		}
//		System.out.println(isNegative);
	}
	
	public HugeInteger(int n) {
		if(n<1) {throw new IllegalArgumentException("Wrong input");} //if input is less than one, throw an exception
		data = new int[n];
		Random r = new Random();
		if (r.nextInt(11) >= 5) {isNegative = true;} //50% chance of being negative
		else {isNegative = false;}
		data[0] = (r.nextInt(9) + 1); //the first number is between 1-9
		for(int i=1;i<n;i++) {
			data[i] = r.nextInt(10); //the subsequent numbers are between 0-9
		}
//		for (int i=0;i<data.length;i++) {
//			System.out.print(data[i]);
//		}
//		System.out.println(isNegative);
	}
	
	//Functions
	
	public HugeInteger add(HugeInteger h) {
		int carry=0;
		int[] temp;
		int counter;
		int offset=0;
		int flag = 0;
		HugeInteger sum;
		String hi = "";
		if (this.data.length == h.data.length) { //dealing with same size numbers
			temp = new int[this.data.length + 1]; //the new num can be at most one space more
			counter = this.data.length;
			if ((this.isNegative == true && h.isNegative == false)) { //if one is negative and one is positive
				int j = 0;
				while(h.data[j] == this.data[j]) {
					j++;
					if(j>=this.data.length) {
						hi +="0";
						sum = new HugeInteger(hi); //create a new HugeInteger object using the string we built and return it
						return sum;
					}
				}
				if(h.data[j] > this.data[j]) {  //check which one is larger and execute larger minus smaller
					for(int i=this.data.length-1;i>=0;i--) {
						temp[counter] = h.data[i] - this.data[i] - carry;
						if (temp[counter] < 0) {carry = 1; temp[counter]+=10;}
						else {carry = 0;}
						//System.out.println(temp[counter]);
						counter--;
					}
				} else if (h.data[j] < this.data[j]) {
					flag = 1;
					for(int i=this.data.length-1;i>=0;i--) {
						temp[counter] = this.data[i] - h.data[i] - carry;
						if (temp[counter] < 0) {carry = 1; temp[counter]+=10;}
						else {carry = 0;}
						counter--;
					}
				}
			} else if (this.isNegative == false && h.isNegative == true) { //if second one is negative and first one is positive, similar to previous function
				int j = 0;
				while(h.data[j] == this.data[j]) {
					j++;
					if(j>=this.data.length) {
						hi +="0";
						sum = new HugeInteger(hi); //create a new HugeInteger object using the string we built and return it
						return sum;
					}
				}
				if(h.data[j] > this.data[j]) {
					flag = 1;
					for(int i=this.data.length-1;i>=0;i--) {
						temp[counter] = h.data[i] - this.data[i] - carry;
						if (temp[counter] < 0) {carry = 1; temp[counter]+=10;}
						else {carry = 0;}
						counter--;
					}
				} else {
					for(int i=this.data.length-1;i>=0;i--) {
						temp[counter] = this.data[i] - h.data[i] - carry;
						if (temp[counter] < 0) {carry = 1; temp[counter]+=10;}
						else {carry = 0;}
						counter--;
					}
				}
			} else {  //if both positive or both negative
				for(int i=this.data.length-1;i>=0;i--) {
					temp[counter] = this.data[i]+h.data[i] + carry;
					if (temp[counter] > 9) {carry = 1; temp[counter]-=10;}
					else {carry = 0;}
					counter--;
					if(i==0 && carry == 1) {temp[counter] = carry;}
					
				}
			}
			
//			for (int i=0;i<temp.length;i++) {
//				System.out.print(temp[i]);
//			}
			for(int i=0;i<temp.length;i++) {
				if((this.isNegative == true && h.isNegative == true && i==0)||(flag == 1 && i==0)) {hi+="-";} //add negative sign
				if(temp[i]==0 && i==0) {continue;} //check for leading zeroes
				hi += temp[i]; //build the string
			}
		}
		else if (this.data.length > h.data.length) { //when one number has more digits then the previous
			temp = new int[this.data.length + 1];  //make an array using the length of the larger number
			offset = this.data.length - h.data.length; //the difference in sizes
			counter = this.data.length;
			if ((this.isNegative == true && h.isNegative == false)) {
				flag = 1;
				for(int i=h.data.length-1;i>=0;i--) {  //add the elements of both, where the larger array is shifted by offset
					temp[counter] = this.data[i+offset] - h.data[i] - carry;
					if (temp[counter] < 0) {carry = 1; temp[counter]+=10;}
					else {carry = 0;}
					counter--;
				}
				for(int i=offset-1;i>=0;i--) { //now the numbers in the larger num can drop down
					temp[i+1] = this.data[i]-carry;
					if (temp[i+1] < 0) {carry = 1; temp[i+1]+=10;}
					else {carry = 0;}
				}
				if(carry == 1) {temp[counter-1] = carry;}
			} else if((this.isNegative == false && h.isNegative == true)){
				for(int i=h.data.length-1;i>=0;i--) {  //add the elements of both, where the larger array is shifted by offset
					temp[counter] = this.data[i+offset] - h.data[i] - carry;
					if (temp[counter] < 0) {carry = 1; temp[counter]+=10;}
					else {carry = 0;}
					counter--;
				}
				for(int i=offset-1;i>=0;i--) { //now the numbers in the larger num can drop down
					temp[i+1] = this.data[i]-carry;
					if (temp[i+1] < 0) {carry = 1; temp[i+1]+=10;}
					else {carry = 0;}
				}
				if(carry == 1) {temp[counter-1] = carry;}
			} else {
				for(int i=h.data.length-1;i>=0;i--) {  //add the elements of both, where the larger array is shifted by offset
					temp[counter] = this.data[i+offset]+h.data[i]+carry;
					if (temp[counter] > 9) {carry = 1; temp[counter]-=10;}
					else {carry = 0;}
					counter--;
				}
				for(int i=offset-1;i>=0;i--) { //now the numbers in the larger num can drop down
					temp[i+1] = this.data[i]+carry;
					if (temp[i+1] > 9) {carry = 1; temp[i+1]-=10;}
					else {carry = 0;}
				}
				if(carry == 1) {temp[counter-1] = carry;}
			}

//			for (int i=0;i<temp.length;i++) {
//				System.out.print(temp[i]);
//			}
			for(int i=0;i<temp.length;i++) {
				if((this.isNegative == true && h.isNegative == true && i==0)||(flag == 1 && i==0)) {hi+="-";} //add negative sign
				if(temp[i]==0 && i==0) {continue;} //check for leading zeroes
				hi += temp[i]; //build the string
			}
		}
		else if (this.data.length < h.data.length) { //when one number has more digits then the previous, but the other way around
			temp = new int[h.data.length + 1];
			offset = h.data.length - this.data.length;
			counter = h.data.length;
			if ((this.isNegative == true && h.isNegative == false)) {
				for(int i=this.data.length-1;i>=0;i--) {
					temp[counter] = h.data[i+offset] - this.data[i] - carry;
					if (temp[counter] < 0) {carry = 1; temp[counter]+=10;}
					else {carry = 0;}
					counter--;
				}
				for(int i=offset-1;i>=0;i--) {
					temp[i+1] = h.data[i]-carry;
					if (temp[i+1] < 0) {carry = 1; temp[i+1]+=10;}
					else {carry = 0;}
					
				}
				if(carry == 1) {temp[counter-1] = carry;}
			} else if ((this.isNegative == false && h.isNegative == true)) {
				flag = 1;
				for(int i=this.data.length-1;i>=0;i--) {
					temp[counter] = h.data[i+offset] - this.data[i] - carry;
					if (temp[counter] < 0) {carry = 1; temp[counter]+=10;}
					else {carry = 0;}
					counter--;
				}
				for(int i=offset-1;i>=0;i--) {
					temp[i+1] = h.data[i]-carry;
					if (temp[i+1] < 0) {carry = 1; temp[i+1]+=10;}
					else {carry = 0;}
					
				}
				if(carry == 1) {temp[counter-1] = carry;}
			} else {
				for(int i=this.data.length-1;i>=0;i--) {
					temp[counter] = this.data[i]+h.data[i+offset]+carry;
					if (temp[counter] > 9) {carry = 1; temp[counter]-=10;}
					else {carry = 0;}
					counter--;
				}
				for(int i=offset-1;i>=0;i--) {
					temp[i+1] = h.data[i]+carry;
					if (temp[i+1] > 9) {carry = 1; temp[i+1]-=10;}
					else {carry = 0;}
					
				}
				if(carry == 1) {temp[counter-1] = carry;}
			}

//			for (int i=0;i<temp.length;i++) {
//				System.out.print(temp[i]);
//			}
			
			for(int i=0;i<temp.length;i++) {
				if((this.isNegative == true && h.isNegative == true && i==0)||(flag == 1 && i==0)) {hi+="-";} //add negative sign
				if(temp[i]==0 && i==0) {continue;} //check for leading zeroes
				hi += temp[i]; //build the string
			}
		}
//		System.out.println(hi);
		sum = new HugeInteger(hi); //create a new HugeInteger object using the string we built and return it
		return sum;
	}
	
	//Lab 2
	
	public HugeInteger subtract(HugeInteger h) {
		
		String hi = "";
		HugeInteger difference;
		
		if(this.isNegative == false) {
			if(h.isNegative == false) {
				HugeInteger temp;
				temp = new HugeInteger("-" + h.toString());
				difference = this.add(temp);
				return difference;
			} else if(h.isNegative == true){
				HugeInteger temp;
				temp = new HugeInteger(h.toString());
				temp.isNegative = false;
				difference = this.add(temp);
				return difference;				
			}
		} else if(this.isNegative == true){
			if (h.isNegative == false) {
				HugeInteger temp;
				temp = new HugeInteger("-" + h.toString());
				difference = this.add(temp);
				return difference;
			} else if(h.isNegative == true){
				HugeInteger temp;
				temp = new HugeInteger(h.toString());
				temp.isNegative = false;
				difference = this.add(temp);
				return difference;		
			}
		}
		
		difference = new HugeInteger("0");
		return difference;
		
	}
	
	public HugeInteger multiply(HugeInteger h) {
		HugeInteger product = new HugeInteger("0");
		HugeInteger i = new HugeInteger("0");
		HugeInteger one = new HugeInteger("1");
		while(i.compareTo(h) == -1) {
			product = product.add(this);
			i = i.add(one);
//			System.out.println(product);
		}
		return product;
		
	}
	
	public int compareTo(HugeInteger h) {
		HugeInteger temp = this.subtract(h);
//		System.out.println(temp.toString());
		if (temp.toString().equals("0")) {
			return 0;
		} else if(temp.isNegative == false) {
			return 1;
		}
		else if(temp.isNegative == true) {
			return -1;
		}
		return 0;
	}
	
	public String toString() {
		String hi = new String("");
		int count = 0;
		while(data[count] == 0) {
			count++;
			if(data.length<=count) {hi+= "0";return hi;}
		}
		if(isNegative == true) {hi+="-";}
		for(int i=count;i<data.length;i++) {
			hi += data[i];
		}
		return hi;
	}
	
}
