import { MatSnackBar } from '@angular/material';
import { GameService } from './../game.service';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { throwError, of } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  selectedGameId: number;
  selectedGame: GameDetailsResponse
  currentGame: GameDetailsResponse
  size: number;
  mines: number;
  elapsedTime: number;
  gameCreated: boolean;
  game: GameResponseDto;
  currentField: Position[][]
  currentSize: string = '0';
  openGames: GameDetailsResponse[];
  hasGames: boolean;

  gameExploded: boolean;
  interval: any;

  constructor(private gameService: GameService,
    private changeDetector: ChangeDetectorRef,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {

    this.gameCreated = false;
    this.size = 8;
    this.mines = 0.1;
    this.openGames = this.route.snapshot.data.openGames
    this.openGames = this.openGames.filter(it => it.endTime === null)

    if (this.openGames.length > 0) {
      this.hasGames = true
      this.gameCreated = true
      this.currentGame = this.openGames[0]
      this.selectedGameId = this.currentGame.gameId
      this.currentField = JSON.parse(this.currentGame.field);
      this.currentGame.exploded = false
      this.startTimer()
    }
  }
  // Colors taken from https://raw.githubusercontent.com/joelbyrd/external-resources/master/images/minesweeper.png
  readonly COLORS: string[] = [
    '#FFF',
    '#00F',
    '#007B00',
    '#F00',
    '#00007B',
    '#7B0000',
    '#007B7B',
    '#000',
    '#7B7B7B',
  ]
  startTimer() {
    this.elapsedTime = Math.trunc((Date.now() - this.currentGame.gameTime) / 1000000)
    this.interval = setInterval(() => {
      this.elapsedTime++
    }, 1000);
  }
  newGame() {
    this.gameService.createGame(this.size, this.mines)
      .subscribe(res => {
        this.gameService.getDetails(res.gameId)
          .subscribe(res2 => {
            this.startTimer();
            this.currentGame = res2;
            this.currentGame.exploded = false;
            this.currentField = JSON.parse(res2.field);
            this.openGames.push(res2)
            this.hasGames = true
            this.gameCreated = true;
          })
      })
  }
  openPosition(x: number, y: number) {
    this.gameService.open(this.currentGame.gameId, x, y)
      .pipe(
        catchError(this.handleError.bind(this))
      )
      .subscribe(_ => {
        this.gameService.getDetails(this.currentGame.gameId)
          .subscribe(res2 => {
            this.currentField = JSON.parse(res2.field);
          })
      })
  }
  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
      return throwError(errorMessage);
    }
    if (error.status === 302) {
      window.location.reload()
      this.snackBar.open('YOU WON!!!', '', {
        duration: 500,
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
      })
    } else if (error.status === 410) {
      this.currentGame.exploded = true
      this.gameService.getDetails(this.currentGame.gameId)
        .subscribe(res2 => {
          this.currentGame.endTime = res2.endTime;
          this.currentField = JSON.parse(res2.field);
          this.changeDetector.detectChanges()
          this.stopTimer()
        })
    }
    return of()
  }
  stopTimer() {
    clearInterval(this.interval);
  }
  flagPosition(x: number, y: number) {
    this.gameService.flag(this.currentGame.gameId, x, y)
      .subscribe(_ => {
        this.gameService.getDetails(this.currentGame.gameId)
          .subscribe(res => {
            this.currentField = JSON.parse(res.field);
          })
      })
    return false;
  }
  goToGame() {
    if (this.currentGame.gameId === this.selectedGameId) {
      return;
    }

    this.gameService.getDetails(this.selectedGameId)
      .subscribe(res => {

        this.gameCreated = true
        this.currentGame = res
        this.currentField = JSON.parse(res.field);
        this.currentGame.exploded = res !== undefined;
        if (res.endTime !== null) {
          this.startTimer();
        }
      })
  }
}
