package reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Clone2 {
	@SuppressWarnings("unchecked")
	public static <T> T cloneObject(T source) {
		Class<?> c = source.getClass();
		T target = null;
		try {
			target = (T) c.newInstance();
			Field[] fds = Class.forName(c.getName()).getDeclaredFields();
			for (Field f : fds) {
//				System.out.println(f.getType().getName() + " " + f.getName());
				f.setAccessible(true);				
				f.set(target, f.get(source));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return target;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T deepCloneObject(T source) {
		Class<?> c = source.getClass();
		T target = null;
		try {
			target = (T) c.newInstance();
			Field[] fds = Class.forName(c.getName()).getDeclaredFields();
			for (Field f : fds) {
//				System.out.println(f.getType().getName() + " " + f.getName());
				f.setAccessible(true);
				if(f.get(source)==null){
					f.set(target, f.get(source));
				}
				else{
					f.set(target, deepCloneObject(f.get(source)));
				}
					
				
			}
		}
		catch (InstantiationException e) {
			target = source;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return target;
	}

	
	public static void main(String[] args) {
		A a=new A(3, 5);
		a.setList(new ArrayList<Integer>(){{add(1);add(2);add(3);}});
		long t1=System.currentTimeMillis();
		A b = cloneObject(a);
		for(int i=0;i<10000;i++){
			b = cloneObject(a);
		}
		long t2=System.currentTimeMillis();
		A c = deepCloneObject(a);
		for(int i=0;i<10000;i++){
			c = deepCloneObject(a);
		}
		long t3=System.currentTimeMillis();
		System.out.println(t2-t1+"ms");
		System.out.println(t3-t2+"ms");
		c.doSomeThing();
		b.doSomeThing();
		a.getList().add(4);
		c.doSomeThing();
		b.doSomeThing();
	}
}
