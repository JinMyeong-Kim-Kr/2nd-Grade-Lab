import java.util.*;

class Expression {
	
	static boolean isNumberic(String s) { //숫자 판별 함수
		try {
			 Double.parseDouble(s);
			return true;
		} catch(NumberFormatException e) {  //문자열이 나타내는 숫자와 일치하지 않는 타입의 숫자로 변환 시 발생
			return false;
		}
	}

	static double Eval(Vector<String> infix) throws Exception {

	
		String[] arr = new String[infix.size()];
		infix.copyInto(arr);
		String tmp = String.join("  ", arr);

		System.out.println("Infix Expression : [" + tmp + "]");

		String[] Operator = {"*","/","+","-","(",")"};

		HashMap<String, Integer> isp = new HashMap<String, Integer>();
		HashMap<String, Integer> icp = new HashMap<String, Integer>();
		isp.put("(", 3); icp.put("(", 0);
		isp.put("*", 1); icp.put("*", 1);
		isp.put("/", 1); icp.put("/", 1);
		isp.put("+", 2); icp.put("+", 2);
		isp.put("-", 2); icp.put("-", 2);

		Stack<String> stack = new Stack<String>();
		Vector<String> postfix = new Vector<String>(); // postfix의 넣을 배열

		for(String x : infix){ // 식의 숫자와 연산자를 순환 
			System.out.println("Current Token : " + x);
			if (x.equals("#")){ // #이면 break
				break;	
			} 
			if(!Arrays.asList(Operator).contains(x)){ // 연산자가 아니면, 즉 숫자면 postfix에 넣음 + 계산 스택에도 넣음
				postfix.add(x);
			}
			else if(x.equals(")")){ // )가 나오면 (가 나올떄까지 stack을 지우면서 postfix에 연산자를 넣음 마지막엔 (까지 지움
				for(;!stack.peek().equals("("); stack.pop()){
					postfix.add(stack.peek());			
				}
				stack.pop();
				
			}
			else { //나머지 연산자들은 들어가는 우선순위가 stack안에 우선순위보다 높으면 바로나옴 ex) + * * 면 * 2개 다빼고 다시 *를 넣음  ex) * / -면 * / 빼고 -만 넣음
				if(stack.isEmpty()){ // 처음ㅁ 넣는거면 그냥 넣음
					stack.push(x);
				}
				else{
				for(;isp.get(stack.peek()) <= icp.get(x); stack.pop()){
					postfix.add(stack.peek());
					if(stack.size() == 1){ // size가 1이면 마지막이므로 지우고 멈춤
						stack.pop();
						break;
					}
				}
				stack.push(x); // 현재 넣으려는 연산자를 넣음
				}
			}
				String strstack = "";
				for(String y : stack){
					strstack += y + " ";
				}
				System.out.println("Stack : " + strstack);
	
		}

		
		if(!stack.isEmpty())
			for(;!stack.isEmpty();stack.pop()){ //남은 stack을 postfix에 넣음
				postfix.add(stack.peek());
			}
		String[] postfixarr = new String[postfix.size()];
		postfix.copyInto(postfixarr);
		String tmp2 = String.join("  ", postfixarr);
		System.out.println("Postfix Expression : [" + tmp2 + "]");
		// 여기까지 출력 식 아래부터 계산
		Stack<Double> evalstack = new Stack<Double>();
		double answer = 0 ; // 계산에 담기 위한 정수
		

		// 2 5 3 * - -> 5 * 3 -> 
		for(String a : postfix){
			double num1 = 0;
			double num2 = 0;
			if(isNumberic(a)){ // 숫자를 판별하고 숫자면 evalstack에 넣음
				evalstack.push(Double.parseDouble(a));
			}
			else{ // 숫자가 아니면 연산자 이므로 숫자 2개를 뽑아서 연산자로 계산함 ex) 2 2 + 면 answer에 2 넣고 pop, 그 다음 2에 answer를 + 하고 다시 지움
				if(a.equals("+")){ 
					num1 = evalstack.peek(); //위에숫자를 꺼냄
					evalstack.pop();
					num2 = evalstack.peek();
					evalstack.pop();
					answer = num1 + num2;
					evalstack.push(answer); // top = answer
				}
				else if(a.equals("-")){ 
					num1 = evalstack.peek();
					evalstack.pop();
					num2 = evalstack.peek();
					evalstack.pop();
					answer = num2 - num1;
					evalstack.push(answer);
				}
				else if(a.equals("*")){ 
					num1 = evalstack.peek();
					evalstack.pop();
					num2 = evalstack.peek();
					evalstack.pop();
					answer = num1 * num2 ;
					evalstack.push(answer);
				}
				else if(a.equals("/")){ 
					num1 = evalstack.peek();
					evalstack.pop();
					num2 = evalstack.peek();
					evalstack.pop();
					answer = num2 / num1 ;
					evalstack.push(answer);
				}
			}
		}

	// NEED TO IMPLEMENT
		return answer;
	}
	
}; 



