package model;

public enum Rating {
	ONESTAR,
	TWOSTAR,
	THREESTAR,
	FOURSTAR,
	FIVESTAR;

    public int toInt() {
        return this.ordinal() + 1;
    }
}
