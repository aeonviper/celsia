package celsia;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class TestClass {
	private Object object;
	private Method beforeClassMethod;
	private Method afterClassMethod;
	private List<TestMethod> testMethods = new ArrayList<TestMethod>();
	private Map<String, CountDownLatch> latchMap = new HashMap<String, CountDownLatch>();

	public List<TestMethod> getTestMethods() {
		return testMethods;
	}

	public void setTestMethods(List<TestMethod> testMethods) {
		this.testMethods = testMethods;
	}

	public Map<String, CountDownLatch> getLatchMap() {
		return latchMap;
	}

	public void setLatchMap(Map<String, CountDownLatch> latchMap) {
		this.latchMap = latchMap;
	}

	public Method getBeforeClassMethod() {
		return beforeClassMethod;
	}

	public void setBeforeClassMethod(Method beforeClassMethod) {
		this.beforeClassMethod = beforeClassMethod;
	}

	public Method getAfterClassMethod() {
		return afterClassMethod;
	}

	public void setAfterClassMethod(Method afterClassMethod) {
		this.afterClassMethod = afterClassMethod;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
