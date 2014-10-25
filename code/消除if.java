import java.util.Date;
import java.util.function.Supplier;

public class 消除if {
	private Supplier<Date> data = () -> {
		Date d = new Date();
		data = () -> d;
		return d;
	};

	public Date getDate() {
		return data.get();
	}

	public static void main(String[] args) throws Exception {
		消除if a = new 消除if();
		System.out.println(a.getDate());
	}
}
