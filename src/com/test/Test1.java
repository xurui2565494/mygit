package com.test;

import com.test.dao.NoReturnMultiParam;
import com.test.dao.NoReturnNoParam;
import com.test.dao.NoReturnOneParam;
import com.test.dao.ReturnMultiParam;
import com.test.dao.ReturnNoParam;
import com.test.dao.ReturnOneParam;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//多参无返回
		NoReturnMultiParam noReturnMultiParam = (int a, int b) -> {
			
			System.out.println("两数求和："+(a+b));
			
		};
		
		noReturnMultiParam.method(1, 3);
		
		NoReturnOneParam noReturnOneParam = (int a) -> {
			
			System.out.println("NoReturnOneParam:" + a);
			
		};
		
		noReturnOneParam.method(6);
		
		
		NoReturnNoParam noReturnNoParam = () -> {
			
			System.out.println("NoReturnNoParam");
			
		};
		
		noReturnNoParam.method();
		
		ReturnMultiParam returnMultiParam = (int a, int b) -> {
			
			return a + b;
			
		};
		
		System.out.println("ReturnMultiParam:"+returnMultiParam.method(2, 3));
		
		
		ReturnOneParam returnOneParam = (int a) -> {
			
			return a;
			
		};
		
		System.out.println("ReturnOneParam:"+returnOneParam.method(8));
		
		ReturnNoParam returnNoParam = () ->{
			
			return 1;
			
		};
		
		System.out.println("ReturnNoParam:"+returnNoParam.method());
		

	}

}
