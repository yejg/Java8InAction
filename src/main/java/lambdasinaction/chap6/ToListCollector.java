package lambdasinaction.chap6;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.*;

public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
	/*
	  interface Collector<T, A, R>
	     T是流中要收集的项目的泛型。
		 A是累加器的类型，累加器是在收集过程中用于累积部分结果的对象。
		 R是收集操作得到的对象（通常但并不一定是集合）的类型。
	 */


	// 建立新的结果容器：supplier方法
	@Override
	public Supplier<List<T>> supplier() {
		// 下面两种写法都可以
		// return () -> new ArrayList<T>();
		return ArrayList::new;
	}

	// 将元素添加到结果容器：accumulator方法
	@Override
	public BiConsumer<List<T>, T> accumulator() {
		// 下面两种写法都可以
		// return (list, item) -> list.add(item);
		return List::add;
	}

	// 对结果容器应用最终转换：finisher方法
	@Override
	public Function<List<T>, List<T>> finisher() {
		// 下面两种写法都可以
		// return i -> i;
		return Function.identity();
	}

	// 合并两个结果容器：combiner方法
	@Override
	public BinaryOperator<List<T>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	// 返回一个不可变的Set
	// 定义收集器的行为——————尤其是关于流是否可以并行归约，以及可以使用哪些优化的提示。
	// 	UNORDERED——归约结果不受流中项目的遍历和累积顺序的影响。
	//  CONCURRENT——accumulator函数可以从多个线程同时调用，且该收集器可以并行归约流。如果收集器没有标为UNORDERED，此参数无效。
	//  IDENTITY_FINISH——这表明完成器方法返回的函数是一个恒等函数，可以跳过。这种情况下，累加器对象将会直接用作归约过程的最终结果。这也意味着，将累加器A不加检查地转换为结果R是安全的。
	@Override
	public Set<Characteristics> characteristics() {
		return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
	}
}
