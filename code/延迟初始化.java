import java.util.Date;

/**
 * <pre>
 * 如何完全不用if实现属性的延迟初始化。
 * 
 * 整个代码完全不使用if关键字，但等价于下面代码中的功能（即：一个类属性在第一次调用时初始化）：
 * private Object obj;
 * public Object getObj() {
 * 		if (obj == null) {
 * 			obj = new Object();
 * 		}
 * 		return obj;
 * }
 * 
 * 注：在单例模式的实现中有一种实现是利用类加载的顺序来达到在第一次使用时初始化的目地的，但那种方式只适用于类属性，而非对象属性。
 * </pre>
 */
public class 延迟初始化 {
	private DataHolder<Date> holder = new DataHolder<Date>(new IDataCreator<Date>() {
		@Override
		public Date creatData() {
			return new Date();
		}
	});

	public Date getDate() {
		return holder.data.getData();
	}

	public static void main(String[] args) throws Exception {
		延迟初始化 t = new 延迟初始化();
		System.out.println(t.getDate());
		System.out.println(t.getDate());
	}
}

interface IDataCreator<T> {
	T creatData();
}

class DataHolder<T> {
	LazyData<T> data;

	public DataHolder(IDataCreator<T> creator) {
		data = new LazyData<T>(this, creator);
	}
}

class LazyData<T> {

	private DataHolder<T> holder;
	private IDataCreator<T> creator;

	public LazyData(DataHolder<T> holder, IDataCreator<T> creator) {
		this.holder = holder;
		this.creator = creator;
	}

	private LazyData() {
	}

	public T getData() {
		final T d = creator.creatData();
		this.holder.data = new LazyData<T>() {
			public T getData() {
				return d;
			}
		};
		return d;
	}
}