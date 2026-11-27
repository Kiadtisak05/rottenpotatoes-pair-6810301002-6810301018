Tool: Claude Sonnet 5.5 (fresh Claude Code subagent, no tools, one run)
Date: 2026-11-27
Prompt: docs/ai/l3-prompt.txt

# spec.md

# Requirements Spec: Find movies a child may watch by choosing a rating

Source: intent.md (draft), Rotten Potatoes movie catalog (Movie, Rating, MovieCatalog from L2).
Authors of the intent: Kiadtisak05 and Nightlynight24. Customer proxy: a parent of an 8-year-old.

## 1. Functional requirements

Ratings are the poster labels G, PG, PG-13, R and NC-17. "The catalog" means MovieCatalog. Checks are made through the domain classes, because there is no web page yet.

- **REQ-1** The catalog shall offer an operation that takes one rating and returns the list of movies whose rating equals it. Test: with a catalog holding movies of several ratings, asking for PG returns every PG movie.
- **REQ-2** The result shall contain only movies with the chosen rating. Test: no movie in the result has a rating other than the chosen one.
- **REQ-3** The result shall contain every movie in the catalog that has the chosen rating. Test: the count of the result equals the count of catalog movies with that rating.
- **REQ-4** The operation shall accept each of the five ratings G, PG, PG-13, R and NC-17. Test: call it once per rating and check each call succeeds and filters correctly.
- **REQ-5** The result shall be ordered with newer movies before older ones. Test: for any two adjacent movies in the result, the first is not older than the second.
- **REQ-6** Each movie in the result shall carry enough data (at least its title and rating) for the parent to see it without opening the movie. Test: the result items expose title and rating directly.
- **REQ-7** The operation shall not change the catalog. Test: the catalog contents and order are identical before and after the call.
- **REQ-8** The operation shall not require login, an age profile, or any user identity, and shall not store any personal data. Test: the operation's inputs are only the rating; no user data is stored after the call.
- **REQ-9** The operation shall not recommend movies; it only filters by the chosen rating. Test: results never include movies of a different rating, whatever the child's age.
- **REQ-10** When no movie has the chosen rating, the operation shall return an empty list and shall not fail. Test: asking for NC-17 on a catalog with none returns an empty list with no exception. (Depends on an assumption, see A1.)
- **REQ-11** A missing rating (null) or a value that is not one of the five ratings shall be rejected with a clear error and shall not return a list. Test: passing null raises the defined error. (See A5.)
- **REQ-12** The feature shall be implemented in plain Java with no database. Test: the build has no database dependency and the tests run with the catalog held in memory.

## 2. User stories

**Story 1: Filter by one rating**
- As a parent of an 8-year-old,
- So that I can find a movie my child may watch without opening each movie,
- I want to choose one rating and see only the movies with that rating.

Acceptance criteria:
- Choosing a rating returns only movies with that rating (REQ-1, REQ-2).
- Every movie with that rating is returned (REQ-3).
- I do not have to open any movie to see its title and rating (REQ-6).
- If no movie has that rating, I get an empty result, not an error (REQ-10).

**Story 2: Newest first**
- As a parent,
- So that I see recent movies before old ones and can pick a current title quickly,
- I want the filtered movies to be listed newest first.

Acceptance criteria:
- The list is ordered from newest to oldest by the date chosen in A2 (REQ-5).
- Two movies with the same date keep a stable, defined order (A3).
- Ordering applies to every one of the five ratings.

**Story 3: No account needed, no data kept**
- As any moviegoer,
- So that I can use the filter without giving personal information,
- I want to filter by rating with no login, no profile, and nothing stored about me.

Acceptance criteria:
- The filter works with only a rating as input (REQ-8).
- Nothing about the user is kept after the call (REQ-8).
- Movies are not recommended or removed based on who is asking (REQ-9).

## 3. Gherkin scenarios for Story 1

