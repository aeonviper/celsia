package celsia;

import java.util.ArrayList;
import java.util.List;

public class TestData {
	public List<List> getList() {
		List groupList = new ArrayList();
		List list;

		list = new ArrayList();
		list.add(new somepackage.test.UnitTest());
		groupList.add(list);

		return groupList;
	}

}
