import { Fav } from "./Favs.ts";
import { Pool } from "pg";
import dotenv from 'dotenv';
dotenv.config();

const db_schema = process.env.DB_SCHEMA;

export class Game {
    game_id: string;
    home_team_id: string;
    away_team_id: string;
    match_date: Date;
    home_goals: number | null;
    away_goals: number | null;
    constructor(game_id: string, home_team_id: string, away_team_id: string, match_date: Date, home_goals: number | null, away_goals: number | null) {
        this.game_id = game_id;
        this.home_team_id = home_team_id;
        this.away_team_id = away_team_id;
        this.match_date = match_date;
        this.home_goals = home_goals;
        this.away_goals = away_goals;
    }
}

export class GameDAO {

    private pool: Pool

    constructor(pg_pool: Pool) {
        this.pool = pg_pool;
    }

    private rowToGame(row: any): Game {
        return new Game(
            "game_id" in row ? String(row["game_id"]) : "",
            "home_team_id" in row ? String(row["home_team_id"]) : "",
            "away_team_id" in row ? String(row["away_team_id"]) : "",
            "match_date" in row ? new Date(row["match_date"]) : new Date(0),
            "home_goals" in row && row["home_goals"] !== null ? Number(row["home_goals"]): null,
            "away_goals" in row && row["away_goals"] !== null ? Number(row["away_goals"]): null
        );
    }

    async getGamesByTeamId(team_id: string): Promise<Game[]> {
        const query = `
            SELECT * FROM ${db_schema}.fp_games
            WHERE (home_team_id = '${team_id}' OR away_team_id = '${team_id}')
            ORDER BY match_date DESC
        `;
        const result = await this.pool.query(query);
        return result.rows.map(row => this.rowToGame(row));
    }

    async getGamesForFavs(favs: Fav[]): Promise<Game[]> {
        if (favs.length === 0) {
            return [];
        }
        const conditions = favs
            .map(fav => `((home_team_id = '${fav.team_id}' OR away_team_id = '${fav.team_id}'))`)
            .join(" OR ");
        const query = `
            SELECT * FROM ${db_schema}.fp_games
            WHERE ${conditions}
            ORDER BY match_date DESC
        `;
        const result = await this.pool.query(query);
        return result.rows.map(row => this.rowToGame(row));
    }

    async getRecentGames(): Promise<Game[]>{
        const query = `
            SELECT * FROM ${db_schema}.fp_games
            ORDER BY match_date DESC
        `;
        const result = await this.pool.query(query);
        return result.rows.map(row => this.rowToGame(row));
    }
}
