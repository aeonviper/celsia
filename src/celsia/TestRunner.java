package celsia;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class TestRunner extends Thread {
	private ExecutorService executor;
	private TestClass testClass;
	private CountDownLatch executorLatch;
	private Queue<Throwable> exceptionList;

	public TestRunner(ExecutorService executor, CountDownLatch executorLatch, Queue<Throwable> exceptionList, TestClass testClass) {
		this.executor = executor;
		this.testClass = testClass;
		this.executorLatch = executorLatch;
		this.exceptionList = exceptionList;
	}

	public void resolve(List<String> executionOrder, TestMethod testMethod, TestClass testClass) {
		for (String dependOnMethod : testMethod.getDependsOnMethods()) {
			for (TestMethod t : testClass.getTestMethods()) {
				if (dependOnMethod.equals(t.getMethod().getName())) {
					resolve(executionOrder, t, testClass);
				}
			}
		}
		if (!executionOrder.contains(testMethod.getMethod().getName())) {
			executionOrder.add(testMethod.getMethod().getName());
		}
	}

	public void resolveExecutionOrder(List<String> executionOrder, TestClass testClass) {
		for (TestMethod testMethod : testClass.getTestMethods()) {
			resolve(executionOrder, testMethod, testClass);
		}
		// System.out.println(executionOrder);
	}

	public void run() {
		int i;
		List<CountDownLatch> latches;
		CountDownLatch myLatch;
		List<String> executionOrder = new ArrayList<String>();
		resolveExecutionOrder(executionOrder, testClass);
		Method method = null;

		try {
			if (testClass.getBeforeClassMethod() != null) {
				method = testClass.getBeforeClassMethod();
				System.out.println(Thread.currentThread() + ": " + testClass.getObject().getClass().getCanonicalName() + " " + testClass.getBeforeClassMethod().getName());
				testClass.getBeforeClassMethod().invoke(testClass.getObject());
			}

			for (String element : executionOrder) {
				for (TestMethod testMethod : testClass.getTestMethods()) {
					method = testMethod.getMethod();
					if (element.equals(testMethod.getMethod().getName())) {
						myLatch = testClass.getLatchMap().get(testMethod.getMethod().getName());
						latches = new ArrayList<CountDownLatch>();

						for (String dependOnMethod : testMethod.getDependsOnMethods()) {
							latches.add(testClass.getLatchMap().get(dependOnMethod));
						}

						for (i = 0; i < testMethod.getInvocationCount(); i++) {
							if (testMethod.getThreadPoolSize() > 1) {
								executor.execute(new TestWorker(testClass.getObject(), testMethod.getMethod(), exceptionList, myLatch, latches));
							} else {
								new TestWorker(testClass.getObject(), testMethod.getMethod(), exceptionList, myLatch, latches).run();
							}
						}
					}
				}
			}

			for (CountDownLatch latch : testClass.getLatchMap().values()) {
				latch.await();
			}

			if (testClass.getAfterClassMethod() != null) {
				method = testClass.getAfterClassMethod();
				System.out.println(Thread.currentThread() + ": " + testClass.getObject().getClass().getCanonicalName() + " " + testClass.getAfterClassMethod().getName());
				testClass.getAfterClassMethod().invoke(testClass.getObject());
			}

		} catch (Exception e) {
			Common.handleException(e, method, testClass.getObject(), exceptionList);
		} finally {
			executorLatch.countDown();
		}
	}

	public TestClass getTestClass() {
		return testClass;
	}
}
