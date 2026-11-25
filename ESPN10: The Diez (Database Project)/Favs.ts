import { Pool } from "pg";
import dotenv from 'dotenv';
dotenv.config();

const db_schema = process.env.DB_SCHEMA;

export class Fav {
    username: string;
    team_id: string;
    constructor(username: string, team_id: string) {
        this.username = username;
        this.team_id = team_id;
    }
}

export class FavDAO {

    private pg_pool: Pool

    constructor(pg_pool: Pool) {
        this.pg_pool = pg_pool;
    }

    async addFav(fav: Fav): Promise<void> {
        const query = `
            INSERT INTO ${db_schema}.fp_favs (username, team_id, sport)
            VALUES ('${fav.username}', '${fav.team_id}')
            ON CONFLICT DO NOTHING
        `;
        await this.pg_pool.query(query);
    }

    async removeFav(fav: Fav): Promise<void> {
        const query = `
            DELETE FROM ${db_schema}.fp_favs
            WHERE username = '${fav.username}' AND team_id = '${fav.team_id}'
        `;
        await this.pg_pool.query(query);
    }

    async getFavsByUsername(username: string): Promise<Fav[]> {
        const query = `SELECT * FROM ${db_schema}.fp_favs WHERE username = '${username}'`;
        const result = await this.pg_pool.query(query);
        return result.rows.map(row => new Fav(row.username, row.team_id));
    }

}
