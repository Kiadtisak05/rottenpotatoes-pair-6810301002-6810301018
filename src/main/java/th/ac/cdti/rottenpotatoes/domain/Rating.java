package th.ac.cdti.rottenpotatoes.domain;

/** The five ratings printed on movie posters. */
public enum Rating {
  G("G"), PG("PG"), PG13("PG-13"), R("R"), NC17("NC-17");

  private final String label;

  Rating(String label) {
    this.label = label;
  }

  /** Returns the label as printed on the poster, for example "PG-13". */
  public String label() {
    return label;
  }

  /** Returns the rating whose poster label is this text. */
  public static Rating fromLabel(String label) {
    for (Rating r : values()) {
      if (r.label.equals(label)) {
        return r;
      }
    }
    throw new IllegalArgumentException("unknown rating: " + label);
  }
}
