import java.util.Date;

public class 消除if {

	private static interface Holder<T> {
		T getData();
	}

	private Holder<Date> data = () -> {
		Date d = new Date();
		data = () -> d;
		return d;
	};

	public Date getDate() {
		return data.getData();
	}

	public static void main(String[] args) throws Exception {
		消除if a = new 消除if();
		System.out.println(a.getDate());
		System.out.println(a.getDate());
		Thread.sleep(1000);
		消除if b = new 消除if();
		System.out.println(b.getDate());
		System.out.println(b.getDate());
	}

}
