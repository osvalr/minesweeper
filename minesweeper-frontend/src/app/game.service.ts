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

  createGame(size: number, mines: number): Observable<GameResponseDto> {
    return this.httpClient.post<GameResponseDto>(this.ENDPOINT, { 'size': size, 'mines': mines });
  }

  getOpenGames(): Observable<GameListResponseDto> {
    return this.httpClient.get<GameListResponseDto>(this.ENDPOINT);
  }
  getDetails(gameId: number): Observable<GameDetailsResponse> {
    return this.httpClient.get<GameDetailsResponse>(this.ENDPOINT + "/" + gameId + "/details")
  }

  open(gameId: number, x: number, y: number): Observable<void> {
    return this.httpClient.post<void>(this.ENDPOINT + gameId + "/positions", { 'x': x + 1, 'y': y + 1 })
  }

  flag(gameId: number, x: number, y: number): Observable<void> {
    return this.httpClient.post<void>(this.ENDPOINT + gameId + "/flags", { 'x': x + 1, 'y': y + 1 })
  }

}
