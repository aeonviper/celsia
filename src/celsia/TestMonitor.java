package celsia;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestMonitor extends Thread {

	CountDownLatch latch = null;
	List<TestRunner> list = new ArrayList<TestRunner>();

	public TestRunner add(TestRunner testRunner) {
		list.add(testRunner);
		return testRunner;
	}

	public CountDownLatch add(CountDownLatch latch) {
		this.latch = latch;
		return latch;
	}

	public void run() {
		while (!list.isEmpty()) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("--===  Monitor ===--");
			List<TestRunner> liveList = new ArrayList<TestRunner>();
			for (TestRunner testRunner : list) {
				if (testRunner.isAlive()) {
					System.out.println(testRunner.getTestClass().getObject().getClass().getCanonicalName() + " is alive");
					liveList.add(testRunner);
				}
			}

			list = liveList;

			if (latch != null) {
				System.out.println("Latch: " + latch.getCount());
			}
		}

		System.out.println("--===  End Monitor ===--");
		if (latch != null) {
			System.out.println("Latch: " + latch.getCount());
		}
	}
}
