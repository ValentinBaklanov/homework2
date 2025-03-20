package dao;

public enum SqlForFilm {
    CREATE_SQL("""
            INSERT INTO film (name_film, country, date_realize, genre, id_director)
            VALUES (?, ?, ?, ?, ?)
            """),
    UPDATE_SQL("""
            UPDATE film
            SET name_film = ?,
                country = ?,
                date_realize = ?,
                genre = ?,
                id_director = ?
            WHERE id_film = ?
            """),
    DELETE_SQL("""
            DELETE FROM film
            WHERE id_film = ?
            """),
    FIND_ALL_SQL("""
            SELECT film.id_film,
               name_film,
               country,
               date_realize,
               genre,
               d.id_director,
               d.name_director,
               d.birth_date_director
            FROM film
            INNER JOIN director d on film.id_director = d.id_director
            """),
    FIND_BY_ID("""
            SELECT film.id_film,
               name_film,
               country,
               date_realize,
               genre,
               d.id_director,
               d.name_director,
               d.birth_date_director
            FROM film
            INNER JOIN director d on film.id_director = d.id_director
            WHERE film.id_film = ?
            """);

    private final String query;

    SqlForFilm(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
