package edu.csus.csc130.fall2018.assignment2;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class QuickSortNonRecursiveTest {

	@Test
	void testSortI_1() {
		Character[] a = {'b'};
		QuickSortNonRecursive.sort(a);
		Assert.assertTrue(SortUtils.isNonDecreasingOrder(a));
	}
	
	@Test
	void testSortI_2() {
		Integer[] a = {2, 1};
		QuickSortNonRecursive.sort(a);
		Assert.assertTrue(SortUtils.isNonDecreasingOrder(a));
	}
	
	@Test
	void testSortI_3() {
		Integer[] a = {1, 2};
		QuickSortNonRecursive.sort(a);
		Assert.assertTrue(SortUtils.isNonDecreasingOrder(a));
	}
	
	@Test
	void testSortI_4() {
		String[] a = {"1", "3", "2"};
		QuickSortNonRecursive.sort(a);
		Assert.assertTrue(SortUtils.isNonDecreasingOrder(a));
	}
	
	@Test
	void test_SortI_5() {
		Integer[] a = {
				303, 698, 891, 719, 862, 406, 206, 78, 370, 481, 
				101, 271, 181, 682, 358, 759, 356, 796, 557, 983, 
				45, 844, 237, 900, 564, 632, 895, 722, 769, 935, 
				819, 742, 587, 432, 349, 94, 424, 18, 918, 503, 
				316, 789, 891, 780, 422, 44, 699, 549, 139, 688, 
				241, 875, 706, 402, 65, 118, 470, 276, 32, 626, 
				2, 668, 221, 516, 702, 678, 619, 524, 280, 117, 
				576, 377, 454, 22, 584, 539, 848, 242, 778, 161, 
				283, 310, 516, 263, 7, 528, 139, 148, 473, 623, 
				746, 846, 301, 767, 179, 98, 332, 728, 604, 932};
		QuickSortNonRecursive.sort(a);
		Assert.assertTrue(SortUtils.isNonDecreasingOrder(a));
	}

}
