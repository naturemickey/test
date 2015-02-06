package calc;

public class Token {

	private final int type;
	private final String text;

	public Token(int type, String text) {
		this.text = text;
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public String getText() {
		return text;
	}

}
