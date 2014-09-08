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
public class 延迟初始化 implements IDataHolder<Date> {
	private ILazyData<Date> data = new LazyData<Date>(this, () -> new Date());

	public Date getDate() {
		return data.getData();
	}

	public static void main(String[] args) throws Exception {
		延迟初始化 t = new 延迟初始化();
		System.out.println(t.getDate());
		Thread.sleep(1000);
		System.out.println(t.getDate());
		Thread.sleep(1000);
		System.out.println(new 延迟初始化().getDate());
	}

	@Override
	public void setData(ILazyData<Date> data) {
		this.data = data;
	}

	@Override
	public ILazyData<Date> getData() {
		return this.data;
	}
}

interface IDataHolder<T> {
	void setData(ILazyData<T> data);

	ILazyData<T> getData();
}

interface ILazyData<T> {
	T getData();
}

interface IDataCreator<T> {
	T creatData();
}

class LazyData<T> implements ILazyData<T> {

	private IDataHolder<T> holder;
	private IDataCreator<T> creator;

	public LazyData(IDataHolder<T> holder, IDataCreator<T> creator) {
		this.holder = holder;
		this.creator = creator;
	}

	private LazyData() {
	}

	@Override
	public T getData() {
		ILazyData<T> data = new LazyData<T>() {
			T data = creator.creatData();

			@Override
			public T getData() {
				return data;
			}
		};
		this.holder.setData(data);
		return data.getData();
	}
}