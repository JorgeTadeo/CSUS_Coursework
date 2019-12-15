package composite;

public class EmailAddress implements Distribution {
	private String name;
	private String address;

	public EmailAddress(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public void sendMessage(String msg) {
	}
}
