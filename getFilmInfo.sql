use cinema;
SELECT 
    films.name,
    countries.name,
    films.date,
    films.box_office,
    films.time,
    screenwriters_directors.name,
    screenwriters_directors.lastname,
    screenwriters_directors.name,
    screenwriters_directors.lastname,
    films.poster,
    films.description
FROM
    films
        JOIN
    countries ON films.id_country = countries.id_country
        JOIN
    screenwriters_directors ON films.id_screen_writer = screenwriters_directors.id_screenwriter_director;