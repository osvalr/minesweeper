import { GameService } from './game.service';
import { Injectable } from '@angular/core';
import { Resolve } from "@angular/router";
import { Observable } from 'rxjs';

@Injectable()
export class OpenGamesResolver implements Resolve<Observable<any>> {
    constructor(private gameService: GameService) { }
    resolve(): Observable<any> {
        return this.gameService.getOpenGames()
    }

}