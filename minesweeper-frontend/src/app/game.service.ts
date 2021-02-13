import { environment } from './../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  readonly ENDPOINT = environment.address + "/games/"
  constructor(private httpClient: HttpClient) { }

  createGame(rows: number, cols: number, mines: number): Observable<GameResponseDto> {
    return this.httpClient.post<GameResponseDto>(this.ENDPOINT, { 'rows': rows, 'cols': cols, 'mines': mines });
  }

  getOpenGames(): Observable<GameListResponseDto> {
    return this.httpClient.get<GameListResponseDto>(this.ENDPOINT);
  }
  getDetails(gameId: number): Observable<GameDetailsResponse> {
    return this.httpClient.get<GameDetailsResponse>(this.ENDPOINT + gameId + "/details")
  }

  open(gameId: number, row: number, col: number): Observable<void> {
    return this.httpClient.post<void>(this.ENDPOINT + gameId + "/positions", { 'x': col + 1, 'y': row + 1 })
  }

  flag(gameId: number, row: number, col: number): Observable<void> {
    return this.httpClient.post<void>(this.ENDPOINT + gameId + "/flags", { 'x': col + 1, 'y': row + 1 })
  }

}