```gherkin
Feature: Filter movies by rating
  As a parent of an 8-year-old
  I want to choose one rating and see only the movies with that rating
  So that I can find a movie my child may watch without opening each movie

  Background:
    Given a catalog with these movies
      | title   | rating | release year |
      | Movie A | G      | 2018         |
      | Movie B | PG     | 2022         |
      | Movie C | PG     | 2015         |
      | Movie D | R      | 2020         |

  Scenario: Happy path - choose a rating that has movies
    When the parent chooses the rating "PG"
    Then the result contains "Movie B" and "Movie C"
    And the result does not contain "Movie A"
    And the result does not contain "Movie D"
    And "Movie B" is listed before "Movie C"

  Scenario: Sad path - no movie has the chosen rating
    When the parent chooses the rating "NC-17"
    Then the result is an empty list
    And no error is raised

  Scenario: Sad path - no rating is chosen
    When the parent chooses no rating
    Then an error is raised saying a rating is required
    And no list of movies is returned

  Scenario: Sad path - a rating that is not one of the five labels
    When the parent chooses the rating "PG-18"
    Then an error is raised saying the rating is not valid
    And no list of movies is returned
```

## 4. Assumptions (not stated in the intent)

- **A1** (answers open question 1) When no movie has the chosen rating, the result is an empty list, not an error. The parent-facing message (for example "No movies found") belongs to a later web page and is out of scope now.
- **A2** (answers open question 3) "Newest" means the movie's release date, not the date it was added to the catalog. Reason: a parent wants recent films, and the added-date is a catalog detail. If Movie from L2 only holds a release year, ordering is by year.
- **A3** Movies with the same release date are ordered by title A to Z, so the order is repeatable.
- **A4** (answers open question 2) The parent chooses exactly one rating per request. Choosing several at once is out of scope. The intent says "chooses one rating".
- **A5** A null or unknown rating is rejected with an error. The intent does not say; this follows from ratings being limited to five labels.
- **A6** Movie already has a rating and a release date (or year) from L2, and Rating already models the five labels. If not, adding them is a small change, not a new feature.
- **A7** Rating matching is exact. Choosing PG does not also return G, and choosing PG-13 does not return PG. The tool does not decide that a lower rating is "suitable".
- **A8** The result is a plain Java list returned from MovieCatalog, and tests call it directly, because there is no web page yet.
- **A9** The catalog is held in memory and may be empty. An empty catalog gives an empty result.
- **A10** Movies with a missing release date are placed after all dated movies. (Only needed if L2 allows missing dates.)

## 5. Areas of concern

1. **Wrong child-safety expectation.** The tool shows movies with exactly one rating. A parent may think PG-13 or R results are "approved" for an 8-year-old, or may expect G to be included when choosing PG. The tool does not judge suitability, and the intent says no age profile and no recommendations. Wording on a future page should avoid implying advice.
2. **Open questions are answered by assumption, not by the customer.** A1, A2 and A4 should be confirmed with the customer proxy (the parent) before the spec leaves draft status.
3. **Release date versus added date.** If L2 Movie holds only one of them, the choice in A2 may be forced. The two orders can differ a lot for old films added recently.
4. **Data quality.** Movies in the catalog may have a missing or wrongly spelled rating or date. The spec does not define how the catalog is cleaned.
5. **Rating labels.** The labels G, PG, PG-13, R and NC-17 are US poster labels. Other regions use different systems. The intent fixes these five, so other systems are out of scope.
6. **Case and format.** Whether "pg-13" or "PG13" is accepted is not stated. This spec assumes the Rating type is used, so free text is not an input. Confirm if text input is expected later.
7. **No web page yet.** Story acceptance is checked through domain classes only. Real usability for the parent (reading the list without opening movies) cannot be tested until a page exists.
8. **Performance.** "The list is long" but no size is given. No performance requirement is set. A very large catalog is not considered.
9. **Privacy.** REQ-8 forbids personal data, but logging or analytics added later could break this. Keep it in mind when the web page is added.
10. **Stories 2 and 3 are partly constraints.** Story 3 is mostly a constraint, so its acceptance criteria are checked by design review and tests of inputs, not by user behaviour.
