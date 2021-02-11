import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OpenGamesResolver } from './open-games.resolver';


const routes: Routes = [{
  path: '',
  component: AppComponent,
  resolve: {
    openGames: OpenGamesResolver
  }
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [OpenGamesResolver]
})
export class AppRoutingModule { }
