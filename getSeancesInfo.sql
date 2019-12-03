use cinema;
SELECT 
    f.name,
    h.name,
    s.date,
    s.time
FROM
    seances s
        JOIN
    films f ON s.id_film = f.id_film
        JOIN
	halls h ON s.id_hall = h.id_hall;