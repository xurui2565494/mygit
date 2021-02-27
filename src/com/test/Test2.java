package com.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import com.test.dao.NoReturnMultiParam;
import com.test.dao.NoReturnNoParam;
import com.test.dao.NoReturnOneParam;
import com.test.dao.ReturnMultiParam;
import com.test.dao.ReturnNoParam;
import com.test.dao.ReturnOneParam;

public class Test2 {
	
	public static void main(String[] args) {
		
		//简化参数类型，可以不写参数类型，但是必须所有参数都不写
		NoReturnMultiParam lambda1 = (a, b) -> {
			
			System.out.println("简化参数类型(" + a + "," + b + ")");
			
		};
		
		lambda1.method(1, 2);
		
		//简化参数小括号，如果只有一个参数的话，可以省略小括号
		NoReturnOneParam lambda2 = a -> {
			
			System.out.println("简化参数小括号");
			
		};
		
		lambda2.method(3);
		
		//如果方法体只有一条语句，可以省略方法体大括号
		NoReturnNoParam lambda3 = () -> System.out.println("简化方法体大括号");
		
		lambda3.method();
		
		//如果方法体只有一条语句，并且是 return 语句，则可以省略方法体大括号
		ReturnMultiParam lambda4 = (a, b) ->  a + b;
		
		System.out.println(lambda4.method(3, 5));
		
		ReturnOneParam lambda5 = a -> a + 5;
		
		System.out.println(lambda5.method(5));
		
		ReturnNoParam lambda6 = () -> 1;
		
		System.out.println(lambda6.method());
		
		
		//快速指向已经实现的方法
		ReturnOneParam  lambda7 = a -> doubleNum(a);
		
		System.out.println(lambda7.method(8));
		
		//引用了已经实现的 doubleNum 方法
		ReturnOneParam  lambda8 = Test2 :: doubleNum;
		
		System.out.println(lambda8.method(8));
		
		Test2 test2 = new Test2();
		
		ReturnOneParam lambda9 = test2 :: addTwo;
		
		System.out.println(lambda9.method(8));
		
		//重启一个线程, lambda简化线程的创建过程
		new Thread(
				()->{
					
					System.out.println("121212");
					
				}
				
		).start();
		
		/**
		 * 集合遍历
		 */
		
		 ArrayList<Integer> list = new ArrayList<>();

	      Collections.addAll(list, 1,2,3,4,5);

	      //lambda表达式 方法引用
	      list.forEach(System.out::println);
	      
	      list.forEach(element -> System.out.println("a"+element));

	      list.forEach(element -> {
	        if (element % 2 == 0) {
	          System.out.println(element);
	        }
	      });
	      
	      list.removeIf(ele -> ele == 2); //移除遍历元素等于2的数据
	      
	      list.forEach(System.out::println);
	      
	      List<Item> items = new ArrayList<>();
	      
	      for(int i = 0; i < 1000; i++) {
		      items.add(new Item(13+i, "背心", 7.80+i));
		      items.add(new Item(11+i, "半袖", 37.80+i));
		      items.add(new Item(14+i, "风衣", 139.80+i));
		      items.add(new Item(12+i, "秋裤", 55.33+i));
	      }
	      long start = new Date().getTime();
	      
	      items.sort(new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {
				// TODO Auto-generated method stub
				return o1.getId() - o2.getId();
			}
			
	      });
	
	      System.out.println(new Date().getTime() - start);
	      
//	      items.forEach(System.out::println);
	      
	      start = new Date().getTime();
	      
	      items.sort((o1, o2) -> o1.getId() - o2.getId()); //数据量比较大的时候，排序效率比较高
	      
	      System.out.println(new Date().getTime() - start);

//	      items.forEach(System.out::println);
	      
	      int num = 10;
	      
	      Consumer<String> consumer = ele -> {
	            System.out.println(num);
	      };
	      
	      consumer.accept("hello");
	      
//	      num++;
		
	}
	
	public static int doubleNum (int a) {
		
		return a*2;
		
	}
	
	public int addTwo( int a ) {
		
		return a + 2;
	}
	
}

class ThreadTest implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
}

class Item {
	
	int id;
	String name;
	double price;
	
	Item(int id, String name, double price){
		
		this.id = id;
		this.name = name;
		this.price = price;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String toString() {
		
		return "Item("+id+","+name+","+price+")";
		
	}
	
	
}
