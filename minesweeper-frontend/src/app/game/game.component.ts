import { GameStateArray, GameStateEnum } from './../GameStateEnum';
import { COLORS_ARRAY } from '../colors.array';
import { MatSnackBar } from '@angular/material';
import { GameService } from './../game.service';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { throwError, of } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IdValue } from '../IdValue';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  readonly colorsArray = COLORS_ARRAY;

  readonly statesArray: IdValue[] = GameStateArray;
  selectedGameId: number;
  selectedGame: GameDetailsResponse
  currentGame: GameDetailsResponse

  elapsedTime: number;
  gameCreated: boolean;
  game: GameResponseDto;
  currentField: Position[][]
  currentSize: string = '0';
  openGames: GameDetailsResponse[];
  hasGames: boolean;
  form: FormGroup;
  gameExploded: boolean;
  interval: any;

  constructor(private gameService: GameService,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.form = this.formBuilder.group({
      rowsFormControl: ['5', Validators.required],
      colsFormControl: ['5', Validators.required],
      minesFormControl: ['0.1', Validators.required]
    })

    this.openGames = this.route.snapshot.data.openGames
    if (this.openGames.length > 0) {
      this.currentGame = this.openGames[0]
      this.currentField = JSON.parse(this.currentGame.field);
      this.startTimer()
    }
  }

  startTimer() {
    this.elapsedTime = Math.trunc((Date.now() - this.currentGame.gameTime) / 1000000)
    this.interval = setInterval(() => {
      this.elapsedTime++
    }, 1000);
  }
  newGame() {
    this.gameService.createGame(this.form.get('rowsFormControl').value,
      this.form.get('colsFormControl').value,
      this.form.get('minesFormControl').value)
      .subscribe(res => {
        this.stopTimer();

        this.gameService.getDetails(res.gameId)
          .subscribe(res2 => {
            this.currentGame = res2;
            this.currentField = JSON.parse(res2.field);
            this.openGames.push(res2)
            this.startTimer();
          })
      })
  }
  openPosition(x: number, y: number) {
    this.gameService.open(this.currentGame.gameId, y, x)
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
      this.stopTimer()
      this.currentGame = null
      window.location.reload()
      this.snackBar.open('YOU WON!!!', '', {
        duration: 500,
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
      })
    } else if (error.status === 410) {
      this.gameService.getDetails(this.currentGame.gameId)
        .subscribe(res2 => {
          this.currentGame.endTime = res2.endTime;
          this.currentField = JSON.parse(res2.field);
          this.currentGame.state = 2
          this.stopTimer()
        })
    }
    return of()
  }
  stopTimer() {
    clearInterval(this.interval);
  }
  flagPosition(x: number, y: number) {
    let currentValue: boolean = this.currentField[y][x].flag;
    this.gameService.flag(this.currentGame.gameId, y, x)
      .pipe(
        catchError(err => {
          this.currentField[y][x].flag = currentValue
          return of()
        })
      )
      .subscribe(_ => {
        this.currentField[y][x].flag = !currentValue
      })
    return false;
  }
  goToGame() {
    if (this.currentGame.gameId === this.selectedGameId) {
      return;
    }
    this.currentGame = null
    this.gameService.getDetails(this.selectedGameId)
      .subscribe(res => {
        this.currentGame = res
        this.currentField = JSON.parse(res.field);
        if (res.endTime !== null) {
          this.startTimer();
        }
      })
  }
}
