import { GameService } from './../game.service';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { throwError, of } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  gameId: number;
  startTime: number;
  endTime: number;
  gameSize: number;
  minesProb: number;
  form: FormGroup;
  gameCreated: boolean;
  game: GameResponseDto;
  gameExploded: boolean;
  field: Position[][]
  currentSize: string = '0';

  constructor(private gameService: GameService,
    private changeDetector: ChangeDetectorRef,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.gameExploded = false;
    this.gameCreated = false;
    this.gameSize = 8;
    this.minesProb = 0.1;
    this.form = new FormGroup({
      userName: new FormControl(''),
    });
    console.log(this.route.snapshot.data.openGames);
  }

  newGame() {
    this.gameExploded = false;
    this.gameCreated = false;
    this.gameService.createGame(this.gameSize, this.minesProb)
      .subscribe(res => {
        this.gameId = res.gameId;
        this.startTime = res.gameTime;
        this.gameService.getDetails(this.gameId)
          .subscribe(res2 => {
            this.gameCreated = res !== undefined;
            this.field = JSON.parse(res2.field);
          })
      })
  }
  openPosition(x: number, y: number) {
    this.gameService.open(this.gameId, x, y)
      .pipe(
        catchError(this.handleError.bind(this))
      )
      .subscribe(_ => {
        this.gameService.getDetails(this.gameId)
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
    this.gameService.getDetails(this.gameId)
      .subscribe(res2 => {
        this.endTime = res2.endTime;
        this.field = JSON.parse(res2.field);
        this.changeDetector.detectChanges()
      })
    return of()
  }
  flagPosition(x: number, y: number) {
    this.gameService.flag(this.gameId, x, y)
      .subscribe(_ => {
        this.gameService.getDetails(this.gameId)
          .subscribe(res2 => {
            this.field = JSON.parse(res2.field);
          })
      })
    return false;
  }
}
