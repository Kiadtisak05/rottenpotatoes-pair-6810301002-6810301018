package th.ac.cdti.rottenpotatoes.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** A read-only list of movies with the queries that the pages need. */
public final class MovieCatalog {

  private final List<Movie> movies;

  /** Keeps a copy of the given movies. */
  public MovieCatalog(List<Movie> movies) {
    this.movies = List.copyOf(movies);
  }

  /** Returns the movies with this rating, sorted by title. */
  public List<Movie> byRating(Rating rating) {
    return movies.stream()
        .filter(movie -> movie.rating() == rating)
        .sorted()
        .toList();
  }

  /** Returns every title, sorted without regard to case. */
  public List<String> titlesSorted() {
    return movies.stream()
        .sorted()
        .map(Movie::title)
        .toList();
  }

  /** Returns at most limit movies, the newest release first. */
  public List<Movie> newestFirst(int limit) {
    return movies.stream()
        .sorted(Comparator.comparing(Movie::releaseDate).reversed())
        .limit(limit)
        .toList();
  }

  /** Finds a movie by title without regard to case. */
  public Optional<Movie> findByTitle(String title) {
    return movies.stream()
        .filter(movie -> movie.title().equalsIgnoreCase(title))
        .findFirst();
  }
}
