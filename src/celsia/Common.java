package celsia;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Queue;

public class Common {
	public static void handleException(Exception e, Method method, Object object, Queue<Throwable> exceptionList) {
		if (e instanceof InvocationTargetException) {
			if (/* e.getCause() instanceof java.sql.SQLException && */("someMethod".equals(method.getName()) || "someMethod".equals(method.getName()))) {

				System.out.println(Thread.currentThread() + ": " + object.getClass().getCanonicalName() + " " + method.getName() + " - Ignoring caught exception: " + e.getClass().getName());

			} else {
				exceptionList.add(e.getCause());
			}
		} else {
			// System.out.println(Thread.currentThread() + ": " + object.getClass().getCanonicalName() + " " + method.getName());
			exceptionList.add(e);
		}
	}
}
