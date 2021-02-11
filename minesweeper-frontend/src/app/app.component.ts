import { GameService } from './game.service';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { throwError, of } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css', '../../node_modules/bootstrap/dist/css/bootstrap.min.css']
})
export class AppComponent implements OnInit {
  endTime: number;


  constructor(private gameService: GameService,
    private changeDetector: ChangeDetectorRef) { }

  gameCreated: boolean;
  game: GameResponse;
  gameExploded: boolean;
  field: Position[][]
  currentSize: string = '0';
  ngOnInit(): void {
    this.gameExploded = false;
    this.gameCreated = false;
  }

  newGame() {
    this.gameExploded = false;
    this.gameCreated = false;
    this.gameService.createGame(0)
      .subscribe(res => {
        this.game = res;
        this.gameService.getDetails(this.game.gameId)
          .subscribe(res2 => {
            this.gameCreated = res !== undefined;
            this.field = JSON.parse(res2.field);
          })
      })
  }
  openPosition(x: number, y: number) {
    this.gameService.open(this.game.gameId, x, y)
      .pipe(
        catchError(this.handleError.bind(this))
      )
      .subscribe(_ => {
        this.gameService.getDetails(this.game.gameId)
          .subscribe(res2 => {
            this.field = JSON.parse(res2.field);
          })
      })
  }
  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
      return throwError(errorMessage);
    }
    this.gameExploded = true
    this.gameService.getDetails(this.game.gameId)
      .subscribe(res2 => {
        this.endTime = res2.endTime;
        this.field = JSON.parse(res2.field);
        this.changeDetector.detectChanges()
      })
    return of()
  }
  flagPosition(x: number, y: number) {
    this.gameService.flag(this.game.gameId, x, y)
      .subscribe(_ => {
        this.gameService.getDetails(this.game.gameId)
          .subscribe(res2 => {
            this.field = JSON.parse(res2.field);
          })
      })
    return false;
  }
}
