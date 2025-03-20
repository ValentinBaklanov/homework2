package dao;

public enum SqlForDirector {
    CREATE_SQL("""
            INSERT INTO director (name_director, birth_date_director)
            VALUES (?, ?)
            """),
    UPDATE_SQL("""
            UPDATE director
            SET name_director = ?,
                birth_date_director = ?
            WHERE id_director = ?
            """),
    DELETE_SQL("""
            DELETE FROM director
            WHERE id_director = ?
            """),
    FIND_ALL_SQL("""
            SELECT director.id_director,
                name_director,
                birth_date_director,
                id_film,
                name_film,
                country,
                date_realize,
                genre
            FROM director
            LEFT JOIN public.film f on director.id_director = f.id_director
            """),
    FIND_BY_ID("""
            SELECT director.id_director,
                name_director,
                birth_date_director,
                id_film,
                name_film,
                country,
                date_realize,
                genre
            FROM director
            LEFT JOIN public.film f on director.id_director = f.id_director
            WHERE director.id_director = ?
            """),
    FIND_BY_NAME_DIRECTOR("""
            SELECT director.id_director,
                name_director,
                birth_date_director,
                id_film,
                name_film,
                country,
                date_realize,
                genre
            FROM director
            LEFT JOIN public.film f on director.id_director = f.id_director
            WHERE director.name_director = ?
            """);

    private String query;

    SqlForDirector(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
