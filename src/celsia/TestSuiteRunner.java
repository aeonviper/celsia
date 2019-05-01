package celsia;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import trellium.test.Suite;

public class TestSuiteRunner {
	public static void main(String[] args) {
		for (List list : new TestData().getList()) {
			System.out.println("-=: Start TestSuiteRunner :=-");
			new TestSuiteRunner().run(list);
			System.out.println("-=: Finish TestSuiteRunner :=-");
		}
	}

	private void run(List testClassList) {
		ExecutorService executor = Executors.newFixedThreadPool(100);
		Queue<Throwable> exceptionList = new ConcurrentLinkedQueue<Throwable>();
		CountDownLatch executorLatch = new CountDownLatch(testClassList.size());
		Suite suite = new Suite();
		suite.init();

		TestMonitor testMonitor = new TestMonitor();
		testMonitor.add(executorLatch);

		for (Object obj : testClassList) {
			testMonitor.add(new TestRunner(executor, executorLatch, exceptionList, AnnotationScanner.scan(obj))).start();
		}

		testMonitor.start();

		try {
			executorLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		executor.shutdown();
		suite.destroy();
		System.out.println("--=== Test Result(s) ===--");
		System.out.println("Number of exceptions: " + exceptionList.size());
		for (Throwable t : exceptionList) {
			t.printStackTrace(System.out);
			System.out.println();
		}

	}
}
