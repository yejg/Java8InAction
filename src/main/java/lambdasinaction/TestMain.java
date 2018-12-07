package lambdasinaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 * 开发人员: @author yejg<br>
 * 开发时间: 2018年11月08日<br>
 */
public class TestMain {

	public static void main(String[] args) {
		long fastest = Long.MAX_VALUE;
		Map<String, String> sourceMap = new HashMap<String, String>();
		for (int i = 0; i < 5000; i++) {
			sourceMap.put("" + i, "" + i);
		}
		long start = System.nanoTime();
		for (int i = 0; i < 10; i++) {
			sourceMap.entrySet().parallelStream().forEach(e -> System.out.print(e.getKey() + " "));
			System.out.println();
			long duration = (System.nanoTime() - start) / 1_000_000;
			if (duration < fastest) {
				fastest = duration;
			}
			System.out.println("done in " + duration);
		}
		System.out.println("fast in " + fastest);
	}
}

//		Map<String, String> stringMap = Stream.generate(() -> {
//			Map<String, String> tmpMap = new HashMap<String, String>();
//			tmpMap.put("1", "1");
//			return tmpMap;
//		}).limit(20).flatMap(x -> x.entrySet().stream()).collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
//
//		stringMap.entrySet().stream().forEach(x-> System.out.println(x.getKey()));