import { AuthGuard } from './auth.guard';
import { GameComponent } from './game/game.component';
import { LoginComponent } from './login/login.component';
import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OpenGamesResolver } from './open-games.resolver';


const routes: Routes = [
  {
    path: 'game',
    component: GameComponent,
    resolve: {
      openGames: OpenGamesResolver
    },
    canActivate: [
      AuthGuard
    ],
  },
  {
    path: 'login',
    component: LoginComponent,
  },

  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '/',
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
