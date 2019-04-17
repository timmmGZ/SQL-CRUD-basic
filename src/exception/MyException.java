package exception;

import java.util.Random;

public class MyException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyException(String message) {
		super(message);
	}
public static void main(String[]args) {
	Random r=new Random();
	for(int i=0;i<15;i++)
		System.out.println(r.nextInt(100));
}
	public MyException(Throwable t) {
		super(t);
	}
}
