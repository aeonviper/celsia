package celsia;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTransactionTestData {
	public List getTests() {
		List testClasses = new ArrayList();
		testClasses.add(new somepackage.test.DatabaseTest());
		return testClasses;
	}
}
