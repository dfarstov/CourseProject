SELECT 
	s.id_seance,
    p1.raw,
    p2.place_number,
    pr.price,
    u1.name,
    u2.lastname
FROM
    tickets t
        JOIN
    seances s ON t.id_seance = s.id_seance
		JOIN
    places p1 ON t.id_place = p1.id_place
		JOIN
    places p2 ON t.id_place = p2.id_place
		JOIN
    ticket_prices pr ON t.id_price = pr.id_price
		JOIN
    users u1 ON t.id_user = u1.id_user
		JOIN
    users u2 ON t.id_user = u2.id_user;