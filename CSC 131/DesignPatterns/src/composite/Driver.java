package composite;

public class Driver {
	public static void main(String[] args) {

		DistributionList cs, math, spam;
		EmailAddress dean;

		spam = new DistributionList();
		dean = new EmailAddress("Tex Avery", "tex@hollywood.edu");

		cs = new DistributionList();
		cs.add(new EmailAddress("Gilligan", "littlebuddy@island.edu"));
		cs.add(new EmailAddress("The Skipper", "skipper@island.edu"));
		cs.add(new EmailAddress("Mr. and Mrs. Howell", "magoo@island.edu"));

		math = new DistributionList();
		math.add(new EmailAddress("Fred Flintstone", "bedrock.edu"));
		math.add(new EmailAddress("Barney Rubble", "bedrock.edu"));
		math.add(new EmailAddress("Stony Curtis", "hollyrock.edu"));

		spam.add(cs);
		spam.add(math);
		spam.add(dean);

		spam.sendMessage("Buy my book!");
	}
}
