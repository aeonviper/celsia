package celsia;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AnnotationScanner {

	public static TestClass scan(Object object) {
		TestClass testClass = new TestClass();
		testClass.setObject(object);

		for (Method method : object.getClass().getMethods()) {

			for (Annotation annotation : method.getAnnotations()) {
				// System.out.println(object.getClass().getCanonicalName() + " " + method.getName());
				// System.out.println(annotation.toString());
				if (annotation instanceof Test) {
					TestMethod testMethod = new TestMethod();
					testMethod.setMethod(method);

					Test annotationTest = (Test) annotation;
					for (String dependOnMethod : annotationTest.dependsOnMethods()) {
						// System.out.println(dependOnMethod);
						testMethod.getDependsOnMethods().add(dependOnMethod);
					}

					testMethod.setInvocationCount(annotationTest.invocationCount());
					assertTrue(testMethod.getInvocationCount() > 0);

					testMethod.setThreadPoolSize(annotationTest.threadPoolSize());
					if (testMethod.getThreadPoolSize() == 0) {
						testMethod.setThreadPoolSize(1);
					}
					assertTrue(testMethod.getThreadPoolSize() > 0);

					testClass.getLatchMap().put(method.getName(), new CountDownLatch(testMethod.getInvocationCount()));

					testClass.getTestMethods().add(testMethod);
				} else if (annotation instanceof BeforeClass) {
					testClass.setBeforeClassMethod(method);
				} else if (annotation instanceof AfterClass) {
					testClass.setAfterClassMethod(method);
				} else {
					System.err.println("Unknown annotation: " + annotation);
				}
			}
		}

		return testClass;
	}

}
