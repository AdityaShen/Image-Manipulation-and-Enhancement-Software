package src.controller.helper;

/**
 * This class represents a generic pair of values, often used to store related items together, such
 * as coordinates, key-value pairs, or other dual-element structures.
 *
 * @param <F> the type of the first element
 * @param <S> the type of the second element
 */
public class Pair<F, S> {

  private F first;
  private S second;

  /**
   * Constructs a Pair with specified first and second elements.
   *
   * @param first  the first element of the pair
   * @param second the second element of the pair
   */
  public Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Returns the first element of the pair.
   *
   * @return the first element of the pair
   */
  public F getFirst() {
    return this.first;
  }

  /**
   * Returns the second element of the pair.
   *
   * @return the second element of the pair
   */
  public S getSecond() {
    return this.second;
  }
}
