package celsia;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestMethod {
	private Method method;
	private int invocationCount;
	private int threadPoolSize;
	private List<String> dependsOnMethods = new ArrayList<String>();

	public int getInvocationCount() {
		return invocationCount;
	}

	public void setInvocationCount(int invocationCount) {
		this.invocationCount = invocationCount;
	}

	public List<String> getDependsOnMethods() {
		return dependsOnMethods;
	}

	public void setDependsOnMethods(List<String> dependsOnMethods) {
		this.dependsOnMethods = dependsOnMethods;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}
}
