package celsia;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class TestWorker extends Thread {
	private CountDownLatch myLatch;
	private List<CountDownLatch> latches;
	private Object object;
	private Method method;
	private Queue<Throwable> exceptionList;

	public TestWorker(Object object, Method method, Queue<Throwable> exceptionList, CountDownLatch myLatch, List<CountDownLatch> latches) {
		this.myLatch = myLatch;
		this.latches = latches;
		this.object = object;
		this.method = method;
		this.exceptionList = exceptionList;
	}

	public void run() {
		try {
			if (latches != null) {
				for (CountDownLatch latch : latches) {
					latch.await();
				}
			}

			// System.out.println(Thread.currentThread() + ": " + object.getClass().getCanonicalName() + " " + method.getName());
			method.invoke(object);
		} catch (Exception e) {
			Common.handleException(e, method, object, exceptionList);
		}

		if (myLatch != null) {
			myLatch.countDown();
		}
	}
}
