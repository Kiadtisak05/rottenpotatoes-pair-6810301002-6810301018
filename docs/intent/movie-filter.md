# Intent: Find movies a child may watch by choosing a rating
Author: Kiadtisak05 and Nightlynight24, for the customer proxy (a parent of an 8-year-old). Status: draft.

## Problem
To choose a movie for an 8-year-old, a parent has to open each movie one by one to check its rating.
The list is long, so finding a movie the child may watch takes too much time.

## Proposed outcome
A parent chooses one rating and sees only the movies with that rating, without opening any movie.
Newer movies are shown before older ones.

## Affected users and systems
Parents first, then every moviegoer. The movie catalog of Rotten Potatoes (Movie, Rating and
MovieCatalog from L2). There is no web page yet: the behaviour is checked through the domain classes.

## Constraints
No login, no age profile, no recommendations, and no personal data is kept; ratings are the poster labels G, PG, PG-13, R and NC-17.
Plain Java this week, no database.

## Open questions
What should the parent see when no movie has the chosen rating?
Can the parent choose more than one rating at the same time?
Does "newest" mean the release date or the date the movie was added to the catalog?
