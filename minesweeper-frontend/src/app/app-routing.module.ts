import { GameComponent } from './game/game.component';
import { LoginComponent } from './login/login.component';
import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OpenGamesResolver } from './open-games.resolver';


const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full',
  },
  {
    path: 'game',
    component: GameComponent,
    resolve: {
      openGames: OpenGamesResolver
    },
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: 'login',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [
    OpenGamesResolver
  ]
})
export class AppRoutingModule { }
