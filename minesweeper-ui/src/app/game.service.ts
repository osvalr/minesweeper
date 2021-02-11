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

  createGame(size: number): Observable<GameResponse> {
    return this.httpClient.post<GameResponse>(this.ENDPOINT, { 'size': 0 });
  }

  getDetails(gameId: number): Observable<GameDetailsResponse> {
    return this.httpClient.get<GameDetailsResponse>(this.ENDPOINT + "?id=" + gameId)
  }
  open(gameId: number, x: number, y: number): Observable<void> {
    return this.httpClient.post<void>(this.ENDPOINT + gameId + "/open", { 'x': x + 1, 'y': y + 1 })
  }

  flag(gameId: number, x: number, y: number): Observable<void> {
    return this.httpClient.post<void>(this.ENDPOINT + gameId + "/flag", { 'x': x + 1, 'y': y + 1 })
  }

}
